package com.group17.flightticket.entity;

import com.group17.flightticket.enums.SeatCategory;
import lombok.Data;

@Data
public class Reservation {
    private Flight flight;
    private SeatCategory seatCategory;
    private double fee;
    private double refundRate = 0.8;
    public Reservation(Flight flight, SeatCategory seatCategory) {
        this.flight = flight;
        this.seatCategory = seatCategory;
        this.fee = calculateFee(seatCategory);
    }

    public SeatCategory getCategory() {
        return seatCategory;
    }

    public void modifyCategory(SeatCategory newSeatCategory) {
        this.seatCategory = newSeatCategory;
        this.fee = calculateFee(newSeatCategory); // Recalculate fee for the new seatCategory
    }

    public Double getRefundFee() {
        return fee * refundRate;
    }

    private double calculateFee(SeatCategory seatCategory) {
        return seatCategory.getBaseFee(); // Base fee directly fetched from the enum
    }




}