package com.tco.requests;

import com.tco.misc.BadRequestException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

public class TestTourRequest {
    private Places places;
    private Places optimizedPlaces;
    private TourRequest tourRequest;

    final Place place_0 = new Place("00.000", "-45.000");
    final Place place_1 = new Place("00.000", "-90.000");
    final Place place_2 = new Place("45.000", "-45.000");
    final Place place_3 = new Place("45.000", "-90.000");

    // Rectangle -- Colorado Four Corners
    final Place place_square_NW = new Place("41.0042", "-109.0856"); 
    final Place place_square_NE = new Place("40.9905", "-102.0415"); 
    final Place place_square_SW = new Place("37.0286", "-109.0389"); 
    final Place place_square_SE = new Place("37.0277", "-102.0724"); 

     // Pentagram
    final Place place_pentagram_N = new Place("49.0008", "-100.7654");
    final Place place_pentagram_E = new Place("47.8882", "-97.7603");
    final Place place_pentagram_SE = new Place("45.9035", "-98.3134");
    final Place place_pentagram_SW = new Place("45.8901", "-101.6590");
    final Place place_pentagram_W = new Place("47.8805", "-102.8149");

    // Five Point Shape 01 (Shape 3 from TourRequest Slides)
    final Place place_01_point_A = new Place("41.0041", "-105.4401");
    final Place place_01_point_B = new Place("39.6243", "-108.0441");
    final Place place_01_point_C = new Place("37.0088", "-107.6533");
    final Place place_01_point_D = new Place("37.0050", "-104.0708");
    final Place place_01_point_E = new Place("39.6514", "-102.8533");

    final double earthRadius_km = 6371; 

    @BeforeEach
    public void setUp() {
        places = new Places();
        optimizedPlaces = new Places();
    }

    @Test
    @DisplayName("ayushad: test correct tour with four places") 
    public void testTempTour() throws BadRequestException {
        places.add(place_0);
        places.add(place_1);
        places.add(place_2);
        places.add(place_3);

        tourRequest = new TourRequest(3959.0, places, 0.99, "vincenty");
        tourRequest.buildResponse();

        optimizedPlaces = tourRequest.getPlaces();

        assertEquals(place_0, optimizedPlaces.get(0));
        assertEquals(place_3, optimizedPlaces.get(2));
    }

    @Test
    @DisplayName("ayushad: test correct request type in tour")
    public void testTourRequestType() {
        tourRequest = new TourRequest();
        assertEquals("tour", tourRequest.getRequestType());
    }

    @Test
    @DisplayName("ayushad: returns better tour given four corners of rectangle nn test")
    public void testConstructRectangleOneOpt() throws BadRequestException {
        places.add(place_square_SW);
        places.add(place_square_NE);
        places.add(place_square_SE);
        places.add(place_square_NW);

        tourRequest = new TourRequest(earthRadius_km, places, 0.99, "vincenty");
        tourRequest.buildResponse();

        optimizedPlaces = tourRequest.getPlaces();

        assertEquals(place_square_SW, optimizedPlaces.get(0));
        assertEquals(place_square_NW, optimizedPlaces.get(1));
        assertEquals(place_square_NE, optimizedPlaces.get(2));
        assertEquals(place_square_SE, optimizedPlaces.get(3));
    }

    @Test
    @DisplayName("ayushad: returns better tour from five points (from slides)")
    public void testConstructShape01() throws BadRequestException {
        places.add(place_01_point_A);
        places.add(place_01_point_C);
        places.add(place_01_point_D);
        places.add(place_01_point_B);
        places.add(place_01_point_E);

        tourRequest = new TourRequest(earthRadius_km, places, 0.99, "vincenty");
        tourRequest.buildResponse();

        optimizedPlaces = tourRequest.getPlaces();
        
        assertEquals(place_01_point_A, optimizedPlaces.get(0));
        assertEquals(place_01_point_E, optimizedPlaces.get(1));
        assertEquals(place_01_point_D, optimizedPlaces.get(2));
        assertEquals(place_01_point_C, optimizedPlaces.get(3));
        assertEquals(place_01_point_B, optimizedPlaces.get(4));
    }

    @Test
    @DisplayName("ayushad: empty places list returns empty places")
    public void testEmptyTour() throws BadRequestException {
        tourRequest = new TourRequest(1.0, places, 0.01, "cosines");
        tourRequest.buildResponse();
        optimizedPlaces = tourRequest.getPlaces();
        assertTrue(optimizedPlaces.isEmpty());
    }

    @Test
    @DisplayName("ayushad: returns better tour given points of pentagram")
    public void testConstructPentagramOneOpt() throws BadRequestException {
        places.add(place_pentagram_N);
        places.add(place_pentagram_SE);
        places.add(place_pentagram_W);
        places.add(place_pentagram_E);
        places.add(place_pentagram_SW);

        tourRequest = new TourRequest(earthRadius_km, places, 0.99, "haversine");
        tourRequest.buildResponse();

        optimizedPlaces = tourRequest.getPlaces();

        assertEquals(place_pentagram_N, optimizedPlaces.get(0));
        assertEquals(place_pentagram_W, optimizedPlaces.get(1));
        assertEquals(place_pentagram_SW, optimizedPlaces.get(2));
        assertEquals(place_pentagram_SE, optimizedPlaces.get(3));
        assertEquals(place_pentagram_E, optimizedPlaces.get(4));
    }
}