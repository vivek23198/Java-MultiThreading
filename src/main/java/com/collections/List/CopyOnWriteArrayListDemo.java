package com.collections.List;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class CopyOnWriteArrayListDemo {

    public static void main(String[] args) {

        /*
         * COPYONWRITEARRAYLIST NOTES (Interview Revision)
         * ----------------------------------------------
         * 1. Thread-safe variant of ArrayList.
         * 2. Belongs to java.util.concurrent package.
         *
         * 3. Core Idea: "Copy on Write"
         *    - On every write (add/remove/update),
         *      a NEW copy of internal array is created.
         *    - Modification happens on the new copy.
         *    - Reference is updated atomically.
         *
         * Example:
         * oldArray -> [A, B, C]
         * newArray -> [A, B, C, D]
         *
         * 4. Readers always see a stable snapshot (no locking needed).
         */


        /*
         * READ vs WRITE PERFORMANCE
         * -------------------------
         * Read  -> Very Fast (no locking)
         * Write -> Expensive (copy entire array)
         *
         * Best suited when:
         * - Reads >> Writes (read-heavy system)
         */


        /*
         * BASIC USAGE
         */
        CopyOnWriteArrayList<Integer> list = new CopyOnWriteArrayList<>();


        /*
         * Example 1: Safe modification during iteration
         */

        List<String> shoppingList = new CopyOnWriteArrayList<>();

        shoppingList.add("Milk");
        shoppingList.add("Eggs");
        shoppingList.add("Bread");

        System.out.println("Initial Shopping List: " + shoppingList);

        /*
         * IMPORTANT:
         * No ConcurrentModificationException
         * because iteration happens on snapshot
         */
        for (String item : shoppingList) {

            System.out.println(item);

            // Modifying while iterating (safe here)
            if (item.equals("Eggs")) {
                shoppingList.add("Butter");
                System.out.println("Added Butter while reading.");
            }
        }

        /*
         * Note:
         * "Butter" will NOT appear in current loop
         * because iterator is working on OLD snapshot
         */
        System.out.println("Updated Shopping List: " + shoppingList);



        /*
         * Example 2: Multi-threaded scenario
         */

        List<String> sharedList = new CopyOnWriteArrayList<>();

        sharedList.add("Item1");
        sharedList.add("Item2");
        sharedList.add("Item3");


        /*
         * Reader Thread
         * Continuously reads list safely
         */
        Thread readerThread = new Thread(() -> {
            try {
                while (true) {
                    for (String item : sharedList) {
                        System.out.println("Reading item: " + item);
                        Thread.sleep(100);
                    }
                }
            } catch (Exception e) {
                System.out.println("Exception in reader thread: " + e);
            }
        });


        /*
         * Writer Thread
         * Modifies list safely without affecting readers
         */
        Thread writerThread = new Thread(() -> {
            try {
                Thread.sleep(500);

                sharedList.add("Item4");
                System.out.println("Added Item4 to the list.");

                Thread.sleep(500);

                sharedList.remove("Item1");
                System.out.println("Removed Item1 from the list.");

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });


        readerThread.start();
        writerThread.start();



        /*
         * INTERNAL WORKING (Important for Interview)
         * ------------------------------------------
         * - Uses ReentrantLock internally for writes.
         * - Array reference is volatile.
         * - Writes are atomic.
         * - Iterators are snapshot-based (fail-safe).
         */


        /*
         * FAIL-FAST vs FAIL-SAFE
         * ----------------------
         * ArrayList Iterator:
         * - Fail-Fast
         * - Throws ConcurrentModificationException
         *
         * CopyOnWriteArrayList:
         * - Fail-Safe
         * - Works on snapshot copy
         */


        /*
         * TRADEOFFS
         * ---------
         *
         * Pros:
         * + Thread-safe without external synchronization
         * + No ConcurrentModificationException
         * + Excellent for read-heavy systems
         *
         * Cons:
         * - High memory usage (copies on every write)
         * - Slow writes (O(n))
         * - Not suitable for frequent updates
         */


        /*
         * WHEN TO USE?
         * ------------
         * - Event listeners list
         * - Configuration data
         * - Read-heavy cache
         * - Rare modifications
         */


        /*
         * INTERVIEW QUESTIONS
         * -------------------
         *
         * Q1 Why no ConcurrentModificationException?
         * Ans: Iterator works on snapshot copy.
         *
         * Q2 Is it better than synchronizedList?
         * Ans:
         * - For read-heavy → YES
         * - For write-heavy → NO
         *
         * Q3 Time complexity?
         * Read  -> O(1)
         * Write -> O(n)
         *
         * Q4 Does iterator reflect latest changes?
         * Ans: No (snapshot-based).
         */
    }
}