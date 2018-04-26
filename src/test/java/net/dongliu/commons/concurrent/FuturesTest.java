package net.dongliu.commons.concurrent;

import org.junit.Test;

import java.util.concurrent.CompletableFuture;

import static org.junit.Assert.assertEquals;

public class FuturesTest {

    @Test
    public void completed() {
        CompletableFuture<Integer> future = Futures.value(1);
        assertEquals(Integer.valueOf(1), future.join());
    }
}