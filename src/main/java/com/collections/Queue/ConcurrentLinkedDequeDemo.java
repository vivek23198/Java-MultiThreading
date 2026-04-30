package com.collections.Queue;

import java.util.concurrent.ConcurrentLinkedDeque;

public class ConcurrentLinkedDequeDemo {
    public static void main(String[] args) {

        // 🔹 ConcurrentLinkedDeque Properties:
        // 1. Thread-safe, non-blocking double-ended queue (Deque)
        // 2. Uses CAS (Compare-And-Swap) instead of locks → high performance
        // 3. Allows concurrent access by multiple threads without blocking
        // 4. Based on linked nodes (not array)
        // 5. Does NOT allow null elements
        // 6. Provides weakly consistent iterators (no ConcurrentModificationException)
        // 7. Suitable for high-concurrency, low-latency applications

        // Creating ConcurrentLinkedDeque
        ConcurrentLinkedDeque<String> deque = new ConcurrentLinkedDeque<>();

        // Adding elements
        deque.add("Element1");        // adds at tail (same as addLast)
        deque.addFirst("Element0");   // adds at front
        deque.addLast("Element2");    // explicitly adds at end

        // Current deque: [Element0, Element1, Element2]
        System.out.println(deque);

        // Removing elements from both ends
        String first = deque.removeFirst(); // removes head → Element0
        String last = deque.removeLast();   // removes tail → Element2

        // Remaining deque: [Element1]
    }
}