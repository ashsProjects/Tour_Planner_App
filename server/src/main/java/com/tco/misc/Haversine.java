package com.tco.misc;

public class Haversine implements GreatCircleDistance {

    @Override
    public long between(GeographicCoordinate coord1, GeographicCoordinate coord2, double radius) {
        double coord1lat = coord1.latRadians();
        double coord1lon = coord1.lonRadians();
        double coord2lat = coord2.latRadians();
        double coord2lon = coord2.lonRadians();

        double latDistance = coord2lat - coord1lat;
        double lonDistance = coord2lon - coord1lon;

        // Haversine Formula
        double a = Math.pow(Math.sin(latDistance / 2), 2) + 
                   Math.pow(Math.sin(lonDistance / 2), 2) *
                   Math.cos(coord1lat) * 
                   Math.cos(coord2lat);
        double c = 2 * Math.asin(Math.sqrt(a));

        long distance = Math.round(radius * c);

        return distance;
    }
}
