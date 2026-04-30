package com.multithreading.interviewQuestion.producerAndconsumer;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

/*
3 Producer threads each produce 10 items (30 total). 2 Consumer threads compete for items.
Shared BlockingQueue capacity = 10. Use CountDownLatch(30) to track when all items are consumed.
Main thread waits for latch to reach 0, then prints 'All 30 items consumed!'.
Track consumed items in ConcurrentHashMap to verify no duplicates.
 */
public class Problem3 {
    private static final int QUEUE_CAPACITY = 10;
    private final BlockingQueue<Integer> sharedQueue = new ArrayBlockingQueue<>(QUEUE_CAPACITY);
    private static final int POISON_PILL = -1;

    AtomicInteger counter = new AtomicInteger(1);
    private final ConcurrentHashMap<Integer, Boolean> concurrentHashMap = new ConcurrentHashMap<>();

    public void producer() throws InterruptedException {
        for(int i=1; i<=10; i++) {
            int value = counter.getAndIncrement();
            sharedQueue.put(value);
            System.out.println("Produced :: "+value);
        }
    }

    public void consumer(CountDownLatch latch) throws InterruptedException {
        try {
            while (true) {
                int elem = sharedQueue.take();
                if (elem == POISON_PILL) {
                    break;
                }

                if(concurrentHashMap.putIfAbsent(elem, true) != null) {
                    System.out.println("Duplicate Deleted :: "+elem);
                }

                System.out.println("Consumed :: "+elem);
                latch.countDown();

            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public static void main(String[] args) throws InterruptedException {
        Problem3 problem3 = new Problem3();
        CountDownLatch latch = new CountDownLatch(30);

        Thread p1 = new Thread(() -> {
            try {
                problem3.producer();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        Thread p2 = new Thread(() -> {
            try {
                problem3.producer();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        Thread p3 = new Thread(() -> {
            try {
                problem3.producer();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        Thread c1 = new Thread(() -> {
            try {
                problem3.consumer(latch);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        Thread c2 = new Thread(() -> {
            try {
                problem3.consumer(latch);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        p1.start(); p2.start(); p3.start();
        c1.start(); c2.start();

        // ✅ Wait for producers FIRST
        p1.join(); p2.join(); p3.join();

        problem3.sharedQueue.put(POISON_PILL);
        problem3.sharedQueue.put(POISON_PILL);

        latch.await();
        System.out.println("All 30 items Consumed");

    }
}
