package com.multithreading.ExecutorFramework;

import java.util.concurrent.Phaser;

public class PhaserExample {
    public static void main(String[] args) {
        Phaser phaser = new Phaser(1); // Register self (main thread)

        Runnable task = () -> {
            phaser.register(); // Register new thread
            System.out.println(Thread.currentThread().getName() + " finished Phase 1.");
            phaser.arriveAndAwaitAdvance(); // Wait for all to finish Phase 1

            System.out.println(Thread.currentThread().getName() + " finished Phase 2.");
            phaser.arriveAndDeregister(); // Finish and leave
        };

        new Thread(task, "Thread-1").start();
        new Thread(task, "Thread-2").start();

        phaser.arriveAndAwaitAdvance(); // Main thread coordination
        System.out.println("Phase 1 complete across all threads.");
        phaser.arriveAndDeregister(); // Main thread leaves
    }
}
