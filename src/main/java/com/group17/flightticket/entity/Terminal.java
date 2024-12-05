package com.group17.flightticket.entity;

import lombok.Data;
import java.util.ArrayList;
import java.util.List;

/**
 * The {@code Terminal} class represents an airport terminal used as the origin or destination
 * of flights. It manages departing and arriving flights, and interacts with passengers
 * for processes like boarding.
 */
@Data
public class Terminal {
    private String name; // Terminal name
    private String location; // Terminal location
    private List<Flight> departingFlights = new ArrayList<>(); // Flights departing from this terminal
    private List<Flight> arrivingFlights = new ArrayList<>();  // Flights arriving at this terminal

    /**
     * Constructs a new {@code Terminal} instance.
     *
     * @param name     The name of the terminal.
     * @param location The location of the terminal (e.g., city or airport name).
     */
    public Terminal(String name, String location) {
        this.name = name;
        this.location = location;
    }

    /**
     * Adds a flight to the list of departing flights.
     *
     * @param flight The flight to add.
     */
    public void addDepartingFlight(Flight flight) {
        departingFlights.add(flight);
    }

    /**
     * Adds a flight to the list of arriving flights.
     *
     * @param flight The flight to add.
     */
    public void addArrivingFlight(Flight flight) {
        arrivingFlights.add(flight);
    }

    /**
     * Notifies the terminal about updates, such as boarding status or flight changes.
     *
     * @param message The notification message.
     */
    public void notify(String message) {
        System.out.println("Terminal [" + name + "] Notification: " + message);
    }


    /**
     * Checks if a passenger is present at this terminal.
     *
     * @param passenger The passenger to check.
     * @return {@code true} if the passenger is at the terminal, {@code false} otherwise.
     */
    public boolean hasPassenger(Passenger passenger) {
        return passenger.isAtTerminal(this);
    }
}
