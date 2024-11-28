package com.group17.flightticket;

import com.group17.flightticket.entity.ChinaEasternAirlines;
import com.group17.flightticket.entity.Flight;
import com.group17.flightticket.entity.Passenger;
import com.group17.flightticket.entity.Reservation;
import com.group17.flightticket.enums.SeatCategory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = FlightTicketSystemApplication.class)
public class TestCases {

    ChinaEasternAirlines easternAirlines;

    Flight flightDomestic;

    Flight flightAbroad;

    Passenger passengerAlice;
    Passenger passengerBob;
    Passenger passengerHaru;
    Passenger passengerJack;

    //Some Preparation before test case running
    @BeforeEach
    void beforeEach() {

        easternAirlines = new ChinaEasternAirlines();

        flightAbroad = new Flight("MU12322", "NewYork",easternAirlines, "Paris",
                LocalDateTime.of(2024, 11, 18, 12, 0),
                LocalDateTime.of(2024, 11, 19, 0, 0),
                10);
        flightDomestic = new Flight("MU45613", "Shanghai",easternAirlines, "Guangzhou",
                LocalDateTime.of(2024, 11, 20, 10, 0),
                LocalDateTime.of(2024, 11, 20, 13, 0),
                2);

        easternAirlines.addFlight(flightDomestic);
        easternAirlines.addFlight(flightAbroad);

        this.passengerAlice = new Passenger("Alice",2000);
        this.passengerBob = new Passenger("Bob",3000);
        this.passengerHaru = new Passenger("Haru",10000);
        this.passengerJack = new Passenger("Jack",300);
    }

    @Test
    void TestReservation() {

        Reservation AliceReservation = passengerAlice.makeReservationV3(flightDomestic, SeatCategory.FIRST_CLASS);
        Reservation BobReservation = passengerBob.makeReservationV3(flightDomestic,  SeatCategory.FIRST_CLASS);

        /*assertTrue(passengerAlice.makeReservation(flightDomestic, SeatCategory.FIRST_CLASS));
        assertTrue(passengerBob.makeReservation(flightDomestic, SeatCategory.FIRST_CLASS));*/

        //Capcaity Exceed book fail
        Reservation HaruReservation= passengerHaru.makeReservationV3(flightDomestic, SeatCategory.FIRST_CLASS);

        assertNull(HaruReservation);

        //Get Member List of flightDomestic
        List<String> flightNumberList = flightDomestic.getFlightNumberList();
        flightNumberList.forEach(System.out::println); //it should print Alice Bob

        //Close Reservation For flightAbroad
        flightAbroad.setBOpenForReservation(false);
        assertNull(passengerBob.makeReservationV3(flightAbroad,  SeatCategory.FIRST_CLASS));

        //Cancel Reservation (Some Notification message will be print out if it Cancel Reservation Sucessfully)
        assertTrue(passengerBob.cancelReservation(flightDomestic, easternAirlines));
    }

    @Test
    void TestCancelDelayAndNotify() {

        Reservation JackReservation = passengerJack.makeReservationV3(flightAbroad,  SeatCategory.BUSINESS);
        Reservation AliceReservation = passengerAlice.makeReservationV3(flightAbroad,  SeatCategory.BUSINESS);

        //Delay 2h For flightDomestic
        //Alice will be Notified
        LocalDateTime DelayFlightStartTime = flightDomestic.getDepartureTime().plusHours(2);
        LocalDateTime DelayFlightArrivalTime = flightDomestic.getArrivalTime().plusHours(2);
        easternAirlines.delayFlight(flightDomestic.getFlightNumber(),
                DelayFlightStartTime,DelayFlightArrivalTime);

        //flightDomestic inventory

        //flight MU12322 remain 9 seat
        //flight MU45613 remain 1 seat,and there will be short of capacity notification
        easternAirlines.inventory();

        //sufficient balance for alice to modify SeatCategory
        assertTrue(passengerAlice.modifySeatCategory(flightDomestic.getFlightNumber(),SeatCategory.FIRST_CLASS));

        //Insufficient balance for jack to modify SeatCategory
        assertFalse(passengerJack.modifySeatCategory(flightAbroad.getFlightNumber(),SeatCategory.FIRST_CLASS));

    }

    @Test
    void TestNewMethod() {
        Reservation JackReservation=passengerJack.makeReservationV3(flightAbroad,  SeatCategory.BUSINESS);
        assertFalse(passengerJack.modifySeatCategoryV2(JackReservation,SeatCategory.FIRST_CLASS));

    }

    @Test
    void TestNewLoyalScheme() {
        Reservation JackReservation = passengerJack.makeReservationV3(flightAbroad,  SeatCategory.BUSINESS);
        passengerJack.cancelReservation(flightAbroad,easternAirlines);

    }
}
