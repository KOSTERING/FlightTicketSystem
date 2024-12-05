package com.group17.flightticket.entity;
import lombok.Data;


/**
 * The {@code Insurance} class represents an insurance policy purchased by a passenger.
 * It includes details such as the policy number, coverage amount, type of insurance,
 * and the passenger associated with the policy.
 * This class provides functionality to store and retrieve insurance details.
 */
@Data
public class Insurance {
    private String policyNumber;
    private double coverageAmount;
    private Passenger insuredPassenger;
    private String insuranceType;

    /**
     * Constructor for the Insurance class.
     *
     * @param policyNumber     The policy number of the insurance.
     * @param coverageAmount   The total coverage amount.
     * @param insuredPassenger The passenger who purchased the insurance.
     * @param insuranceType    The type of insurance.
     */
    public Insurance(String policyNumber, double coverageAmount, Passenger insuredPassenger, String insuranceType) {
        this.policyNumber = policyNumber;
        this.coverageAmount = coverageAmount;
        this.insuredPassenger = insuredPassenger;
        this.insuranceType = insuranceType;
    }

    /**
     * Displays details of the insurance policy.
     */
    public void displayInsuranceDetails() {
        System.out.println("Insurance Policy: " + policyNumber);
        System.out.println("Type: " + insuranceType);
        System.out.println("Coverage Amount: $" + coverageAmount);
        System.out.println("Insured Passenger: " + insuredPassenger.getName());
    }
}
