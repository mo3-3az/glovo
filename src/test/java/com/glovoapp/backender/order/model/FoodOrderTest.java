package com.glovoapp.backender.order.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Collections;

class FoodOrderTest {

    @Test
    void isFoodOrder() {
        Assertions.assertTrue(FoodKeyword.isFoodOrder("1x Pizza", Collections.singletonList(FoodKeyword.PIZZA)), "A pizza order is a food order");
    }

    @Test
    void isFoodOrderCaseInsensitive() {
        Assertions.assertTrue(FoodKeyword.isFoodOrder("1x PiZZa", Collections.singletonList(FoodKeyword.PIZZA)), "A piZZa order is a food order");
    }

    @Test
    void isNotFoodOrder() {
        Assertions.assertFalse(FoodKeyword.isFoodOrder("1x CAKE", Collections.singletonList(FoodKeyword.PIZZA)), "A CAKE order is not a food order");
    }
}