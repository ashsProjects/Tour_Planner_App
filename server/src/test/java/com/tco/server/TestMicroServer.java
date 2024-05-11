package com.tco.server;

import java.net.ServerSocket;
import org.json.JSONObject;
import org.json.JSONArray;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;

import spark.Spark;

public class TestMicroServer {

    public final static int TEST_SERVER_PORT = getAvailablePort();
    public final static String BASE_URL = "http://localhost:" + TEST_SERVER_PORT;

    private static int getAvailablePort(){
        try ( ServerSocket socket = new ServerSocket(0)) {
            return socket.getLocalPort();
        } catch (Exception E) {
            return 8000;
        }
    }

    @BeforeAll
    public static void startTheMicroServer() {
        new MicroServer(TEST_SERVER_PORT);
    }

    @BeforeEach
    public void WaitForMicroServerToBeReady() {
        // make sure spark is started before making the request
        Spark.awaitInitialization();
    }

    @AfterAll
    public static void stopTheMicroServer() {
        Spark.stop();
        Spark.awaitStop();
    }

    private static HttpResponse postRequest(String endPointPath, String requestBodyJSON) throws IOException {
        HttpPost request = new HttpPost(BASE_URL + endPointPath);
        request.setEntity(new StringEntity(requestBodyJSON, ContentType.APPLICATION_JSON));
        HttpClient httpClient = HttpClientBuilder.create().build();
        return httpClient.execute(request);
    }

    @Test
    @DisplayName("base: Valid config request succeeds with 200 status")
    public void testValidConfigRequest() throws IOException {
        String requestBodyJSON = new JSONObject()
            .put("requestType", "config")
            .toString();
        HttpResponse response = postRequest("/api/config", requestBodyJSON);
        assertEquals(200, response.getStatusLine().getStatusCode());
    }

    @Test
    @DisplayName("base: An invalid request responds with 400 status")
    public void testInvalidRequest() throws IOException {
        String invalidRequestJSON = "{ }";
        HttpResponse response = postRequest("/api/config", invalidRequestJSON);
        assertEquals(400, response.getStatusLine().getStatusCode());
    }

    @Test
    @DisplayName("ayushad: Valid distances request succeeds with 200 status")
    public void testValidDistancesRequest() throws IOException {
        JSONArray places = new JSONArray();
        JSONObject object  = new JSONObject();
        object.put("latitude", "-90.00");
        object.put("longitude", "0.00");
        places.put(object);

        String requestBodyJSON = new JSONObject()
            .put("requestType", "distances")
            .put("places", places)
            .put("earthRadius", 3959)
            .put("formula", "cosines")
            .toString();
        HttpResponse response = postRequest("/api/distances", requestBodyJSON);
        assertEquals(200, response.getStatusLine().getStatusCode());
    }

    @Test
    @DisplayName("ayushad: An invalid distances request responds with 400 status")
    public void testInvalidDistancesRequest() throws IOException {
        String invalidRequestJSON = "{ }";
        HttpResponse response = postRequest("/api/distances", invalidRequestJSON);
        assertEquals(400, response.getStatusLine().getStatusCode());
    }

    @Test
    @DisplayName("ayushad: Valid tour request succeeds with 200 status")
    public void testValidTourRequest() throws IOException {
        JSONArray places = new JSONArray();
        JSONObject object  = new JSONObject();
        object.put("latitude", "-90.00");
        object.put("longitude", "0.00");
        places.put(object);

        String requestBodyJSON = new JSONObject()
            .put("requestType", "tour")
            .put("places", places)
            .put("earthRadius", 3959)
            .put("response", 0.9)
            .put("formula", "vincenty")
            .toString();
        HttpResponse response = postRequest("/api/tour", requestBodyJSON);
        assertEquals(200, response.getStatusLine().getStatusCode());
    }

    @Test
    @DisplayName("base: An invalid endpoint responds with 404 status")
    public void testInvalidEndpoint() throws IOException {
        String invalidRequestJSON = "{ }";
        HttpResponse response = postRequest("/api/invalid", invalidRequestJSON);
        assertEquals(404, response.getStatusLine().getStatusCode());
    }

    @Test
    @DisplayName("ayushad: Valid find request succeeds with 200 status")
    public void testValidFindRequest() throws IOException, Exception {
        JSONArray countries = new JSONArray();
        countries.put("Sweden");
        JSONArray types = new JSONArray();
        types.put("balloonport");

        String requestBodyJSON = new JSONObject()
            .put("requestType", "find")
            .put("match", "")
            .put("type", types)
            .put("where", countries)
            .put("limit", 50)
            .toString();
        HttpResponse response = postRequest("/api/find", requestBodyJSON);
        assertEquals(200, response.getStatusLine().getStatusCode());
    }

    @Test
    @DisplayName("ayushad: An invalid find request responds with 400 status")
    public void testInvalidFindRequest() throws IOException {
        String invalidRequestJSON = "{ }";
        HttpResponse response = postRequest("/api/find", invalidRequestJSON);
        assertEquals(400, response.getStatusLine().getStatusCode());
    }
}