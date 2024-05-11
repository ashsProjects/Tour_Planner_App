package com.tco.misc;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.tco.requests.Place;
import com.tco.requests.Places;
import com.tco.requests.Distances;

public class GeographicLocations {
    // Initalize for near
    private Place place;
    private Integer distance;
    private Double earthRadius;
    private String formula;
    private Distances distancesInOrder;
    
    // Initalize for Find
    private String match;
    private String type = "";
    private String where = "";
    private Integer limit;

	public GeographicLocations() {}

    public GeographicLocations(Place place, Integer distance, Double earthRadius, String formula, Integer limit) {
		this.place = place;
		this.distance = distance;
		this.earthRadius = earthRadius;
		this.formula = formula;
        this.limit = limit > 100 ? 100 : limit;
        this.distancesInOrder = new Distances();
    }
    
    public GeographicLocations(String match, List<String> type, List<String> where, Integer limit) {
        this.match = match;
		this.limit = (limit > 100 || limit <= 0) ? 100 : limit;
        
        if (type == null) this.type = null;
        else createTypeFilter(type);
        
        if (where != null) {
            for (String w: where) {this.where += "'" + w + "'" + ",";}
            this.where = this.where.substring(0, this.where.length()-1);
        }
        else this.where = null;
    }

    private void createTypeFilter(List<String> type) {
        for (String t: type) {
            if (t.equals("airport")) this.type += "'small_airport','medium_airport','large_airport',";
            else if (t.equals("other")) this.type += "'closed','seaplane_base',";
            else this.type += "'" + t + "'" + ",";
        }
        this.type = this.type.substring(0, this.type.length()-1);
    }

    public Places find() throws Exception {
        String query = Select.find(this.match, this.limit, this.type, this.where);
        Places matchedPlaces = Database.places(query);
        return matchedPlaces;
    }

    public Integer found() throws Exception { 
        String query = Select.find(this.match, -1, this.type, this.where);
        Integer found = Database.found(query);
        return found;
    }

    public Places near() throws Exception {
        double lat = Double.parseDouble(this.place.get("latitude"));
        double lon = Double.parseDouble(this.place.get("longitude"));
        double normalizedDistance = (3959.0 / this.earthRadius) * this.distance;

        String query = Select.near(lat, lon, normalizedDistance);
        Places nearbyPlaces = sortPlaces(Database.places(query));
        return nearbyPlaces;
    }

    public Distances distances() {
        return distancesInOrder;
    }

    private Places sortPlaces(Places places) throws BadRequestException {
        Map<Double, Integer> distancesIndex = new TreeMap<>();
        FormulaFactory factory = FormulaFactory.getInstance();
        GreatCircleDistance distanceCalculator = factory.get(this.formula);

        GeographicCoordinate from = this.place;
        for (int i = 0; i < places.size(); i++) {
            long distance = distanceCalculator.between(from, places.get(i), this.earthRadius);
            if (distance <= this.distance) {
                double dist = distance + (i * 0.0001);
                distancesIndex.put(dist, i);
            }
        }

        return sortHelper(distancesIndex, places);
    }

    private Places sortHelper(Map<Double, Integer> distancesIndex, Places places) {
        Places sortedPlaces = new Places();
        int currLimit = 0;
        for (Map.Entry<Double, Integer> entry: distancesIndex.entrySet()) {
            if (currLimit >= this.limit) break;
            sortedPlaces.add(places.get(entry.getValue()));
            distancesInOrder.add((long) entry.getKey().doubleValue());
            currLimit++;
        }
        return sortedPlaces;
    }

    public List<String> countries() throws Exception {
        String query = Select.getAllCountries();
        return Database.getCountries(query);
    }
}