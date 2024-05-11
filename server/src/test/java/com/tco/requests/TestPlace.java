package com.tco.requests;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestPlace {
    private Place place;

    @BeforeEach
    public void createConfigurationForTestCases() {
        place = new Place("45", "-90");
    }

    @Test
    @DisplayName("ayushad: Latitude is converted to radians correctly")
    public void testLatRadians() {
        final double inRadians45 = 0.785398;
        double radiansFromPlace = place.latRadians();
        assertEquals(inRadians45, radiansFromPlace, 0.001f); // delta is 0.001f for closeness
    }

    @Test
    @DisplayName("ayushad: Longitude is converted to radians correctly")
    public void testLonRadians() {
        final double inRadiansNeg90 = -1.5708;
        double radiansFromPlace = place.lonRadians();
        assertEquals(inRadiansNeg90, radiansFromPlace, 0.001f); // delta is 0.001f for closeness
    }
    
}
