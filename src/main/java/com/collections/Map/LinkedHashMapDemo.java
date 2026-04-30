package com.collections.Map;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/*
============================= LINKEDHASHMAP (INTERVIEW REVISION) =============================

1. What is LinkedHashMap?
   - HashMap + Doubly Linked List
   - Maintains predictable iteration order

2. Types of Ordering:
   a) Insertion Order (default)
      → Order in which elements were added

   b) Access Order (accessOrder = true)
      → Recently accessed elements move to END
      → Used in LRU Cache

3. Internal Structure:
   - Same as HashMap (array + bucket)
   - PLUS doubly linked list pointers:
        before ← node → after

4. Time Complexity:
   - put() → O(1)
   - get() → O(1)
   - remove() → O(1)
   - iteration → O(n)

5. Space Complexity:
   - O(n) + extra memory for before/after pointers

6. Key Use Cases:
   - LRU Cache
   - Maintain insertion/access order

7. Difference from HashMap:
   - HashMap → no order
   - LinkedHashMap → ordered

==============================================================================================
*/

public class LinkedHashMapDemo {

    public static void main(String[] args) {

        /*
         * CREATION
         * --------
         * capacity = 11
         * loadFactor = 0.3
         * accessOrder = true → LRU behavior
         */
        LinkedHashMap<String, Integer> linkedHashMap =
                new LinkedHashMap<>(11, 0.3f, true);


        /*
         * INSERTION
         */
        linkedHashMap.put("Orange", 10);
        linkedHashMap.put("Apple", 20);
        linkedHashMap.put("Guava", 13);


        /*
         * ACCESS ORDER DEMO
         * -----------------
         * Accessing elements moves them to END
         */
        linkedHashMap.get("Apple");
        linkedHashMap.get("Orange");
        linkedHashMap.get("Guava");
        linkedHashMap.get("Apple");
        linkedHashMap.get("Orange");
        linkedHashMap.get("Apple");
        linkedHashMap.get("Guava");


        /*
         * ITERATION → shows ACCESS ORDER
         */
        for (Map.Entry<String, Integer> entry : linkedHashMap.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }



        /*
         * HASHMAP COMPARISON
         */
        HashMap<String, Integer> hashMap = new HashMap<>();

        hashMap.put("Shubham", 91);
        hashMap.put("Bob", 80);
        hashMap.put("Akshit", 78);


        /*
         * Creating LinkedHashMap from HashMap
         *
         * ⚠️ Order depends on HashMap iteration
         * → NOT guaranteed
         */
        LinkedHashMap<String, Integer> linkedHashMap1 =
                new LinkedHashMap<>(hashMap);

        System.out.println("LinkedHashMap Creation .....");

        for (Map.Entry<String, Integer> entry : linkedHashMap1.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }



        /*
         * getOrDefault()
         */
        Integer res = hashMap.getOrDefault("Vipul", 0);
        System.out.println("Default value: " + res);



        /*
         * UPDATE
         */
        hashMap.put("Shubham", 92);

        System.out.println(hashMap);



        /*
         * 🔥 LRU CACHE IMPLEMENTATION (IMPORTANT)
         * ---------------------------------------
         * Override removeEldestEntry()
         */

        LinkedHashMap<Integer, String> lruCache =
                new LinkedHashMap<>(3, 0.75f, true) {

                    protected boolean removeEldestEntry(Map.Entry<Integer, String> eldest) {
                        return size() > 3; // max size = 3
                    }
                };

        lruCache.put(1, "A");
        lruCache.put(2, "B");
        lruCache.put(3, "C");

        lruCache.get(1); // access → moves to end

        lruCache.put(4, "D"); // removes least recently used (key=2)

        System.out.println("LRU Cache: " + lruCache);



        /*
         * INTERVIEW TRAPS ⚠️
         * -----------------
         *
         * Q1: Is LinkedHashMap slower than HashMap?
         * Ans: Slightly (due to extra pointers)
         *
         * Q2: Does it maintain sorted order?
         * Ans: No (use TreeMap for sorting)
         *
         * Q3: Difference between insertion and access order?
         * Ans:
         * insertion → fixed order
         * access → dynamic (LRU)
         *
         * Q4: Can it be used for cache?
         * Ans: Yes (LRU implementation)
         */
    }
}