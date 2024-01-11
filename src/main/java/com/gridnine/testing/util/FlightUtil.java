package com.gridnine.testing.util;

import com.gridnine.testing.model.Segment;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

public class FlightUtil {

    public static boolean isLandTimeExceeded(List<Segment> segments, long max) {

        if (segments.size() <= 1) {
            return true;
        }

        long counter = 0L;

        for (int i = 0; i < segments.size() - 1; i++) {
            LocalDateTime arrivalTime = segments.get(i).getArrivalDate();
            LocalDateTime departureTime = segments.get(i + 1).getDepartureDate();

            counter += Duration.between(arrivalTime, departureTime).toMinutes();

            if (counter > max) {
                return false;
            }
        }

        return true;
    }

    public static boolean isLandTimeLessThanNeeded(List<Segment> segments, long min) {

        if (segments.size() <= 1) {
            return false;
        }

        long counter = 0L;

        for (int i = 0; i < segments.size() - 1; i++) {
            var arrivalTime = segments.get(i).getArrivalDate();
            var departureTime = segments.get(i + 1).getDepartureDate();

            counter += Duration.between(arrivalTime, departureTime).toMinutes();

            if (counter < min) {
                return false;
            }
        }

        return true;
    }
}
