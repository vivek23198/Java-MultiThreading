package com.multithreading.interviewQuestion.coreMultiThreading;

/*
Thread-ODD prints only odd numbers: 1, 3, 5, 7... 19. Thread-EVEN prints only even numbers: 2, 4, 6, 8... 20.
Output MUST be perfectly sequential: 1, 2, 3, 4, 5, 6...
No number should be skipped or duplicated.
Use wait() and notify() for synchronization.
 */
public class OddAndEven {
    private static boolean isEven = false;

    public synchronized void printOdd(int i) {
        while(isEven) {
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        System.out.print(i+" ");
        isEven = true;
        notifyAll();
    }

    public synchronized void printEven(int i)  {
        while (!isEven) {
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        System.out.print(i+" ");
        isEven = false;
        notifyAll();
    }

    public static void main(String[] args) {
        OddAndEven oddAndEven = new OddAndEven();

        Thread evenPrint = new Thread(() -> {
            for(int i=2; i<= 20; i+=2) {
                oddAndEven.printEven(i);
            }
        });

        Thread oddPrint = new Thread(() -> {
            for(int i=1; i<= 20; i+=2) {
                oddAndEven.printOdd(i);
            }
        });

        evenPrint.start();
        oddPrint.start();


        try {
            evenPrint.join();
            oddPrint.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println();
        System.out.println("Task Execution Completed....");
    }
}
