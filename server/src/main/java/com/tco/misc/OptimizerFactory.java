package com.tco.misc;

public class OptimizerFactory {
    //create a singleton which gets returned
    private static OptimizerFactory instance = new OptimizerFactory();

    //prevents OptimizerFactory instances from being created
    private OptimizerFactory() {};

    public static OptimizerFactory getInstance() {
        return instance;
    }

    public TourConstruction get(int N) throws BadRequestException {
        switch(N) {
            case 0:
                return new NoOpt();
            case 1:
                return new OneOpt();
            case 2:
                return new TwoOpt();
            case 3:
                return new ThreeOpt();
            default:
                throw new BadRequestException();

        }
    }
}
