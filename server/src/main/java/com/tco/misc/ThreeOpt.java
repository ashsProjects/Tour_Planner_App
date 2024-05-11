package com.tco.misc;

import com.tco.requests.Places;
import java.util.Arrays;

public class ThreeOpt extends TourConstruction {
    private final TwoOpt twoOpt = new TwoOpt();
    private int[] routePlus;

    @Override
    public void improve(int[] route) {
        routePlus = Arrays.copyOf(route, route.length+1);
        routePlus[route.length] = route[0];

        boolean improvement = true;
        while (improvement) {
            improvement = improveHelper(route.length);
        }
        System.arraycopy(routePlus, 0, route, 0, route.length);
    }

    private boolean improveHelper(int n) {
        boolean enhancement = false;
        for (int i = 0; i < n-3; ++i) {
            for (int j = i+1; j < n-2; ++j) {
                enhancement = improveHelperHelper(enhancement, i, j, n);
            }
        }
        return enhancement;
    }

    private boolean improveHelperHelper(boolean enhancement, int i, int j, int n) {
        for (int k = j+1; k < n-1; ++k) {
            enhancement = reverseability(i, j, k);
        }
        return enhancement;
    }

    private boolean reverseability(int i, int j, int k) {
        int reversals = threeOptReverse(i, j, k);
        if (threeOptReverseI1J(reversals)) twoOpt.twoOptReverse(routePlus, i+1, j);
        if (threeOptReverseJ1K(reversals)) twoOpt.twoOptReverse(routePlus, j+1, k);
        if (threeOptReverseI1K(reversals)) twoOpt.twoOptReverse(routePlus, i+1, k);
        if (reversals > 0) return true;
        return false;
    }

    private int threeOptReverse(int i, int j, int k) {      
        int type = 0;
        int[][] routes={{i, (i+1), j, (j+1), k, (k+1)},
                        {i, j, (i+1), (j+1), k, (k+1)},
                        {i, (i+1), j, k, (j+1), (k+1)},
                        {i, j, (i+1), k, (j+1), (k+1)},
                        {i, k, (j+1), j, (i+1), (k+1)},
                        {i, k, (j+1), (i+1), j, (k+1)},
                        {i, (j+1), k, j, (i+1), (k+1)},
                        {i, (j+1), k, (i+1), j, (k+1)}
                    };

        long lowest = threeOptReversalHelper(routes[0]);
        for (int e = 1; e < routes.length; e++) {
            long current = threeOptReversalHelper(routes[e]);
            if (current < lowest) {lowest = current; type = e;}
        }
        return type;
    }

    private long threeOptReversalHelper(int[] curr) {
        long newDistance = distanceMatrix.getValue(routePlus[curr[0]], routePlus[curr[1]]);
        newDistance += distanceMatrix.getValue(routePlus[curr[2]], routePlus[curr[3]]);
        newDistance += distanceMatrix.getValue(routePlus[curr[4]], routePlus[curr[5]]);
        return newDistance;
    }

    private boolean threeOptReverseI1J(int reversals) {
        return (reversals & 0b001) > 0;
    }

    private boolean threeOptReverseJ1K(int reversals) {
        return (reversals & 0b010) > 0;
    }

    private boolean threeOptReverseI1K(int reversals) {
        return (reversals & 0b100) > 0;
    }
}

