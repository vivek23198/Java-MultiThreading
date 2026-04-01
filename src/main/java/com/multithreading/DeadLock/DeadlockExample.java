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

class Resource {

    // ================= METHOD 1 =================
    public synchronized void method1(Resource r) {

        String threadName = Thread.currentThread().getName();

        // Lock acquired on current object
        System.out.println(threadName + " locked Resource1");

        try {
            Thread.sleep(100); // simulate delay
        } catch (Exception e) {}

        // Trying to acquire second lock
        System.out.println(threadName + " trying to lock Resource2");

        // Nested lock → possible deadlock
        r.method2();
    }

    // ================= METHOD 2 =================
    public synchronized void method2() {

        String threadName = Thread.currentThread().getName();

        // Lock acquired on second object
        System.out.println(threadName + " locked Resource2");
    }
}

public class DeadlockExample {

    public static void main(String[] args) {

        Resource r1 = new Resource();
        Resource r2 = new Resource();

        // Thread 1
        Thread t1 = new Thread(() -> {
            r1.method1(r2);   // Locks r1 → waits for r2
        }, "Thread-1");

        // Thread 2
        Thread t2 = new Thread(() -> {
            r2.method1(r1);   // Locks r2 → waits for r1
        }, "Thread-2");

        t1.start();
        t2.start();
    }
}