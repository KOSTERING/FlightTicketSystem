package com.group17.flightticket.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 *  The Flight class represents a flight entity that encapsulates information about the flight,
 *  such as flight number, departure and landing times, origin, destination, etc.
 *  It provides methods to manipulate this information to support management and query functions.
 * @version 1.0
 * @since 2024-11-18
 */
@Data
public class Flight {
    private String flightNumber;
    private Terminal  origin;
    private AirlineCompany airlineCompany;
    private Terminal  destination;
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;
    private int capacity;
    private double fee;
    private List<Passenger> passengerList;
    private List<Passenger> boardedPassengers = new ArrayList<>(); // Passengers who have boarded
    private boolean bOpenForReservation = true;

    /**
     * Constructs a Flight object with specified details.
     *
     * @param flightNumber   The unique identifier for the flight.
     * @param origin         The departure terminal of the flight.
     * @param destination    The arrival terminal of the flight.
     * @param departureTime  The scheduled departure time of the flight.
     * @param arrivalTime    The scheduled arrival time of the flight.
     * @param capacity       The total number of seats available on the flight.
     */
    public Flight(String flightNumber, Terminal origin, AirlineCompany airlineCompany, Terminal destination, LocalDateTime departureTime, LocalDateTime arrivalTime, int capacity) {
        this.flightNumber = flightNumber;
        this.origin = origin;
        this.airlineCompany = airlineCompany;
        this.destination = destination;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.capacity = capacity;
        this.passengerList = new ArrayList<>();
        // Add this flight to the originating terminal's departing flights
        this.origin.addDepartingFlight(this);
        // Add this flight to the destination terminal's arriving flights
        this.destination.addArrivingFlight(this);
    }

    /**
     * Adds a passenger to the flight's passenger list if the flight is open for reservation
     * and there is available capacity.
     *
     * @param passenger The passenger to be added to the flight.
     * @return true if the passenger was successfully added, false otherwise.
     */
    public boolean addPassenger(Passenger passenger) {
        if (bOpenForReservation && passengerList.size() < capacity) {
            passengerList.add(passenger);
            return true;
        }
        return false;
    }

    /**
     * Removes the specified passenger from the flight's passenger list.
     *
     * @param passenger The passenger to be removed from the flight.
     * @return true if the passenger was successfully removed, false otherwise.
     */
    public boolean removePassenger(Passenger passenger) {
        return passengerList.remove(passenger);
    }

    /**
     * Delays the flight by updating the departure and arrival times.
     * This method also notifies all passengers about the flight delay.
     *
     * @param newDepartureTime The new scheduled departure time for the flight.
     * @param newArrivalTime   The new scheduled arrival time for the flight.
     */
    public void delayFlight(LocalDateTime newDepartureTime, LocalDateTime newArrivalTime) {
        this.departureTime = newDepartureTime;
        this.arrivalTime = newArrivalTime;
        notifyPassengers();
    }

    /**
     * Calculates the number of remaining seats on the flight.
     * This is determined by subtracting the current number of passengers
     * in the passenger list from the total capacity of the flight.
     *
     * @return The count of seats that have not been assigned to passengers.
     */
    public int getRemainSeatCount() {
        return capacity - passengerList.size();
    }

    /**
     * Notifies all passengers on the flight about any important updates or information.
     * This method iterates over the list of passengers associated with the flight and
     * calls the {@link Passenger#Notified()} method for each passenger to ensure they are notified.
     */
    private void notifyPassengers() {
        for (Passenger passenger : passengerList) {
            passenger.Notified();
        }
    }

    /**
     * Retrieves a list of names of all passengers currently booked on the flight.
     *
     * @return A list containing the names of all passengers on the flight.
     */
    public List<String> getFlightNumberList() {
        List<String> RetNameList  = new ArrayList<>();
        for (Passenger passenger:passengerList) {
            RetNameList.add(passenger.getName());
        }
        return RetNameList;
    }

    /**
     * Manages the boarding process for the flight, prioritizing passengers with quick boarding.
     */
    public void boardPassengers() {
        if (boardedPassengers.size() >= capacity) {
            System.out.println("All passengers have already boarded.");
            return;
        }

        System.out.println("Boarding process starting for flight " + flightNumber);

        // Notify terminal about boarding start
        origin.notify("Boarding started for flight " + flightNumber);

        // Priority boarding
        for (Passenger passenger : passengerList) {
            if (passenger.hasPriorityBoarding() && origin.hasPassenger(passenger) && boardedPassengers.size() < capacity) {
                boardedPassengers.add(passenger);
                System.out.println("Priority boarding: Passenger " + passenger.getName() + " has boarded.");
            }
        }

        // Regular boarding
        for (Passenger passenger : passengerList) {
            if (!passenger.hasPriorityBoarding() && origin.hasPassenger(passenger) && boardedPassengers.size() < capacity) {
                boardedPassengers.add(passenger);
                System.out.println("Regular boarding: Passenger " + passenger.getName() + " has boarded.");
            }
        }

        if (boardedPassengers.size() >= capacity) {
            System.out.println("Flight " + flightNumber + " is fully boarded.");
        }

        passengerList.clear();

        // Notify terminal about boarding completion
        origin.notify("Boarding completed for flight " + flightNumber);
    }

    /**
     * Calculates the hash code for the Flight object. The hash code is generated based on the flight number,
     * origin, destination, departure time, and arrival time.
     * <p>
     * This method overrides {@link Object#hashCode()} to create a unique hash code for the flight based on its key attributes,
     * which helps in efficient lookups and storage in collections like HashMap or HashSet.
     *
     * @return The hash code of the Flight object.
     */

    @Override
    public int hashCode() {
        return Objects.hash(flightNumber,origin,destination,departureTime,arrivalTime);
    }

    /**
     * Returns a concise string representation of the Flight object. This method avoids recursive calls and provides
     * a simple description containing the flight number and origin terminal.
     * <p>
     * This method overrides {@link Object#toString()} to provide a brief and informative string representation
     * of the Flight object, useful for debugging and logging purposes.
     *
     * @return A string representation of the Flight, formatted as "Flight{Number='flightNumber', origin=origin}".
     */
    @Override
    public String toString() {
        // Return a simplified description, avoiding recursive calls
        return "Flight{Number='" + flightNumber + "', origin=" + origin + "}";
    }

}