package com.glovoapp.backender.order.priority;

import com.glovoapp.backender.distance.Location;
import com.glovoapp.backender.order.model.Order;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * This is used to return a list of comparators (priority strategy) based on the passed list.
 * It also supports a distance variable and a location in case of a distance comparator.
 *
 * @author Moath
 */
public class OrdersPriorityStrategy {

    private int distanceSlotInMeters;
    private Location location;
    private List<OrdersPriority> ordersPriorities;

    public List<Comparator<Order>> getComparators() {
        List<Comparator<Order>> comparators = new ArrayList<>();
        ordersPriorities.forEach(ordersPriority -> {
            switch (ordersPriority) {
                case FOOD:
                    comparators.add(new FoodComparator());
                    break;

                case VIP:
                    comparators.add(new VIPComparator());
                    break;

                case DISTANCE:
                    comparators.add(new DistanceComparator(distanceSlotInMeters, location));
                    break;
            }
        });

        return comparators;
    }

    public void setOrdersPriorities(List<OrdersPriority> ordersPriorities) {
        this.ordersPriorities = ordersPriorities;
    }

    public void setDistanceSlotInMeters(int distanceSlotInMeters) {
        this.distanceSlotInMeters = distanceSlotInMeters;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}
