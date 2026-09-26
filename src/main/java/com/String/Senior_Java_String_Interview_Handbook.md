# Senior Java Interview Handbook — String

> Target: 5–10+ years Java / Senior Software Engineer / SDE2 interviews

## 1. Why String Is Important

String questions test:
- Immutability
- String Pool
- `==` vs `equals()`
- `hashCode()`
- `StringBuilder` / `StringBuffer`
- JVM memory concepts
- Performance
- Collections
- String interning
- Coding and optimization

---

## 2. What Is String?

`String` is a class in `java.lang`.

Important properties:
- String is immutable.
- String is `final`.
- String implements `CharSequence`.
- String literals can be stored in the String Pool.

```java
String name = "Vivek";
```

---

## 3. Why Is String Immutable?

Once a String is created, its value cannot be changed.

```java
String s = "Hello";

s.concat(" World");

System.out.println(s);
```

Output:

```text
Hello
```

`concat()` returns a new String.

```java
s = s.concat(" World");
```

Now `s` refers to `"Hello World"`.

### Why immutability?

1. **String Pool** — pooled Strings can be safely shared.
2. **Security** — Strings are used for paths, URLs, configuration and other security-sensitive values.
3. **Hashing** — stable content means stable `hashCode()`, making Strings reliable HashMap keys.
4. **Thread safety** — immutable objects can be safely shared between threads.

---

## 4. String Pool

```java
String a = "Java";
String b = "Java";

System.out.println(a == b);
```

Output:

```text
true
```

Both references can point to the same pooled String.

Conceptually:

```text
       String Pool
           |
         "Java"
        /             a        b
```

---

## 5. `==` vs `equals()`

`==` checks reference identity.

`equals()` checks String content.

```java
String a = "Java";
String b = new String("Java");

System.out.println(a == b);       // false
System.out.println(a.equals(b));  // true
```

Interview rule:

> Use `equals()` for String value comparison.

---

## 6. `new String()` Trap

```java
String a = "Java";
String b = new String("Java");

System.out.println(a == b);
System.out.println(a.equals(b));
```

Output:

```text
false
true
```

The literal can be in the pool, while `new String()` creates a separate String object.

---

## 7. `intern()`

`intern()` returns the canonical representation from the String Pool.

```java
String a = new String("Java");
String b = a.intern();
String c = "Java";

System.out.println(b == c);
```

Output:

```text
true
```

Use `intern()` carefully. Excessive interning can create memory pressure and is not automatically a performance optimization.

---

## 8. Compile-Time vs Runtime Concatenation

### Compile-time

```java
String a = "Java";
String b = "Ja" + "va";

System.out.println(a == b);
```

Output:

```text
true
```

The compiler can fold the constant expression.

### Runtime

```java
String a = "Java";

String x = "Ja";
String b = x + "va";

System.out.println(a == b);
```

Typically:

```text
false
```

But:

```java
a.equals(b)
```

is:

```text
true
```

---

## 9. `final String` vs Immutable String

```java
final String s = "Java";
```

`final` prevents reassignment of the reference.

```java
s = "Python"; // compilation error
```

This is different from immutability:

```text
final       → reference cannot be reassigned
immutable   → object's state cannot be changed
```

---

## 10. String Concatenation Performance

Avoid repeated concatenation in large loops:

```java
String result = "";

for (int i = 0; i < 10000; i++) {
    result += i;
}
```

Because String is immutable, repeated construction can create many intermediate objects and repeated copying.

Prefer:

```java
StringBuilder result = new StringBuilder();

for (int i = 0; i < 10000; i++) {
    result.append(i);
}

String output = result.toString();
```

---

## 11. StringBuilder vs StringBuffer

| Feature | StringBuilder | StringBuffer |
|---|---|---|
| Mutable | Yes | Yes |
| Thread-safe | No | Yes |
| Synchronization | No | Synchronized methods |
| Typical use | Local string construction | Shared mutable buffer when synchronization is required |

For ordinary method-local construction, `StringBuilder` is generally preferred.

---

## 12. StringBuilder Capacity

```java
StringBuilder sb = new StringBuilder(1000);
```

Providing an approximate capacity can reduce repeated resizing when the expected output is large.

---

## 13. Important String Methods

```java
String s = "JavaProgramming";

s.length();
s.charAt(0);
s.substring(4);
s.contains("Java");
s.startsWith("Java");
s.endsWith("ing");
s.indexOf("Pro");
s.replace("Java", "Spring");
```

String methods do not mutate the original String. Operations that transform content return another String.

---

## 14. `replace()` vs `replaceAll()`

`replace()` performs literal replacement.

```java
"a1b2c3".replace("1", "X");
```

`replaceAll()` uses a regular expression.

```java
"a1b2c3".replaceAll("\d", "X");
```

Result:

```text
aXbXcX
```

Use `replaceAll()` only when regex behavior is actually needed.

---

## 15. `split()`

```java
String s = "java,spring,kafka";

String[] values = s.split(",");
```

Result:

```text
[java, spring, kafka]
```

Remember that `split()` uses a regular expression.

For a regex metacharacter as delimiter, escape it appropriately:

```java
"java|spring".split("\|");
```

---

## 16. `isEmpty()` vs `isBlank()`

```java
String s = "   ";

System.out.println(s.isEmpty()); // false
System.out.println(s.isBlank()); // true
```

- `isEmpty()` checks whether length is zero.
- `isBlank()` checks whether the String contains no non-whitespace characters.

---

## 17. `null` vs Empty String

```java
String a = null;
String b = "";
```

These are different.

```java
a == null;       // true
b.isEmpty();     // true
```

Calling:

```java
a.isEmpty();
```

throws `NullPointerException`.

---

## 18. `String.valueOf()` vs `toString()`

```java
Integer value = null;
```

This throws:

```java
value.toString();
```

because `value` is null.

But:

```java
String.valueOf(value);
```

returns:

```text
"null"
```

---

## 19. String as HashMap Key

Strings are excellent HashMap keys because they are immutable and have content-based `equals()` / `hashCode()`.

```java
Map<String, Integer> map = new HashMap<>();

map.put("Java", 100);

System.out.println(map.get("Java"));
```

Output:

```text
100
```

Two equal Strings have equal hash codes.

---

## 20. TreeSet + String

```java
Set<String> set = new TreeSet<>();

set.add("Banana");
set.add("Apple");
set.add("Orange");

System.out.println(set);
```

Output:

```text
[Apple, Banana, Orange]
```

TreeSet uses natural ordering.

### Important TreeSet Trap

For custom objects, TreeSet uses `compareTo()` or a `Comparator` to determine equivalence for sorted-set operations.

If:

```java
compareTo() == 0
```

the TreeSet can treat the objects as duplicates even when:

```java
equals() == false
```

Example:

```java
class Employee implements Comparable<Employee> {

    int id;
    String name;

    Employee(int id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public int compareTo(Employee other) {
        return Integer.compare(this.id, other.id);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Employee)) return false;

        Employee other = (Employee) obj;
        return id == other.id && name.equals(other.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
```

```java
Set<Employee> set = new TreeSet<>();

set.add(new Employee(101, "Vivek"));
set.add(new Employee(101, "Amit"));

System.out.println(set.size());
```

Output:

```text
1
```

Reason:

```text
equals() → false
compareTo() → 0
```

Interview rule:

```text
HashSet → hashCode() + equals()
TreeSet → compareTo() / Comparator
```

Ideally, natural ordering should be consistent with `equals()`.

---

# 21. Coding: Reverse a String

```java
public static String reverse(String s) {
    char[] chars = s.toCharArray();

    int left = 0;
    int right = chars.length - 1;

    while (left < right) {
        char temp = chars[left];
        chars[left] = chars[right];
        chars[right] = temp;

        left++;
        right--;
    }

    return new String(chars);
}
```

Complexity:

```text
Time: O(n)
Space: O(n)
```

---

# 22. Coding: First Non-Repeating Character

Input:

```text
"swiss"
```

Output:

```text
'w'
```

One approach:

```java
public static char firstNonRepeating(String s) {

    Map<Character, Integer> frequency = new LinkedHashMap<>();

    for (char c : s.toCharArray()) {
        frequency.put(c, frequency.getOrDefault(c, 0) + 1);
    }

    for (char c : s.toCharArray()) {
        if (frequency.get(c) == 1) {
            return c;
        }
    }

    return ' ';
}
```

Complexity:

```text
Time: O(n)
Space: O(n)
```

---

# 23. Coding: Remove Duplicate Characters

Input:

```text
"programming"
```

Output:

```text
"progamin"
```

```java
public static String removeDuplicates(String s) {

    Set<Character> set = new LinkedHashSet<>();

    for (char c : s.toCharArray()) {
        set.add(c);
    }

    StringBuilder result = new StringBuilder();

    for (char c : set) {
        result.append(c);
    }

    return result.toString();
}
```

---

# 24. Coding: Check Anagram

```java
public static boolean isAnagram(String a, String b) {

    if (a.length() != b.length()) {
        return false;
    }

    int[] count = new int[256];

    for (char c : a.toCharArray()) {
        count[c]++;
    }

    for (char c : b.toCharArray()) {
        count[c]--;

        if (count[c] < 0) {
            return false;
        }
    }

    return true;
}
```

For arbitrary Unicode text, a fixed-size ASCII array is not sufficient; use an appropriate Unicode-aware representation when required.

Complexity:

```text
Time: O(n)
Space: O(1) for the fixed character table
```

---

# 25. Coding: Longest Substring Without Repeating Characters

Input:

```text
"abcabcbb"
```

Output:

```text
3
```

Expected pattern:

**Sliding Window**

```java
public static int lengthOfLongestSubstring(String s) {

    Set<Character> set = new HashSet<>();

    int left = 0;
    int maxLength = 0;

    for (int right = 0; right < s.length(); right++) {

        while (set.contains(s.charAt(right))) {
            set.remove(s.charAt(left));
            left++;
        }

        set.add(s.charAt(right));

        maxLength = Math.max(maxLength, right - left + 1);
    }

    return maxLength;
}
```

Complexity:

```text
Time: O(n)
Space: O(min(n, character-set-size))
```

---

# 26. Coding: String Compression

Input:

```text
"aaabbccccd"
```

Output:

```text
"a3b2c4d1"
```

```java
public static String compress(String s) {

    if (s == null || s.isEmpty()) {
        return s;
    }

    StringBuilder result = new StringBuilder();
    int count = 1;

    for (int i = 1; i <= s.length(); i++) {

        if (i < s.length() && s.charAt(i) == s.charAt(i - 1)) {
            count++;
        } else {
            result.append(s.charAt(i - 1));
            result.append(count);
            count = 1;
        }
    }

    return result.toString();
}
```

Complexity:

```text
Time: O(n)
Space: O(n)
```

---

# 27. Coding: Valid Palindrome

Example:

```text
"A man, a plan, a canal: Panama"
```

Output:

```text
true
```

Ignore spaces, punctuation and case.

Senior-level approach:

Use two pointers directly instead of creating unnecessary intermediate Strings.

```text
left  →                       ← right
A man, a plan, a canal: Panama
```

Target complexity:

```text
Time: O(n)
Extra Space: O(1)
```

---

# 28. Coding: Minimum Window Substring

Input:

```text
s = "ADOBECODEBANC"
t = "ABC"
```

Output:

```text
"BANC"
```

Key concepts:

- Sliding Window
- Two pointers
- Frequency map
- Expand right
- Contract left
- Track required characters

Target:

```text
Time: O(n)
Space: O(character-set-size)
```

---

# 29. Coding: Group Anagrams

Input:

```text
["eat", "tea", "tan", "ate", "nat", "bat"]
```

Possible output:

```text
[
  ["eat", "tea", "ate"],
  ["tan", "nat"],
  ["bat"]
]
```

Common approach:

```text
word
 ↓
sort characters
 ↓
canonical key
 ↓
HashMap
```

Example:

```text
eat → aet
tea → aet
ate → aet
```

Senior follow-up:

> Can you avoid sorting every word?

Expected discussion:

Character-frequency signature.

---

# 30. Production Scenario: Large String Processing

Suppose a service receives a 500 MB text payload.

Ask:

> Would you convert the entire payload into one String?

Things to consider:

- Memory pressure
- Multiple intermediate copies
- GC pressure
- Streaming
- Buffered input
- Processing chunks/lines
- Avoiding unnecessary String creation

General principle:

> Do not load huge data into a String if the data can be processed as a stream.

---

# 31. Production Scenario: StringBuilder

Multiple threads share:

```java
StringBuilder builder;
```

and append to it.

Question:

> Is this thread-safe?

Answer:

**No.**

Possible approaches:

- Avoid sharing it.
- Give each thread its own builder.
- Synchronize access if genuinely required.
- Prefer a design that avoids shared mutable state.

Do not automatically choose `StringBuffer` without understanding the actual concurrency requirement.

---

# 32. Production Scenario: String Concatenation

Given:

```java
String result = "";

for (String value : values) {
    result += value;
}
```

There are one million values.

Ask:

> What problems can this create and how would you improve it?

Expected:

```text
String immutable
      ↓
many intermediate objects
      ↓
repeated copying
      ↓
allocation / GC pressure
      ↓
StringBuilder
```

---

# 33. Output-Based Interview Questions

## Question 1

```java
String a = "Java";
String b = "Java";
String c = new String("Java");

System.out.println(a == b);
System.out.println(a == c);
System.out.println(a.equals(c));
```

Output:

```text
true
false
true
```

---

## Question 2

```java
String a = "Ja";
String b = "va";

String c = "Java";
String d = a + b;

System.out.println(c == d);
System.out.println(c.equals(d));
```

Expected:

```text
false
true
```

---

## Question 3

```java
String a = "Ja" + "va";
String b = "Java";

System.out.println(a == b);
```

Expected:

```text
true
```

---

## Question 4

```java
String s = "Java";

s.concat(" Developer");

System.out.println(s);
```

Expected:

```text
Java
```

---

## Question 5

```java
StringBuilder sb = new StringBuilder("Java");

sb.append(" Developer");

System.out.println(sb);
```

Expected:

```text
Java Developer
```

---

# 34. Quick Comparison

| Type | Mutable | Thread-safe | Typical use |
|---|---|---|---|
| String | No | Safe to share due to immutability | Text/value |
| StringBuilder | Yes | No | Efficient local string construction |
| StringBuffer | Yes | Synchronized methods | Shared mutable buffer when synchronization is required |

---

# 35. Senior Interview Questions

### Core

1. Why is String immutable?
2. What is String Pool?
3. Difference between `==` and `equals()`?
4. Why is String final?
5. What does `intern()` do?
6. Why is String a good HashMap key?
7. String vs StringBuilder?
8. StringBuilder vs StringBuffer?

### Intermediate

9. What happens during String concatenation?
10. Why is String concatenation inside loops expensive?
11. `replace()` vs `replaceAll()`?
12. `isEmpty()` vs `isBlank()`?
13. `String.valueOf()` vs `toString()`?
14. Compile-time vs runtime concatenation?
15. How does String hashing work conceptually?

### Senior

16. How does String immutability help security?
17. Why is immutability useful for concurrency?
18. Explain String Pool behavior.
19. What are the risks of excessive `intern()` usage?
20. How would you process a 500 MB text file?
21. How would you optimize repeated String construction?
22. Why should ordering be consistent with equals for sorted collections?
23. How would you design efficient cache keys?
24. How would you handle very large text data?
25. How would you troubleshoot excessive String allocation in production?

---

# 36. Must-Know String DSA Problems

1. Reverse String
2. Check Palindrome
3. First Non-Repeating Character
4. Remove Duplicate Characters
5. Anagram
6. String Compression
7. Longest Substring Without Repeating Characters
8. Longest Palindromic Substring
9. Minimum Window Substring
10. Group Anagrams
11. Valid Parentheses
12. Longest Common Prefix
13. Reverse Words in a String
14. Compare Version Numbers
15. String-to-Integer (`atoi`) style problem

For each problem, be ready to explain:

```text
Brute Force
    ↓
Optimized Approach
    ↓
Data Structure
    ↓
Time Complexity
    ↓
Space Complexity
    ↓
Edge Cases
    ↓
Production Considerations
```

---

# 37. Golden Interview Answers

### Why is String immutable?

> String is immutable primarily because it enables safe sharing through the String Pool, provides stable values for hashing and collection keys, improves security for values such as paths and configuration, and makes String instances safely shareable across threads.

### Why StringBuilder?

> String is immutable, so repeated concatenation can create many intermediate Strings. StringBuilder provides a mutable character sequence and is generally preferred for repeated construction within a thread.

### Why not `==`?

> `==` checks reference identity, whereas String's `equals()` checks content equality. For String values, we normally use `equals()`.

### Why can TreeSet discard unequal objects?

> TreeSet uses natural ordering or its Comparator to determine equivalence for sorted-set operations. If comparison returns zero, the elements are treated as equivalent even if `equals()` would return false.

---

# 38. Final Cheat Sheet

```text
String
├── Immutable
├── final
├── CharSequence
├── String Pool
├── equals() → content
├── == → reference
├── hashCode() → content-based
└── safe to share because immutable

StringBuilder
├── Mutable
├── Not synchronized
├── Preferred for most local construction
└── append() modifies builder

StringBuffer
├── Mutable
├── Synchronized methods
└── Use when shared mutable buffer synchronization is actually required

String Pool
├── String literals can be pooled
├── "Java" == "Java" → true
├── new String("Java") creates another object
└── intern() → canonical pooled representation

HashSet
└── hashCode() + equals()

TreeSet
└── compareTo() / Comparator

Common String DSA
├── Two pointers
├── Sliding window
├── Frequency Map
├── HashSet
├── Sorting
└── StringBuilder
```
