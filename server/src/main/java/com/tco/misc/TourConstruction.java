package com.tco.misc;

import com.tco.requests.Places;

public abstract class TourConstruction {
    protected int[] lowestTour;
    protected long lowestTotalDistance;
    protected DistanceMatrix distanceMatrix;

    public Places construct(Places places, Double radius, String formula, Double response) throws BadRequestException {
        distanceMatrix = new DistanceMatrix(places.size());
        distanceMatrix.fillMatrix(places, radius, formula);

        int[] tour = new int[places.size()];
        resetTour(tour, places.size());
        lowestTotalDistance = sumDistances(tour);
        lowestTour = tour.clone();
        
        for (int i = 0; i < tour.length; i++) {
            createTour(i, tour);
            improve(tour);
            long currDistance = sumDistances(tour);
            if (currDistance < lowestTotalDistance) {
                lowestTotalDistance = currDistance;
                lowestTour = tour.clone();
            }
        }

        Places placesToReturn = new Places();
        int indexOf0 = findIndexOfValue(lowestTour, 0);
        for (int i = indexOf0; i < (lowestTour.length + indexOf0); i++) {
            int currIndex = i % lowestTour.length;
            placesToReturn.add(places.get(lowestTour[currIndex]));
        }

        return placesToReturn;
    }

    private void createTour(int start, int[] tour) {
        swapIndices(tour, start, 0);
        for (int i = 0; i < tour.length - 1; i++) {
            int lowestIndex = distanceMatrix.findLowestDistance(i, tour);
            if (lowestIndex == -1) break;
            swapIndices(tour, lowestIndex, i+1);
        }
    }

    private void swapIndices(int[] tour, int valToSwap, int newIndex) {
        int indexOfValueToSwap = findIndexOfValue(tour, valToSwap);
        int temp = tour[newIndex];
        tour[newIndex] = tour[indexOfValueToSwap];
        tour[indexOfValueToSwap] = temp;
    }

    private int findIndexOfValue(int[] tour, int val) {
        int indexOfVal = 0;
        for (int i = 0; i < tour.length; i++) {
            if (tour[i] == val) {
                indexOfVal = i;
                break;
            }
        }
        return indexOfVal;
    }

    private long sumDistances(int[] tour) {
        long sum = 0;
        for (int i = 0; i < tour.length; i++) {
            int from = tour[i];
            int to = tour[(i + 1) % tour.length];
            sum += distanceMatrix.getValue(from, to);
        }
        return sum;
    }

    private void resetTour(int[] tour, int size) {
        for (int i = 0; i < size; i++) {
            tour[i] = i;
        }
    }

    public void improve(int[] route) {} //empty method to be overloaded
}