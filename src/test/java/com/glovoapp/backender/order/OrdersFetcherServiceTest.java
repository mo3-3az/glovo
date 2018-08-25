package com.glovoapp.backender.order;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OrdersFetcherServiceTest {

    private OrdersFetcherService ordersFetcherService;

    @BeforeEach
    void setUp() {
        ordersFetcherService = new OrdersFetcherService(new OrdersFetcherProperties(), new OrderRepository());
    }

    //

    @Test
    void fetchOrdersForCourierWithBox() {
    }

    @Test
    void fetchOrdersForCourierWithNoBox() {
    }

    @Test
    void fetchFoodOrdersForCourierWithBox() {
    }

    //

    @Test
    void fetchOrdersForCourierWithBicycle() {
    }

    @Test
    void fetchOrdersForCourierWithMotorcycle() {
    }

    @Test
    void fetchOrdersForCourierWithElectricScooter() {
    }

    @Test
    void fetchOrdersForCourierWithNoVehicle() {
    }

    @Test
    void fetchOrdersForCourierWithNormalVehicle() {
    }

    @Test
    void fetchOrdersForCourierWithLongDistanceVehicle() {
    }

    //

    @Test
    void fetchOrdersForCourierWithinDistance() {
    }

    @Test
    void fetchOrdersForCourierOutsideDistance() {
    }

    //

    @Test
    void fetchOrdersPrioritizedByDistanceVipFood() {
    }

    @Test
    void fetchOrdersPrioritizedByFoodVipDistance() {
    }
}