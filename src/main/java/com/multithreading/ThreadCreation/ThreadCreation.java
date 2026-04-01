package com.multithreading.ThreadCreation;

/*
============================= THREAD CREATION (INTERVIEW REVISION) =============================

1. Ways to Create Thread in Java:
   ✔ Extend Thread class
   ✔ Implement Runnable interface (preferred)

2. Why Runnable is Preferred?
   - Java does not support multiple inheritance
   - Runnable allows extending another class
   - Better separation of task and thread

3. Thread Execution Flow:
   new Thread() → start() → JVM creates new thread → run()

4. Important Methods:
   - start() → creates new thread
   - run() → contains task logic

5. Key Difference:
   - Thread → defines thread
   - Runnable → defines task

==============================================================================================
*/

public class ThreadCreation extends Thread {

    // Overriding run() method → task to be executed by thread
    @Override
    public void run() {
        System.out.println("Welcome to the World of MultiThreading");
    }

    public static void main(String[] args) {

        // ================= METHOD 1: EXTENDING THREAD =================

        ThreadCreation t1 = new ThreadCreation();

        // start() creates new thread and calls run()
        t1.start();


        // ================= METHOD 2: IMPLEMENTING RUNNABLE =================

        ThreadCreation2 t2 = new ThreadCreation2();

        // Pass Runnable object to Thread
        Thread th = new Thread(t2);

        // Start thread
        th.start();
    }
}


// Runnable implementation → defines task
class ThreadCreation2 implements Runnable {

    @Override
    public void run() {
        System.out.println("Thread Created Using Interface");
    }
}