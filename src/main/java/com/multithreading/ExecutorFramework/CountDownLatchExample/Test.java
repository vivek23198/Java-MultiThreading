package com.multithreading.ExecutorFramework.CountDownLatchExample;

import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/*
============================= COUNTDOWNLATCH (INTERVIEW REVISION) =============================

1. What is CountDownLatch?
   - A synchronization aid that allows one or more threads to wait
     until a set of operations (other threads) complete

2. Key Concept:
   - Initialize with count (number of tasks)
   - Each task calls countDown()
   - Waiting thread calls await()
   - When count reaches 0 → waiting thread proceeds

3. Behavior:
   - One-time use (NOT reusable)
   - Main thread waits for worker threads

4. Use Cases:
   ✔ Waiting for services to start
   ✔ Parallel task completion
   ✔ System initialization

==============================================================================================
*/

public class Test {

    public static void main(String[] args) throws InterruptedException {

        int n = 3;

        // Thread pool with 3 threads
        ExecutorService executorService =
                Executors.newFixedThreadPool(n);

        // Latch initialized with count = 3
        CountDownLatch latch = new CountDownLatch(n);

        // Submit 3 dependent services
        executorService.submit(new DependentService(latch));
        executorService.submit(new DependentService(latch));
        executorService.submit(new DependentService(latch));

        // Main thread waits until count reaches 0
        latch.await();

        // Executes only after all services complete
        System.out.println("Main");

        executorService.shutdown();
    }
}


// Worker task
class DependentService implements Callable<String> {

    private final CountDownLatch latch;

    public DependentService(CountDownLatch latch) {
        this.latch = latch;
    }

    @Override
    public String call() throws Exception {

        try {
            System.out.println(Thread.currentThread().getName() +
                    " service started.");

            // Simulate work
            Thread.sleep(2000);

        } finally {

            // Decrement latch count
            latch.countDown();
        }

        return "ok";
    }
}