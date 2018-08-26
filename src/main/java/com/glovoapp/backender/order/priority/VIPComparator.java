package com.glovoapp.backender.order.priority;

import com.glovoapp.backender.order.model.Order;

/**
 * This will compare two orders together, the order which has VIP as true will be before the one that doesn't.
 *
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