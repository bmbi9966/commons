package net.dongliu.commons.concurrent;

import net.dongliu.commons.collection.Lists;
import org.junit.Ignore;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;

public class ThreadPoolBuilderTest {

    @Test
    @Ignore("remove annoying output")
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
        assertEquals(Lists.of("exception1", "exception2"), messages);
    }
}