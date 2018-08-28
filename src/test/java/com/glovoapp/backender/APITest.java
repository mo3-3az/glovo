package com.glovoapp.backender;

import com.glovoapp.backender.courier.Courier;
import com.glovoapp.backender.courier.CourierRepository;
import com.glovoapp.backender.courier.Vehicle;
import com.glovoapp.backender.distance.DistanceCalculator;
import com.glovoapp.backender.distance.Location;
import com.glovoapp.backender.order.config.OrdersFetcherProperties;
import com.glovoapp.backender.order.model.FoodKeyword;
import com.glovoapp.backender.order.service.OrderRepository;
import com.glovoapp.backender.order.service.OrdersFetcherService;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(API.class)
public class APITest {

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private OrdersFetcherProperties ordersFetcherProperties;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrdersFetcherService ordersFetcherService;

    @Autowired
    private CourierRepository courierRepository;

    private MockMvc mvc;

    @Before
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .build();

        Assertions.assertNotNull(mvc);
    }

    @Test
    public void root() throws Exception {
        mvc.perform(get("/")).andDo(print()).andExpect(status().isOk());
    }

    @Test
    public void orders() throws Exception {
        mvc.perform(get("/orders")).andDo(print()).andExpect(status().isOk());
    }

    @Test
    public void ordersByCourierId() throws Exception {
        mvc.perform(get("/orders/courier-4ab4ce03951c")).andDo(print()).andExpect(status().isOk());
    }


    @Test
    @DisplayName(
            "Given a courier without a box." +
                    "When the courier is fetching orders." +
                    "Then only orders that are not described as food should return.")
    public void ordersByCourierIdWithoutABox() throws Exception {
        final Optional<Courier> courierWithoutABox = courierRepository.findAll().stream().filter(courier -> !courier.canDeliverFood()).findFirst();
        if (!courierWithoutABox.isPresent()) {
            throw new Exception("Cannot find a courier without a box!");
        }

        mvc.perform(get("/orders/" + courierWithoutABox.get().getId()))
                .andDo(print())
                .andExpect(status().isOk()).andDo(result -> {
            final JsonArray ordersAsJsonArray = new JsonParser().parse(result.getResponse().getContentAsString()).getAsJsonArray();
            ordersAsJsonArray.forEach(jsonElement -> {
                final String orderDesc = jsonElement.getAsJsonObject().get("description").getAsString();
                Assertions.assertFalse(FoodKeyword.isFoodOrder(orderDesc, ordersFetcherProperties.getFoodKeywords()),
                        "Order shouldn't have food since courier doesn't have a box!");
            });
        });
    }

    @Test
    @DisplayName(
            "Given a courier with a box and a motorcycle." +
                    "When the courier is fetching orders." +
                    "Then all the orders should return.")
    public void ordersByCourierIdWithABoxAndAMotorcycle() throws Exception {
        final Optional<Courier> courierWithABoxAndAMotorcycle = courierRepository.findAll().stream()
                .filter(courier -> courier.canDeliverFood() && Vehicle.MOTORCYCLE.equals(courier.getVehicle())).findFirst();
        Assertions.assertTrue(courierWithABoxAndAMotorcycle.isPresent(), "Cannot find a courier with a box and a motorcycle!");

        mvc.perform(get("/orders/" + courierWithABoxAndAMotorcycle.get().getId()))
                .andDo(print())
                .andExpect(status().isOk()).andDo(result -> {
            final JsonArray ordersAsJsonArray = new JsonParser().parse(result.getResponse().getContentAsString()).getAsJsonArray();
            Assertions.assertEquals(orderRepository.findAll().size(), ordersAsJsonArray.size()
                    , "A courier with a box and a motorcycle can deliver any order!");
        });
    }

    @Test
    @DisplayName(
            "Given a courier with a box and a bicycle." +
                    "When the courier is fetching orders." +
                    "Then only orders that are within 5 kilometers should return.")
    public void ordersByCourierIdWithABoxAndABicycle() throws Exception {
        final Optional<Courier> courierWithABoxAndABicycle = courierRepository.findAll().stream()
                .filter(courier -> courier.canDeliverFood() && Vehicle.BICYCLE.equals(courier.getVehicle())).findFirst();
        Assertions.assertTrue(courierWithABoxAndABicycle.isPresent(), "Cannot find a courier with a box and a motorcycle!");

        mvc.perform(get("/orders/" + courierWithABoxAndABicycle.get().getId()))
                .andDo(print())
                .andExpect(status().isOk()).andDo(result -> {
            final JsonArray ordersAsJsonArray = new JsonParser().parse(result.getResponse().getContentAsString()).getAsJsonArray();
            final int longDistanceInKilometers = ordersFetcherProperties.getLongDistanceInKilometers();
            ordersAsJsonArray.forEach(jsonElement -> {
                final String orderId = jsonElement.getAsJsonObject().get("id").getAsString();
                final Location pickup = orderRepository
                        .findAll()
                        .stream()
                        .filter(order -> order.getId().equals(orderId)).findFirst().get().getPickup();
                final double distance = DistanceCalculator.calculateDistanceKilometers(pickup, courierWithABoxAndABicycle.get().getLocation());
                Assertions.assertTrue(distance <= longDistanceInKilometers
                        , "Order shouldn't be futher than " + longDistanceInKilometers + " kilometers, since the courier is riding a bicycle!");
            });
        });
    }


    @Test
    public void ordersByInvalidCourierId() throws Exception {
        mvc.perform(get("/orders/courierId")).andDo(print()).andExpect(status().isNotFound());
    }

    @Test
    public void ordersByNonexistentCourierId() throws Exception {
        mvc.perform(get("/orders/courier-xxxxxxxxxx")).andDo(print()).andExpect(status().isNotFound());
    }
}
