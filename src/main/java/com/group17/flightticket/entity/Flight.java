package com.group17.flightticket.entity;

import com.group17.flightticket.Interface.Airline;
import lombok.Data;
import lombok.EqualsAndHashCode;

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
    private String origin;
    private AirlineCompany airlineCompany;
    private String destination;
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;
    private int capacity;
    private double fee;
    private List<Passenger> passengerList;
    private boolean bOpenForReservation = true;

    /**
     * Constructs a Flight object with specified details.
     *
     * @param flightNumber   The unique identifier for the flight.
     * @param origin         The departure location of the flight.
     * @param destination    The arrival location of the flight.
     * @param departureTime  The scheduled departure time of the flight.
     * @param arrivalTime    The scheduled arrival time of the flight.
     * @param capacity       The total number of seats available on the flight.
     */
    public Flight(String flightNumber, String origin,AirlineCompany airlineCompany, String destination, LocalDateTime departureTime, LocalDateTime arrivalTime, int capacity) {
        this.flightNumber = flightNumber;
        this.origin = origin;
        this.airlineCompany = airlineCompany;
        this.destination = destination;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.capacity = capacity;
        this.passengerList = new ArrayList<>();
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
    @Override
    public int hashCode() {
        return Objects.hash(flightNumber,origin,destination,departureTime,arrivalTime);
    }


    @Override
    public String toString() {
        // 简单返回乘客的基本信息，避免递归调用
        return "Flight{Number='" + flightNumber + "', origin=" + origin + "}";
    }

}