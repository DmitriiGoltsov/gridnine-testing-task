package com.gridnine.testing.service;

import com.gridnine.testing.model.Flight;
import com.gridnine.testing.util.FlightBuilder;

import javax.lang.model.type.NullType;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.logging.Logger;
import java.util.stream.Stream;

public class FlightServiceImpl implements FlightService {

    private final Logger LOGGER = Logger.getAnonymousLogger();

    @Override
    public List<Flight> getAllFlights() {
        return FlightBuilder.createFlights();
    }

    // TO DO: decide if the filtration of the null from predicates is a correct way of null handling
    @Override
    public List<Flight> getFlightsByCriteria(Predicate<Flight>... predicates) {
        Predicate<Flight> combinedPredicate = Stream.of(predicates)
                .filter(Objects::nonNull)
                .reduce(Predicate::and)
                .orElse(predicate -> true);

        return getFlightsByCriteria(combinedPredicate);
    }

    @Override
    public List<Flight> getFlightsByCriteria(Predicate<Flight> predicate) {
        if (!Objects.nonNull(predicate)) {
            throw new IllegalArgumentException("Predicate cannot be null.");
        }

        return getAllFlights().stream()
                .filter(predicate)
                .toList();
    }
}
