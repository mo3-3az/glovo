package com.glovoapp.backender.order.config;

import com.glovoapp.backender.courier.Vehicle;
import com.glovoapp.backender.order.model.FoodKeyword;
import com.glovoapp.backender.order.priority.OrdersPriority;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

class OrdersFetcherPropertiesTest {

    private OrdersFetcherProperties ordersFetcherProperties;

    @BeforeEach
    void setUp() {
        ordersFetcherProperties = new OrdersFetcherProperties();
    }

    @Test
    void foodKeywords() {
        final List<FoodKeyword> list = Arrays.asList(FoodKeyword.values());
        ordersFetcherProperties.setFoodKeywords(list);
        Assertions.assertEquals(list, ordersFetcherProperties.getFoodKeywords());
    }

    @Test
    void longDistanceVehicles() {
        final List<Vehicle> list = Arrays.asList(Vehicle.values());
        ordersFetcherProperties.setLongDistanceVehicles(list);
        Assertions.assertEquals(list, ordersFetcherProperties.getLongDistanceVehicles());
    }

    @Test
    void longDistanceInKilometers() {
        final int longDistanceInKilometers = 5;
        ordersFetcherProperties.setLongDistanceInKilometers(longDistanceInKilometers);
        Assertions.assertEquals(longDistanceInKilometers, ordersFetcherProperties.getLongDistanceInKilometers());
    }

    @Test
    void distanceSlotInMeters() {
        final int distanceSlotInMeters = 500;
        ordersFetcherProperties.setDistanceSlotInMeters(distanceSlotInMeters);
        Assertions.assertEquals(distanceSlotInMeters, ordersFetcherProperties.getDistanceSlotInMeters());
    }

    @Test
    void ordersPriorities() {
        final List<OrdersPriority> list = Arrays.asList(OrdersPriority.values());
        ordersFetcherProperties.setOrdersPriorities(list);
        Assertions.assertEquals(list, ordersFetcherProperties.getOrdersPriorities());

    }

}