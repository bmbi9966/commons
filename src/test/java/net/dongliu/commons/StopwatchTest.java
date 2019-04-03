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
        var elapsed = stopwatch.elapsed();
        Thread.sleep(10);
        assertEquals(elapsed, stopwatch.elapsed());

    }

    @Test
    void elapsed() throws InterruptedException {
        var stopwatch = Stopwatch.create().start();
        Thread.sleep(20);
        assertTrue(stopwatch.elapsed().compareTo(Duration.ZERO) > 0);
        stopwatch.stop();
        stopwatch.start();
        assertTrue(stopwatch.elapsed().compareTo(Duration.ofMillis(10)) < 0);

    }

    @Test
    void elapsedNanos() {
    }

    @Test
    void elapsedMillis() throws InterruptedException {
        var stopwatch = Stopwatch.create().start();
        Thread.sleep(20);
        assertTrue(Math.abs(stopwatch.elapsedMillis() - 20) < 5);
        stopwatch.stop();
        stopwatch.start();
        assertTrue(stopwatch.elapsedMillis() < 5);
    }
}