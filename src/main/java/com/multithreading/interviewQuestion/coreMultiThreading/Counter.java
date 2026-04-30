package com.multithreading.interviewQuestion.coreMultiThreading;

import java.util.concurrent.atomic.AtomicInteger;

public class Counter {

    AtomicInteger counter = new AtomicInteger(0);

    public void incrementCounter() {
        counter.getAndIncrement();
    }

    public int getCounter() {
        return counter.get();
    }



    public static void main(String[] args) throws InterruptedException {
        Counter counterObj = new Counter();

        Thread t1 = new Thread(() -> {
            for(int i=1; i<= 10000; i++){
                counterObj.incrementCounter();
            }
        });

        Thread t2 = new Thread(() -> {
            for(int i=1; i<= 10000; i++){
                counterObj.incrementCounter();
            }
        });

        Thread t3 = new Thread(() -> {
            for(int i=1; i<= 10000; i++){
                counterObj.incrementCounter();
            }
        });

        Thread t4 = new Thread(() -> {
            for(int i=1; i<= 10000; i++){
                counterObj.incrementCounter();
            }
        });

        Thread t5 = new Thread(() -> {
            for(int i=1; i<= 10000; i++){
                counterObj.incrementCounter();
            }
        });

        t1.start();
        t2.start();
        t3.start();
        t4.start();
        t5.start();

        t1.join();
        t2.join();
        t3.join();
        t4.join();
        t5.join();

        System.out.println("Final Counter Value :: "+counterObj.getCounter());
        System.out.println("Task Execution Completed....");
    }
}
