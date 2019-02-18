package net.dongliu.commons.concurrent;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ThreadPoolBuilderTest {

    @Test
    @Disabled("remove annoying output")
    public void taskExceptionListener() throws InterruptedException {
        List<String> messages = new ArrayList<>();
        ThreadPoolExecutor executor = new ThreadPoolBuilder()
                .poolSize(1, 1)
                .taskExceptionListener((r, e) -> messages.add(e.getMessage()))
                .build();
        executor.execute(() -> {
            throw new RuntimeException("exception1");
        });
        executor.submit(() -> {
            throw new RuntimeException("exception2");
        });
        executor.shutdown();
        executor.awaitTermination(1, TimeUnit.SECONDS);
        assertEquals(List.of("exception1", "exception2"), messages);
    }
}