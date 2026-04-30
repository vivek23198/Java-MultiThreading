package com.multithreading.ExecutorFramework;

import java.util.concurrent.Semaphore;

public class SemaphoreExample {
    public static void main(String[] args) {
        Semaphore bouncer = new Semaphore(2); // Only 2 permits available

        Runnable visitor = () -> {
            try {
                System.out.println(Thread.currentThread().getName() + " waiting for permit...");
                bouncer.acquire(); // Take a permit
                System.out.println(Thread.currentThread().getName() + " ENTERED.");
                Thread.sleep(2000); // Using the resource
            } catch (InterruptedException e) { e.printStackTrace(); }
            finally {
                System.out.println(Thread.currentThread().getName() + " RELEASING permit.");
                bouncer.release(); // Give permit back
            }
        };

        for (int i = 1; i <= 4; i++) {
            new Thread(visitor, "Visitor-" + i).start();
        }
    }
}
