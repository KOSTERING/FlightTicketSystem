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
     * Adds loyalty Points to given passenger's account.
     *
     * @param passenger The passenger to whom points will be added.
     * @param pointsToAdd The number of points to add.
     */
    public void addPointsV2(Passenger passenger, int pointsToAdd) {
        loyaltyPoints.put(passenger.getName(), loyaltyPoints.getOrDefault(passenger.getName(), 0) + pointsToAdd);
    }

    /**
     * Deducts loyalty points from the given passenger's account.If the passenger doesn't have enough points,the points will be reduced to zero.
     * @param passenger The passenger whose points will be deducted.
     * @param pointsToDeduct The number of points to deduct.
     */
    public void deductPointsV2(Passenger passenger, int pointsToDeduct) {
        int currentPoints = loyaltyPoints.getOrDefault(passenger.getName(), 0);
        loyaltyPoints.put(passenger.getName(), Math.max(currentPoints - pointsToDeduct, 0));
    }

    /**
     * Gets the current loyalty points of the given passenger.
     *
     * @param passenger The passenger whose points will be received.
     * @return The number of loyalty points the passenger currently has.
     */
    public int getPointsV2(Passenger passenger) {
        return loyaltyPoints.getOrDefault(passenger.getName(), 0);
    }

    /**
     *Redeems loyalty points for a discount on the flight fee.The discount is limited to the number of points available,but cannot exceed the flight fee.
     * @param passenger The passenger whose loyalty points will be redeemed.
     * @param flightFee The total flight fee before the discount.
     * @return The new flight fee after reducing the discount.
     */
    public double redeemPointsV2(Passenger passenger, double flightFee) {
        int currentPoints = loyaltyPoints.getOrDefault(passenger.getName(), 0);
        double discount = Math.min(currentPoints, flightFee);
        loyaltyPoints.put(passenger.getName(), currentPoints - (int) discount);
        return flightFee - discount;
    }
}