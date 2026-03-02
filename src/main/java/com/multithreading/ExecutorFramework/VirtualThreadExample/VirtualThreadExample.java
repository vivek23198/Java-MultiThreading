//package com.multithreading.ExecutorFramework.VirtualThreadExample;
//
//import java.util.concurrent.ExecutorService;
//import java.util.concurrent.Executors;
//import java.util.stream.IntStream;
//
//public class VirtualThreadExample {
//
//    public static void main(String[] args) throws Exception {
//
//        System.out.println("Main Thread : " + Thread.currentThread());
//
//        // ===================================================
//        // 1️⃣ Simple Virtual Thread
//        // ===================================================
//        Thread.startVirtualThread(() -> {
//            System.out.println("Simple Virtual Thread -> "
//                    + Thread.currentThread());
//        });
//
//        // ===================================================
//        // 2️⃣ Virtual Thread using Builder API
//        // ===================================================
//        Thread vt = Thread.ofVirtual()
//                .name("My-Virtual-Thread")
//                .start(() -> {
//                    System.out.println("Builder Virtual Thread -> "
//                            + Thread.currentThread());
//                });
//
//        vt.join();
//
//        // ===================================================
//        // 3️⃣ Multiple Virtual Threads
//        // ===================================================
//        System.out.println("\nCreating multiple virtual threads");
//
//        IntStream.range(1, 6).forEach(i ->
//                Thread.startVirtualThread(() -> {
//                    System.out.println("Task " + i +
//                            " executed by " + Thread.currentThread());
//                })
//        );
//
//        // ===================================================
//        // 4️⃣ Virtual Thread Executor (MOST IMPORTANT)
//        // ===================================================
//        System.out.println("\nExecutorService with Virtual Threads");
//
//        try (ExecutorService executor =
//                     Executors.newVirtualThreadPerTaskExecutor()) {
//
//            IntStream.range(1, 10).forEach(i ->
//                    executor.submit(() -> {
//                        System.out.println("Executor Task " + i +
//                                " -> " + Thread.currentThread());
//
//                        // Simulate blocking I/O
//                        Thread.sleep(1000);
//
//                        return i;
//                    })
//            );
//        }
//
//        // ===================================================
//        // 5️⃣ Virtual Threads Handling Blocking Operations
//        // ===================================================
//        System.out.println("\nBlocking calls using Virtual Threads");
//
//        Thread.startVirtualThread(() -> {
//            try {
//                System.out.println("Before sleep -> "
//                        + Thread.currentThread());
//                Thread.sleep(2000);
//                System.out.println("After sleep -> "
//                        + Thread.currentThread());
//            } catch (InterruptedException e) {
//                Thread.currentThread().interrupt();
//            }
//        });
//
//        // Wait main thread
//        Thread.sleep(3000);
//
//        System.out.println("\nAll Virtual Thread examples completed");
//    }
//}
