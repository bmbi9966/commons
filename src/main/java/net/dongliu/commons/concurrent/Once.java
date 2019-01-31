package net.dongliu.commons.concurrent;

/**
 * Run block of code only once, even if runnable code throw exception.
 * The following call to this will block until the first call finished, normally or exception thrown.
 * This class is thread-safe.
 *
 * @deprecated using {@link net.dongliu.commons.function.Runnables#runOnce(Runnable)}
 */
@Deprecated
public class Once {

    private volatile boolean run;

    private Once() {
    }

    /**
     * Create new Once instance
     */
    public static Once create() {
        return new Once();
    }

    /**
     * Run a runnable, only when this method of the instance is called first time.
     *
     * @param runnable the code to be run
     * @return true if is call first time, and the runnable is run; false if not.
     */
    public boolean run(Runnable runnable) {
        if (!run) {
            synchronized (this) {
                if (!run) {
                    try {
                        runnable.run();
                        return true;
                    } finally {
                        run = true;
                    }
                }
            }
        }
        return false;
    }
}
