package com.gridnine.testing.test.unit;

import com.gridnine.testing.model.Flight;
import com.gridnine.testing.service.FlightService;
import com.gridnine.testing.service.FlightServiceImpl;
import com.gridnine.testing.test.util.FlightBuilderTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class FilterModuleTest {

    private FlightService flightService;
    private List<Flight> testFlights;

    private Predicate<Flight> nullPredicate;

    private Predicate<Flight> departureBeforeNow;
    private Predicate<Flight> arrivalBeforeDeparture;
    private Predicate<Flight> landingTimeMoreThan120Minutes;

    private List<Flight> idealFlights;
    private List<Flight> flightsWithoutDepartureBeforeNow;
    private List<Flight> flightsWithoutDepartureBeforeArriving;
    private List<Flight> flightsWithoutLandingTimeMoreThan120Minutes;

    @BeforeEach
    void beforeEach() {
        flightService = new FlightServiceImpl();
        testFlights = flightService.getAllFlights();

        nullPredicate = null;
        departureBeforeNow = flight -> flight.getSegments().stream()
                .noneMatch(segment -> segment.getDepartureDate().isBefore(LocalDateTime.now()));
        landingTimeMoreThan120Minutes = flight -> flight.getSegments().stream()
                .noneMatch(segment -> segment.getArrivalDate().isBefore(segment.getDepartureDate()));

        idealFlights = FlightBuilderTest.createIdealFlights();
        flightsWithoutDepartureBeforeNow = FlightBuilderTest.createFlightsWithoutDepartingInPast();
        flightsWithoutDepartureBeforeArriving = FlightBuilderTest.createFlightsWithoutDepartingBeforeArriving();
    }

    @Test
    void filterTestWithEmptyPredicate() {
        Predicate<Flight> nullPredicate = null;
        List<Predicate<Flight>> nullPredicates = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            nullPredicates.add(null);
        }

        assertAll(
                () -> assertThrows(IllegalArgumentException.class,
                        () -> flightService.getFlightsByCriteria(nullPredicate)),
                () -> assertDoesNotThrow(() -> flightService.getFlightsByCriteria(departureBeforeNow,
                                arrivalBeforeDeparture, null), IllegalArgumentException.class.getCanonicalName())
        );
    }

    @Test
    void filterTestWithDepartureBeforeNowPredicate() {
        List<Flight> actualList = flightService.getFlightsByCriteria(departureBeforeNow);
        assertEquals(actualList, flightsWithoutDepartureBeforeNow);
    }

}
