package com.glovoapp.backender.order;

import com.glovoapp.backender.courier.Courier;

import java.util.List;

/**
 * @author Moath
 */
public interface OrdersFetcher {

    List<Order> fetchOrders(Courier courier);

}
