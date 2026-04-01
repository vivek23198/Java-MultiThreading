package com.multithreading.synchronization.Locks.ReentrantReadWriteLock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/*
============================= REENTRANT READ-WRITE LOCK =============================

1. What is ReadWriteLock?
   - A lock that separates READ and WRITE operations

2. Why use it?
   - Improves performance when:
        ✔ Many reads
        ✔ Few writes

3. Types of Locks:
   - Read Lock  → multiple threads allowed (shared)
   - Write Lock → only one thread (exclusive)

4. Behavior:
   ✔ Multiple readers can read simultaneously
   ❌ Writer blocks all readers and writers
   ❌ Readers block writer if already reading

5. Use Case:
   - Caching
   - Database reads
   - Configuration data

6. Advantage over ReentrantLock:
   - Allows concurrency for read-heavy scenarios

=====================================================================================
*/

public class ReadWriteCounter {

    private int count = 0;

    // ReadWriteLock provides separate locks
    private final ReadWriteLock lock = new ReentrantReadWriteLock();

    // Shared read lock
    private final Lock readLock = lock.readLock();

    // Exclusive write lock
    private final Lock writeLock = lock.writeLock();


    // ================= WRITE OPERATION =================
    public void increment() {

        // Only one thread can write at a time
        writeLock.lock();

        try {
            count++;

            // Simulate delay
            Thread.sleep(50);

        } catch (InterruptedException e) {

            Thread.currentThread().interrupt();

        } finally {

            writeLock.unlock();
        }
    }


    // ================= READ OPERATION =================
    public int getCount() {

        // Multiple threads can read simultaneously
        readLock.lock();

        try {
            return count;

        } finally {

            readLock.unlock();
        }
    }


    public static void main(String[] args) throws InterruptedException {

        ReadWriteCounter counter = new ReadWriteCounter();

        // Reader task
        Runnable readTask = () -> {
            for (int i = 0; i < 10; i++) {
                System.out.println(Thread.currentThread().getName() +
                        " read: " + counter.getCount());
            }
        };

        // Writer task
        Runnable writeTask = () -> {
            for (int i = 0; i < 10; i++) {
                counter.increment();
                System.out.println(Thread.currentThread().getName() +
                        " incremented");
            }
        };

        // Creating threads
        Thread writerThread = new Thread(writeTask, "Writer");
        Thread readerThread1 = new Thread(readTask, "Reader-1");
        Thread readerThread2 = new Thread(readTask, "Reader-2");

        // Start threads
        writerThread.start();
        readerThread1.start();
        readerThread2.start();

        // Wait for completion
        writerThread.join();
        readerThread1.join();
        readerThread2.join();

        System.out.println("Final count: " + counter.getCount());
    }
}