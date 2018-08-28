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
        distanceComparator = new DistanceComparator(1000, Location.get(41.0, 2.101));
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
                new Order().withPickup(Location.get(41.0, 2.101)),
                new Order().withPickup(Location.get(41.0, 2.101)),
                new Order().withPickup(Location.get(41.0, 2.101)),
                new Order().withPickup(Location.get(41.0, 2.101))
        );

        final List<Order> ordersCopy = new ArrayList<>(ordersOriginal);
        ordersOriginal.sort(distanceComparator);
        Assertions.assertEquals(ordersCopy, ordersOriginal);
    }

    @Test
    void sortOrdersSomeWithinSameDistanceSlotSomeFurther() {
        final List<Order> ordersOriginal = Arrays.asList(
                new Order().withPickup(Location.get(41.0, 2.101)),
                new Order().withPickup(Location.get(51.0, 2.101)),
                new Order().withPickup(Location.get(51.0, 2.101)),
                new Order().withPickup(Location.get(41.0, 2.101))
        );

        final List<Order> ordersSorted = Arrays.asList(
                new Order().withPickup(Location.get(41.0, 2.101)),
                new Order().withPickup(Location.get(41.0, 2.101)),
                new Order().withPickup(Location.get(51.0, 2.101)),
                new Order().withPickup(Location.get(51.0, 2.101))
        );

        ordersOriginal.sort(distanceComparator);
        Assertions.assertEquals(ordersSorted, ordersOriginal);
    }

    @Test
    void sortOrdersFarFromEachOther() {
        DistanceComparator distanceComparator = new DistanceComparator(1000, Location.get(40.0, 2.101));
        final List<Order> ordersOriginal = Arrays.asList(
                new Order().withPickup(Location.get(55.0, 2.101)),
                new Order().withPickup(Location.get(50.0, 2.101)),
                new Order().withPickup(Location.get(45.0, 2.101)),
                new Order().withPickup(Location.get(40.0, 2.101))
        );

        final List<Order> ordersSorted = Arrays.asList(
                new Order().withPickup(Location.get(40.0, 2.101)),
                new Order().withPickup(Location.get(45.0, 2.101)),
                new Order().withPickup(Location.get(50.0, 2.101)),
                new Order().withPickup(Location.get(55.0, 2.101))
        );

        ordersOriginal.sort(distanceComparator);
        Assertions.assertEquals(ordersSorted, ordersOriginal);
    }

    @Test
    void sortOrdersWithinSameLongDistanceSlot() {
        DistanceComparator distanceComparator = new DistanceComparator(100000, Location.get(41.0, 2.101));
        final List<Order> ordersOriginal = Arrays.asList(
                new Order().withPickup(Location.get(41.10000000001, 2.101)),
                new Order().withPickup(Location.get(41.10000000002, 2.101)),
                new Order().withPickup(Location.get(41.10000000003, 2.101)),
                new Order().withPickup(Location.get(41.10000000004, 2.101))
        );

        final List<Order> ordersSorted = Arrays.asList(
                new Order().withPickup(Location.get(41.10000000001, 2.101)),
                new Order().withPickup(Location.get(41.10000000002, 2.101)),
                new Order().withPickup(Location.get(41.10000000003, 2.101)),
                new Order().withPickup(Location.get(41.10000000004, 2.101))
        );

        ordersOriginal.sort(distanceComparator);
        Assertions.assertEquals(ordersSorted, ordersOriginal);
    }

    @Test
    void sortNullOrders() {
        final List<Order> ordersOriginal = Arrays.asList(
                null, new Order().withPickup(Location.get(41.4, 2.101)), null
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