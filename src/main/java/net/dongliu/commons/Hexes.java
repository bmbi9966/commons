package net.dongliu.commons;

import net.dongliu.commons.exception.HexDecodeException;

import static java.util.Objects.requireNonNull;

/**
 * Hex utils.
 */
public class Hexes {

    /**
     * Return a hex encoder.
     *
     * @param uppercase if use upper case hex chars
     * @return a hex encoder
     */
    public static Encoder encoder(boolean uppercase) {
        return uppercase ? decodeUpper.get() : decodeLower.get();
    }

    /**
     * Return a hex encoder which encode bytes to upper case hex string.
     *
     * @return a hex encoder
     */
    public static Encoder encoder() {
        return encoder(true);
    }

    private static final Lazy<Encoder> decodeUpper = Lazy.of(() -> new Encoder(true));
    private static final Lazy<Encoder> decodeLower = Lazy.of(() -> new Encoder(false));

    /**
     * Return a hex decoder
     *
     * @return a hex decoder
     */
    public static Decoder decoder() {
        return decoder.get();
    }

    private static final Lazy<Decoder> decoder = Lazy.of(Decoder::new);

    /**
     * The class for configuring and performing hex encode, using fluent api.
     * This class is immutable, and can be reused.
     */
    public static class Encoder {
        private static final char[] HEX_CHARS_LOWER = {
                '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'a', 'b', 'c', 'd', 'e', 'f'
        };

        private static final char[] HEX_CHARS_UPPER = {
                '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'A', 'B', 'C', 'D', 'E', 'F'
        };
        private final boolean uppercase;

        private Encoder(boolean uppercase) {
            this.uppercase = uppercase;
        }


        /**
         * Converts an array of bytes into a String representing the hexadecimal values of each byte in order.
         *
         * @param data a byte[] to convert to Hex characters
         * @return A String containing hexadecimal characters
         */
        public String encode(byte[] data) {
            requireNonNull(data);
            return encode(data, uppercase ? HEX_CHARS_UPPER : HEX_CHARS_LOWER);
        }

        private String encode(byte[] data, char[] hexChars) {
            StringBuilder sb = new StringBuilder(data.length * 2);
            for (byte b : data) {
                sb.append(hexChars[(0xF0 & b) >> 4]);
                sb.append(hexChars[0x0F & b]);
            }
            return sb.toString();
        }
    }

    /**
     * Hex decoder. This class is immutable.
     */
    public static class Decoder {

        /**
         * Converts a string representing hexadecimal values into an array of bytes of those same values.
         *
         * @param data A String of characters containing hexadecimal digits
         * @return A byte array containing binary data decoded from the supplied char array.
         */
        public byte[] decode(String data) throws HexDecodeException {
            requireNonNull(data);
            return decode((CharSequence) data);
        }

        /**
         * Converts a sequence of chars representing hexadecimal values into an array of bytes of those same values.
         *
         * @param data A sequence of chars containing hexadecimal digits
         * @return A byte array containing binary data decoded from the supplied char array.
         */
        public byte[] decode(CharSequence data) throws HexDecodeException {
            requireNonNull(data);
            int len = data.length();

            if ((len & 0x01) != 0) {
                throw new HexDecodeException("Invalid hex characters with odd len: " + len);
            }

            byte[] result = new byte[len >> 1];

            for (int i = 0; i < result.length; i++) {
                int f = toDigit(data.charAt(2 * i)) << 4;
                f = f | toDigit(data.charAt(2 * i + 1));
                result[i] = (byte) (f & 0xFF);
            }

            return result;
        }

        /**
         * Converts a hexadecimal character to an integer.
         *
         * @param ch A character to convert to an integer digit
         * @return An integer
         */
        private int toDigit(char ch) throws HexDecodeException {
            if ('0' <= ch && ch <= '9') {
                return ch - '0';
            }
            if ('a' <= ch && ch <= 'f') {
                return ch - 'a' + 10;
            }
            if ('A' <= ch && ch <= 'F') {
                return ch - 'A' + 10;
            }
            throw new HexDecodeException("Illegal hexadecimal character " + ch);
        }
    }

}
