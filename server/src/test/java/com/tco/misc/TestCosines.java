package com.tco.misc;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

public class TestCosines {
    private Cosines cosines = new Cosines();
    
    static class Geo implements GeographicCoordinate {
        double degreesLatitude;
        double degreesLongitude;

        public Geo(double lat, double lon) {
            this.degreesLatitude = lat;
            this.degreesLongitude = lon;
        }

        @Override
        public double latRadians() {return Math.toRadians(this.degreesLatitude);}
        @Override
        public double lonRadians() {return Math.toRadians(this.degreesLongitude);}
    }

    final Geo origin = new Geo(0, 0);
    final Geo e180 = new Geo(0, 180);
    final Geo w180 = new Geo(0, -180);
    final Geo e90 = new Geo(0, 90);
    final Geo w90 = new Geo(0, -90);
    final Geo n90 = new Geo(90, 0);
    final Geo s90 = new Geo(-90, 0);
    final Geo n45 = new Geo(45, 0);
    final Geo s45 = new Geo(-45, 0);
    final Geo e45 = new Geo(0, 45);
    final Geo w45 = new Geo(0, -45);

    final static long small = 1L;
    final static long piSmall = Math.round(Math.PI * small);
    final static long piSmallHalf = Math.round(Math.PI / 2.0 * small);

    final static long big = 1000000000000L;
    final static long piBig = Math.round(Math.PI * big);
    final static long piBigHalf = Math.round(Math.PI / 2.0 * big);


    @Test
    @DisplayName("ayushad: distance to self should be zero")
    public void testDistanceToSelf() {
        assertEquals(0L, cosines.between(origin, origin, small));
        assertEquals(0L, cosines.between(origin, origin, big));
    }

    @Test
    @DisplayName("ayushad: distance origin to south pole")
    public void testOriginToSouth() {
        assertEquals(piSmallHalf, cosines.between(origin, s90, small));
        assertEquals(piBigHalf, cosines.between(origin, s90, big));
    }

    @Test
    @DisplayName("ayushad: distance to same location should be zero")
    public void testDistanceToSamePlace() {
        assertEquals(0L, cosines.between(e180, w180, small));
    }

    @Test
    @DisplayName("ayushad: distance from pole to pole")
    public void testDistancePoleToPole() {
        assertEquals(piBig, cosines.between(n90, s90, big));
        assertEquals(piSmall, cosines.between(s90, n90, small));
    }
}
