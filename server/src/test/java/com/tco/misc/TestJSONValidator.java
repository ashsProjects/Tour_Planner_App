package com.tco.misc;

import com.tco.requests.ConfigRequest;
import com.tco.requests.DistancesRequest;
import com.tco.requests.TourRequest;
import java.lang.reflect.Type;

import org.everit.json.schema.SchemaException;
import org.everit.json.schema.loader.SchemaLoader;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.mockito.MockedStatic;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


public class TestJSONValidator {

    private void test(String request, Type type, boolean valid) {
        try {
            JSONValidator.validate(request, type);
            assertTrue(valid);
        } catch ( Exception e ) {
            assertFalse(valid);
        }
    }

    @Test
    @DisplayName("base: Config request should fail schema validation")
    public void testConfigRequestFail() {
        test("{}", ConfigRequest.class, false);
    }

    @Test
    @DisplayName("base: Distances request should fail schema validation")
    public void testDistancesRequestFail() {
        test("{}", DistancesRequest.class, false);
    }

    @Test
    @DisplayName("base: Tour request should fail schema validation")
    public void testTourRequestFail() {
        test("{}", TourRequest.class, false);
    }

    @Test
    @DisplayName("base: Config request should pass schema validation")
    public void testConfigRequestPass() {
        test("{\"requestType\":\"config\",\"features\":[\"config\"]}", ConfigRequest.class, true);
    }

    @Test
    @DisplayName("base: Distances request should pass schema validation")
    public void testDistancesRequestPass() {
        String requestFormat = "{\"requestType\":\"distances\"," + 
            "\"places\":[{\"name\":\"place1\", \"latitude\":\"40.6\", \"longitude\":\"-105.1\"}]," + 
            "\"earthRadius\":100}";
        test(requestFormat, DistancesRequest.class, true);
    }

    @Test
    @DisplayName("base: Tour request should pass schema validation")
    public void testTourRequestPass() {
        String requestFormat = "{\"requestType\":\"tour\"," + 
            "\"earthRadius\":100," + 
            "\"response\":1," +
            "\"places\":[{\"name\":\"place1\", \"latitude\":\"45.00\", \"longitude\":\"-10.00\"}]}";
        test(requestFormat, TourRequest.class, true);
    }

    @Test
    @DisplayName("base: There should be no schema for the JSONValidator class")
    public void testMissingSchema() {
        test("", JSONValidator.class, false);
    }

    @Test
    @DisplayName("base: An invalid schema results in validate() failing")
    public void testInvalidSchema() {
        try (MockedStatic<SchemaLoader> mockedSchemaLoader = mockStatic(SchemaLoader.class)) {
            mockedSchemaLoader.when(() -> SchemaLoader.load(any(JSONObject.class)))
                    .thenThrow(SchemaException.class);

            test("{\"requestType\":\"config\"}", ConfigRequest.class, false);
        }
    }
}
