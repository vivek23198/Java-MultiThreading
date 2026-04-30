package com.multithreading.interviewQuestion.producerAndconsumer;

import java.sql.SQLOutput;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

/*
Bounded buffer of size 5. One Producer generates integers 1 to 20 (100ms between each).
One Consumer consumes and prints them (150ms per item).
Producer MUST wait when buffer is full.
Consumer MUST wait when buffer is empty. No item should be lost or processed twice.
Implement using wait() and notify() on synchronized methods.
 */
public class Problem1 {
    private static final int MAX_CAPACITY = 5;
    private static final int MAX = 20;
    private static int value = 1;
    private static final Queue<Integer> boundedBuffer = new LinkedList<>();

    public synchronized void produce() throws InterruptedException {
        while(value <= MAX) {
            while(boundedBuffer.size() == MAX_CAPACITY) {
                wait();
            }
            boundedBuffer.offer(value);
            System.out.println("Produced at "+value);
            value++;
            notifyAll();
            Thread.sleep(100);
        }
    }

    public synchronized void consume() throws InterruptedException {
        while(true) {
            while(boundedBuffer.isEmpty()) {
                if(value > MAX) return;
                wait();
            }

            int consumed = boundedBuffer.poll();
            System.out.print(consumed+" ");
            notifyAll();
            Thread.sleep(150);
        }

    }

    public static void main(String[] args) {
        Problem1 problem1 = new Problem1();

        Thread producer = new Thread(() -> {
            try {
                problem1.produce();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        Thread consumer = new Thread(() -> {
            try {
                problem1.consume();
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


        System.out.println("Producer-Consumer Completed its Task.....");

    }
}
