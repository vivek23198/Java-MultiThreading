package com.multithreading.interviewQuestion.coreMultiThreading;

public class FizzBuzzPrint {

    private int start = 1;
    private final int max = 20;

    public synchronized void printFizz() throws InterruptedException {
        while(start <= max) {
            while(start <= max && !(start%3 == 0 && start%5 != 0)) {
                wait();
            }
            System.out.print("Fizz ");
            start++;
            notifyAll();
        }
    }

    public synchronized  void printBuzz() throws InterruptedException {
        while (start <= max) {
            while(start <= max && !(start%5 == 0 && start%3 != 0)) {
                wait();
            }
            System.out.print("Buzz ");
            start++;
            notifyAll();
        }
    }

    public  synchronized  void printFizzBuzz() throws InterruptedException {
        while (start <= max) {
            while(start<= max && !(start%15 == 0)) {
                wait();
            }
            System.out.print("FizzBuzz ");
            start++;
            notifyAll();
        }
    }

    public  synchronized  void printNumber() throws InterruptedException {
        while (start <= max) {
            while(start <= max && (start%5 == 0 || start%3 == 0)) {
                wait();
            }
            System.out.print(start+" ");
            start++;
            notifyAll();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        FizzBuzzPrint fizzBuzzPrint = new FizzBuzzPrint();

        Thread t1 = new Thread(() -> {
            try {
                fizzBuzzPrint.printFizz();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        Thread t2 = new Thread(() -> {
            try {
                fizzBuzzPrint.printBuzz();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        Thread t3 = new Thread(() -> {
            try{
                fizzBuzzPrint.printFizzBuzz();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        Thread t4 = new Thread(() -> {
            try{
                fizzBuzzPrint.printNumber();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        t1.start();
        t2.start();
        t3.start();
        t4.start();

        t1.join();
        t2.join();
        t3.join();
        t4.join();

        System.out.println("Task Execution Completed.....");
    }
}
