# Java Memory Management & Garbage Collection — Interview Questions & Answers

## How to use this guide

This guide is designed for Java backend interviews, especially for 4–7 years of experience.

Priority:
- ⭐⭐⭐ = Must know
- ⭐⭐ = Important
- ⭐ = Good to know

The guide covers:
- JVM memory structure
- Heap and Stack
- Metaspace
- Method area
- Object allocation
- References
- Garbage Collection
- GC roots
- Generational GC
- Young/Old generations
- Minor/Major/Full GC
- G1 GC
- ZGC
- Parallel GC
- Serial GC
- Stop-the-world
- Memory leaks
- OutOfMemoryError
- StackOverflowError
- JVM options
- GC logs and monitoring
- Tricky output/code questions
- Senior-level production scenarios

---

# 1. JVM Memory Basics

## Q1. What is JVM memory management? ⭐⭐⭐

JVM memory management is the process by which the JVM allocates memory for Java objects, manages execution memory, and automatically reclaims memory that is no longer reachable.

At a high level:

```text
Java Application
      |
      v
     JVM
      |
      +---- Stack
      |
      +---- Heap
      |
      +---- Metaspace
      |
      +---- Other native/JVM areas
```

The most important interview areas are:

```text
Heap → Objects
Stack → Method calls / local variables / frames
Metaspace → Class metadata
```

---

# 2. JVM Runtime Memory Areas

## Q2. What are the main memory areas in the JVM? ⭐⭐⭐

Important areas include:

```text
JVM Runtime Memory
|
+-- Heap
+-- Java Stacks
+-- PC Register
+-- Native Method Stack
+-- Method Area
     |
     +-- Metaspace in modern HotSpot JVMs
```

### Heap

Stores objects and arrays.

### Stack

Each thread has its own JVM stack containing stack frames for method invocations.

### PC Register

Each thread has its own program counter used to track the current JVM instruction.

### Native Method Stack

Used for native method execution.

### Method Area

A JVM specification memory area for class-level structures. In HotSpot, class metadata is stored in **Metaspace**.

---

# 3. Heap Memory

## Q3. What is heap memory? ⭐⭐⭐

Heap is the JVM memory area where objects and arrays are allocated.

Example:

```java
Employee emp = new Employee("Vivek");
```

Conceptually:

```text
Stack
+----------------+
| emp reference  |
+-------+--------+
        |
        v
Heap
+----------------------+
| Employee object      |
| name = "Vivek"       |
+----------------------+
```

The reference variable and the object are conceptually different things.

---

## Q4. Is the heap shared between threads?

Yes.

All Java threads generally share the heap.

```text
Thread 1 ----\
Thread 2 -----+----> Shared Heap
Thread 3 ----/
```

This is why shared mutable heap objects can require synchronization or thread-safe designs.

---

## Q5. What is `-Xms`?

`-Xms` specifies the initial Java heap size.

Example:

```bash
-Xms512m
```

This configures an initial heap size of approximately 512 MB.

---

## Q6. What is `-Xmx`?

`-Xmx` specifies the maximum Java heap size.

Example:

```bash
-Xmx2g
```

This sets the maximum Java heap to approximately 2 GB.

---

## Q7. Difference between Xms and Xmx?

```text
-Xms → initial heap size
-Xmx → maximum heap size
```

Example:

```bash
java -Xms512m -Xmx2g MyApplication
```

---

# 4. Stack Memory

## Q8. What is stack memory? ⭐⭐⭐

Each thread has its own JVM stack.

A stack contains frames for method invocations.

A frame can contain:
- local variables
- operand stack
- references
- return information
- other execution data

Example:

```java
public void method() {

    int x = 10;

    Employee emp = new Employee();
}
```

Conceptually:

```text
Thread Stack
+-----------------------+
| method() frame        |
| x = 10                |
| emp = reference ------+----> Heap Employee
+-----------------------+
```

---

## Q9. Is stack memory shared?

No.

Each thread has its own JVM stack.

```text
Thread 1 → Stack 1
Thread 2 → Stack 2
Thread 3 → Stack 3

All share → Heap
```

---

## Q10. What causes StackOverflowError? ⭐⭐⭐

Usually excessive or infinite recursion.

```java
public void test() {
    test();
}
```

Eventually:

```text
StackOverflowError
```

Each recursive call creates another stack frame.

```text
test()
  ↓
test()
  ↓
test()
  ↓
...
  ↓
StackOverflowError
```

---

## Q11. What is `-Xss`?

`-Xss` controls the thread stack size.

Example:

```bash
-Xss1m
```

A very small stack can make deep recursion fail sooner; increasing it can allow deeper call stacks but consumes more native memory per thread.

---

# 5. Stack vs Heap

## Q12. Stack vs Heap? ⭐⭐⭐

| Stack | Heap |
|---|---|
| Per-thread | Shared among threads |
| Stores stack frames/local execution data | Stores objects and arrays |
| Automatically managed with method calls/returns | Managed by GC |
| Usually smaller | Usually much larger |
| StackOverflowError possible | OutOfMemoryError possible |
| LIFO execution model | Object allocation area |

Interview memory:

```text
Stack → thread + method execution
Heap  → objects
```

---

# 6. References

## Q13. Where is a reference stored?

Consider:

```java
Employee emp = new Employee();
```

The `Employee` object is allocated on the heap.

The reference variable `emp` is part of the current execution context; if it is a local variable, it is stored in the current stack frame.

For instance fields:

```java
class Department {
    Employee employee;
}
```

the reference field is part of the `Department` object on the heap.

Important:

> Do not oversimplify this as "all references are always on the stack."

---

# 7. Object Allocation

## Q14. What happens when we execute `new Employee()`? ⭐⭐⭐

Conceptually:

```text
1. JVM evaluates the class/object creation request
2. Memory is allocated for the object
3. Fields receive default values
4. Constructor executes
5. Reference to the object is returned
```

Example:

```java
Employee e = new Employee("Vivek");
```

Conceptually:

```text
Heap:
Employee
name = null initially
      ↓
constructor
      ↓
name = "Vivek"
```

---

## Q15. What are default values for object fields?

Examples:

```text
int       → 0
long      → 0L
double    → 0.0
boolean   → false
char      → '\u0000'
reference → null
```

---

# 8. Young and Old Generation

## Q16. What is generational garbage collection? ⭐⭐⭐

Many JVM garbage collectors use the idea that most objects die young.

The heap can be conceptually divided into:

```text
Heap
|
+-- Young Generation
|    |
|    +-- Eden
|    +-- Survivor
|    +-- Survivor
|
+-- Old Generation
```

New objects are typically allocated in the young generation.

Objects that survive collections may eventually be promoted to the old generation.

---

## Q17. What is Eden?

Eden is the region where newly allocated objects are typically placed in generational collectors.

Example:

```text
Young Generation
|
+-- Eden
+-- Survivor
+-- Survivor
```

---

## Q18. What are Survivor spaces?

Survivor spaces are used for objects that survive young-generation collection.

Conceptually:

```text
Eden
  |
  | GC
  v
Survivor 0
  |
  | GC
  v
Survivor 1
  |
  v
Old Generation
```

The exact implementation depends on the collector.

---

## Q19. Why do objects move between survivor spaces?

During young-generation collection, live objects can be copied from Eden and one survivor region to another survivor region.

The surviving objects can accumulate age information and eventually be promoted to old generation depending on collector policy.

---

# 9. Garbage Collection

## Q20. What is Garbage Collection? ⭐⭐⭐

Garbage Collection is the JVM's automatic process for reclaiming memory occupied by objects that are no longer reachable.

Example:

```java
Employee e = new Employee();

e = null;
```

If no other live reference points to that object, it becomes eligible for GC.

```text
e
|
v
Employee

e = null

Employee
   X
No reachable reference
```

Important:

> Eligible for GC does not mean GC happens immediately.

---

## Q21. How does the JVM determine whether an object is garbage?

The JVM primarily determines liveness using reachability from **GC roots**.

If an object cannot be reached from the roots through references, it is eligible for collection.

---

# 10. GC Roots

## Q22. What are GC roots? ⭐⭐⭐

GC roots are special references from which the JVM determines object reachability.

Common examples include:
- local variables/references in active stack frames
- static references
- active Java threads
- JNI/native references
- certain JVM-internal references

Conceptually:

```text
GC Roots
   |
   +---- Object A
   |       |
   |       +---- Object B
   |
   +---- Object C
```

A disconnected object graph can become eligible for GC.

---

## Q23. Can circular references cause a memory leak?

Circular references alone do not prevent garbage collection.

Example:

```java
class A {
    B b;
}

class B {
    A a;
}
```

If nothing outside the cycle can reach either object:

```text
GC Roots

   X

A <----> B
```

Both are unreachable and can be collected.

This is a common interview trap.

---

# 11. Reachability

## Q24. What are the basic reachability concepts?

Conceptually, Java references can be discussed as:

```text
Strongly reachable
Softly reachable
Weakly reachable
Phantom reachable
```

The normal reference:

```java
Employee e = new Employee();
```

is a strong reference.

Special reference types include:

```java
SoftReference
WeakReference
PhantomReference
```

---

# 12. Strong Reference

## Q25. What is a strong reference?

A normal Java reference is a strong reference.

```java
Employee employee = new Employee();
```

As long as a strong path from a GC root reaches the object, normal GC will not reclaim it.

---

# 13. WeakReference

## Q26. What is WeakReference? ⭐⭐

A `WeakReference` does not keep an object strongly reachable.

Example:

```java
WeakReference<Employee> ref =
    new WeakReference<>(new Employee());
```

After the object becomes otherwise unreachable, it may be reclaimed during GC.

Weak references are commonly associated with caches or structures where entries should not prevent collection.

---

# 14. SoftReference

## Q27. What is SoftReference?

A soft reference is intended for objects that may be reclaimed when the JVM needs memory.

It has historically been used for memory-sensitive caches.

Modern applications should choose caching strategies carefully rather than assuming `SoftReference` is an ideal general-purpose cache.

---

# 15. PhantomReference

## Q28. What is PhantomReference?

A `PhantomReference` is used for advanced post-mortem/reference-processing scenarios.

It does not provide normal access to the referent through `get()`; `get()` returns `null`.

It is associated with `ReferenceQueue` and can be useful for advanced resource/lifecycle tracking.

---

# 16. finalize()

## Q29. What is finalize()? ⭐⭐⭐

`finalize()` was a legacy mechanism associated with garbage collection and cleanup.

It is deprecated and should not be used for resource management.

Use:
- try-with-resources
- `AutoCloseable`
- explicit lifecycle management

instead.

---

# 17. Minor, Major and Full GC

## Q30. What is Minor GC? ⭐⭐⭐

Traditionally, a Minor GC refers to collection of the young generation.

It is generally more frequent and shorter than old-generation/full collections.

The exact terminology and behavior can vary by garbage collector.

---

## Q31. What is Major GC?

The term "Major GC" is commonly used for collection involving the old generation.

However, terminology is not completely standardized across JVM collectors.

In interviews, explain the collector being discussed rather than assuming every collector uses exactly the same phases.

---

## Q32. What is Full GC? ⭐⭐⭐

A Full GC generally means a broad collection involving the heap and often associated class metadata/related JVM work, depending on collector and JVM implementation.

It is usually more disruptive and expensive than normal young-generation collection.

---

# 18. Stop-the-World

## Q33. What is Stop-the-World (STW)? ⭐⭐⭐

Stop-the-World means application threads are paused while the JVM performs certain operations.

Conceptually:

```text
Application Threads
      |
      | running
      v
      X  paused
      |
      | GC / JVM operation
      v
      X
      |
      | resume
      v
    running
```

Modern collectors aim to reduce pause duration, but STW phases can still exist.

---

# 19. Garbage Collection Algorithms

## Q34. What are common GC approaches? ⭐⭐⭐

Historically and conceptually:

### Mark-Sweep

```text
1. Mark live objects
2. Sweep dead objects
```

Problem:

```text
Memory fragmentation
```

### Mark-Compact

```text
1. Mark live objects
2. Move/compact them
3. Reclaim free space
```

### Copying

Copy live objects from one region to another.

Useful for young-generation style collection.

---

# 20. G1 Garbage Collector

## Q35. What is G1 GC? ⭐⭐⭐

G1 (Garbage-First) is a region-based garbage collector designed to provide predictable pause targets while efficiently managing large heaps.

Instead of treating the heap simply as one contiguous young/old layout, G1 divides the heap into regions.

Conceptually:

```text
Heap
+----+----+----+----+----+
| R1 | R2 | R3 | R4 | R5 |
+----+----+----+----+----+
| R6 | R7 | R8 | R9 | R10|
+----+----+----+----+----+
```

Regions can play different roles over time.

---

## Q36. Why is G1 called Garbage-First?

G1 prioritizes regions expected to provide the most garbage to reclaim for a given amount of GC work.

So it attempts to collect regions with high reclaimable value first.

---

## Q37. What is a G1 mixed collection?

A mixed collection can collect:
- young regions
- selected old regions

The goal is to reclaim old-generation garbage without necessarily collecting the entire old generation at once.

---

## Q38. What is `-XX:MaxGCPauseMillis`?

It is a G1 tuning target indicating a desired maximum pause-time goal.

Example:

```bash
-XX:MaxGCPauseMillis=200
```

Important:

> It is a goal, not a hard guarantee.

---

# 21. ZGC

## Q39. What is ZGC? ⭐⭐

ZGC is a low-latency garbage collector designed to keep GC pauses very short, even with large heaps.

It performs much of its work concurrently with application execution.

Use cases include applications where low latency is a high priority.

---

# 22. Parallel GC

## Q40. What is Parallel GC?

Parallel GC uses multiple worker threads to perform garbage collection.

It is generally focused on throughput rather than extremely low latency.

---

# 23. Serial GC

## Q41. What is Serial GC?

Serial GC performs collection using a single GC thread.

It can be appropriate for small heaps or simple applications, but is usually not the first choice for large production backend services.

---

# 24. G1 vs ZGC vs Parallel

## Q42. Compare G1, ZGC and Parallel GC. ⭐⭐⭐

| Collector | Main Goal | Key Idea |
|---|---|---|
| G1 | Balanced throughput + predictable pauses | Region-based |
| ZGC | Very low latency | Highly concurrent |
| Parallel | High throughput | Parallel collection |
| Serial | Simplicity/small workloads | Single GC thread |

Interview answer:

> Choose the collector based on latency, throughput, heap size, workload, and measured GC behavior rather than simply choosing the newest collector.

---

# 25. Memory Leak

## Q43. Can Java applications have memory leaks? ⭐⭐⭐

Yes.

Garbage collection only removes objects that are unreachable.

If an application accidentally keeps references to objects that it no longer needs, those objects remain reachable and cannot be collected.

Example:

```java
class Cache {

    private static final List<byte[]> data =
        new ArrayList<>();

    static void add() {
        data.add(new byte[1024 * 1024]);
    }
}
```

The static list continuously retains objects.

Possible result:

```text
Heap usage ↑
Heap usage ↑
Heap usage ↑
...
OutOfMemoryError
```

---

# 26. Common Java Memory Leak Causes

## Q44. What commonly causes memory leaks in Java? ⭐⭐⭐

Examples:

- Static collections
- Unbounded caches
- Objects retained by long-lived collections
- ThreadLocal values not cleaned correctly
- Listeners/subscribers not deregistered
- ClassLoader leaks
- Long-lived maps
- Accumulating queues
- Incorrect application-level caching

---

# 27. ThreadLocal Memory Leak

## Q45. How can ThreadLocal cause memory leaks? ⭐⭐⭐

A thread pool contains long-lived worker threads.

If a task stores a large object in `ThreadLocal` and does not remove it, the value may remain associated with that thread.

Example:

```java
private static final ThreadLocal<byte[]> LOCAL =
    new ThreadLocal<>();

try {
    LOCAL.set(new byte[10 * 1024 * 1024]);

    // business logic

} finally {
    LOCAL.remove();
}
```

Best practice:

```java
finally {
    LOCAL.remove();
}
```

---

# 28. Static Collection Leak

## Q46. Why can static collections cause memory leaks?

Static fields can remain reachable for a very long time, often for the lifetime of the classloader/application.

Example:

```java
static Map<String, Object> cache =
    new HashMap<>();
```

If entries are continuously added and never evicted:

```text
cache size ↑
heap usage ↑
```

Use bounded/expiring caches where appropriate.

---

# 29. OutOfMemoryError

## Q47. What is OutOfMemoryError? ⭐⭐⭐

It occurs when the JVM cannot satisfy a memory allocation request.

Example:

```java
List<byte[]> list = new ArrayList<>();

while (true) {
    list.add(new byte[1024 * 1024]);
}
```

Eventually the heap may be exhausted.

---

## Q48. Common types/messages of OutOfMemoryError?

Examples include:

```text
Java heap space
GC overhead limit exceeded
Metaspace
Direct buffer memory
Unable to create native thread
```

The exact message tells you something about the exhausted resource.

---

# 30. GC Overhead Limit

## Q49. What is `GC overhead limit exceeded`?

It can occur when the JVM spends an excessive amount of time performing garbage collection while recovering very little memory.

Conceptually:

```text
GC work ↑↑↑
Useful application work ↓↓↓
Recovered memory very small
```

It is a symptom of severe memory pressure and should be investigated rather than simply disabled.

---

# 31. Metaspace

## Q50. What is Metaspace? ⭐⭐⭐

In modern HotSpot JVMs, Metaspace is native memory used primarily for class metadata.

It replaced the old permanent generation (PermGen) in Java 8.

Potential causes of excessive Metaspace:
- dynamically generated classes
- classloader leaks
- repeatedly loading classes without releasing classloaders

---

## Q51. PermGen vs Metaspace?

Before Java 8:

```text
Class metadata → PermGen
```

Java 8+ HotSpot:

```text
Class metadata → Metaspace
```

Metaspace is allocated from native memory rather than the traditional Java heap.

---

# 32. Direct Memory

## Q52. What is direct/off-heap memory?

Memory can also be allocated outside the Java heap.

Example:

```java
ByteBuffer buffer =
    ByteBuffer.allocateDirect(1024 * 1024);
```

This can be useful for high-performance I/O and networking.

Direct memory is outside the normal Java heap and has its own limits/accounting considerations.

---

# 33. Native Memory

## Q53. Is all JVM memory part of `-Xmx`?

No.

The JVM process uses more memory than the Java heap.

Conceptually:

```text
JVM Process Memory
|
+-- Java Heap
+-- Metaspace
+-- Thread stacks
+-- Code cache
+-- Direct buffers
+-- Native/JVM structures
+-- Other native allocations
```

Therefore:

```text
Process RSS > -Xmx
```

can be completely normal.

---

# 34. String Pool

## Q54. What is the String Pool? ⭐⭐⭐

String literals are interned by the JVM so identical literals can share a canonical string instance.

Example:

```java
String a = "Java";
String b = "Java";

System.out.println(a == b);
```

Output:

```text
true
```

Because both refer to the same interned literal.

---

## Q55. What happens with `new String()`?

```java
String a = new String("Java");
String b = new String("Java");

System.out.println(a == b);
System.out.println(a.equals(b));
```

Output:

```text
false
true
```

Two separate String objects are created, although the literal `"Java"` itself is interned.

---

# 35. Tricky Memory Output Questions

## Q56. What is the output?

```java
String a = "Java";
String b = "Java";

System.out.println(a == b);
```

Output:

```text
true
```

---

## Q57. What is the output?

```java
String a = new String("Java");
String b = new String("Java");

System.out.println(a == b);
System.out.println(a.equals(b));
```

Output:

```text
false
true
```

---

## Q58. What is the output?

```java
String a = "Ja" + "va";
String b = "Java";

System.out.println(a == b);
```

Output:

```text
true
```

The concatenation of compile-time constants can be resolved at compile time and refers to the same interned literal.

---

## Q59. What is the output?

```java
String x = "Java";
String y = "Ja";
String z = y + "va";

System.out.println(x == z);
```

Output:

```text
false
```

Here `z` is produced at runtime because `y` is a variable, so it creates a distinct String object.

---

## Q60. What is the output?

```java
String x = "Java";
String y = new String("Java");

y.intern();

System.out.println(x == y);
```

Output:

```text
false
```

`intern()` returns the canonical pooled String; it does not change `y`.

Correct:

```java
System.out.println(x == y.intern());
```

Output:

```text
true
```

---

# 36. Garbage Eligibility Tricky Questions

## Q61. Is this object eligible for GC?

```java
Employee e = new Employee();

e = null;
```

If there are no other references to the object, yes, it becomes eligible for GC.

But GC may happen later or not before the application exits.

---

## Q62. What about this?

```java
Employee e1 = new Employee();
Employee e2 = e1;

e1 = null;
```

Is the object eligible?

No.

```text
e2
 |
 v
Employee
```

`e2` still strongly references it.

---

## Q63. What about this?

```java
Employee e1 = new Employee();
Employee e2 = new Employee();

e1 = e2;
```

The original object referenced by `e1` becomes eligible for GC if no other references point to it.

The second object remains reachable through `e1`/`e2`.

---

# 37. Anonymous Objects

## Q64. Is this object eligible for GC?

```java
new Employee();
```

After the statement completes, if no reference was retained and no other path can reach the object, it can become eligible for GC.

---

# 38. `System.gc()`

## Q65. Does `System.gc()` guarantee garbage collection? ⭐⭐⭐

No.

```java
System.gc();
```

is only a request/suggestion to the JVM.

The JVM is not required to perform GC immediately because of this call.

Interview one-liner:

> `System.gc()` is a request, not a guarantee.

---

# 39. `finalize()` and GC

## Q66. Does GC call finalize() before every object is collected?

Do not rely on this.

`finalize()` is deprecated and unreliable for resource management.

Use explicit cleanup or try-with-resources.

---

# 40. Escape Analysis

## Q67. What is escape analysis? ⭐⭐

Escape analysis is a JVM optimization technique that determines whether an object/reference escapes a method or thread.

If an object does not escape, the JVM may optimize its allocation or synchronization.

Example:

```java
public int calculate() {

    Point p = new Point(10, 20);

    return p.x + p.y;
}
```

The JVM may optimize the object allocation depending on runtime analysis and JIT compilation.

Important:

> Do not assume every `new` necessarily results in a permanent heap allocation visible in the same way at runtime.

---

# 41. TLAB

## Q68. What is TLAB?

TLAB means **Thread-Local Allocation Buffer**.

It allows a thread to allocate objects in a private allocation area within the heap without synchronizing with other threads for every small allocation.

Conceptually:

```text
Heap
|
+-- Thread 1 TLAB
+-- Thread 2 TLAB
+-- Thread 3 TLAB
```

This improves allocation performance.

---

# 42. Object Promotion

## Q69. What is object promotion?

Objects that survive enough young-generation collections may be promoted to old generation, depending on collector policy.

Conceptually:

```text
Eden
  |
  v
Survivor
  |
  v
Survivor
  |
  v
Old Generation
```

Promotion is not necessarily based on one universal fixed age across all collectors/configurations.

---

# 43. Humongous Objects in G1

## Q70. What is a humongous object in G1? ⭐⭐

In G1, very large objects relative to a region can require special humongous-object handling and occupy one or more contiguous regions.

Large allocations can contribute significantly to memory pressure and fragmentation-related behavior.

---

# 44. GC Tuning

## Q71. Should we tune GC immediately when an application is slow?

No.

First measure and identify the bottleneck.

Check:
- GC pause times
- allocation rate
- heap usage
- old-generation occupancy
- CPU
- thread behavior
- application latency
- object retention
- logs/profiling data

Then tune based on evidence.

---

# 45. Useful JVM GC Options

## Q72. What JVM options are commonly discussed in interviews? ⭐⭐⭐

Examples:

```bash
-Xms512m
-Xmx2g
-Xss1m
```

G1:

```bash
-XX:+UseG1GC
-XX:MaxGCPauseMillis=200
```

Heap dump on OOM:

```bash
-XX:+HeapDumpOnOutOfMemoryError
-XX:HeapDumpPath=/tmp/heapdump.hprof
```

GC logging on modern Java:

```bash
-Xlog:gc*
```

Exact JVM options can vary by Java version and collector.

---

# 46. Heap Dump

## Q73. What is a heap dump? ⭐⭐⭐

A heap dump is a snapshot of objects and object relationships in the JVM heap.

It can help investigate:
- memory leaks
- retained objects
- large collections
- unexpectedly long object lifetimes
- object counts

Tools commonly used include:

```text
Eclipse MAT
VisualVM
JDK tools
JProfiler
YourKit
```

---

# 47. GC Logs

## Q74. Why are GC logs useful?

They help analyze:

- GC frequency
- pause duration
- heap occupancy
- allocation pressure
- old-generation behavior
- promotion
- possible memory pressure

Example modern Java logging:

```bash
-Xlog:gc*
```

---

# 48. Monitoring Memory in Production

## Q75. What metrics would you monitor? ⭐⭐⭐

Important metrics:

```text
Heap used
Heap committed
Heap max
Old generation occupancy
GC pause time
GC frequency
Allocation rate
CPU
Thread count
Native memory
Metaspace usage
Direct memory
Application latency
```

For a backend service, correlate GC metrics with application latency and throughput.

---

# 49. Memory Leak Investigation

## Q76. How would you investigate a memory leak in production? ⭐⭐⭐

A practical process:

```text
1. Observe memory trend
        ↓
2. Check GC behavior
        ↓
3. Check heap usage after GC
        ↓
4. Capture heap dump
        ↓
5. Analyze retained objects
        ↓
6. Find GC root path
        ↓
7. Identify long-lived reference
        ↓
8. Fix retention
        ↓
9. Verify after deployment
```

Tools:

```text
Heap dump
Eclipse MAT
JFR
JMC
VisualVM
GC logs
APM/metrics
```

---

# 50. Retained Size vs Shallow Size

## Q77. What is shallow size?

The memory directly occupied by an object itself, excluding objects it references.

---

## Q78. What is retained size?

The amount of memory that would become reclaimable if that object were removed, including objects retained only through it.

This is particularly useful during heap-dump analysis.

---

# 51. Why GC Cannot Fix Every Memory Problem

## Q79. If GC runs frequently but memory is still high, what could be happening? ⭐⭐⭐

Possibilities:

- Application is retaining objects intentionally/unintentionally.
- Allocation rate is very high.
- Cache is too large.
- Objects have long lifetimes.
- Heap is undersized.
- There is a memory leak.
- Native/off-heap memory is high.
- Metaspace/direct memory is growing.

GC can only reclaim objects that are eligible.

---

# 52. High Allocation Rate

## Q80. What is allocation pressure?

Allocation pressure means the application creates objects at a high rate.

Example:

```java
for (int i = 0; i < 1_000_000; i++) {
    new Employee();
}
```

Even if objects become garbage quickly, the JVM may need to perform frequent GC because allocation is fast.

This can hurt throughput or latency depending on the collector/workload.

---

# 53. GC and Performance

## Q81. How can GC affect application performance?

GC consumes CPU and may pause application threads during STW phases.

Possible symptoms:

```text
GC pause ↑
       ↓
Request latency ↑

GC frequency ↑
       ↓
CPU available for application ↓
```

Therefore, memory tuning is closely related to application latency and throughput.

---

# 54. Large Heap vs Small Heap

## Q82. Is a larger heap always better?

No.

A larger heap can:
- reduce frequency of some collections
- provide more allocation headroom

But it can also:
- increase memory footprint
- allow more objects to accumulate
- make certain GC cycles more expensive
- hide retention problems

Tune based on measurements and workload.

---

# 55. Memory Leak vs Memory Spike

## Q83. Difference between memory leak and memory spike?

### Memory spike

Temporary increase:

```text
Memory
  /\
 /  \
/    \__
```

Memory eventually falls.

### Memory leak

Memory keeps growing or the post-GC baseline keeps increasing:

```text
Memory
   /
  /
 /
/________
```

until resources are exhausted.

---

# 56. Tricky Code Question — Object Eligibility

## Q84. What happens?

```java
Employee e = new Employee();

e = new Employee();
```

The first `Employee` becomes eligible for GC if no other reference points to it.

The second object is referenced by `e`.

---

# 57. Tricky Code Question — Two References

## Q85. What happens?

```java
Employee e1 = new Employee();
Employee e2 = e1;

e1 = null;
e2 = null;
```

After both references are cleared, the object becomes eligible for GC if there are no other reachable references.

---

# 58. Tricky Code Question — Method Scope

## Q86. What happens?

```java
public void create() {

    Employee e = new Employee();

}
```

After the method returns, the local reference `e` disappears.

If no other reachable reference points to the object, it becomes eligible for GC.

---

# 59. Tricky Code Question — Static Reference

## Q87. What happens?

```java
class Cache {

    static Employee employee =
        new Employee();
}
```

The object remains reachable through the static field as long as the relevant class/classloader remains reachable.

Setting:

```java
Cache.employee = null;
```

can make it eligible if there are no other references.

---

# 60. Tricky Code Question — Circular Reference

## Q88. Are these objects collectible?

```java
class A {
    B b;
}

class B {
    A a;
}

A a = new A();
B b = new B();

a.b = b;
b.a = a;

a = null;
b = null;
```

Yes, assuming no other reachable references exist.

The cycle alone does not keep them alive.

---

# 61. Tricky Output — String Pool

## Q89. What is the output?

```java
String s1 = "Hello";
String s2 = "Hello";

System.out.println(s1 == s2);
```

Output:

```text
true
```

---

## Q90. What is the output?

```java
String s1 = new String("Hello");
String s2 = "Hello";

System.out.println(s1 == s2);
System.out.println(s1.equals(s2));
```

Output:

```text
false
true
```

---

# 62. OOP + Memory Tricky Question

## Q91. What is stored where?

```java
class Employee {

    String name;
    int age;
}

Employee e = new Employee();
```

Conceptually:

```text
Stack / current frame
---------------------
e → reference

Heap
---------------------
Employee object
name → reference
age  → primitive value
```

The String object itself is a separate object and may be in the String pool if it is an interned literal.

---

# 63. `final` Reference

## Q92. Does final make an object immutable?

No.

```java
final List<String> list =
    new ArrayList<>();

list.add("Java");
```

This is valid.

`final` prevents reassignment of the reference:

```java
// list = new ArrayList<>(); // invalid
```

but does not make the referenced object immutable.

---

# 64. Garbage Collection and final References

## Q93. Can an object referenced by a final variable be garbage collected?

Yes, once the containing reference itself is no longer reachable.

`final` prevents reassignment of the reference; it does not make the object immortal.

---

# 65. Production Scenario — High GC

## Q94. Production API latency suddenly increases and GC pauses increase. What do you check? ⭐⭐⭐

Check:

```text
1. GC pause duration
2. GC frequency
3. Allocation rate
4. Heap occupancy
5. Old-gen occupancy
6. Promotion behavior
7. Large object allocation
8. Recent deployment changes
9. Traffic increase
10. CPU
11. Thread count
12. Application latency
```

Then correlate the timeline with application traffic and recent code changes.

---

# 66. Production Scenario — Heap Looks Full

## Q95. Heap usage reaches 90–95%. Is it automatically a memory leak?

No.

High heap usage alone does not prove a leak.

Ask:

```text
After GC:
    Does used memory fall significantly?
```

If the post-GC baseline keeps increasing over time, investigate retention/leak.

---

# 67. Production Scenario — OutOfMemoryError

## Q96. Application throws OutOfMemoryError. What would you do? ⭐⭐⭐

Immediate steps depend on the environment, but generally:

1. Preserve logs/metrics.
2. Determine which memory area failed.
3. Check heap/GC behavior.
4. Capture/analyze heap dump when possible.
5. Inspect recent code/config changes.
6. Check traffic and allocation spikes.
7. Check native/direct/metaspace/thread memory where relevant.
8. Fix root cause rather than simply increasing `-Xmx`.

Useful option:

```bash
-XX:+HeapDumpOnOutOfMemoryError
```

---

# 68. Production Scenario — Thread Explosion

## Q97. Application has `Unable to create native thread`. Is this necessarily a heap problem?

No.

It can indicate native memory/thread resource exhaustion.

Investigate:

```text
Thread count
-Xss
Native memory
Thread pools
Blocked threads
Unbounded thread creation
OS limits
```

---

# 69. Production Scenario — Metaspace OOM

## Q98. What could cause `OutOfMemoryError: Metaspace`?

Potential causes:

- Excessive class generation
- Dynamic proxies/classes
- Classloader leaks
- Repeated application/module loading
- Incorrect lifecycle management

Do not simply increase `MaxMetaspaceSize` without investigating retention/classloading behavior.

---

# 70. Production Scenario — Direct Buffer Memory

## Q99. What could cause `OutOfMemoryError: Direct buffer memory`?

Potential causes include excessive direct `ByteBuffer` allocations or insufficient direct-memory capacity.

Investigate:

```text
Direct buffer usage
Netty/off-heap allocations
Buffer lifecycle
Native memory
Application traffic
```

---

# 71. GC Interview Scenario

## Q100. Why might increasing heap size reduce GC frequency but not fix a memory leak?

Suppose the application retains unnecessary objects.

```text
Leak:
100 MB
200 MB
300 MB
400 MB
...
```

Increasing:

```bash
-Xmx2g
```

to:

```bash
-Xmx4g
```

only gives the application more time before failure.

The retained objects are still reachable.

Therefore:

> More memory can delay the symptom but does not fix the retention problem.

---

# 72. GC Monitoring Tools

## Q101. Which tools can help analyze JVM memory and GC? ⭐⭐⭐

Common tools:

```text
jcmd
jstat
jmap
jstack
Java Flight Recorder (JFR)
Java Mission Control (JMC)
VisualVM
Eclipse MAT
GC logs
APM/metrics systems
```

Tool choice depends on whether you need:
- thread analysis
- heap analysis
- GC statistics
- profiling
- production telemetry

---

# 73. `jstat`

## Q102. What is jstat?

`jstat` provides JVM statistics such as GC and memory-related information.

Example:

```bash
jstat -gc <pid> 1000
```

This can periodically display GC-related statistics.

---

# 74. `jcmd`

## Q103. What is jcmd?

`jcmd` is a general-purpose JDK diagnostic utility.

Example:

```bash
jcmd <pid> GC.heap_info
```

Other diagnostic commands can provide information about:
- heap
- threads
- VM flags
- class histogram
- JFR

---

# 75. JFR

## Q104. What is Java Flight Recorder? ⭐⭐

JFR is a low-overhead JVM/application profiling and diagnostics framework.

It can provide information about:
- CPU
- allocation
- GC
- threads
- locks
- I/O
- application behavior

It is useful for production performance investigations.

---

# 76. JMC

## Q105. What is Java Mission Control?

JMC is a tool used to analyze JVM diagnostic data, including JFR recordings.

Typical flow:

```text
Application
    ↓
JFR recording
    ↓
JMC analysis
```

---

# 77. Heap Dump Analysis

## Q106. What would you look for in a heap dump? ⭐⭐⭐

Look for:

```text
1. Largest objects
2. Largest retained sizes
3. Dominator tree
4. Collection sizes
5. GC root paths
6. Duplicate/unexpected objects
7. Class instance counts
8. Long-lived caches
9. ThreadLocal retention
10. Unexpected object graphs
```

---

# 78. Dominator Tree

## Q107. What is a dominator tree?

A dominator tree helps identify objects that retain large portions of the heap.

If object A dominates object B, B cannot become unreachable while A remains reachable through that path.

It is useful for finding the object responsible for retaining large amounts of memory.

---

# 79. WeakHashMap

## Q108. What is WeakHashMap? ⭐⭐

`WeakHashMap` uses weak references for its keys.

When a key is no longer strongly referenced elsewhere, its entry can be removed by GC.

Example:

```java
Map<Object, String> map =
    new WeakHashMap<>();

Object key = new Object();

map.put(key, "value");

key = null;
```

The entry may disappear after GC because the key is weakly referenced.

Important:

> GC timing is not deterministic, so never use WeakHashMap when application correctness depends on immediate removal.

---

# 80. Memory Visibility vs Memory Management

## Q109. Is Java memory management the same as Java Memory Model (JMM)?

No.

### JVM memory management

Deals with:
- heap
- stack
- GC
- allocation
- memory reclamation

### Java Memory Model

Deals with:
- visibility
- ordering
- happens-before
- synchronization
- volatile
- atomicity aspects

They are related but different topics.

---

# 81. Volatile and Memory

## Q110. Does volatile prevent garbage collection?

No.

`volatile` affects visibility and ordering of variable accesses between threads; it does not determine whether an object is garbage.

---

# 82. Synchronization and Memory

## Q111. Does synchronized prevent GC?

No.

Synchronization controls thread coordination and memory visibility.

An object can still be garbage collected when it becomes unreachable.

---

# 83. Final Revision Table

## Q112. Stack vs Heap vs Metaspace?

| Area | Main Purpose | Shared? |
|---|---|---|
| Stack | Per-thread method execution | No |
| Heap | Objects/arrays | Yes |
| Metaspace | Class metadata in HotSpot | Shared JVM area |

---

## Q113. Minor GC vs Full GC?

| Minor GC | Full GC |
|---|---|
| Traditionally young generation | Broad/heavy collection |
| Usually more frequent | Usually less frequent |
| Often shorter | Often more disruptive |
| Collector-dependent details | Collector-dependent details |

---

## Q114. Strong vs Weak reference?

```text
Strong reference
→ keeps object strongly reachable

Weak reference
→ does not keep object strongly reachable
```

---

## Q115. Memory leak vs OutOfMemoryError?

```text
Memory leak
→ root cause / unwanted retention

OutOfMemoryError
→ symptom that a memory allocation could not be satisfied
```

A leak can eventually cause an OOM, but OOM does not always mean a leak.

---

# 84. Rapid-Fire Interview Questions

## Q116. Where are Java objects generally allocated?

Heap.

## Q117. Is heap shared between threads?

Yes.

## Q118. Is each thread's stack shared?

No.

## Q119. What does GC reclaim?

Unreachable/collectible objects.

## Q120. Does `System.gc()` guarantee GC?

No.

## Q121. What is a GC root?

A root from which object reachability is determined.

## Q122. Can circular references be collected?

Yes, if unreachable from GC roots.

## Q123. What is Eden?

Typical young-generation allocation region.

## Q124. What are Survivor spaces?

Regions used for surviving objects in generational collectors.

## Q125. What is object promotion?

Moving/surviving objects into old-generation space according to collector policy.

## Q126. What is STW?

Stop-the-World pause of application threads for certain JVM operations.

## Q127. What is G1?

Region-based garbage collector designed for balanced throughput and predictable pause goals.

## Q128. What is ZGC?

Low-latency, highly concurrent garbage collector.

## Q129. What is Parallel GC?

Throughput-oriented parallel collector.

## Q130. What is Metaspace?

Native memory area used primarily for class metadata in modern HotSpot JVMs.

## Q131. What replaced PermGen?

Metaspace in Java 8 HotSpot.

## Q132. What is `-Xms`?

Initial heap size.

## Q133. What is `-Xmx`?

Maximum heap size.

## Q134. What is `-Xss`?

Thread stack size.

## Q135. What is a heap dump?

Snapshot of heap objects and relationships.

## Q136. What is JFR?

Java Flight Recorder, a low-overhead profiling/diagnostics framework.

## Q137. What is a memory leak?

Unwanted retention of objects that are no longer logically needed.

## Q138. Does final make an object immutable?

No.

## Q139. Does volatile make an object immutable?

No.

## Q140. Does synchronized prevent GC?

No.

---

# 85. Most Important Questions for a 5–6 Year Java Backend Interview

## ⭐⭐⭐ Must Know

1. JVM memory areas
2. Heap vs Stack
3. Heap generations
4. Eden and Survivor spaces
5. GC roots
6. Reachability
7. How GC determines garbage
8. Minor vs Full GC
9. Stop-the-World
10. G1 GC
11. `-Xms` vs `-Xmx`
12. Memory leaks
13. OutOfMemoryError
14. Metaspace
15. String pool
16. `System.gc()`
17. Heap dump analysis
18. GC logs
19. Production memory troubleshooting
20. StackOverflowError
21. ThreadLocal memory leaks
22. Strong/weak references

## ⭐⭐ Important

- ZGC
- Parallel GC
- Serial GC
- SoftReference
- PhantomReference
- WeakHashMap
- TLAB
- Escape analysis
- Direct memory
- Native memory
- JFR/JMC
- Dominator tree
- Humongous objects in G1

---

# 86. Interview Scenarios You Should Practice

### Scenario 1

> Production API latency increased suddenly and GC pauses increased. How would you investigate?

Expected areas:

```text
GC logs
heap occupancy
allocation rate
old-gen
CPU
traffic
recent deployment
object retention
JFR
```

### Scenario 2

> Application crashes with `OutOfMemoryError: Java heap space`.

Discuss:

```text
heap dump
retained objects
GC root path
allocation rate
cache size
-Xmx
recent code changes
```

### Scenario 3

> Memory keeps growing even though GC runs frequently.

Discuss:

```text
reachable objects
memory leak
static collection
ThreadLocal
cache
listener registration
heap dump
dominator tree
```

### Scenario 4

> 5000 threads are created and the application throws `Unable to create native thread`.

Discuss:

```text
thread pool configuration
-Xss
native memory
OS limits
unbounded thread creation
blocked threads
```

### Scenario 5

> Heap is at 95%. Is that necessarily a problem?

Answer:

> No. Check post-GC occupancy, GC frequency, pause time, allocation rate, and application latency. High occupancy alone does not prove a leak.

---

# 87. One-Page Memory Management Cheat Sheet

```text
                         JVM
                          |
          +---------------+---------------+
          |               |               |
        Heap            Stack          Metaspace
          |               |               |
      Objects         Per-thread       Class metadata
      Arrays          Frames
          |
      +---+---+
      |       |
    Young     Old
      |
  +---+---+
  |   |   |
Eden S0  S1
```

### Object lifecycle

```text
new Object()
     ↓
  Allocation
     ↓
   Eden
     ↓
Young GC
     ↓
Survivor
     ↓
Promotion
     ↓
Old Generation
     ↓
Object becomes unreachable
     ↓
Eligible for GC
     ↓
Collector reclaims memory
```

### GC

```text
GC Root
   |
   v
Reachable Object
   |
   v
Reachable Object

No path from GC Root
        ↓
Object is collectible
```

### Key JVM options

```text
-Xms        → initial heap
-Xmx        → max heap
-Xss        → thread stack

-XX:+UseG1GC
-XX:MaxGCPauseMillis=200

-XX:+HeapDumpOnOutOfMemoryError
-XX:HeapDumpPath=...
-Xlog:gc*
```

### Collector memory

```text
G1       → balanced / pause-oriented
ZGC      → low latency
Parallel → throughput
Serial   → simple/small workloads
```

### Memory problems

```text
StackOverflowError
→ excessive stack usage / recursion

Java heap space
→ heap allocation failure

Metaspace
→ class metadata/native memory pressure

Direct buffer memory
→ off-heap direct buffer pressure

Unable to create native thread
→ native/thread resource exhaustion

GC overhead limit exceeded
→ excessive GC with little useful memory recovery
```

---

# Final Interview Rule

Do not answer:

> "GC deletes unused objects."

A stronger senior-level answer is:

> "The JVM identifies objects that are no longer reachable from GC roots and reclaims their memory using the selected garbage collector. Modern collectors use different regioning, concurrency, compaction, and pause-management techniques, so GC behavior depends on the collector and workload."

For production troubleshooting, remember:

```text
Measure
  ↓
Identify memory area
  ↓
Check GC behavior
  ↓
Check allocation/retention
  ↓
Heap dump / JFR / GC logs
  ↓
Find GC root / retention path
  ↓
Fix root cause
  ↓
Verify after deployment
```
