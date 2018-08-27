package com.glovoapp.backender.courier;

import com.glovoapp.backender.distance.Location;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class CourierTest {

    @Test
    void hashCodeSame() {
        Assertions.assertEquals(
                new Courier()
                        .withLocation(Location.get(1.0, 2.0))
                        .withVehicle(Vehicle.BICYCLE)
                        .withBox(true)
                        .withId("courier-1")
                        .withName("Marco")
                        .hashCode(),
                new Courier()
                        .withLocation(Location.get(1.0, 2.0))
                        .withVehicle(Vehicle.BICYCLE)
                        .withBox(true)
                        .withId("courier-1")
                        .withName("Marco")
                        .hashCode());
    }

    @Test
    void hashCodeDifferent() {
        Assertions.assertNotEquals(
                new Courier()
                        .withLocation(Location.get(1.0, 2.0))
                        .withVehicle(Vehicle.BICYCLE)
                        .withBox(true)
                        .withId("courier-2")
                        .withName("Marco")
                        .hashCode(),
                new Courier()
                        .withLocation(Location.get(1.0, 2.0))
                        .withVehicle(Vehicle.BICYCLE)
                        .withBox(true)
                        .withId("courier-1")
                        .withName("Marco")
                        .hashCode());
    }

    @Test
    void toStringSame() {
        Assertions.assertEquals(
                new Courier()
                        .withLocation(Location.get(1.0, 2.0))
                        .withVehicle(Vehicle.BICYCLE)
                        .withBox(true)
                        .withId("courier-1")
                        .withName("Marco")
                        .toString(),
                new Courier()
                        .withLocation(Location.get(1.0, 2.0))
                        .withVehicle(Vehicle.BICYCLE)
                        .withBox(true)
                        .withId("courier-1")
                        .withName("Marco")
                        .toString());
    }

    @Test
    void toStringDifferent() {
        Assertions.assertNotEquals(
                new Courier()
                        .withLocation(Location.get(1.0, 2.0))
                        .withVehicle(Vehicle.BICYCLE)
                        .withBox(true)
                        .withId("courier-2")
                        .withName("Marco")
                        .toString(),
                new Courier()
                        .withLocation(Location.get(1.0, 2.0))
                        .withVehicle(Vehicle.BICYCLE)
                        .withBox(true)
                        .withId("courier-1")
                        .withName("Marco")
                        .toString());
    }
}