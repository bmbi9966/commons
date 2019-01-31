package net.dongliu.commons.function;

import java.util.function.Consumer;

/**
 * Utils for Consumer
 */
public class Consumers {

    private static final Consumer doNothing = t -> {};

    /**
     * Return a consumer that do noting.
     */
    @SuppressWarnings("unchecked")
    public static <T> Consumer<T> doNothing() {
        return (Consumer<T>) doNothing;
    }
}
