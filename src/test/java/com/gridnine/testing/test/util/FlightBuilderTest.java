package com.gridnine.testing.test.util;

import com.gridnine.testing.model.Flight;
import com.gridnine.testing.model.Segment;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FlightBuilderTest {

    private final static LocalDateTime THREE_DAYS_FROM_NOW = LocalDateTime.now().plusDays(3);

    public static List<Flight> createIdealFlights() {
        return new ArrayList<>(Arrays.asList(
                //A normal flight with two hour duration
                createFlight(THREE_DAYS_FROM_NOW, THREE_DAYS_FROM_NOW.plusHours(2)),
                //A normal multi segment flight
                createFlight(THREE_DAYS_FROM_NOW, THREE_DAYS_FROM_NOW.plusHours(2),
                        THREE_DAYS_FROM_NOW.plusHours(3), THREE_DAYS_FROM_NOW.plusHours(5))));
    }

    public static List<Flight> createFlightsWithoutDepartingInPast() {
        List<Flight> result = createIdealFlights();
        result.addAll(Arrays.asList(
                createFlight(THREE_DAYS_FROM_NOW, THREE_DAYS_FROM_NOW.minusHours(6)),
                //A flight with more than two hours ground time
                createFlight(THREE_DAYS_FROM_NOW, THREE_DAYS_FROM_NOW.plusHours(2),
                        THREE_DAYS_FROM_NOW.plusHours(5), THREE_DAYS_FROM_NOW.plusHours(6)),
                //Another flight with more than two hours ground time
                createFlight(THREE_DAYS_FROM_NOW, THREE_DAYS_FROM_NOW.plusHours(2),
                        THREE_DAYS_FROM_NOW.plusHours(3), THREE_DAYS_FROM_NOW.plusHours(4),
                        THREE_DAYS_FROM_NOW.plusHours(6), THREE_DAYS_FROM_NOW.plusHours(7)))
        );

        return result;
    }

    public static List<Flight> createFlightsWithoutDepartingBeforeArriving() {
        List<Flight> result = createIdealFlights();
        result.addAll(Arrays.asList(
                //A flight departing in the past
                createFlight(THREE_DAYS_FROM_NOW.minusDays(6), THREE_DAYS_FROM_NOW),
                //A flight with more than two hours ground time
                createFlight(THREE_DAYS_FROM_NOW, THREE_DAYS_FROM_NOW.plusHours(2),
                        THREE_DAYS_FROM_NOW.plusHours(5), THREE_DAYS_FROM_NOW.plusHours(6)),
                //Another flight with more than two hours ground time
                createFlight(THREE_DAYS_FROM_NOW, THREE_DAYS_FROM_NOW.plusHours(2),
                        THREE_DAYS_FROM_NOW.plusHours(3), THREE_DAYS_FROM_NOW.plusHours(4),
                        THREE_DAYS_FROM_NOW.plusHours(6), THREE_DAYS_FROM_NOW.plusHours(7))
        ));

        return result;
    }

    public static List<Flight> createFlightsWithoutLandingTimeExceeding() {
        List<Flight> result = createIdealFlights();
        result.addAll(Arrays.asList(
                //A flight departing in the past
                createFlight(THREE_DAYS_FROM_NOW.minusDays(6), THREE_DAYS_FROM_NOW),
                //A flight that departs before it arrives
                createFlight(THREE_DAYS_FROM_NOW, THREE_DAYS_FROM_NOW.minusHours(6))
        ));

        return result;
    }

    private static Flight createFlight(final LocalDateTime... dates) {
        if ((dates.length % 2) != 0) {
            throw new IllegalArgumentException(
                    "you must pass an even number of dates");
        }
        List<Segment> segments = new ArrayList<>(dates.length / 2);
        for (int i = 0; i < (dates.length - 1); i += 2) {
            segments.add(new Segment(dates[i], dates[i + 1]));
        }
        return new Flight(segments);
    }
}
