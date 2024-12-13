package com.group17.flightticket.entity;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents an airline company that manages a collection of flights.
 * Provides functionality to add, cancel, and delay flights, as well as generate an inventory report.
 */
@Data
public class AirlineCompany {
    private String companyName;
    private List<Flight> flights = new ArrayList<>();
    protected static final int CAPACITY_THRESHOLD = 5;
    private LoyalScheme loyalScheme;

    /**
     * Constructs a new instance of an AirlineCompany with the specified company name.
     * Initializes the list of flights for the airline.
     *
     * @param companyName The name of the airline company.
     */
    public AirlineCompany(String companyName) {
        this.companyName = companyName;
        this.flights = new ArrayList<>();
        this.loyalScheme = new LoyalScheme();
    }

    /**
     * Adds a flight to the airline company's list of managed flights.
     *
     * @param flight The Flight object representing the flight to be added.
     */
    public void addFlight(Flight flight) {
        flights.add(flight);
    }

    /**
     * Cancels a flight from the airline company's list of managed flights based on the provided flight number.
     *
     * @param flight The instance of the flight to be canceled.
     * @return true if the flight was successfully found and canceled, false otherwise.
     */

    public boolean cancelFlight(Flight flight) {
        boolean bRes =  false;
        if (flight != null && flights.contains(flights)) {
            bRes = flights.remove(flight);
        }
        return bRes;
    }

    /**
     * Delays a flight by updating its departure and arrival times.
     * This method searches for the flight by flight number within the list of managed flights.
     * Once found, it calls the Flight's delayFlight method to adjust the timings.
     *
     * @param flight The instance of the flight to be delayed.
     * @param newDepartureTime The new departure time for the flight.
     * @param newArrivalTime The new arrival time for the flight.
     */

    public void delayFlight(Flight flight, LocalDateTime newDepartureTime, LocalDateTime newArrivalTime) {
        if (flight != null) {
            flight.delayFlight(newDepartureTime,newArrivalTime);
        }
    }



    /**
     * Prints the inventory of all flights under the airline company.
     * Displays the company name followed by each flight's number and the remaining seats available.
     */
    public void inventory() {
        for (Flight flight : flights) {
            System.out.println("AirLineCompany Name:" + companyName);
            System.out.println("Flight " + flight.getFlightNumber() + " has " +
                    flight.getRemainSeatCount() + " seats remaining.");
        }
    }
}