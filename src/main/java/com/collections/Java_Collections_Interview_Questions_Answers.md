# Java Collections — Interview Questions & Answers

## Target Level
Java Backend Developer — 5–6 years experience

This guide covers the Java Collections topics most useful for backend interviews: fundamentals, internal working, complexity, coding, concurrency, and scenario-based questions.

---

# 1. Collection Framework Basics

## Q1. What is the Java Collection Framework?

### Answer

The Java Collection Framework is a set of interfaces, implementations and utility algorithms used to store and manipulate groups of objects.

Main interfaces:

```text
Iterable
   |
Collection
   |
   +-- List
   +-- Set
   +-- Queue
        |
        +-- Deque

Map is separate from Collection
```

Important implementations:

```text
List  → ArrayList, LinkedList, Vector
Set   → HashSet, LinkedHashSet, TreeSet
Queue → PriorityQueue, LinkedList
Deque → ArrayDeque, LinkedList
Map   → HashMap, LinkedHashMap, TreeMap, Hashtable, ConcurrentHashMap
```

---

## Q2. Collection vs Collections?

### Answer

`Collection` is an interface.

```java
Collection<String> names;
```

`Collections` is a utility class containing static methods.

```java
Collections.sort(list);
Collections.reverse(list);
Collections.max(list);
Collections.min(list);
```

Memory trick:

```text
Collection  → interface
Collections → utility class
```

---

## Q3. Collection vs Map?

### Answer

`Collection` stores individual elements.

```text
List
Set
Queue
```

`Map` stores key-value pairs.

```text
Map<K, V>
```

Also, `Map` does **not** extend `Collection`.

---

## Q4. List vs Set vs Map?

### Answer

| Type | Duplicate | Ordering | Example |
|---|---|---|---|
| List | Allowed | Usually insertion/index order | ArrayList |
| Set | Not allowed | Depends on implementation | HashSet |
| Map | Keys unique | Depends on implementation | HashMap |

---

## Q5. Which collection should I choose?

### Answer

```text
Need index/order + duplicates
        → ArrayList

Need fast lookup + unique elements
        → HashSet

Need insertion order + unique elements
        → LinkedHashSet

Need sorted unique elements
        → TreeSet

Need key-value lookup
        → HashMap

Need insertion-ordered key-value pairs
        → LinkedHashMap

Need sorted keys
        → TreeMap

Need priority-based processing
        → PriorityQueue

Need thread-safe key-value operations
        → ConcurrentHashMap
```

---

# 2. ArrayList

## Q6. What is ArrayList?

### Answer

`ArrayList` is a resizable-array implementation of `List`.

It provides:

- fast random access
- insertion order
- duplicates
- one `null` element or multiple nulls
- non-synchronized operations

Example:

```java
List<String> names = new ArrayList<>();

names.add("Vivek");
names.add("Rahul");
names.add("Vivek");
```

---

## Q7. How does ArrayList work internally?

### Answer

Conceptually:

```text
ArrayList
    |
Object[] elementData
    |
+-----+-----+-----+-----+
|  A  |  B  |  C  |     |
+-----+-----+-----+-----+
```

When capacity is insufficient, ArrayList creates a larger array and copies the existing elements.

---

## Q8. What is the time complexity of ArrayList operations?

### Answer

| Operation | Average |
|---|---:|
| get(index) | O(1) |
| set(index) | O(1) |
| add(element) | O(1) amortized |
| add(index, element) | O(n) |
| remove(index) | O(n) |
| contains() | O(n) |
| iteration | O(n) |

Why is `add()` amortized O(1)?

Most insertions don't require resizing. Occasionally resizing requires copying elements, but averaged over many insertions, append is amortized O(1).

---

## Q9. Why is ArrayList faster than LinkedList for random access?

### Answer

ArrayList uses an array:

```java
list.get(500);
```

The JVM can directly access the corresponding array index.

LinkedList must traverse nodes to reach an index.

Therefore:

```text
ArrayList.get(index) → O(1)
LinkedList.get(index) → O(n)
```

---

# 3. LinkedList

## Q10. How does LinkedList work internally?

### Answer

Java's `LinkedList` is a doubly linked list.

Conceptually:

```text
null ← Node A ⇄ Node B ⇄ Node C → null
```

Each node contains links to the previous and next node.

---

## Q11. ArrayList vs LinkedList?

### Answer

| Feature | ArrayList | LinkedList |
|---|---|---|
| Internal structure | Dynamic array | Doubly linked list |
| get(index) | O(1) | O(n) |
| Append | O(1) amortized | O(1) |
| Insert/remove at known linked-list position | O(n) shift | O(1) after node is located |
| Memory | Lower | Higher |
| Cache locality | Better | Worse |

Important interview point:

> LinkedList is not automatically faster for insertion. If you first need to find the position by index, finding it is O(n).

---

# 4. Vector and Stack

## Q12. What is Vector?

### Answer

`Vector` is a legacy dynamic-array implementation.

Its methods are synchronized, so it generally has more synchronization overhead than `ArrayList`.

For modern applications, `ArrayList` is usually preferred when external synchronization is not required.

---

## Q13. Vector vs ArrayList?

### Answer

```text
ArrayList → not synchronized
Vector    → synchronized legacy collection
```

For thread-safe use cases, prefer choosing an appropriate concurrent collection or explicit synchronization based on the requirement.

---

## Q14. What is Stack?

### Answer

`Stack` is a legacy class extending `Vector`.

It provides LIFO operations:

```text
push()
pop()
peek()
```

For modern code, prefer:

```java
Deque<Integer> stack = new ArrayDeque<>();
```

Example:

```java
stack.push(10);
stack.push(20);

System.out.println(stack.pop()); // 20
```

---

# 5. HashSet

## Q15. What is HashSet?

### Answer

`HashSet` stores unique elements and is backed by a `HashMap`.

```java
Set<String> set = new HashSet<>();

set.add("Java");
set.add("Python");
set.add("Java");
```

The second `"Java"` is not added.

---

## Q16. How does HashSet guarantee uniqueness?

### Answer

HashSet internally uses HashMap.

Conceptually:

```text
HashSet.add(element)
       ↓
HashMap.put(element, PRESENT)
       ↓
hashCode()
       ↓
bucket
       ↓
equals()
```

Therefore `equals()` and `hashCode()` are important for custom objects.

---

## Q17. Can HashSet contain null?

### Answer

Yes. A HashSet can contain one `null` element.

---

## Q18. HashSet vs LinkedHashSet vs TreeSet?

### Answer

```text
HashSet
→ unique
→ no guaranteed iteration order
→ average O(1) basic operations

LinkedHashSet
→ unique
→ insertion order
→ average O(1) basic operations

TreeSet
→ unique
→ sorted order
→ O(log n) basic operations
```

---

# 6. TreeSet

## Q19. How does TreeSet work internally?

### Answer

`TreeSet` is backed by a `TreeMap`.

It maintains elements in sorted order using a tree-based structure.

Conceptually:

```text
        50
       /  \
     30    70
    / \    / \
  20 40  60 80
```

---

## Q20. Comparable vs Comparator in TreeSet?

### Answer

`Comparable` defines the object's natural ordering.

```java
class Employee implements Comparable<Employee> {
    public int compareTo(Employee other) {
        return this.age - other.age;
    }
}
```

`Comparator` provides an external/custom ordering.

```java
Comparator<Employee> byName =
    Comparator.comparing(Employee::getName);
```

---

## Q21. Important TreeSet interview trap?

### Answer

TreeSet uses comparison to determine ordering and uniqueness.

If:

```java
compareTo() == 0
```

the TreeSet can treat two objects as duplicates even if `equals()` returns `false`.

Therefore comparison logic must be designed carefully.

---

# 7. HashMap

## Q22. What is HashMap?

### Answer

`HashMap<K,V>` stores key-value pairs.

Example:

```java
Map<Integer, String> map = new HashMap<>();

map.put(101, "Vivek");
map.put(102, "Rahul");
```

Keys are unique. Values can be duplicated.

---

## Q23. Can HashMap contain null?

### Answer

HashMap allows:

```text
One null key
Multiple null values
```

Example:

```java
Map<String, String> map = new HashMap<>();

map.put(null, "A");
map.put("Java", null);
map.put("Spring", null);
```

---

## Q24. How does HashMap work internally?

### Answer

Simplified lookup flow:

```text
put(key, value)
      ↓
key.hashCode()
      ↓
hash calculation
      ↓
bucket index
      ↓
check bucket
      ↓
equals() if needed
      ↓
insert/update Node
```

For lookup:

```text
get(key)
  ↓
hash
  ↓
bucket
  ↓
compare hash/key
  ↓
return value
```

---

## Q25. Why are equals() and hashCode() important for HashMap?

### Answer

HashMap first uses the hash to find a candidate bucket and then uses equality to identify the correct key.

Contract:

```text
If a.equals(b) == true
then
a.hashCode() == b.hashCode()
```

Example:

```java
class Employee {
    String name;
    int age;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Employee)) return false;

        Employee e = (Employee) o;

        return age == e.age &&
               Objects.equals(name, e.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, age);
    }
}
```

---

## Q26. What happens when two keys have the same hash?

### Answer

That is a collision.

Multiple entries can belong to the same bucket.

HashMap uses equality checks to distinguish keys.

Modern Java HashMap can use a tree structure for sufficiently large collision-heavy buckets.

---

## Q27. What is HashMap treeification?

### Answer

In modern Java implementations, a heavily populated bucket can be converted from a linked structure to a tree structure once implementation thresholds are reached and the table is sufficiently large.

The goal is to improve lookup behavior under heavy collisions.

Interview answer:

> HashMap can treeify a collision-heavy bucket, changing it from a linked-node structure to a red-black tree under the implementation's thresholds.

Do not memorize only the threshold numbers; understand the reason: handling heavy collisions more efficiently.

---

## Q28. What happens if you mutate a HashMap key after insertion?

### Answer

This is dangerous.

Example:

```java
Employee e = new Employee("Vivek", 30);

map.put(e, "Developer");

e.setName("Rahul");

map.get(e);
```

If `hashCode()` depends on `name`, the key may now map to a different bucket.

The original entry still exists, but lookup may fail.

Rule:

> Don't mutate fields used by `equals()` and `hashCode()` while an object is being used as a HashMap key.

---

## Q29. What happens when putting the same logical key twice?

### Answer

```java
map.put("Java", 10);
map.put("Java", 20);
```

The second operation updates the value:

```text
Java → 20
```

The map still contains one key.

---

## Q30. Does HashMap use values during get()?

### Answer

No.

Lookup is based on the key.

```java
map.get(key);
```

The value is returned after the matching key is found.

---

# 8. LinkedHashMap

## Q31. What is LinkedHashMap?

### Answer

`LinkedHashMap` extends HashMap behavior by maintaining a linked ordering of entries.

By default it maintains insertion order.

```java
Map<String, Integer> map =
    new LinkedHashMap<>();

map.put("A", 1);
map.put("B", 2);
map.put("C", 3);
```

Iteration:

```text
A
B
C
```

---

## Q32. Can LinkedHashMap maintain access order?

### Answer

Yes.

```java
LinkedHashMap<K,V> map =
    new LinkedHashMap<>(16, 0.75f, true);
```

The last `true` enables access-order behavior.

This makes LinkedHashMap useful for implementing simple LRU-style caches.

---

# 9. TreeMap

## Q33. What is TreeMap?

### Answer

`TreeMap` stores key-value pairs sorted by key.

It is tree-based.

Basic operations are generally:

```text
get → O(log n)
put → O(log n)
remove → O(log n)
```

---

## Q34. HashMap vs TreeMap?

### Answer

| Feature | HashMap | TreeMap |
|---|---|---|
| Ordering | No guaranteed order | Sorted by key |
| Basic lookup | Average O(1) | O(log n) |
| Null key | Allows one | Natural ordering generally does not allow null key |
| Use case | Fast lookup | Sorted keys/range operations |

---

## Q35. When would you use TreeMap?

### Answer

Use TreeMap when you need:

- sorted keys
- range queries
- first/last key
- floor/ceiling operations
- navigation around keys

Example:

```java
treeMap.floorKey(50);
treeMap.ceilingKey(50);
```

---

# 10. Queue and Deque

## Q36. What is Queue?

### Answer

Queue generally follows FIFO:

```text
First In
   ↓
First Out
```

Example:

```java
Queue<String> queue = new LinkedList<>();

queue.offer("A");
queue.offer("B");

queue.poll(); // A
```

---

## Q37. What is PriorityQueue?

### Answer

`PriorityQueue` processes elements according to priority rather than insertion order.

Example:

```java
PriorityQueue<Integer> pq =
    new PriorityQueue<>();

pq.offer(30);
pq.offer(10);
pq.offer(20);

System.out.println(pq.poll()); // 10
```

Important:

> Iterating over a PriorityQueue does not guarantee sorted output. `poll()` retrieves the highest-priority element according to the queue's ordering.

---

## Q38. What is Deque?

### Answer

Deque means **Double Ended Queue**.

You can add/remove from both ends.

```java
Deque<Integer> deque =
    new ArrayDeque<>();

deque.addFirst(10);
deque.addLast(20);

deque.removeFirst();
deque.removeLast();
```

---

## Q39. How can ArrayDeque be used as a Stack?

### Answer

```java
Deque<Integer> stack =
    new ArrayDeque<>();

stack.push(10);
stack.push(20);

System.out.println(stack.pop()); // 20
```

This is preferred over the legacy `Stack` class for many modern use cases.

---

# 11. Iterator

## Q40. What is Iterator?

### Answer

`Iterator` provides a standard way to traverse a collection.

```java
Iterator<String> iterator =
    list.iterator();

while (iterator.hasNext()) {
    System.out.println(iterator.next());
}
```

---

## Q41. How do you safely remove while iterating?

### Answer

Use `Iterator.remove()`:

```java
Iterator<Integer> iterator =
    list.iterator();

while (iterator.hasNext()) {

    Integer value = iterator.next();

    if (value < 10) {
        iterator.remove();
    }
}
```

Avoid structurally modifying the collection directly during normal iteration:

```java
for (Integer value : list) {
    if (value < 10) {
        list.remove(value); // dangerous
    }
}
```

---

## Q42. What is ListIterator?

### Answer

`ListIterator` is specifically for lists and supports:

- forward traversal
- backward traversal
- `add()`
- `set()`
- `remove()`

Example:

```java
ListIterator<String> iterator =
    list.listIterator();
```

---

# 12. Fail-Fast and Concurrent Iteration

## Q43. What is fail-fast iteration?

### Answer

Many standard collection iterators detect structural modification during iteration and may throw `ConcurrentModificationException`.

Example:

```java
for (String name : list) {
    list.add("New");
}
```

This can cause:

```text
ConcurrentModificationException
```

It is a best-effort detection mechanism, not a synchronization guarantee.

---

## Q44. How is ConcurrentHashMap iteration different?

### Answer

ConcurrentHashMap supports concurrent access and its iterators are weakly consistent.

They:

- do not normally throw `ConcurrentModificationException` merely because other threads modify the map
- may reflect some modifications made during iteration
- do not represent a frozen snapshot

---

# 13. Comparable and Comparator

## Q45. Comparable vs Comparator?

### Answer

### Comparable

Defines natural ordering inside the class:

```java
class Employee
        implements Comparable<Employee> {

    public int compareTo(Employee other) {
        return Integer.compare(
            this.age,
            other.age
        );
    }
}
```

Usage:

```java
Collections.sort(employees);
```

### Comparator

Defines external/custom ordering:

```java
employees.sort(
    Comparator.comparing(Employee::getName)
);
```

Memory trick:

```text
Comparable → compareTo() → inside class
Comparator → compare()  → outside class
```

---

# 14. equals() and hashCode()

## Q46. What is the equals/hashCode contract?

### Answer

If:

```java
a.equals(b) == true
```

then:

```java
a.hashCode() == b.hashCode()
```

But the reverse is not required.

```text
same hash → may be equal or may collide
equal     → must have same hash
```

---

## Q47. Why should equals and hashCode use the same identity fields?

### Answer

Suppose equality uses:

```text
name + age
```

but hashCode uses only:

```text
name
```

It can still satisfy the basic contract because equal objects have the same name and therefore same hash, but the hash distribution may be poorer.

More importantly, if equality uses a field that hashCode ignores in a way that creates inconsistent behavior, the implementation becomes harder to reason about.

Best practice:

> Use the same logical identity fields consistently in both methods.

---

# 15. ConcurrentHashMap

## Q48. What is ConcurrentHashMap?

### Answer

`ConcurrentHashMap` is a thread-safe implementation of `ConcurrentMap`.

It allows concurrent access without synchronizing the entire map for every operation.

```java
ConcurrentHashMap<String, Integer> map =
    new ConcurrentHashMap<>();
```

---

## Q49. Can ConcurrentHashMap contain null?

### Answer

No.

It does not allow:

```text
null key
null value
```

Example:

```java
map.put(null, 10); // NullPointerException
```

---

## Q50. Why doesn't ConcurrentHashMap allow null?

### Answer

In concurrent code, a `null` return from `get()` needs an unambiguous meaning.

```java
map.get(key)
```

can mean:

```text
key doesn't exist
```

If null values were allowed, it could also mean:

```text
key exists with null value
```

Disallowing null avoids this ambiguity.

---

## Q51. Important atomic methods of ConcurrentHashMap?

### Answer

Know these particularly well:

```java
putIfAbsent()
remove(key, value)
replace(key, value)
replace(key, oldValue, newValue)
computeIfAbsent()
computeIfPresent()
merge()
```

---

## Q52. Explain putIfAbsent()

### Answer

```java
map.putIfAbsent("Java", 10);
```

If Java doesn't exist:

```text
Java → 10
```

If Java already exists:

```text
existing value remains unchanged
```

It is useful for atomic initialization.

---

## Q53. Explain remove(key, value)

### Answer

```java
map.remove("Java", 10);
```

It removes the entry only if the current value is also `10`.

Useful for conditional atomic removal.

---

## Q54. Explain replace(key, oldValue, newValue)

### Answer

```java
map.replace("Java", 10, 20);
```

The replacement happens only if the current value is `10`.

This avoids a separate:

```text
get → compare → put
```

sequence.

---

## Q55. Explain computeIfAbsent()

### Answer

```java
map.computeIfAbsent(
    "Java",
    key -> expensiveCalculation()
);
```

The function is executed when the key is absent.

Common use:

```java
map.computeIfAbsent(
    skill,
    key -> new ArrayList<>()
).add(employee);
```

This is useful for grouping values.

---

## Q56. Explain merge()

### Answer

```java
map.merge(
    "Java",
    1,
    Integer::sum
);
```

If absent:

```text
Java → 1
```

If present with 3:

```text
Java → 4
```

Common use: frequency counting.

---

# 16. Synchronized Collections vs Concurrent Collections

## Q57. Collections.synchronizedList() vs CopyOnWriteArrayList?

### Answer

`synchronizedList()` synchronizes operations on a wrapper.

```java
List<String> list =
    Collections.synchronizedList(
        new ArrayList<>()
    );
```

`CopyOnWriteArrayList` creates a new underlying array for structural writes.

It is useful when:

```text
Reads >> Writes
```

because readers can iterate without locking the same way ordinary synchronized lists require.

---

## Q58. Why is CopyOnWriteArrayList expensive for writes?

### Answer

Each structural modification creates a new copy of the underlying array.

Therefore it is appropriate when:

```text
Many reads
Few writes
```

not when the collection is frequently modified.

---

# 17. Immutable and Unmodifiable Collections

## Q59. Collections.unmodifiableList() vs List.of()?

### Answer

`Collections.unmodifiableList(list)` creates an unmodifiable **view** over an existing list.

If the original list changes, the view can reflect those changes.

```java
List<String> original =
    new ArrayList<>();

List<String> view =
    Collections.unmodifiableList(original);
```

`List.of()` creates an immutable collection instance.

```java
List<String> list =
    List.of("A", "B");
```

It does not allow structural modification and does not allow null elements.

---

## Q60. How do you create an immutable list?

### Answer

```java
List<String> list =
    List.of("Java", "Spring", "Kafka");
```

Or:

```java
List<String> immutable =
    List.copyOf(original);
```

---

# 18. Generics and Collections

## Q61. Why should we use generics with collections?

### Answer

Generics provide compile-time type safety.

Without generics:

```java
List list = new ArrayList();
list.add("Java");
list.add(10);
```

With generics:

```java
List<String> list =
    new ArrayList<>();

list.add("Java");
// list.add(10); // compile error
```

Benefits:

- type safety
- fewer casts
- clearer code

---

## Q62. What is <? extends T>?

### Answer

It represents an unknown subtype of `T`.

Example:

```java
List<? extends Number> numbers;
```

This can refer to:

```text
List<Integer>
List<Double>
List<Float>
```

You can safely read values as `Number`, but you generally cannot add arbitrary `Number` values.

Memory:

```text
extends → producer / read
```

---

## Q63. What is <? super T>?

### Answer

It represents an unknown supertype of `T`.

```java
List<? super Integer> list;
```

Could be:

```text
List<Integer>
List<Number>
List<Object>
```

You can safely add `Integer`.

Memory:

```text
super → consumer / write
```

This is the **PECS** rule:

```text
Producer Extends
Consumer Super
```

---

# 19. Collection Complexity Cheat Sheet

| Collection | get | add | remove | contains | Ordering |
|---|---:|---:|---:|---:|---|
| ArrayList | O(1) | O(1)* | O(n) | O(n) | Insertion |
| LinkedList | O(n) | O(1)** | O(1)** | O(n) | Insertion |
| HashSet | — | O(1)* | O(1)* | O(1)* | No guarantee |
| LinkedHashSet | — | O(1)* | O(1)* | O(1)* | Insertion |
| TreeSet | — | O(log n) | O(log n) | O(log n) | Sorted |
| HashMap | — | O(1)* | O(1)* | O(1)* | No guarantee |
| LinkedHashMap | — | O(1)* | O(1)* | O(1)* | Insertion/access |
| TreeMap | — | O(log n) | O(log n) | O(log n) | Sorted |
| PriorityQueue | — | O(log n) | O(log n) | O(n) | Priority at head |

`*` Average/amortized where applicable.

`**` O(1) after the relevant node/position is already known; finding an indexed position can be O(n).

---

# 20. Coding Questions

## Q64. Remove duplicates from a List

Input:

```text
[10, 20, 10, 30, 20, 40]
```

### Answer

Simple approach:

```java
List<Integer> numbers =
    Arrays.asList(10, 20, 10, 30, 20, 40);

List<Integer> unique =
    new ArrayList<>(new LinkedHashSet<>(numbers));

System.out.println(unique);
```

Output:

```text
[10, 20, 30, 40]
```

`LinkedHashSet` removes duplicates while preserving insertion order.

---

## Q65. Find duplicate elements in a List

### Answer

```java
Set<Integer> seen = new HashSet<>();
Set<Integer> duplicates = new HashSet<>();

for (Integer number : numbers) {

    if (!seen.add(number)) {
        duplicates.add(number);
    }
}

System.out.println(duplicates);
```

---

## Q66. Find frequency of each element

### Answer

```java
Map<Integer, Integer> frequency =
    new HashMap<>();

for (Integer number : numbers) {
    frequency.merge(number, 1, Integer::sum);
}

System.out.println(frequency);
```

---

## Q67. Find first non-repeating element

Input:

```text
[4, 5, 1, 4, 5, 2]
```

### Answer

```java
Map<Integer, Integer> frequency =
    new LinkedHashMap<>();

for (Integer number : numbers) {
    frequency.merge(number, 1, Integer::sum);
}

for (Map.Entry<Integer, Integer> entry :
        frequency.entrySet()) {

    if (entry.getValue() == 1) {
        System.out.println(entry.getKey());
        break;
    }
}
```

Output:

```text
1
```

Why LinkedHashMap?

Because we need to preserve the original insertion order.

---

## Q68. Sort employees by salary

### Answer

```java
employees.sort(
    Comparator.comparing(Employee::getSalary)
);
```

Descending:

```java
employees.sort(
    Comparator.comparing(Employee::getSalary)
              .reversed()
);
```

---

## Q69. Sort employees by salary, then name

### Answer

```java
employees.sort(
    Comparator.comparing(Employee::getSalary)
              .thenComparing(Employee::getName)
);
```

---

## Q70. Group employees by skill

### Answer

```java
Map<String, List<Employee>> grouped =
    employees.stream()
        .collect(Collectors.groupingBy(
            Employee::getSkill
        ));
```

---

## Q71. Find highest-paid employee

### Answer

```java
Optional<Employee> employee =
    employees.stream()
        .max(Comparator.comparing(Employee::getSalary));
```

---

## Q72. Convert List<Employee> to Map<Id, Employee>

### Answer

```java
Map<Integer, Employee> map =
    employees.stream()
        .collect(Collectors.toMap(
            Employee::getId,
            Function.identity()
        ));
```

If duplicate IDs are possible, provide a merge function:

```java
Map<Integer, Employee> map =
    employees.stream()
        .collect(Collectors.toMap(
            Employee::getId,
            Function.identity(),
            (oldValue, newValue) -> newValue
        ));
```

---

# 21. Scenario-Based Questions

## Q73. You need fast lookup by employee ID. Which collection?

### Answer

Use:

```java
Map<Integer, Employee>
```

Usually:

```java
HashMap<Integer, Employee>
```

if ordering and concurrency are not requirements.

---

## Q74. You need unique employees and insertion order.

### Answer

Use:

```java
LinkedHashSet<Employee>
```

assuming `equals()` and `hashCode()` correctly represent employee identity.

---

## Q75. You need unique employees sorted by salary.

### Answer

Potentially use:

```java
TreeSet<Employee>
```

with a comparator.

But be careful: if the comparator returns `0` for employees with different identities, TreeSet treats them as duplicates.

---

## Q76. You need sorted employee IDs and range queries.

### Answer

Use:

```java
TreeMap<Integer, Employee>
```

Useful methods include:

```java
firstKey()
lastKey()
floorKey()
ceilingKey()
subMap()
headMap()
tailMap()
```

---

## Q77. You need a cache that evicts the least recently used item.

### Answer

A simple LRU-style design can use access-order `LinkedHashMap`.

```java
LinkedHashMap<String, String> cache =
    new LinkedHashMap<>(
        16,
        0.75f,
        true
    );
```

Override `removeEldestEntry()` to enforce a maximum size.

---

# 22. Multithreading + Collections

## Q78. Why is HashMap not thread-safe?

### Answer

Concurrent modifications can cause inconsistent state and unsafe visibility/coordination.

For concurrent access, choose an appropriate solution such as:

```text
ConcurrentHashMap
Collections.synchronizedMap()
explicit locking
```

depending on requirements.

---

## Q79. HashMap vs ConcurrentHashMap?

### Answer

```text
HashMap
→ not thread-safe
→ allows null key/value
→ good for single-threaded or externally synchronized use

ConcurrentHashMap
→ designed for concurrent access
→ does not allow null keys/values
→ provides atomic compound operations
```

---

## Q80. Why not just synchronize the entire HashMap?

### Answer

You can use:

```java
Collections.synchronizedMap(...)
```

but coarse-grained synchronization can reduce concurrency.

`ConcurrentHashMap` is designed to allow higher concurrency for many operations and provides useful atomic methods.

---

## Q81. How would you safely count events from multiple threads?

### Answer

Use `ConcurrentHashMap.merge()`:

```java
ConcurrentHashMap<String, Integer> counts =
    new ConcurrentHashMap<>();

counts.merge("LOGIN", 1, Integer::sum);
```

This performs the update atomically for the map operation.

---

## Q82. How would multiple threads group employees by skill?

### Answer

One approach is:

```java
ConcurrentHashMap<String, List<Employee>> map =
    new ConcurrentHashMap<>();

map.computeIfAbsent(
    employee.getSkill(),
    key -> Collections.synchronizedList(
        new ArrayList<>()
    )
).add(employee);
```

But the correct design depends on the complete concurrency requirement. A concurrent outer map does not automatically make a normal `ArrayList` thread-safe.

---

# 23. Important Tricky Questions

## Q83. Why does HashMap require immutable keys?

### Answer

It does not technically require the key object to be immutable, but keys should behave immutably with respect to fields used by `equals()` and `hashCode()`.

Otherwise mutation can make the entry difficult or impossible to retrieve.

---

## Q84. Can two different keys have the same hashCode?

### Answer

Yes.

This is called a collision.

```text
key1.hashCode() == key2.hashCode()
```

does not mean:

```text
key1.equals(key2)
```

must be true.

---

## Q85. Can equal objects have different hashCodes?

### Answer

No.

If:

```java
a.equals(b)
```

is true, then:

```java
a.hashCode() == b.hashCode()
```

must be true.

---

## Q86. Does HashSet maintain insertion order?

### Answer

No guarantee.

If insertion order matters, use:

```java
LinkedHashSet
```

---

## Q87. Does HashMap maintain insertion order?

### Answer

No guaranteed insertion order.

If you need insertion order, use:

```java
LinkedHashMap
```

---

## Q88. Does PriorityQueue return sorted data when iterated?

### Answer

No.

Only the head element is guaranteed according to the queue's ordering.

Use repeated:

```java
poll()
```

to retrieve elements in priority order.

---

## Q89. Can we store duplicate keys in HashMap?

### Answer

No.

A key is unique.

Putting the same logical key again replaces its value.

---

## Q90. Can we store duplicate values in HashMap?

### Answer

Yes.

```java
map.put(1, "Java");
map.put(2, "Java");
```

Both entries are valid.

---

# 24. Production-Level Questions

## Q91. How would you choose a collection in production?

### Answer

Consider:

```text
1. Duplicate requirement
2. Ordering requirement
3. Sorting requirement
4. Lookup requirement
5. Insert/delete pattern
6. Memory usage
7. Thread safety
8. Concurrency level
9. Read/write ratio
10. Need for range queries
```

Don't choose a collection only because it is familiar.

---

## Q92. ArrayList or LinkedList for a large list?

### Answer

Usually start with `ArrayList` unless there is a demonstrated need for linked-list behavior.

Reasons:

- O(1) indexed access
- better cache locality
- lower per-element memory overhead
- efficient iteration

`LinkedList` should be chosen for a specific access/update pattern, not merely because "insert/delete is O(1)."

---

## Q93. HashMap or ConcurrentHashMap in a Spring Boot application?

### Answer

It depends on ownership and concurrency.

If the map is confined to one request/thread:

```java
HashMap
```

may be enough.

If it is shared mutable state accessed concurrently:

```java
ConcurrentHashMap
```

may be appropriate.

But don't use a concurrent collection as a substitute for understanding the complete consistency requirement.

---

# 25. Rapid-Fire Revision

## Q94. Which List gives O(1) indexed access?

**Answer:** ArrayList.

## Q95. Which Set maintains insertion order?

**Answer:** LinkedHashSet.

## Q96. Which Set maintains sorted order?

**Answer:** TreeSet.

## Q97. Which Map maintains insertion order?

**Answer:** LinkedHashMap.

## Q98. Which Map maintains sorted keys?

**Answer:** TreeMap.

## Q99. Which Map is designed for concurrent access?

**Answer:** ConcurrentHashMap.

## Q100. Which Queue is priority-based?

**Answer:** PriorityQueue.

## Q101. Modern replacement for Stack?

**Answer:** `Deque`, commonly `ArrayDeque`.

## Q102. Does Map extend Collection?

**Answer:** No.

## Q103. Does HashMap allow null?

**Answer:** Yes, one null key and multiple null values.

## Q104. Does ConcurrentHashMap allow null?

**Answer:** No.

## Q105. Does HashSet allow duplicates?

**Answer:** No.

## Q106. What does HashSet use internally?

**Answer:** HashMap.

## Q107. What does TreeSet use internally?

**Answer:** TreeMap.

## Q108. What does TreeMap generally use internally?

**Answer:** A tree-based sorted map structure.

## Q109. Comparable method?

**Answer:** `compareTo()`.

## Q110. Comparator method?

**Answer:** `compare()`.

## Q111. What is PECS?

**Answer:**
```text
Producer Extends
Consumer Super
```

## Q112. What happens when two HashMap keys have the same hash?

**Answer:** Collision handling occurs; equality is then used to distinguish keys.

## Q113. What happens if a HashMap key is mutated after insertion?

**Answer:** Lookup can fail if fields used by `hashCode()`/`equals()` changed.

---

# Final Collections Cheat Sheet

```text
                    Collection
                        |
          +-------------+-------------+
          |             |             |
         List           Set          Queue
          |             |             |
    +-----+-----+    +---+---+      PriorityQueue
    |           |    |       |      Deque
ArrayList   LinkedList HashSet
    |                   |
 Vector            LinkedHashSet
 Stack                  |
                      TreeSet


                     Map
                      |
          +-----------+-----------+
          |           |           |
       HashMap   LinkedHashMap  TreeMap
          |
  ConcurrentHashMap
```

## Selection Cheat Sheet

```text
Index access
    → ArrayList

Unique values
    → HashSet

Unique + insertion order
    → LinkedHashSet

Unique + sorted
    → TreeSet

Key → Value
    → HashMap

Key → Value + insertion order
    → LinkedHashMap

Key → Value + sorted keys
    → TreeMap

Concurrent key → value
    → ConcurrentHashMap

Priority processing
    → PriorityQueue

Stack
    → ArrayDeque

Double-ended operations
    → ArrayDeque
```

# ⭐ What You Should Master for 5–6 Year Interviews

## Must Know

```text
Collection hierarchy
List / Set / Map / Queue
ArrayList
LinkedList
HashSet
LinkedHashSet
TreeSet
HashMap
LinkedHashMap
TreeMap
ConcurrentHashMap
Iterator
Comparable vs Comparator
equals() + hashCode()
HashMap internal working
HashMap collision
Time complexity
Generics / PECS
Collections utility methods
```

## Very Important Coding

```text
Remove duplicates
Find duplicates
Frequency count
First non-repeating element
Sort objects
Multiple-level sorting
Group by field
Highest/lowest value
List → Map
Map → List
LRU cache
Concurrent frequency counting
Concurrent grouping
```

## Senior-Level Discussion

```text
HashMap internal working
Mutable HashMap key
HashMap collision/treeification
ConcurrentHashMap
Atomic map operations
CopyOnWriteArrayList
Large collection memory
Collection choice
Thread safety
Producer/consumer with collections
Ordering + concurrency
```

# Interview Follow-Up Checklist

For every collection question, be ready to explain:

```text
1. Why did you choose this collection?
2. How does it work internally?
3. What is the time complexity?
4. What is the memory overhead?
5. Does it maintain order?
6. Does it allow duplicates?
7. Does it allow null?
8. Is it thread-safe?
9. What happens under concurrent modification?
10. What happens in the worst case?
11. What alternative collection could you use?
12. Why is your chosen collection better for this use case?
```
