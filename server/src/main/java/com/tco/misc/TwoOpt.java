package com.tco.misc;

import java.util.Arrays;
import com.tco.requests.Places;

public class TwoOpt extends TourConstruction {
    @Override
    public void improve(int[] route) {
        int[] routePlus = Arrays.copyOf(route, route.length+1);
        routePlus[route.length] = route[0];

        boolean improvement = true;
        while (improvement) {
            improvement = improveHelper(route.length, routePlus);
        }
        System.arraycopy(routePlus, 0, route, 0, route.length);
    }

    private boolean improveHelper(int size, int[] routePlus) {
        boolean improvement = false;
        for (int i = 0; i <= size-3; i++) {
            for (int j = i+2; j <= size-1; j++) {
                improvement = checkIfImproves(improvement, routePlus, i, j);
            }
        }
        return improvement;
    }

    private boolean checkIfImproves(boolean improvement, int[] routePlus, int i, int j) {
        if (twoOptImproves(routePlus, i, j)) {
            twoOptReverse(routePlus, i+1, j);
            improvement = true;
        }
        return improvement;
    }

    private boolean twoOptImproves(int[] route, int i, int j) {
        long legDisij = distanceMatrix.getValue(route[i], route[j]);
        long legDisi1j1 = distanceMatrix.getValue(route[i+1], route[j+1]);
        long legDisii1 = distanceMatrix.getValue(route[i], route[i+1]);
        long legDisjj1 = distanceMatrix.getValue(route[j], route[j+1]);
        return ((legDisij + legDisi1j1) < (legDisii1 + legDisjj1));
    }

    protected void twoOptReverse(int[] route, int i, int j) {
        while (i < j) {
            int temp = route[i];
            route[i] = route[j];
            route[j] = temp;
            i++; j--;
        }
    }
}
