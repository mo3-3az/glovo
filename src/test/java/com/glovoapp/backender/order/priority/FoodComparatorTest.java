package com.glovoapp.backender.order.priority;

import com.glovoapp.backender.order.model.Order;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

class FoodComparatorTest {

    @Test
    void sortOrders() {
        FoodComparator foodComparator = new FoodComparator();
        final List<Order> ordersOriginal = Arrays.asList(
                new Order().withFood(false),
                new Order().withFood(true)
        );

        final List<Order> ordersSorted = Arrays.asList(
                new Order().withFood(true),
                new Order().withFood(false)
        );

        ordersOriginal.sort(foodComparator);
        Assertions.assertEquals(ordersSorted, ordersOriginal);
    }

    @Test
    void sortOrdersAllFoodDoesNotChangeOrder() {
        FoodComparator foodComparator = new FoodComparator();
        final List<Order> ordersOriginal = Arrays.asList(
                new Order().withFood(true).withId("1"),
                new Order().withFood(true).withId("2"),
                new Order().withFood(true).withId("3"),
                new Order().withFood(true).withId("4"),
                new Order().withFood(true).withId("5")
        );

        final List<Order> ordersSorted = Arrays.asList(
                new Order().withFood(true).withId("1"),
                new Order().withFood(true).withId("2"),
                new Order().withFood(true).withId("3"),
                new Order().withFood(true).withId("4"),
                new Order().withFood(true).withId("5")
        );

        ordersOriginal.sort(foodComparator);
        Assertions.assertEquals(ordersSorted, ordersOriginal);
    }

}