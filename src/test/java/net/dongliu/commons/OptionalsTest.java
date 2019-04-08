package net.dongliu.commons;

import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class OptionalsTest {

    @Deprecated
    @Test
    public void stream() {
        assertEquals(1, Optionals.stream(Optional.of(1)).count());
        assertEquals(0, Optionals.stream(Optional.empty()).count());
    }

    @SuppressWarnings("deprecation")
    @Test
    void or() {
        assertEquals(Optional.of(1), Optionals.or(Optional.of(1), Optional.empty()));
        assertEquals(Optional.of(1), Optionals.or(Optional.of(1), Optional.of(2)));
        assertEquals(Optional.of(2), Optionals.or(Optional.empty(), Optional.of(2)));
        assertEquals(Optional.empty(), Optionals.or(Optional.empty(), Optional.empty()));

        assertEquals(Optional.of(1), Optionals.or(Optional.of(1), Optional::empty));
        assertEquals(Optional.of(1), Optionals.or(Optional.of(1), () -> Optional.of(2)));
        assertEquals(Optional.of(2), Optionals.or(Optional.empty(), () -> Optional.of(2)));
        assertEquals(Optional.empty(), Optionals.or(Optional.empty(), Optional::empty));
    }
}