package net.dongliu.commons;

import org.junit.Test;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.sql.SQLException;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ThrowablesTest {

    @Test(expected = Exception.class)
    public void sneakyThrow() {
        throw Throwables.sneakyThrow(new Exception());
    }

    @Test
    public void getRootCause() {
        Exception e = new RuntimeException();
        assertEquals(e, Throwables.getRootCause(e));
        Exception e2 = new Exception(e);
        assertEquals(e, Throwables.getRootCause(e2));
        Exception e3 = new Exception(e2);
        assertEquals(e, Throwables.getRootCause(e3));
    }

    @Test
    public void getStackTrace() {
        Exception e = new RuntimeException("my-exception");
        String stackTrace = Throwables.getStackTrace(e);
        assertTrue(stackTrace.contains("java.lang.RuntimeException"));
        assertTrue(stackTrace.contains("my-exception"));
    }

    @Test
    public void getCauseOf() {
        IOException ioe = new IOException();
        RuntimeException re = new RuntimeException(ioe);
        Exception e = new Exception(re);
        assertEquals(Optional.of(e), Throwables.getCauseOf(e, Throwable.class));
        assertEquals(Optional.of(re), Throwables.getCauseOf(e, RuntimeException.class));
        assertEquals(Optional.of(ioe), Throwables.getCauseOf(e, IOException.class));
        assertEquals(Optional.empty(), Throwables.getCauseOf(e, SQLException.class));
    }

    @Test
    public void throwIfUnchecked() {
        Throwables.throwIfUnchecked(new IOException());
    }

    @Test(expected = UncheckedIOException.class)
    public void throwIfUnchecked2() {
        Throwables.throwIfUnchecked(new UncheckedIOException(new IOException()));
    }
}