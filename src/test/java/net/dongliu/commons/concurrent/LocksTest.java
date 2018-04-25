package net.dongliu.commons.concurrent;

import net.dongliu.commons.concurrent.Locks.LockResource;
import org.junit.Test;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import static org.junit.Assert.assertEquals;

public class LocksTest {

    @Test
    public void with() {
        ReentrantLock lock = new ReentrantLock();
        try (LockResource c = Locks.with(lock)) {
            assertEquals(1, lock.getHoldCount());
        }
    }

    @Test
    public void runWith() {
        Lock lock = new ReentrantLock();
        Locks.runWith(lock, () -> {
        });
    }

    @Test
    public void callWith() {
        Lock lock = new ReentrantLock();
        Integer i = Locks.callWith(lock, () -> 0);
        assertEquals(Integer.valueOf(0), i);
    }

    @Test
    public void withRead() {
        ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
        try (LockResource c = Locks.withRead(lock)) {
            assertEquals(1, lock.getReadHoldCount());
        }
    }

    @Test
    public void withWrite() {
        ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
        try (LockResource c = Locks.withWrite(lock)) {
            assertEquals(1, lock.getWriteHoldCount());
        }
    }
}