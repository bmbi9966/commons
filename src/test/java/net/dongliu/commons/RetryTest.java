package net.dongliu.commons;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RetryTest {

    @Test
    void test() {
        int[] ia = {0};
        Retry.of(5).run(() -> ia[0]++);
        assertEquals(1, ia[0]);

        ia[0] = 0;
        Retry.of(5).run(new Runnable() {
            private int count;

            @Override
            public void run() {
                count++;
                if (count <= 4) {
                    throw new RuntimeException();
                }
            }
        });

        assertThrows(RuntimeException.class, () -> Retry.of(5).run(new Runnable() {
            private int count;

            @Override
            public void run() {
                count++;
                if (count <= 5) {
                    throw new RuntimeException();
                }
            }
        }));
    }

    @Test
    void call() {
        int[] ia = {0};
        assertEquals(Integer.valueOf(0), Retry.of(5).call(() -> ia[0]++));
        assertEquals(1, ia[0]);
    }

}