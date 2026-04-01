package com.collections.Map;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/*
============================= HASHCODE & EQUALS (INTERVIEW REVISION) =============================

1. Why hashCode() and equals() are important?
   - HashMap uses BOTH methods to store and retrieve keys correctly.
   - hashCode() → decides bucket index
   - equals()   → resolves collision inside bucket

2. Flow of put(key, value):
   Step 1: Compute hashCode of key
   Step 2: Find index using (n-1) & hash
   Step 3: If bucket is empty → insert
   Step 4: If bucket has elements:
           → Compare using equals()
           → If equal → REPLACE value
           → Else → add new node (collision)

3. Flow of get(key):
   Step 1: Compute hashCode
   Step 2: Go to bucket
   Step 3: Use equals() to find exact key

4. Contract Rules:
   - If two objects are equal → hashCode MUST be same
   - If hashCode same → objects MAY or MAY NOT be equal

5. Common Mistakes:
   ❌ Override equals() but NOT hashCode() → duplicates in HashMap
   ❌ Wrong equals() logic → retrieval issues

6. Time Complexity:
   - put() → O(1) average
   - get() → O(1) average
   - collision case → O(log n) (tree) or O(n)

7. Real Insight (VERY IMPORTANT 🔥):
   - HashMap first checks hashCode → fast filtering
   - Then uses equals() → exact match

==============================================================================================
*/

public class HashCodeAndEqualsMethod {
    public static void main(String[] args) {

        // Creating HashMap with custom object as key
        HashMap<Person, String> map = new HashMap<>();

        // Creating objects
        Person p1 = new Person("Alice", 1);
        Person p2 = new Person("Bob", 2);

        // p3 has SAME data as p1 → logically equal
        Person p3 = new Person("Alice", 1);

        // Insert operations
        map.put(p1, "Engineer"); // hashcode1 → index1
        map.put(p2, "Designer"); // hashcode2 → index2

        // p3 has SAME hashCode as p1 → goes to same bucket
        // equals() returns true → value gets REPLACED
        map.put(p3, "Manager");

        // Explanation:
        // p1 and p3 are equal → only ONE entry stored
        // value becomes "Manager"

        System.out.println("HashMap Size: " + map.size()); // Expected: 2

        // Fetch using p1 → returns updated value
        System.out.println("Value for p1: " + map.get(p1)); // Manager

        // Fetch using p3 → same result (since equal key)
        System.out.println("Value for p3: " + map.get(p3)); // Manager


        // ================= STRING EXAMPLE =================

        Map<String, Integer> map1 = new HashMap<>();

        // Insert key-value pairs
        map1.put("Shubham", 90); // hashcode1 → index1
        map1.put("Neha", 92);    // hashcode2 → index2

        // Duplicate key → equals() returns true → value replaced
        map1.put("Shubham", 99);

        // Final value for "Shubham" will be 99
    }
}

// Custom class used as key in HashMap
class Person {
    private String name;
    private int id;

    public Person(String name, int id) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    // hashCode determines bucket location
    @Override
    public int hashCode() {
        // Combines name and id to generate hash
        return Objects.hash(name, id);
    }

    // equals determines logical equality
    @Override
    public boolean equals(Object obj) {

        // Same reference → equal
        if (this == obj) {
            return true;
        }

        // Null check
        if (obj == null) {
            return false;
        }

        // Class check
        if (getClass() != obj.getClass()) {
            return false;
        }

        // Typecasting
        Person other = (Person) obj;

        // Compare fields
        return id == other.getId() &&
                Objects.equals(name, other.getName());
    }

    @Override
    public String toString() {
        return "id: " + id + ", name: " + name;
    }
}