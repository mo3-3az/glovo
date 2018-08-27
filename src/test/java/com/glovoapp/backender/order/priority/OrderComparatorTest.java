package com.glovoapp.backender.order.priority;

import com.glovoapp.backender.distance.Location;
import com.glovoapp.backender.order.model.Order;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

class OrderComparatorTest {

    private OrdersPriorityStrategy ordersPriorityStrategy;


    @Test
    void sortOrdersPrioritizedByDistanceVIPFood() {
        ordersPriorityStrategy = new OrdersPriorityStrategy(Arrays.asList(OrdersPriority.DISTANCE, OrdersPriority.VIP, OrdersPriority.FOOD), 1000);
        ordersPriorityStrategy.setLocation(Location.get(41.1, 1.0));
        OrdersComparator orderComparator = new OrdersComparator(ordersPriorityStrategy.getComparators());

        final List<Order> ordersOriginal = Arrays.asList(
                new Order().withPickup(Location.get(43.1, 1.0)).withVip(true).withFood(false),
                new Order().withPickup(Location.get(41.1, 1.0)).withVip(true).withFood(false),
                new Order().withPickup(Location.get(41.1, 1.0)).withVip(true).withFood(true),
                new Order().withPickup(Location.get(42.1, 1.0)).withVip(false).withFood(false)
        );

        final List<Order> ordersSorted = Arrays.asList(
                new Order().withPickup(Location.get(41.1, 1.0)).withVip(true).withFood(true),
                new Order().withPickup(Location.get(41.1, 1.0)).withVip(true).withFood(false),
                new Order().withPickup(Location.get(42.1, 1.0)).withVip(false).withFood(false),
                new Order().withPickup(Location.get(43.1, 1.0)).withVip(true).withFood(false)
        );

        ordersOriginal.sort(orderComparator);
        Assertions.assertEquals(ordersSorted, ordersOriginal);
    }

    @Test
    void sortOrdersPrioritizedByFoodDistanceVIP() {
        ordersPriorityStrategy = new OrdersPriorityStrategy(Arrays.asList(OrdersPriority.FOOD, OrdersPriority.DISTANCE, OrdersPriority.VIP), 1000);
        ordersPriorityStrategy.setLocation(Location.get(41.1, 1.0));
        OrdersComparator orderComparator = new OrdersComparator(ordersPriorityStrategy.getComparators());

        final List<Order> ordersOriginal = Arrays.asList(
                new Order().withPickup(Location.get(43.1, 1.0)).withVip(true).withFood(false),
                new Order().withPickup(Location.get(41.1, 1.0)).withVip(true).withFood(false),
                new Order().withPickup(Location.get(41.1, 1.0)).withVip(true).withFood(true),
                new Order().withPickup(Location.get(42.1, 1.0)).withVip(false).withFood(true)
        );

        final List<Order> ordersSorted = Arrays.asList(
                new Order().withPickup(Location.get(41.1, 1.0)).withVip(true).withFood(true),
                new Order().withPickup(Location.get(42.1, 1.0)).withVip(false).withFood(true),
                new Order().withPickup(Location.get(41.1, 1.0)).withVip(true).withFood(false),
                new Order().withPickup(Location.get(43.1, 1.0)).withVip(true).withFood(false)
        );

        ordersOriginal.sort(orderComparator);
        Assertions.assertEquals(ordersSorted, ordersOriginal);
    }

    @Test
    void sortOrdersPrioritizedByVIPFoodDistance() {
        ordersPriorityStrategy = new OrdersPriorityStrategy(Arrays.asList(OrdersPriority.VIP, OrdersPriority.FOOD, OrdersPriority.DISTANCE), 1000);
        ordersPriorityStrategy.setLocation(Location.get(41.1, 1.0));
        OrdersComparator orderComparator = new OrdersComparator(ordersPriorityStrategy.getComparators());

        final List<Order> ordersOriginal = Arrays.asList(
                new Order().withPickup(Location.get(41.1, 1.0)).withVip(true).withFood(false),
                new Order().withPickup(Location.get(43.1, 1.0)).withVip(true).withFood(false),
                new Order().withPickup(Location.get(41.1, 1.0)).withVip(false).withFood(false),
                new Order().withPickup(Location.get(42.1, 1.0)).withVip(false).withFood(false),
                new Order().withPickup(Location.get(41.1, 1.0)).withVip(true).withFood(true),
                new Order().withPickup(Location.get(42.1, 1.0)).withVip(false).withFood(true)
        );

        final List<Order> ordersSorted = Arrays.asList(
                new Order().withPickup(Location.get(41.1, 1.0)).withVip(true).withFood(true),
                new Order().withPickup(Location.get(41.1, 1.0)).withVip(true).withFood(false),
                new Order().withPickup(Location.get(43.1, 1.0)).withVip(true).withFood(false),
                new Order().withPickup(Location.get(42.1, 1.0)).withVip(false).withFood(true),
                new Order().withPickup(Location.get(41.1, 1.0)).withVip(false).withFood(false),
                new Order().withPickup(Location.get(42.1, 1.0)).withVip(false).withFood(false)
        );

        ordersOriginal.sort(orderComparator);
        Assertions.assertEquals(ordersSorted, ordersOriginal);
    }

}