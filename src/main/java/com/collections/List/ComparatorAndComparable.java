package com.collections.List;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ComparatorAndComparable {

    public static void main(String[] args) {

        /*
         * COMPARATOR vs COMPARABLE (Interview Revision)
         * --------------------------------------------
         *
         * Comparable:
         * - Present inside class
         * - Defines DEFAULT/NATURAL sorting
         * - Method: compareTo()
         * - Example: sort by name or id by default
         *
         * Comparator:
         * - External to class
         * - Defines CUSTOM sorting
         * - Method: compare()
         * - Multiple sorting logics possible
         */


        List<Student> students = new ArrayList<>();

        students.add(new Student("Charlie", 3.5));
        students.add(new Student("Bob", 3.7));
        students.add(new Student("Alice", 3.5));
        students.add(new Student("Akshit", 3.9));


        /*
         * MODERN WAY (Recommended)
         * ------------------------
         * Sort by:
         * 1. GPA descending
         * 2. Name ascending
         */
        Comparator<Student> comparator =
                Comparator.comparing(Student::getGpa)   // sort by GPA
                        .reversed()                     // descending
                        .thenComparing(Student::getName); // tie-break by name


        /*
         * MANUAL COMPARATOR (Important for interviews)
         */
        students.sort((o1, o2) -> {

            // Compare GPA (descending)
            if (o2.getGpa() - o1.getGpa() > 0) {
                return 1;
            } else if (o2.getGpa() - o1.getGpa() < 0) {
                return -1;
            } else {
                // If GPA same → compare names (ascending)
                return o1.getName().compareTo(o2.getName());
            }
        });


        /*
         * PRINT RESULT
         */
        for (Student s : students) {
            System.out.println(s.getName() + ": " + s.getGpa());
        }


        /*
         * USING Collections.sort()
         * ------------------------
         * Older style (before Java 8)
         */
        Collections.sort(students, comparator);



        /*
         * IMPORTANT POINTS
         * ----------------
         *
         * 1. compare() return values:
         *    negative -> o1 < o2
         *    zero     -> equal
         *    positive -> o1 > o2
         *
         * 2. Avoid subtracting doubles directly (precision issue)
         *    Better:
         *    Double.compare(o2.getGpa(), o1.getGpa())
         *
         * 3. String comparison:
         *    compareTo() → lexicographical order
         */


        /*
         * BETTER SAFE VERSION (Recommended)
         */
        students.sort((o1, o2) -> {
            int gpaCompare = Double.compare(o2.getGpa(), o1.getGpa());
            if (gpaCompare != 0) return gpaCompare;

            return o1.getName().compareTo(o2.getName());
        });



        /*
         * TRADEOFF
         * --------
         *
         * Comparable:
         * + Simple, default sorting
         * - Only ONE sorting logic
         *
         * Comparator:
         * + Multiple sorting logics
         * + Flexible
         * - Slightly more verbose
         */


        /*
         * INTERVIEW QUESTIONS
         * -------------------
         *
         * Q1 Difference between Comparator and Comparable?
         * Ans:
         * Comparable → internal, single sort
         * Comparator → external, multiple sorts
         *
         * Q2 Which one is better?
         * Ans:
         * Comparator (more flexible)
         *
         * Q3 Can we use both?
         * Ans: Yes.
         *
         * Q4 What happens if compare() returns wrong values?
         * Ans: Sorting becomes inconsistent/incorrect.
         */
    }
}