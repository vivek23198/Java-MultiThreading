package com.collections.Map;

import java.util.Hashtable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListMap;

public class HashTableDemo {

    public static void main(String[] args) {

        /*
         * HASHTABLE NOTES (Interview Revision)
         * -----------------------------------
         *
         * 1. Thread-safe (all methods are synchronized)
         *    → Uses intrinsic lock (synchronized keyword)
         *
         * 2. Does NOT allow:
         *    - null key
         *    - null value
         *
         * 3. Legacy class (introduced before Java Collections Framework)
         *
         * 4. Performance:
         *    - Slower than HashMap
         *    - Because entire map is locked (coarse-grained locking)
         *
         * 5. Internal structure:
         *    - Array + LinkedList (for collision)
         *    - No tree optimization like HashMap (Java 8)
         *
         * 6. Order:
         *    - No insertion order guarantee
         *
         * 7. Default:
         *    - Initial capacity = 11
         *    - Load factor = 0.75
         *
         * 8. Concurrency limitation:
         *    - Only ONE thread can access at a time
         */


        /*
         * CREATION
         */
        Hashtable<Integer, String> map = new Hashtable<>();


        /*
         * MULTI-THREADING DEMO
         */

        // Thread 1 → inserts 0 to 999
        Thread thread1 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {

                /*
                 * put() is synchronized
                 * Thread-safe but slow due to locking entire map
                 */
                map.put(i, "Thread1");
            }
        });


        // Thread 2 → inserts 1000 to 1999
        Thread thread2 = new Thread(() -> {
            for (int i = 1000; i < 2000; i++) {

                /*
                 * No overlap → no overwrite
                 */
                map.put(i, "Thread2");
            }
        });


        /*
         * START THREADS
         */
        thread1.start();
        thread2.start();


        /*
         * WAIT FOR THREADS TO COMPLETE
         */
        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        /*
         * RESULT
         * Expected = 2000
         */
        System.out.println("Final size of Hashtable: " + map.size());



        /*
         * INTERVIEW TRAPS ⚠️
         * -----------------
         *
         * Q1: Why Hashtable is slow?
         * Ans:
         * Entire map is locked (coarse-grained locking).
         *
         * Q2: Does it allow null?
         * Ans: No (throws NullPointerException).
         *
         * Q3: Is it used today?
         * Ans: Rarely (legacy).
         */



        /*
         * MODERN ALTERNATIVES
         * -------------------
         */

        /*
         * 1. ConcurrentHashMap (MOST IMPORTANT)
         *
         * + Thread-safe
         * + Better performance
         * + Uses segment-level locking / CAS
         * + Multiple threads can read/write simultaneously
         * - Does NOT allow null key/value
         */
        ConcurrentHashMap<Integer, String> concurrentMap = new ConcurrentHashMap<>();


        /*
         * 2. ConcurrentSkipListMap
         *
         * + Thread-safe + Sorted (like TreeMap)
         * + Uses Skip List data structure
         * + O(log n) operations
         */
        ConcurrentSkipListMap<Integer, String> sortedMap =
                new ConcurrentSkipListMap<>();



        /*
         * HASHTABLE vs HASHMAP vs CONCURRENTHASHMAP
         * -----------------------------------------
         *
         * HashMap:
         * + Fast (O(1))
         * - Not thread-safe
         * + Allows 1 null key
         *
         * Hashtable:
         * + Thread-safe
         * - Slow (full synchronization)
         * - No null allowed
         *
         * ConcurrentHashMap:
         * + Thread-safe
         * + High performance
         * + Fine-grained locking
         * - No null allowed
         *
         * 👉 Interview Answer:
         * "Use ConcurrentHashMap instead of Hashtable"
         */
    }
}