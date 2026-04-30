package com.collections.Map;

import java.util.Map;
import java.util.NavigableMap;
import java.util.SortedMap;
import java.util.TreeMap;

public class TreeMapRangeExample {

    public static void main(String[] args) {

        /*
         * TREE MAP NOTES (Interview Revision)
         * ----------------------------------
         * 1. TreeMap stores data in SORTED order based on keys.
         * 2. Internally uses Red-Black Tree (Self Balancing BST).
         * 3. Keys are unique.
         * 4. Values can be duplicate.
         * 5. Null Key NOT allowed.
         * 6. Null Values allowed.
         * 7. Time Complexity:
         *      put()    -> O(log n)
         *      get()    -> O(log n)
         *      remove() -> O(log n)
         * 8. Best when sorting / range queries / nearest key search needed.
         */

        // Key = Employee ID
        // Value = Employee Name
        TreeMap<Integer, String> employeeMap = new TreeMap<>();

        /*
         * put() inserts in sorted order automatically
         * Even if inserted randomly, output remains sorted by key
         */
        employeeMap.put(1001, "Amit");
        employeeMap.put(1005, "Sushila");
        employeeMap.put(1010, "Vivek");
        employeeMap.put(1015, "Rohan");
        employeeMap.put(1020, "Priya");
        employeeMap.put(1025, "Aniket");

        // TreeMap always prints sorted by key
        System.out.println("Full Employee List: " + employeeMap);

        /*
         * RANGE QUERY USING subMap(fromKey, toKey)
         *
         * Default Behavior:
         * fromKey = Inclusive
         * toKey   = Exclusive
         *
         * [1010, 1020)
         * Means:
         * Includes 1010
         * Excludes 1020
         */
        SortedMap<Integer, String> rangeMap =
                employeeMap.subMap(1010, 1020);

        System.out.println("\nEmployees in range [1010 to 1020):");

        for (Map.Entry<Integer, String> entry : rangeMap.entrySet()) {
            System.out.println("ID: " + entry.getKey()
                    + ", Name: " + entry.getValue());
        }

        /*
         * INCLUSIVE RANGE QUERY
         *
         * subMap(fromKey, fromInclusive, toKey, toInclusive)
         *
         * Here:
         * 1010 included
         * 1020 included
         *
         * [1010, 1020]
         */
        NavigableMap<Integer, String> inclusiveRange =
                employeeMap.subMap(1010, true, 1020, true);

        System.out.println("\nEmployees in range [1010 to 1020] (Both Inclusive):");

        inclusiveRange.forEach((id, name) ->
                System.out.println("ID: " + id + ", Name: " + name));



        /*
         * EXTRA IMPORTANT TREE MAP METHODS (Interview)
         * -------------------------------------------
         * firstKey()      -> smallest key
         * lastKey()       -> largest key
         * higherKey(k)    -> next greater key
         * lowerKey(k)     -> next smaller key
         * ceilingKey(k)   -> >= k
         * floorKey(k)     -> <= k
         * descendingMap() -> reverse order view
         */



        /*
         * TRADEOFFS : TreeMap vs HashMap
         * ------------------------------
         *
         * HashMap:
         * + Faster average O(1)
         * + Best for simple lookup
         * - No ordering
         * - No range search
         *
         * TreeMap:
         * + Sorted keys
         * + Range queries supported
         * + first/last/higher/lower key operations
         * - Slower than HashMap (O(log n))
         * - More memory due to tree nodes
         *
         * Use TreeMap when:
         * - Need sorted data
         * - Need top/bottom records
         * - Need range queries
         * - Need nearest key search
         *
         * Use HashMap when:
         * - Fast lookup only needed
         */



        /*
         * INTERVIEW QUESTION:
         * Why TreeMap slower than HashMap?
         *
         * Because TreeMap uses Red-Black Tree traversal.
         * HashMap uses hashing + bucket lookup.
         */
    }
}