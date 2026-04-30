package com.collections.List;

import java.util.Arrays;
import java.util.LinkedList;

public class LinkedListImpl {

    public static void main(String[] args) {

        /*
         * LINKEDLIST NOTES (Interview Revision)
         * ------------------------------------
         * 1. LinkedList implements:
         *      - List
         *      - Deque
         *      - Queue
         *
         * 2. Internally uses Doubly Linked List.
         *
         * Each node stores:
         * [prev | data | next]
         *
         * 3. Maintains insertion order.
         * 4. Allows duplicates.
         * 5. Allows null values.
         * 6. Better for frequent insertion/deletion.
         * 7. Slower random access than ArrayList.
         */


        /*
         * Recommended coding style:
         * List<Integer> list = new LinkedList<>();
         *
         * But if you need LinkedList-specific methods:
         * addFirst(), addLast(), getFirst(), getLast()
         * then use LinkedList reference.
         */

        LinkedList<Integer> linkedList = new LinkedList<>();


        /*
         * BASIC ADD OPERATIONS
         */

        linkedList.add(1); // add at end
        linkedList.add(2);
        linkedList.add(3);


        /*
         * get(index)
         * ----------
         * Needs traversal from head or tail.
         * Time = O(n)
         */
        linkedList.get(2);


        /*
         * addLast()
         * add at tail
         * O(1)
         */
        linkedList.addLast(4);


        /*
         * addFirst()
         * add at head
         * O(1)
         */
        linkedList.addFirst(0);


        /*
         * getFirst()
         * get head element
         */
        linkedList.getFirst();


        /*
         * getLast()
         * get tail element
         */
        linkedList.getLast();

        System.out.println(linkedList);



        /*
         * removeIf()
         * Removes elements matching condition
         *
         * Here removes even numbers
         */
        linkedList.removeIf(x -> x % 2 == 0);

        System.out.println(linkedList);



        /*
         * removeAll()
         * removes all matching elements from collection
         */

        LinkedList<String> animals =
                new LinkedList<>(Arrays.asList("Cat", "Dog", "Elephant"));

        LinkedList<String> animalsToRemove =
                new LinkedList<>(Arrays.asList("Dog", "Lion"));

        animals.removeAll(animalsToRemove);

        System.out.println(animals);




        /*
         * IMPORTANT METHODS FOR INTERVIEW
         * ------------------------------
         *
         * addFirst(e)
         * addLast(e)
         * removeFirst()
         * removeLast()
         * peekFirst()
         * peekLast()
         * pollFirst()
         * pollLast()
         *
         * offer(), poll(), peek()
         * (Queue methods)
         *
         * push(), pop()
         * (Stack behavior)
         */




        /*
         * TIME COMPLEXITY
         * ---------------
         *
         * addFirst()      -> O(1)
         * addLast()       -> O(1)
         * removeFirst()   -> O(1)
         * removeLast()    -> O(1)
         * get(index)      -> O(n)
         * search          -> O(n)
         *
         * Note:
         * Java LinkedList keeps head + tail reference.
         */




        /*
         * TRADEOFF : LinkedList vs ArrayList
         * ---------------------------------
         *
         * LinkedList:
         * + Fast insertion/deletion at ends
         * + Good for queue/deque usage
         * - Slow get(index)
         * - More memory (prev + next references)
         * - Poor cache locality
         *
         * ArrayList:
         * + Fast random access O(1)
         * + Better memory locality
         * + Usually faster in real apps
         * - Insert/delete middle costly O(n)
         */




        /*
         * WHEN TO USE LINKEDLIST?
         * -----------------------
         *
         * 1. Implement Queue / Deque
         * 2. Frequent add/remove at head/tail
         * 3. Not much random access needed
         */




        /*
         * INTERVIEW QUESTIONS
         * -------------------
         *
         * Q1 Why LinkedList slower than ArrayList often?
         * Ans:
         * Pointer traversal + poor CPU cache performance.
         *
         * Q2 Is Java LinkedList singly or doubly linked?
         * Ans: Doubly linked.
         *
         * Q3 Can LinkedList be used as Queue?
         * Ans: Yes.
         *
         * Q4 Can LinkedList be used as Stack?
         * Ans: Yes (push/pop).
         *
         * Q5 Is LinkedList thread-safe?
         * Ans: No.
         */
    }
}