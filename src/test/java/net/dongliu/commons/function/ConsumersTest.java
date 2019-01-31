package net.dongliu.commons.function;

import org.junit.jupiter.api.Test;

import java.util.function.Consumer;

import static org.junit.jupiter.api.Assertions.*;

class ConsumersTest {

    @Test
    void doNothing() {
        Consumer<String> c = Consumers.doNothing();
        c.accept("");
    }
}