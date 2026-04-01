package com.multithreading.ExecutorFramework.CyclicBarrierExample;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/*
============================= CYCLIC BARRIER (INTERVIEW REVISION) =============================

1. What is CyclicBarrier?
   - A synchronization aid that allows multiple threads to wait
     until all threads reach a common barrier point

2. Key Concept:
   - Threads call await()
   - All threads wait until required count is reached
   - Once reached → all threads proceed together

3. Why "Cyclic"?
   - Can be reused after completion

4. Barrier Action:
   - Optional Runnable
   - Executes once when all threads reach barrier

5. Use Cases:
   ✔ System startup coordination
   ✔ Parallel processing phases
   ✔ Multi-step workflows

==============================================================================================
*/

public class Main {

    public static void main(String[] args) {

        int numberOfSubsystems = 4;

        // Create CyclicBarrier with 4 threads + barrier action
        CyclicBarrier barrier = new CyclicBarrier(numberOfSubsystems, () -> {
            System.out.println("All subsystems are up and running. System startup complete.");
        });

        // Creating subsystem threads
        Thread webServerThread =
                new Thread(new Subsystem("Web Server", 2000, barrier));

        Thread databaseThread =
                new Thread(new Subsystem("Database", 4000, barrier));

        Thread cacheThread =
                new Thread(new Subsystem("Cache", 3000, barrier));

        Thread messagingServiceThread =
                new Thread(new Subsystem("Messaging Service", 3500, barrier));

        // Start all subsystems
        webServerThread.start();
        databaseThread.start();
        cacheThread.start();
        messagingServiceThread.start();
    }
}


// Worker task (subsystem initialization)
class Subsystem implements Runnable {

    private String name;
    private int initializationTime;
    private CyclicBarrier barrier;

    public Subsystem(String name, int initializationTime, CyclicBarrier barrier) {
        this.name = name;
        this.initializationTime = initializationTime;
        this.barrier = barrier;
    }

    @Override
    public void run() {

        try {
            System.out.println(name + " initialization started.");

            // Simulate initialization work
            Thread.sleep(initializationTime);

            System.out.println(name + " initialization complete.");

            // Wait at barrier until all threads arrive
            barrier.await();

        } catch (InterruptedException | BrokenBarrierException e) {
            Thread.currentThread().interrupt();
        }
    }
}