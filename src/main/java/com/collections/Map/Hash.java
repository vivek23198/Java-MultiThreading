package com.collections.Map;

/*
============================= SIMPLE HASH FUNCTION (INTERVIEW REVISION) =============================

1. What is Hashing?
   - Hashing converts input (key) → fixed-size integer (hash value)
   - Used in HashMap to decide bucket index

2. Our Logic:
   - Convert each character → ASCII value
   - Sum all ASCII values
   - Apply modulo (sum % 10) → restrict index between 0–9

3. Time & Space Complexity:
   - Time: O(n) → iterate over characters
   - Space: O(1)

4. Problem with this Hash Function (IMPORTANT ⚠️):
   ❌ High Collision Rate:
      "ABC" → 65 + 66 + 67 = 198 → 198 % 10 = 8
      "CBA" → 67 + 66 + 65 = 198 → 198 % 10 = 8
      → Different keys, SAME hash → collision

   ❌ Order-insensitive:
      - "ABC" and "CBA" treated same → BAD hash design

   ❌ Small range (0–9):
      - Too many keys map to same buckets

5. Good Hash Function Should:
   ✅ Minimize collisions
   ✅ Be fast to compute
   ✅ Distribute keys uniformly
   ✅ Consider order of characters

6. Real Java HashMap:
   - Uses improved hash (bit mixing)
   - Applies (n-1) & hash for bucket index
   - Handles collisions via LinkedList / Tree

==============================================================================================
*/

public class Hash {
    public static void main(String[] args) {

        // Same characters, different order
        // But our hash function treats them same → collision
        System.out.println(simpleHash("ABC"));
        System.out.println(simpleHash("CBA"));
    }

    public static int simpleHash(String key) {

        int sum = 0;

        // Convert string to char array and iterate
        for (char c : key.toCharArray()) {

            // Add ASCII value of each character
            sum += (int) c;
        }

        // Modulo operation to limit bucket size (0–9)
        // This simulates fixed bucket array size
        return sum % 10;
    }
}