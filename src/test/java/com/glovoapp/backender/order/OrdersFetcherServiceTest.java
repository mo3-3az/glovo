package com.glovoapp.backender.order;

import com.glovoapp.backender.courier.Courier;
import com.glovoapp.backender.courier.Vehicle;
import com.glovoapp.backender.distance.Location;
import com.glovoapp.backender.order.config.OrdersFetcherProperties;
import com.glovoapp.backender.order.model.FoodOrder;
import com.glovoapp.backender.order.model.Order;
import com.glovoapp.backender.order.priority.OrdersPriority;
import com.glovoapp.backender.order.service.OrderRepository;
import com.glovoapp.backender.order.service.OrdersFetcherService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;


@ExtendWith(MockitoExtension.class)
class OrdersFetcherServiceTest {

    private static final String ORDERS_FILE = "/orders-test.json";

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private OrdersFetcherProperties ordersFetcherProperties;

    private OrdersFetcherService ordersFetcherService;

    private Courier courier;

    @BeforeEach
    void setUp() {
        courier = new Courier();

        try (Reader reader = new InputStreamReader(getClass().getResourceAsStream(ORDERS_FILE))) {
            Type type = new TypeToken<List<Order>>() {
            }.getType();
            List<Order> orders = new Gson().fromJson(reader, type);
            Mockito.when(orderRepository.findAll()).thenReturn(orders);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Mockito.when(ordersFetcherProperties.getDistanceSlotInMeters()).thenReturn(500);
        Mockito.when(ordersFetcherProperties.getLongDistanceInKilometers()).thenReturn(5);
        Mockito.when(ordersFetcherProperties.getFoodOrders()).thenReturn(Arrays.asList(FoodOrder.values()));
        Mockito.when(ordersFetcherProperties.getOrdersPriorities()).thenReturn(Arrays.asList(OrdersPriority.values()));
//        Mockito.when(ordersFetcherProperties.getLongDistanceVehicles()).thenReturn(Arrays.asList(Vehicle.values()));

        ordersFetcherService = new OrdersFetcherService(ordersFetcherProperties, orderRepository);
    }

    //

    @Test
    void fetchOrdersForCourierThatIsCloseToWithoutBoxAndBicycle() {
        courier.withBox(true);
        courier.withVehicle(Vehicle.BICYCLE);
        courier.withLocation(new Location(41.39998523694263, 2.172663432928287));
        final List<Order> foodOrders = ordersFetcherService.fetchOrders(courier);
        Assertions.assertFalse(foodOrders.isEmpty(), "Orders list shouldn't be empty!");
//        foodOrders.forEach(order -> Assertions.assertEquals(true, order.getFood()));
    }


    void fetchOrdersForCourierWithNoBox() {
    }


    void fetchFoodOrdersForCourierWithBox() {
    }

    //


    void fetchOrdersForCourierWithBicycle() {
    }


    void fetchOrdersForCourierWithMotorcycle() {
    }


    void fetchOrdersForCourierWithElectricScooter() {
    }


    void fetchOrdersForCourierWithNoVehicle() {
    }


    void fetchOrdersForCourierWithNormalVehicle() {
    }


    void fetchOrdersForCourierWithLongDistanceVehicle() {
    }

    //


    void fetchOrdersForCourierWithinDistance() {
    }


    void fetchOrdersForCourierOutsideDistance() {
    }

    //


    void fetchOrdersPrioritizedByDistanceVipFood() {
    }


    void fetchOrdersPrioritizedByFoodVipDistance() {
    }
}