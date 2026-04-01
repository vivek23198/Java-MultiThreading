package com.multithreading.ExecutorFramework;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/*
============================= THREAD POOL (EXECUTOR SERVICE) =============================

1. What is Thread Pool?
   - A pool of reusable threads
   - Managed by ExecutorService
   - Avoids cost of creating/destroying threads repeatedly

2. Why use Thread Pool?
   ✔ Better performance
   ✔ Resource management
   ✔ Control concurrency

3. Fixed Thread Pool:
   - newFixedThreadPool(n)
   - Uses n threads → tasks are queued if threads are busy

4. Lifecycle:
   - submit() → assign task
   - shutdown() → stop accepting new tasks
   - awaitTermination() → wait for completion

===========================================================================================
*/

public class ThreadPool {

    public static void main(String[] args) {

        long startTime = System.currentTimeMillis();
        System.out.println("Start Time : " + startTime);

        // Create thread pool with 3 threads
        ExecutorService executor = Executors.newFixedThreadPool(3);

        // Submit 9 tasks
        for (int i = 1; i < 10; i++) {

            int finalI = i;

            executor.submit(() -> {

                // Each task computes factorial
                long result = factorial(finalI);

                System.out.println(Thread.currentThread().getName() +
                        " → factorial(" + finalI + ") = " + result);
            });
        }

        // Stop accepting new tasks
        executor.shutdown();

        try {

            // Wait for tasks to finish (max 10 sec)
            if (!executor.awaitTermination(10, TimeUnit.SECONDS)) {

                // Force shutdown if not completed
                executor.shutdownNow();
            }

        } catch (InterruptedException e) {

            executor.shutdownNow();
            Thread.currentThread().interrupt();
        }

        System.out.println("Total time: " +
                (System.currentTimeMillis() - startTime));
    }

    // Simulated heavy task
    private static long factorial(int n) {

        try {
            // Simulate delay
            Thread.sleep(1000);

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        long result = 1;

        for (int i = 1; i <= n; i++) {
            result *= i;
        }

        return result;
    }
}