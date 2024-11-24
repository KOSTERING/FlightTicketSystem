package com.group17.flightticket.entity;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class AirlineCompany {
    private String companyName;
    private List<Flight> flights = new ArrayList<>();

    public AirlineCompany(String companyName) {
        this.companyName = companyName;
        this.flights = new ArrayList<>();
    }

    public void addFlight(Flight flight) {
        flights.add(flight);
    }

    public boolean cancelFlight(String flightNumber) {
        boolean bRes =  false;
        for (Flight flight : flights) {
            if (flight.getFlightNumber().equals(flightNumber)) {
                bRes = flights.remove(flight);
            }
        }
        return bRes;
    }

    public void delayFlight(String flightNumber, LocalDateTime newDepartureTime, LocalDateTime newArrivalTime) {
        for (Flight flight : flights) {
            if (flight.getFlightNumber().equals(flightNumber)) {
                flight.delayFlight(newDepartureTime, newArrivalTime);
            }
        }
    }

    public void inventory() {
        for (Flight flight : flights) {
            System.out.println("AirLineCompany Name:" + companyName);
            System.out.println("Flight " + flight.getFlightNumber() + " has " +
                    flight.getRemainSeatCount() + " seats remaining.");
        }
    }
}