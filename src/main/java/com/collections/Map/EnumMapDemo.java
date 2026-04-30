package com.collections.Map;

import java.util.EnumMap;
import java.util.Map;

public class EnumMapDemo {
    public static void main(String[] args) {

        // EnumMap Properties:
        // 1. Special Map implementation for enum keys only
        // 2. Internally backed by an array → very fast access (O(1))
        // 3. Uses enum ordinal (index) instead of hashing
        // 4. Maintains natural order of enum constants (declaration order)
        // 5. NOT thread-safe (must use Collections.synchronizedMap if needed)
        // 6. Does NOT allow null keys (NullPointerException)
        // 7. Allows null values
        // 8. More memory efficient than HashMap (no hashing overhead)
        // 9. Faster than HashMap for enum keys

        // Creating EnumMap with key type Day
        // Must pass enum class type while creating
        Map<Day, String> map = new EnumMap<>(Day.class);

        // Internally behaves like array:
        // index = ordinal of enum → fast lookup
        // Example: [MONDAY, TUESDAY, ...]
        // [_,"Gym",_,_,_,_,_]

        // Adding values
        map.put(Day.TUESDAY, "Gym");   // stored at index of TUESDAY
        map.put(Day.MONDAY, "Walk");   // stored at index of MONDAY

        // Retrieval using enum key → very fast (array access)
        String s = map.get(Day.TUESDAY);

        // Output maintains enum order (not insertion order)
        System.out.println(map);
    }
}

// Enum definition
enum Day {
    MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY
}