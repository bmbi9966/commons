package net.dongliu.commons;


import java.io.IOException;
import java.io.PrintStream;
import java.io.UncheckedIOException;
import java.io.Writer;

import static java.util.Objects.requireNonNull;

/**
 * Utils for print. The static method are delegated to {@link Printer}
 */
public class Prints {

    private static final Printer defaultPrinter = new Printer(" ", System.lineSeparator(), System.out);

    /**
     * @see Printer#print(Object)
     */
    public static void print(Object value) {
        defaultPrinter.print(value);
    }

    /**
     * @see Printer#print(Object...)
     */
    public static void print(Object... values) {
        defaultPrinter.print(values);
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
     * @see Printer#out(Writer)
     */
    public static Printer out(Writer writer) {
        requireNonNull(writer);
        return defaultPrinter.out(writer);
    }

    /**
     * @see Printer#out(PrintStream)
     */
    public static Printer out(PrintStream printStream) {
        requireNonNull(printStream);
        return defaultPrinter.out(printStream);
    }

    /**
     * Immutable class with setting for printing.
     */
    public static class Printer {
        private final String sep;
        private final String lineEnd;
        private final PrintStream printStream;
        private final Writer writer;

        private Printer(String sep, String lineEnd, PrintStream printStream, Writer writer) {
            this.sep = sep;
            this.lineEnd = lineEnd;
            this.printStream = printStream;
            this.writer = writer;
        }

        private Printer(String sep, String lineEnd, PrintStream printStream) {
            this(sep, lineEnd, printStream, null);
        }

        private Printer(String sep, String lineEnd, Writer writer) {
            this(sep, lineEnd, null, writer);
        }

        /**
         * Print one value
         */
        public void print(Object value) {
            synchronized (lock()) {
                write(String.valueOf(value));
                write(lineEnd);
            }
        }

        /**
         * Print values
         *
         * @param values the values
         */
        public void print(Object... values) {
            requireNonNull(values);
            if (values.length == 0) {
                return;
            }

            synchronized (lock()) {
                for (int i = 0; i < values.length; i++) {
                    write(String.valueOf(values[i]));
                    if (i < values.length - 1) {
                        write(sep);
                    }
                }
                write(lineEnd);
            }
        }

        /**
         * Set separator for print multi values. Default is space " ".
         *
         * @param sep the separator
         */
        public Printer sep(String sep) {
            requireNonNull(sep);
            return new Printer(sep, lineEnd, printStream, writer);
        }

        /**
         * Additional print string after one print call finished. Default is system line end.
         *
         * @param end the end string
         */
        public Printer end(String end) {
            requireNonNull(end);
            return new Printer(sep, end, printStream, writer);
        }

        /**
         * The writer to print to, instead of default System.out.
         *
         * @param writer the writer
         */
        public Printer out(Writer writer) {
            requireNonNull(writer);
            return new Printer(sep, lineEnd, null, writer);
        }

        /**
         * The printStream to print to, instead of default System.out.
         *
         * @param printStream the printStream
         */
        public Printer out(PrintStream printStream) {
            requireNonNull(printStream);
            return new Printer(sep, lineEnd, printStream, null);
        }

        private void write(String str) {
            if (str.isEmpty()) {
                return;
            }
            if (printStream != null) {
                printStream.print(str);
            } else if (writer != null) {
                try {
                    writer.write(str);
                } catch (IOException e) {
                    throw new UncheckedIOException(e);
                }
            } else {
                throw new RuntimeException();
            }
        }

        private Object lock() {
            if (printStream != null) {
                return printStream;
            }
            return writer;
        }

    }
}
