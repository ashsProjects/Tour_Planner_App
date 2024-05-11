package com.tco.misc;

import com.tco.misc.GeographicCoordinate;

public interface GreatCircleDistance {
    public long between(GeographicCoordinate coord1, GeographicCoordinate coord2, double radius);
}
