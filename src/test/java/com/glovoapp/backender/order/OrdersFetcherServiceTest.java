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

    private static final double LAT = 41.39998523694263;
    private static final double LON = 2.172663432928287;
    private static final double LAT_CLOSE = 41.39998523695000;
    private static final double LAT_FAR = 51.39998523694263;
    private static final String ORDER_DESC_NOT_FOOD = "Not food";
    private static final String ORDER_DESC_PIZZA = "Pizza";
    public static final int LONG_DISTANCE_IN_KILOMETERS = 5;

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
    }

    @Test
    void fetchOrdersForCourier_CloseToOrder_WithABox_AndShortDistanceVehicle() {
        final Location courierLocation = new Location(LAT, LON);
        final Location orderLocation = new Location(LAT_CLOSE, LON);
        courier
                .withBox(true)
                .withVehicle(Vehicle.BICYCLE)
                .withLocation(courierLocation);

        final List<Order> mockList = getMockOrdersList(orderLocation);
        Mockito.when(orderRepository.findAll()).thenReturn(mockList);
        ordersFetcherService = new OrdersFetcherService(ordersFetcherProperties, orderRepository);

        Mockito.when(ordersFetcherProperties.getLongDistanceInKilometers()).thenReturn(LONG_DISTANCE_IN_KILOMETERS);
        final List<Order> orders = ordersFetcherService.fetchOrders(courier);
        final List<Order> expectedList = Arrays.asList(
                new Order().withPickup(orderLocation).withFood(false).withVip(true).withDescription(ORDER_DESC_NOT_FOOD),
                new Order().withPickup(orderLocation).withFood(true).withVip(false).withDescription(ORDER_DESC_PIZZA),
                new Order().withPickup(orderLocation).withFood(false).withVip(false).withDescription(ORDER_DESC_NOT_FOOD)
        );
        Assertions.assertEquals(expectedList, orders);
    }

    @Test
    void fetchOrdersForCourier_FarFromOrder_WithABox_AndShortDistanceVehicle() {
        final Location courierLocation = new Location(LAT, LON);
        final Location orderLocation = new Location(LAT_FAR, LON);
        courier
                .withBox(true)
                .withVehicle(Vehicle.BICYCLE)
                .withLocation(courierLocation);

        final List<Order> mockList = getMockOrdersList(orderLocation);
        Mockito.when(ordersFetcherProperties.getLongDistanceInKilometers()).thenReturn(LONG_DISTANCE_IN_KILOMETERS);
        Mockito.when(orderRepository.findAll()).thenReturn(mockList);
        ordersFetcherService = new OrdersFetcherService(ordersFetcherProperties, orderRepository);


        final List<Order> orders = ordersFetcherService.fetchOrders(courier);
        final List<Order> expectedList = Collections.emptyList();
        Assertions.assertEquals(expectedList, orders);
    }

    @Test
    void fetchOrdersForCourier_FarFromOrder_WithABox_AndLongDistanceVehicle() {
        final Location courierLocation = new Location(LAT, LON);
        final Location orderLocation = new Location(LAT_FAR, LON);
        courier
                .withBox(true)
                .withVehicle(Vehicle.MOTORCYCLE)
                .withLocation(courierLocation);

        final List<Order> mockList = getMockOrdersList(orderLocation);
        Mockito.when(orderRepository.findAll()).thenReturn(mockList);
        ordersFetcherService = new OrdersFetcherService(ordersFetcherProperties, orderRepository);

        final List<Order> orders = ordersFetcherService.fetchOrders(courier);
        final List<Order> expectedList = Arrays.asList(
                new Order().withPickup(orderLocation).withFood(false).withVip(true).withDescription(ORDER_DESC_NOT_FOOD),
                new Order().withPickup(orderLocation).withFood(true).withVip(false).withDescription(ORDER_DESC_PIZZA),
                new Order().withPickup(orderLocation).withFood(false).withVip(false).withDescription(ORDER_DESC_NOT_FOOD)
        );
        Assertions.assertEquals(expectedList, orders);
    }

    @Test
    void fetchOrdersForCourier_CloseToOrder_WithoutABox_AndShortDistanceVehicle() {
        final Location courierLocation = new Location(LAT, LON);
        final Location orderLocation = new Location(LAT, LON);
        courier
                .withBox(false)
                .withVehicle(Vehicle.BICYCLE)
                .withLocation(courierLocation);

        final List<Order> mockList = getMockOrdersList(orderLocation);
        Mockito.when(orderRepository.findAll()).thenReturn(mockList);
        ordersFetcherService = new OrdersFetcherService(ordersFetcherProperties, orderRepository);

        final List<Order> orders = ordersFetcherService.fetchOrders(courier);
        final List<Order> expectedList = Arrays.asList(
                new Order().withPickup(orderLocation).withFood(false).withVip(true).withDescription(ORDER_DESC_NOT_FOOD),
                new Order().withPickup(orderLocation).withFood(false).withVip(false).withDescription(ORDER_DESC_NOT_FOOD)
        );
        Assertions.assertEquals(expectedList, orders);
    }

    @Test
    void fetchOrdersForCourier_FarFromOrder_WithoutABox_AndShortDistanceVehicle() {
        final Location courierLocation = new Location(LAT, LON);
        final Location orderLocation = new Location(LAT_FAR, LON);
        courier
                .withBox(false)
                .withVehicle(Vehicle.BICYCLE)
                .withLocation(courierLocation);

        final List<Order> mockList = getMockOrdersList(orderLocation);
        Mockito.when(ordersFetcherProperties.getLongDistanceInKilometers()).thenReturn(LONG_DISTANCE_IN_KILOMETERS);
        Mockito.when(orderRepository.findAll()).thenReturn(mockList);
        ordersFetcherService = new OrdersFetcherService(ordersFetcherProperties, orderRepository);

        final List<Order> orders = ordersFetcherService.fetchOrders(courier);
        final List<Order> expectedList = Collections.emptyList();
        Assertions.assertEquals(expectedList, orders);
    }

    @Test
    void fetchOrdersForCourier_FarFromOrder_WithoutABox_AndLongDistanceVehicle() {
        final Location courierLocation = new Location(LAT, LON);
        final Location orderLocation = new Location(LAT_FAR, LON);
        courier
                .withBox(false)
                .withVehicle(Vehicle.MOTORCYCLE)
                .withLocation(courierLocation);

        Mockito.when(orderRepository.findAll()).thenReturn(getMockOrdersList(orderLocation));
        ordersFetcherService = new OrdersFetcherService(ordersFetcherProperties, orderRepository);

        final List<Order> orders = ordersFetcherService.fetchOrders(courier);
        final List<Order> expectedList = Arrays.asList(
                new Order().withPickup(orderLocation).withFood(false).withVip(true).withDescription(ORDER_DESC_NOT_FOOD),
                new Order().withPickup(orderLocation).withFood(false).withVip(false).withDescription(ORDER_DESC_NOT_FOOD)
        );
        Assertions.assertEquals(expectedList, orders);
    }

    private List<Order> getMockOrdersList(Location orderLocation) {
        return Arrays.asList(
                new Order().withPickup(orderLocation).withFood(true).withVip(false).withDescription(ORDER_DESC_PIZZA),
                new Order().withPickup(orderLocation).withFood(false).withVip(false).withDescription(ORDER_DESC_NOT_FOOD),
                new Order().withPickup(orderLocation).withFood(false).withVip(true).withDescription(ORDER_DESC_NOT_FOOD)
        );
    }
}