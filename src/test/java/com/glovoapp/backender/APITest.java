package com.glovoapp.backender;

import com.glovoapp.backender.courier.Courier;
import com.glovoapp.backender.courier.CourierRepository;
import com.glovoapp.backender.courier.Vehicle;
import com.glovoapp.backender.order.config.OrdersFetcherProperties;
import com.glovoapp.backender.order.model.FoodKeyword;
import com.glovoapp.backender.order.service.OrderRepository;
import com.glovoapp.backender.order.service.OrdersFetcherService;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${backender.welcome_message}")
    String welcomeMessage;

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
    public void ordersByInvalidCourierId() throws Exception {
        mvc.perform(get("/orders/courierId")).andDo(print()).andExpect(status().isNotFound());
    }

    @Test
    public void ordersByNonexistentCourierId() throws Exception {
        mvc.perform(get("/orders/courier-xxxxxxxxxx")).andDo(print()).andExpect(status().isNotFound());
    }
}
