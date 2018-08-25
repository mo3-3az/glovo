package com.glovoapp.backender.order.config;

import com.glovoapp.backender.courier.Vehicle;
import com.glovoapp.backender.order.model.FoodOrder;
import com.glovoapp.backender.order.priority.OrdersPriority;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * @author Moath
 */
@Configuration
@ConfigurationProperties("orders-fetcher")
public class OrdersFetcherProperties {

    private List<FoodOrder> foodOrders;

    private List<Vehicle> longDistanceVehicles;

    private int longDistanceInKilometers;

    private int distanceSlotInMeters;

    private List<OrdersPriority> ordersPriorities;

    public void setFoodOrders(List<FoodOrder> foodOrders) {
        this.foodOrders = foodOrders;
    }

    public void setLongDistanceVehicles(List<Vehicle> longDistanceVehicles) {
        this.longDistanceVehicles = longDistanceVehicles;
    }

    public void setLongDistanceInKilometers(int longDistanceInKilometers) {
        this.longDistanceInKilometers = longDistanceInKilometers;
    }

    public void setDistanceSlotInMeters(int distanceSlotInMeters) {
        this.distanceSlotInMeters = distanceSlotInMeters;
    }

    public void setOrdersPriorities(List<OrdersPriority> ordersPriorities) {
        this.ordersPriorities = ordersPriorities;
    }

    public List<FoodOrder> getFoodOrders() {
        return foodOrders;
    }

    public List<Vehicle> getLongDistanceVehicles() {
        return longDistanceVehicles;
    }

    public int getLongDistanceInKilometers() {
        return longDistanceInKilometers;
    }

    public int getDistanceSlotInMeters() {
        return distanceSlotInMeters;
    }

    public List<OrdersPriority> getOrdersPriorities() {
        return ordersPriorities;
    }
}
