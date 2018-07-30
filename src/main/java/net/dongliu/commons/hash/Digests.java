package net.dongliu.commons.hash;

import net.dongliu.commons.Hexes;
import net.dongliu.commons.Lazy;
import net.dongliu.commons.exception.DigestEncodeException;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import static java.nio.charset.StandardCharsets.UTF_8;
import static java.util.Objects.requireNonNull;

/**
 * Message Digest operations.
 */
public class Digests {
    private static final String ALG_SHA_512 = "SHA-512";
    private static final String ALG_SHA_256 = "SHA-256";
    private static final String ALG_SHA1 = "SHA1";
    private static final String ALG_MD5 = "MD5";

    /**
     * Return a md5 digest encoder.
     *
     * @return md5 digest encoder.
     */
    public static Encoder md5() {
        return md5.get();
    }

    /**
     * Return a sha1 digest encoder.
     *
     * @return sha1 digest encoder.
     */
    public static Encoder sha1() {
        return sha1.get();
    }

    /**
     * Return a sha256 digest encoder.
     *
     * @return sha256 digest encoder.
     */
    public static Encoder sha256() {
        return sha256.get();
    }

    /**
     * Return a sha512 digest encoder.
     *
     * @return sha512 digest encoder.
     */
    public static Encoder sha512() {
        return sha512.get();
    }

    private static final Lazy<Encoder> md5 = Lazy.of(() -> new Encoder(ALG_MD5));
    private static final Lazy<Encoder> sha1 = Lazy.of(() -> new Encoder(ALG_SHA1));
    private static final Lazy<Encoder> sha256 = Lazy.of(() -> new Encoder(ALG_SHA_256));
    private static final Lazy<Encoder> sha512 = Lazy.of(() -> new Encoder(ALG_SHA_512));

    /**
     * For digest encoding.
     * This is immutable class, can be reused.
     */
    public static class Encoder {
        private static final int BULK_SIZE = 1024 * 8;
        private final String algorithm;

        private Encoder(String algorithm) {
            this.algorithm = algorithm;
        }

        /**
         * Calculate digest for string using utf-8 charset.
         *
         * @param str the string
         * @return digest
         */
        public DigestResult digest(String str) {
            return digest(requireNonNull(str), UTF_8);
        }

        /**
         * Calculate digest for string.
         *
         * @param str     the string
         * @param charset the charset to decode string
         * @return digest
         */
        public DigestResult digest(String str, Charset charset) {
            requireNonNull(str);
            requireNonNull(charset);
            return digest(str.getBytes(charset));
        }

        /**
         * Calculate digest for byte array data.
         *
         * @param data the data
         * @return digest
         */
        public DigestResult digest(byte[] data) {
            requireNonNull(data);
            int offset = 0;
            MessageDigest md = messageDigest(algorithm);
            while (offset < data.length) {
                int size = Math.min(BULK_SIZE, data.length - offset);
                md.update(data, offset, size);
                offset += size;
            }
            return new DigestResult(md.digest());
        }

        /**
         * Calculate digest which digest all data in InputStream.
         * The InputSteam is left unclosed when finished or error occurred.
         *
         * @param in the inputStream contains the data
         * @return digest
         */
        public DigestResult digest(InputStream in) throws IOException {
            requireNonNull(in);
            MessageDigest md = messageDigest(algorithm);
            byte[] data = new byte[BULK_SIZE];
            int read;
            while ((read = in.read(data)) != -1) {
                md.update(data, 0, read);
            }
            return new DigestResult(md.digest());
        }

        private static MessageDigest messageDigest(String algorithm) {
            try {
                return MessageDigest.getInstance(algorithm);
            } catch (NoSuchAlgorithmException e) {
                throw new DigestEncodeException(e);
            }
        }
    }


    /**
     * For hold digest result, and convert to result of various types.
     * This class is immutable.
     */
    public static class DigestResult {
        private final byte[] data;

        private DigestResult(byte[] data) {
            this.data = data;
        }

        /**
         * Digest result as bytes.
         *
         * @return digest bytes
         */
        public byte[] asBytes() {
            return data;
        }

        /**
         * Digest result as uppercase hex string.
         *
         * @return hex string
         */
        public String asHex() {
            return asHex(true);
        }

        /**
         * Digest result as hex string.
         *
         * @param uppercase if using upper case hex string
         * @return hex string
         */
        public String asHex(boolean uppercase) {
            return Hexes.encoder(uppercase).encode(data);
        }

        /**
         * Digest result as base64ed value.
         *
         * @return base64ed value
         */
        public String asBase64() {
            return Base64.getEncoder().encodeToString(data);
        }
    }


}
