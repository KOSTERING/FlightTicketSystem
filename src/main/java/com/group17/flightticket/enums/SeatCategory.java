package com.group17.flightticket.enums;
public enum SeatCategory {
    ECONOMY(100.0),
    PREMIUM_ECONOMY(150.0),
    BUSINESS(300.0),
    FIRST_CLASS(500.0);

    private final double baseFee;

    SeatCategory(double baseFee) {
        this.baseFee = baseFee;
    }

    public double getBaseFee() {
        return baseFee;
    }
}