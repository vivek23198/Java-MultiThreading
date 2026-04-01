package com.collections.Map;

import java.util.LinkedHashMap;
import java.util.Map;

/*
============================= LRU CACHE USING LINKEDHASHMAP =============================

1. What is LRU Cache?
   - LRU = Least Recently Used
   - Removes the element that was NOT used recently when capacity is full

2. Why LinkedHashMap?
   - Supports access order (via doubly linked list)
   - Maintains order of usage → perfect for LRU

3. Key Concept:
   - accessOrder = true → recently accessed elements move to END
   - Oldest (least recently used) stays at HEAD

4. removeEldestEntry():
   - Automatically called after every put()
   - If returns true → eldest entry is removed

5. Time Complexity:
   - get() → O(1)
   - put() → O(1)
   - remove() → O(1)

6. Space Complexity:
   - O(capacity)

7. Real Use Cases:
   - Caching (DB queries, API responses)
   - Memory optimization systems

=========================================================================================
*/

public class LRUCache<K, V> extends LinkedHashMap<K, V> {

    // Maximum capacity of cache
    private int capacity;

    public LRUCache(int capacity) {

        // accessOrder = true → enables LRU behavior
        super(capacity, 0.75f, true);

        this.capacity = capacity;
    }

    // This method decides when to remove the eldest entry
    @Override
    protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {

        // If size exceeds capacity → remove least recently used
        return size() > capacity;
    }

    public static void main(String[] args) {

        // Create LRU Cache with capacity = 3
        LRUCache<String, Integer> studentMap = new LRUCache<>(3);

        // Insert elements
        studentMap.put("Bob", 99);     // [Bob]
        studentMap.put("Alice", 89);   // [Bob, Alice]
        studentMap.put("Ram", 91);     // [Bob, Alice, Ram]

        // Update Bob → moves to most recently used (end)
        studentMap.put("Bob", 100);    // [Alice, Ram, Bob]

        // Insert new key → capacity exceeded → remove LRU (Alice)
        studentMap.put("Vipul", 89);   // [Ram, Bob, Vipul]

        // Final output
        System.out.println(studentMap);
    }
}