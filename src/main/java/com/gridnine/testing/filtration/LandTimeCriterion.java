package com.gridnine.testing.filtration;

import com.gridnine.testing.model.Flight;

import java.util.function.Predicate;

import static com.gridnine.testing.util.FlightUtil.isLandTimeExceeded;
import static com.gridnine.testing.util.FlightUtil.isLandTimeLessThanNeeded;

public class LandTimeCriterion implements FlightFiltrationCriterion {

    private final String mode;
    private final long minutesMore;
    private final long minutesLess;

    public LandTimeCriterion(String mode, long minutesMore, long minutesLess) {
        this.mode = mode;
        this.minutesMore = minutesMore;
        this.minutesLess = minutesLess;
    }

    @Override
    public Predicate<Flight> getPredicate() {
        Predicate<Flight> result;
        switch (mode) {
            case "more" -> result = flight -> isLandTimeExceeded(flight.getSegments(), minutesMore);
            case "less" -> result = flight -> isLandTimeLessThanNeeded(flight.getSegments(), minutesLess);
            case "moreAndLess" -> result = flight -> isLandTimeExceeded(flight.getSegments(), minutesMore)
                    && isLandTimeLessThanNeeded(flight.getSegments(), minutesLess);
            default -> throw new IllegalArgumentException("Mode field is incorrect");
        }

        return result;
    }
}
