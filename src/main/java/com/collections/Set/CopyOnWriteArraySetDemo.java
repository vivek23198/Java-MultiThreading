package com.collections.Set;

import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.CopyOnWriteArraySet;

public class CopyOnWriteArraySetDemo {
    public static void main(String[] args) {

        // CopyOnWriteArraySet Properties:
        // 1. Thread-safe (no explicit synchronization needed)
        // 2. Uses Copy-On-Write mechanism → new copy created on modification
        // 3. Iterators are fail-safe (no ConcurrentModificationException)
        // 4. Iteration works on snapshot → does NOT reflect changes made during iteration
        // 5. No duplicate elements allowed
        // 6. Best suited for READ-heavy scenarios (writes are expensive)

        // ConcurrentSkipListSet Properties:
        // 1. Thread-safe
        // 2. Maintains sorted order (like TreeSet)
        // 3. Uses Skip List data structure
        // 4. Allows concurrent modifications during iteration
        // 5. Iterators are weakly consistent (may reflect some changes)

        CopyOnWriteArraySet<Integer> copyOnWriteSet = new CopyOnWriteArraySet<>();
        ConcurrentSkipListSet<Integer> concurrentSkipListSet = new ConcurrentSkipListSet<>();

        // Adding elements 1 to 5 in both sets
        for (int i = 1; i <= 5; i++) {
            copyOnWriteSet.add(i);
            concurrentSkipListSet.add(i);
        }

        // Initial state of both sets
        System.out.println("Initial CopyOnWriteArraySet: " + copyOnWriteSet);
        System.out.println("Initial ConcurrentSkipListSet: " + concurrentSkipListSet);

        System.out.println("\nIterating and modifying CopyOnWriteArraySet:");

        // Iteration over CopyOnWriteArraySet
        for (Integer num : copyOnWriteSet) {
            System.out.println("Reading from CopyOnWriteArraySet: " + num);

            // Modification during iteration:
            // SAFE → No exception
            // But iterator does NOT see this change (snapshot behavior)
            copyOnWriteSet.add(6);
        }

        System.out.println("\nIterating and modifying ConcurrentSkipListSet:");

        // Iteration over ConcurrentSkipListSet
        for (Integer num : concurrentSkipListSet) {
            System.out.println("Reading from ConcurrentSkipListSet: " + num);

            // Modification during iteration:
            // SAFE → No exception
            // Iterator MAY reflect changes (weakly consistent)
            concurrentSkipListSet.add(6);
        }
    }
}