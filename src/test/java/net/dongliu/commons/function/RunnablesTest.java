package net.dongliu.commons.function;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class RunnablesTest {

    @Test
    void runOnce() {
        Runnable runnable = mock(Runnable.class);
        Runnable r = Runnables.runOnce(runnable);
        r.run();
        verify(runnable).run();
        r.run();
        verify(runnable).run();
    }

    @Test
    void asCallable() throws Exception {
        Runnable runnable = mock(Runnable.class);
        var callable = Runnables.asCallable(runnable);
        assertNull(callable.call());
        verify(runnable).run();
    }
}