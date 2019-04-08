package net.dongliu.commons;

import java.util.Random;

/**
 * Utils for random. This utils always use a plain {@link Random } instance in whole process.
 */
public class Randoms {

    private static Random random = new Random();

    private static final class RandomHolder {
        private static StringRandom stringRandom = new StringRandom(random);
    }

    private Randoms() {
    }

    /**
     * @see Random#nextInt()
     */
    public static int nextInt() {
        return random.nextInt();
    }

    /**
     * @see Random#nextInt(int)
     */
    public static int nextInt(int bound) {
        return random.nextInt(bound);
    }

    /**
     * return a random int value in range [begin, end).
     *
     * @param begin random int range begin (inclusive)
     * @param end   random int range end (excluded)
     */
    public static int nextInt(int begin, int end) {
        if (begin >= end) {
            throw new IllegalArgumentException("invalid range bounds, begin: " + begin + ", end: " + end);
        }
        return begin + random.nextInt(end - begin);
    }

    /**
     * @see Random#nextLong()
     */
    public static long nextLong() {
        return random.nextLong();
    }

    /**
     * @see Random#nextFloat()
     */
    public static double nextFloat() {
        return random.nextFloat();
    }

    /**
     * @see Random#nextDouble()
     */
    public static double nextDouble() {
        return random.nextDouble();
    }

    /**
     * @see Random#nextBoolean()
     */
    public static boolean nextBoolean() {
        return random.nextBoolean();
    }

    /**
     * @see Random#nextBytes(byte[])
     */
    public static void nextBytes(byte[] bytes) {
        random.nextBytes(bytes);
    }

    /**
     * Return a byte array with len, filled by random data.
     */
    public static byte[] bytes(int len) {
        if (len < 0) {
            throw new IllegalArgumentException("invalid byte array len: " + len);
        }
        if (len == 0) {
            return new byte[0];
        }
        byte[] bytes = new byte[len];
        random.nextBytes(bytes);
        return bytes;
    }

    /**
     * @see StringRandom#asciiString(int)
     */
    public static String asciiString(int len) {
        return RandomHolder.stringRandom.asciiString(len);
    }

    /**
     * @see StringRandom#alphanumericString(int)
     */
    public static String alphanumericString(int len) {
        return RandomHolder.stringRandom.alphanumericString(len);
    }
}
