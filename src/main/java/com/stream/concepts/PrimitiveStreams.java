package com.stream.concepts;

import java.util.Arrays;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;

/*
============================= PRIMITIVE STREAMS (INTERVIEW REVISION) =============================

1. What are Primitive Streams?
   - Specialized streams for primitive types:
        ✔ IntStream
        ✔ LongStream
        ✔ DoubleStream

2. Why use Primitive Streams?
   - Avoid boxing/unboxing overhead
   - Improve performance (memory + speed)
   - Provide extra utility methods like sum(), average()

3. Boxing vs Unboxing:
   - Boxing → primitive → wrapper (int → Integer)
   - Unboxing → wrapper → primitive

4. Common Methods:
   - range(), rangeClosed()
   - sum(), min(), max(), average()
   - mapToInt(), mapToDouble()
   - boxed() → convert to Stream<Integer>

5. Time Complexity:
   - O(n)

==============================================================================================
*/

public class PrimitiveStreams {
    public static void main(String[] args) {

        // ================= ARRAY TO INTSTREAM =================

        int[] numbers = {1, 2, 3, 4, 5};

        // Convert array → IntStream
        IntStream stream = Arrays.stream(numbers);


        // ================= RANGE =================

        // range(start, end) → excludes end
        System.out.println(
                IntStream.range(1, 5)
                        .boxed() // convert to Integer
                        .collect(Collectors.toList())
        ); // [1, 2, 3, 4]

        // rangeClosed(start, end) → includes end
        System.out.println(
                IntStream.rangeClosed(1, 5)
                        .boxed()
                        .collect(Collectors.toList())
        ); // [1, 2, 3, 4, 5]


        // ================= CREATING STREAM =================

        IntStream.of(1, 2, 3); // simple creation


        // ================= DOUBLE STREAM =================

        // Generate 5 random double values
        DoubleStream doubles = new Random().doubles(5);

        // Convert to boxed (Double) and collect
        System.out.println(doubles.boxed().toList());

        // Other useful operations (commented)
        // doubles.sum();
        // doubles.min();
        // doubles.max();
        // doubles.average();
        // doubles.summaryStatistics();


        // ================= INT STREAM =================

        // Generate 5 random integers
        IntStream intStream = new Random().ints(5);

        System.out.println(intStream.boxed().toList());
    }
}