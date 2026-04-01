package com.multithreading.LifeCycle;

/*
============================= THREAD LIFECYCLE (INTERVIEW REVISION) =============================

1. Thread States in Java:
   - NEW           → Thread created, not started
   - RUNNABLE      → Ready or running
   - BLOCKED       → Waiting for lock
   - WAITING       → Waiting indefinitely (wait(), join())
   - TIMED_WAITING → Waiting for specific time (sleep(), wait(timeout))
   - TERMINATED    → Execution completed

2. Lifecycle Flow:
   NEW → RUNNABLE → (RUNNING) → WAITING/BLOCKED → RUNNABLE → TERMINATED

3. Important Methods:
   - start()  → moves NEW → RUNNABLE
   - sleep()  → RUNNING → TIMED_WAITING
   - join()   → main thread waits for another thread

==============================================================================================
*/

public class ThreadState extends Thread {

    @Override
    public void run() {

        // Thread enters RUNNING state
        System.out.println("RUNNING");

        try {
            // Thread goes to TIMED_WAITING state for 2 seconds
            Thread.sleep(2000);

        } catch (InterruptedException e) {
            System.out.println(e);
        }
    }

    public static void main(String[] args) throws InterruptedException {

        ThreadState t1 = new ThreadState();

        // Thread is created but not started
        System.out.println(t1.getState()); // NEW

        // Start thread → moves to RUNNABLE
        t1.start();
        System.out.println(t1.getState()); // RUNNABLE

        // Give time for thread to enter sleep
        Thread.sleep(100);

        // Thread is sleeping → TIMED_WAITING
        System.out.println(t1.getState()); // TIMED_WAITING

        // Wait for thread to finish
        t1.join();

        // Thread execution completed
        System.out.println(t1.getState()); // TERMINATED
    }
}