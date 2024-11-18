package com.group17.flightticket.entity;

public class ChinaEasternAirlines extends AirlineCompany {

    public ChinaEasternAirlines() {
        super("China Eastern Airlines");
    }

    @Override
    public void inventory() {
        System.out.println("Inventory Report for " + getCompanyName() + ":");
        for (Flight flight : getFlights()) {
            int availableSeats = flight.getRemainSeatCount();
            System.out.println("Flight " + flight.getFlightNumber() + " from " + flight.getOrigin() + 
                               " to " + flight.getDestination() + " has " + availableSeats + 
                               " seats remaining.");
            if (availableSeats <= 5) {
                System.out.println("  * Attention: This flight is nearing full capacity!");
            }
        }
    }
}