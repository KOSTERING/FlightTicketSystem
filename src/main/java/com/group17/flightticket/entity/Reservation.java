package com.group17.flightticket.entity;

import com.group17.flightticket.enums.SeatCategory;
import lombok.Data;
/**
 * The Reservation class represents a reservation made for a specific flight.
 * It includes details about the flight, seat category, associated fee,
 * and the refund rate. The fee is calculated based on the selected seat category.
 */
@Data
public class Reservation {
    private Flight flight;
    private SeatCategory seatCategory;
    private double fee;
    private double refundRate = 0.8;
    private Insurance insurance;
    /**
     * Constructs a Reservation with the given flight and seat category.
     * The fee is calculated based on the selected seat category.
     *
     * @param flight The flight for which the reservation is made.
     * @param seatCategory The category of the seat reserved.
     */
    public Reservation(Flight flight, SeatCategory seatCategory) {
        this.flight = flight;
        this.seatCategory = seatCategory;
        this.fee = calculateFee(seatCategory);
    }
    /**
     * Modifies the seat category of the reservation.
     * Updates the fee based on the new seat category.
     *
     * @param newSeatCategory The new seat category to update the reservation.
     */
    public void modifyCategory(SeatCategory newSeatCategory) {
        this.seatCategory = newSeatCategory;
        this.fee = calculateFee(newSeatCategory); // Recalculate fee for the new seatCategory
    }
    /**
     * Calculates the refund amount for the reservation based on the refund rate.
     *
     * @return The refundable fee.
     */
    public Double getRefundFee() {
        return fee * refundRate;
    }
    /**
     * Calculates the fee for the reservation based on the selected seat category.
     *
     * @param seatCategory The category of the seat for which the fee is calculated.
     * @return The fee corresponding to the given seat category.
     */
    private double calculateFee(SeatCategory seatCategory) {
        return seatCategory.getBaseFee(); // Base fee directly fetched from the enum
    }

}