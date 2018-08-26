package com.glovoapp.backender.order.model;

import java.util.List;

/**
 * @author Moath
 */
public enum FoodOrder {
    PIZZA,
    CAKE,
    KEBAB,
    FLAMINGO;

    public static boolean isFoodOrder(String orderDescription, List<FoodOrder> foodOrders) {
        for (FoodOrder foodOrder : foodOrders) {
            if (orderDescription.toUpperCase().contains(foodOrder.name())) {
                return true;
            }
        }

        return false;
    }
}
