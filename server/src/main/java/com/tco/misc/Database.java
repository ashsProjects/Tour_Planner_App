package com.tco.misc;

import com.tco.requests.Place;
import com.tco.requests.Places;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;
import java.util.ArrayList;

public class Database {
    final static String USER = "cs314-db";
    final static String PASSWORD = "eiK5liet1uej";
    final static String URL = "jdbc:mariadb://faure.cs.colostate.edu/cs314";

    private static ResultSet performQuery(String sql) throws Exception {
        try (
            Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
            Statement query = conn.createStatement();
        ) {
            ResultSet results = query.executeQuery(sql);
            return results;
        } catch (Exception e) {
            throw e;
        }
    }

    public static Integer found(String sql) throws Exception {
	    ResultSet results = performQuery(sql);
        results.next();
        return results.getInt("count");
    }

    public static Places places(String sql) throws Exception {
        ResultSet results = performQuery(sql);
		int count = 0;
		Places places = new Places();

		while (results.next()) {
            Place place = new Place();
            place.put("id", results.getString(1));
            place.put("name", results.getString(2));
            place.put("municipality", results.getString(3));
            place.put("region", results.getString(4));
            place.put("country", results.getString(5));
            place.put("latitude", results.getString(6));
            place.put("longitude", results.getString(7));
            place.put("altitude", results.getString(8));
            place.put("type", results.getString(9));
            place.put("index", String.format("%d", ++count));
            places.add(place);
		}
		return places;
    }

    public static List<String> getCountries(String sql) throws Exception {
        ResultSet results = performQuery(sql);
        List<String> resultList = new ArrayList<>();

        while (results.next()) {
            resultList.add(results.getString(1));
        }
        return resultList;
    }
}
