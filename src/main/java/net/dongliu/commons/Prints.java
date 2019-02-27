package net.dongliu.commons;


import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.Iterator;
import java.util.Map;

import static java.util.Objects.requireNonNull;

/**
 * Utils for print. The static method are delegated to {@link Printer}
 */
public class Prints {

    private static final Printer defaultPrinter = new Printer(" ", System.lineSeparator(), System.out);

    /**
     * @see Printer#print(Object)
     */
    public static void print(@Nullable Object value) {
        defaultPrinter.print(value);
    }

    /**
     * @see Printer#print(Object, Object)
     */
    public static void print(@Nullable Object value1, @Nullable Object value2) {
        defaultPrinter.print(value1, value2);
    }

    /**
     * @see Printer#print(Object, Object, Object)
     */
    public static void print(@Nullable Object value1, @Nullable Object value2, @Nullable Object value3) {
        defaultPrinter.print(value1, value2, value3);
    }

    /**
     * @see Printer#print(Object, Object, Object, Object)
     */
    public static void print(@Nullable Object value1, @Nullable Object value2, @Nullable Object value3,
                             @Nullable Object value4) {
        defaultPrinter.print(value1, value2, value3, value4);
    }

    /**
     * @see Printer#print(Object, Object, Object, Object, Object)
     */
    public static void print(@Nullable Object value1, @Nullable Object value2, @Nullable Object value3,
                             @Nullable Object value4, @Nullable Object value5) {
        defaultPrinter.print(value1, value2, value3, value4, value5);
    }

    /**
     * @see Printer#print(Object...)
     */
    public static void print(@Nullable Object @NonNull ... values) {
        defaultPrinter.print(values);
    }

    /**
     * @see Printer#printValues(Iterable)
     */
    public void printValues(@NonNull Iterable<@Nullable ?> iterable) {
        defaultPrinter.printValues(iterable);
    }

    /**
     * @see Printer#printMap(Map)
     */
    public void printMap(@NonNull Map<@Nullable ?, @Nullable ?> map) {
        defaultPrinter.printMap(map);
    }

    /**
     * @see Printer#sep(String)
     */
    public static Printer sep(String sep) {
        requireNonNull(sep);
        return defaultPrinter.sep(sep);
    }

    /**
     * @see Printer#end(String)
     */
    public static Printer end(String end) {
        requireNonNull(end);
        return defaultPrinter.end(end);
    }

    /**
     * @see Printer#out(Appendable)
     */
    public static Printer out(Appendable appendable) {
        requireNonNull(appendable);
        return defaultPrinter.out(appendable);
    }

    /**
     * Immutable class with setting for printing.
     */
    public static class Printer {
        private final String sep;
        private final String end;
        private final Appendable out;

        private Printer(String sep, String end, Appendable out) {
            this.sep = sep;
            this.end = end;
            this.out = out;
        }

        /**
         * Print one value
         */
        public void print(@Nullable Object value) {
            synchronized (out) {
                write(String.valueOf(value));
                write(end);
            }
        }

        /**
         * Print values
         */
        public void print(@Nullable Object value1, @Nullable Object value2) {
            synchronized (out) {
                write(String.valueOf(value1));
                write(sep);
                write(String.valueOf(value2));
                write(end);
            }
        }

        /**
         * Print values
         */
        public void print(@Nullable Object value1, @Nullable Object value2, @Nullable Object value3) {
            synchronized (out) {
                write(String.valueOf(value1));
                write(sep);
                write(String.valueOf(value2));
                write(sep);
                write(String.valueOf(value3));
                write(end);
            }
        }

        /**
         * Print values
         */
        public void print(@Nullable Object value1, @Nullable Object value2, @Nullable Object value3,
                          @Nullable Object value4) {
            synchronized (out) {
                write(String.valueOf(value1));
                write(sep);
                write(String.valueOf(value2));
                write(sep);
                write(String.valueOf(value3));
                write(sep);
                write(String.valueOf(value4));
                write(end);
            }
        }

        /**
         * Print values
         */
        public void print(@Nullable Object value1, @Nullable Object value2, @Nullable Object value3,
                          @Nullable Object value4, @Nullable Object value5) {
            synchronized (out) {
                write(String.valueOf(value1));
                write(sep);
                write(String.valueOf(value2));
                write(sep);
                write(String.valueOf(value3));
                write(sep);
                write(String.valueOf(value4));
                write(sep);
                write(String.valueOf(value5));
                write(end);
            }
        }

        /**
         * Print values
         *
         * @param values the values
         */
        public void print(@Nullable Object @NonNull ... values) {
            requireNonNull(values);
            if (values.length == 0) {
                return;
            }

            synchronized (out) {
                for (int i = 0; i < values.length; i++) {
                    write(String.valueOf(values[i]));
                    if (i < values.length - 1) {
                        write(sep);
                    }
                }
                write(end);
            }
        }

        /**
         * Print iterable values, with sep and end.
         *
         * @param iterable cannot be null
         */
        public void printValues(@NonNull Iterable<@Nullable ?> iterable) {
            requireNonNull(iterable);
            synchronized (out) {
                Iterator<?> iterator = iterable.iterator();
                boolean first = true;
                while (iterator.hasNext()) {
                    if (!first) {
                        write(sep);
                    }
                    Object value = iterator.next();
                    write(String.valueOf(value));
                    first = false;
                }
                write(end);
            }
        }

        /**
         * Print a map, with sep between entries, and end with a end string.
         *
         * @param map cannot be null
         */
        public void printMap(@NonNull Map<@Nullable ?, @Nullable ?> map) {
            requireNonNull(map);
            synchronized (out) {
                int i = 0;
                int size = map.size();
                for (Map.Entry<?, ?> entry : map.entrySet()) {
                    write(String.valueOf(entry.getKey()));
                    write(" = ");
                    write(String.valueOf(entry.getValue()));
                    if (i++ < size - 1) {
                        write(sep);
                    }
                }
                write(end);
            }
        }

        /**
         * Set separator for print multi values. Default is space " ".
         *
         * @param sep the separator
         */
        public Printer sep(String sep) {
            requireNonNull(sep);
            return new Printer(sep, end, out);
        }

        /**
         * Additional print string after one print call finished. Default is system line end.
         *
         * @param end the end string
         */
        public Printer end(String end) {
            requireNonNull(end);
            return new Printer(sep, end, out);
        }

        /**
         * The writer to print to, instead of default System.out.
         *
         * @param appendable the writer
         */
        public Printer out(Appendable appendable) {
            requireNonNull(appendable);
            return new Printer(sep, end, appendable);
        }

        private void write(String str) {
            if (str.isEmpty()) {
                return;
            }

            try {
                out.append(str);
            } catch (IOException e) {
                throw new UncheckedIOException(e);
            }
        }

    }
}
