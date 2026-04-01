package com.stream.concepts;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

/*
============================= JAVA STREAMS (INTERVIEW REVISION) =============================

1. What is Stream?
   - A sequence of elements that supports functional & declarative processing
   - Does NOT store data (works on source like collection/array)

2. Stream Pipeline:
   Source → Intermediate Operations → Terminal Operation

   Example:
   list.stream() → filter() → map() → collect()

3. Types of Operations:
   a) Intermediate (LAZY)
      - filter(), map(), sorted(), distinct()
      - Returns Stream
      - Executed only when terminal operation is called

   b) Terminal (EAGER)
      - forEach(), collect(), count(), reduce()
      - Produces result → triggers execution

4. Key Features:
   - Functional programming style
   - Internal iteration
   - Can be parallelized (parallelStream())

5. Time Complexity:
   - Usually O(n) depending on operations

6. Important Points:
   - Streams are NOT reusable
   - They do NOT modify original data
   - Lazy evaluation improves performance

==============================================================================================
*/

public class StreamDemo {
    public static void main(String[] args) {

        // ================= BASIC STREAM EXAMPLE =================

        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);

        // filter → intermediate (lazy)
        // count → terminal (triggers execution)
        // Count even numbers
        System.out.println(
                numbers.stream()
                        .filter(x -> x % 2 == 0) // keeps only even numbers
                        .count()                 // counts elements
        );


        // ================= CREATING STREAMS =================

        // 1. From collections
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5);
        Stream<Integer> stream = list.stream();

        // 2. From arrays
        String[] array = {"a", "b", "c"};
        Stream<String> stream1 = Arrays.stream(array);

        // 3. Using Stream.of()
        Stream<String> stream2 = Stream.of("a", "b");


        // ================= INFINITE STREAMS =================

        // Generates infinite stream of 1s
        Stream<Integer> infiniteStream1 = Stream.generate(() -> 1);

        // Generates sequence: 1, 2, 3, 4...
        Stream<Integer> infiniteStream2 = Stream.iterate(1, x -> x + 1);

        // IMPORTANT: Always limit infinite streams before terminal operation
        infiniteStream2.limit(5).forEach(System.out::println);
    }
}