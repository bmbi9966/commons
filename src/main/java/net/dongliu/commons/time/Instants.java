package net.dongliu.commons.time;

import java.time.*;
import java.util.Objects;

/**
 * Utils for instant
 */
public class Instants {

    /**
     * subtract two instant
     */
    public static Duration subtract(Instant minuend, Instant subtrahend) {
        Objects.requireNonNull(minuend);
        Objects.requireNonNull(subtrahend);
        if (minuend.equals(subtrahend)) {
            return Duration.ZERO;
        }
        return Duration.ofSeconds(minuend.getEpochSecond() - subtrahend.getEpochSecond(),
                minuend.getNano() - subtrahend.getNano());
    }

    /**
     * Instant to LocalDateTime with specified zone
     */
    public static LocalDateTime toLocalDateTime(Instant instant, ZoneId zoneId) {
        Objects.requireNonNull(instant);
        return ZonedDateTime.ofInstant(instant, zoneId).toLocalDateTime();
    }

    /**
     * Instant to LocalDateTime with current system zone
     */
    public static LocalDateTime toLocalDateTime(Instant instant) {
        return toLocalDateTime(instant, ZoneId.systemDefault());
    }
}
