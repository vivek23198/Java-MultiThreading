package com.stream.concepts;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

/*
============================= STREAM INTERMEDIATE OPERATIONS =============================

1. What are Intermediate Operations?
   - Operations that transform a stream into another stream
   - They are LAZY → execution happens only when terminal operation is called

2. Key Characteristics:
   - Return a new Stream
   - Do NOT execute immediately
   - Can be chained

3. Common Intermediate Operations:
   - filter()   → condition based filtering
   - map()      → transformation
   - sorted()   → sorting
   - distinct() → remove duplicates
   - limit()    → restrict size
   - skip()     → skip elements
   - peek()     → debugging (side effects)
   - flatMap()  → flatten nested structures

4. Time Complexity:
   - Usually O(n), depends on operation

5. Important Notes:
   - Lazy execution → improves performance
   - Order of operations matters
   - Streams are consumed only once

===========================================================================================
*/

public class IntermediateOps {
    public static void main(String[] args) {

        // ================= 1. FILTER =================

        List<String> list = Arrays.asList("Akshit", "Ram", "Shyam", "Ghanshyam", "Akshit");

        // Creates filtered stream (LAZY → not executed yet)
        Stream<String> filteredStream = list.stream()
                .filter(x -> x.startsWith("A"));

        // Terminal operation → triggers execution
        long res = list.stream()
                .filter(x -> x.startsWith("A"))
                .count();

        System.out.println(res); // count of names starting with 'A'


        // ================= 2. MAP =================

        // Transform each element → uppercase
        Stream<String> stringStream = list.stream()
                .map(String::toUpperCase);


        // ================= 3. SORTED =================

        // Natural sorting (alphabetical)
        Stream<String> sortedStream = list.stream()
                .sorted();

        // Custom sorting (based on length)
        Stream<String> sortedStreamUsingComparator =
                list.stream()
                        .sorted((a, b) -> a.length() - b.length());


        // ================= 4. DISTINCT =================

        // Removes duplicate elements
        System.out.println(
                list.stream()
                        .filter(x -> x.startsWith("A"))
                        .distinct()
                        .count()
        );


        // ================= 5. LIMIT =================

        // Limit infinite stream to first 100 elements
        System.out.println(
                Stream.iterate(1, x -> x + 1)
                        .limit(100)
                        .count()
        );


        // ================= 6. SKIP =================

        // Skip first 10 elements, then take next 100
        System.out.println(
                Stream.iterate(1, x -> x + 1)
                        .skip(10)
                        .limit(100)
                        .count()
        );


        // ================= 7. PEEK =================

        // Used for debugging → performs action while processing
        Stream.iterate(1, x -> x + 1)
                .skip(10)
                .limit(100)
                .peek(System.out::println) // prints elements
                .count();                  // terminal operation


        // ================= 8. FLATMAP =================

        // Nested list example
        List<List<String>> listOfLists = Arrays.asList(
                Arrays.asList("apple", "banana"),
                Arrays.asList("orange", "kiwi"),
                Arrays.asList("pear", "grape")
        );

        // Access specific element
        System.out.println(listOfLists.get(1).get(1)); // kiwi

        // Flatten list of lists → single stream
        System.out.println(
                listOfLists.stream()
                        .flatMap(x -> x.stream()) // flatten
                        .map(String::toUpperCase)
                        .toList()
        );

        // Sentence example → split into words → flatten
        List<String> sentences = Arrays.asList(
                "Hello world",
                "Java streams are powerful",
                "flatMap is useful"
        );

        System.out.println(
                sentences.stream()
                        .flatMap(sentence -> Arrays.stream(sentence.split(" ")))
                        .map(String::toUpperCase)
                        .toList()
        );
    }
}