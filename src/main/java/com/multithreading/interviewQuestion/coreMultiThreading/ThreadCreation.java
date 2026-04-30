package com.multithreading.interviewQuestion.coreMultiThreading;

class Thread1 extends  Thread {

    @Override
    public void run() {
        try {
            for(int i=1; i<=5; i++) {
                System.out.println(Thread.currentThread().getName() +
                        " | ID: " + Thread.currentThread().getId() +
                        " | Count: " + i);
                Thread.sleep(3000);
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}

class Thread2 implements Runnable {

    @Override
    public void run() {
        try {
            for(int i=1; i<=5; i++) {
                System.out.println(Thread.currentThread().getName() +
                        " | ID: " + Thread.currentThread().getId() +
                        " | Count: " + i);
                Thread.sleep(3000);
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}

public class ThreadCreation {

    public static void main(String[] args) {
        //Both print their thread name and ID 5 times with 300ms delay
        Thread1 t1 = new Thread1();
        t1.start();

        Thread t2 = new Thread(new Thread2());
        t2.start();
    }
}
