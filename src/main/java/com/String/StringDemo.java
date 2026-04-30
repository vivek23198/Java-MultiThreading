package com.String;

/*
================================= STRING vs STRINGBUFFER vs STRINGBUILDER =================================

Definition:
------------
String, StringBuffer, and StringBuilder are used to handle text (sequence of characters) in Java.

Key Differences:
----------------

1. String
   - Immutable (cannot be changed once created)
   - Thread-safe (because immutable)
   - Stored in String Pool

2. StringBuffer
   - Mutable (can be modified)
   - Thread-safe (synchronized methods)
   - Slower due to synchronization

3. StringBuilder
   - Mutable
   - NOT thread-safe
   - Faster than StringBuffer

==============================================================================================
*/

public class StringDemo {

    public static void main(String[] args) {

        /*
         * ================= 1. STRING =================
         * Immutable → new object created on modification
         */
        String s1 = "Hello";
        s1.concat(" World"); // ❌ does NOT modify original

        System.out.println("String (unchanged): " + s1); // Hello

        // Correct way
        s1 = s1.concat(" World");
        System.out.println("String (new object): " + s1); // Hello World


        /*
         * ================= STRING POOL =================
         */
        String a = "Java";
        String b = "Java";

        System.out.println(a == b); // true (same reference in pool)

        String c = new String("Java");
        System.out.println(a == c); // false (different object)


        /*
         * ================= 2. STRINGBUFFER =================
         * Mutable + Thread-safe
         */
        StringBuffer sb = new StringBuffer("Hello");

        sb.append(" World"); // modifies same object
        System.out.println("StringBuffer: " + sb);

        // reverse
        sb.reverse();
        System.out.println("Reversed: " + sb);

        // capacity concept
        StringBuffer cap = new StringBuffer();
        System.out.println("Default Capacity: " + cap.capacity()); // 16

        cap.append("Hello");
        System.out.println("Capacity after append: " + cap.capacity());


        /*
         * ================= 3. STRINGBUILDER =================
         * Mutable + NOT thread-safe (faster)
         */
        StringBuilder sb2 = new StringBuilder("Hello");

        sb2.append(" Java");
        System.out.println("StringBuilder: " + sb2);

        sb2.insert(5, " World");
        System.out.println("After Insert: " + sb2);

        sb2.delete(5, 11);
        System.out.println("After Delete: " + sb2);


        /*
         * ================= PERFORMANCE TEST =================
         */
        long start = System.currentTimeMillis();

        String str = "";
        for (int i = 0; i < 10000; i++) {
            str += i; // creates new object each time
        }

        long end = System.currentTimeMillis();
        System.out.println("String Time: " + (end - start));


        start = System.currentTimeMillis();

        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < 10000; i++) {
            builder.append(i);
        }

        end = System.currentTimeMillis();
        System.out.println("StringBuilder Time: " + (end - start));


        /*
         * ================= EQUALS vs == =================
         */
        String s2 = "Test";
        String s3 = new String("Test");

        System.out.println(s2 == s3);       // false
        System.out.println(s2.equals(s3));  // true


        /*
         * ================= IMPORTANT METHODS =================
         */

        String str2 = "Java";

        System.out.println(str2.length());       // 4
        System.out.println(str2.charAt(0));      // J
        System.out.println(str2.substring(1));   // ava
        System.out.println(str2.toUpperCase());  // JAVA


        /*
         * ================= INTERVIEW TRAP =================
         */
        String x = "hello";
        String y = "hello";

        System.out.println(x == y); // true (pool)

        String z = new String("hello");
        System.out.println(x == z); // false


        /*
         * ================= STRINGBUFFER vs STRINGBUILDER =================
         */

        // Use StringBuffer when:
        // - Multithreading environment
        // - Thread safety required

        // Use StringBuilder when:
        // - Single-threaded
        // - Performance critical
    }
}


/*
================================= INTERVIEW QUESTIONS =================================

Q1: Difference between String and StringBuilder?
Ans:
String → immutable
StringBuilder → mutable

Q2: StringBuffer vs StringBuilder?
Ans:
StringBuffer → thread-safe
StringBuilder → faster (no synchronization)

Q3: Why String is immutable?
Ans:
Security, caching, thread-safety

Q4: What is String Pool?
Ans:
Memory optimization technique

==============================================================================================
*/


/*
================================= ONE-LINER =================================

String → immutable
StringBuffer → mutable + thread-safe
StringBuilder → mutable + fast

==============================================================================================
*/
