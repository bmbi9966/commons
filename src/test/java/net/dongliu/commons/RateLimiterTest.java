package net.dongliu.commons;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RateLimiterTest {

    @Test
    void test() {
        var rateLimiter = RateLimiter.of(100);
        long begin = System.currentTimeMillis();
        for (int i = 0; i < 10; i++) {
            rateLimiter.acquire();
        }
        var elappsed = System.currentTimeMillis() - begin;
        assertTrue(elappsed >= 100 && elappsed < 200);
    }

}