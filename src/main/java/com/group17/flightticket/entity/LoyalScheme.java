package com.group17.flightticket.entity;

import java.util.HashMap;

/**
 *The LoyalScheme class represents a loyalty program for passengers, allowing the management of points.
 *It provides methods to add points, deduct points, retrieve points balance, and redeem points for flight fee discounts.
 */
public class LoyalScheme {
    //A map to store loyalty points for each passenger
    private HashMap<String, Integer> loyaltyPoints;

    /**
     * Constructs a new LoyalScheme instance
     * Initializes an empty map to store Loyalty points
     */
    public LoyalScheme() {
        this.loyaltyPoints = new HashMap<>();
    }

    public void addPointsV2(Passenger passenger, int pointsToAdd) {
        loyaltyPoints.put(passenger.getName(), loyaltyPoints.getOrDefault(passenger.getName(), 0) + pointsToAdd);
    }

    public void deductPointsV2(Passenger passenger, int pointsToDeduct) {
        int currentPoints = loyaltyPoints.getOrDefault(passenger.getName(), 0);
        loyaltyPoints.put(passenger.getName(), Math.max(currentPoints - pointsToDeduct, 0));
    }

    public int getPointsV2(Passenger passenger) {
        return loyaltyPoints.getOrDefault(passenger.getName(), 0);
    }

    public double redeemPointsV2(Passenger passenger, double flightFee) {
        int currentPoints = loyaltyPoints.getOrDefault(passenger.getName(), 0);
        double discount = Math.min(currentPoints, flightFee);
        loyaltyPoints.put(passenger.getName(), currentPoints - (int) discount);
        return flightFee - discount;
    }
}