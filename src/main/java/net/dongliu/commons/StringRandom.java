package net.dongliu.commons;

import java.util.Objects;
import java.util.Random;

import static java.nio.charset.StandardCharsets.US_ASCII;

/**
 * Wrap random for more convenient methods
 */
public class StringRandom {

    private final Random random;

    public StringRandom() {
        this(new Random());
    }

    public StringRandom(Random random) {
        this.random = Objects.requireNonNull(random);
    }

    /**
     * Generate random string only contains ascii printable([20,127)) chars with specific len. The DEL char is excluded.
     */
    public String asciiString(int len) {
        checkStringLen(len);
        if (len == 0) {
            return "";
        }

        var chars = new byte[len];
        for (int i = 0; i < chars.length; i++) {
            chars[i] = (byte) (random.nextInt(127 - 20) + 20);
        }
        return new String(chars, US_ASCII);
    }

    private static byte[] alphanumeric = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz".getBytes(US_ASCII);

    /**
     * Generate random string only contains ascii alphabet and number chars([48,58),[65, 91),[97,123) ]) with specific len.
     */
    public String alphanumericString(int len) {
        checkStringLen(len);
        if (len == 0) {
            return "";
        }

        var chars = new byte[len];
        for (int i = 0; i < chars.length; i++) {
            int index = random.nextInt(alphanumeric.length);
            chars[i] = alphanumeric[index];
        }
        return new String(chars, US_ASCII);
    }

    private void checkStringLen(int len) {
        if (len < 0) {
            throw new IllegalArgumentException("len less than zero: " + len);
        }
    }
}
