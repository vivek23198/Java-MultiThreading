package com.multithreading.ThreadCommunication;

import java.util.LinkedList;
import java.util.Queue;

/*
============================= THREAD COMMUNICATION (PRODUCER-CONSUMER) =============================

1. What is Thread Communication?
   - Threads coordinate with each other using:
        ✔ wait()
        ✔ notify()
        ✔ notifyAll()

2. Problem:
   - Producer → adds data
   - Consumer → removes data
   - Need synchronization to avoid:
        ❌ Overfilling buffer
        ❌ Consuming from empty buffer

3. Key Methods:
   - wait()      → releases lock, waits
   - notify()    → wakes one waiting thread
   - notifyAll() → wakes all waiting threads

4. Important Rules:
   ✔ Must be used inside synchronized block/method
   ✔ Always use while loop (NOT if) → avoid spurious wakeups

5. Flow:
   Producer:
     - If buffer full → wait
     - Else → add item → notify

   Consumer:
     - If buffer empty → wait
     - Else → remove item → notify

6. Time Complexity:
   - O(1) operations

==============================================================================================
*/

public class Communication {

    // Shared buffer between producer and consumer
    private final Queue<Integer> buffer = new LinkedList<>();

    // Maximum capacity of buffer
    private static final int CAPACITY = 5;


    // ================= PRODUCER =================
    public synchronized void produce(int value) throws InterruptedException {

        // Wait if buffer is full
        while (buffer.size() == CAPACITY) {
            wait(); // releases lock and waits
        }

        // Add value to buffer
        buffer.offer(value);
        System.out.println("Produced " + value);

        Thread.sleep(1000); // simulate delay

        // Notify waiting consumer
        notify();
    }


    // ================= CONSUMER =================
    public synchronized void consume() throws InterruptedException {

        // Wait if buffer is empty
        while (buffer.isEmpty()) {
            wait(); // releases lock and waits
        }

        // Remove value from buffer
        int value = buffer.poll();
        System.out.println("Consumed " + value);

        Thread.sleep(500); // simulate delay

        // Notify waiting producer
        notify();
    }


    public static void main(String[] args) {

        Communication communication = new Communication();

        // Producer Thread
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    communication.produce(i);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });

        // Consumer Thread
        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    communication.consume();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });

        t1.start();
        t2.start();
    }
}