package com.multithreading.synchronization.Locks.ReentrantLock;


import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * A Reentrant Lock means:
 *
 * 👉 Same thread can acquire the same lock multiple times
 * without causing a deadlock.
 *
 * That is why it is called Re-entrant (enter again).
 */
public class ReEntrantLockLock {
    private final Lock lock = new ReentrantLock();

    public void outerMethod() {
        lock.lock();
        try {
            System.out.println("Outer method");
            innerMethod();
        } finally {
            lock.unlock();
        }
    }

    public void innerMethod() {
        lock.lock();
        try {
            System.out.println("Inner method");
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) {
        ReEntrantLockLock example = new ReEntrantLockLock();
        example.outerMethod();
    }
}
