package com.tco.requests;

import com.tco.misc.BadRequestException;
import com.tco.misc.GeographicLocations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NearRequest extends Request {
    private static final transient Logger log = LoggerFactory.getLogger(NearRequest.class);
    private Place place;
    private Integer distance;
    private Double earthRadius;
    private String formula;
    private Integer limit;
    private Places places;
    private Distances distances;

    public NearRequest() {
        super.requestType = "near";
    }

    public NearRequest(Place p, Integer d, Double r, String f, Integer l) {
        super.requestType = "near";
        this.place = p;
        this.distance = d;
        this.earthRadius = r;
        this.formula = f;
        this.limit = l;
    }

    @Override
    public void buildResponse() throws BadRequestException, Exception {
        GeographicLocations geoLoc = new GeographicLocations(
            this.place, this.distance, this.earthRadius, this.formula, this.limit
        );
        places = geoLoc.near();
        distances = geoLoc.distances();
        log.trace("buildResponse -> {}", this);
    }

    public Places getPlaces() {
        return this.places;
    }

    public Distances getDistances() {
        return this.distances;
    }
}
