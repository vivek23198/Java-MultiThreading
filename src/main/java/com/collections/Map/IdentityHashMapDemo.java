package com.collections.Map;

import java.util.IdentityHashMap;
import java.util.Map;

/*
============================= IDENTITYHASHMAP (INTERVIEW REVISION) =============================

1. What is IdentityHashMap?
   - Special Map implementation
   - Uses REFERENCE equality (==)
   - NOT equals()

   👉 Means:
   Two keys are equal ONLY if they point to SAME object in memory.

2. Difference from HashMap:

   HashMap:
   --------
   - Uses equals() + hashCode()
   - Logical equality
   - "Akshit".equals("Akshit") → TRUE

   IdentityHashMap:
   ----------------
   - Uses == (reference comparison)
   - Ignores equals() completely
   - new String("Akshit") == new String("Akshit") → FALSE

3. Internal Working:
   - Uses array (NOT bucket + linked list like HashMap)
   - Stores keys and values in alternating positions:
        [k1, v1, k2, v2, k3, v3...]
   - Open Addressing (linear probing)

4. Use Cases:
   - Object graph processing (serialization frameworks)
   - Detect duplicate object references
   - Caching based on object identity
   - Debugging memory-related issues

5. Important Behavior:
   - Same value but different objects → treated as DIFFERENT keys
   - Same reference → value overwritten

==============================================================================================
*/

public class IdentityHashMapDemo {

    public static void main(String[] args) {

        /*
         * DEMO 1: Different objects, same value
         */

        Map<String, Integer> map = new IdentityHashMap<>();

        String key1 = new String("Akshit");
        String key2 = new String("Akshit");

        // Different memory references
        map.put(key1, 90);
        map.put(key2, 92);

        /*
         * Output will contain BOTH entries
         * because key1 != key2 (reference-wise)
         */
        System.out.println("Different objects: " + map);



        /*
         * DEMO 2: Same reference
         */

        String key3 = key1; // same reference

        map.put(key3, 100); // replaces value of key1

        /*
         * Only one entry updated
         */
        System.out.println("Same reference update: " + map);



        /*
         * DEMO 3: String literal case (important trap)
         */

        Map<String, Integer> map2 = new IdentityHashMap<>();

        String s1 = "Hello";
        String s2 = "Hello";

        /*
         * Due to String pool:
         * s1 == s2 → TRUE
         */
        map2.put(s1, 1);
        map2.put(s2, 2);

        /*
         * Only ONE entry will exist
         */
        System.out.println("String pool case: " + map2);



        /*
         * INTERVIEW TRAPS ⚠️
         * -----------------
         *
         * Q1: Does IdentityHashMap use hashCode()?
         * Ans: Yes, but comparison is done using ==.
         *
         * Q2: Can two equal objects exist as separate keys?
         * Ans: YES (if references differ)
         *
         * Q3: Is it commonly used?
         * Ans: Rare, mostly in internal frameworks.
         *
         * Q4: Is it thread-safe?
         * Ans: No.
         *
         * Q5: Does it maintain order?
         * Ans: No.
         */



        /*
         * TRADEOFFS
         * ---------
         *
         * IdentityHashMap:
         * + Useful for reference-based logic
         * + Avoids equals() overhead
         * - Confusing behavior
         * - Rare real-world use
         *
         * HashMap:
         * + Standard use
         * + Logical equality
         * - Cannot distinguish same-value objects
         */
    }
}