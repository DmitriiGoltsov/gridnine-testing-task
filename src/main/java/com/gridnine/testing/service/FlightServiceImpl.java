package com.gridnine.testing.service;

import com.gridnine.testing.filtration.FlightFiltrationCriterion;
import com.gridnine.testing.model.Flight;
import com.gridnine.testing.util.FlightBuilder;

import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FlightServiceImpl implements FlightService {

    private final Logger LOGGER = Logger.getLogger("Flight service logger");

    private final FlightBuilder databaseImitation;

    public FlightServiceImpl(FlightBuilder databaseImitation) {
        this.databaseImitation = databaseImitation;
    }

    @Override
    public List<Flight> getAllFlights() {
        return databaseImitation.getFlights();
    }

    @Override
    public List<Flight> getFlightsByCriteria(List<FlightFiltrationCriterion> criteria) {
        if (Objects.isNull(criteria) || criteria.isEmpty()) {
            throw new IllegalArgumentException("Criteria list cannot be empty or null.");
        }

        Predicate<Flight> combinedPredicate = makeCombinedPredicate(criteria);

        return getAllFlights().stream()
                .filter(combinedPredicate)
                .toList();
    }

    private Predicate<Flight> makeCombinedPredicate(List<FlightFiltrationCriterion> criteria) {

        try {
            return criteria.stream()
                    .map(FlightFiltrationCriterion::getPredicate)
                    .reduce(Predicate::and)
                    .orElse(predicate -> true);
        } catch (NullPointerException e) {
            LOGGER.log(Level.INFO, "Exception occurred during making a combined predicated", e);
            throw new IllegalArgumentException("Criteria contains at least one null");
        }
    }
}
