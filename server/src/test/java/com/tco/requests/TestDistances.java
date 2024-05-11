package com.tco.requests;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

public class TestDistances {
    Distances distances = new Distances();
    final long[] zeroDistances = {0L, 0L, 0L, 0L, 0L};
    final long[] smallDistances = {1L, 2L, 6L, 1L, 4L, 7L, 8L, 3L, 4L, 9L, 0L, 2L};
    final long[] largeDistances = {1000000L, 10000000L, 100000000L, 1000000000L, 10000000000L, 100000000000L};

    @Test
    @DisplayName("ayushad: Total distance should be zero for an empty route")
    public void testEmptyRoute() {
        long distancesTotal = distances.total();
        assertEquals(0L, distancesTotal);
    }

    @Test
    @DisplayName("ayushad: Total distance should be 26 for a route with short distances")
    public void testZeroDistancesRoute() {
        for (long distance : zeroDistances) distances.add(distance);
        long distancesTotal = distances.total();
        assertEquals(0L, distancesTotal);
    }

    @Test
    @DisplayName("ayushad: Total distance should be 60 for a route with medium distances")
    public void testSmallDistancesRoute() {
        for (long distance : smallDistances) distances.add(distance);
        long distancesTotal = distances.total();
        assertEquals(47L, distancesTotal);
    }

    @Test
    @DisplayName("ayushad: Total distance should be 350 for a route with long distances")
    public void testLargeDistancesRoute() {
        for (long distance : largeDistances) distances.add(distance);
        long distancesTotal = distances.total();
        assertEquals(111111000000L, distancesTotal);
    }
}