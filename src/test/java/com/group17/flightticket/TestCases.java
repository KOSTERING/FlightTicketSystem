package com.group17.flightticket;

import com.group17.flightticket.entity.ChinaEasternAirlines;
import com.group17.flightticket.entity.Flight;
import com.group17.flightticket.entity.Passenger;
import com.group17.flightticket.enums.SeatCategory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@SpringBootTest(classes = FlightTicketSystemApplication.class)
public class TestCases {

    ChinaEasternAirlines easternAirlines;

    Flight flightDomestic;

    Flight flightAbroad;

    Passenger passengerAlice;
    Passenger passengerBob;
    Passenger passengerHaru;

    @BeforeEach
    void beforeEach() {

        easternAirlines = new ChinaEasternAirlines();

        flightAbroad = new Flight("MU12322", "NewYork", "Paris",
                LocalDateTime.of(2024, 11, 18, 12, 0),
                LocalDateTime.of(2024, 11, 19, 0, 0),
                10);
        flightDomestic = new Flight("MU45613", "Shanghai", "Guangzhou",
                LocalDateTime.of(2024, 11, 20, 10, 0),
                LocalDateTime.of(2024, 11, 20, 13, 0),
                2);

        easternAirlines.addFlight(flightDomestic);
        easternAirlines.addFlight(flightAbroad);

        this.passengerAlice = new Passenger("Alice",2000);
        this.passengerBob = new Passenger("Bob",3000);
        this.passengerHaru = new Passenger("Haru",10000);
    }

    @Test
    void TestReservation() {
        
        System.out.println(passengerAlice.makeReservation(flightDomestic, SeatCategory.FIRST_CLASS));
        System.out.println(passengerBob.makeReservation(flightDomestic, SeatCategory.FIRST_CLASS));

        //Capcaity Exceed
        System.out.println(passengerHaru.makeReservation(flightDomestic, SeatCategory.FIRST_CLASS));

        //Get Member List of flightDomestic
        List<String> flightNumberList = flightDomestic.getFlightNumberList();
        flightNumberList.forEach(System.out::println);

        //Close Reservation For flightAbroad
        flightAbroad.setBOpenForReservation(false);
        System.out.println(passengerBob.makeReservation(flightAbroad, SeatCategory.FIRST_CLASS));

        //Cancel Reservation
        System.out.println(passengerBob.cancelReservation(flightDomestic));
    }

    @Test
    void TestCancelDelayAndNotify() {

        System.out.println(passengerAlice.makeReservation(flightDomestic, SeatCategory.FIRST_CLASS));

        //Delay 2h For flightDomestic
        easternAirlines.delayFlight(flightDomestic.getFlightNumber(),
                flightDomestic.getDepartureTime().plusHours(2),
                flightDomestic.getArrivalTime().plusHours(2));

        //flightDomestic inventory
        easternAirlines.inventory();

        //flight remain5 seat
    }

}
