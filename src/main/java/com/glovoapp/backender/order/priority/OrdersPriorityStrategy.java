package com.glovoapp.backender.order.priority;

import com.glovoapp.backender.distance.Location;
import com.glovoapp.backender.order.model.Order;

import java.util.*;

/**
 * This is used to return a list of comparators (priority strategy) based on the passed list.
 * It also supports a distance variable and a location in case of a distance comparator.
 *
 * @author Moath
 */
public class OrdersPriorityStrategy {

    private final int distanceSlotInMeters;
    private final Map<OrdersPriority, Comparator<Order>> comparatorsMap;

    public OrdersPriorityStrategy(List<OrdersPriority> ordersPriorities, int distanceSlotInMeters) {
        this.distanceSlotInMeters = distanceSlotInMeters;
        comparatorsMap = new LinkedHashMap<>(ordersPriorities.size());

        ordersPriorities.forEach(ordersPriority -> {
            switch (ordersPriority) {
                case FOOD:
                    comparatorsMap.put(ordersPriority, new FoodComparator());
                    break;

                case VIP:
                    comparatorsMap.put(ordersPriority, new VIPComparator());
                    break;

                case DISTANCE:
                    comparatorsMap.put(ordersPriority, null);
                    break;
            }
        });
    }

    public List<Comparator<Order>> getComparators() {
        return new ArrayList<>(comparatorsMap.values());
    }

    public void setLocation(Location location) {
        comparatorsMap.put(OrdersPriority.DISTANCE, new DistanceComparator(distanceSlotInMeters, location));
    }

}
