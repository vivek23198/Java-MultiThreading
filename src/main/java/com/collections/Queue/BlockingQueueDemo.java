package com.collections.Queue;

import java.util.Comparator;
import java.util.concurrent.*;

class Producer implements Runnable {
    private BlockingQueue<Integer> queue;
    private int value = 0;

    public Producer(BlockingQueue<Integer> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        while (true) { // continuously producing data
            try {
                // Producing value
                System.out.println("Producer produced: " + value);

                // put() → blocks if queue is full until space is available
                queue.put(value++);

                // Simulate production delay
                Thread.sleep(1000);
            } catch (Exception e) {
                // Proper interruption handling
                Thread.currentThread().interrupt();
                System.out.println("Producer interrupted");
            }
        }
    }
}


class Consumer implements Runnable {
    private BlockingQueue<Integer> queue;

    public Consumer(BlockingQueue<Integer> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        while (true) { // continuously consuming data
            try {
                // take() → blocks if queue is empty until element is available
                Integer value = queue.take();

                // Consuming value
                System.out.println("Consumer consumed: " + value);

                // Simulate consumption delay
                Thread.sleep(2000);
            } catch (Exception e) {
                Thread.currentThread().interrupt();
                System.out.println("Consumer interrupted");
            }
        }
    }
}

public class BlockingQueueDemo {
    public static void main(String[] args) {

        // 🔹 ArrayBlockingQueue
        // 1. Thread-safe (built-in synchronization)
        // 2. Bounded queue (fixed capacity = 5)
        // 3. Uses circular array internally
        // 4. Single lock → lower concurrency compared to LinkedBlockingQueue
        BlockingQueue<Integer> queue = new ArrayBlockingQueue<>(5);

        // BlockingQueue Key Behavior:
        // put()  → blocks if queue is FULL
        // take() → blocks if queue is EMPTY
        // offer() → can wait with timeout (non-blocking alternative)

        // Creating Producer and Consumer threads
        Thread producer = new Thread(new Producer(queue));
        Thread consumer = new Thread(new Consumer(queue));

        // Start execution
        producer.start();
        consumer.start();


        // 🔹 LinkedBlockingQueue
        // 1. Optionally bounded (default unbounded)
        // 2. Uses LinkedList internally
        // 3. Two locks (separate for put & take) → better concurrency
        BlockingQueue<Integer> queue1 = new LinkedBlockingQueue<>();


        // 🔹 PriorityBlockingQueue
        // 1. Unbounded queue
        // 2. Elements ordered based on priority (natural or comparator)
        // 3. Uses Binary Heap internally
        // 4. put() will NOT block (since unbounded)
        BlockingQueue<String> queue2 =
                new PriorityBlockingQueue<>(11, Comparator.reverseOrder());

        // Adding elements (ordered based on priority)
        queue2.add("apple");
        queue2.add("banana");
        queue2.add("cherry");

        // Output not strictly sorted view, but head follows priority
        System.out.println(queue2);


        // 🔹 SynchronousQueue
        // 1. No capacity (0 size)
        // 2. Each put() must wait for corresponding take()
        // 3. Direct handoff between producer & consumer
        // 4. Used in thread pools (ExecutorService)
        BlockingQueue<Integer> queue3 = new SynchronousQueue<>();
    }
}