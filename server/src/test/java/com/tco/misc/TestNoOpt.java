package com.tco.misc;

import java.util.Arrays;
import com.tco.requests.Places;
import com.tco.requests.Place;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

public class TestNoOpt {
    NoOpt noOpt;
    Places places;
    
    @BeforeEach
    public void setUpTest() {
        OptimizerFactory optimizer = OptimizerFactory.getInstance();
        try {
            TourConstruction opt = optimizer.get(0);
            this.noOpt = (NoOpt) opt;

        } catch(Exception e) {
            System.err.println("Error in setup: " + e.getMessage());
            throw new RuntimeException("Failed to set up test environment", e);
        }

        places = new Places();
        places.add(new Place("90.0000", "90.0000"));
        places.add(new Place("45.0000", "45.0000"));
        places.add(new Place("-90.0000", "-180.0000"));
    }

    @Test
    @DisplayName("ayushad: check NosOpt initialization")
    public void noOptTest() {
        assertTrue(this.noOpt instanceof NoOpt);
        assertTrue(this.noOpt != null);
    }

    @Test
    @DisplayName("ayushad: check NosOpt returns the same places")
    public void noOptReturnTest() {
        Places output = this.noOpt.construct(this.places, 1.0, "vincenty", 0.8);
        assertTrue(this.places == output);
    }
    
}
