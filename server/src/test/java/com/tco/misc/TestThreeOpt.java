package com.tco.misc;

import com.tco.requests.Places;
import com.tco.requests.Place;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

public class TestThreeOpt {
    TourConstruction optimizer;
    Places places;
    double earthRadius;
    String formula;
    double response;

    @BeforeEach
    public void createConfigForThreeOpt() {
        optimizer = new ThreeOpt();

        places = new Places();
        places.add(new Place("-78", "-3"));//0
        places.add(new Place("-68", "-1"));//1
        places.add(new Place("84", "-86"));//2
        places.add(new Place("-56", "61"));//3
        places.add(new Place("-12", "-114"));//4
        places.add(new Place("12", "-153"));//5
        places.add(new Place("86", "42"));//6
        places.add(new Place("14", "-110"));//7
        places.add(new Place("51", "83"));//8
        places.add(new Place("40", "111"));//9

        earthRadius = 3959;
        formula = "vincenty";
        response = 0.99;
    }

    @Test 
    @DisplayName("ayushad: returns a better route than one opt")
    public void checkCorrectValueForThreeOpt() throws BadRequestException {
        Places optimizedPlaces = optimizer.construct(places, earthRadius, formula, response);
        int[] correct = {0, 4, 7, 5, 2, 6, 8, 9, 3, 1};
        for (int i = 0; i < optimizedPlaces.size(); i++) {
            assertEquals(optimizedPlaces.get(i), places.get(correct[i]));
        }
    }
}
