package com.glovoapp.backender.order.priority;

import com.glovoapp.backender.order.model.Order;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

class VIPComparatorTest {

    private VIPComparator vipComparator;

    @BeforeEach
    void setUp() {
        vipComparator = new VIPComparator();
    }

    @Test
    void sortOrders() {
        final List<Order> ordersOriginal = Arrays.asList(
                new Order().withVip(false),
                new Order().withVip(true)
        );

        final List<Order> ordersSorted = Arrays.asList(
                new Order().withVip(true),
                new Order().withVip(false)
        );

        ordersOriginal.sort(vipComparator);
        Assertions.assertEquals(ordersSorted, ordersOriginal);
    }

    @Test
    void sortOrdersWithNullVipValues() {
        final List<Order> ordersOriginal = Arrays.asList(
                new Order(),
                new Order().withVip(true)
        );

        Assertions.assertThrows(IllegalArgumentException.class, () -> ordersOriginal.sort(vipComparator));
    }

    @Test
    void sortOrdersWithNullValue() {
        final List<Order> ordersOriginal = Arrays.asList(
                null,
                new Order()
        );

        Assertions.assertThrows(IllegalArgumentException.class, () -> ordersOriginal.sort(vipComparator));
    }

    @Test
    void sortOrdersWithNullValues() {
        final List<Order> ordersOriginal = Arrays.asList(
                null,
                null
        );

        Assertions.assertThrows(IllegalArgumentException.class, () -> ordersOriginal.sort(vipComparator));
    }

    @Test
    void sortOrdersAllVIPDoesNotChangeOrder() {
        final List<Order> ordersOriginal = Arrays.asList(
                new Order().withVip(true),
                new Order().withVip(true),
                new Order().withVip(true),
                new Order().withVip(true),
                new Order().withVip(true)
        );

        final List<Order> ordersSorted = Arrays.asList(
                new Order().withVip(true),
                new Order().withVip(true),
                new Order().withVip(true),
                new Order().withVip(true),
                new Order().withVip(true)
        );

        ordersOriginal.sort(vipComparator);
        Assertions.assertEquals(ordersSorted, ordersOriginal);
    }

}