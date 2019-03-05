package net.dongliu.commons;

import org.junit.jupiter.api.Test;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

class StopwatchTest {

    @Test
    void create() {
    }

    @Test
    void start() {
        var stopwatch = Stopwatch.create().start();
        assertThrows(IllegalStateException.class, stopwatch::start);
        stopwatch.stop();
        stopwatch.start();
    }

    @Test
    void stop() throws InterruptedException {
        var stopwatch = Stopwatch.create();
        assertThrows(IllegalStateException.class, stopwatch::stop);
        stopwatch.start();
        stopwatch.stop();
        var elasped = stopwatch.elasped();
        Thread.sleep(10);
        assertEquals(elasped, stopwatch.elasped());

    }

    @Test
    void elasped() throws InterruptedException {
        var stopwatch = Stopwatch.create().start();
        Thread.sleep(20);
        assertTrue(stopwatch.elasped().compareTo(Duration.ZERO) > 0);
        stopwatch.stop();
        stopwatch.start();
        assertTrue(stopwatch.elasped().compareTo(Duration.ofMillis(10)) < 0);

    }

    @Test
    void elapsedNanos() {
    }

    @Test
    void elaspedMillis() {
    }
}