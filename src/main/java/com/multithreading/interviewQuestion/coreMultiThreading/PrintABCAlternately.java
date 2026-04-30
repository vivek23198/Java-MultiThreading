package com.multithreading.interviewQuestion.coreMultiThreading;

/*
Three threads print A, B, C in order — ABCABC...
 */
public class PrintABCAlternately {

    private int value = 1;

    public synchronized void printA() throws InterruptedException {
        while(value != 1) {
            wait();
        }
        System.out.print("A");
        value = 2;
        notifyAll();
    }

    public synchronized void printB() throws InterruptedException {
        while(value != 2) {
            wait();
        }
        System.out.print("B");
        value = 3;
        notifyAll();
    }

    public synchronized void printC() throws InterruptedException {
        while(value != 3) {
            wait();
        }
        System.out.print("C");
        value = 1;
        notifyAll();
    }

    public static void main(String[] args) throws InterruptedException {
        PrintABCAlternately printABCAlternately = new PrintABCAlternately();
        Thread t1 = new Thread(() -> {
            for(int i=1; i<=10; i++) {
                try {
                    printABCAlternately.printA();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        Thread t2 = new Thread(() -> {
            for(int i=1; i<=10; i++) {
                try {
                    printABCAlternately.printB();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        Thread t3 = new Thread(() -> {
            for(int i=1; i<=10; i++) {
                try {
                    printABCAlternately.printC();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        t1.start();
        t2.start();
        t3.start();

        t1.join();
        t2.join();
        t3.join();

        System.out.println();
        System.out.println("Execution Completed.....");

    }
}
