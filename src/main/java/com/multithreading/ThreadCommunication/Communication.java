package com.multithreading.ThreadCommunication;


import java.util.LinkedList;
import java.util.Queue;

public class Communication {

    private final Queue<Integer> buffer = new LinkedList<>();
    private static final int CAPACITY = 5;

    public synchronized void produce(int value) throws InterruptedException {

        while (buffer.size() == CAPACITY) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        buffer.offer(value);
        System.out.println("Produced "+value);

        Thread.sleep(1000);
        notify();
    }

    public synchronized void consume() throws InterruptedException {
        while(buffer.isEmpty()) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        int value = buffer.poll();
        System.out.println("Consume "+value);
        Thread.sleep(500);
        notify();
    }

    public static void main(String[] args) {
        Communication communication = new Communication();

        Thread t1 = new Thread(() -> {
            for (int i=0; i<10; i++) {
                try {
                    communication.produce(i);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        Thread t2 = new Thread(() -> {
            for (int i=0; i<10; i++) {
                try {
                    communication.consume();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        t1.start();
        t2.start();
    }
}
