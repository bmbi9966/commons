package net.dongliu.commons.function;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.function.Consumer;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class ConsumersTest {

    @Test
    void doNothing() {
        Consumer<String> c = Consumers.doNothing();
        c.accept("");
    }

    @Test
    @SuppressWarnings("unchecked")
    void adapterIndexed() {
        IndexedConsumer<String> consumer = mock(IndexedConsumer.class);
        Mockito.doNothing().when(consumer).accept(anyInt(), any());
        var c = Consumers.adapterIndexed(consumer);
        c.accept("1");
        verify(consumer).accept(eq(0L), eq("1"));
        c.accept("2");
        verify(consumer).accept(eq(1L), eq("2"));
        c.accept("3");
        verify(consumer).accept(eq(2L), eq("3"));

    }
}