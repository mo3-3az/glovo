package com.glovoapp.backender.order.service;

import com.glovoapp.backender.courier.Courier;
import com.glovoapp.backender.order.model.Order;

import java.util.List;

/**
 * @author Moath
 */
public interface OrdersFetcher {

    List<Order> fetchOrders(Courier courier);

}
