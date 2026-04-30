package com.multithreading.AtomicInteger;

/*
============================= VOLATILE KEYWORD (INTERVIEW REVISION) =============================

1. What is volatile?
   - Ensures visibility of changes across threads
   - When one thread updates a variable → other threads see updated value immediately

2. Problem without volatile:
   - Threads may use cached value (CPU cache)
   - Changes made by one thread may NOT be visible to others

3. What volatile guarantees:
   ✔ Visibility (latest value is always read from main memory)
   ❌ NOT atomicity (no thread safety for compound operations)

4. Use Case:
   - Flags, status variables, configuration values

==============================================================================================
*/

class SharedObj {

    // volatile ensures visibility across threads
    private volatile boolean flag = false;

    // Writer thread updates the flag
    public void setFlagTrue() {
        System.out.println("Writer thread made the flag true !");
        flag = true;
    }

    // Reader thread continuously checks flag
    public void printIfFlagTrue() {

        // Busy waiting (spin loop)
        while (!flag) {
            System.out.println("Reading Flag since it is False");
            // Without volatile → may never exit loop ❌
        }

        System.out.println("Flag is true !");
    }
}

public class VolatileExample {

    public static void main(String[] args) {

        SharedObj sharedObj = new SharedObj();

        // Writer thread
        Thread writerThread = new Thread(() -> {

            try {
                Thread.sleep(1000); // simulate delay
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            sharedObj.setFlagTrue();
        });

        // Reader thread
        Thread readerThread = new Thread(() ->
                sharedObj.printIfFlagTrue());

        writerThread.start();
        readerThread.start();
    }
}