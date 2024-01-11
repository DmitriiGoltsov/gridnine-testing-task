package com.gridnine.testing;

import com.gridnine.testing.filtration.ArrivalBeforeDepartureCriterion;
import com.gridnine.testing.filtration.DepartureBeforeNowCriterion;
import com.gridnine.testing.filtration.FlightFiltrationCriterion;
import com.gridnine.testing.filtration.LandTimeCriterion;
import com.gridnine.testing.model.Flight;
import com.gridnine.testing.service.FlightService;
import com.gridnine.testing.service.FlightServiceImpl;
import com.gridnine.testing.util.FlightBuilder;

import java.time.LocalDateTime;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        FlightService flightService = new FlightServiceImpl();

        List<FlightFiltrationCriterion> departureBeforeNow = List.of(new DepartureBeforeNowCriterion());
        List<FlightFiltrationCriterion> arrivalBeforeDeparture = List.of(new ArrivalBeforeDepartureCriterion());
        List<FlightFiltrationCriterion> landTimeMore = List.of(new LandTimeCriterion("more", 120L, 0));
        List<FlightFiltrationCriterion> combinedCriteria = List.of(
                new DepartureBeforeNowCriterion(),
                new LandTimeCriterion("more", 120L, 0),
                new ArrivalBeforeDepartureCriterion());

        System.out.println("Flights are" + flightService.getAllFlights());

        System.out.println("\nFlights without departures before now "
                + flightService.getFlightsByCriteria(departureBeforeNow));

        System.out.println("\nFlights without arrival before departure "
                + flightService.getFlightsByCriteria(arrivalBeforeDeparture));

        System.out.println("\nFlights without more than 2 hours on land "
                + flightService.getFlightsByCriteria(landTimeMore));

        System.out.println("\nFlights after combined criteria "
                + flightService.getFlightsByCriteria(combinedCriteria));
    }
}