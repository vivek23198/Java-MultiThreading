package com.multithreading.interviewQuestion.producerAndconsumer;

import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/*
Solve Q11 again but using ArrayBlockingQueue. No explicit wait/notify/synchronized needed.
BlockingQueue handles all coordination internally. 1 producer, 1 consumer, buffer capacity 5, items 1-20.
Compare the code simplicity with Q11's raw synchronization approach.
 */
public class Problem2 {
    private static final int MAX = 20;
    private static final int MAX_CAPACITY = 5;
    private static int value =1;
    private final BlockingQueue<Integer> queue = new ArrayBlockingQueue<>(MAX_CAPACITY);

    public void producer() throws InterruptedException {
        for(int i=1; i<=MAX; i++){
            queue.put(i);
            System.out.println("Produced :: "+i);
        }

    }

    public void consumer() throws InterruptedException {
        for(int i=1; i<= MAX; i++){
            int consumedElem = queue.take();
            System.out.println("Consumed :: " +consumedElem);
            Thread.sleep(150);
        }
    }

    public static void main(String[] args) {
        Problem2 problem2 = new Problem2();
        Thread producer = new Thread(() -> {
            try {
                problem2.producer();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        Thread consumer = new Thread(() -> {
            try {
                problem2.consumer();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        producer.start();
        consumer.start();

        try {
            producer.join();
            consumer.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Producer-Consumer Task Completed");
    }
}
