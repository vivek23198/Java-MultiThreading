package com.collections.Map;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/*
============================= LINKEDHASHMAP (INTERVIEW REVISION) =============================

1. What is LinkedHashMap?
   - It is a HashMap + Doubly Linked List
   - Maintains ORDER of elements

2. Types of Ordering:
   a) Insertion Order (default)
      → elements appear in order they were inserted

   b) Access Order (when accessOrder = true)
      → elements move to end when accessed (LRU behavior)

3. Internal Structure:
   - Array of buckets (same as HashMap)
   - Each entry also has:
        → before pointer
        → after pointer
   - Forms a Doubly Linked List

4. Time Complexity:
   - put() → O(1)
   - get() → O(1)
   - iteration → O(n)

5. Space Complexity:
   - O(n) (extra memory for linked list pointers)

6. Key Use Case:
   - LRU Cache (Least Recently Used)
   - Maintain predictable iteration order

7. Difference from HashMap:
   - HashMap → No order
   - LinkedHashMap → Maintains order

==============================================================================================
*/

public class LinkedHashMapDemo {
    public static void main(String[] args) {

        // Creating LinkedHashMap with:
        // initial capacity = 11
        // load factor = 0.3
        // accessOrder = true → maintains ACCESS ORDER (LRU style)
        LinkedHashMap<String, Integer> linkedHashMap =
                new LinkedHashMap<>(11, 0.3f, true); // uses doubly linked list internally

        // Insertion (insertion order initially)
        linkedHashMap.put("Orange", 10);
        linkedHashMap.put("Apple", 20);
        linkedHashMap.put("Guava", 13);

        // Accessing elements → moves them to END (since accessOrder = true)
        linkedHashMap.get("Apple");
        linkedHashMap.get("Orange");
        linkedHashMap.get("Guava");
        linkedHashMap.get("Apple");
        linkedHashMap.get("Orange");
        linkedHashMap.get("Apple");
        linkedHashMap.get("Guava");

        // Iteration will reflect ACCESS ORDER (recently accessed last)
        for (Map.Entry<String, Integer> entry : linkedHashMap.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }

        // ================= HASHMAP EXAMPLE =================

        // HashMap does NOT maintain order
        HashMap<String, Integer> hashMap = new HashMap<>();
        // Insert values
        hashMap.put("Shubham", 91);
        hashMap.put("Bob", 80);
        hashMap.put("Akshit", 78);

        // Creating LinkedHashMap from existing HashMap
        // Copies entries (order depends on HashMap iteration → unpredictable)
        LinkedHashMap<String, Integer> linkedHashMap1 = new LinkedHashMap<>(hashMap);

        System.out.println("LinkedHashMap Creation .....");
        // LinkedHashMap does not gurantee order bcoz hashmap is not in the order
        for (Map.Entry<String, Integer> entry : linkedHashMap1.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }

        // getOrDefault():
        // If key exists → return value
        // Else → return default value
        Integer res = hashMap.getOrDefault("Vipul", 0); // returns 0

        // Updating existing key → replaces value
        hashMap.put("Shubham", 92);

        // Final map (order NOT guaranteed)
        System.out.println(hashMap);
    }
}