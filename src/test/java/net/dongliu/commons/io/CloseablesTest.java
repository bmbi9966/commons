package net.dongliu.commons.io;

import org.junit.jupiter.api.Test;

import java.io.IOException;

public class CloseablesTest {

    @Test
    public void closeQuietly() {
        AutoCloseable closeable = () -> {
            throw new IOException();
        };
        Closeables.closeQuietly(closeable);
    }
}