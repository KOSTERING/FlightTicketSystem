package com.group17.flightticket.entity;

import com.group17.flightticket.enums.SeatCategory;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * The Passenger class represents a passenger with their details and actions.
 * Each passenger has a name, a balance, and a list of reservations.
 */
@Data
public class Passenger {
    /** The name of the passenger */
    private String name;
    /** The balance of the passenger's account */
    private double balance;
    /** The list of reservations made by the passenger */
    private List<Reservation> reservations =  new ArrayList<>();
    /** Prefix for logging messages */
    private static String methodLogPrefix = "LogPassenger_ ";

    /**
     * Constructor to create a new Passenger instance.
     *
     * @param name    the name of the passenger
     * @param balance the initial account balance
     */
    public Passenger(String name, double balance) {
        this.name = name;
        this.balance = balance;
    }
     /**
     * Make a reservation for a flight.
     *
     * @param flight   the flight to be reserved
     * @param category the seat category for the reservation
     * @return true if the reservation is successful, false otherwise
     */
    public boolean makeReservation(Flight flight, SeatCategory category) {
        if (!flight.isBOpenForReservation()) {
            System.out.println(methodLogPrefix+ "The Flight number: " + flight.getFlightNumber()+" are no longer open for Reservation");
            return false;
        }

        if (reservations.stream().anyMatch(reservation -> reservation.getFlight().equals(flight))) {
            System.out.println(methodLogPrefix+ "Conflict: Already booked on this flight.");
            return false;
        }

        Reservation reservation = new Reservation(flight, category);
        double fee = reservation.getFee();
        if (balance < fee) {
            System.out.println(methodLogPrefix + "Your balance is Insufficient for this flight.");
            return false;
        }

        balance -= fee;
        reservations.add(reservation);
        return flight.addPassenger(this);
    }
     /**
     * Cancel an existing reservation for a flight.
     *
     * @param flight the flight to cancel the reservation for
     * @return true if the cancellation is successful, false otherwise
     */
    public boolean cancelReservation(Flight flight) {
        for (Reservation reservation : reservations) {
            if (reservation.getFlight().equals(flight)) {
                reservations.remove(reservation);
                flight.removePassenger(this);
                balance += reservation.getRefundFee();
                System.out.println(methodLogPrefix + "Passenger:"+ this.name +" Reservation for FlightNum:" +flight.getFlightNumber() +
                        "has been Canceled.");
                return true;
            }
        }
        return false;
    }
     /**
     * Notify the passenger.
     * Typically called when there is important information about a flight.
     */
    public void Notified(){
        System.out.println("name = " + name +" has been Notified");
    }
}