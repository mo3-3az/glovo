package com.glovoapp.backender.order.config;

import com.glovoapp.backender.courier.Vehicle;
import com.glovoapp.backender.order.model.FoodKeyword;
import com.glovoapp.backender.order.priority.OrdersPriority;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * Configuration properties that will be used to change the behavior of the OrdersFetcher.
 *
 * @author Moath
 * @see "application.properties"
 */
@Configuration
@ConfigurationProperties("orders-fetcher")
public class OrdersFetcherProperties {

    private List<FoodKeyword> foodKeywords;

    private List<Vehicle> longDistanceVehicles;

    private int longDistanceInKilometers;

    private int distanceSlotInMeters;

    private List<OrdersPriority> ordersPriorities;

    public void setFoodKeywords(List<FoodKeyword> foodKeywords) {
        this.foodKeywords = foodKeywords;
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

    public List<FoodKeyword> getFoodKeywords() {
        return foodKeywords;
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
