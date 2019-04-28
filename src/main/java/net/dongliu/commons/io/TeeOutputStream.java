package net.dongliu.commons.io;

import net.dongliu.commons.Throwables;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

/**
 * A output stream write data to multi underlying delegate OutputStreams.
 * If any of those OutputStreams operate failed, the whole operation failed.
 */
public class TeeOutputStream extends OutputStream {
    private final List<OutputStream> outputs;

    public TeeOutputStream(OutputStream... outputs) {
        this(List.of(outputs));
    }

    public TeeOutputStream(List<OutputStream> outputs) {
        this.outputs = outputs;
    }

    @Override
    public void write(byte[] b) throws IOException {
        for (var output : outputs) {
            output.write(b);
        }
    }

    @Override
    public void write(byte[] b, int off, int len) throws IOException {
        for (var output : outputs) {
            output.write(b, off, len);
        }
    }

    @Override
    public void flush() throws IOException {
        for (var output : outputs) {
            output.flush();
        }
    }

    @Override
    public void close() throws IOException {
        Throwable t = null;
        for (var output : outputs) {
            try {
                output.close();
            } catch (Throwable e) {
                if (t == null) {
                    t = e;
                } else {
                    t.addSuppressed(e);
                }
            }
        }
        if (t != null) {
            throw Throwables.sneakyThrow(t);
        }
    }

    @Override
    public void write(int b) throws IOException {
        for (var output : outputs) {
            output.write(b);
        }
    }
}
