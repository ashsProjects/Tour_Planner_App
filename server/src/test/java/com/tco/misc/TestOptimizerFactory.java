package com.tco.misc;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

public class TestOptimizerFactory {
    OptimizerFactory optimizer = OptimizerFactory.getInstance();
    
    @Test 
    @DisplayName("ayushad: check NosOpt")
    public void checkNoOpt() throws BadRequestException {
        TourConstruction tourConstruction = optimizer.get(0);
        assertTrue(tourConstruction instanceof NoOpt);
    }
    @Test 
    @DisplayName("ayushad: check OneOpt")
    public void checkOneOpt() throws BadRequestException {
        TourConstruction tourConstruction = optimizer.get(1);
        assertTrue(tourConstruction instanceof OneOpt);
    }
    @Test 
    @DisplayName("ayushad: check TwoOpt")
    public void checkTwoOpt() throws BadRequestException {
        TourConstruction tourConstruction = optimizer.get(2);
        assertTrue(tourConstruction instanceof TwoOpt);
    }
    @Test 
    @DisplayName("ayushad: check ThreeOpt")
    public void checkThreeOpt() throws BadRequestException {
        TourConstruction tourConstruction = optimizer.get(3);
        assertTrue(tourConstruction instanceof ThreeOpt);
    }
    @Test 
    @DisplayName("ayushad: check throws")
    public void checkThrows() throws BadRequestException {
        assertThrows(BadRequestException.class, () -> {
            TourConstruction tourConstruction = optimizer.get(-1);
        });
    }
}
