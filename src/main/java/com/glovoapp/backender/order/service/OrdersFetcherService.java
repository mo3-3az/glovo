package com.glovoapp.backender.order.service;

import com.glovoapp.backender.courier.Courier;
import com.glovoapp.backender.distance.DistanceCalculator;
import com.glovoapp.backender.distance.Location;
import com.glovoapp.backender.order.config.OrdersFetcherProperties;
import com.glovoapp.backender.order.model.FoodOrder;
import com.glovoapp.backender.order.model.Order;
import com.glovoapp.backender.order.priority.OrderComparator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Moath
 */
@Service
public class OrdersFetcherService implements OrdersFetcher {

    private final OrdersFetcherProperties properties;
    private final OrderRepository orderRepository;
    private List<Order> foodOrders;
    private List<Order> noneFoodOrders;

    @Autowired
    public OrdersFetcherService(OrdersFetcherProperties properties, OrderRepository orderRepository) {
        this.properties = properties;
        this.orderRepository = orderRepository;
        foodOrders = new ArrayList<>();
        noneFoodOrders = new ArrayList<>();

        initSets();
    }

    private void initSets() {
        orderRepository.findAll().forEach(order -> {
            if (FoodOrder.isFoodOrder(order.getDescription(), properties.getFoodOrders())) {
                foodOrders.add(order);
            } else {
                noneFoodOrders.add(order);
            }
        });
    }

    @Override
    public List<Order> fetchOrders(Courier courier) {
        List<Order> orders;
        if (courier.canDeliverFood()) {
            orders = foodOrders
                    .stream()
                    .filter(order -> canCourierPickUp(courier, order.getPickup()))
                    .collect(Collectors.toList());
        } else {
            orders = noneFoodOrders
                    .stream()
                    .filter(order -> canCourierPickUp(courier, order.getPickup()))
                    .collect(Collectors.toList());
        }

        //Sort based on distance slots
        orders.sort(new OrderComparator(properties.getOrdersPriorities(), properties.getDistanceSlotInMeters(), courier.getLocation()));
        return orders;
    }

    private boolean canCourierPickUp(Courier courier, Location pickupLocation) {
        if (DistanceCalculator.calculateDistanceKilometers(pickupLocation, courier.getLocation()) <= properties.getLongDistanceInKilometers()) {
            return true;
        }

        return properties.getLongDistanceVehicles().contains(courier.getVehicle());
    }

}
