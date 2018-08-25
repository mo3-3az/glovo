package com.glovoapp.backender.order;

import com.glovoapp.backender.courier.Courier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * @author Moath
 */
@Service
public class OrdersFetcherService implements OrdersFetcher {

    private final OrdersFetcherProperties properties;

    @Autowired
    public OrdersFetcherService(OrdersFetcherProperties properties) {
        this.properties = properties;
    }

    @Override
    public List<Order> fetchOrders(OrderRepository orderRepository, Courier courier) {
        return Collections.emptyList();
    }
}
