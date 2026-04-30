package com.collections.Set;

import java.util.*;
import java.util.concurrent.ConcurrentSkipListSet;

public class SetOverview {
    public static void main(String[] args) {

        // Set Properties:
        // 1. Does NOT allow duplicate elements
        // 2. Stores unique values only
        // 3. Can have at most one null (depends on implementation)
        // 4. No index-based access (unlike List)
        // 5. Performance depends on implementation (HashSet, TreeSet, etc.)

        // Map vs Set:
        // Map → key-value pairs
        // Set → only keys (internally uses Map)

        // Common Implementations:
        // HashSet → Unordered, fastest (O(1))
        // LinkedHashSet → Maintains insertion order
        // TreeSet → Sorted order (uses Red-Black Tree)
        // EnumSet → For enum types (very fast, bit vector)

        // 🔹 TreeSet (Sorted + NavigableSet)
        NavigableSet<Integer> set = new TreeSet<>();

        set.add(12);
        set.add(1);
        set.add(1);  // Duplicate → ignored
        set.add(67);

        // TreeSet maintains sorted order automatically
        System.out.println(set);  // Output: [1, 12, 67]

        // contains() → checks if element exists (log n for TreeSet)
        System.out.println(set.contains(12));

        // remove() → removes element if present
        System.out.println(set.remove(67));

        // clear() → removes all elements
        set.clear();

        // isEmpty() → checks if set is empty
        System.out.println(set.isEmpty());

        // Iteration over set
        for(int i: set){
            System.out.println(i);
        }

        // 🔹 Thread-safe Set
        // ConcurrentSkipListSet:
        // 1. Thread-safe
        // 2. Sorted (like TreeSet)
        // 3. Uses Skip List internally
        // 4. Allows concurrent access (better than synchronized set)
        Set<Integer> set1 = new ConcurrentSkipListSet<>();

        // 🔹 Immutable Set (Java 9+)
        // Set.of():
        // 1. Creates unmodifiable set
        // 2. Does NOT allow null
        // 3. Throws exception if modification attempted
        Set<Integer> integers = Set.of(1, 2, 3,4,5,6,7,8,9,54,4323,545,4545);

        // integers.add(10); ❌ UnsupportedOperationException
    }
}