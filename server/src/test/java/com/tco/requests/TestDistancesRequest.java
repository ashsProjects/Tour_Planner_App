package com.tco.requests;

import java.util.Arrays;
import java.util.ArrayList;
import com.tco.misc.BadRequestException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

public class TestDistancesRequest {
    private Places places;
    private Distances distances;
    private DistancesRequest distancesRequest;

    final Place place_00 = new Place("0.0000", "0.0000"); 
    final Place place_NP = new Place("90.0000", "0"); // North Pole
    final Place place_SP = new Place("-90.0000", "0"); // South Pole 
    final Place place_NE = new Place("40.7128", "74.0060"); // New York
    final Place place_NW = new Place("51.5074", "-0.1278"); // London
    final Place place_SW = new Place("-33.8688", "151.2093"); // Sydney
    final Place place_SE = new Place("-23.5505", "-46.6333"); // Sao Paolo

    final static Double earthRadius_km = 6371.0; 

    @BeforeEach
    public void setUp() {
        // Resetting our ArrayLists for each test
        places = new Places();
        distances = new Distances();

        places.add(place_NE);
        places.add(place_NW);
        places.add(place_SW);
        places.add(place_SE);
    }

    @Test
    @DisplayName("ayushad: distances should be [5587, 16994, 13357, 14225] using Vincenty formula") 
    public void testBuildDistanceListVincenty() throws BadRequestException {
        ArrayList<Long> calculated_distances = new ArrayList<>(Arrays.asList(5587L, 16994L, 13357L, 14225L));
        distancesRequest = new DistancesRequest(earthRadius_km, places, "vincenty");
        distancesRequest.buildResponse();
        distances = distancesRequest.getDistanceList();
        assertEquals(calculated_distances, distances);
    }

    @Test
    @DisplayName("ayushad: distances should be [5587, 16994, 13357, 14225] using Haversine formula") 
    public void testBuildDistanceListHaversine() throws BadRequestException {
        ArrayList<Long> calculated_distances = new ArrayList<>(Arrays.asList(5587L, 16994L, 13357L, 14225L));
        distancesRequest = new DistancesRequest(earthRadius_km, places, "haversine");
        distancesRequest.buildResponse();
        distances = distancesRequest.getDistanceList();
        assertEquals(calculated_distances, distances);
    }

    @Test
    @DisplayName("ayushad: distances should be [5587, 16994, 13357, 14225] using cosines formula") 
    public void testBuildDistanceListCosines() throws BadRequestException {
        ArrayList<Long> calculated_distances = new ArrayList<>(Arrays.asList(5587L, 16994L, 13357L, 14225L));
        distancesRequest = new DistancesRequest(earthRadius_km, places, "cosines");
        distancesRequest.buildResponse();
        distances = distancesRequest.getDistanceList();
        assertEquals(calculated_distances, distances);
    }

    @Test
    @DisplayName("ayushad: distances should be [5587, 16994, 13357, 14225] using blank formula -- defaults to vincenty")
    public void testBuildDistanceDefault() throws BadRequestException {
        ArrayList<Long> calculated_distances = new ArrayList<>(Arrays.asList(5587L, 16994L, 13357L, 14225L));
        distancesRequest = new DistancesRequest(earthRadius_km, places);
        distancesRequest.buildResponse();
        distances = distancesRequest.getDistanceList();
        assertEquals(calculated_distances, distances);
    }
    
    @Test
    @DisplayName("ayushad: distances should be 0 when using the same place -- vincenty")
    public void testBuildDistanceSamePlace() throws BadRequestException {
        places = new Places();
        places.add(place_00);
        places.add(place_00);

        ArrayList<Long> calculated_distances = new ArrayList<>(Arrays.asList(0L, 0L));
        distancesRequest = new DistancesRequest(earthRadius_km, places);
        distancesRequest.buildResponse();
        distances = distancesRequest.getDistanceList();
        assertEquals(calculated_distances, distances);
    }

    @Test
    @DisplayName("ayushad: checkRequest() earthRadius null should throw error")
    public void testNullRadiusRequest() throws BadRequestException {
        DistancesRequest req = new DistancesRequest(null, places);
        assertThrows(BadRequestException.class, () -> req.buildResponse());
    }
    @Test
    @DisplayName("ayushad: checkRequest() earthRadius <= 0 should throw error")
    public void testNegativeRadiusRequest() throws BadRequestException {
        DistancesRequest req = new DistancesRequest(-1.0, places);
        DistancesRequest req1 = new DistancesRequest(0.0, places);
        assertThrows(BadRequestException.class, () -> req.buildResponse());
        assertThrows(BadRequestException.class, () -> req1.buildResponse());
    }
    
    @Test
    @DisplayName("ayushad: checkRequest() places null should throw error")
    public void testNullPlacesRequest() throws BadRequestException {
        double radius = 1;
        DistancesRequest req = new DistancesRequest(radius, null);
        assertThrows(BadRequestException.class, () -> req.buildResponse());
    }
    
    @Test
    @DisplayName("ayushad: checkRequest() should throw error -- invalid latitude")
    public void testInvalidLatitude() throws BadRequestException {
        Place invalidPlace = new Place("91.0000" , "0.0000");
        places = new Places();
        places.add(invalidPlace);

        DistancesRequest req = new DistancesRequest(earthRadius_km, places);
        assertThrows(BadRequestException.class, () -> req.buildResponse());       
    }

    @Test
    @DisplayName("ayushad: checkrequest() should throw error -- invalid longitude")
    public void testInvalidLongitude() throws BadRequestException {
        Place invalidPlace = new Place("0.0000" , "181.0000");
        places = new Places();
        places.add(invalidPlace);

        DistancesRequest req = new DistancesRequest(earthRadius_km, places);
        assertThrows(BadRequestException.class, () -> req.buildResponse());
    }
}
