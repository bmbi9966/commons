package net.dongliu.commons.sequence;

class Utils {
    static void checkSize(int size) {
        if (size <= 0) {
            throw new IllegalArgumentException("size " + size + " should larger than zero");
        }
    }

    static void checkCount(long count) {
        if (count < 0) {
            throw new IllegalArgumentException("count " + count + " should larger than or equal with zero");
        }
    }
}
