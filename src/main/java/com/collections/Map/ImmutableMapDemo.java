package com.collections.Map;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ImmutableMapDemo {

    public static void main(String[] args) {

        /*
         * IMMUTABLE MAP CONCEPT (Interview Revision)
         * ------------------------------------------
         * Immutable → Cannot be modified after creation
         * (no put, remove, update)
         */


        /*
         * 1. NORMAL HASHMAP (MUTABLE)
         */
        Map<String, Integer> map1 = new HashMap<>();

        map1.put("A", 1);
        map1.put("B", 2);



        /*
         * 2. Collections.unmodifiableMap()
         * --------------------------------
         *
         * - Returns READ-ONLY VIEW (wrapper)
         * - Underlying map is still mutable
         * - Changes in original map reflect here
         * - Modification through wrapper → Exception
         */

        Map<String, Integer> map2 = Collections.unmodifiableMap(map1);

        System.out.println("Unmodifiable view: " + map2);

        // map2.put("C", 3); ❌ UnsupportedOperationException


        /*
         * IMPORTANT TRAP ⚠️
         * ----------------
         * Changes in original map affect unmodifiable map
         */

        map1.put("C", 3);

        System.out.println("After modifying original: " + map2);



        /*
         * 3. Map.of() (Java 9+)
         * ---------------------
         *
         * - Truly IMMUTABLE
         * - No add/remove/update allowed
         * - No null key/value allowed
         * - Max 10 entries
         */

        Map<String, Integer> map3 =
                Map.of("Shubham", 98, "Vivek", 89);

        // map3.put("Akshit", 88); ❌ Exception

        System.out.println("Map.of(): " + map3);



        /*
         * 4. Map.ofEntries()
         * ------------------
         *
         * - For more than 10 entries
         * - Also immutable
         */

        Map<String, Integer> map4 = Map.ofEntries(
                Map.entry("Akshit", 99),
                Map.entry("Vivek", 99)
        );

        System.out.println("Map.ofEntries(): " + map4);



        /*
         * IMMUTABLE vs UNMODIFIABLE
         * -------------------------
         *
         * UnmodifiableMap:
         * + Wrapper around existing map
         * + Underlying map can still change
         * - Not truly immutable
         *
         * Map.of():
         * + Completely immutable
         * + No internal change possible
         * - Fixed size
         */


        /*
         * INTERVIEW TRAPS ⚠️
         * -----------------
         *
         * Q1: Is unmodifiableMap immutable?
         * Ans: No (it's just a wrapper)
         *
         * Q2: Which is truly immutable?
         * Ans: Map.of(), Map.ofEntries()
         *
         * Q3: Can Map.of accept null?
         * Ans: No → throws NullPointerException
         *
         * Q4: Difference between defensive copy vs wrapper?
         * Ans:
         * Wrapper → shared reference
         * Copy → separate object
         */


        /*
         * BEST PRACTICE
         * -------------
         *
         * For read-only config:
         * → Use Map.of()
         *
         * For exposing internal map safely:
         * → Use Collections.unmodifiableMap()
         */
    }
}