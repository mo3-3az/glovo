package com.glovoapp.backender.order;

import com.glovoapp.backender.courier.Vehicle;
import com.glovoapp.backender.distance.Distance;
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

    private long longDistance;

    private Distance longDistanceUnit;

    private long distanceSlot;

    private Distance distanceSlotUnit;

    List<OrdersPriority> ordersPriorities;

    public List<FoodOrder> getFoodOrders() {
        return foodOrders;
    }

    public void setFoodOrders(List<FoodOrder> foodOrders) {
        this.foodOrders = foodOrders;
    }

    public List<Vehicle> getLongDistanceVehicles() {
        return longDistanceVehicles;
    }

    public void setLongDistanceVehicles(List<Vehicle> longDistanceVehicles) {
        this.longDistanceVehicles = longDistanceVehicles;
    }

    public long getLongDistance() {
        return longDistance;
    }

    public void setLongDistance(long longDistance) {
        this.longDistance = longDistance;
    }

    public Distance getLongDistanceUnit() {
        return longDistanceUnit;
    }

    public void setLongDistanceUnit(Distance longDistanceUnit) {
        this.longDistanceUnit = longDistanceUnit;
    }

    public long getDistanceSlot() {
        return distanceSlot;
    }

    public void setDistanceSlot(long distanceSlot) {
        this.distanceSlot = distanceSlot;
    }

    public Distance getDistanceSlotUnit() {
        return distanceSlotUnit;
    }

    public void setDistanceSlotUnit(Distance distanceSlotUnit) {
        this.distanceSlotUnit = distanceSlotUnit;
    }

    public List<OrdersPriority> getOrdersPriorities() {
        return ordersPriorities;
    }

    public void setOrdersPriorities(List<OrdersPriority> ordersPriorities) {
        this.ordersPriorities = ordersPriorities;
    }
}
