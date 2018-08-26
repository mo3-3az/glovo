package com.glovoapp.backender.distance;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DistanceCalculatorTest {

    @Test
    void smokeTest() {
        Location francescMacia = new Location(41.3925603, 2.1418532);
        Location placaCatalunya = new Location(41.3870194, 2.1678584);

        // More or less 2km from Francesc Macia to Placa Catalunya
        assertEquals(2.0, DistanceCalculator.calculateDistanceKilometers(francescMacia, placaCatalunya), 0.5);
    }

    @Test
    void distanceInMeters() {
        Location francescMacia = new Location(41.3925603, 2.1418532);
        Location placaCatalunya = new Location(41.3870194, 2.1678584);

        // More or less 2km from Francesc Macia to Placa Catalunya
        assertEquals(2.255 * 1000, DistanceCalculator.calculateDistanceMeters(francescMacia, placaCatalunya), 0.5);
    }


}