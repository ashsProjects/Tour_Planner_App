package com.tco.requests;

import com.tco.misc.FormulaFactory;
import com.tco.misc.BadRequestException;
import com.tco.misc.GeographicCoordinate;
import com.tco.misc.GreatCircleDistance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DistancesRequest extends Request {
    private static final transient Logger log = LoggerFactory.getLogger(DistancesRequest.class);
    private Places places;
    private Distances distances;
    private Double earthRadius;
    private String formula;
    
    public DistancesRequest() {
        super.requestType = "distances";
    }

    public DistancesRequest(Double radius, Places places) {
        super.requestType = "distances";
        this.places = places;
        this.earthRadius = radius;
    }

    public DistancesRequest(Double radius, Places places, String formula) {
        super.requestType = "distances";
        this.places = places;
        this.earthRadius = radius;
        this.formula = formula;
    }

    @Override
    public void buildResponse() throws BadRequestException {
        checkRadius();
        checkPlaces();
        distances = buildDistanceList();
        log.trace("buildResponse -> {}", this);
    }

    private Distances buildDistanceList() throws BadRequestException {
        Distances distances = new Distances();

        FormulaFactory factory = FormulaFactory.getInstance();
        GreatCircleDistance distanceCalculator = factory.get(formula);

        for (int i = 0; i < places.size(); i++) {
            GeographicCoordinate from = places.get(i);
            GeographicCoordinate to = places.get((i + 1) % places.size());
            long distance = distanceCalculator.between(from, to, earthRadius);
            distances.add(distance);
        }

        return distances;
    }

    public Distances getDistanceList() {
        return distances;
    }

    private void checkRadius() throws BadRequestException {
        if (earthRadius == null) throw new BadRequestException();
        if (earthRadius <= 0) throw new BadRequestException(); 
    }

    private void checkPlaces() throws BadRequestException {
        if (places == null) throw new BadRequestException();
        for (Place place: places) {
            checkLatRadians(place);
            checkLonRadians(place);
        }
    }

    private void checkLatRadians(Place place) throws BadRequestException {
        if (place.latRadians() < (-0.5 * Math.PI) || place.latRadians() > (0.5 * Math.PI))
            throw new BadRequestException();
    }

    private void checkLonRadians(Place place) throws BadRequestException {
        if (place.lonRadians() < (-1.0 * Math.PI) || place.lonRadians() > (Math.PI))
            throw new BadRequestException();
    }
}
