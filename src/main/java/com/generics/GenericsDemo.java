package com.generics;

import java.util.*;

/*
================================= GENERICS (INTERVIEW REVISION) =================================

Definition:
------------
Generics allow us to write flexible, reusable, and type-safe code by using type parameters (like <T>).

👉 Instead of writing separate classes for Integer, String, etc.,
   we write ONE generic class and reuse it.

Benefits:
----------
+ Type safety (compile-time checking)
+ No explicit casting needed
+ Code reusability

==============================================================================================
*/

public class GenericsDemo {

    public static void main(String[] args) {

        /*
         * ================= 1. GENERIC CLASS =================
         */

        // Here T becomes Integer
        Box<Integer> intBox = new Box<>();
        intBox.set(100);
        System.out.println("Integer Box: " + intBox.get());

        // Here T becomes String
        Box<String> strBox = new Box<>();
        strBox.set("Java Generics");
        System.out.println("String Box: " + strBox.get());


        /*
         * ================= 2. GENERIC METHOD =================
         */

        Integer[] numbers = {1, 2, 3};
        printArray(numbers);

        String[] names = {"A", "B", "C"};
        printArray(names);


        /*
         * ================= 3. BOUNDED TYPES =================
         * Only allow Number or its subclasses
         */

        List<Integer> intList = Arrays.asList(1, 2, 3);
        System.out.println("Sum: " + sum(intList));


        /*
         * ================= 4. WILDCARDS =================
         */

        List<String> list = Arrays.asList("A", "B", "C");
        printList(list);

        /*
         * Upper Bound (? extends)
         * -----------------------
         * Used when we only READ data
         */
        List<? extends Number> nums = Arrays.asList(1, 2, 3);

        // nums.add(10); ❌ NOT allowed
        // Because compiler doesn't know exact type


        /*
         * Lower Bound (? super)
         * ---------------------
         * Used when we WRITE data
         */
        List<? super Integer> superList = new ArrayList<>();
        superList.add(10); // ✅ allowed


        /*
         * ================= 5. TYPE ERASURE =================
         */

        List<String> list1 = new ArrayList<>();
        List<Integer> list2 = new ArrayList<>();

        // At runtime, both are same
        System.out.println("Same class at runtime: " +
                (list1.getClass() == list2.getClass())); // true
    }


    /*
     * ================= GENERIC METHOD =================
     * Works with ANY type
     */
    public static <T> void printArray(T[] array) {
        for (T element : array) {
            System.out.println("Element: " + element);
        }
    }


    /*
     * ================= BOUNDED GENERIC =================
     * Only accepts Number or subclass
     */
    public static double sum(List<? extends Number> list) {
        double sum = 0;

        for (Number n : list) {
            sum += n.doubleValue();
        }

        return sum;
    }


    /*
     * ================= WILDCARD METHOD =================
     * Accepts ANY type
     */
    public static void printList(List<?> list) {
        for (Object obj : list) {
            System.out.println("List item: " + obj);
        }
    }
}


/*
============================= GENERIC CLASS =============================

T → Type parameter (placeholder)

At runtime:
- Box<Integer> → Integer
- Box<String> → String

*/
class Box<T> {

    private T value;

    public void set(T value) {
        this.value = value;
    }

    public T get() {
        return value;
    }
}


/*
================================= KEY CONCEPTS =================================

1. Generic Class:
   class Box<T>

2. Generic Method:
   <T> void method(T value)

3. Bounded Types:
   <T extends Number>

4. Wildcards:
   <?> → unknown
   <? extends T> → read only
   <? super T> → write

5. Type Erasure:
   Generics removed at runtime

================================================================================
*/


/*
================================= INTERVIEW QUESTIONS =================================

Q1: Why Generics?
Ans: Type safety + reusable code

Q2: Can generics use primitives?
Ans: ❌ No → use wrapper classes (Integer, Double)

Q3: What is PECS rule?
Ans:
Producer → extends
Consumer → super

Q4: What is Type Erasure?
Ans:
Generics exist only at compile time

================================================================================
*/


/*
================================= ONE-LINER =================================

Generics = "Write once, use for any type safely"

================================================================================
*/
