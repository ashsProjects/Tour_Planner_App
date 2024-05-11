package com.tco.misc;

import com.tco.requests.Place;
import java.util.LinkedHashMap;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

public class TestHaversine {
    private Haversine haversine = new Haversine();

    final Place place_center = new Place("0","0");
    final Place place_NP = new Place("90.0000", "0"); // North Pole
    final Place place_SP = new Place("-90.0000", "0"); // South Pole 
    final Place place_NE = new Place("40.7128", "74.0060"); // New York
    final Place place_NW = new Place("51.5074", "-0.1278"); // London
    final Place place_SW = new Place(" -33.8688", "151.2093"); // Sydney
    final Place place_SE = new Place("-23.5505", "-46.6333 "); // Sao Paolo

    final static long small = 1L;
    final static long piSmall = Math.round(Math.PI * small);
    final static long piSmallHalf = Math.round(Math.PI / 2.0 * small);

    final static long big = 1000000000000L;
    final static long piBig = Math.round(Math.PI * big);
    final static long piBigHalf = Math.round(Math.PI / 2.0 * big);

    final static int earthRadius_km = 6371; 

    @Test
    @DisplayName("ayushad: distance to self should be zero")
    public void testDistanceToSelf() {
        assertEquals(0L, haversine.between(place_center, place_center, small));
        assertEquals(0L, haversine.between(place_NE, place_NE, big));
    }

    @Test 
    @DisplayName("ayushad: distance between New York and London should be 5587 km")
    public void placeToPlace() {
        assertEquals(5587L, haversine.between(place_NE, place_NW, earthRadius_km));
    }

    @Test
    @DisplayName("ayushad: distance from center to north pole should be half of pi * scale")
    public void testCenterToNorthPole() {
        assertEquals(piSmallHalf, haversine.between(place_center, place_NP, small));
        assertEquals(piBigHalf, haversine.between(place_center, place_NP, big));
    }

    @Test 
    @DisplayName("ayushad: distance between two poles should equal pi * scale")
    public void testSouthPoleToNorthPole() {
        assertEquals(piSmall, haversine.between(place_SP, place_NP, small));
        assertEquals(piBig, haversine.between(place_SP, place_NP, big));
    }
}
