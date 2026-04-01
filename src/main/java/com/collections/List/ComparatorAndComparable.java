package com.collections.List;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ComparatorAndComparable {
    public static void main(String[] args) {
        List<Student> students =  new ArrayList<Student>();
        students.add(new Student("Charlie", 3.5));
        students.add(new Student("Bob", 3.7));
        students.add(new Student("Alice", 3.5));
        students.add(new Student("Akshit", 3.9));

        Comparator<Student> comparator = Comparator.comparing(Student::getGpa).reversed().thenComparing(Student::getName);


        students.sort((o1, o2) -> {
            if (o2.getGpa() - o1.getGpa() > 0) {
                return 1;
            } else if (o2.getGpa() - o1.getGpa() < 0) {
                return -1;
            } else {
                return o2.getName().compareTo(o1.getName());
            }
        });
        for (Student s : students) {
            System.out.println(s.getName() + ": " + s.getGpa());
        }
        Collections.sort(students, comparator);
    }

}

