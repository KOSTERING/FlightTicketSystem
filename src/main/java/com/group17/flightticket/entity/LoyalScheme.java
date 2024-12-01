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
    /**
     * Adds loyalty points to a specified passenger's account.
     * If the passenger does not exist, they will be added with the specified points.
     *
     * @param passengerName The name of the passenger.
     * @param pointsToAdd The number of points to be added to the passenger's account.
     */
    public void addPoints(String passengerName, int pointsToAdd) {
        loyaltyPoints.put(passengerName, loyaltyPoints.getOrDefault(passengerName, 0) + pointsToAdd);
    }
    /**
     * Deducts loyalty points from a specified passenger's account.
     * The points will not go below zero (i.e., no negative points).
     *
     * @param passengerName The name of the passenger.
     * @param pointsToDeduct The number of points to be deducted from the passenger's account.
     */
    public void deductPoints(String passengerName, int pointsToDeduct) {
        int currentPoints = loyaltyPoints.getOrDefault(passengerName, 0);
        loyaltyPoints.put(passengerName, Math.max(currentPoints - pointsToDeduct, 0));
    }
    /**
     * Retrieves the current loyalty points balance of a specified passenger.
     * If the passenger does not have any points, returns 0.
     *
     * @param passengerName The name of the passenger.
     * @return The current points balance for the passenger.
     */
    public int getPoints(String passengerName) {
        return loyaltyPoints.getOrDefault(passengerName, 0);
    }
    /**
     * Redeems loyalty points for a flight fee discount.
     * The passenger can redeem points equal to the flight fee, but not exceeding their current points balance.
     * The remaining balance of the flight fee after applying the discount is returned.
     *
     * @param passengerName The name of the passenger.
     * @param flightFee The total flight fee.
     * @return The remaining flight fee after applying the discount.
     */
    public double redeemPoints(String passengerName, double flightFee) {
        int currentPoints = loyaltyPoints.getOrDefault(passengerName, 0);
        double discount = Math.min(currentPoints, flightFee);
        loyaltyPoints.put(passengerName, currentPoints - (int) discount);
        return flightFee - discount;
    }
}