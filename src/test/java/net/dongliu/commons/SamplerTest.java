package net.dongliu.commons;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SamplerTest {

    @Test
    void random() {
    }

    @Test
    void roundRobin() {
        var sampler = Sampler.roundRobin(0.7);
        assertFalse(sampler.shouldRun());
        assertTrue(sampler.shouldRun());
        assertTrue(sampler.shouldRun());
        assertFalse(sampler.shouldRun());
    }
}