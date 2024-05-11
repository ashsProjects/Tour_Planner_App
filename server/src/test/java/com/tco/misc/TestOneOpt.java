package com.tco.misc;

import com.tco.requests.Places;
import com.tco.requests.Place;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

public class TestOneOpt {
    TourConstruction optimizer;
    Places places;
    double earthRadius;
    String formula;
    double response;

    @BeforeEach
    public void createConfigForOneOpt() {
        optimizer = new OneOpt();

        places = new Places();
        places.add(new Place("00.000", "-45.000")); //0
        places.add(new Place("00.000", "-90.000")); //1
        places.add(new Place("45.000", "-45.000")); //2
        places.add(new Place("45.000", "-90.000")); //3

        earthRadius = 3959;
        formula = "vincenty";
        response = 0.99;
    }

    @Test 
    @DisplayName("ayushad: returns a better route for 4 places as a rectangle")
    public void checkCorrectValueForFour() throws BadRequestException {
        Places optimizedPlaces = optimizer.construct(places, earthRadius, formula, response);
        String placeLat = optimizedPlaces.get(3).get("latitude");
        String placeLon = optimizedPlaces.get(3).get("longitude");

        assertEquals("45.000", placeLat);
        assertEquals("-45.000", placeLon);
    }

    @Test 
    @DisplayName("ayushad: returns a better route for 5 places as a pentagon")
    public void checkCorrectValueForFive() throws BadRequestException {
        places.add(new Place("-23.000", "-70.000"));
        Places optimizedPlaces = optimizer.construct(places, earthRadius, formula, response);
        optimizer.improve(new int[] {1,2,3});
        String placeLat = optimizedPlaces.get(1).get("latitude");
        String placeLon = optimizedPlaces.get(1).get("longitude");

        assertEquals("-23.000", placeLat);
        assertEquals("-70.000", placeLon);
    }
}