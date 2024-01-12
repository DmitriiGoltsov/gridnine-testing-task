package com.gridnine.testing.test.unit;

import com.gridnine.testing.filtration.ArrivalBeforeDepartureCriterion;
import com.gridnine.testing.filtration.DepartureNotBeforeNowCriterion;
import com.gridnine.testing.filtration.FlightFiltrationCriterion;
import com.gridnine.testing.filtration.LandTimeCriterion;
import com.gridnine.testing.model.Flight;
import com.gridnine.testing.service.FlightService;
import com.gridnine.testing.service.FlightServiceImpl;
import com.gridnine.testing.util.FlightBuilder;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static com.gridnine.testing.util.LandTimeMode.MORE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class FilterModuleTest {

    private FlightService flightService;

    private LandTimeCriterion landTimeCriterion;

    private ArrivalBeforeDepartureCriterion arrivalBeforeDepartureCriterion;

    private DepartureNotBeforeNowCriterion departureNotBeforeNowCriterion;

    private List<FlightFiltrationCriterion> combinedCriteria;

    private List<Flight> idealFlights;

    private List<Flight> flightsWithoutDepartureBeforeNow;

    private List<Flight> flightsWithoutDepartureBeforeArrival;

    private List<Flight> flightsWithoutLandingTimeMoreThan120Minutes;

    private List<Flight> flights;

    @BeforeEach
    void beforeEach() {
        FlightBuilder databaseImitation = new FlightBuilder();
        flightService = new FlightServiceImpl(databaseImitation);

        landTimeCriterion = new LandTimeCriterion(MORE, 120L, 0L);
        arrivalBeforeDepartureCriterion = new ArrivalBeforeDepartureCriterion();
        departureNotBeforeNowCriterion = new DepartureNotBeforeNowCriterion();
        combinedCriteria = List.of(landTimeCriterion, arrivalBeforeDepartureCriterion, departureNotBeforeNowCriterion);

        flights = flightService.getAllFlights();
        idealFlights = flights.subList(0, 2);
        flightsWithoutDepartureBeforeNow = List.of(flights.get(0), flights.get(1), flights.get(3), flights.get(4), flights.get(5));
        flightsWithoutDepartureBeforeArrival = List.of(flights.get(0), flights.get(1), flights.get(2), flights.get(4), flights.get(5));
        flightsWithoutLandingTimeMoreThan120Minutes = List.of(flights.get(0), flights.get(1), flights.get(2), flights.get(3));
    }

    @Test
    void filterTestWithEmptyPredicate() {
        List<FlightFiltrationCriterion> nulls = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            nulls.add(null);
        }

        List<FlightFiltrationCriterion> mixedList = new ArrayList<>();
        mixedList.add(null);
        mixedList.add(departureNotBeforeNowCriterion);

        assertThrows(IllegalArgumentException.class, () -> flightService.getFlightsByCriteria(nulls));
        assertThrows(IllegalArgumentException.class, () -> flightService.getFlightsByCriteria(mixedList));
    }

    @Test
    void filterTestWithDepartureBeforeNowCriterion() {
        List<FlightFiltrationCriterion> criteria = List.of(departureNotBeforeNowCriterion);
        List<Flight> actualList = flightService.getFlightsByCriteria(criteria);

        assertThat(actualList).hasSize(5);
        assertIterableEquals(actualList, flightsWithoutDepartureBeforeNow);
    }

    @Test
    void filterTestWithDepartureBeforeArrivalCriterion() {
        List<FlightFiltrationCriterion> criteria = List.of(arrivalBeforeDepartureCriterion);
        List<Flight> actualList = flightService.getFlightsByCriteria(criteria);

        assertThat(actualList).hasSize(5);
        assertIterableEquals(actualList, flightsWithoutDepartureBeforeArrival);
    }

    @Test
    void filterTestWithLandTimeMoreThan120Minutes() {
        List<FlightFiltrationCriterion> criteria = List.of(landTimeCriterion);
        List<Flight> actualList = flightService.getFlightsByCriteria(criteria);

        assertThat(actualList).hasSize(4);
        assertIterableEquals(actualList, flightsWithoutLandingTimeMoreThan120Minutes);
    }

    @Test
    void filterTestWithCombinedCriterion() {
        List<Flight> actualList = flightService.getFlightsByCriteria(combinedCriteria);

        assertThat(actualList).hasSize(2);
        assertIterableEquals(actualList, idealFlights);
    }
}
