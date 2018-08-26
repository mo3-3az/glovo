package com.glovoapp.backender.order.priority;

/**
 * This defines strategies to priorities orders.
 *
 * @author Moath
 * @see "Property: orders-fetcher.ordersPriorities"
 */
public enum OrdersPriority {
    DISTANCE,
    VIP,
    FOOD
}