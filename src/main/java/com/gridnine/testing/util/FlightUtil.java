package com.gridnine.testing.util;

import com.gridnine.testing.model.Segment;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

public class FlightUtil {

    public static boolean doesLandTimeSuffice(List<Segment> segments, long min, long max) {

        if (segments.size() <= 1) {
            return true;
        }

        long counter = 0L;
        for (int i = 0; i < segments.size() - 1; i++) {
            LocalDateTime arrivalTime = segments.get(i).getArrivalDate();
            LocalDateTime departureTime = segments.get(i + 1).getDepartureDate();

            counter += Duration.between(arrivalTime, departureTime).toMinutes();

            if (counter > max || counter < min) {
                return false;
            }
        }

        return true;
    }
}
