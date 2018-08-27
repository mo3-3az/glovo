package com.glovoapp.backender.order.model;

import com.glovoapp.backender.distance.Location;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class OrderTest {

    @Test
    void hashCodeSame() {
        Assertions.assertEquals(
                new Order()
                        .withPickup(Location.get(1.0, 2.0))
                        .withDelivery(Location.get(1.0, 2.0))
                        .withVip(true)
                        .withFood(true)
                        .withId("order-1")
                        .withDescription("Order1")
                        .hashCode(),
                new Order()
                        .withPickup(Location.get(1.0, 2.0))
                        .withDelivery(Location.get(1.0, 2.0))
                        .withVip(true)
                        .withFood(true)
                        .withId("order-1")
                        .withDescription("Order1")
                        .hashCode());
    }

    @Test
    void hashCodeDifferent() {
        Assertions.assertNotEquals(
                new Order()
                        .withPickup(Location.get(1.0, 2.0))
                        .withDelivery(Location.get(1.0, 2.0))
                        .withVip(true)
                        .withFood(true)
                        .withId("order-1")
                        .withDescription("Order1")
                        .hashCode(),
                new Order()
                        .withPickup(Location.get(1.0, 2.0))
                        .withDelivery(Location.get(1.0, 2.0))
                        .withVip(true)
                        .withFood(true)
                        .withId("order-1")
                        .withDescription("Order2")
                        .hashCode());
    }

    @Test
    void toStringSame() {
        Assertions.assertEquals(
                new Order()
                        .withPickup(Location.get(1.0, 2.0))
                        .withDelivery(Location.get(1.0, 2.0))
                        .withVip(true)
                        .withFood(true)
                        .withId("order-1")
                        .withDescription("Order1")
                        .toString(),
                new Order()
                        .withPickup(Location.get(1.0, 2.0))
                        .withDelivery(Location.get(1.0, 2.0))
                        .withVip(true)
                        .withFood(true)
                        .withId("order-1")
                        .withDescription("Order1")
                        .toString());
    }

    @Test
    void toStringDifferent() {
        Assertions.assertNotEquals(
                new Order()
                        .withPickup(Location.get(1.0, 2.0))
                        .withDelivery(Location.get(1.0, 2.0))
                        .withVip(true)
                        .withFood(true)
                        .withId("order-1")
                        .withDescription("Order1")
                        .toString(),
                new Order()
                        .withPickup(Location.get(1.0, 2.0))
                        .withDelivery(Location.get(1.0, 2.0))
                        .withVip(true)
                        .withFood(true)
                        .withId("order-1")
                        .withDescription("Order2")
                        .toString());
    }
}