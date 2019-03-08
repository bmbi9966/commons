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
     * @param rate the rate to do really operation, should be [0,1]
     * @throws IllegalArgumentException if rate value is illegal
     */
    public static Sampler random(double rate) {
        return random(new Random(), checkRate(rate));
    }

    /**
     * Return a random sampler, with specific random instance, and rate
     *
     * @param random the random instance
     * @param rate   the rate to do really operation, should be [0,1]
     * @throws IllegalArgumentException if rate value is illegal
     */
    public static Sampler random(Random random, double rate) {
        requireNonNull(random);
        return new RandomSampler(random, checkRate(rate));
    }

    /**
     * Return a sampler sample by round robin
     *
     * @param rate the rate to do really operation, should be [0,1]
     * @throws IllegalArgumentException if rate value is illegal
     */
    public static Sampler roundRobin(double rate) {
        return new RoundRobinSampler(checkRate(rate));
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

    private static double checkRate(double rate) {
        if (rate < 0 || rate > 1.0) {
            throw new IllegalArgumentException("illegal rate value: " + rate);
        }
        return rate;
    }

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
