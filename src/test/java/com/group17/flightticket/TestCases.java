package com.group17.flightticket;

import com.group17.flightticket.entity.*;
import com.group17.flightticket.enums.SeatCategory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = FlightTicketSystemApplication.class)
public class TestCases {

    ChinaEasternAirlines easternAirlines;

    Flight flightDomestic;

    Flight flightAbroad;

    Terminal NewYorkTerminal;
    Terminal ShanghaiTerminal;
    Terminal ParisTerminal;
    Terminal GuangzhouTerminal;

    Passenger passengerAlice;
    Passenger passengerBob;
    Passenger passengerHaru;
    Passenger passengerJack;
    Passenger passengerMary;

    //Some Preparation before test case running
    @BeforeEach
    void beforeEach() {

        easternAirlines = new ChinaEasternAirlines();

        NewYorkTerminal=new Terminal("NewYork Terminal", "NewYork");
        ShanghaiTerminal=new Terminal("Shanghai Terminal", "Shanghai");
        ParisTerminal=new Terminal("Paris Terminal", "Paris");
        GuangzhouTerminal=new Terminal("Guangzhou Terminal", "Guangzhou");

        flightAbroad = new Flight("MU12322", NewYorkTerminal, easternAirlines, ParisTerminal,
                LocalDateTime.of(2024, 11, 18, 12, 0),
                LocalDateTime.of(2024, 11, 19, 0, 0),
                10);
        flightDomestic = new Flight("MU45613", ShanghaiTerminal, easternAirlines, GuangzhouTerminal,
                LocalDateTime.of(2024, 11, 20, 10, 0),
                LocalDateTime.of(2024, 11, 20, 13, 0),
                3);

        easternAirlines.addFlight(flightDomestic);
        easternAirlines.addFlight(flightAbroad);

        this.passengerAlice = new Passenger("Alice",2000);
        this.passengerBob = new Passenger("Bob",3000);
        this.passengerHaru = new Passenger("Haru",10000);
        this.passengerJack = new Passenger("Jack",300);
        this.passengerMary = new Passenger("Mary",5000);

    }

    @Test
    void TestReservation() {

        Reservation AliceReservation = passengerAlice.makeReservationV4(flightDomestic, SeatCategory.FIRST_CLASS);
        Reservation BobReservation = passengerBob.makeReservationV4(flightDomestic,  SeatCategory.FIRST_CLASS);

        /*assertTrue(passengerAlice.makeReservation(flightDomestic, SeatCategory.FIRST_CLASS));
        assertTrue(passengerBob.makeReservation(flightDomestic, SeatCategory.FIRST_CLASS));*/

        // Mary makes a reservation with insurance
        Reservation MaryReservation = passengerMary.makeReservationV4(flightDomestic,  SeatCategory.FIRST_CLASS,true,1000,false);

        //Capcaity Exceed book fail
        Reservation HaruReservation= passengerHaru.makeReservationV4(flightDomestic, SeatCategory.FIRST_CLASS);

        assertNull(HaruReservation);

        //Get Member List of flightDomestic
        List<String> flightNumberList = flightDomestic.getFlightNumberList();
        flightNumberList.forEach(System.out::println); //it should print Alice Bob

        //Close Reservation For flightAbroad
        flightAbroad.setBOpenForReservation(false);
        assertNull(passengerBob.makeReservationV4(flightAbroad,  SeatCategory.FIRST_CLASS));

        //Cancel Reservation (Some Notification message will be print out if it Cancel Reservation Sucessfully)
        assertTrue(passengerBob.cancelReservationV2(flightDomestic, easternAirlines));
        assertTrue(passengerMary.cancelReservationV2(flightDomestic, easternAirlines));
    }

    @Test
    void TestCancelDelayAndNotify() {

        Reservation JackReservation = passengerJack.makeReservationV4(flightAbroad,  SeatCategory.BUSINESS);
        Reservation AliceReservation = passengerAlice.makeReservationV4(flightAbroad,  SeatCategory.BUSINESS);

        //Delay 2h For flightDomestic
        //Alice and jack will be Notified
        LocalDateTime DelayFlightStartTime = flightAbroad.getDepartureTime().plusHours(2);
        LocalDateTime DelayFlightArrivalTime = flightAbroad.getArrivalTime().plusHours(2);
        easternAirlines.delayFlight(flightAbroad,
                DelayFlightStartTime,DelayFlightArrivalTime);

        //flightDomestic inventory

        //flight MU12322 remain 9 seat
        //flight MU45613 remain 1 seat,and there will be short of capacity notification
        easternAirlines.inventory();

        //sufficient balance for alice to modify SeatCategory
        assertTrue(passengerAlice.modifySeatCategory(flightAbroad.getFlightNumber(),SeatCategory.FIRST_CLASS));

        //Insufficient balance for jack to modify SeatCategory
        assertFalse(passengerJack.modifySeatCategory(flightAbroad.getFlightNumber(),SeatCategory.FIRST_CLASS));

    }

    @Test
    void TestNewMethod() {
        Reservation JackReservation=passengerJack.makeReservationV4(flightAbroad,  SeatCategory.BUSINESS);
        assertFalse(passengerJack.modifySeatCategoryV2(JackReservation,SeatCategory.FIRST_CLASS));

    }

    @Test
    void TestNewLoyalScheme() {
        Reservation JackReservation = passengerJack.makeReservationV4(flightAbroad,  SeatCategory.BUSINESS);
        passengerJack.cancelReservationV2(flightAbroad,easternAirlines);
    }

    @Test
    void testBoardingPriority() {
        passengerAlice.setCurrentTerminal(ShanghaiTerminal);
        passengerBob.setCurrentTerminal(ShanghaiTerminal);
        passengerAlice.enablePriorityBoarding();

        flightDomestic.addPassenger(passengerAlice);
        flightDomestic.addPassenger(passengerBob);
        flightDomestic.boardPassengers();

        List<Passenger> boarded = flightDomestic.getBoardedPassengers();
        assertEquals(2, boarded.size());
        assertEquals("Alice", boarded.get(0).getName()); // Alice priority bording
    }

    @Test
    void testTerminalPassengerPresence() {
        passengerAlice.setCurrentTerminal(NewYorkTerminal);
        passengerBob.setCurrentTerminal(ParisTerminal);

        assertTrue(NewYorkTerminal.hasPassenger(passengerAlice));
        assertFalse(ParisTerminal.hasPassenger(passengerAlice));
        assertTrue(ParisTerminal.hasPassenger(passengerBob));
    }

}
