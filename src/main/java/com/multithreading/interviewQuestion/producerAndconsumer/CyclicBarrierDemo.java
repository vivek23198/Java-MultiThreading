package com.multithreading.interviewQuestion.producerAndconsumer;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;

/*
4 player threads must all reach 'ready' state before each game round starts.
Each player does random preparation (1-3s) then calls barrier.await().
Game round starts ONLY when all 4 players are ready. After each round, barrier resets automatically.
Run 3 rounds. Use CyclicBarrier(4, barrierAction) where barrierAction prints 'Round X starting!'.
 */
public class CyclicBarrierDemo {


    public static void main(String[] args) throws InterruptedException {
        AtomicInteger round = new AtomicInteger(1);

        CyclicBarrier cyclicBarrier = new CyclicBarrier(4,
                () -> System.out.println("Round "+round.getAndIncrement()+" starting!"));

        Runnable task = () -> {
            for(int i=1; i<=3; i++) {
                try {
                    int prepTime = ThreadLocalRandom.current().nextInt(1000, 3000);

                    System.out.println(Thread.currentThread().getName() +
                            " preparing for round " + i + " (" + prepTime + "ms)");

                    Thread.sleep(prepTime);

                    System.out.println(Thread.currentThread().getName() +
                            " ready for round " + i);

                    cyclicBarrier.await();

//                    System.out.println(Thread.currentThread().getName() +
//                            " playing round " + i);
                } catch (InterruptedException | BrokenBarrierException e) {
                    throw new RuntimeException(e);
                }
            }
        };

        Thread t1 = new Thread(task, "Player-1");
        Thread t2 = new Thread(task, "Player-2");
        Thread t3 = new Thread(task, "Player-3");
        Thread t4 = new Thread(task, "Player-4");

        t1.start();
        t2.start();
        t3.start();
        t4.start();

        t1.join();
        t2.join();
        t3.join();
        t4.join();

        System.out.println("All operation completed");
    }
}
