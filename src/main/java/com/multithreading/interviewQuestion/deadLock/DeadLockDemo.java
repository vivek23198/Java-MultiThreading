package com.multithreading.interviewQuestion.deadLock;

/*
Create a deadlock between 2 threads and 2 locks. Thread-A locks Lock1 then Lock2.
Thread-B locks Lock2 then Lock1. Both block forever waiting for the other.
Use jstack or ThreadMXBean to detect the deadlock programmatically.
Part 2: Fix using lock ordering (always acquire in same order).
Part 3: Fix using tryLock with backoff.
 */
public class DeadLockDemo {

    public static void main(String[] args) throws InterruptedException {
        final Object lockA = new Object();
        final Object lockB = new Object();

        Thread t1 = new Thread(() -> {
            synchronized (lockA) {
                System.out.println(Thread.currentThread().getName()+" acquire lock A, and waiting for Resource B to acquire Lock");
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                synchronized (lockB) {
                    System.out.println(Thread.currentThread().getName()+" acquire lock B, and waiting for Resource A to acquire Lock");
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }

            }
        }, "Thread-1");

        Thread t2 = new Thread(() -> {
            synchronized (lockB) {
                System.out.println(Thread.currentThread().getName()+" acquire lock B, and waiting for Resource A to acquire Lock");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                synchronized (lockA) {
                    System.out.println(Thread.currentThread().getName()+" acquire lock A, and waiting for Resource B to acquire Lock");
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }, "Thread-2");

        t1.start();
        t2.start();

        t1.join();
        t2.join();

        System.out.println("All Thread Execution Completed !");
    }
}
