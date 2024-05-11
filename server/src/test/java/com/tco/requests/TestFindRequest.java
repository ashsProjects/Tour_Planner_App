package com.tco.requests;

import java.util.Arrays;
import java.util.ArrayList;
import com.tco.misc.BadRequestException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

public class TestFindRequest {
    private FindRequest request;
    private Places placesReturned;
    private Integer foundPlaces;

    @Test
    @DisplayName("ayushad: match is empty")
    public void testMatchEmpty() throws BadRequestException, Exception {
        request = new FindRequest("", null, null, 1000);
        request.buildResponse();
        placesReturned = request.getPlaces();
        foundPlaces = request.getFound();
        assertTrue(placesReturned.size() == 100);
        assertTrue(foundPlaces == 50427);
    }

    @Test
    @DisplayName("ayushad: match has random letters")
    public void testMatchRandom() throws BadRequestException, Exception {
        request = new FindRequest("adgfda", null, null, 15);
        request.buildResponse();
        placesReturned = request.getPlaces();
        foundPlaces = request.getFound();
        assertTrue(placesReturned.size() == 0);
        assertTrue(foundPlaces == 0);
    }

    @Test
    @DisplayName("ayushad: limit is 0")
    public void testLimit0() throws BadRequestException, Exception {
        request = new FindRequest("farm", null, null, 0);
        request.buildResponse();
        placesReturned = request.getPlaces();
        foundPlaces = request.getFound();
        assertTrue(placesReturned.size() == 100);
        assertTrue(foundPlaces == 863);
    }

    @Test
    @DisplayName("ayushad: matches farm with no type or where")
    public void testMatchFarm() throws BadRequestException, Exception {
        request = new FindRequest("farm", null, null, 5);
        request.buildResponse();
        placesReturned = request.getPlaces();
        foundPlaces = request.getFound();
        assertEquals("Hayenga's Cant Find Farms Airport", placesReturned.get(0).get("name"));
        assertEquals("32.38759994506836", placesReturned.get(4).get("latitude"));
    }

    @Test
    @DisplayName("ayushad: matches dave in United States")
    public void testMatchDaveUS() throws BadRequestException, Exception {
        request = new FindRequest("DAVE", null, new ArrayList<String>(Arrays.asList("United States")), 5);
        request.buildResponse();
        placesReturned = request.getPlaces();
        foundPlaces = request.getFound();
        assertTrue(foundPlaces == 22);
        assertEquals("Dave's Airport", placesReturned.get(0).get("name"));
    }

    @Test
    @DisplayName("ayushad: matches all airports with green")
    public void testAirportsGreen() throws BadRequestException, Exception {
        request = new FindRequest("green", new ArrayList<String>(Arrays.asList("airport")), null, 15);
        request.buildResponse();
        placesReturned = request.getPlaces();
        foundPlaces = request.getFound();
        assertTrue(foundPlaces == 215);
        assertEquals("Frost Field", placesReturned.get(4).get("name"));
    }

    @Test
    @DisplayName("ayushad: matches all airports and others in Canada and Russia with red")
    public void testMultipleTypeWhere() throws BadRequestException, Exception {
        request = new FindRequest("red", new ArrayList<String>(Arrays.asList("airport", "other")), new ArrayList<String>(Arrays.asList("Canada", "Russia")), 10);
        request.buildResponse();
        placesReturned = request.getPlaces();
        foundPlaces = request.getFound();
        assertTrue(foundPlaces == 27);
        assertEquals("Bredenbury Airport", placesReturned.get(0).get("name"));
        assertEquals("Canada", placesReturned.get(9).get("country"));
    }

    @Test
    @DisplayName("ayushad: testing airports in Fort Collins")
    public void testAirportsFortCollins() throws BadRequestException, Exception {
        request = new FindRequest("Fort Collins", new ArrayList<String>(Arrays.asList("airport")), new ArrayList<String>(Arrays.asList("United States")), 10);
        request.buildResponse();
        placesReturned = request.getPlaces();
        foundPlaces = request.getFound();
        assertTrue(foundPlaces == 5);
        assertEquals("Wkr Airport", placesReturned.get(0).get("name"));
        assertEquals("United States", placesReturned.get(4).get("country"));
    }

    @Test
    @DisplayName("ayushad: testing Boise no filters")
    public void testGeneralBoise() throws BadRequestException, Exception {
        request = new FindRequest("Boise", null, new ArrayList<String>(Arrays.asList("United States")), 10);
        request.buildResponse();
        placesReturned = request.getPlaces();
        foundPlaces = request.getFound();
        assertTrue(foundPlaces == 7);
        assertEquals("St Luke's Boise Medical Center Heliport", placesReturned.get(1).get("name"));
        assertEquals("Boise", placesReturned.get(0).get("municipality"));
    }

    @Test
    @DisplayName("ayushad: test search with and without country specified")
    public void testDifferencesInResults() throws BadRequestException, Exception {
        request = new FindRequest("Colorado", null, new ArrayList<String>(Arrays.asList("United States")), 10);
        request.buildResponse();
        Integer foundPlacesWithCountry = request.getFound();
        request = new FindRequest("Colorado", null, null, 10);
        request.buildResponse();
        Integer foundPlacesWithoutCountry = request.getFound();
        assertTrue(foundPlacesWithCountry <=  foundPlacesWithoutCountry);
    }

    @Test
    @DisplayName("ayushad: test with a limit and without return the same number of found")
    public void testLimitDoesntEffectFound() throws BadRequestException, Exception {
        request = new FindRequest("Colorado", null, null, 0);
        request.buildResponse();
        int foundPlacesWithoutLimit = request.getFound();
        request = new FindRequest("Colorado", null, null, 10);
        request.buildResponse();
        int foundPlacesWithLimit10 = request.getFound();
        request = new FindRequest("Colorado", null, null, 50);
        request.buildResponse();
        int foundPlacesWithLimit50 = request.getFound();
        request = new FindRequest("Colorado", null, null, 99);
        request.buildResponse();
        int foundPlacesWithLimit99 = request.getFound();

        assertTrue(foundPlacesWithoutLimit == foundPlacesWithLimit10); 
        assertTrue(foundPlacesWithLimit50 == foundPlacesWithLimit99);
        assertTrue(foundPlacesWithoutLimit == foundPlacesWithLimit99);
    }

    @Test
    @DisplayName("ayushad: test limit below bounds")
    public void testLimitBelowBounds() throws BadRequestException, Exception {
        request = new FindRequest("", null, null, -1);
        request.buildResponse();
        placesReturned = request.getPlaces();
        assertTrue(request.getFound() == 50427 && placesReturned.size() == 100);
    }

    @Test
    @DisplayName("ayushad: test limit above bounds")
    public void testLimitAboveBounds() throws BadRequestException, Exception {
        request = new FindRequest("", null, null, 101);
        request.buildResponse();
        placesReturned = request.getPlaces();
        assertTrue(request.getFound() == 50427 && placesReturned.size() == 100);
    }

    @Test
    @DisplayName("ayushad: match is non-alphanumeric characters")
    public void testNonAlphanumericChars() throws BadRequestException, Exception {
        request = new FindRequest("#$^&#((@&!^))", null, null, 100);
        request.buildResponse();
        placesReturned = request.getPlaces();
        assertTrue(request.getFound() == 0 && placesReturned.size() == 0);
    }
    @Test
    @DisplayName("ayushad: Test when match is null")
    public void testMatchNull() throws BadRequestException, Exception {
        request = new FindRequest(null, null, null, 100);
        request.buildResponse();
        placesReturned = request.getPlaces();
        foundPlaces = request.getFound();
        assertEquals(4, placesReturned.size());
        assertEquals(4, foundPlaces);
    }
}
