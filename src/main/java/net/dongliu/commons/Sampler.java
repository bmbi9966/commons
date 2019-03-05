package net.dongliu.commons;

import java.util.Random;

import static java.util.Objects.requireNonNull;

/**
 * Utils for run code by sampling.
 */
public abstract class Sampler {

    /**
     * Return a sampler sample by random
     *
     * @param rate the rate to do really operation.
     *             A rate less then 0 never run the code, while a rate larger than or equal 1 always run.
     */
    public static Sampler random(double rate) {
        return random(new Random(), rate);
    }

    /**
     * Return a random sampler, with specific random instance, and rate
     *
     * @param random the random instance
     * @param rate   the rate to do really operation.
     *               A rate less then 0 never run the code, while a rate larger than or equal 1 always run.
     */
    public static Sampler random(Random random, double rate) {
        requireNonNull(random);
        return new RandomSampler(random, rate);
    }

    /**
     * Return a sampler sample by round robin
     *
     * @param rate the rate to do really operation.
     *             A rate less then 0 never run the code, while a rate larger than or equal 1 always run.
     */
    public static Sampler roundRobin(double rate) {
        return new RoundRobinSampler(rate);
    }

    /**
     * Run code by sampling
     */
    public void run(Runnable runnable) {
        if (shouldRun()) {
            runnable.run();
        }
    }

    /**
     * If should really run for this time
     */
    public abstract boolean shouldRun();


    private static class RandomSampler extends Sampler {
        private final Random random;
        private final Double rate;

        private RandomSampler(Random random, Double rate) {
            this.random = random;
            this.rate = rate;
        }

        @Override
        public boolean shouldRun() {
            double v = random.nextDouble();
            return v < rate;
        }
    }

    private static class RoundRobinSampler extends Sampler {
        private final double rate;
        private double current;

        private RoundRobinSampler(Double rate) {
            this.rate = rate;
        }

        @Override
        public boolean shouldRun() {
            if (rate <= 0.0) {
                return false;
            }
            if (rate >= 1.0) {
                return true;
            }

            synchronized (this) {
                current += rate;
                if (current > 1) {
                    current = current - 1;
                    return true;
                }
            }
            return false;
        }
    }
}
