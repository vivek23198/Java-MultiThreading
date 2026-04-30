package com.collections.Map;

import java.util.*;

/*
================================ HASHMAP INTERNAL WORKING (INTERVIEW REVISION) ================================

1. Data Structure:
   - HashMap uses an array of Node<K,V> (bucket array).
   - Each bucket can store:
        -> Single Node
        -> Linked List (collision handling - Java 7)
        -> Red-Black Tree (Java 8+, when bucket size > 8 AND capacity >= 64)

2. Hashing Process:
   - Step 1: key.hashCode()
   - Step 2: hash spread (improves distribution)
   - Step 3: index = (n - 1) & hash   (fast modulo)
   - Ensures uniform distribution across buckets.

3. Collision Handling:
   - Multiple keys can map to same index.
   - Stored as:
        -> LinkedList (initially)
        -> Converted to Tree when:
            bucket size > 8 (TREEIFY_THRESHOLD)
            AND capacity >= 64
   - If size reduces → converts back to list.

4. Time Complexity:
   - put()         → O(1) avg, O(log n) (tree), O(n) worst
   - get()         → O(1) avg
   - remove()      → O(1) avg
   - containsKey() → O(1)
   - containsValue() → O(n)
   - iteration     → O(n)

5. Load Factor & Resize:
   - Default load factor = 0.75
   - Resize condition:
        size > capacity * loadFactor
   - New capacity = 2 * old capacity
   - Rehashing happens (costly operation)

6. Important Points:
   - Allows ONE null key (stored at index 0)
   - Allows multiple null values
   - Duplicate key → overwrites value
   - Not thread-safe
   - Order NOT guaranteed

7. Space Complexity:
   - O(n)

8. Internal Node Structure:
   static class Node<K,V> {
       int hash;
       K key;
       V value;
       Node<K,V> next;
   }

===============================================================================================================
*/

public class HashMapDemo {

    public static void main(String[] args) {

        /*
         * INITIALIZATION
         * capacity = 17, loadFactor = 0.5
         * resize when size > 8
         */
        HashMap<Integer, String> map = new HashMap<>(17, 0.5f);


        /*
         * INSERTION (put)
         * --------------
         * Steps:
         * 1. Compute hash
         * 2. Find index
         * 3. If empty → insert
         * 4. If collision → traverse bucket
         * 5. If key exists → replace value
         */
        map.put(31, "Shubham");
        map.put(11, "Akshit");
        map.put(2, "Neha");

        // Duplicate key → value replaced
        map.put(2, "Mehul");

        System.out.println(map);



        /*
         * FETCH (get)
         * ----------
         * Uses hash → index → traverse bucket
         */
        String student = map.get(31); // O(1)
        System.out.println(student);

        // Key not present → returns null
        System.out.println(map.get(69));



        /*
         * containsKey vs containsValue
         */
        System.out.println(map.containsKey(2)); // O(1)
        System.out.println(map.containsValue("Shubham")); // O(n)



        /*
         * ITERATION METHODS
         */

        // ❌ Less efficient (extra lookup)
        for (int key : map.keySet()) {
            System.out.println(map.get(key));
        }

        // ✅ Best practice
        for (Map.Entry<Integer, String> entry : map.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }



        /*
         * MODIFY VALUES DURING ITERATION
         */
        for (Map.Entry<Integer, String> entry : map.entrySet()) {
            entry.setValue(entry.getValue().toUpperCase());
        }

        System.out.println(map);



        /*
         * REMOVE OPERATIONS
         */

        // remove(key)
        // map.remove(31);

        // remove(key, value) → only removes if both match
        boolean res = map.remove(31, "Nitin");
        System.out.println("REMOVED ? :" + res);

        System.out.println(map);



        /*
         * INTERVIEW TRAPS ⚠️
         * -----------------
         *
         * 1. Why equals() important?
         *    - hashCode decides bucket
         *    - equals decides exact match inside bucket
         *
         * 2. If hashCode same but equals different?
         *    → stored in same bucket (collision)
         *
         * 3. If both same?
         *    → value replaced
         *
         * 4. Why power of 2 capacity?
         *    → (n-1) & hash is faster than modulo
         *
         * 5. When tree conversion happens?
         *    → size > 8 AND capacity >= 64
         *
         * 6. Fail-Fast behavior:
         *    - Iterator throws ConcurrentModificationException
         */



        /*
         * HASHMAP vs HASHTABLE vs CONCURRENTHASHMAP
         * ----------------------------------------
         *
         * HashMap:
         * + Fast
         * - Not thread-safe
         *
         * Hashtable:
         * + Thread-safe (synchronized)
         * - Slow (entire map locked)
         * - Legacy
         *
         * ConcurrentHashMap:
         * + Thread-safe
         * + Better performance (segment locking / CAS)
         * - No null key/value allowed
         */



        /*
         * ARRAY LIST vs HASHMAP LOOKUP
         */
        List<Integer> list = Arrays.asList(2, 4, 32, 43, 4, 432);

        // O(n)
        list.contains(32);

        /*
         * KEY TAKEAWAY:
         * HashMap lookup → O(1)
         * List lookup    → O(n)
         */
    }
}