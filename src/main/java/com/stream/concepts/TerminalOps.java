package com.stream.concepts;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/*
============================= STREAM TERMINAL OPERATIONS =============================

1. What are Terminal Operations?
   - Operations that PRODUCE a result or side-effect
   - They trigger execution of the stream pipeline
   - After terminal operation → stream is CLOSED (cannot reuse)

2. Key Characteristics:
   - Return non-stream result (value, Optional, collection, etc.)
   - Execute entire pipeline
   - Only ONE terminal operation per stream

3. Common Terminal Operations:
   - collect() → convert to collection
   - forEach() → iterate
   - reduce() → aggregate
   - count() → count elements
   - anyMatch(), allMatch(), noneMatch()
   - findFirst(), findAny()
   - toArray()
   - min(), max()

4. Important Concepts:
   - Streams cannot be reused after terminal operation
   - Optional is used to handle null safely
   - Parallel streams may not preserve order (use forEachOrdered)

=======================================================================================
*/

public class TerminalOps {
    public static void main(String[] args) {

        List<Integer> list = Arrays.asList(1, 2, 3);

        // ================= 1. COLLECT =================

        // Collect elements into a new List
        list.stream().skip(1).collect(Collectors.toList());

        // Java 16+ shortcut
        list.stream().skip(1).toList();


        // ================= 2. FOREACH =================

        // Iterates over elements (terminal operation)
        list.stream().forEach(x -> System.out.println(x));


        // ================= 3. REDUCE =================

        // Combines elements into a single result
        Optional<Integer> optionalInteger = list.stream().reduce(Integer::sum);

        // Optional used to avoid null
        System.out.println(optionalInteger.get()); // 6


        // ================= 4. COUNT =================

        // Counts number of elements (not explicitly shown above)
        long count = list.stream().count();


        // ================= 5. MATCH OPERATIONS =================

        // anyMatch → at least one element matches
        boolean b = list.stream().anyMatch(x -> x % 2 == 0);
        System.out.println(b);

        // allMatch → all elements satisfy condition
        boolean b1 = list.stream().allMatch(x -> x > 0);
        System.out.println(b1);

        // noneMatch → no element satisfies condition
        boolean b2 = list.stream().noneMatch(x -> x < 0);
        System.out.println(b2);


        // ================= 6. FIND =================

        // findFirst → returns first element (ordered)
        System.out.println(list.stream().findFirst().get());

        // findAny → may return any element (useful in parallel streams)
        System.out.println(list.stream().findAny().get());


        // ================= 7. TO ARRAY =================

        Object[] array = Stream.of(1, 2, 3).toArray();


        // ================= 8. MIN / MAX =================

        System.out.println("max: " +
                Stream.of(2, 44, 69).max((o1, o2) -> o2 - o1));

        System.out.println("min: " +
                Stream.of(2, 44, 69).min(Comparator.naturalOrder()));


        // ================= 9. FOREACH vs FOREACHORDERED =================

        List<Integer> numbers0 = Arrays.asList(1,2,3,4,5,6,7,8,9,10);

        System.out.println("Using forEach with parallel stream:");
        numbers0.parallelStream().forEach(System.out::println); // order NOT guaranteed

        System.out.println("Using forEachOrdered with parallel stream:");
        numbers0.parallelStream().forEachOrdered(System.out::println); // order maintained


        // ================= REAL EXAMPLES =================

        // Filtering and collecting
        List<String> names = Arrays.asList("Anna", "Bob", "Charlie", "David");
        System.out.println(
                names.stream()
                        .filter(x -> x.length() > 3)
                        .toList()
        );

        // Squaring and sorting
        List<Integer> numbers = Arrays.asList(5, 2, 9, 1, 6);
        System.out.println(
                numbers.stream()
                        .map(x -> x * x)
                        .sorted()
                        .toList()
        );

        // Summing values
        List<Integer> integers = Arrays.asList(1, 2, 3, 4, 5);
        System.out.println(
                integers.stream()
                        .reduce(Integer::sum)
                        .get()
        );

        // Counting occurrences of a character
        String sentence = "Hello world";
        System.out.println(
                sentence.chars()
                        .filter(x -> x == 'l')
                        .count()
        );


        // ================= IMPORTANT: STREAM REUSE =================

        Stream<String> stream = names.stream();

        // First terminal operation → OK
        stream.forEach(System.out::println);

        // Second operation → ERROR (stream already consumed)
        // stream.map(String::toUpperCase).toList(); // IllegalStateException


        // ================= STATELESS vs STATEFUL =================

        // Stateless → operations like map(), filter()
        // Stateful → operations like sorted(), distinct()
    }
}