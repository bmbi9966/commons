package net.dongliu.commons.time;

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;

import static java.time.format.DateTimeFormatter.ISO_LOCAL_DATE;
import static java.time.format.DateTimeFormatter.ISO_LOCAL_TIME;
import static java.time.temporal.ChronoField.*;

/**
 * Common {@link DateTimeFormatter}s
 */
public class DateTimeFormatters {

    /**
     * A simple DateTimeFormatter use iso 8601 format but omit the 'T' char and the timezone part.
     */
    public static final DateTimeFormatter ISO_SIMPLE_NANOS = new DateTimeFormatterBuilder()
            .append(ISO_LOCAL_DATE)
            .appendLiteral(' ')
            .append(ISO_LOCAL_TIME)
            .toFormatter();


    /**
     * A simple DateTimeFormatter use iso 8601 format but omit the 'T' char and the timezone part.
     */
    public static final DateTimeFormatter ISO_SIMPLE_MILLS = new DateTimeFormatterBuilder()
            .append(ISO_LOCAL_DATE)
            .appendLiteral(' ')
            .appendValue(HOUR_OF_DAY, 2)
            .appendLiteral(':')
            .appendValue(MINUTE_OF_HOUR, 2)
            .appendLiteral(':')
            .appendValue(SECOND_OF_MINUTE, 2)
            .appendFraction(MILLI_OF_SECOND, 0, 3, true)
            .toFormatter();

    /**
     * A simple DateTimeFormatter use iso 8601 format but omit the 'T' char and the timezone part.
     */
    public static final DateTimeFormatter ISO_SIMPLE_SECONDS = new DateTimeFormatterBuilder()
            .append(ISO_LOCAL_DATE)
            .appendLiteral(' ')
            .appendValue(HOUR_OF_DAY, 2)
            .appendLiteral(':')
            .appendValue(MINUTE_OF_HOUR, 2)
            .appendLiteral(':')
            .appendValue(SECOND_OF_MINUTE, 2)
            .toFormatter();
}
