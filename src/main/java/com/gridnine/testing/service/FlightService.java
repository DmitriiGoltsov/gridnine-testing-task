package com.gridnine.testing.service;

import com.gridnine.testing.filtration.FlightFiltrationCriterion;
import com.gridnine.testing.model.Flight;

import java.util.List;

public interface FlightService {
    List<Flight> getFlightsByCriteria(List<FlightFiltrationCriterion> criteria);
    List<Flight> getAllFlights();
}
