# Java 8 — Interview Questions & Answers

## Target Level
Java Backend Developer — 5–6 years experience

This guide covers the Java 8 topics most commonly asked in backend interviews, with conceptual answers, code examples, coding questions, traps, and senior-level discussion points.

---

# 1. Java 8 Overview

## Q1. What are the major features introduced in Java 8?

### Answer

The most important Java 8 features are:

```text
1. Lambda Expressions
2. Functional Interfaces
3. Stream API
4. Method References
5. Default Methods
6. Static Methods in Interfaces
7. Optional
8. New Date and Time API
9. CompletableFuture
10. Collectors
11. forEach()
12. Nashorn JavaScript Engine
```

For backend interviews, focus heavily on:

```text
Lambda
Functional Interface
Stream API
Optional
Method Reference
Collectors
Default Methods
Date/Time API
CompletableFuture
```

---

# 2. Lambda Expressions

## Q2. What is a Lambda Expression?

### Answer

A lambda is a concise way to represent a function that can be passed around as a value.

Syntax:

```java
(parameters) -> expression
```

or:

```java
(parameters) -> {
    // statements
}
```

Example:

```java
(a, b) -> a + b
```

Without lambda:

```java
Comparator<Integer> comparator =
    new Comparator<Integer>() {
        @Override
        public int compare(Integer a, Integer b) {
            return a.compareTo(b);
        }
    };
```

With lambda:

```java
Comparator<Integer> comparator =
    (a, b) -> a.compareTo(b);
```

---

## Q3. Why were lambda expressions introduced?

### Answer

Main reasons:

- reduce boilerplate
- enable functional programming
- make collection processing easier
- work naturally with Stream API
- allow behavior to be passed as an argument

Example:

```java
List<Integer> numbers =
    Arrays.asList(10, 20, 30);

numbers.forEach(n -> System.out.println(n));
```

---

## Q4. What is the difference between lambda and anonymous class?

### Answer

Anonymous class:

```java
Runnable r = new Runnable() {
    @Override
    public void run() {
        System.out.println("Hello");
    }
};
```

Lambda:

```java
Runnable r =
    () -> System.out.println("Hello");
```

Important differences:

- lambda is more concise
- lambda works with functional interfaces
- `this` inside a lambda refers to the enclosing object
- anonymous class creates its own `this`

---

## Q5. Can a lambda have multiple statements?

### Answer

Yes.

```java
(a, b) -> {
    int sum = a + b;
    return sum;
}
```

If there is a block body and a return value is required, use `return`.

---

# 3. Functional Interfaces

## Q6. What is a Functional Interface?

### Answer

A functional interface has exactly **one abstract method**.

Example:

```java
@FunctionalInterface
interface Calculator {
    int calculate(int a, int b);
}
```

Usage:

```java
Calculator add =
    (a, b) -> a + b;
```

It can contain multiple default and static methods because those are not abstract methods.

---

## Q7. What does @FunctionalInterface do?

### Answer

It tells the compiler that the interface is intended to have exactly one abstract method.

```java
@FunctionalInterface
interface Calculator {
    int calculate(int a, int b);
}
```

If another abstract method is added, compilation fails.

It improves correctness and communicates intent.

---

## Q8. What are common built-in functional interfaces?

### Answer

Important interfaces from `java.util.function`:

| Interface | Method | Purpose |
|---|---|---|
| Predicate<T> | `test()` | T → boolean |
| Function<T,R> | `apply()` | T → R |
| Consumer<T> | `accept()` | T → void |
| Supplier<T> | `get()` | () → T |
| UnaryOperator<T> | `apply()` | T → T |
| BinaryOperator<T> | `apply()` | (T,T) → T |
| BiFunction<T,U,R> | `apply()` | (T,U) → R |
| BiConsumer<T,U> | `accept()` | (T,U) → void |

---

## Q9. Explain Predicate.

### Answer

`Predicate<T>` takes a value and returns boolean.

```java
Predicate<Integer> isEven =
    n -> n % 2 == 0;

System.out.println(isEven.test(10));
```

Output:

```text
true
```

Common Stream use:

```java
numbers.stream()
       .filter(isEven)
       .forEach(System.out::println);
```

---

## Q10. Explain Function.

### Answer

`Function<T,R>` transforms one type into another.

```java
Function<String, Integer> length =
    str -> str.length();

System.out.println(length.apply("Java"));
```

Output:

```text
4
```

---

## Q11. Explain Consumer.

### Answer

`Consumer<T>` accepts a value and returns nothing.

```java
Consumer<String> print =
    value -> System.out.println(value);
```

Example:

```java
names.forEach(print);
```

---

## Q12. Explain Supplier.

### Answer

`Supplier<T>` takes no input and supplies a value.

```java
Supplier<Double> random =
    () -> Math.random();

System.out.println(random.get());
```

---

# 4. Method References

## Q13. What is a method reference?

### Answer

A method reference is a shorter syntax for a lambda when an existing method already represents the required behavior.

Syntax:

```java
ClassName::methodName
```

Example:

```java
names.forEach(System.out::println);
```

Equivalent lambda:

```java
names.forEach(name -> System.out.println(name));
```

---

## Q14. Types of method references?

### Answer

Four common forms:

```text
1. Static method
2. Instance method of a particular object
3. Instance method of an arbitrary object of a type
4. Constructor reference
```

Examples:

```java
Integer::parseInt
System.out::println
String::toUpperCase
ArrayList::new
```

---

## Q15. What is constructor reference?

### Answer

Use:

```java
ClassName::new
```

Example:

```java
Supplier<List<String>> supplier =
    ArrayList::new;
```

Equivalent:

```java
Supplier<List<String>> supplier =
    () -> new ArrayList<>();
```

---

# 5. Stream API

## Q16. What is Stream API?

### Answer

Stream API provides a declarative way to process collections and other data sources.

Example:

```java
List<Integer> numbers =
    Arrays.asList(10, 15, 20, 25);

List<Integer> result =
    numbers.stream()
           .filter(n -> n % 2 == 0)
           .collect(Collectors.toList());
```

Output:

```text
[10, 20]
```

---

## Q17. Collection vs Stream?

### Answer

A Collection stores data.

A Stream processes data.

```text
Collection
    ↓
stores elements

Stream
    ↓
processes elements
```

A stream does not normally modify the source collection simply by performing stream operations.

---

## Q18. Does Stream store data?

### Answer

No.

A Stream represents a pipeline for processing elements from a source.

The source could be:

```text
Collection
Array
I/O channel
generated data
```

---

## Q19. What are intermediate and terminal operations?

### Answer

### Intermediate

Return another Stream.

Examples:

```text
filter
map
flatMap
distinct
sorted
limit
skip
peek
```

### Terminal

Produce a result or side effect and terminate the stream.

Examples:

```text
collect
forEach
reduce
count
min
max
findFirst
findAny
anyMatch
allMatch
noneMatch
```

Example:

```java
numbers.stream()
       .filter(n -> n > 10)       // intermediate
       .map(n -> n * 2)           // intermediate
       .collect(Collectors.toList()); // terminal
```

---

## Q20. What is lazy evaluation in Stream API?

### Answer

Intermediate operations are generally lazy.

They are not executed until a terminal operation is invoked.

Example:

```java
Stream<Integer> stream =
    numbers.stream()
           .filter(n -> {
               System.out.println("filter: " + n);
               return n > 10;
           });
```

Nothing is processed yet.

Processing starts when:

```java
stream.collect(Collectors.toList());
```

is called.

---

## Q21. What happens if there is no terminal operation?

### Answer

The pipeline is not executed.

Example:

```java
numbers.stream()
       .filter(n -> n > 10)
       .map(n -> n * 2);
```

No actual processing is triggered because there is no terminal operation.

---

# 6. filter()

## Q22. What does filter() do?

### Answer

`filter()` selects elements that satisfy a condition.

```java
List<Integer> result =
    numbers.stream()
           .filter(n -> n % 2 == 0)
           .collect(Collectors.toList());
```

---

## Q23. Find employees whose salary is greater than 1 lakh.

### Answer

```java
List<Employee> result =
    employees.stream()
        .filter(e -> e.getSalary() > 100000)
        .collect(Collectors.toList());
```

---

# 7. map()

## Q24. What does map() do?

### Answer

`map()` transforms each element.

```java
List<String> names =
    employees.stream()
        .map(Employee::getName)
        .collect(Collectors.toList());
```

Example:

```text
Employee
   ↓ map()
Employee name
```

---

## Q25. map() vs filter()?

### Answer

```text
filter → decides which elements remain
map    → transforms elements
```

Example:

```java
numbers.stream()
       .filter(n -> n > 10)
       .map(n -> n * 2)
       .toList();
```

---

# 8. flatMap()

## Q26. What is flatMap()?

### Answer

`flatMap()` transforms each element into a stream and then flattens all resulting streams into one stream.

Example:

```java
List<List<Integer>> numbers =
    Arrays.asList(
        Arrays.asList(1, 2),
        Arrays.asList(3, 4)
    );

List<Integer> result =
    numbers.stream()
           .flatMap(List::stream)
           .collect(Collectors.toList());
```

Output:

```text
[1, 2, 3, 4]
```

Memory trick:

```text
map    → transform
flatMap → transform + flatten
```

---

## Q27. map vs flatMap?

### Answer

Suppose:

```text
List<List<String>>
```

Using `map`:

```text
Stream<List<String>>
```

Using `flatMap`:

```text
Stream<String>
```

Use `flatMap()` when nested structures need to be flattened.

---

# 9. distinct(), sorted(), limit(), skip()

## Q28. What does distinct() do?

### Answer

Removes duplicate elements according to equality semantics.

```java
List<Integer> result =
    numbers.stream()
           .distinct()
           .collect(Collectors.toList());
```

---

## Q29. What does sorted() do?

### Answer

Sorts stream elements using natural ordering or a comparator.

```java
numbers.stream()
       .sorted()
       .forEach(System.out::println);
```

Custom:

```java
employees.stream()
    .sorted(Comparator.comparing(Employee::getSalary))
    .forEach(System.out::println);
```

---

## Q30. What do limit() and skip() do?

### Answer

`limit(n)` keeps the first `n` elements.

```java
stream.limit(5);
```

`skip(n)` ignores the first `n` elements.

```java
stream.skip(5);
```

---

# 10. reduce()

## Q31. What is reduce()?

### Answer

`reduce()` combines stream elements into a single result.

Example:

```java
int sum =
    numbers.stream()
           .reduce(0, Integer::sum);
```

For:

```text
[10, 20, 30]
```

result:

```text
60
```

---

## Q32. reduce() vs collect()?

### Answer

`reduce()` is generally used to combine elements into a single value.

```java
int sum =
    numbers.stream()
           .reduce(0, Integer::sum);
```

`collect()` is designed for mutable reduction into containers/results.

```java
List<Integer> list =
    numbers.stream()
           .collect(Collectors.toList());
```

---

# 11. findFirst / findAny

## Q33. findFirst() vs findAny()?

### Answer

`findFirst()` returns the first element according to encounter order when one exists.

`findAny()` returns any element and is especially useful where encounter order does not matter, particularly with parallel streams.

Both return:

```java
Optional<T>
```

---

## Q34. anyMatch, allMatch and noneMatch?

### Answer

```java
anyMatch → at least one matches
allMatch → all match
noneMatch → none match
```

Example:

```java
boolean result =
    numbers.stream()
           .anyMatch(n -> n > 100);
```

---

# 12. Collectors

## Q35. What is Collectors?

### Answer

`Collectors` provides predefined collectors used with `Stream.collect()`.

Important methods:

```text
toList()
toSet()
toMap()
joining()
groupingBy()
partitioningBy()
counting()
mapping()
summarizingInt()
averagingInt()
maxBy()
minBy()
```

---

## Q36. How do you convert a Stream to List?

### Answer

Traditional Java 8:

```java
List<String> names =
    stream.collect(Collectors.toList());
```

Note: `Stream.toList()` was introduced after Java 8. For Java 8 interview questions, use `Collectors.toList()`.

---

## Q37. How do you convert a Stream to Set?

### Answer

```java
Set<String> names =
    stream.collect(Collectors.toSet());
```

---

## Q38. How do you join strings?

### Answer

```java
String result =
    names.stream()
         .collect(Collectors.joining(", "));
```

Example:

```text
Vivek, Rahul, Amit
```

---

# 13. groupingBy()

## Q39. What is groupingBy()?

### Answer

It groups elements based on a classification function.

Example:

```java
Map<String, List<Employee>> result =
    employees.stream()
        .collect(Collectors.groupingBy(
            Employee::getDepartment
        ));
```

Result:

```text
IT       → [employees...]
Finance  → [employees...]
HR       → [employees...]
```

---

## Q40. Count employees by department.

### Answer

```java
Map<String, Long> result =
    employees.stream()
        .collect(Collectors.groupingBy(
            Employee::getDepartment,
            Collectors.counting()
        ));
```

---

## Q41. Find highest salary in each department.

### Answer

```java
Map<String, Optional<Employee>> result =
    employees.stream()
        .collect(Collectors.groupingBy(
            Employee::getDepartment,
            Collectors.maxBy(
                Comparator.comparing(Employee::getSalary)
            )
        ));
```

---

# 14. partitioningBy()

## Q42. groupingBy vs partitioningBy?

### Answer

`groupingBy()` can create many groups.

`partitioningBy()` divides elements into exactly two groups:

```text
true
false
```

Example:

```java
Map<Boolean, List<Employee>> result =
    employees.stream()
        .collect(Collectors.partitioningBy(
            e -> e.getSalary() > 100000
        ));
```

---

# 15. toMap()

## Q43. Convert List<Employee> to Map<Id, Employee>.

### Answer

```java
Map<Integer, Employee> result =
    employees.stream()
        .collect(Collectors.toMap(
            Employee::getId,
            Function.identity()
        ));
```

---

## Q44. What happens if duplicate keys occur in toMap()?

### Answer

This can throw:

```text
IllegalStateException
```

Provide a merge function:

```java
Map<Integer, Employee> result =
    employees.stream()
        .collect(Collectors.toMap(
            Employee::getId,
            Function.identity(),
            (oldValue, newValue) -> newValue
        ));
```

---

# 16. Optional

## Q45. What is Optional?

### Answer

`Optional<T>` is a container that may contain a value or be empty.

It can make absence explicit and reduce some accidental null handling.

Example:

```java
Optional<String> name =
    Optional.ofNullable(employee.getName());
```

---

## Q46. Why was Optional introduced?

### Answer

Primarily to make absence explicit in APIs and reduce error-prone null handling.

Instead of:

```java
String name = getName();

if (name != null) {
    ...
}
```

an API may return:

```java
Optional<String>
```

---

## Q47. Optional.of() vs ofNullable()?

### Answer

```java
Optional.of(value)
```

expects a non-null value. Passing null throws `NullPointerException`.

```java
Optional.ofNullable(value)
```

allows null and returns `Optional.empty()` when value is null.

---

## Q48. Optional.empty()?

### Answer

Creates an empty Optional:

```java
Optional<String> result =
    Optional.empty();
```

---

## Q49. isPresent() vs ifPresent()?

### Answer

`isPresent()` checks whether a value exists:

```java
if (optional.isPresent()) {
    ...
}
```

`ifPresent()` performs an action when a value exists:

```java
optional.ifPresent(
    value -> System.out.println(value)
);
```

---

## Q50. orElse() vs orElseGet()?

### Answer

```java
orElse(defaultValue)
```

evaluates the argument before the call, even if the Optional already contains a value.

```java
orElseGet(() -> defaultValue)
```

evaluates the supplier only when the Optional is empty.

This matters when the fallback computation is expensive.

Example:

```java
String name =
    optional.orElseGet(() -> fetchDefaultName());
```

---

## Q51. orElseThrow()?

### Answer

Java 8 style:

```java
String name =
    optional.orElseThrow(
        () -> new RuntimeException("Name not found")
    );
```

It throws the supplied exception when empty.

---

## Q52. Should Optional be used everywhere?

### Answer

No.

Good uses include:

- return values where absence is expected
- making an API's optional result explicit

Avoid blindly using Optional for:

- every field
- every method parameter
- unnecessary local variables
- serialization models where it is inappropriate

---

# 17. Default Methods

## Q53. What is a default method?

### Answer

Java 8 allows interfaces to contain concrete methods using `default`.

```java
interface Payment {

    default void validate() {
        System.out.println("Validation");
    }

    void pay();
}
```

A class implementing the interface gets the default implementation unless it overrides it.

---

## Q54. Why were default methods introduced?

### Answer

Primarily to evolve interfaces without breaking all existing implementations.

For example, a new method can be added with a default implementation.

---

## Q55. What happens if two interfaces have the same default method?

### Answer

The implementing class must resolve the conflict.

```java
interface A {
    default void show() {
        System.out.println("A");
    }
}

interface B {
    default void show() {
        System.out.println("B");
    }
}

class Test implements A, B {

    @Override
    public void show() {
        A.super.show();
    }
}
```

---

## Q56. Can an interface have static methods in Java 8?

### Answer

Yes.

```java
interface Utility {

    static void print() {
        System.out.println("Hello");
    }
}
```

Call it using:

```java
Utility.print();
```

Static interface methods are not inherited as instance methods.

---

# 18. Date and Time API

## Q57. Why was the new Date/Time API introduced?

### Answer

The old date APIs such as `java.util.Date` and `Calendar` had usability and design issues.

Java 8 introduced the `java.time` API, which is clearer and designed around immutable date/time types.

Important classes:

```text
LocalDate
LocalTime
LocalDateTime
ZonedDateTime
Instant
Duration
Period
DateTimeFormatter
```

---

## Q58. LocalDate example

### Answer

```java
LocalDate today =
    LocalDate.now();

LocalDate tomorrow =
    today.plusDays(1);
```

---

## Q59. LocalDateTime vs ZonedDateTime?

### Answer

`LocalDateTime` has date and time but no time-zone information.

```java
LocalDateTime.now();
```

`ZonedDateTime` includes a time zone.

```java
ZonedDateTime.now(
    ZoneId.of("Asia/Kolkata")
);
```

Use `ZonedDateTime` when time-zone context matters.

---

## Q60. Instant vs LocalDateTime?

### Answer

`Instant` represents a point on the UTC timeline.

`LocalDateTime` represents a date/time without zone information.

For distributed systems, timestamps representing an absolute point in time are often better represented using `Instant`.

---

## Q61. Period vs Duration?

### Answer

`Period` is date-based:

```text
years
months
days
```

`Duration` is time-based:

```text
seconds
nanos
```

Example:

```java
Period.ofDays(5);

Duration.ofHours(5);
```

---

# 19. forEach()

## Q62. What is forEach() in Java 8?

### Answer

Collections and streams can use `forEach()` with a lambda.

```java
names.forEach(
    name -> System.out.println(name)
);
```

Method reference:

```java
names.forEach(System.out::println);
```

---

## Q63. forEach() vs traditional for loop?

### Answer

`forEach()` is concise and works well with functional-style processing.

Traditional loops are often preferable when you need:

- complex control flow
- `break`
- `continue`
- checked exception handling patterns
- more explicit imperative logic

Do not use streams merely to make simple code harder to understand.

---

# 20. Parallel Streams

## Q64. What is a parallel stream?

### Answer

A parallel stream divides stream processing across multiple threads, typically using the common ForkJoinPool.

```java
numbers.parallelStream()
       .map(this::process)
       .collect(Collectors.toList());
```

---

## Q65. Sequential stream vs parallel stream?

### Answer

```java
list.stream()
```

processes sequentially.

```java
list.parallelStream()
```

allows parallel processing.

Parallel streams are not automatically faster.

---

## Q66. When should you avoid parallel streams?

### Answer

Be careful when:

- the workload is small
- operations are I/O-bound
- operations have side effects
- ordering is important
- shared mutable state is used
- common pool contention matters
- you need precise thread-pool control

For backend applications, explicitly managed executors are often better when you need control over concurrency.

---

## Q67. Why is shared mutable state dangerous in streams?

### Answer

This is dangerous:

```java
List<Integer> result =
    new ArrayList<>();

numbers.parallelStream()
       .forEach(n -> result.add(n));
```

`ArrayList` is not thread-safe.

Prefer collectors:

```java
List<Integer> result =
    numbers.parallelStream()
           .collect(Collectors.toList());
```

or another design appropriate to the requirement.

---

# 21. Stream Ordering

## Q68. What is encounter order?

### Answer

Encounter order is the order in which a stream source presents elements when the source has an order.

For example, an ArrayList has encounter order.

Operations such as `forEachOrdered()` can preserve encounter order.

---

## Q69. forEach() vs forEachOrdered() in parallel streams?

### Answer

```java
parallelStream().forEach(...)
```

does not guarantee encounter-order output.

```java
parallelStream().forEachOrdered(...)
```

preserves encounter order for the terminal operation, potentially reducing some parallelism benefits.

---

# 22. Stream Performance and Traps

## Q70. Does filter() modify the original collection?

### Answer

No, stream operations normally create a processing pipeline rather than modifying the source collection.

Example:

```java
List<Integer> numbers =
    Arrays.asList(1, 2, 3);

List<Integer> result =
    numbers.stream()
           .filter(n -> n > 1)
           .collect(Collectors.toList());
```

`numbers` remains unchanged.

---

## Q71. What is short-circuiting in streams?

### Answer

Some operations can stop processing once the result is known.

Examples:

```text
findFirst
findAny
anyMatch
allMatch
noneMatch
limit
```

Example:

```java
boolean found =
    numbers.stream()
           .anyMatch(n -> n > 100);
```

The stream can stop once a matching element is found.

---

## Q72. What is the difference between map() and peek()?

### Answer

`map()` is intended for transformation.

```java
stream.map(Employee::getName)
```

`peek()` is mainly intended for observing elements during pipeline execution, often for debugging.

```java
stream.peek(e -> log.info("{}", e))
```

Do not rely on `peek()` for essential business side effects.

---

# 23. Common Java 8 Coding Questions

## Q73. Find even numbers from a List

### Answer

```java
List<Integer> result =
    numbers.stream()
           .filter(n -> n % 2 == 0)
           .collect(Collectors.toList());
```

---

## Q74. Find duplicate numbers

### Answer

```java
Set<Integer> seen =
    new HashSet<>();

Set<Integer> duplicates =
    numbers.stream()
           .filter(n -> !seen.add(n))
           .collect(Collectors.toSet());
```

For sequential stream processing this is a common solution. Avoid this mutable-state pattern in parallel streams.

---

## Q75. Find first non-repeating element

### Answer

```java
Map<Integer, Long> frequency =
    numbers.stream()
           .collect(Collectors.groupingBy(
               Function.identity(),
               LinkedHashMap::new,
               Collectors.counting()
           ));

Optional<Integer> result =
    frequency.entrySet().stream()
        .filter(e -> e.getValue() == 1)
        .map(Map.Entry::getKey)
        .findFirst();
```

`LinkedHashMap` preserves encounter/insertion order for the frequency map.

---

## Q76. Find maximum number

### Answer

```java
Optional<Integer> max =
    numbers.stream()
           .max(Integer::compareTo);
```

---

## Q77. Find second-highest number

### Answer

```java
Optional<Integer> secondHighest =
    numbers.stream()
           .distinct()
           .sorted(Comparator.reverseOrder())
           .skip(1)
           .findFirst();
```

For very large datasets, discuss whether sorting is appropriate; sorting costs O(n log n).

---

## Q78. Count occurrences of each word

### Answer

```java
Map<String, Long> frequency =
    words.stream()
         .collect(Collectors.groupingBy(
             Function.identity(),
             Collectors.counting()
         ));
```

---

## Q79. Find the longest string

### Answer

```java
Optional<String> longest =
    names.stream()
         .max(Comparator.comparingInt(String::length));
```

---

## Q80. Convert List<String> to uppercase

### Answer

```java
List<String> result =
    names.stream()
         .map(String::toUpperCase)
         .collect(Collectors.toList());
```

---

# 24. Employee-Based Interview Problems

## Q81. Find employees with salary > 1 lakh

### Answer

```java
List<Employee> result =
    employees.stream()
        .filter(e -> e.getSalary() > 100000)
        .collect(Collectors.toList());
```

---

## Q82. Sort employees by salary descending

### Answer

```java
List<Employee> result =
    employees.stream()
        .sorted(
            Comparator.comparing(Employee::getSalary)
                      .reversed()
        )
        .collect(Collectors.toList());
```

---

## Q83. Sort by salary, then name

### Answer

```java
List<Employee> result =
    employees.stream()
        .sorted(
            Comparator.comparing(Employee::getSalary)
                      .thenComparing(Employee::getName)
        )
        .collect(Collectors.toList());
```

---

## Q84. Group employees by department

### Answer

```java
Map<String, List<Employee>> result =
    employees.stream()
        .collect(Collectors.groupingBy(
            Employee::getDepartment
        ));
```

---

## Q85. Count employees in each department

### Answer

```java
Map<String, Long> result =
    employees.stream()
        .collect(Collectors.groupingBy(
            Employee::getDepartment,
            Collectors.counting()
        ));
```

---

## Q86. Find highest-paid employee in each department

### Answer

```java
Map<String, Optional<Employee>> result =
    employees.stream()
        .collect(Collectors.groupingBy(
            Employee::getDepartment,
            Collectors.maxBy(
                Comparator.comparing(Employee::getSalary)
            )
        ));
```

---

## Q87. Find average salary by department

### Answer

```java
Map<String, Double> result =
    employees.stream()
        .collect(Collectors.groupingBy(
            Employee::getDepartment,
            Collectors.averagingDouble(
                Employee::getSalary
            )
        ));
```

---

## Q88. Find employees whose name starts with V

### Answer

```java
List<Employee> result =
    employees.stream()
        .filter(e -> e.getName().startsWith("V"))
        .collect(Collectors.toList());
```

---

## Q89. Convert employees to Map<id, name>

### Answer

```java
Map<Integer, String> result =
    employees.stream()
        .collect(Collectors.toMap(
            Employee::getId,
            Employee::getName
        ));
```

If IDs can duplicate, supply a merge function.

---

# 25. CompletableFuture

## Q90. What is CompletableFuture?

### Answer

`CompletableFuture` provides an API for asynchronous and composable computations.

It implements `Future` and `CompletionStage`.

Example:

```java
CompletableFuture<String> future =
    CompletableFuture.supplyAsync(
        () -> "Hello"
    );

System.out.println(future.join());
```

---

## Q91. runAsync() vs supplyAsync()?

### Answer

`runAsync()` is for asynchronous work that does not return a result.

```java
CompletableFuture.runAsync(
    () -> sendEmail()
);
```

`supplyAsync()` returns a result.

```java
CompletableFuture<String> future =
    CompletableFuture.supplyAsync(
        () -> fetchUser()
    );
```

Memory:

```text
runAsync    → Runnable → no result
supplyAsync → Supplier → result
```

---

## Q92. thenApply() vs thenAccept() vs thenRun()?

### Answer

### thenApply()

Transforms the result.

```java
future.thenApply(
    value -> value.toUpperCase()
);
```

### thenAccept()

Consumes the result and returns no result.

```java
future.thenAccept(
    value -> System.out.println(value)
);
```

### thenRun()

Runs an action without receiving the previous result.

```java
future.thenRun(
    () -> System.out.println("Done")
);
```

Memory:

```text
thenApply  → transform
thenAccept → consume
thenRun    → just run
```

---

## Q93. thenCompose() vs thenCombine()?

### Answer

`thenCompose()` chains dependent asynchronous operations.

```text
Future A
   ↓
Future B
```

Use it when B depends on A.

`thenCombine()` combines two independent futures.

```text
Future A ─┐
          ├→ combined result
Future B ─┘
```

Example:

```java
CompletableFuture<User> userFuture = ...;
CompletableFuture<Order> orderFuture = ...;

CompletableFuture<String> result =
    userFuture.thenCombine(
        orderFuture,
        (user, order) ->
            user.getName() + ":" + order.getId()
    );
```

---

## Q94. exceptionally() vs handle()?

### Answer

`exceptionally()` handles an exception and can provide a fallback result.

```java
future.exceptionally(
    ex -> "default"
);
```

`handle()` receives both the result and exception.

```java
future.handle((result, ex) -> {
    if (ex != null) {
        return "default";
    }
    return result;
});
```

---

## Q95. What is allOf()?

### Answer

`CompletableFuture.allOf()` completes when all supplied futures complete.

```java
CompletableFuture<Void> all =
    CompletableFuture.allOf(
        future1,
        future2,
        future3
    );
```

It is useful when several independent asynchronous operations must all finish before continuing.

---

# 26. Java 8 Interview Traps

## Q96. Can a lambda access local variables?

### Answer

Yes, but local variables captured by a lambda must be **final or effectively final**.

Valid:

```java
int x = 10;

Runnable r =
    () -> System.out.println(x);
```

Invalid:

```java
int x = 10;

x++;

Runnable r =
    () -> System.out.println(x);
```

because `x` is no longer effectively final.

---

## Q97. Can lambda modify an instance variable?

### Answer

Yes.

```java
class Test {

    private int count;

    void test() {
        Runnable r =
            () -> count++;
    }
}
```

The effectively-final restriction applies to captured local variables, not ordinary instance fields.

---

## Q98. Can an interface have a default method and abstract method?

### Answer

Yes.

```java
interface Payment {

    default void validate() {
        System.out.println("Validation");
    }

    void pay();
}
```

It remains a functional interface because only `pay()` is abstract.

---

## Q99. Can a functional interface have Object methods?

### Answer

Methods corresponding to public methods of `Object` do not count as additional abstract methods for the functional-interface definition.

---

## Q100. Why can't you modify a collection inside a stream in many cases?

### Answer

Because modifying the source while it is being traversed can cause:

```text
ConcurrentModificationException
```

or unpredictable behavior.

Bad:

```java
list.stream()
    .forEach(x -> list.remove(x));
```

Prefer producing a new result:

```java
List<Integer> result =
    list.stream()
        .filter(x -> x > 10)
        .collect(Collectors.toList());
```

---

# 27. Senior-Level Scenario Questions

## Q101. You have 10 million records. Should you use streams?

### Answer

Streams can be used, but the decision depends on:

```text
Memory
CPU cost
I/O
Pipeline complexity
Parallelism
Latency
Maintainability
```

Streams do not automatically make processing faster.

For large data processing, also consider:

```text
batching
database-side processing
pagination
streaming I/O
parallel workers
backpressure
```

---

## Q102. When would you prefer a loop over a stream?

### Answer

Prefer a loop when:

- logic is highly stateful
- complex control flow is needed
- early exit is complicated
- checked exception handling is clearer imperatively
- a stream would reduce readability
- performance profiling shows a meaningful benefit

Streams are a tool, not a rule.

---

## Q103. Are streams reusable?

### Answer

No.

Once a terminal operation is executed, the stream is consumed.

```java
Stream<Integer> stream =
    numbers.stream();

stream.count();

stream.forEach(System.out::println); // error
```

A stream should be recreated from the source if needed.

---

## Q104. Are streams thread-safe?

### Answer

A Stream itself is not a thread-safe collection.

Parallel streams can execute operations concurrently, so shared mutable state inside stream operations must be avoided or properly coordinated.

---

## Q105. What is the common ForkJoinPool in parallel streams?

### Answer

Parallel streams normally use the common `ForkJoinPool`.

This matters in server applications because unrelated tasks can also use shared common-pool resources.

If you require precise executor/thread-pool control, consider using an explicitly managed executor and a design appropriate to your workload.

---

# 28. Java 8 API Quick Reference

```text
Lambda
    (x) -> x * 2

Predicate<T>
    T -> boolean

Function<T,R>
    T -> R

Consumer<T>
    T -> void

Supplier<T>
    () -> T

Stream
    filter
    map
    flatMap
    distinct
    sorted
    limit
    skip
    reduce
    collect

Collectors
    toList
    toSet
    toMap
    joining
    groupingBy
    partitioningBy
    counting
    averaging
    maxBy
    minBy

Optional
    of
    ofNullable
    empty
    map
    flatMap
    filter
    ifPresent
    orElse
    orElseGet
    orElseThrow

CompletableFuture
    runAsync
    supplyAsync
    thenApply
    thenAccept
    thenRun
    thenCompose
    thenCombine
    exceptionally
    handle
    allOf
```

# 29. Final Java 8 Interview Checklist

## 🔴 Must Master

```text
Lambda expressions
Functional interfaces
Predicate
Function
Consumer
Supplier
Method references
Stream API
filter
map
flatMap
distinct
sorted
reduce
collect
Collectors
groupingBy
partitioningBy
toMap
Optional
of vs ofNullable
orElse vs orElseGet
Default methods
Comparable/Comparator with streams
Java 8 Date/Time API
```

## 🟡 Very Important for Backend

```text
Parallel streams
Stream lazy evaluation
Short-circuit operations
Stream ordering
CompletableFuture
thenApply
thenCompose
thenCombine
exceptionally
handle
allOf
```

## 🟢 Awareness

```text
ForkJoinPool
Nashorn
Advanced Collector implementations
Custom Spliterator
Primitive streams
```

# Interview Follow-Up Checklist

For every Java 8 coding question, be ready to answer:

```text
1. Why did you choose Stream API?
2. Could you solve it using a normal loop?
3. What is the time complexity?
4. What is the space complexity?
5. Is the operation lazy?
6. Which operation is terminal?
7. Does it preserve ordering?
8. What happens with null?
9. Can this be parallelized?
10. Is there shared mutable state?
11. What happens with duplicate keys in toMap()?
12. Why use Optional here?
13. Why orElseGet() instead of orElse()?
14. Would this be suitable for production?
15. Would you use CompletableFuture for this scenario?
