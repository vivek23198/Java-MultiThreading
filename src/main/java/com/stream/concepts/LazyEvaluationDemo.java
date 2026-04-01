package com.stream.concepts;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/*
============================= LAZY EVALUATION IN STREAMS =============================

1. What is Lazy Evaluation?
   - Intermediate operations (filter, map, etc.) are NOT executed immediately
   - Execution happens ONLY when a terminal operation is invoked

2. Why Lazy?
   - Improves performance
   - Avoids unnecessary computations
   - Processes elements only when required

3. Stream Execution Flow:
   - Define pipeline → NO execution
   - Terminal operation → triggers processing

4. Important Insight:
   - Processing happens element-by-element (not stage-by-stage)

5. Example Flow:
   stream.filter(...).map(...).collect()

   Execution:
   Element1 → filter → map → collect
   Element2 → filter → map → collect

   (NOT: first filter all, then map all)

======================================================================================
*/

public class LazyEvaluationDemo {
    public static void main(String[] args) {

        List<String> names = Arrays.asList("Alice", "Bob", "Charlie", "David");

        // Intermediate operation (LAZY → not executed yet)
        Stream<String> stream = names.stream()
                .filter(name -> {
                    System.out.println("Filtering: " + name);
                    return name.length() > 3;
                });

        // No filtering happens till now
        System.out.println("Before terminal operation");

        // Terminal operation → triggers execution
        List<String> result = stream.collect(Collectors.toList());

        System.out.println("After terminal operation");

        // Final filtered result
        System.out.println(result);
    }
}