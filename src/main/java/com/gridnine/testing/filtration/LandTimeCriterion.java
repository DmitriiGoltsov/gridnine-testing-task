package com.gridnine.testing.filtration;

import com.gridnine.testing.model.Flight;
import com.gridnine.testing.util.LandTimeMode;

import java.util.function.Predicate;

import static com.gridnine.testing.util.FlightUtil.doesLandTimeSuffice;

public class LandTimeCriterion implements FlightFiltrationCriterion {

    private final LandTimeMode mode;
    private final long minutesMore;
    private final long minutesLess;

    public LandTimeCriterion(LandTimeMode mode, long minutesMore, long minutesLess) {
        this.mode = mode;
        this.minutesMore = minutesMore;
        this.minutesLess = minutesLess;
    }

    @Override
    public Predicate<Flight> getPredicate() {
        Predicate<Flight> result;
        switch (mode) {
            case MORE -> result = flight -> doesLandTimeSuffice(flight.getSegments(), 0, minutesMore);
            case LESS -> result = flight -> doesLandTimeSuffice(flight.getSegments(), minutesLess, 0);
            case MORE_AND_LESS -> result = flight -> doesLandTimeSuffice(flight.getSegments(), minutesLess, minutesMore);
            default -> throw new IllegalArgumentException("Mode field is incorrect");
        }

        return result;
    }
}
