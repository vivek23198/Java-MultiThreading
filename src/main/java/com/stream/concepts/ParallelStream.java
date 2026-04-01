package com.stream.concepts;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

/*
============================= PARALLEL STREAMS (INTERVIEW REVISION) =============================

1. What is Parallel Stream?
   - A stream that processes elements using multiple threads
   - Uses ForkJoinPool (common pool) internally

2. Why use Parallel Streams?
   - Improves performance for:
        ✔ Large datasets
        ✔ CPU-intensive tasks
        ✔ Independent operations

3. Sequential vs Parallel:
   - stream()          → single thread
   - parallelStream()  → multiple threads

4. Time Complexity:
   - Same logically, but faster due to parallel execution
   - Overhead exists → not always faster

5. Important Conditions for Parallel Streams:
   ✔ Stateless operations
   ✔ No shared mutable state
   ✔ Independent tasks

6. When NOT to use:
   ❌ Small datasets
   ❌ I/O operations
   ❌ Stateful logic (like cumulative sum)

==============================================================================================
*/

public class ParallelStream {
    public static void main(String[] args) {

        // ================= PERFORMANCE COMPARISON =================

        long startTime = System.currentTimeMillis();

        // Create list from 1 to 20000
        List<Integer> list = Stream.iterate(1, x -> x + 1)
                .limit(20000)
                .toList();

        // Sequential processing
        List<Long> factorialsList =
                list.stream()
                        .map(ParallelStream::factorial)
                        .toList();

        long endTime = System.currentTimeMillis();

        System.out.println("Time taken with sequential stream: " +
                (endTime - startTime) + " ms");


        // ================= PARALLEL STREAM =================

        startTime = System.currentTimeMillis();

        // Parallel processing (multi-threaded)
        factorialsList =
                list.parallelStream()
                        .map(ParallelStream::factorial)
                        .toList();

        // You can convert back to sequential if needed
        // .sequential()

        endTime = System.currentTimeMillis();

        System.out.println("Time taken with parallel stream: " +
                (endTime - startTime) + " ms");


        // ================= IMPORTANT NOTE =================

        // Parallel streams are best for:
        // ✔ CPU-intensive tasks (like factorial)
        // ✔ Independent operations

        // Not good for:
        // ❌ Small tasks
        // ❌ Shared mutable state


        // ================= STATEFUL ISSUE EXAMPLE =================

        // Cumulative Sum → depends on previous values (STATEFUL)
        // [1, 2, 3, 4, 5] → [1, 3, 6, 10, 15]

        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);

        AtomicInteger sum = new AtomicInteger(0);

        // Sequential ensures correct order
        List<Integer> cumulativeSum =
                numbers.stream()
                        .sequential() // important
                        .map(sum::addAndGet)
                        .toList();

        System.out.println("Expected cumulative sum: [1, 3, 6, 10, 15]");
        System.out.println("Actual result with parallel stream: " + cumulativeSum);
    }

    // CPU-intensive operation
    private static long factorial(int n) {

        long result = 1;

        for (int i = 2; i <= n; i++) {
            result *= i;
        }

        return result;
    }
}