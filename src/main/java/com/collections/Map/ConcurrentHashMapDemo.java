package com.collections.Map;

import java.util.concurrent.ConcurrentHashMap;

/*
============================= CONCURRENTHASHMAP (INTERVIEW REVISION) =============================

1. What is ConcurrentHashMap?
   - A thread-safe version of HashMap
   - Designed for high concurrency

2. Java 7 Implementation:
   - Segment-based locking
   - Default 16 segments → each segment is a mini HashMap
   - Only one segment locked at a time → better concurrency than Hashtable

3. Java 8 Implementation:
   - Removed segmentation ❌
   - Uses:
        ✔ CAS (Compare-And-Swap)
        ✔ Synchronized blocks (only when needed)
        ✔ Node-level locking

4. Compare-And-Swap (CAS):
   - Atomic operation
   - Example:
        Thread A sees value = 45
        Thread A tries to update to 50
        If still 45 → update
        Else → retry

5. Locking Behavior:
   - Reads → NO lock (mostly)
   - Writes → minimal locking (bucket-level)

6. Performance:
   - Much faster than Hashtable / synchronizedMap
   - High throughput in multi-threading

7. Differences from HashMap:
   - Thread-safe
   - No null keys/values allowed

8. Time Complexity:
   - get() → O(1)
   - put() → O(1)

9. Related:
   - ConcurrentSkipListMap → sorted + thread-safe (log n)

==============================================================================================
*/

public class ConcurrentHashMapDemo {
    public static void main(String[] args) {

        // Thread-safe map
        ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>();

        // ================= BASIC OPERATIONS =================

        map.put("A", 1);
        map.put("B", 2);

        // get() is thread-safe without full locking
        System.out.println(map.get("A"));

        // ================= IMPORTANT NOTE =================

        // ❌ Does NOT allow null key or value
        // map.put(null, 1); → NullPointerException

        // ================= ATOMIC OPERATIONS =================

        // putIfAbsent → avoids race condition
        map.putIfAbsent("A", 100); // will NOT overwrite existing value

        // compute → atomic update
        map.compute("A", (k, v) -> v + 10);

        // merge → combine values safely
        map.merge("B", 5, Integer::sum);

        System.out.println(map);

        // ================= MULTI-THREAD SAFE =================

        // Safe for concurrent reads and writes
        // No need for external synchronization

        // ================= IMPORTANT =================

        // MAP --> SORTED + THREAD SAFE
        // Use: ConcurrentSkipListMap (O(log n))
    }
}