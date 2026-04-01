package com.multithreading.synchronization.Locks.ReentrantLock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/*
============================= REENTRANTLOCK + TRYLOCK (INTERVIEW REVISION) =============================

1. What is ReentrantLock?
   - Advanced locking mechanism (alternative to synchronized)
   - Provides more control over locking

2. What is tryLock()?
   - Attempts to acquire lock without blocking indefinitely
   - Can specify timeout
   - Prevents deadlock situations

3. Why use tryLock(timeout)?
   ✔ Avoid waiting forever
   ✔ Improve responsiveness
   ✔ Handle contention gracefully

4. Key Behavior:
   - If lock acquired → proceed
   - If not → skip or retry

5. Important Methods:
   - lock() → blocks until lock acquired
   - tryLock() → immediate attempt
   - tryLock(time, unit) → waits for given time

6. Use Case:
   - Banking, payment systems, critical sections

==============================================================================================
*/

public class BankAccount {

    // Shared resource
    private int balance = 100;

    // Lock for synchronization
    private final Lock lock = new ReentrantLock();

    public void withdraw(int amount) {

        System.out.println(Thread.currentThread().getName() +
                " attempting to withdraw " + amount);

        try {

            // Try to acquire lock within 1 second
            if (lock.tryLock(1000, TimeUnit.MILLISECONDS)) {

                try {

                    // Check sufficient balance
                    if (balance >= amount) {

                        System.out.println(Thread.currentThread().getName() +
                                " proceeding with withdrawal");

                        // Simulate processing delay
                        Thread.sleep(3000);

                        balance -= amount;

                        System.out.println(Thread.currentThread().getName() +
                                " completed withdrawal. Remaining balance: " + balance);

                    } else {
                        System.out.println(Thread.currentThread().getName() +
                                " insufficient balance");
                    }

                } finally {
                    // Always release lock
                    lock.unlock();
                }

            } else {
                // Could not acquire lock within timeout
                System.out.println(Thread.currentThread().getName() +
                        " could not acquire the lock, will try later");
            }

        } catch (InterruptedException e) {

            // Restore interrupt status
            Thread.currentThread().interrupt();
        }
    }

    public static void main(String[] args) {

        BankAccount sbi = new BankAccount();

        // Task to withdraw money
        Runnable task = new Runnable() {
            @Override
            public void run() {
                sbi.withdraw(50);
            }
        };

        // Two threads trying to access same account
        Thread t1 = new Thread(task, "Thread 1");
        Thread t2 = new Thread(task, "Thread 2");

        t1.start();
        t2.start();
    }
}