package com.glovoapp.backender.distance;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class LocationTest {

    @Test
    void hashCodeSame() {
        Assertions.assertEquals(Location.get(40.1, 2.1).hashCode(), Location.get(40.1, 2.1).hashCode());
    }

    @Test
    void hashCodeDifferent() {
        Assertions.assertNotEquals(Location.get(40.1, 2.0).hashCode(), Location.get(40.1, 2.1).hashCode());
    }

    @Test
    void toStringSame() {
        Assertions.assertEquals(Location.get(40.1, 2.1).toString(), Location.get(40.1, 2.1).toString());
    }

    @Test
    void toStringDifferent() {
        Assertions.assertNotEquals(Location.get(40.1, 2.0).toString(), Location.get(40.1, 2.1).toString());
    }
}