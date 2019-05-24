package net.dongliu.commons.function;

import java.util.function.Predicate;

/**
 * Utils for Predicate
 */
public class Predicates {

    /**
     * Return a always true Predicate
     *
     * @param <T> the value type
     */
    public static <T> Predicate<T> alwaysTrue() {
        return ignore -> true;
    }

    /**
     * Return a always false Predicate
     *
     * @param <T> the value type
     */
    public static <T> Predicate<T> alwaysfalse() {
        return ignore -> false;
    }
}
