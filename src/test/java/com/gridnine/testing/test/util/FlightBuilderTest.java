package com.gridnine.testing.test.util;

import com.gridnine.testing.model.Flight;
import com.gridnine.testing.model.Segment;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class FlightBuilderTest {

    private final static LocalDateTime THREE_DAYS_FROM_NOW = LocalDateTime.now().plusDays(3);

    public static List<Flight> createIdealFlights() {
        List<Flight> result = new ArrayList<>();
        // add a normal flight with two hour duration
        result.add(createFlight(THREE_DAYS_FROM_NOW, THREE_DAYS_FROM_NOW.plusHours(2)));
        // add a normal multi segment flight
        result.add(createFlight(THREE_DAYS_FROM_NOW, THREE_DAYS_FROM_NOW.plusHours(2),
                THREE_DAYS_FROM_NOW.plusHours(3), THREE_DAYS_FROM_NOW.plusHours(5)));

        return result;
    }

    public static List<Flight> createFlightsWithoutDepartingInPast() {
        List<Flight> result = createIdealFlights();
        // add a flight where arrival is before departure
        result.add(createFlight(THREE_DAYS_FROM_NOW, THREE_DAYS_FROM_NOW.minusHours(6)));
        // Add a flight with more than two hours ground time
        result.add(createFlight(THREE_DAYS_FROM_NOW, THREE_DAYS_FROM_NOW.plusHours(2),
                        THREE_DAYS_FROM_NOW.plusHours(5), THREE_DAYS_FROM_NOW.plusHours(6)));
        //Add another flight with more than two hours ground time
        result.add(createFlight(THREE_DAYS_FROM_NOW, THREE_DAYS_FROM_NOW.plusHours(2),
                THREE_DAYS_FROM_NOW.plusHours(3), THREE_DAYS_FROM_NOW.plusHours(4),
                THREE_DAYS_FROM_NOW.plusHours(6), THREE_DAYS_FROM_NOW.plusHours(7)));

        return result;
    }

    public static List<Flight> createFlightsWithoutDepartingBeforeArriving() {
        List<Flight> result = createIdealFlights();

        //Add a flight departing in the past
        result.add(createFlight(THREE_DAYS_FROM_NOW.minusDays(6), THREE_DAYS_FROM_NOW));
        //Add a flight with more than two hours ground time
        result.add(createFlight(THREE_DAYS_FROM_NOW, THREE_DAYS_FROM_NOW.plusHours(2),
                THREE_DAYS_FROM_NOW.plusHours(5), THREE_DAYS_FROM_NOW.plusHours(6)));
        //Add another flight with more than two hours ground time
        result.add(createFlight(THREE_DAYS_FROM_NOW, THREE_DAYS_FROM_NOW.plusHours(2),
                THREE_DAYS_FROM_NOW.plusHours(3), THREE_DAYS_FROM_NOW.plusHours(4),
                THREE_DAYS_FROM_NOW.plusHours(6), THREE_DAYS_FROM_NOW.plusHours(7)));

        return result;
    }

    public static List<Flight> createFlightsWithoutLandingTimeExceeding() {
        List<Flight> result = createIdealFlights();

        //Add a flight departing in the past
        result.add( createFlight(THREE_DAYS_FROM_NOW.minusDays(6), THREE_DAYS_FROM_NOW));
        //Add a flight that departs before it arrives
        result.add(createFlight(THREE_DAYS_FROM_NOW, THREE_DAYS_FROM_NOW.minusHours(6)));

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
