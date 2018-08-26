package com.glovoapp.backender.order.service;

import com.glovoapp.backender.courier.Courier;
import com.glovoapp.backender.order.model.Order;

import java.util.List;

/**
 * This is the interface that will define the API to fetch orders base on couriers.
 *
 * @author Moath
 */
public interface OrdersFetcher {

    /**
     * This should return a list of orders that a courier can deliver based on the couriers info and the configurations.
     */
    List<Order> fetchOrders(Courier courier);

}
