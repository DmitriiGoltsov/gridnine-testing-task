package com.gridnine.testing.service;

import com.gridnine.testing.model.Flight;

import java.util.List;
import java.util.function.Predicate;

public interface FlightService {
    List<Flight> getFlightsByCriteria(Predicate<Flight>...predicates);
    List<Flight> getFlightsByCriteria(Predicate<Flight> predicate);
    List<Flight> getAllFlights();
}
