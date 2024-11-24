package com.group17.flightticket.enums;
/**
 * The SeatCategory enum represents various seat categories available on a flight.
 * Each seat category is associated with a base fee for the reservation.
 */
public enum SeatCategory {
    ECONOMY(100.0),
    PREMIUM_ECONOMY(150.0),
    BUSINESS(300.0),
    FIRST_CLASS(500.0);

    private final double baseFee;
    /**
     * Constructs a SeatCategory with the specified base fee.
     *
     * @param baseFee The base fee associated with the seat category.
     */
    SeatCategory(double baseFee) {
        this.baseFee = baseFee;
    }
    /**
     * Retrieves the base fee associated with the seat category.
     *
     * @return The base fee of the seat category.
     */
    public double getBaseFee() {
        return baseFee;
    }
}