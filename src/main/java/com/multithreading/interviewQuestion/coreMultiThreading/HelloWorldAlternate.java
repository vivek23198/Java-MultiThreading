package com.multithreading.interviewQuestion.coreMultiThreading;

/*
Print 'Hello' and 'World' alternately from two threads
 */
public class HelloWorldAlternate {
    private static boolean isHelloTurn = true;

    public synchronized void printHello() throws InterruptedException {
        while(!isHelloTurn) {
            wait();
        }
        System.out.print("Hello ");
        Thread.sleep(1000);
        isHelloTurn = false;
    }

    public synchronized void printWorld() throws InterruptedException {
        while (isHelloTurn) {
            wait();
        }
        System.out.print("World ");
        Thread.sleep(1000);
        isHelloTurn = true;
    }

    public static void main(String[] args) throws InterruptedException {
        HelloWorldAlternate helloWorldAlternate = new HelloWorldAlternate();
        Thread t1 = new Thread(() -> {
            for(int i=1; i<= 10; i++) {
                try {
                    helloWorldAlternate.printHello();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        Thread t2 = new Thread(() -> {
            for(int i=1; i<= 10; i++) {
                try {
                    helloWorldAlternate.printWorld();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        t1.start();
        t2.start();

        t1.join();
        t2.join();

        System.out.println("Task Execution Completed");
    }
}
