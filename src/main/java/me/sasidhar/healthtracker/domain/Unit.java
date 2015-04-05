package me.sasidhar.healthtracker.domain;

/**
 * Created by Sasidhar on 4/4/15.
 */
public enum Unit {

    METRIC_WEIGHT("lb"),
    SI_WEIGHT("Kg"),
    CONVENTIONAL_GLUCOSE("mg/dL"),
    SI_GLUCOSE("mmol/L");

    private String symbol;

    Unit(String symbol) {
        this.symbol = symbol;
    }

    public String getSymbol() {
        return symbol;
    }

    public static Unit lookupBySymbol(String symbol) {
        for(Unit unit : values()) {
            if(unit.symbol.equals(symbol)) {
                return unit;
            }
        }
        return null;
    }
}
