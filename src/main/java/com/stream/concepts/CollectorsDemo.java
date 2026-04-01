package com.stream.concepts;

import java.util.*;
import java.util.stream.Collectors;

/*
============================= COLLECTORS (INTERVIEW REVISION) =============================

1. What is Collectors?
   - A utility class in java.util.stream
   - Provides predefined collectors to transform stream → collection or result

2. Why use Collectors?
   - Convert stream → List, Set, Map
   - Perform grouping, partitioning, aggregation

3. Common Collectors:
   - toList(), toSet(), toMap()
   - groupingBy()
   - partitioningBy()
   - joining()
   - counting(), summing(), averaging()
   - mapping()

4. Key Concepts:
   - groupingBy → multiple groups
   - partitioningBy → only 2 groups (true/false)
   - toMap → key-value mapping (handle duplicates carefully)

5. Time Complexity:
   - Mostly O(n)

===========================================================================================
*/

public class CollectorsDemo {
    public static void main(String[] args) {

        // ================= 1. TO LIST =================

        List<String> names = Arrays.asList("Alice", "Bob", "Charlie");

        List<String> res = names.stream()
                .filter(name -> name.startsWith("A"))
                .collect(Collectors.toList());

        System.out.println(res);


        // ================= 2. TO SET =================

        List<Integer> nums = Arrays.asList(1, 2, 2, 3, 4, 4, 5);

        // Removes duplicates
        Set<Integer> set = nums.stream()
                .collect(Collectors.toSet());

        System.out.println(set);


        // ================= 3. TO SPECIFIC COLLECTION =================

        ArrayDeque<String> collect =
                names.stream()
                        .collect(Collectors.toCollection(() -> new ArrayDeque<>()));


        // ================= 4. JOINING =================

        // Combine elements into a single string
        String concatenatedNames =
                names.stream()
                        .map(String::toUpperCase)
                        .collect(Collectors.joining(", "));

        System.out.println(concatenatedNames);


        // ================= 5. SUMMARIZING =================

        List<Integer> numbers = Arrays.asList(2, 3, 5, 7, 11);

        // Generates statistics
        IntSummaryStatistics stats =
                numbers.stream()
                        .collect(Collectors.summarizingInt(x -> x));

        System.out.println("Count: " + stats.getCount());
        System.out.println("Sum: " + stats.getSum());
        System.out.println("Min: " + stats.getMin());
        System.out.println("Average: " + stats.getAverage());
        System.out.println("Max: " + stats.getMax());


        // ================= 6. AVERAGING =================

        Double average =
                numbers.stream()
                        .collect(Collectors.averagingInt(x -> x));

        System.out.println("Average: " + average);


        // ================= 7. COUNTING =================

        Long count =
                numbers.stream()
                        .collect(Collectors.counting());

        System.out.println("Count: " + count);


        // ================= 8. GROUPING =================

        List<String> words = Arrays.asList("hello", "world", "java", "streams", "collecting");

        // Group by length
        System.out.println(words.stream()
                .collect(Collectors.groupingBy(String::length)));

        // Group and join
        System.out.println(words.stream()
                .collect(Collectors.groupingBy(String::length,
                        Collectors.joining(", "))));

        // Group and count
        System.out.println(words.stream()
                .collect(Collectors.groupingBy(String::length,
                        Collectors.counting())));

        // Custom map (TreeMap)
        TreeMap<Integer, Long> treeMap =
                words.stream()
                        .collect(Collectors.groupingBy(String::length,
                                TreeMap::new,
                                Collectors.counting()));

        System.out.println(treeMap);


        // ================= 9. PARTITIONING =================

        // Splits into 2 groups (true / false)
        System.out.println(words.stream()
                .collect(Collectors.partitioningBy(x -> x.length() > 5)));


        // ================= 10. MAPPING =================

        // Apply transformation before collecting
        System.out.println(words.stream()
                .collect(Collectors.mapping(x -> x.toUpperCase(),
                        Collectors.toList())));


        // ================= REAL EXAMPLES =================

        // Group names by length
        List<String> l1 = Arrays.asList("Anna", "Bob", "Alexander", "Brian", "Alice");
        System.out.println(l1.stream()
                .collect(Collectors.groupingBy(String::length)));

        // Word frequency
        String sentence = "hello world hello java world";
        System.out.println(Arrays.stream(sentence.split(" "))
                .collect(Collectors.groupingBy(x -> x,
                        Collectors.counting())));

        // Partition even/odd
        List<Integer> l2 = Arrays.asList(1, 2, 3, 4, 5, 6);
        System.out.println(l2.stream()
                .collect(Collectors.partitioningBy(x -> x % 2 == 0)));

        // Sum values
        Map<String, Integer> items = new HashMap<>();
        items.put("Apple", 10);
        items.put("Banana", 20);
        items.put("Orange", 15);

        System.out.println(items.values().stream().reduce(Integer::sum));
        System.out.println(items.values().stream()
                .collect(Collectors.summingInt(x -> x)));


        // ================= 11. TO MAP =================

        List<String> fruits = Arrays.asList("Apple", "Banana", "Cherry");

        // key = uppercase, value = length
        System.out.println(fruits.stream()
                .collect(Collectors.toMap(x -> x.toUpperCase(),
                        x -> x.length())));

        // Handle duplicates (VERY IMPORTANT 🔥)
        List<String> words2 = Arrays.asList("apple", "banana", "apple", "orange", "banana", "apple");

        System.out.println(words2.stream()
                .collect(Collectors.toMap(
                        k -> k,        // key
                        v -> 1,        // initial value
                        (x, y) -> x + y // merge function
                )));
    }
}