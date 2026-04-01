////package com.multithreading.ExecutorFramework.VirtualThreadExample;
//
//import java.util.concurrent.ExecutorService;
//import java.util.concurrent.Executors;
//import java.util.stream.IntStream;
//
///*
//============================= VIRTUAL THREADS (JAVA 21 - PROJECT LOOM) =============================
//
//1. What are Virtual Threads?
//   - Lightweight threads managed by JVM (not OS)
//   - Introduced in Java 21 (Project Loom)
//
//2. Why Virtual Threads?
//   ✔ Can create millions of threads
//   ✔ Cheap (low memory)
//   ✔ Ideal for I/O-bound tasks
//
//3. Platform Thread vs Virtual Thread:
//
//   Platform Thread:
//   - OS-managed
//   - Expensive
//   - Limited (~1000s)
//
//   Virtual Thread:
//   - JVM-managed
//   - Lightweight
//   - Millions possible
//
//4. Key Concept:
//   - Virtual threads are mapped to a small number of platform threads
//   - Uses carrier threads internally
//
//5. Best Use Case:
//   ✔ I/O operations (DB calls, APIs, file reading)
//   ❌ CPU-heavy tasks (use thread pool instead)
//
//==============================================================================================
//*/
//
//public class VirtualThreadExample {
//
//    public static void main(String[] args) throws Exception {
//
//        System.out.println("Main Thread : " + Thread.currentThread());
//
//
//        // ===================================================
//        // 1️⃣ Simple Virtual Thread
//        // ===================================================
//
//        // Creates and starts a virtual thread
//        Thread.startVirtualThread(() -> {
//            System.out.println("Simple Virtual Thread -> "
//                    + Thread.currentThread());
//        });
//
//
//        // ===================================================
//        // 2️⃣ Virtual Thread using Builder API
//        // ===================================================
//
//        Thread vt = Thread.ofVirtual()
//                .name("My-Virtual-Thread")
//                .start(() -> {
//                    System.out.println("Builder Virtual Thread -> "
//                            + Thread.currentThread());
//                });
//
//        vt.join(); // wait for completion
//
//
//        // ===================================================
//        // 3️⃣ Multiple Virtual Threads
//        // ===================================================
//
//        System.out.println("\nCreating multiple virtual threads");
//
//        IntStream.range(1, 6).forEach(i ->
//                Thread.startVirtualThread(() -> {
//                    System.out.println("Task " + i +
//                            " executed by " + Thread.currentThread());
//                })
//        );
//
//
//        // ===================================================
//        // 4️⃣ Virtual Thread Executor (MOST IMPORTANT 🔥)
//        // ===================================================
//
//        System.out.println("\nExecutorService with Virtual Threads");
//
//        try (ExecutorService executor =
//                     Executors.newVirtualThreadPerTaskExecutor()) {
//
//            IntStream.range(1, 10).forEach(i ->
//                    executor.submit(() -> {
//
//                        System.out.println("Executor Task " + i +
//                                " -> " + Thread.currentThread());
//
//                        // Simulating blocking I/O
//                        Thread.sleep(1000);
//
//                        return i;
//                    })
//            );
//        }
//
//
//        // ===================================================
//        // 5️⃣ Handling Blocking Operations
//        // ===================================================
//
//        System.out.println("\nBlocking calls using Virtual Threads");
//
//        Thread.startVirtualThread(() -> {
//            try {
//                System.out.println("Before sleep -> "
//                        + Thread.currentThread());
//
//                Thread.sleep(2000);
//
//                System.out.println("After sleep -> "
//                        + Thread.currentThread());
//
//            } catch (InterruptedException e) {
//                Thread.currentThread().interrupt();
//            }
//        });
//
//
//        // Wait main thread to complete
//        Thread.sleep(3000);
//
//        System.out.println("\nAll Virtual Thread examples completed");
//    }
//}