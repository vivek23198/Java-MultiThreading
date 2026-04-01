package com.multithreading.synchronization.Locks.FairnessOfLock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/*
============================= REENTRANT LOCK & FAIRNESS =============================

1. What is ReentrantLock?
   - A flexible alternative to synchronized
   - Provides explicit locking mechanism

2. Fairness Policy:
   - true  → Fair Lock (FIFO order)
   - false → Non-Fair Lock (default, faster)

3. Fair Lock:
   - Threads acquire lock in the order they requested it
   - Prevents starvation
   - Slower due to scheduling overhead

4. Non-Fair Lock:
   - Threads can "jump the queue"
   - Better performance
   - May cause starvation

5. Why use ReentrantLock over synchronized?
   ✔ tryLock() support
   ✔ interruptible locking
   ✔ fairness control
   ✔ better flexibility

6. Important Methods:
   - lock()      → acquire lock
   - unlock()    → release lock
   - tryLock()   → attempt without waiting
   - lockInterruptibly()

====================================================================================
*/

public class FairnessLockExample {

    // Fair lock → ensures FIFO ordering
    private final Lock lock = new ReentrantLock(true);

    public void accessResource() {

        // Acquire lock
        lock.lock();

        try {

            System.out.println(Thread.currentThread().getName() + " acquired the lock.");

            // Simulate work
            Thread.sleep(1000);

        } catch (InterruptedException e) {

            Thread.currentThread().interrupt();

        } finally {

            // Always release lock in finally block
            System.out.println(Thread.currentThread().getName() + " released the lock.");
            lock.unlock();
        }
    }

    public static void main(String[] args) {

        FairnessLockExample example = new FairnessLockExample();

        // Task to access shared resource
        Runnable task = new Runnable() {
            @Override
            public void run() {
                example.accessResource();
            }
        };

        // Creating multiple threads
        Thread thread1 = new Thread(task, "Thread 1");
        Thread thread2 = new Thread(task, "Thread 2");
        Thread thread3 = new Thread(task, "Thread 3");

        // Start threads
        thread1.start();
        thread2.start();
        thread3.start();
    }
}