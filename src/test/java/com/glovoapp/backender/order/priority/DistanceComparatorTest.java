package com.glovoapp.backender.order.priority;

import com.glovoapp.backender.distance.Location;
import com.glovoapp.backender.order.model.Order;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class DistanceComparatorTest {

    private DistanceComparator distanceComparator;

    @BeforeEach
    void setUp() {
        distanceComparator = new DistanceComparator(1000, Location.get(10.0, 1.0));
    }

    @Test
    void sortNullOrdersBasedOnNegativeDistance() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> new DistanceComparator(-10, Location.get(10.0, 1.0)));
    }

    @Test
    void sortNullOrdersBasedOnNullLocation() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> new DistanceComparator(100, null));
    }

    @Test
    void sortOrdersWithinSameDistanceSlot() {
        final List<Order> ordersOriginal = Arrays.asList(
                new Order().withPickup(Location.get(10.0, 1.0)),
                new Order().withPickup(Location.get(10.0, 1.0)),
                new Order().withPickup(Location.get(10.0, 1.0)),
                new Order().withPickup(Location.get(10.0, 1.0))
        );

        final List<Order> ordersCopy = new ArrayList<>(ordersOriginal);
        ordersOriginal.sort(distanceComparator);
        Assertions.assertEquals(ordersCopy, ordersOriginal);
    }

    @Test
    void sortOrdersSomeWithinSameDistanceSlotSomeFurther() {
        final List<Order> ordersOriginal = Arrays.asList(
                new Order().withPickup(Location.get(10.0, 1.0)),
                new Order().withPickup(Location.get(110.0, 1.0)),
                new Order().withPickup(Location.get(100.0, 1.0)),
                new Order().withPickup(Location.get(10.0, 1.0))
        );

        final List<Order> ordersSorted = Arrays.asList(
                new Order().withPickup(Location.get(10.0, 1.0)),
                new Order().withPickup(Location.get(10.0, 1.0)),
                new Order().withPickup(Location.get(100.0, 1.0)),
                new Order().withPickup(Location.get(110.0, 1.0))
        );

        ordersOriginal.sort(distanceComparator);
        Assertions.assertEquals(ordersSorted, ordersOriginal);
    }

    @Test
    void sortOrdersFarFromEachOther() {
        DistanceComparator distanceComparator = new DistanceComparator(1000, Location.get(10.0, 1.0));
        final List<Order> ordersOriginal = Arrays.asList(
                new Order().withPickup(Location.get(5000.0, 1.0)),
                new Order().withPickup(Location.get(1000.0, 1.0)),
                new Order().withPickup(Location.get(500.0, 1.0)),
                new Order().withPickup(Location.get(100.0, 1.0))
        );

        final List<Order> ordersSorted = Arrays.asList(
                new Order().withPickup(Location.get(100.0, 1.0)),
                new Order().withPickup(Location.get(500.0, 1.0)),
                new Order().withPickup(Location.get(1000.0, 1.0)),
                new Order().withPickup(Location.get(5000.0, 1.0))
        );

        ordersOriginal.sort(distanceComparator);
        Assertions.assertEquals(ordersSorted, ordersOriginal);
    }

    @Test
    void sortOrdersWithinSameLongDistanceSlot() {
        DistanceComparator distanceComparator = new DistanceComparator(100000, Location.get(1.0, 1.0));
        final List<Order> ordersOriginal = Arrays.asList(
                new Order().withPickup(Location.get(1.1, 1.0)),
                new Order().withPickup(Location.get(1.2, 1.0)),
                new Order().withPickup(Location.get(1.3, 1.0)),
                new Order().withPickup(Location.get(1.4, 1.0))
        );

        final List<Order> ordersSorted = Arrays.asList(
                new Order().withPickup(Location.get(1.1, 1.0)),
                new Order().withPickup(Location.get(1.2, 1.0)),
                new Order().withPickup(Location.get(1.3, 1.0)),
                new Order().withPickup(Location.get(1.4, 1.0))
        );

        ordersOriginal.sort(distanceComparator);
        Assertions.assertEquals(ordersSorted, ordersOriginal);
    }

    @Test
    void sortNullOrders() {
        final List<Order> ordersOriginal = Arrays.asList(
                null, new Order().withPickup(Location.get(1.4, 1.0)), null
        );

        Assertions.assertThrows(IllegalArgumentException.class, () -> ordersOriginal.sort(distanceComparator));
    }

    @Test
    void sortOrdersWithNullPickIpLocations() {
        final List<Order> ordersOriginal = Arrays.asList(
                new Order(), new Order()
        );

        Assertions.assertThrows(IllegalArgumentException.class, () -> ordersOriginal.sort(distanceComparator));
    }
}