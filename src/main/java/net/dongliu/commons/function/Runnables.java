package net.dongliu.commons.function;

import static java.util.Objects.requireNonNull;

/**
 * Utils for Runnable
 */
public class Runnables {

    /**
     * Return a thread-safe runnable which only run once.
     * <p>
     * The runnable is guaranteed to finish executing when call memoized runnable finished.
     * </p>
     * <p>
     * The passed in runnable should not  throw exception.
     * If error occurred when run the code, the exception would be thrown, and the next call will run the code again.
     * </p>
     */
    public static Runnable runOnce(Runnable runnable) {
        requireNonNull(runnable);
        if (runnable instanceof OnceRunnable) {
            return runnable;
        }
        return new OnceRunnable(runnable);
    }

    private static class OnceRunnable implements Runnable {
        private final Runnable runnable;
        private volatile boolean run;

        private OnceRunnable(Runnable runnable) {
            this.runnable = runnable;
        }

        @Override
        public void run() {
            if (!run) {
                synchronized (this) {
                    if (!run) {
                        runnable.run();
                        run = true;
                    }
                }
            }
        }
    }
}
