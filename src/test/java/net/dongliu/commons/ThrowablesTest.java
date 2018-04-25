package net.dongliu.commons;

import org.junit.Test;

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
}