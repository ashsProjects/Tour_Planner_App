package com.tco.misc;

import java.util.Arrays;
import com.tco.requests.Places;
import com.tco.requests.Place;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

public class TestDistanceMatrix {
    private DistanceMatrix distances;
    private Places places;

    @BeforeEach
    public void createConfigForDistanceMatrix() {
        distances = new DistanceMatrix(3);
        //setting distances to [[11,0,0], [0,22,0], [0,0,3333333333333]]
        distances.setValue(0, 0, 11);
        distances.setValue(1, 1, 22);
        distances.setValue(2, 2, 3333333333333L);

        //setting up places
        places = new Places();
        places.add(new Place("90.0000", "90.0000"));
        places.add(new Place("45.0000", "45.0000"));
        places.add(new Place("-90.0000", "-180.0000"));
    }

    @Test 
    @DisplayName("ayushad: distance matrix returns correct size")
    public void checkMatrixSize() throws BadRequestException {
        assertEquals(9, distances.getSize());
    }

    @Test 
    @DisplayName("ayushad: distance matrix returns correct dimension")
    public void checkMatrixDimension() throws BadRequestException {
        assertEquals(3, distances.getDimension());
    }

    @Test 
    @DisplayName("ayushad: check matrix contains correct values")
    public void checkCorrectValuesInMatrix() throws BadRequestException {
        assertEquals(11, distances.getValue(0,0));
        assertEquals(22, distances.getValue(1,1));
        assertEquals(3333333333333L, distances.getValue(2,2));
    }

    @Test 
    @DisplayName("ayushad: check matrix is filled correctly")
    public void checkFillMatrix() throws BadRequestException {
        distances = new DistanceMatrix(3);
        distances.fillMatrix(places, 10, "vincenty");
        assertEquals(0, distances.getValue(0, 0));
        assertEquals(8, distances.getValue(0, 1));
        assertEquals(31, distances.getValue(0, 2));
        assertEquals(24, distances.getValue(1, 2));
    }

    @Test 
    @DisplayName("ayushad: check opposite indices have same values")
    public void checkOppositeValues() throws BadRequestException {
        distances = new DistanceMatrix(3);
        distances.fillMatrix(places, 10, "vincenty");
        assertEquals(distances.getValue(0, 1), distances.getValue(1, 0));
        assertEquals(distances.getValue(0, 2), distances.getValue(2, 0));
        assertEquals(distances.getValue(1, 2), distances.getValue(2, 1));
    }

    @Test 
    @DisplayName("ayushad: check index of lowest value returned is correct")
    public void checkLowestValueReturned() throws BadRequestException {
        distances = new DistanceMatrix(3);
        distances.fillMatrix(places, 10, "vincenty");
        int[] tour = {0, 1, 2};
        assertEquals(1, distances.findLowestDistance(0, tour));
    }

    @Test 
    @DisplayName("ayushad: check index of lowest value is 0 when all nodes are visited")
    public void checkLowestWhenAllVisited() throws BadRequestException {
        distances = new DistanceMatrix(3);
        distances.fillMatrix(places, 10, "vincenty");
        int[] tour = {0, 1, 2};
        assertEquals(-1, distances.findLowestDistance(2, tour));
    }
}