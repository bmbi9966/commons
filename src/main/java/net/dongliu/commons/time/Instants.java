package net.dongliu.commons.time;

import java.time.*;

import static java.util.Objects.requireNonNull;

/**
 * Utils for instant
 */
public class Instants {

    /**
     * Nanos since epoch
     */
    public static long toEpochNanos(Instant instant) {
        return instant.getEpochSecond() * 1000_000_000L + instant.getNano();
    }

    /**
     * subtract two instant
     */
    public static Duration subtract(Instant minuend, Instant subtrahend) {
        requireNonNull(minuend);
        requireNonNull(subtrahend);
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
        requireNonNull(instant);
        requireNonNull(zoneId);
        return ZonedDateTime.ofInstant(instant, zoneId).toLocalDateTime();
    }

    /**
     * Instant to LocalDateTime with current system zone
     */
    public static LocalDateTime toLocalDateTime(Instant instant) {
        return toLocalDateTime(instant, ZoneId.systemDefault());
    }
}
