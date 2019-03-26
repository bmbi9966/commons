package net.dongliu.commons.io;

import java.io.OutputStream;

/**
 * A output stream that discard all written data
 */
class BlackHoleOutputStream extends OutputStream {

    final static BlackHoleOutputStream instance = new BlackHoleOutputStream();

    public BlackHoleOutputStream() {
        super();
    }

    @Override
    public void write(byte[] b) {
    }

    @Override
    public void write(byte[] b, int off, int len) {
    }

    @Override
    public void flush() {
    }

    @Override
    public void close() {
    }

    @Override
    public void write(int b) {
    }
}
