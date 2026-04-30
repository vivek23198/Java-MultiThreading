package com.collections.Queue;

import java.util.PriorityQueue;

public class PriorityQueueDemo {
    public static void main(String[] args) {

        // PriorityQueue Properties:
        // 1. Part of Queue interface (but behaves differently from FIFO queues)
        // 2. Orders elements based on priority (natural ordering by default)
        // 3. Default is MIN-HEAP → smallest element has highest priority
        // 4. Does NOT allow null elements
        // 5. Allows duplicate elements
        // 6. Not thread-safe
        // 7. Internal structure = Binary Heap
        // 8. add(), poll() → O(log n), peek() → O(1)

        // Creating PriorityQueue (min-heap by default)
        PriorityQueue<Integer> pq = new PriorityQueue<>();

        // Adding elements
        pq.add(15);
        pq.add(10);
        pq.add(30);
        pq.add(5);

        // Printing queue directly
        // ⚠️ Not sorted → internal heap structure (only head is guaranteed smallest)
        System.out.println(pq);

        // Removing elements one by one
        // poll() always removes smallest element (min-heap behavior)
        while (!pq.isEmpty()){
            System.out.println(pq.poll()); // Output: 5, 10, 15, 30
        }

        // Internal Working:
        // PriorityQueue uses a binary heap (array-based)
        // Root always contains smallest element (for min-heap)

        // Custom Comparator Example (Max-Heap):
//         PriorityQueue<Integer> maxHeap = new PriorityQueue<>((a, b) -> b - a);
    }
}