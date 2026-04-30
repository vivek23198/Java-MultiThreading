package com.collections.Map;

import java.util.concurrent.ConcurrentSkipListMap;

public class ConcurrentSkipListMapDemo {
    public static void main(String[] args) {

        // ConcurrentSkipListMap Properties:
        // 1. Thread-safe → designed for concurrent access (no full synchronization)
        // 2. Sorted Map → maintains keys in natural order (like TreeMap)
        // 3. Uses Skip List data structure internally (not tree-based)
        // 4. Allows concurrent reads & writes → high performance in multithreaded apps
        // 5. Does NOT allow null key or null value (throws NullPointerException)
        // 6. Non-blocking algorithm (uses CAS internally instead of locking entire structure)
        // 7. Provides log(n) time complexity for operations (put, get, remove)
        // 8. Better alternative to synchronized TreeMap
        // 9. Suitable when you need both sorting + concurrency

        // Creating ConcurrentSkipListMap (thread-safe + sorted map)
        ConcurrentSkipListMap<String, Integer> map = new ConcurrentSkipListMap<>();

        // Inserting key-value pair
        // Keys will be stored in sorted (alphabetical) order
        map.put("Apple", 2);

        // Example: if more elements added → automatically sorted
        // map.put("Banana", 3);
        // map.put("Cherry", 1);

        // Thread-safe retrieval
        System.out.println(map);
    }
}