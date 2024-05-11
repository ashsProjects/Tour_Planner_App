package com.tco.requests;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.LinkedHashMap;
import com.tco.misc.GeographicCoordinate;

public class Place extends LinkedHashMap<String, String> implements GeographicCoordinate {

    private static final transient Logger log = LoggerFactory.getLogger(Place.class);

    public Place(String latitude, String longitude) {
        this.put("latitude", latitude);
        this.put("longitude", longitude);
    }

    public Place() {}

    public double latRadians() {
        double latInRad = Double.parseDouble(this.get("latitude"));
        return Math.toRadians(latInRad);
    }
    public double lonRadians() {
        double lonInRad = Double.parseDouble(this.get("longitude"));
        return Math.toRadians(lonInRad);
    }
}