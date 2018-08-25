package com.glovoapp.backender.order.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Collections;

class FoodOrderTest {

    @Test
    void isFoodOrder() {
        Assertions.assertTrue(FoodOrder.isFoodOrder("1x Pizza", Collections.singletonList(FoodOrder.PIZZA)), "A pizza order is a food order");
    }

    @Test
    void isFoodOrderCaseInsensitive() {
        Assertions.assertTrue(FoodOrder.isFoodOrder("1x PiZZa", Collections.singletonList(FoodOrder.PIZZA)), "A piZZa order is a food order");
    }

    @Test
    void isNotFoodOrder() {
        Assertions.assertFalse(FoodOrder.isFoodOrder("1x CAKE", Collections.singletonList(FoodOrder.PIZZA)), "A CAKE order is not a food order");
    }
}