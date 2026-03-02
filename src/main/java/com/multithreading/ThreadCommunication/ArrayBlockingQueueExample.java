package com.multithreading.ThreadCommunication;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

class ProducerConsumer {

    private BlockingQueue<Integer> queue =
            new ArrayBlockingQueue<>(5); // fixed capacity

    // PRODUCER METHOD
    public void produce() {
        int value = 1;

        try {
            while (true) {
                System.out.println("Producing : " + value);
                queue.put(value); // waits if queue is full
                value++;

                Thread.sleep(500);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    // CONSUMER METHOD
    public void consume() {

        try {
            while (true) {
                int val = queue.take(); // waits if queue empty
                System.out.println("Consuming : " + val);

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

        Thread producerThread = new Thread(() -> pc.produce());
        Thread consumerThread = new Thread(() -> pc.consume());

        producerThread.start();
        consumerThread.start();
    }
}
