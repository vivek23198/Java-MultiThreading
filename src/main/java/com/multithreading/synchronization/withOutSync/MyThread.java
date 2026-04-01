package com.multithreading.synchronization.withOutSync;

/*
============================= RACE CONDITION (WITHOUT SYNCHRONIZATION) =============================

1. What is Race Condition?
   - When multiple threads access and modify shared data concurrently
   - Final result depends on execution order → unpredictable

2. Problem in this Code:
   - count++ is NOT atomic
   - It involves 3 steps:
        1. Read value
        2. Increment
        3. Write back

3. Issue:
   - Two threads may read same value at same time
   - Both increment → one update lost

4. Result:
   - Expected: 2000
   - Actual: less than or equal to 2000 (inconsistent)

5. Why it happens?
   - No synchronization → no thread safety

==============================================================================================
*/

class Counter {

    private int count = 0; // shared resource

    public void increment() {

        // NOT atomic operation → causes race condition
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
            // Wait for both threads to finish
            t1.join();
            t2.join();
        } catch (Exception e) {
            Thread.currentThread().interrupt();
        }

        // Expected: 2000
        // Actual: unpredictable (race condition)
        System.out.println(counter.getCount());
    }
}