package com.multithreading.ExecutorFramework;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/*
============================= EXECUTOR SERVICE & FUTURE =============================

1. What is ExecutorService?
   - Manages thread pool
   - Avoids manual thread creation
   - Improves performance and scalability

2. What is Future?
   - Represents result of an asynchronous computation
   - Allows checking status and retrieving result later

3. submit() vs execute():
   - execute() → Runnable, no return
   - submit()  → Callable/Runnable, returns Future

4. Important Methods of Future:
   - get()      → blocks until result is available
   - isDone()   → checks if task completed
   - cancel()   → cancels task

5. Key Concept:
   - Asynchronous execution (non-blocking until get())

=====================================================================================
*/

public class FutureExample {

    public static void main(String[] args)
            throws ExecutionException, InterruptedException {

        // Create thread pool with single thread
        ExecutorService executorService =
                Executors.newSingleThreadExecutor();

        // Submit task → returns Future
        Future<String> future =
                executorService.submit(() -> "Hello");

        // get() → blocking call until result is ready
        System.out.println(future.get()); // prints "Hello"

        // Check if task is completed
        if (future.isDone()) {
            System.out.println("Task is done !");
        }

        // Shutdown executor
        executorService.shutdown();
    }
}