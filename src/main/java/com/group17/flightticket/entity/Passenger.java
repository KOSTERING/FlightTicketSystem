package com.group17.flightticket.entity;

import com.group17.flightticket.enums.SeatCategory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

/**
 * The {@code Passenger} class represents a passenger with their details and actions.
 * Each passenger has a name, a balance, and a list of reservations.
 * It provides methods for making, modifying, and canceling flight reservations,
 * as well as managing loyalty points.
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
     * Makes a reservation for a flight with a specified seat category.
     * This method checks if the flight is open for reservation and if there are available seats.
     * It also checks if the passenger already has a reservation for this flight.
     * Loyalty points are applied to reduce the flight fee if available.
     * Points are earned based on the flight fee.
     *
     * @param flight   the flight to be reserved
     * @param category the seat category selected for the reservation
     * @return the created reservation, or {@code null} if the reservation could not be made
     */
    public Reservation makeReservationV3(Flight flight, SeatCategory category) {

        if (!flight.isBOpenForReservation() || flight.getRemainSeatCount() < 1) {
            System.out.println("Flight is not open for reservation or fully booked.");
            return null;
        }
        if (flight.getRemainSeatCount() < 1) {
            return null;
        }

        if (reservations.stream().anyMatch(reservation -> reservation.getFlight().equals(flight))) {
            System.out.println(methodLogPrefix+ "Conflict: Already booked on this flight.");
            return null;
        }

        Reservation reservation = new Reservation(flight, category);
        double fee = reservation.getFee();


        LoyalScheme loyalScheme = flight.getAirlineCompany().getLoyalScheme();
        double points = loyalScheme.getPoints(this.getName());
        if (points + balance < fee) {
            System.out.println("Insufficient balance or Loyal points for this flight.");
            return null;
        }
        Double needToPay = loyalScheme.redeemPoints(this.getName(), fee);

        balance -= needToPay;

        int pointsEarned = (int) (fee / 10); // 每10元获得1积分
        loyalScheme.addPoints(this.getName(), pointsEarned);
        reservations.add(reservation);
        flight.addPassenger(this);

        System.out.println("Reservation successful. Points earned: " + pointsEarned + " current total points " + loyalScheme.getPoints(getName()));
        return reservation;
    }

    /**
     * Modifies the seat category of an existing reservation for a specified flight number.
     * The method ensures that the passenger has a reservation for the given flight,
     * and checks if the new category differs from the current one.
     * It also ensures that the passenger's balance is sufficient to cover the difference in price.
     *
     * @param flightNum the flight number of the reservation to be modified
     * @param newCategory the new seat category for the reservation
     * @return {@code true} if the seat category was successfully modified, {@code false} otherwise
     */

    public boolean modifySeatCategory(String flightNum,SeatCategory newCategory) {

        Reservation currentReservation = null;
        for (Reservation reservation:reservations) {
            if (flightNum.equals(reservation.getFlight().getFlightNumber())) {
                currentReservation =  reservation;
            }
        }
        if (currentReservation == null) {
            System.out.println("Your reservation for FlightNum: "+flightNum+" does not exist! please make a reservation first!");
            return false;
        }

        SeatCategory currentcategory = currentReservation.getSeatCategory();
        if (newCategory == currentcategory) {
           System.out.println("You book the same seatCategory compare to the previous one，please check again");
           return false;
       }
        double gapPrice = currentcategory.getBaseFee() - newCategory.getBaseFee();
        if (balance + gapPrice < 0) {
            System.out.println("Your balance is Insufficient for this change!");
            return false;
        }

        currentReservation.modifyCategory(newCategory);

        balance += gapPrice;
        System.out.println("You Flight: " + flightNum + " seatCategory has now Change to " + newCategory.name());
        return true;
    }
    /**
     * Modifies the seat category of an existing reservation, identified by the provided reservation object.
     * This method ensures the reservation exists and the new seat category is different from the current one.
     * It also ensures the passenger has enough balance to pay for the price difference.
     *
     * @param reservation the reservation to modify
     * @param newCategory the new seat category for the reservation
     * @return {@code true} if the seat category was successfully modified, {@code false} otherwise
     */

    public boolean modifySeatCategoryV2(Reservation reservation,SeatCategory newCategory) {
        if(reservation==null){
            System.out.println("The reservation object is null!");
            return false;
        }
        Reservation currentReservation = null;
        for (Reservation singleRes:reservations) {
            if (reservation.equals(singleRes)) {
                currentReservation =  singleRes;
                break;
            }
        }
        if (currentReservation == null) {
            System.out.println("Your reservation does not exist! please make a reservation first!");
            return false;
        }

        SeatCategory currentCategory = currentReservation.getSeatCategory();
        if (newCategory == currentCategory) {
            System.out.println("You book the same seatCategory compare to the previous one，please check again");
            return false;
        }
        double gapPrice = currentCategory.getBaseFee() - newCategory.getBaseFee();
        if (balance + gapPrice < 0) {
            System.out.println("Your balance is Insufficient for this change!");
            return false;
        }

        currentReservation.modifyCategory(newCategory);

        balance += gapPrice;
        System.out.println("You Flight: " + reservation.getFlight().getFlightNumber() + " seatCategory has now Change to " + newCategory.name());
        return true;
    }

    /**
     * Cancels an existing reservation for a flight.
     * The method refunds the passenger's balance and loyalty points based on the reservation's refund fee.
     *
     * @param flight the flight to cancel the reservation for
     * @param airlineCompany the airline company associated with the flight
     * @return {@code true} if the reservation was successfully canceled, {@code false} otherwise
     */
    public boolean cancelReservation(Flight flight, AirlineCompany airlineCompany) {
        for (Reservation reservation : reservations) {
            if (reservation.getFlight().equals(flight)) {
                reservations.remove(reservation);
                flight.removePassenger(this);
                Double refundFee = reservation.getRefundFee();
                balance += refundFee;
                int pointsToRefund = (int) (refundFee / 10);
                airlineCompany.getLoyalScheme().addPoints(this.getName(),pointsToRefund);
                System.out.println(methodLogPrefix + "Passenger:"+ this.name +" Reservation for FlightNum:" +flight.getFlightNumber() +
                        "has been Canceled + pointsToRefund:" + pointsToRefund);
                return true;
            }
        }
        return false;
    }
    /**
     * Notifies the passenger with important information about their flight or reservation.
     * This method can be called when there is a need to inform the passenger of changes or updates.
     */
    public void Notified(){
        System.out.println("name = " + name +" has been Notified");
    }
}