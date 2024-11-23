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
     *
     * @param flightNum the flightNum you wanna make changes
     * @param newCategory new seat category for the reservation
     * @return true if the change has been made successfully
     */

    public boolean modifySeatCategory(String flightNum,SeatCategory newCategory) {
        Reservation CurrentReservation = null;
        for (Reservation singleRes:reservations) {
            if (flightNum.equals(singleRes.getFlight().getFlightNumber())) {
                CurrentReservation =  singleRes;
            }
        }
        if (CurrentReservation == null) {
            System.out.println("Your reservation for FlightNum: "+flightNum+" does not exist! please make a reservation first!");
            return false;
        }

        SeatCategory CurrentCategory = CurrentReservation.getSeatCategory();
        if (newCategory == CurrentCategory) {
           System.out.println("You book the same seatCategory compare to the previous one，please check again");
           return false;
       }
        double gapPrice = CurrentCategory.getBaseFee() - newCategory.getBaseFee();
        if (balance + gapPrice < 0) {
            System.out.println("Your balance is Insufficient for this change!");
            return false;
        }
        CurrentReservation.modifyCategory(newCategory);
        balance += gapPrice;
        System.out.println("You Flight: " + flightNum + " seatCategory has now Change to " + newCategory.name());
        return true;
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