package com.multithreading.ThreadMethods;

/*
============================= THREAD METHODS (INTERVIEW REVISION) =============================

1. Thread Creation:
   - Extend Thread class OR implement Runnable
   - Override run() method → contains task logic

2. start() vs run():
   - start() → creates new thread → calls run() internally
   - run() → normal method call (no new thread)

3. Thread Methods Used:
   - setPriority() → sets thread priority (1 to 10)
   - sleep(ms) → pauses thread execution
   - currentThread() → returns current thread object

4. Thread Priority:
   - MIN_PRIORITY = 1
   - NORM_PRIORITY = 5 (default)
   - MAX_PRIORITY = 10
   - NOTE: Priority is just a hint, not guaranteed

5. Exception Handling:
   - sleep() throws InterruptedException → must handle

6. Important Concepts:
   - Threads run concurrently (order not guaranteed)
   - Scheduling is OS-dependent

==============================================================================================
*/

public class MyThread extends Thread {

    // Constructor to set thread name
    public MyThread(String name) {
        super(name);
    }

    @Override
    public void run() {

        // Code executed by each thread
        System.out.println("Thread is Running...");

        // Outer loop → repetition
        for (int i = 1; i <= 5; i++) {

            // Inner loop → repeated printing
            for (int j = 0; j < 5; j++) {

                // Print thread details
                System.out.println(
                        Thread.currentThread().getName()
                                + " - Priority: "
                                + Thread.currentThread().getPriority()
                                + " - count: " + i
                );

                try {
                    // Pause thread for 1 second
                    Thread.sleep(1000);
                } catch (InterruptedException e) {

                    // Exception handling for interruption
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {

        // Creating threads with different priorities
        MyThread l = new MyThread("Low Priority Thread");
        MyThread m = new MyThread("Medium Priority Thread");
        MyThread n = new MyThread("High Priority Thread");

        // Setting priorities
        l.setPriority(Thread.MIN_PRIORITY);   // 1
        m.setPriority(Thread.NORM_PRIORITY);  // 5
        n.setPriority(Thread.MAX_PRIORITY);   // 10

        // Starting threads (parallel execution begins)
        l.start();
        m.start();
        n.start();

        // NOTE:
        // Output order is NOT guaranteed (depends on scheduler)
    }
}