package com.glovoapp.backender.order.priority;

import com.glovoapp.backender.order.model.Order;

/**
 * @author Moath
 */
public class VIPComparator extends BooleanComparator {

    @Override
    public int compare(Order order1, Order order2) {
        return compareBooleans(order1.getVip(), order2.getVip());
    }

}