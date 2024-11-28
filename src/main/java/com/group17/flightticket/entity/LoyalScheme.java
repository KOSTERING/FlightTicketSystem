package com.group17.flightticket.entity;

import java.util.HashMap;

public class LoyalScheme {
    private HashMap<String, Integer> loyaltyPoints;

    public LoyalScheme() {
        this.loyaltyPoints = new HashMap<>();
    }

    public void addPoints(String passengerName, int pointsToAdd) {
        loyaltyPoints.put(passengerName, loyaltyPoints.getOrDefault(passengerName, 0) + pointsToAdd);
    }

    public void deductPoints(String passengerName, int pointsToDeduct) {
        int currentPoints = loyaltyPoints.getOrDefault(passengerName, 0);
        loyaltyPoints.put(passengerName, Math.max(currentPoints - pointsToDeduct, 0));
    }

    public int getPoints(String passengerName) {
        return loyaltyPoints.getOrDefault(passengerName, 0);
    }

    public double redeemPoints(String passengerName, double flightFee) {
        int currentPoints = loyaltyPoints.getOrDefault(passengerName, 0);
        double discount = Math.min(currentPoints, flightFee);
        loyaltyPoints.put(passengerName, currentPoints - (int) discount);
        return flightFee - discount;
    }
}