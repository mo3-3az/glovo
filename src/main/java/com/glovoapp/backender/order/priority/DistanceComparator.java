package com.glovoapp.backender.order.priority;

import com.glovoapp.backender.distance.DistanceCalculator;
import com.glovoapp.backender.distance.Location;
import com.glovoapp.backender.order.model.Order;

import java.util.Comparator;

/**
 * This will compare two orders together, the order which is located distanceSlotInMeters meters within a specific location
 * will be before the other.
 * <p>
 * If both are the same or within the slot they will be regarded as equals.
 *
 * @author Moath
 */
class DistanceComparator implements Comparator<Order> {

    private int distanceSlotInMeters;
    private Location location;

    DistanceComparator(int distanceSlotInMeters, Location location) {
        if (distanceSlotInMeters < 0) {
            throw new IllegalArgumentException("distanceSlotInMeters is negative!");
        }

        if (location == null) {
            throw new IllegalArgumentException("location is null!");
        }

        this.distanceSlotInMeters = distanceSlotInMeters;
        this.location = location;
    }

    @Override
    public int compare(Order order1, Order order2) {
        if (order1 == null || order1.getPickup() == null) {
            throw new IllegalArgumentException("order1 is null or the pick up location is null!");
        }

        if (order2 == null || order2.getPickup() == null) {
            throw new IllegalArgumentException("order2 is null or the pick up location is null!");
        }

        final double distBetweenOrder1AndLocationInMeters = DistanceCalculator.calculateDistanceMeters(order1.getPickup(), location);
        final double distBetweenOrder2AndLocationInMeters = DistanceCalculator.calculateDistanceMeters(order2.getPickup(), location);

        if (distBetweenOrder1AndLocationInMeters == distBetweenOrder2AndLocationInMeters) {
            return 0;
        }

        if (distBetweenOrder1AndLocationInMeters <= distanceSlotInMeters && distBetweenOrder2AndLocationInMeters <= distanceSlotInMeters) {
            return 0;
        }

        if (distBetweenOrder2AndLocationInMeters <= distanceSlotInMeters) {
            return 1;
        }

        return -1;
    }

}