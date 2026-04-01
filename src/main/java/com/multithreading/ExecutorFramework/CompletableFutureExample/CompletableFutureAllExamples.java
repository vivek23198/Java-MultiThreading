package com.multithreading.ExecutorFramework.CompletableFutureExample;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/*
============================= COMPLETABLE FUTURE (INTERVIEW REVISION) =============================

1. What is CompletableFuture?
   - An advanced version of Future
   - Supports async, non-blocking programming
   - Allows chaining, combining, and handling results

2. Why CompletableFuture?
   ✔ Non-blocking
   ✔ Better than Future
   ✔ Supports chaining & composition

3. Key Concepts:
   - Async execution
   - Functional chaining
   - Error handling

4. join() vs get():
   - join() → unchecked exception
   - get()  → checked exception

==============================================================================================
*/

public class CompletableFutureAllExamples {

    public static void main(String[] args)
            throws ExecutionException, InterruptedException {

        // ================= 1. runAsync =================
        // No return value
        CompletableFuture<Void> runAsyncExample =
                CompletableFuture.runAsync(() ->
                        System.out.println("runAsync executed"));

        runAsyncExample.join();


        // ================= 2. supplyAsync =================
        // Returns value
        CompletableFuture<String> supplyAsyncExample =
                CompletableFuture.supplyAsync(() -> "Hello");

        System.out.println("supplyAsync: " + supplyAsyncExample.join());


        // ================= 3. thenApply =================
        // Transform result
        CompletableFuture<String> thenApplyExample =
                CompletableFuture.supplyAsync(() -> "java")
                        .thenApply(str -> str.toUpperCase());

        System.out.println("thenApply: " + thenApplyExample.join());


        // ================= 4. thenAccept =================
        // Consume result (no return)
        CompletableFuture.supplyAsync(() -> "Consumed Value")
                .thenAccept(val ->
                        System.out.println("thenAccept: " + val))
                .join();


        // ================= 5. thenRun =================
        // No input, no output
        CompletableFuture.supplyAsync(() -> "Task Finished")
                .thenRun(() ->
                        System.out.println("thenRun executed"))
                .join();


        // ================= 6. thenCompose =================
        // Chain dependent async calls
        CompletableFuture<String> composeExample =
                CompletableFuture.supplyAsync(() -> "Order")
                        .thenCompose(order ->
                                CompletableFuture.supplyAsync(() ->
                                        order + " -> Payment"));

        System.out.println("thenCompose: " + composeExample.join());


        // ================= 7. thenCombine =================
        // Combine independent futures
        CompletableFuture<String> f1 =
                CompletableFuture.supplyAsync(() -> "User");

        CompletableFuture<String> f2 =
                CompletableFuture.supplyAsync(() -> "Order");

        CompletableFuture<String> combineExample =
                f1.thenCombine(f2, (a, b) -> a + " + " + b);

        System.out.println("thenCombine: " + combineExample.join());


        // ================= 8. allOf =================
        // Wait for all tasks
        CompletableFuture<Void> allOfExample =
                CompletableFuture.allOf(f1, f2);

        allOfExample.join();
        System.out.println("allOf completed");


        // ================= 9. anyOf =================
        // First completed task
        CompletableFuture<Object> anyOfExample =
                CompletableFuture.anyOf(f1, f2);

        System.out.println("anyOf: " + anyOfExample.join());


        // ================= 10. exceptionally =================
        // Handle exceptions
        CompletableFuture<Object> exceptionExample =
                CompletableFuture.supplyAsync(() -> {
                    throw new RuntimeException("Error");
                }).exceptionally(ex -> "Fallback Value");

        System.out.println("exceptionally: " + exceptionExample.join());


        // ================= 11. handle =================
        // Handle success + failure
        CompletableFuture<String> handleExample =
                CompletableFuture.supplyAsync(() -> "Handle Test")
                        .handle((res, ex) -> {
                            if (ex != null) return "Error handled";
                            return res + " Processed";
                        });

        System.out.println("handle: " + handleExample.join());


        // ================= 12. whenComplete =================
        // Post-completion action (no change in result)
        CompletableFuture.supplyAsync(() -> "Completed Task")
                .whenComplete((res, ex) ->
                        System.out.println("whenComplete: " + res))
                .join();


        System.out.println("All CompletableFuture examples executed");
    }
}