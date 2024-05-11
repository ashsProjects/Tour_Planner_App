package com.tco.requests;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import com.tco.misc.GeographicLocations;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConfigRequest extends Request {

    private static final transient Logger log = LoggerFactory.getLogger(ConfigRequest.class);
    private String serverName;
    private List<String> features;
    private List<String> formulae;
    private List<String> type;
    private List<String> where;

    @Override
    public void buildResponse() throws Exception {
        serverName = "t12 Git Gud";

        features = new ArrayList<>();
        features.add("config");
        features.add("distances");
        features.add("tour");
        features.add("find");
        features.add("where");
        features.add("type");
        features.add("near");
        

        formulae = new ArrayList<>(Arrays.asList("vincenty", "haversine", "cosines"));
        GeographicLocations geoLoc = new GeographicLocations();
        where = geoLoc.countries();
        type = new ArrayList<>(Arrays.asList("airport", "heliport", "balloonport", "other"));

        log.trace("buildResponse -> {}", this);
    }

  /* The following methods exist only for testing purposes and are not used
  during normal execution, including the constructor. */

    public ConfigRequest() {
        this.requestType = "config";
    }

    public String getServerName() {
        return serverName;
    }

    public boolean validFeature(String feature){
        return features.contains(feature);
    }
}
