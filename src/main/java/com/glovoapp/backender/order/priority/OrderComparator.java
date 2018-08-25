package com.glovoapp.backender.order.priority;

import com.glovoapp.backender.distance.Location;
import com.glovoapp.backender.order.model.Order;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * @author Moath
 */
public class OrderComparator implements Comparator<Order> {

    private final List<Comparator<Order>> comparators;

    public OrderComparator(List<OrdersPriority> ordersPriorities, int distanceSlotInMeters, Location location) {
        comparators = new ArrayList<>();
        ordersPriorities.forEach(ordersPriority -> {
            switch (ordersPriority) {
                case FOOD:
                    comparators.add(new FoodComparator());
                    break;

                case VIP:
                    comparators.add(new VIPComparator());
                    break;

//                case DISTANCE:
//                    comparators.add(new DistanceComparator(distanceSlotInMeters, location));
//                    break;
            }
        });
    }

    public int compare(Order o1, Order o2) {
        for (Comparator<Order> c : comparators) {
            int result = c.compare(o1, o2);
            if (result != 0) {
                return result;
            }
        }

        return 0;
    }

}