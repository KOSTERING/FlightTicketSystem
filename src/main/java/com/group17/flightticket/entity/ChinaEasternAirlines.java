package com.group17.flightticket.entity;

import static com.group17.flightticket.utils.FlightConstants.FLIGHT_CAPACITY_TRRESHOLD;

/**
 * Represents the China Eastern Airlines, a subclass of AirlineCompany.
 * This class includes specific behaviors and properties for managing
 * flights and inventory reports for China Eastern Airlines.
 */
public class ChinaEasternAirlines extends AirlineCompany {
    /**
     * Constructor to create a new instance of China Eastern Airlines.
     * Initializes the airline with the company name "China Eastern Airlines".
     */
    public ChinaEasternAirlines() {
        super("China Eastern Airlines");
    }
    /**
     * Generates and prints the inventory report for the airline.
     * The report includes flight details such as flight number, origin, destination,
     * remaining seat count, and alerts if a flight is nearing full capacity.
     */
    @Override
    public void inventory() {
        System.out.println("Inventory Report for " + getCompanyName() + ":");
        for (Flight flight : getFlights()) {
            int availableSeats = flight.getRemainSeatCount();
            System.out.println("Flight " + flight.getFlightNumber() + " from " + flight.getOrigin().getLocation() +
                               " to " + flight.getDestination().getLocation() + " has " + availableSeats +
                               " seats remaining.");
            if (availableSeats <= FLIGHT_CAPACITY_TRRESHOLD) {
                System.out.println("  * Attention: This flight is nearing full capacity!");
            }
        }
    }
}