package com.glovoapp.backender.order;

import com.glovoapp.backender.courier.Courier;
import com.glovoapp.backender.courier.Vehicle;
import com.glovoapp.backender.distance.Location;
import com.glovoapp.backender.order.config.OrdersFetcherProperties;
import com.glovoapp.backender.order.model.FoodKeyword;
import com.glovoapp.backender.order.model.Order;
import com.glovoapp.backender.order.priority.OrdersPriority;
import com.glovoapp.backender.order.service.OrderRepository;
import com.glovoapp.backender.order.service.OrdersFetcherService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;


@ExtendWith(MockitoExtension.class)
class OrdersFetcherServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private OrdersFetcherProperties ordersFetcherProperties;

    private OrdersFetcherService ordersFetcherService;

    private Courier courier;

    @BeforeEach
    void setUp() {
        courier = new Courier();

        Mockito.when(ordersFetcherProperties.getDistanceSlotInMeters()).thenReturn(500);
        Mockito.when(ordersFetcherProperties.getFoodKeywords()).thenReturn(Arrays.asList(FoodKeyword.values()));
        Mockito.when(ordersFetcherProperties.getOrdersPriorities()).thenReturn(Arrays.asList(OrdersPriority.values()));
        Mockito.when(ordersFetcherProperties.getLongDistanceVehicles()).thenReturn(Arrays.asList(Vehicle.MOTORCYCLE, Vehicle.ELECTRIC_SCOOTER));

        ordersFetcherService = new OrdersFetcherService(ordersFetcherProperties, orderRepository);
    }

    @Test
    void fetchOrdersForCourier_CloseToOrder_WithABox_AndShortDistanceVehicle() {
        final Location courierLocation = new Location(41.39998523694263, 2.172663432928287);
        final Location orderLocation = new Location(41.39998523695000, 2.172663432928287);
        courier
                .withBox(true)
                .withVehicle(Vehicle.BICYCLE)
                .withLocation(courierLocation);

        final List<Order> mockList = Arrays.asList(
                new Order().withPickup(orderLocation).withFood(true).withVip(false).withDescription("Pizza"),
                new Order().withPickup(orderLocation).withFood(false).withVip(false).withDescription("Not food"),
                new Order().withPickup(orderLocation).withFood(false).withVip(true).withDescription("Not food")
        );
        Mockito.when(orderRepository.findAll()).thenReturn(mockList);
        ordersFetcherService = new OrdersFetcherService(ordersFetcherProperties, orderRepository);

        Mockito.when(ordersFetcherProperties.getLongDistanceInKilometers()).thenReturn(5);
        final List<Order> orders = ordersFetcherService.fetchOrders(courier);
        final List<Order> expectedList = Arrays.asList(
                new Order().withPickup(orderLocation).withFood(false).withVip(true).withDescription("Not food"),
                new Order().withPickup(orderLocation).withFood(true).withVip(false).withDescription("Pizza"),
                new Order().withPickup(orderLocation).withFood(false).withVip(false).withDescription("Not food")
        );
        Assertions.assertEquals(expectedList, orders);
    }


    @Test
    void fetchOrdersForCourier_FarFromOrder_WithABox_AndShortDistanceVehicle() {
        final Location courierLocation = new Location(41.39998523694263, 2.172663432928287);
        final Location orderLocation = new Location(51.39998523694263, 2.172663432928287);
        courier
                .withBox(true)
                .withVehicle(Vehicle.BICYCLE)
                .withLocation(courierLocation);

        final List<Order> mockList = Arrays.asList(
                new Order().withPickup(orderLocation).withFood(true).withVip(false).withDescription("Pizza"),
                new Order().withPickup(orderLocation).withFood(false).withVip(false).withDescription("Not food"),
                new Order().withPickup(orderLocation).withFood(false).withVip(true).withDescription("Not food")
        );
        Mockito.when(ordersFetcherProperties.getLongDistanceInKilometers()).thenReturn(5);
        Mockito.when(orderRepository.findAll()).thenReturn(mockList);
        ordersFetcherService = new OrdersFetcherService(ordersFetcherProperties, orderRepository);


        final List<Order> orders = ordersFetcherService.fetchOrders(courier);
        final List<Order> expectedList = Collections.emptyList();
        Assertions.assertEquals(expectedList, orders);
    }

    @Test
    void fetchOrdersForCourier_FarFromOrder_WithABox_AndLongDistanceVehicle() {
        final Location courierLocation = new Location(41.39998523694263, 2.172663432928287);
        final Location orderLocation = new Location(51.39998523694263, 2.172663432928287);
        courier
                .withBox(true)
                .withVehicle(Vehicle.MOTORCYCLE)
                .withLocation(courierLocation);

        final List<Order> mockList = Arrays.asList(
                new Order().withPickup(orderLocation).withFood(true).withVip(false).withDescription("1.Pizza"),
                new Order().withPickup(orderLocation).withFood(false).withVip(false).withDescription("2.Not food"),
                new Order().withPickup(orderLocation).withFood(false).withVip(true).withDescription("3.Not food")
        );
        Mockito.when(orderRepository.findAll()).thenReturn(mockList);
        ordersFetcherService = new OrdersFetcherService(ordersFetcherProperties, orderRepository);

        final List<Order> orders = ordersFetcherService.fetchOrders(courier);
        final List<Order> expectedList = Arrays.asList(
                new Order().withPickup(orderLocation).withFood(false).withVip(true).withDescription("3.Not food"),
                new Order().withPickup(orderLocation).withFood(true).withVip(false).withDescription("1.Pizza"),
                new Order().withPickup(orderLocation).withFood(false).withVip(false).withDescription("2.Not food")
        );
        Assertions.assertEquals(expectedList, orders);
    }

    @Test
    void fetchOrdersForCourier_CloseToOrder_WithoutABox_AndShortDistanceVehicle() {
        final Location courierLocation = new Location(41.39998523694263, 2.172663432928287);
        final Location orderLocation = new Location(41.39998523694263, 2.172663432928287);
        courier
                .withBox(false)
                .withVehicle(Vehicle.BICYCLE)
                .withLocation(courierLocation);

        final List<Order> mockList = Arrays.asList(
                new Order().withPickup(orderLocation).withFood(true).withVip(false).withDescription("1.Pizza"),
                new Order().withPickup(orderLocation).withFood(false).withVip(false).withDescription("2.Not food"),
                new Order().withPickup(orderLocation).withFood(false).withVip(true).withDescription("3.Not food")
        );
        Mockito.when(orderRepository.findAll()).thenReturn(mockList);
        ordersFetcherService = new OrdersFetcherService(ordersFetcherProperties, orderRepository);

        final List<Order> orders = ordersFetcherService.fetchOrders(courier);
        final List<Order> expectedList = Arrays.asList(
                new Order().withPickup(orderLocation).withFood(false).withVip(true).withDescription("3.Not food"),
                new Order().withPickup(orderLocation).withFood(false).withVip(false).withDescription("2.Not food")
        );
        Assertions.assertEquals(expectedList, orders);
    }

    @Test
    void fetchOrdersForCourier_FarFromOrder_WithoutABox_AndShortDistanceVehicle() {
        final Location courierLocation = new Location(41.39998523694263, 2.172663432928287);
        final Location orderLocation = new Location(51.39998523694263, 2.172663432928287);
        courier
                .withBox(false)
                .withVehicle(Vehicle.BICYCLE)
                .withLocation(courierLocation);

        final List<Order> mockList = Arrays.asList(
                new Order().withPickup(orderLocation).withFood(true).withVip(false).withDescription("1.Pizza"),
                new Order().withPickup(orderLocation).withFood(false).withVip(false).withDescription("2.Not food"),
                new Order().withPickup(orderLocation).withFood(false).withVip(true).withDescription("3.Not food")
        );
        Mockito.when(ordersFetcherProperties.getLongDistanceInKilometers()).thenReturn(5);
        Mockito.when(orderRepository.findAll()).thenReturn(mockList);
        ordersFetcherService = new OrdersFetcherService(ordersFetcherProperties, orderRepository);

        final List<Order> orders = ordersFetcherService.fetchOrders(courier);
        final List<Order> expectedList = Collections.emptyList();
        Assertions.assertEquals(expectedList, orders);
    }

    @Test
    void fetchOrdersForCourier_FarFromOrder_WithoutABox_AndLongDistanceVehicle() {
        final Location courierLocation = new Location(41.39998523694263, 2.172663432928287);
        final Location orderLocation = new Location(51.39998523694263, 2.172663432928287);
        courier
                .withBox(false)
                .withVehicle(Vehicle.MOTORCYCLE)
                .withLocation(courierLocation);

        final List<Order> mockList = Arrays.asList(
                new Order().withPickup(orderLocation).withFood(true).withVip(false).withDescription("1.Pizza"),
                new Order().withPickup(orderLocation).withFood(false).withVip(false).withDescription("2.Not food"),
                new Order().withPickup(orderLocation).withFood(false).withVip(true).withDescription("3.Not food")
        );
        Mockito.when(orderRepository.findAll()).thenReturn(mockList);
        ordersFetcherService = new OrdersFetcherService(ordersFetcherProperties, orderRepository);

        final List<Order> orders = ordersFetcherService.fetchOrders(courier);
        final List<Order> expectedList = Arrays.asList(
                new Order().withPickup(orderLocation).withFood(false).withVip(true).withDescription("3.Not food"),
                new Order().withPickup(orderLocation).withFood(false).withVip(false).withDescription("2.Not food")
        );
        Assertions.assertEquals(expectedList, orders);
    }

}