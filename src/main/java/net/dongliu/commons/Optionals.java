package net.dongliu.commons;

import java.util.Optional;
import java.util.stream.Stream;

/**
 * Utils method for Optional
 */
public class Optionals {

    /**
     * Convert Optional to Stream
     */
    public static <T> Stream<T> toStream(Optional<T> optional) {
        return optional.map(Stream::of).orElseGet(Stream::empty);
    }
}
