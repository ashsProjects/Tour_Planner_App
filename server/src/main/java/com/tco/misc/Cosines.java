package com.tco.misc;

import com.tco.misc.GreatCircleDistance;

public class Cosines implements GreatCircleDistance {
    @Override
    public long between(GeographicCoordinate coord1, GeographicCoordinate coord2, double radius) {
        double coord1Lat = coord1.latRadians();
        double coord1Lon = coord1.lonRadians();
        double coord2Lat = coord2.latRadians();
        double coord2Lon = coord2.lonRadians();
        
        double lonDifference = Math.abs(coord1Lon - coord2Lon);
        double sinProduct = Math.sin(coord1Lat) * Math.sin(coord2Lat);
        double cosProduct = Math.cos(coord1Lat) * Math.cos(coord2Lat) * Math.cos(lonDifference);

        double innerSum = sinProduct + cosProduct;
        double acos = Math.acos(innerSum);

        long distance = Math.round(radius * Math.abs(acos));
        

        return distance;

    }
}
