package com.gridnine.testing.filtration;

import com.gridnine.testing.model.Flight;

import java.util.function.Predicate;

public class ArrivalBeforeDepartureCriterion implements FlightFiltrationCriterion {

    //TO Do: check the task list
    @Override
    public Predicate<Flight> getPredicate() {
        return flight -> flight.getSegments().stream()
                .noneMatch(segment -> segment.getArrivalDate().isBefore(segment.getDepartureDate()));
    }
}
