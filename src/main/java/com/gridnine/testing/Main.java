package com.gridnine.testing;

import com.gridnine.testing.model.Flight;
import com.gridnine.testing.service.FlightService;
import com.gridnine.testing.service.FlightServiceImpl;
import com.gridnine.testing.util.FlightBuilder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.function.Predicate;

public class Main {
    public static void main(String[] args) {

        FlightService flightService = new FlightServiceImpl();

        System.out.println(LocalDateTime.now());

        Predicate<Flight> nullPredicate = null;

        Predicate<Flight> departureBeforeNow = flight -> flight.getSegments().stream()
                    .noneMatch(segment -> segment.getDepartureDate().isBefore(LocalDateTime.now()));

        Predicate<Flight> arrivalBeforeDeparture = flight -> flight.getSegments().stream()
                .noneMatch(segment -> segment.getArrivalDate().isBefore(segment.getDepartureDate()));

        Predicate<Flight> landingTimeMoreThan120Minutes = flight -> flight.getMaxLandingTime() < 120L;

        List<Flight> flights = FlightBuilder.createFlights();
        System.out.println("Flights are " + flights);

        System.out.println("\nFlights without departures before now "
                + flightService.getFlightsByCriteria(departureBeforeNow));

        System.out.println("\nFlights without arrival before departure "
                + flightService.getFlightsByCriteria(arrivalBeforeDeparture));

        System.out.println("\nFlights without landing time more than 2 hours "
                + flightService.getFlightsByCriteria(landingTimeMoreThan120Minutes));

        System.out.println("\n NullPredicate" + flightService.getFlightsByCriteria(nullPredicate));
    }
}