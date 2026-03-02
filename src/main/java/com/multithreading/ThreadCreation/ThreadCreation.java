package com.multithreading.ThreadCreation;

public class ThreadCreation extends Thread{

    @Override
    public void run() {
        System.out.println("Welcome to the World of MultiThreading");
    }

    public static void main(String[] args) {
        ThreadCreation t1 = new ThreadCreation();
        t1.start();

        ThreadCreation2 t2 = new ThreadCreation2();
        Thread th = new Thread(t2);
        th.start();
    }


}

class ThreadCreation2 implements Runnable {

    @Override
    public void run() {
        System.out.println("Thread Created Using Interface");
    }
}
