package com.gridnine.testing.filtration;

import com.gridnine.testing.model.Flight;

import java.util.function.Predicate;

public interface FlightFiltrationCriterion {
    Predicate<Flight> getPredicate();
}
