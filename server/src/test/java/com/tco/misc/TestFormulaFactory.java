package com.tco.misc;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

public class TestFormulaFactory {
    FormulaFactory factory = FormulaFactory.getInstance();

    @Test 
    @DisplayName("ayushad: vincenty returns a Vincenty object")
    public void checkVincenty() throws BadRequestException {
        GreatCircleDistance distanceCalculator = factory.get("vincenty");
        assertTrue(distanceCalculator instanceof Vincenty);
    }

    @Test 
    @DisplayName("ayushad: cosines returns a Cosines object")
    public void checkCosines() throws BadRequestException {
        GreatCircleDistance distanceCalculator = factory.get("cosines");
        assertTrue(distanceCalculator instanceof Cosines);
    }

    @Test 
    @DisplayName("ayushad: haversine returns a Haversine object")
    public void checkHaversine() throws BadRequestException {
        GreatCircleDistance distanceCalculator = factory.get("haversine");
        assertTrue(distanceCalculator instanceof Haversine);
    }

    @Test 
    @DisplayName("ayushad: null string returns a Vincenty object")
    public void checkNullString() throws BadRequestException {
        GreatCircleDistance distanceCalculator = factory.get(null);
        assertTrue(distanceCalculator instanceof Vincenty);
    }

    @Test 
    @DisplayName("ayushad: wrong formula should throw error")
    public void checkWrongFormula() throws BadRequestException {
        assertThrows(BadRequestException.class, () -> {
            GreatCircleDistance distanceCalculator = factory.get("tanh");
        });
    }
}
