package com.collections.Map;

import java.util.*;

/*
================================ HASHMAP INTERNAL WORKING (INTERVIEW REVISION) ================================

1. Data Structure:
   - HashMap uses an array of Node<K,V> (called buckets).
   - Each bucket can store:
        -> Single node OR
        -> Linked List (before Java 8)
        -> Red-Black Tree (after Java 8, if collisions > 8)

2. Hashing:
   - Key → hashCode() → index (using (n-1) & hash)
   - This determines where the key-value pair is stored.

3. Collision Handling:
   - If two keys map to same index → collision occurs.
   - Handled using:
        -> LinkedList (Java 7)
        -> Tree (Java 8, improves performance)

4. Time Complexity:
   - put()      → O(1) average, O(log n) worst (tree), O(n) worst-case (bad hash)
   - get()      → O(1) average
   - remove()   → O(1) average
   - containsKey() → O(1)
   - iteration  → O(n)

5. Load Factor & Capacity:
   - Default load factor = 0.75
   - Resize happens when: size > capacity * loadFactor
   - Rehashing doubles capacity (n → 2n)

6. Important Points:
   - Duplicate keys → value gets replaced
   - Allows one null key and multiple null values
   - Not thread-safe (use ConcurrentHashMap in multithreading)
   - Order is NOT guaranteed

7. Space Complexity:
   - O(n) for storing n elements

===============================================================================================================
*/

public class HashMapDemo {
    public static void main(String[] args) {

        // Creating HashMap with initial capacity = 17 and load factor = 0.5
        // Resize will happen when size > 17 * 0.5 = 8
        HashMap<Integer, String> map = new HashMap<>(17, 0.5f);

        // Inserting key-value pairs
        // Time Complexity: O(1) average
        map.put(31, "Shubham");
        map.put(11, "Akshit");
        map.put(2, "Neha");

        // Duplicate key → value gets replaced
        // Key '2' already exists, so "Neha" will be replaced with "Mehul"
        map.put(2, "Mehul");

        // Printing entire map (order not guaranteed)
        System.out.println(map);

        // Fetch value using key
        // Returns value if key exists, else null
        String student = map.get(31); // O(1)
        System.out.println(student);

        // Key does not exist → returns null
        String s = map.get(69); // O(1)
        System.out.println(s);

        // Check if key exists
        System.out.println(map.containsKey(2)); // O(1)

        // Check if value exists (requires full traversal)
        System.out.println(map.containsValue("Shubham")); // O(n)

        // Iterating over keys
        // keySet() returns Set of keys
        for (int i : map.keySet()) {
            // For each key, we fetch value → O(1)
            System.out.println(map.get(i));
        }

        // entrySet() gives Set of key-value pairs
        Set<Map.Entry<Integer, String>> entries = map.entrySet();

        // Iterating and modifying values
        // Updating value to uppercase
        for (Map.Entry<Integer, String> entry : entries) {
            entry.setValue(entry.getValue().toUpperCase()); // O(1)
        }

        // Updated map after modification
        System.out.println(map);

        // Remove using key
        // map.remove(31);

        // Remove using key + value
        // Removes only if BOTH match
        boolean res = map.remove(31, "Nitin"); // O(1)
        System.out.println("REMOVED ? :" + res);

        // Final map
        System.out.println(map);

        // Creating list using Arrays.asList()
        List<Integer> list = Arrays.asList(2, 4, 32, 43, 4, 432);

        // Check if element exists in list
        // Time Complexity: O(n)
        list.contains(32);
    }
}