package net.dongliu.commons;

import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class OptionalsTest {

    @Test
    public void stream() {
        assertEquals(1, Optionals.stream(Optional.of(1)).count());
        assertEquals(0, Optionals.stream(Optional.empty()).count());
    }
}