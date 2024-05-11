package com.tco.requests;

import java.util.ArrayList;

public class Distances extends ArrayList<Long> {   
    public long total() {
        long runningSum = 0L;
        for (Long l: this) {
            runningSum += l;
        }
        return runningSum;
    }
}
