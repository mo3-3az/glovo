package com.glovoapp.backender.order.service;

import com.glovoapp.backender.order.model.Order;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

@Component
public class OrderRepository {
    private static final String ORDERS_FILE = "/orders.json";
    private static final List<Order> orders;

    static {
        orders = getOrdersFromFile(ORDERS_FILE);
    }

    public List<Order> findAll() {
        return new ArrayList<>(orders);
    }

    private static List<Order> getOrdersFromFile(String ordersFile) {
        try (Reader reader = new InputStreamReader(OrderRepository.class.getResourceAsStream(ordersFile))) {
            Type type = new TypeToken<List<Order>>() {
            }.getType();
            return new Gson().fromJson(reader, type);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
