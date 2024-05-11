package com.tco.requests;

import com.tco.misc.BadRequestException;
import com.tco.misc.OptimizerFactory;
import com.tco.misc.TourConstruction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TourRequest extends Request {
    private static final transient Logger log = LoggerFactory.getLogger(TourRequest.class);
    private Places places;
    private Double earthRadius;
    private String formula;
    private Double response;

    public TourRequest() {
        super.requestType = "tour";
    }

    public TourRequest(Double radius, Places places, Double response, String formula) {
        super.requestType = "tour";
        this.places = places;
        this.earthRadius = radius;
        this.formula = formula;
        this.response = response;
    }

    @Override
    public void buildResponse() throws BadRequestException { 
        log.trace("buildResponse -> {}", this);
        optimizePlaces();
    }

    private void optimizePlaces() throws BadRequestException {
        TourConstruction optimizer = checkResponse();
        places = optimizer.construct(places, earthRadius, formula, response);
    }

    private TourConstruction checkResponse() throws BadRequestException {
        int optimizerNum = 1;
        double expected1Opt = test1OptResponse();
        double expected2Opt = test2OptResponse();
        double expected3Opt = test3OptResponse();

        if (response == 0 || places.size() == 0 || expected1Opt > response) optimizerNum = 0;
        else if (expected3Opt < response) optimizerNum = 3;
        else if (expected2Opt < response) optimizerNum = 2;

        OptimizerFactory factory = OptimizerFactory.getInstance();
        TourConstruction optimizer = factory.get(optimizerNum);
        return optimizer;
    }

    private double test1OptResponse() {
        final double A1 = 0.0000000017; 
        final double B1 = -0.0000019002;
        final double C1 = 0.0012816228; 
        final double D1 = -0.0683345802;
        int x = places.size();
        double expectedTime = (A1 * Math.pow(x, 3)) + (B1 * Math.pow(x, 2)) + (C1 * x) + D1;
        return expectedTime;
    }

    private double test2OptResponse() {
        final double A2 = 0.0000000099; 
        final double B2 = -0.0000008997;
        final double C2 = 0.0003939391; 
        final double D2 = 0.1120505846; 
        int x = places.size();
        double expectedTime = D2 + (A2 * Math.pow(x, 3)) + (B2 * Math.pow(x, 2)) + (C2 * x);
        return expectedTime;
    }

    private double test3OptResponse() {
        final double A3 = 0.0000000086;
        final double B3 = 0.0000009809;
        final double C3 = -0.0001102642;
        final double D3 = 0.0041660683;
        final double E3 = 0.0785478104;
        int x = places.size();
        double expectedTime = D3 + (A3 * Math.pow(x, 4)) + (B3 * Math.pow(x, 3)) + (C3 * Math.pow(x, 2)) + (D3 * x);
        return expectedTime;
    }

    public Places getPlaces() {
        return places;
    }
}