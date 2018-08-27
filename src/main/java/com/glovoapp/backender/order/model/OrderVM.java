package com.glovoapp.backender.order.model;

/**
 * To be used for exposing order information through the API
 */
public class OrderVM {
    private final String id;
    private final String description;

    public OrderVM(String id, String description) {
        this.id = id;
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }
}
