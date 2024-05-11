package com.tco.requests;

import java.util.List;
import java.util.ArrayList;

import com.tco.misc.GeographicLocations;
import com.tco.misc.BadRequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FindRequest extends Request {
    private static final transient Logger log = LoggerFactory.getLogger(FindRequest.class);
    private String match;
    private List<String> type;
    private List<String> where;
    private Integer limit;
    private Integer found;
    private Places places;

    public FindRequest() {
        super.requestType = "find";
    }

    public FindRequest(String m, List<String> t, List<String> w, Integer limit) {
        this.limit = limit;
        this.match = m;
        this.type = t;
        this.where = w;
        super.requestType = "find";
    }

    @Override
    public void buildResponse() throws BadRequestException, Exception {
        GeographicLocations geoLoc = new GeographicLocations(match , type, where, limit);
        places = geoLoc.find();
        found = geoLoc.found();
        log.trace("buildResponse -> {}", this);
    }

    public Places getPlaces() {
        return this.places;
    }

    public Integer getFound() {
        return this.found;
    }
}
