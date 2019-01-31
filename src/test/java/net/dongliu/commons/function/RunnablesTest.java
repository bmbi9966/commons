package net.dongliu.commons.function;

import org.junit.jupiter.api.Test;

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

}