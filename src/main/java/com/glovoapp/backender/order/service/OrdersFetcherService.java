package com.glovoapp.backender.order.service;

import com.glovoapp.backender.courier.Courier;
import com.glovoapp.backender.distance.DistanceCalculator;
import com.glovoapp.backender.distance.Location;
import com.glovoapp.backender.order.config.OrdersFetcherProperties;
import com.glovoapp.backender.order.model.FoodKeyword;
import com.glovoapp.backender.order.model.Order;
import com.glovoapp.backender.order.priority.OrdersComparator;
import com.glovoapp.backender.order.priority.OrdersPriorityStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * This is the implementation of the API. The idea here is to divide orders to food and none food orders based on the
 * order's description according to the configurations.
 * <p>
 * At every fetch request it will filter furthermore based on the courier's info according to the configurations.
 *
 * @author Moath
 */
@Service
public class OrdersFetcherService implements OrdersFetcher {

    private final OrdersFetcherProperties properties;
    private final OrderRepository orderRepository;
    private final OrdersPriorityStrategy ordersPriorityStrategy;
    private List<Order> foodOrders;
    private List<Order> noneFoodOrders;

    @Autowired
    public OrdersFetcherService(OrdersFetcherProperties properties, OrderRepository orderRepository) {
        this.properties = properties;
        this.orderRepository = orderRepository;

        ordersPriorityStrategy = new OrdersPriorityStrategy();
        ordersPriorityStrategy.setDistanceSlotInMeters(properties.getDistanceSlotInMeters());
        ordersPriorityStrategy.setOrdersPriorities(properties.getOrdersPriorities());

        initialize();
    }

    private void initialize() {
        foodOrders = new ArrayList<>();
        noneFoodOrders = new ArrayList<>();

        final List<FoodKeyword> foodKeywords = properties.getFoodKeywords();
        orderRepository.findAll().forEach(order -> {
            if (FoodKeyword.isFoodOrder(order.getDescription(), foodKeywords)) {
                this.foodOrders.add(order);
            } else {
                noneFoodOrders.add(order);
            }
        });
    }

    @Override
    public List<Order> fetchOrders(Courier courier) {
        List<Order> orders = new ArrayList<>();

        noneFoodOrders
                .stream()
                .filter(order -> canCourierPickUp(courier, order.getPickup()))
                .forEach(orders::add);

        if (courier.canDeliverFood()) {
            foodOrders
                    .stream()
                    .filter(order -> canCourierPickUp(courier, order.getPickup()))
                    .forEach(orders::add);
        }

        ordersPriorityStrategy.setLocation(courier.getLocation());
        orders.sort(new OrdersComparator(ordersPriorityStrategy.getComparators()));

        return orders;
    }

    private boolean canCourierPickUp(Courier courier, Location pickupLocation) {
        //Does the courier has a vehicle that can go far.
        if (properties.getLongDistanceVehicles().contains(courier.getVehicle())) {
            return true;
        }

        //Is the courier close to the order.
        return DistanceCalculator.calculateDistanceKilometers(pickupLocation, courier.getLocation()) <= properties.getLongDistanceInKilometers();
    }

}
