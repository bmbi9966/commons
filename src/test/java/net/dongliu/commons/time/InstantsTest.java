package net.dongliu.commons.time;

import org.junit.jupiter.api.Test;

import java.time.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

class InstantsTest {

    @Test
    void subtract() {
        assertEquals(Duration.ofSeconds(1, 1),
                Instants.subtract(Instant.ofEpochSecond(1000, 100), Instant.ofEpochSecond(999, 99)));
        assertEquals(Duration.ofSeconds(0, 999999900),
                Instants.subtract(Instant.ofEpochSecond(1000, 100), Instant.ofEpochSecond(999, 200)));
    }

    @Test
    void toLocalDateTime() {
        assertEquals(LocalDateTime.of(1999, 12, 1, 12, 1, 1, 0),
                Instants.toLocalDateTime(Instant.ofEpochSecond(944020861L), ZoneOffset.ofHours(8)));
    }
}