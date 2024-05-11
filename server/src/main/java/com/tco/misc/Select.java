package com.tco.misc;

public class Select {
    private final static String WORLD = "world";
    private final static String COUNTRY = "country";
    private final static String REGION = "region";
    private final static String COLUMNS = "world.id, world.name, world.municipality, region.name as region, "
        + "country.name as country, world.latitude, world.longitude, world.altitude, world.type";
    
    public static String near(double lat, double lon, double normalizedDistance) {
        double delta = Math.min((normalizedDistance / 69 * 2.00), 25);
        double lowerLat = Math.max(lat - delta, -90.0);
        double upperLat = Math.min(lat + delta, 90.0);
        double lowerLon = ((((lon - delta) % 180.0) + 180) % 180);
        double upperLon = ((((lon + delta) % 180.0) - 180) % 180);

        String where = " where latitude between " + lowerLat + " and " + upperLat;
        if (lowerLat <= -90 || upperLat >= 90) where += "";
        else if (lowerLon > upperLon) where += " and (longitude between " + lowerLon + " and 180 or longitude between -180 and " + upperLon + ")";
        else where += " and longitude between " + lowerLon + " and " + upperLon;

        return statement(where, COLUMNS, "");
    }

    public static String find(String match, int limit, String types, String countries) {
        String where = " where (world.id like '%" + match + "%' or world.name like '%" + match + "%'";
        where += " or world.municipality like '%" + match + "%' or region.name like '%" + match + "%'";
        where += " or country.name like '%" + match + "%')";
        if (types != null) where += " and " + WORLD + ".type in (" + types +  ")";
        if (countries != null) where += " and " + COUNTRY + ".name in (" + countries + ")";
        
        if (limit == -1) return statement(where, "count(*) as count", "");
        else return statement(where, COLUMNS, " limit " + limit);
    }

    public static String statement(String where, String data, String limit) {
        String query = "select " + data + " from ";
        query += WORLD + " inner join " + REGION + " on " + WORLD + ".iso_region = " + REGION + ".id";
        query += " inner join " + COUNTRY + " on " + WORLD + ".iso_country = " + COUNTRY + ".id";
        query += where + limit + ";";
        return query;
    }

    public static String getAllCountries() {
        return "select distinct country.name as result from country;";
    }
}
