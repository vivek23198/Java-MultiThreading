package com.multithreading.synchronization.Locks.ReentrantLock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/*
============================= REENTRANT LOCK (INTERVIEW REVISION) =============================

1. What is Reentrant Lock?
   - A lock that can be acquired multiple times by the SAME thread
   - Prevents self-deadlock

2. Why "Reentrant"?
   - "Re" → again
   - Same thread can enter (lock) again

3. Internal Working:
   - Maintains a counter (hold count)
   - Each lock() increments count
   - Each unlock() decrements count
   - Lock released only when count = 0

4. Important Rule:
   - Number of lock() calls MUST match unlock()

5. Without Reentrancy:
   - Thread would block itself → DEADLOCK

==============================================================================================
*/

public class ReEntrantLockLock {

    private final Lock lock = new ReentrantLock();

    // ================= OUTER METHOD =================
    public void outerMethod() {

        // First lock acquisition
        lock.lock();

        try {
            System.out.println("Outer method");

            // Calling another method that also uses same lock
            innerMethod();

        } finally {
            // First unlock
            lock.unlock();
        }
    }

    // ================= INNER METHOD =================
    public void innerMethod() {

        // Same thread acquires lock AGAIN (Reentrant behavior)
        lock.lock();

        try {
            System.out.println("Inner method");

        } finally {
            // Second unlock (important)
            lock.unlock();
        }
    }

    public static void main(String[] args) {

        ReEntrantLockLock example = new ReEntrantLockLock();

        // Single thread execution
        example.outerMethod();
    }
}