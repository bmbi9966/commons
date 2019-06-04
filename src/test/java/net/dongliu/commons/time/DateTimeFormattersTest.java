package net.dongliu.commons.time;

import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DateTimeFormattersTest {

    @Test
    void testFormat() {
        var instant = Instant.ofEpochSecond(1559616210L, 922886000L);
        var zdt = ZonedDateTime.ofInstant(instant, ZoneIds.asiaShangHai());
        var ldt = LocalDateTime.ofInstant(instant, ZoneIds.asiaShangHai());
        assertEquals("2019-06-04 10:43:30.922886", DateTimeFormatters.ISO_SIMPLE_NANOS.format(zdt));
        assertEquals("2019-06-04 10:43:30.922886", DateTimeFormatters.ISO_SIMPLE_NANOS.format(ldt));
        assertEquals("2019-06-04 10:43:30.922", DateTimeFormatters.ISO_SIMPLE_MILLS.format(zdt));
        assertEquals("2019-06-04 10:43:30.922", DateTimeFormatters.ISO_SIMPLE_MILLS.format(ldt));
        assertEquals("2019-06-04 10:43:30", DateTimeFormatters.ISO_SIMPLE_SECONDS.format(zdt));
        assertEquals("2019-06-04 10:43:30", DateTimeFormatters.ISO_SIMPLE_SECONDS.format(ldt));
    }

}