package com.java8Features;

import java.util.Arrays;
import java.util.List;
import java.util.function.*;
import java.util.stream.Collectors;

/*
============================= JAVA 8 FEATURES (INTERVIEW REVISION) =============================

1. Key Features:
   - Lambda Expressions → concise anonymous functions
   - Functional Interfaces → single abstract method
   - Streams API → functional data processing
   - Method Reference → cleaner lambda
   - Default & Static methods in interfaces
   - New Date & Time API

2. Functional Interfaces:
   - Predicate<T>  → takes input, returns boolean
   - Function<T,R> → takes input, returns output
   - Consumer<T>   → takes input, returns nothing
   - Supplier<T>   → takes no input, returns value

3. Additional Interfaces:
   - BiPredicate<T,U>
   - BiFunction<T,U,R>
   - BiConsumer<T,U>
   - UnaryOperator<T> → Function<T,T>
   - BinaryOperator<T> → BiFunction<T,T,T>

4. Important Concepts:
   - Lambda → replaces anonymous classes
   - Method Reference → cleaner syntax (::)
   - Streams → filter, map, reduce operations

5. Time Complexity:
   - Depends on operation (usually O(n) for streams)

==============================================================================================
*/

public class Java8Demo {
    public static void main(String[] args) {

        // ================= LAMBDA EXPRESSION =================

        // Lambda = anonymous function (no name, no return type, no access modifier)
        Thread t1 = new Thread(() -> {
            System.out.println("Hello");
        });

        // Custom Functional Interface implementation using lambda
        MathOperation sumOperation = (a, b) -> a + b;
        MathOperation subtractOperation = (a, b) -> a - b;

        int res = sumOperation.operate(1, 2); // 1 + 2 = 3
        System.out.println(res);


        // ================= PREDICATE =================

        // Predicate → returns boolean
        Predicate<Integer> isEven = x -> x % 2 == 0;
        System.out.println(isEven.test(4)); // true

        Predicate<String> isWordStartingWithA = x -> x.toLowerCase().startsWith("a");
        Predicate<String> isWordEndingWithT = x -> x.toLowerCase().endsWith("t");

        // Combining predicates
        Predicate<String> and = isWordStartingWithA.and(isWordEndingWithT);
        System.out.println(and.test("Akshay")); // false


        // ================= FUNCTION =================

        Function<Integer, Integer> doubleIt = x -> 2 * x;
        Function<Integer, Integer> tripleIt = x -> 3 * x;

        // andThen → first double, then triple
        System.out.println(doubleIt.andThen(tripleIt).apply(20)); // (20*2)*3 = 120

        // Order matters
        System.out.println(tripleIt.andThen(doubleIt).apply(20)); // (20*3)*2 = 120

        // compose → reverse order
        System.out.println(doubleIt.compose(tripleIt).apply(20)); // same as above

        System.out.println(doubleIt.apply(100)); // 200

        // identity → returns input as it is
        Function<Integer, Integer> identity = Function.identity();
        Integer res2 = identity.apply(5);
        System.out.println(res2);


        // ================= CONSUMER =================

        // Consumer → takes input, returns nothing
        Consumer<Integer> print = x -> System.out.println(x);
        print.accept(51);

        List<Integer> list = Arrays.asList(1, 2, 3);

        // Consumer for list
        Consumer<List<Integer>> printList = x -> {
            for (int i : x) {
                System.out.println(i);
            }
        };
        printList.accept(list);


        // ================= SUPPLIER =================

        // Supplier → no input, returns value
        Supplier<String> giveHelloWorld = () -> "Hello World";
        System.out.println(giveHelloWorld.get());


        // ================= COMBINED EXAMPLE =================

        Predicate<Integer> predicate = x -> x % 2 == 0;
        Function<Integer, Integer> function = x -> x * x;
        Consumer<Integer> consumer = x -> System.out.println(x);
        Supplier<Integer> supplier = () -> 100;

        // Flow: get value → check → transform → consume
        if (predicate.test(supplier.get())) {
            consumer.accept(function.apply(supplier.get())); // 100*100 = 10000
        }


        // ================= BI FUNCTIONAL INTERFACES =================

        BiPredicate<Integer, Integer> isSumEven = (x, y) -> (x + y) % 2 == 0;
        System.out.println(isSumEven.test(5, 5)); // true

        BiConsumer<Integer, String> biConsumer = (x, y) -> {
            System.out.println(x);
            System.out.println(y);
        };

        BiFunction<String, String, Integer> biFunction = (x, y) -> (x + y).length();
        System.out.println(biFunction.apply("a", "bc")); // 3


        // ================= OPERATORS =================

        UnaryOperator<Integer> a = x -> 2 * x; // same type input/output
        BinaryOperator<Integer> b = (x, y) -> x + y;


        // ================= METHOD REFERENCE =================

        List<String> students = Arrays.asList("Ram", "Shyam", "Ghanshyam");

        // Using lambda
        students.forEach(x -> System.out.println(x));

        // Using method reference (cleaner)
        students.forEach(System.out::println);


        // ================= CONSTRUCTOR REFERENCE =================

        List<String> names = Arrays.asList("A", "B", "C");

        // Creating objects using constructor reference
        List<MobilePhone> mobilePhoneList =
                names.stream()
                        .map(MobilePhone::new)
                        .collect(Collectors.toList());
    }
}


// Simple class used in constructor reference
class MobilePhone {
    String name;

    public MobilePhone(String name) {
        this.name = name;
    }
}


// Custom Functional Interface
@FunctionalInterface
interface MathOperation {
    int operate(int a, int b);
}