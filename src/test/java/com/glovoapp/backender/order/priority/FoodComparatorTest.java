package com.glovoapp.backender.order.priority;

import com.glovoapp.backender.order.model.Order;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

class FoodComparatorTest {

    private FoodComparator foodComparator;

    @BeforeEach
    void setUp() {
        foodComparator = new FoodComparator();
    }

    @Test
    void sortOrders() {
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
    void sortOrdersWithFoodNullValue() {
        final List<Order> ordersOriginal = Arrays.asList(
                new Order(),
                new Order()
        );

        Assertions.assertThrows(IllegalArgumentException.class, () -> ordersOriginal.sort(foodComparator));
    }

    @Test
    void sortOrdersWithFoodNullValues() {
        final List<Order> ordersOriginal = Arrays.asList(
                new Order(),
                new Order().withFood(true)
        );

        Assertions.assertThrows(IllegalArgumentException.class, () -> ordersOriginal.sort(foodComparator));
    }

    @Test
    void sortOrdersAllFoodDoesNotChangeOrder() {
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

    @Test
    void sortOrdersWithNullValue() {
        final List<Order> ordersOriginal = Arrays.asList(
                null,
                new Order()
        );

        Assertions.assertThrows(IllegalArgumentException.class, () -> ordersOriginal.sort(foodComparator));
    }

    @Test
    void sortOrdersWithNullValues() {
        final List<Order> ordersOriginal = Arrays.asList(
                null,
                null
        );

        Assertions.assertThrows(IllegalArgumentException.class, () -> ordersOriginal.sort(foodComparator));
    }


}