package net.dongliu.commons.concurrent;

import java.util.concurrent.CompletableFuture;

/**
 * Utils method for futures.
 */
public class Futures {


    /**
     * Just alias for CompletableFuture.completedFuture
     *
     * @param result the value
     * @param <T>    the value type
     * @return the completed CompletableFuture
     */
    public static <T> CompletableFuture<T> value(T result) {
        return CompletableFuture.completedFuture(result);
    }

    /**
     * Just alias for CompletableFuture.failedFuture
     *
     * @param throwable the throwable
     * @param <T>       the value type
     * @return the failed CompletableFuture
     */
    public static <T> CompletableFuture<T> failed(Throwable throwable) {
        CompletableFuture<T> completableFuture = new CompletableFuture<>();
        completableFuture.completeExceptionally(throwable);
        return completableFuture;
    }
}
