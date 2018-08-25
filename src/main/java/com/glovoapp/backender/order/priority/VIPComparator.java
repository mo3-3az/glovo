package com.glovoapp.backender.order.priority;

import com.glovoapp.backender.order.model.Order;

/**
 * @author Moath
 */
public class VIPComparator extends BooleanComparator {

    @Override
    public int compare(Order order1, Order order2) {
        if (order1 == null) {
            throw new IllegalArgumentException("order1 is null!");
        }

        if (order2 == null) {
            throw new IllegalArgumentException("order2 is null!");
        }

        return compareBooleans(order1.getVip(), order2.getVip());
    }

}