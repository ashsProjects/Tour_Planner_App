package com.tco.misc;

import com.tco.requests.Places;

public class DistanceMatrix {
    private long[] adjMatrix;
    private int dimension;

    public DistanceMatrix(int dimension) {
        this.dimension = dimension;
        this.adjMatrix = new long[dimension * dimension];
    }

    public void setValue(int row, int col, long value) {
        int index = (row * dimension) + col;
        adjMatrix[index] = value;
    }

    public long getValue(int row, int col) {
        int index = (row * dimension) + col;
        return adjMatrix[index];
    }

    public int getSize() {
        return this.adjMatrix.length;
    }

    public int getDimension() {
        return this.dimension;
    }

    public void fillMatrix(Places places, double earthRadius, String formula) throws BadRequestException {
        FormulaFactory factory = FormulaFactory.getInstance();
        GreatCircleDistance distanceCalculator = factory.get(formula);

        for (int i = 0; i < places.size(); i++) {
            for (int j = i; j < places.size() - 1; j++) {
                int toIndex = (j + 1) % places.size();
                
                GeographicCoordinate from = places.get(i);
                GeographicCoordinate to = places.get(toIndex);
                long distance = distanceCalculator.between(from, to, earthRadius);
        
                this.setValue(i, toIndex, distance);
                this.setValue(toIndex, i, distance);
            }
        }
    }

    public int findLowestDistance(int currPosition, int[] tour) {
        long lowest = Long.MAX_VALUE;
        int lowestIndex = -1;

        int currVal = tour[currPosition];
        for (int i = currPosition + 1; i < tour.length; i++) {
            long tourValue = this.getValue(currVal, tour[i]);
            if (tourValue < lowest) {
                lowest = tourValue;
                lowestIndex = tour[i];
            } 
        }

        return lowestIndex;
    }
}