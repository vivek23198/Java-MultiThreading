package com.multithreading.synchronization.withSync;

/*
============================= SYNCHRONIZATION (THREAD SAFETY) =============================

1. What is Synchronization?
   - Mechanism to control access to shared resources
   - Ensures only ONE thread executes critical section at a time

2. What is Critical Section?
   - Code that accesses shared data (here → count++)

3. How synchronized works?
   - Uses intrinsic lock (monitor)
   - Thread must acquire lock before executing method

4. Fix in this Code:
   - increment() is synchronized → only one thread at a time

5. Result:
   - No race condition
   - Output always correct (2000)

6. Time Complexity:
   - Slight overhead due to locking

===========================================================================================
*/

class Counter {

    private int count = 0; // shared resource

    // synchronized method → thread-safe
    public synchronized void increment() {

        // Now this operation becomes thread-safe
        count++;
    }

    public int getCount() {
        return count;
    }
}

public class MyThread extends Thread {

    private Counter counter;

    public MyThread(Counter counter) {
        this.counter = counter;
    }

    @Override
    public void run() {

        // Each thread increments 1000 times
        for (int i = 0; i < 1000; i++) {
            counter.increment();
        }
    }

    public static void main(String[] args) {

        Counter counter = new Counter();

        // Two threads sharing same counter
        MyThread t1 = new MyThread(counter);
        MyThread t2 = new MyThread(counter);

        t1.start();
        t2.start();

        try {
            // Wait for threads to finish
            t1.join();
            t2.join();
        } catch (Exception e) {
            Thread.currentThread().interrupt();
        }

        // Now always correct
        System.out.println(counter.getCount()); // Always 2000 ✅
    }
}