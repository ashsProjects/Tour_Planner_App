package com.tco.misc;

public class FormulaFactory {
    //create a singleton which gets returned
    private static FormulaFactory instance = new FormulaFactory();

    //prevents FormulaFactory instances from being created
    private FormulaFactory() {};

    public static FormulaFactory getInstance() {
        return instance;
    }

    public GreatCircleDistance get(String formula) throws BadRequestException {
        if (formula == null) return new Vincenty();

        switch (formula) {
            case "vincenty":
                return new Vincenty();
            case "haversine":
                return new Haversine();
            case "cosines":
                return new Cosines();
            default:
                throw new BadRequestException();
        }
    }

}
