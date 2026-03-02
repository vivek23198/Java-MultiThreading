package com.multithreading.DeadLock;

/**
 * For DeadLock to Occur Following condition should be fulfilled
 *
 * All must exist:
 *
 * Mutual Exclusion ✅ - Resource can be used by only one thread at a time.(synchronized)
 *
 * Hold and Wait ✅  -  Thread holds one lock while waiting for another.
 *
 * No Preemption ✅  -  Lock cannot be forcefully taken away. Only owning thread can release it.Java monitors behave like this.
 *
 * Circular Wait ✅ - Threads form circular dependency:
 *
 * Break any one → no deadlock.
 */
class Resource {

    public synchronized void method1(Resource r) {
        String threadName = Thread.currentThread().getName();
        System.out.println(threadName + " locked Resource1");

        try { Thread.sleep(100); } catch (Exception e) {}

        System.out.println(threadName + " trying to lock Resource2");
        r.method2();
    }

    public synchronized void method2() {
        String threadName = Thread.currentThread().getName();
        System.out.println(threadName + " locked Resource2");
    }
}

public class DeadlockExample {

    public static void main(String[] args) {

        Resource r1 = new Resource();
        Resource r2 = new Resource();

        Thread t1 = new Thread(() -> {
            r1.method1(r2);   // Lock r1 → wait r2
        }, "Thread-1");

        Thread t2 = new Thread(() -> {
            r2.method1(r1);   // Lock r2 → wait r1
        }, "Thread-2");

        t1.start();
        t2.start();
    }
}