package com.gridnine.testing.model;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoField;
import java.util.List;
import java.util.stream.Collectors;

public class Flight {

    private final List<Segment> segments;

    public Flight(List<Segment> segments) {
        this.segments = segments;
    }

    public List<Segment> getSegments() {
        return segments;
    }

    @Override
    public String toString() {
        return segments.stream().map(Object::toString)
                .collect(Collectors.joining(" "));
    }

    public long getMaxLandingTime() {

        long maxLandTime = 0;

        for (int i = 0; i < segments.size() - 1; i++) {
            var arrivalTime = segments.get(i).getArrivalDate();
            var departureTime = segments.get(i + 1).getDepartureDate();

            long currentLandTimeInMinutes = Duration.between(arrivalTime, departureTime).toMinutes();

            maxLandTime = Math.max(maxLandTime, currentLandTimeInMinutes);
        }

        return maxLandTime;
    }
}
