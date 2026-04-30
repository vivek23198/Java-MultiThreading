package com.collections.Queue;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.LinkedList;

public class DequeDemo {
    public static void main(String[] args) {

        // Deque (Double-Ended Queue) Properties:
        // 1. Allows insertion & deletion from both ends (front + rear)
        // 2. Can be used as Queue (FIFO) OR Stack (LIFO)
        // 3. More flexible than Queue
        // 4. No capacity restriction (except bounded implementations)
        // 5. ArrayDeque is faster than LinkedList for most operations

        /*
           INSERTION METHODS

           addFirst(E e): Inserts at front → throws exception if fails
           addLast(E e): Inserts at end → throws exception if fails
           offerFirst(E e): Inserts at front → returns false if fails
           offerLast(E e): Inserts at end → returns false if fails
        */

        /*
           REMOVAL METHODS

           removeFirst(): Removes first → throws exception if empty
           removeLast(): Removes last → throws exception if empty
           pollFirst(): Removes first → returns null if empty
           pollLast(): Removes last → returns null if empty
        */

        /*
           EXAMINATION METHODS

           getFirst(): Returns first → throws exception if empty
           getLast(): Returns last → throws exception if empty
           peekFirst(): Returns first → null if empty
           peekLast(): Returns last → null if empty
        */

        /*
           STACK METHODS (LIFO)

           push(E e): Same as addFirst()
           pop(): Same as removeFirst()
        */

        // 🔹 ArrayDeque (Best choice in most cases)
        // 1. Faster than LinkedList
        // 2. Uses resizable circular array (head & tail pointers)
        // 3. No shifting of elements → efficient
        // 4. Does NOT allow null elements
        Deque<Integer> deque1 = new ArrayDeque<>();

        // Insertion operations
        deque1.addFirst(10);  // insert at front → [10]
        deque1.addLast(20);   // insert at end → [10, 20]
        deque1.offerFirst(5); // insert at front → [5, 10, 20]
        deque1.offerLast(25); // insert at end → [5, 10, 20, 25]

        // Current Deque: [5, 10, 20, 25]
        System.out.println(deque1);

        // Access elements
        System.out.println("First Element: " + deque1.getFirst()); // 5
        System.out.println("Last Element: " + deque1.getLast());   // 25

        // Removal operations
        deque1.removeFirst(); // removes 5 → [10, 20, 25]
        deque1.pollLast();    // removes 25 → [10, 20]

        // Iteration
        for (int num : deque1) {
            System.out.println(num);
        }

        // 🔹 LinkedList as Deque
        // 1. Uses doubly linked list
        // 2. Allows null values
        // 3. Better for frequent insert/delete in middle
        // 4. Slightly slower than ArrayDeque
        Deque<Integer> deque2 = new LinkedList<>();
    }
}