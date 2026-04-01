package com.multithreading.ThreadCommunication;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/*
============================= PRODUCER-CONSUMER USING BLOCKINGQUEUE =============================

1. What is BlockingQueue?
   - A thread-safe queue (from java.util.concurrent)
   - Handles synchronization internally
   - No need for wait() / notify()

2. Why use BlockingQueue?
   ✔ Simpler code
   ✔ No manual synchronization
   ✔ Avoids common concurrency bugs

3. Key Methods:
   - put()  → waits if queue is full
   - take() → waits if queue is empty

4. Internal Working:
   - Uses locks internally
   - Ensures safe communication between threads

5. Time Complexity:
   - put() / take() → O(1)

6. Types:
   - ArrayBlockingQueue → fixed size (bounded)
   - LinkedBlockingQueue → optionally bounded
   - PriorityBlockingQueue

==============================================================================================
*/

class ProducerConsumer {

    // Thread-safe queue with fixed capacity
    private BlockingQueue<Integer> queue =
            new ArrayBlockingQueue<>(5);

    // ================= PRODUCER =================
    public void produce() {

        int value = 1;

        try {
            while (true) {

                // Producing data
                System.out.println("Producing : " + value);

                // Adds element → waits if queue is full
                queue.put(value);

                value++;

                // Simulate delay
                Thread.sleep(500);
            }
        } catch (InterruptedException e) {

            // Restore interrupt status
            Thread.currentThread().interrupt();
        }
    }

    // ================= CONSUMER =================
    public void consume() {

        try {
            while (true) {

                // Removes element → waits if queue is empty
                int val = queue.take();

                System.out.println("Consuming : " + val);

                // Simulate delay
                Thread.sleep(800);
            }
        } catch (InterruptedException e) {

            Thread.currentThread().interrupt();
        }
    }
}

public class ArrayBlockingQueueExample {

    public static void main(String[] args) {

        ProducerConsumer pc = new ProducerConsumer();

        // Producer thread
        Thread producerThread = new Thread(() -> pc.produce());

        // Consumer thread
        Thread consumerThread = new Thread(() -> pc.consume());

        // Start threads
        producerThread.start();
        consumerThread.start();
    }
}