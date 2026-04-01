package com.collections.List;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.ArrayList;
import java.lang.reflect.Field;

public class ArrayListDemo {

    public static void main(String[] args) throws Exception {

        // Basic List operations
        List<Integer> list = new ArrayList<>();

        list.add(1);
        list.add(5);
        list.add(80);

        System.out.println(list.get(2));
        System.out.println(list.size());

        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i));
        }

        for (int x : list) {
            System.out.println(x);
        }

        System.out.println(list.contains(5));
        System.out.println(list.contains(50));

        list.remove(2);
        list.add(2, 50);

        list.set(2, 50);

        System.out.println(list);

        // Capacity check using reflection
        ArrayList<Integer> listCap = new ArrayList<>(11);

        for (int i = 0; i < 11; i++) {
            listCap.add(1);
        }

//        Field field = ArrayList.class.getDeclaredField("elementData");
//        field.setAccessible(true);
//
//        Object[] elementData = (Object[]) field.get(listCap);
//        System.out.println("Capacity: " + elementData.length);
//
//        listCap.add(1);
//
//        elementData = (Object[]) field.get(listCap);
//        System.out.println("Capacity after adding: " + elementData.length);
        // ArrayList default growth = 1.5x

        for (int i = 0; i < 8; i++) {
            listCap.remove(0);
        }

//        elementData = (Object[]) field.get(listCap);
//        System.out.println("Capacity after removing: " + elementData.length);
//
//        listCap.trimToSize();
//        // trimToSize() → reduces capacity to current size
//
//        elementData = (Object[]) field.get(listCap);
//        System.out.println("Capacity after trim: " + elementData.length);

        // Arrays.asList → fixed-size list (cannot add/remove, but can update)
        List<String> list1 = Arrays.asList("Monday", "Tuesday");
        list1.set(1, "Wednesday");
        System.out.println(list1);

        // Array to List
        String[] array = {"Apple", "Banana", "Cherry"};
        List<String> list2 = Arrays.asList(array);

        List<String> list4 = new ArrayList<>(list2);
        list4.add("Mango");
        System.out.println(list4);

        // List.of → immutable (cannot add/remove/set)
        List<Integer> list3 = List.of(1, 2, 3, 4);
        // list3.set(1, 33); // ❌ UnsupportedOperationException

        // remove(int) vs remove(Object) → important trap
        List<Integer> list5 = new ArrayList<>();
        list5.add(1);
        list5.add(2);
        list5.add(3);

        list5.remove(Integer.valueOf(1)); // removes element '1', not index
        System.out.println(list5);

        // toArray
        Object[] array2 = list5.toArray();
        Integer[] array1 = list5.toArray(new Integer[0]);

        // Sorting examples
        List<String> words = Arrays.asList("banana", "apple", "date");
        words.sort((a, b) -> b.length() - a.length());
        System.out.println(words);

        List<Integer> list6 = new ArrayList<>();
        list6.add(2);
        list6.add(1);
        list6.add(3);

        list6.sort((a, b) -> b - a);
        System.out.println(list6);

        // Custom sorting using Comparator
        List<Student> students = new ArrayList<>();
        students.add(new Student("Charlie", 3.5));
        students.add(new Student("Bob", 3.7));
        students.add(new Student("Alice", 3.5));
        students.add(new Student("Akshit", 3.9));

        Comparator<Student> comparator =
                Comparator.comparing(Student::getGpa) // Comparator.comparing()
                        .reversed() // reversed()
                        .thenComparing(Student::getName); // thenComparing()

        students.sort(comparator);

        for (Student s : students) {
            System.out.println(s.getName() + ": " + s.getGpa());
        }
    }
}

