package com.collections.Queue;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.SynchronousQueue;

public class SynchronousQueueDemo {
    public static void main(String[] args) {

        // 🔹 SynchronousQueue Properties:
        // 1. Thread-safe BlockingQueue
        // 2. Capacity = 0 → cannot store elements
        // 3. Each put() must wait for a corresponding take()
        // 4. Direct handoff between producer and consumer
        // 5. No internal storage (no buffering)
        // 6. Used in thread pools (e.g., Executors.newCachedThreadPool())
        // 7. Ideal for real-time data exchange between threads

        // Creating SynchronousQueue
        BlockingQueue<String> queue = new SynchronousQueue<>();

        // 🔹 Producer Thread
        Thread producer = new Thread(() -> {
            try {
                // Producer tries to put data
                System.out.println("Producer is waiting to transfer...");

                // put() → blocks until consumer calls take()
                queue.put("Hello from producer!");

                // Executes only after consumer receives the data
                System.out.println("Producer has transferred the message.");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println("Producer was interrupted.");
            }
        });

        // 🔹 Consumer Thread
        Thread consumer = new Thread(() -> {
            try {
                // Consumer waits to receive data
                System.out.println("Consumer is waiting to receive...");

                // take() → blocks until producer provides data
                String message = queue.take();

                // Receives message directly from producer
                System.out.println("Consumer received: " + message);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println("Consumer was interrupted.");
            }
        });

        // Starting threads
        producer.start();
        consumer.start();
    }
}