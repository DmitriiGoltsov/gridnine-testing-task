package com.gridnine.testing.filtration;

import com.gridnine.testing.model.Flight;

import java.time.LocalDateTime;
import java.util.function.Predicate;

public class DepartureNotBeforeNowCriterion implements FlightFiltrationCriterion {

    @Override
    public Predicate<Flight> getPredicate() {
        return flight -> flight.getSegments().stream()
                .noneMatch(segment -> segment.getDepartureDate().isBefore(LocalDateTime.now()));
    }
}
