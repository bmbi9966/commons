package net.dongliu.commons;

import org.junit.Test;

public class ThrowablesTest {

    @Test(expected = Exception.class)
    public void sneakyThrow() {
        Throwables.sneakyThrow(new Exception());
    }
}