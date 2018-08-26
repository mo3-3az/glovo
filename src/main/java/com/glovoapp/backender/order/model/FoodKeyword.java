package com.glovoapp.backender.order.model;

import java.util.List;

/**
 * Keywords that can be used to describe food orders.
 *
 * @author Moath
 * @see "Property: orders-fetcher.foodKeywords"
 */
public enum FoodKeyword {
    PIZZA,
    CAKE,
    FLAMINGO;

    /**
     * This method will determine if the passed order's description is related to food based on the passed
     * list of food keywords.
     */
    public static boolean isFoodOrder(String orderDescription, List<FoodKeyword> foodKeywords) {
        for (FoodKeyword foodOrder : foodKeywords) {
            if (orderDescription.toUpperCase().contains(foodOrder.name())) {
                return true;
            }
        }

        return false;
    }
}
