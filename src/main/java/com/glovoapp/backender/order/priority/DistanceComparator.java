package com.glovoapp.backender.order.priority;

import com.glovoapp.backender.distance.DistanceCalculator;
import com.glovoapp.backender.distance.Location;
import com.glovoapp.backender.order.model.Order;

import java.util.Comparator;

/**
 * @author Moath
 */
public class DistanceComparator implements Comparator<Order> {

    private int distanceSlotInMeters;
    private Location location;

    public DistanceComparator(int distanceSlotInMeters, Location location) {
        this.distanceSlotInMeters = distanceSlotInMeters;
        this.location = location;
    }

    @Override
    public int compare(Order order1, Order order2) {
        final double distBetweenOrder1AndLocationInMeters = DistanceCalculator.calculateDistanceMeters(order1.getPickup(), location);
        final double distBetweenOrder2AndLocationInMeters = DistanceCalculator.calculateDistanceMeters(order2.getPickup(), location);

        if (distBetweenOrder1AndLocationInMeters <= distanceSlotInMeters && distBetweenOrder2AndLocationInMeters <= distanceSlotInMeters) {
            return 0;
        }

        if (distBetweenOrder2AndLocationInMeters <= distanceSlotInMeters) {
            return 1;
        }

        return -1;
    }



}