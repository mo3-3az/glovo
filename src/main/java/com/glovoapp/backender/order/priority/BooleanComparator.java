package com.glovoapp.backender.order.priority;

import com.glovoapp.backender.order.model.Order;

import java.util.Comparator;

/**
 * This comparator favors a true boolean over a false boolean.
 *
 * @author Moath
 */
abstract class BooleanComparator implements Comparator<Order> {

    @Override
    public abstract int compare(Order order1, Order order2);

    int compareBooleans(Boolean boolean1, Boolean boolean2) {
        if (boolean1 == null) {
            throw new IllegalArgumentException("Boolean1 is null!");
        }

        if (boolean2 == null) {
            throw new IllegalArgumentException("Boolean2 is null!");
        }

        if (boolean1 == boolean2) {
            return 0;
        } else if (boolean2) {
            return 1;
        } else {
            return -1;
        }
    }

}