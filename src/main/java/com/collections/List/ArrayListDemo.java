package com.collections.List;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.ArrayList;

public class ArrayListDemo {

    public static void main(String[] args) throws Exception {

        /*
         * ARRAYLIST NOTES (Interview Revision)
         * -----------------------------------
         * 1. ArrayList implements List interface.
         * 2. Internally uses Dynamic Array.
         * 3. Maintains insertion order.
         * 4. Allows duplicate elements.
         * 5. Allows multiple null values.
         * 6. Index based access is fast.
         *
         * Time Complexity:
         * get(index)      -> O(1)
         * add(element)    -> O(1) average
         * add(index,val)  -> O(n)
         * remove(index)   -> O(n)
         * search contains -> O(n)
         */

        // Upcasting: Recommended coding style
        List<Integer> list = new ArrayList<>();

        // add()
        list.add(1);
        list.add(5);
        list.add(80);

        // get(index)
        System.out.println(list.get(2)); // 80

        // size()
        System.out.println(list.size()); // 3


        /*
         * Traversing List
         */

        // Normal for loop
        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i));
        }

        // Enhanced for loop
        for (int x : list) {
            System.out.println(x);
        }

        /*
         * contains()
         * uses equals() internally
         */
        System.out.println(list.contains(5));   // true
        System.out.println(list.contains(50));  // false


        /*
         * remove(index)
         * removes element at index
         */
        list.remove(2);

        /*
         * add(index, value)
         * inserts at specific position
         */
        list.add(2, 50);

        /*
         * set(index, value)
         * replace existing element
         */
        list.set(2, 50);

        System.out.println(list);



        /*
         * CAPACITY CONCEPT
         * ----------------
         * Size     = number of actual elements
         * Capacity = internal array length
         *
         * Default capacity = 10 (first add in many JDKs)
         * Growth = old + old/2  (1.5x)
         */

        ArrayList<Integer> listCap = new ArrayList<>(11);

        for (int i = 0; i < 11; i++) {
            listCap.add(1);
        }

        /*
         * Reflection used to inspect internal array.
         * Good for learning only.
         */

//        Field field = ArrayList.class.getDeclaredField("elementData");
//        field.setAccessible(true);
//
//        Object[] elementData = (Object[]) field.get(listCap);
//        System.out.println("Capacity: " + elementData.length);
//
//        listCap.add(1); // triggers resize
//
//        elementData = (Object[]) field.get(listCap);
//        System.out.println("Capacity after adding: " + elementData.length);


        /*
         * remove() decreases size only
         * capacity remains same
         */
        for (int i = 0; i < 8; i++) {
            listCap.remove(0);
        }

//        elementData = (Object[]) field.get(listCap);
//        System.out.println("Capacity after removing: " + elementData.length);

        /*
         * trimToSize()
         * reduce capacity to current size
         */
//        listCap.trimToSize();
//
//        elementData = (Object[]) field.get(listCap);
//        System.out.println("Capacity after trim: " + elementData.length);



        /*
         * Arrays.asList()
         * ----------------
         * Fixed-size List backed by array.
         *
         * Can:
         * set()
         *
         * Cannot:
         * add()
         * remove()
         */

        List<String> list1 = Arrays.asList("Monday", "Tuesday");
        list1.set(1, "Wednesday");

        System.out.println(list1);



        /*
         * Array to List
         */
        String[] array = {"Apple", "Banana", "Cherry"};

        List<String> list2 = Arrays.asList(array);

        /*
         * To make modifiable list:
         */
        List<String> list4 = new ArrayList<>(list2);
        list4.add("Mango");

        System.out.println(list4);



        /*
         * List.of()
         * ----------
         * Immutable list (Java 9+)
         *
         * Cannot add/remove/set
         */

        List<Integer> list3 = List.of(1, 2, 3, 4);

        // list3.set(1, 33); // UnsupportedOperationException



        /*
         * IMPORTANT INTERVIEW TRAP
         * remove(int) vs remove(Object)
         */

        List<Integer> list5 = new ArrayList<>();
        list5.add(1);
        list5.add(2);
        list5.add(3);

        /*
         * remove(1) means remove index 1
         * remove(Integer.valueOf(1))
         * means remove element 1
         */

        list5.remove(Integer.valueOf(1));

        System.out.println(list5); // [2,3]



        /*
         * toArray()
         */

        Object[] array2 = list5.toArray();

        Integer[] array1 = list5.toArray(new Integer[0]);



        /*
         * SORTING
         */

        List<String> words = Arrays.asList("banana", "apple", "date");

        // Sort by descending length
        words.sort((a, b) -> b.length() - a.length());

        System.out.println(words);



        List<Integer> list6 = new ArrayList<>();
        list6.add(2);
        list6.add(1);
        list6.add(3);

        // Descending order
        list6.sort((a, b) -> b - a);

        System.out.println(list6);



        /*
         * CUSTOM OBJECT SORTING
         */

        List<Student> students = new ArrayList<>();

        students.add(new Student("Charlie", 3.5));
        students.add(new Student("Bob", 3.7));
        students.add(new Student("Alice", 3.5));
        students.add(new Student("Akshit", 3.9));

        /*
         * Sort by:
         * 1. GPA descending
         * 2. Name ascending
         */

        Comparator<Student> comparator =
                Comparator.comparing(Student::getGpa)
                        .reversed()
                        .thenComparing(Student::getName);

        students.sort(comparator);

        for (Student s : students) {
            System.out.println(s.getName() + ": " + s.getGpa());
        }



        /*
         * TRADEOFFS : ArrayList vs LinkedList
         * ----------------------------------
         *
         * ArrayList:
         * + Fast random access O(1)
         * + Better cache performance
         * + Less memory overhead
         * - Insert/delete middle is slow O(n)
         *
         * LinkedList:
         * + Insert/delete at node faster
         * - get(index) slow O(n)
         * - More memory (prev,next refs)
         *
         * Use ArrayList in most real projects.
         */



        /*
         * INTERVIEW QUESTIONS
         * -------------------
         * Q1 Why ArrayList faster than LinkedList?
         * Ans: Contiguous memory + cache friendly.
         *
         * Q2 Is ArrayList thread safe?
         * Ans: No.
         *
         * Q3 Thread-safe alternative?
         * Ans:
         * Collections.synchronizedList(...)
         * CopyOnWriteArrayList
         *
         * Q4 Can ArrayList store primitives?
         * Ans: No, use Wrapper classes.
         */
    }
}