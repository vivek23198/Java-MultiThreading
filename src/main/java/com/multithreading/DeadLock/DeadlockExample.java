package com.multithreading.DeadLock;

/*
============================= DEADLOCK (INTERVIEW REVISION) =============================

1. What is Deadlock?
   - A situation where two or more threads are blocked forever,
     waiting for each other to release resources

2. Necessary Conditions (ALL must exist):
   ✔ Mutual Exclusion → only one thread can use a resource
   ✔ Hold and Wait → thread holds one lock & waits for another
   ✔ No Preemption → cannot forcefully take lock
   ✔ Circular Wait → circular dependency between threads

3. Key Idea:
   - Break ANY one condition → deadlock avoided

4. Real Example:
   - Thread 1 → holds lock A, waits for B
   - Thread 2 → holds lock B, waits for A

=========================================================================================
*/

class DeadlockDemo {

    static final Object lock1 = new Object();
    static final Object lock2 = new Object();

    public static void main(String[] args) {

        Thread t1 = new Thread(() -> {
            synchronized (lock1) {
                System.out.println("Thread 1 acquired lock1");

                try { Thread.sleep(100); } catch (Exception e) {}

                synchronized (lock2) {
                    System.out.println("Thread 1 acquired lock2");
                }
            }
        });

        Thread t2 = new Thread(() -> {
            synchronized (lock2) {
                System.out.println("Thread 2 acquired lock2");

                try { Thread.sleep(100); } catch (Exception e) {}

                synchronized (lock1) {
                    System.out.println("Thread 2 acquired lock1");
                }
            }
        });

        t1.start();
        t2.start();
    }
}