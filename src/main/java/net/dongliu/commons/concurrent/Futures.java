package net.dongliu.commons.concurrent;

import net.dongliu.commons.Lazy;
import net.dongliu.commons.collection.Lists;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;

import static java.util.Objects.requireNonNull;
import static java.util.concurrent.TimeUnit.MILLISECONDS;

/**
 * Utils method for futures.
 */
public class Futures {

    private static final Lazy<ScheduledExecutorService> executorService = Lazy.of(
            () -> new ScheduledThreadPoolExecutor(1, ThreadFactories.newDaemonThreadFactory("delay-executor")));

    /**
     * Just future get value, but throwing uncheck exception.
     *
     * @param future the future
     * @param <T>    the future value type
     * @return the future value
     */
    public static <T> T join(Future<T> future) {
        if (future instanceof CompletableFuture) {
            return ((CompletableFuture<T>) future).join();
        }
        try {
            return future.get();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new CancellationException();
        } catch (ExecutionException e) {
            throw new CompletionException(e.getCause());
        }
    }

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

    /**
     * Create a new Future, which delay some time after the original future complete. If the original future failed,
     * delay is not added.
     *
     * @param future   the original future
     * @param duration delay time
     * @param <T>      value type
     * @return the new future with delay
     */
    public static <T> CompletableFuture<T> delay(CompletableFuture<T> future, Duration duration) {
        requireNonNull(future);
        requireNonNull(duration);
        return future.thenCompose(v -> delay(v, duration));
    }

    /**
     * Create a new Future, which delay some time to complete.
     *
     * @param value    the future
     * @param duration delay time
     * @param <T>      value type
     * @return the new future with delay
     */
    public static <T> CompletableFuture<T> delay(T value, Duration duration) {
        requireNonNull(duration);
        CompletableFuture<T> f = new CompletableFuture<>();
        executorService.get().schedule(() -> f.complete(value), duration.toMillis(), MILLISECONDS);
        return f;
    }

    /**
     * Set timeout for wait future to complete. Throw TimeoutException if not complete when timeout reached.
     * Java9+ CompletableFuture already has this method.
     *
     * @param future   the original future
     * @param duration timeout time
     * @param <T>      value type
     * @return the new future with timeout
     */
    public static <T> CompletableFuture<T> timeout(CompletableFuture<T> future, Duration duration) {
        requireNonNull(future);
        requireNonNull(duration);
        CompletableFuture<T> f = new CompletableFuture<>();
        ScheduledFuture<?> sf = executorService.get().schedule(() -> {
            if (!future.isDone()) {
                future.cancel(true);
                f.completeExceptionally(new TimeoutException());
            }
        }, duration.toMillis(), MILLISECONDS);
        future.thenAccept(v -> {
            sf.cancel(true);
            f.complete(v);
        }).exceptionally(e -> {
            sf.cancel(true);
            f.completeExceptionally(e);
            return null;
        });
        return f;
    }


    /**
     * Wait all future finished, and return a new Future hold the result. If any future failed, the new future failed.
     *
     * @param futures the futures
     * @param <T>     the value type of future
     * @return new future
     */
    @SafeVarargs
    public static <T> CompletableFuture<List<T>> allOf(CompletableFuture<T>... futures) {
        requireNonNull(futures);
        if (futures.length == 0) {
            throw new IllegalArgumentException("no future");
        }
        return CompletableFuture.allOf(futures)
                .thenApply(none -> Lists.convert(Arrays.asList(futures), CompletableFuture::join));
    }

    /**
     * Wait till any one finished, and return a new Future hold the result.
     * If the finished future failed, the new future failed.
     *
     * @param futures the futures
     * @param <T>     the value type of future
     * @return new future
     */
    @SafeVarargs
    public static <T> CompletableFuture<T> anyOf(CompletableFuture<T>... futures) {
        requireNonNull(futures);
        if (futures.length == 0) {
            throw new IllegalArgumentException("no future");
        }
        return CompletableFuture.anyOf(futures)
                .thenCompose(none -> Arrays.stream(futures).filter(CompletableFuture::isDone)
                        .findAny()
                        .orElseThrow(() -> new RuntimeException("should not happen")));
    }
}
