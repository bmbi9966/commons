package net.dongliu.commons.concurrent;

import net.dongliu.commons.Lazy;
import net.dongliu.commons.collection.Lists;
import net.dongliu.commons.collection.Pair;
import net.dongliu.commons.collection.Triple;

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

    private static final Lazy<ScheduledExecutorService> futureScheduleExecutor = Lazy.of(
            () -> new ScheduledThreadPoolExecutor(1, ThreadFactories.newDaemonThreadFactory("delay-executor")));

    /**
     * Wait until future finished, and return the value.
     * If error occurred, throw unchecked exception.
     * If is interrupted, a CancellationException is thrown, and the interrupt bit is set for this thread.
     *
     * @param future the future
     * @param <T>    the future value type
     * @return the future value
     *
     * @throws CancellationException if future is canceled, or get operation is interrupted
     * @throws CompletionException if exception is thrown during future compute
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
     * Alias for CompletableFuture.completedFuture
     *
     * @param result the value
     * @param <T>    the value type
     * @return the completed CompletableFuture
     */
    public static <T> CompletableFuture<T> just(T result) {
        return CompletableFuture.completedFuture(result);
    }

    /**
     * Alias for CompletableFuture.failedFuture
     *
     * @param throwable the throwable
     * @param <T>       the value type
     * @return the failed CompletableFuture
     */
    public static <T> CompletableFuture<T> error(Throwable throwable) {
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
        futureScheduleExecutor.get().schedule(() -> f.complete(value), duration.toMillis(), MILLISECONDS);
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
        ScheduledFuture<?> sf = futureScheduleExecutor.get().schedule(() -> {
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

    /**
     * Combine two futures to one future return a Pair Future.
     * If either future failed with a exception, the return future failed.
     */
    public static <S, T> CompletableFuture<Pair<S, T>> combine(CompletableFuture<S> future1,
                                                               CompletableFuture<T> future2) {
        requireNonNull(future1);
        requireNonNull(future2);
        return future1.thenCombine(future2, Pair::of);
    }

    /**
     * Combine three futures to one future return a Triple Future.
     * If either future failed with a exception, the return future failed.
     */
    public static <A, B, C> CompletableFuture<Triple<A, B, C>> combine(CompletableFuture<A> future1,
                                                                       CompletableFuture<B> future2,
                                                                       CompletableFuture<C> future3) {
        requireNonNull(future1);
        requireNonNull(future2);
        requireNonNull(future3);
        return CompletableFuture.allOf(future1, future2, future3)
                .thenApply(ignore -> Triple.of(future1.join(), future2.join(), future3.join()));
    }
}
