package com.glovoapp.backender.order.priority;

import com.glovoapp.backender.order.model.Order;

import java.util.Comparator;
import java.util.List;

/**
 * This comparator is used to aggregate multiple comparators, it will keep invoking the comparators until it's done or not a zero (equal).
 *
 * @author Moath
 */
public class OrdersComparator implements Comparator<Order> {

    private final List<Comparator<Order>> comparators;

    public OrdersComparator(List<Comparator<Order>> comparators) {
        this.comparators = comparators;
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