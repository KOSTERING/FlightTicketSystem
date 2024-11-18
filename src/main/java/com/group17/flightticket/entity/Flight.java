package com.group17.flightticket.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@EqualsAndHashCode
public class Flight {
    private String flightNumber;
    private String origin;
    private String destination;
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;
    private int capacity;
    private double fee;
    private List<Passenger> passengerList;
    private boolean bOpenForReservation = true;

    public Flight(String flightNumber, String origin, String destination, LocalDateTime departureTime, LocalDateTime arrivalTime, int capacity) {
        this.flightNumber = flightNumber;
        this.origin = origin;
        this.destination = destination;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.capacity = capacity;
        this.passengerList = new ArrayList<>();
    }

    public boolean addPassenger(Passenger passenger) {
        if (bOpenForReservation && passengerList.size() < capacity) {
            passengerList.add(passenger);
            return true;
        }
        return false;
    }

    public boolean removePassenger(Passenger passenger) {
        return passengerList.remove(passenger);
    }

    public void delayFlight(LocalDateTime newDepartureTime, LocalDateTime newArrivalTime) {
        this.departureTime = newDepartureTime;
        this.arrivalTime = newArrivalTime;
        notifyPassengers();
    }

    public int getRemainSeatCount() {
        return capacity - passengerList.size();
    }

    private void notifyPassengers() {
        for (Passenger passenger : passengerList) {
            System.out.println("Notifying " + passenger.getName() + " about flight delay.");
        }
    }

    public List<String> getFlightNumberList() {
        List<String> RetNameList  = new ArrayList<>();
        for (Passenger passenger:passengerList) {
            RetNameList.add(passenger.getName());
        }
        return RetNameList;
    }
}