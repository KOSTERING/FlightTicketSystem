package com.group17.flightticket.entity;

import com.group17.flightticket.enums.SeatCategory;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Passenger {
    private String name;

    private double balance;

    private List<Reservation> reservations =  new ArrayList<>();

    private static String methodLogPrefix = "LogPassenger_ ";

    public Passenger(String name, double balance) {
        this.name = name;
        this.balance = balance;
    }

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
            System.out.println(methodLogPrefix + "Your balance is not enough for this flight.");
            return false;
        }

        balance -= fee;
        reservations.add(reservation);
        return flight.addPassenger(this);
    }

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

    public void Noifitied(){
        System.out.println("name = " + name +" has been Noifitied");
    }
}