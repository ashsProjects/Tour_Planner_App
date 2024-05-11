package com.tco.requests;

import java.util.Arrays;
import java.util.ArrayList;
import com.tco.misc.BadRequestException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

public class TestNearRequest {
    private NearRequest request;
    private Places places;
    private Distances distances;

    @Test
    @DisplayName("ayushad: test default constructor")
    public void testDefaultConstNear() throws BadRequestException, Exception {
        request = new NearRequest();
        assertEquals("near", request.getRequestType());
    }

    @Test
    @DisplayName("ayushad: test places in Colorado limited to 100")
    public void testLimit0Near() throws BadRequestException, Exception {
        Place starting = new Place("40.00", "-105.00");
        request = new NearRequest(starting, 50, Double.valueOf(3959), "vincenty", 3);
        request.buildResponse();
        places = request.getPlaces();
        distances = request.getDistances();

        assertEquals(3, places.size());
        assertTrue(places.size() == distances.size());
        assertEquals(3, distances.get(0));
        assertEquals("Fox Hole Airport", places.get(2).get("name"));
    }

    @Test
    @DisplayName("ayushad: longitude wraps around and no places returned")
    public void testNoPlacesNear() throws BadRequestException, Exception {
        Place starting = new Place("0.00", "179.90");
        request = new NearRequest(starting, 50, Double.valueOf(3959), "vincenty", 1000);
        request.buildResponse();
        places = request.getPlaces();
        distances = request.getDistances();

        assertTrue(places.size() == 0);
    }

    @Test
    @DisplayName("ayushad: test for km")
    public void testKmNear() throws BadRequestException, Exception {
        Place starting = new Place("45.00", "-90.00");
        request = new NearRequest(starting, 50, Double.valueOf(3959), "vincenty", 100);
        request.buildResponse();
        places = request.getPlaces();
        distances = request.getDistances();

        assertEquals(54, places.size());
        assertEquals("Corinth Airport", places.get(0).get("name"));
        assertEquals(19, distances.get(9));
    }

    @Test
    @DisplayName("ayushad: test rural location")
    public void testRural() throws BadRequestException, Exception {
        Place starting = new Place("44.302222", "-96.786111");
        request = new NearRequest(starting, 4, Double.valueOf(6371), "vincenty", 100);
        request.buildResponse();
        places = request.getPlaces();
        distances = request.getDistances();

        assertEquals(2, places.size());
        assertEquals("Brookview Manor Heliport", places.get(0).get("name"));
        assertEquals(2, distances.get(0));
    }

    @Test
    @DisplayName("ayushad: check edge of radius (starting is 45 miles from given location)")
    public void testEdge() throws BadRequestException, Exception {
        Place starting = new Place("40.12825", "-107.53372");
        request = new NearRequest(starting, 45, Double.valueOf(3959), "vincenty", 100);
        request.buildResponse();
        places = request.getPlaces();
        distances = request.getDistances();

        boolean found = false;
        for (int i = 0; i < places.size(); i++) {
            if (places.get(i).get("name").equals("Steamboat Springs Bob Adams Field")) {
                found = true; 
                break;
            }
        }
        assertTrue(found);
    }
}
