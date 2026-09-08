# Java Interview Complete Roadmap — Sections 18–21

Interview-focused revision notes for **3–5 YOE Java interviews**.

Each topic includes:
- Problem / goal
- Core idea
- Proper Java code
- Explanation
- Time and space complexity

---

# 18. Math / Number Programs

## 1. FizzBuzz

### Problem

For numbers from `1` to `n`:

- Divisible by 3 → `Fizz`
- Divisible by 5 → `Buzz`
- Divisible by both → `FizzBuzz`
- Otherwise → print the number

### Code

```java
static void fizzBuzz(int n) {

    for (int i = 1; i <= n; i++) {

        if (i % 15 == 0) {
            System.out.println("FizzBuzz");

        } else if (i % 3 == 0) {
            System.out.println("Fizz");

        } else if (i % 5 == 0) {
            System.out.println("Buzz");

        } else {
            System.out.println(i);
        }
    }
}
```

### Explanation

Check divisibility by `15` first because a number divisible by both 3 and 5 is divisible by 15.

### Complexity

- Time: `O(n)`
- Space: `O(1)`

---

## 2. Random Number in a Range — ThreadLocalRandom

### Problem

Generate a random number between `10` and `20`.

### Code

```java
import java.util.concurrent.ThreadLocalRandom;

int value =
        ThreadLocalRandom.current().nextInt(10, 21);

System.out.println(value);
```

### Explanation

`nextInt(origin, bound)` uses:

```text
origin = inclusive
bound  = exclusive
```

Therefore:

```java
nextInt(10, 21)
```

generates:

```text
10, 11, 12, ... 19, 20
```

### Complexity

- Time: `O(1)`
- Space: `O(1)`

---

## 3. Decimal to Binary

### Code

```java
static String toBinary(int n) {

    if (n == 0) {
        return "0";
    }

    StringBuilder sb = new StringBuilder();

    while (n > 0) {
        sb.append(n % 2);
        n /= 2;
    }

    return sb.reverse().toString();
}
```

### Explanation

Repeatedly divide by `2`.

For example:

```text
10 / 2 → remainder 0
5  / 2 → remainder 1
2  / 2 → remainder 0
1  / 2 → remainder 1
```

Remainders:

```text
0101
```

Reverse:

```text
1010
```

Therefore:

```text
10 → 1010
```

### Complexity

- Time: `O(log n)`
- Space: `O(log n)` for the output

---

## 4. Decimal to Octal

### Code

```java
static String toOctal(int n) {

    if (n == 0) {
        return "0";
    }

    StringBuilder sb = new StringBuilder();

    while (n > 0) {
        sb.append(n % 8);
        n /= 8;
    }

    return sb.reverse().toString();
}
```

### Explanation

Same concept as binary conversion, but divide by `8`.

### Complexity

- Time: `O(log n)`
- Space: `O(log n)`

---

## 5. Decimal to Hexadecimal

### Code

```java
static String toHex(int n) {

    if (n == 0) {
        return "0";
    }

    String digits = "0123456789ABCDEF";

    StringBuilder sb = new StringBuilder();

    while (n > 0) {

        int remainder = n % 16;

        sb.append(digits.charAt(remainder));

        n /= 16;
    }

    return sb.reverse().toString();
}
```

### Explanation

Hexadecimal uses base `16`.

```text
0–9 → 0–9
10  → A
11  → B
12  → C
13  → D
14  → E
15  → F
```

### Complexity

- Time: `O(log n)`
- Space: `O(log n)`

---

## 6. Prime Check

### Code

```java
static boolean isPrime(int n) {

    if (n < 2) {
        return false;
    }

    for (int i = 2; i <= n / i; i++) {

        if (n % i == 0) {
            return false;
        }
    }

    return true;
}
```

### Explanation

A prime number has exactly two factors:

```text
1 and itself
```

We don't need to check all numbers up to `n`.

We only check up to:

```text
sqrt(n)
```

For example:

```text
36
```

If 36 has a factor larger than sqrt(36), the corresponding factor pair will have another factor smaller than sqrt(36).

### Why `i <= n / i`?

Instead of:

```java
i * i <= n
```

we can use:

```java
i <= n / i
```

to avoid integer overflow.

### Complexity

- Time: `O(sqrt(n))`
- Space: `O(1)`

---

## 7. Sum of Digits

### Code

```java
static int sumDigits(int n) {

    n = Math.abs(n);

    int sum = 0;

    while (n > 0) {

        int digit = n % 10;

        sum += digit;

        n /= 10;
    }

    return sum;
}
```

### Example

```text
1234

4 → sum = 4
3 → sum = 7
2 → sum = 9
1 → sum = 10
```

Answer:

```text
10
```

### Key Pattern

```text
digit = n % 10
n = n / 10
```

Remember this pattern for many number problems.

### Complexity

- Time: `O(log n)`
- Space: `O(1)`

---

## 8. Armstrong Number

### Problem

A number is Armstrong if:

```text
sum of each digit ^ number of digits = original number
```

Example:

```text
153

1³ + 5³ + 3³
= 1 + 125 + 27
= 153
```

### Code

```java
static boolean isArmstrong(int n) {

    if (n < 0) {
        return false;
    }

    int original = n;

    int digits =
            String.valueOf(n).length();

    int sum = 0;

    while (n > 0) {

        int digit = n % 10;

        sum += (int) Math.pow(digit, digits);

        n /= 10;
    }

    return sum == original;
}
```

### Complexity

If `d` is the number of digits:

- Time: `O(d)`
- Space: `O(1)` extra space

---

## 9. Perfect Number

### Problem

A perfect number equals the sum of its proper divisors.

Example:

```text
28

1 + 2 + 4 + 7 + 14 = 28
```

### Code

```java
static boolean isPerfect(int n) {

    if (n <= 1) {
        return false;
    }

    int sum = 1;

    for (int i = 2; i <= n / i; i++) {

        if (n % i == 0) {

            sum += i;

            if (i != n / i) {
                sum += n / i;
            }
        }
    }

    return sum == n;
}
```

### Explanation

Divisors occur in pairs.

For:

```text
28
```

we have:

```text
2 × 14
4 × 7
```

So when we find `2`, we also add `14`.

### Complexity

- Time: `O(sqrt(n))`
- Space: `O(1)`

---

## 10. Prime Factors

### Code

```java
static void primeFactors(int n) {

    for (int factor = 2;
         factor <= n / factor;
         factor++) {

        while (n % factor == 0) {

            System.out.print(factor + " ");

            n /= factor;
        }
    }

    if (n > 1) {
        System.out.print(n);
    }
}
```

### Example

```text
n = 60

60 / 2 = 30
30 / 2 = 15
15 / 3 = 5
5 is prime
```

Output:

```text
2 2 3 5
```

### Complexity

- Time: approximately `O(sqrt(n))`
- Space: `O(1)`

---

## 11. Roman Numeral Conversion

### Problem

Convert:

```text
1994 → MCMXCIV
```

### Code

```java
static String intToRoman(int num) {

    int[] values = {
        1000, 900, 500, 400,
        100, 90, 50, 40,
        10, 9, 5, 4, 1
    };

    String[] symbols = {
        "M", "CM", "D", "CD",
        "C", "XC", "L", "XL",
        "X", "IX", "V", "IV", "I"
    };

    StringBuilder result =
            new StringBuilder();

    for (int i = 0; i < values.length; i++) {

        while (num >= values[i]) {

            result.append(symbols[i]);

            num -= values[i];
        }
    }

    return result.toString();
}
```

### Explanation

Use a greedy approach.

Always consume the largest possible Roman value first.

Important subtractive values:

```text
4   → IV
9   → IX
40  → XL
90  → XC
400 → CD
900 → CM
```

### Complexity

For the standard integer Roman numeral range:

- Time: `O(1)`
- Space: `O(1)` excluding output

---

## 12. Trigonometric Values — Math API

### Code

```java
double degrees = 30;

double radians =
        Math.toRadians(degrees);

double sin = Math.sin(radians);
double cos = Math.cos(radians);
double tan = Math.tan(radians);

System.out.println("sin = " + sin);
System.out.println("cos = " + cos);
System.out.println("tan = " + tan);
```

### Important Interview Point

Java's:

```java
Math.sin()
Math.cos()
Math.tan()
```

expect **radians**, not degrees.

Convert:

```java
Math.toRadians(degrees)
```

### Complexity

- Time: `O(1)`
- Space: `O(1)`

---

## 13. Largest Number Less Than N Without a Given Digit

### Example

Given:

```text
N = 500
forbidden digit = 5
```

Find the largest number `< 500` that doesn't contain `5`.

### Simple Interview Solution

```java
static int largestWithoutDigit(
        int n,
        int forbiddenDigit) {

    for (int candidate = n - 1;
         candidate >= 0;
         candidate--) {

        if (!String.valueOf(candidate)
                .contains(
                    String.valueOf(forbiddenDigit))) {

            return candidate;
        }
    }

    return -1;
}
```

### Explanation

Start at:

```text
N - 1
```

and move downward.

The first valid number we find is automatically the largest valid number.

### Complexity

Worst case:

```text
O(N × digits)
```

This straightforward approach is good when `N` is moderate.

---

# 19. File I/O

## 1. Append / Write Text to File — Writer

### Code

```java
import java.io.FileWriter;
import java.io.IOException;

static void appendText(
        String file,
        String text) throws IOException {

    try (FileWriter writer =
             new FileWriter(file, true)) {

        writer.write(text);

        writer.write(
            System.lineSeparator()
        );
    }
}
```

### Important Point

This:

```java
new FileWriter(file, true)
```

means:

```text
true → append mode
```

Without `true`, existing content can be overwritten.

### Complexity

- Time: `O(L)` where L is text length
- Space: `O(1)` extra

---

## 2. Count Characters, Words and Lines

### Code

```java
import java.io.*;

static void countFile(
        String file) throws IOException {

    int lines = 0;
    int words = 0;
    int chars = 0;

    try (BufferedReader br =
             new BufferedReader(
                 new FileReader(file))) {

        String line;

        while ((line = br.readLine()) != null) {

            lines++;

            chars += line.length();

            String trimmed =
                    line.trim();

            if (!trimmed.isEmpty()) {

                words +=
                    trimmed.split("\\s+").length;
            }
        }
    }

    System.out.println(
        "Lines = " + lines);

    System.out.println(
        "Words = " + words);

    System.out.println(
        "Characters = " + chars);
}
```

### Explanation

`BufferedReader` lets us read the file efficiently line by line.

For every line:

```text
lines++
```

Count characters:

```text
line.length()
```

Count words:

```text
split("\\s+")
```

`\\s+` means one or more whitespace characters.

### Complexity

- Time: `O(file size)`
- Working space: `O(line length)`

---

## 3. Most Frequent Word in File

### Code

```java
import java.io.*;
import java.util.*;

static String mostFrequentWord(
        String file) throws IOException {

    Map<String, Integer> frequency =
            new HashMap<>();

    try (BufferedReader br =
             new BufferedReader(
                 new FileReader(file))) {

        String line;

        while ((line = br.readLine()) != null) {

            String[] words =
                    line.toLowerCase()
                        .split("[^a-z0-9]+");

            for (String word : words) {

                if (!word.isEmpty()) {

                    frequency.merge(
                        word,
                        1,
                        Integer::sum
                    );
                }
            }
        }
    }

    return frequency.entrySet()
            .stream()
            .max(
                Map.Entry.comparingByValue()
            )
            .map(Map.Entry::getKey)
            .orElse(null);
}
```

### Key Idea

Use:

```text
File
 ↓
BufferedReader
 ↓
Extract words
 ↓
HashMap<String, Integer>
 ↓
Count frequency
 ↓
Find maximum
```

### Complexity

- Time: `O(C + W)` average
- Space: `O(U)`

Where:

```text
C = characters
W = words
U = unique words
```

---

## 4. Sort Text File

### Code

```java
import java.io.*;
import java.util.*;

static void sortFile(
        String input,
        String output) throws IOException {

    List<String> lines =
            new ArrayList<>();

    try (BufferedReader br =
             new BufferedReader(
                 new FileReader(input))) {

        String line;

        while ((line = br.readLine()) != null) {
            lines.add(line);
        }
    }

    Collections.sort(lines);

    try (BufferedWriter bw =
             new BufferedWriter(
                 new FileWriter(output))) {

        for (String line : lines) {

            bw.write(line);

            bw.newLine();
        }
    }
}
```

### Pattern

```text
Read
 ↓
List
 ↓
Sort
 ↓
Write
```

### Complexity

For `L` lines:

- Time: `O(L log L)`
- Space: `O(L)`

---

## 5. Try-with-resources

### Code

```java
try (BufferedReader br =
         new BufferedReader(
             new FileReader("input.txt"))) {

    String line;

    while ((line = br.readLine()) != null) {
        System.out.println(line);
    }
}
```

### Why use it?

Without try-with-resources, you must manually close resources.

With it:

```text
try starts
   ↓
use resource
   ↓
try finishes
   ↓
resource automatically closes
```

It also closes the resource when an exception occurs.

### Interview Point

Resources used in try-with-resources must implement:

```java
AutoCloseable
```

---

# 20. Java 8 Streams / Lambda

## 1. Generate Random Numbers Using forEach

### Code

```java
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

IntStream.generate(() ->
        ThreadLocalRandom.current()
                .nextInt(100))
    .limit(10)
    .forEach(System.out::println);
```

### Explanation

```text
generate()
   ↓
creates values
   ↓
limit(10)
   ↓
take 10 values
   ↓
forEach()
   ↓
process values
```

### Complexity

For `n` generated values:

- Time: `O(n)`

---

## 2. Unique Squares — map + distinct

### Code

```java
List<Integer> result =
        Arrays.asList(1, 2, 2, 3, 4)
            .stream()
            .map(x -> x * x)
            .distinct()
            .collect(Collectors.toList());
```

Output:

```text
[1, 4, 9, 16]
```

### Explanation

`map()`:

```text
1 → 1
2 → 4
2 → 4
3 → 9
4 → 16
```

Then:

```text
distinct()
```

removes duplicate `4`.

### Complexity

- Time: `O(n)` average
- Space: `O(n)`

---

## 3. Filter Empty Strings / Lines

### Code

```java
List<String> result =
        Arrays.asList(
            "Java",
            "",
            "Spring",
            " ",
            "Boot"
        )
        .stream()
        .filter(s -> !s.trim().isEmpty())
        .collect(Collectors.toList());
```

Output:

```text
[Java, Spring, Boot]
```

### Explanation

`filter()` keeps only elements for which the condition is true.

### Java 8 Note

For Java 11+, you can also use:

```java
s -> !s.isBlank()
```

But `trim().isEmpty()` works for Java 8.

---

## 4. Sort Random Numbers

### Code

```java
List<Integer> numbers =
        new Random()
            .ints(10, 1, 100)
            .boxed()
            .sorted()
            .collect(Collectors.toList());
```

### Explanation

```text
ints()
 ↓
primitive int stream
 ↓
boxed()
 ↓
Integer stream
 ↓
sorted()
 ↓
collect()
```

### Complexity

- Time: `O(n log n)`
- Space: `O(n)`

---

## 5. Find Maximum / Minimum

### Code

```java
List<Integer> numbers =
        Arrays.asList(10, 4, 25, 7);

int max =
        numbers.stream()
            .max(Integer::compareTo)
            .orElseThrow(
                NoSuchElementException::new
            );

int min =
        numbers.stream()
            .min(Integer::compareTo)
            .orElseThrow(
                NoSuchElementException::new
            );
```

### Why Optional?

A stream may be empty.

Therefore:

```java
max()
```

returns:

```java
Optional<Integer>
```

instead of directly returning `Integer`.

### Complexity

- Time: `O(n)`
- Extra space: `O(1)`

---

## 6. Sum / Average

### Code

```java
List<Integer> numbers =
        Arrays.asList(10, 20, 30, 40);

int sum =
        numbers.stream()
            .mapToInt(Integer::intValue)
            .sum();

double average =
        numbers.stream()
            .mapToInt(Integer::intValue)
            .average()
            .orElse(0.0);
```

### Important Point

Use:

```java
mapToInt()
```

for numeric operations to avoid unnecessary boxing.

### Complexity

- Time: `O(n)`
- Extra space: `O(1)`

---

## 7. groupingBy + counting

### Code

```java
Map<String, Long> counts =
        Arrays.asList(
            "Java",
            "Java",
            "Spring",
            "Java"
        )
        .stream()
        .collect(
            Collectors.groupingBy(
                s -> s,
                Collectors.counting()
            )
        );
```

Output conceptually:

```text
Java   → 3
Spring → 1
```

### Explanation

`groupingBy()`:

```text
same key → same group
```

`counting()`:

```text
count elements in each group
```

### Complexity

- Time: `O(n)` average
- Space: `O(k)` for groups

---

## 8. toMap

### Code

```java
Map<String, Integer> lengths =
        Arrays.asList(
            "Alice",
            "Bob",
            "Charlie"
        )
        .stream()
        .collect(
            Collectors.toMap(
                name -> name,
                String::length
            )
        );
```

Result:

```text
Alice   → 5
Bob     → 3
Charlie → 7
```

### Important Interview Point

If duplicate keys are possible, provide a merge function:

```java
Collectors.toMap(
    keyMapper,
    valueMapper,
    (oldValue, newValue) -> oldValue
)
```

Otherwise duplicate keys can cause an exception.

---

## 9. flatMap

### Problem

Convert:

```text
[[1, 2], [3, 4], [5]]
```

into:

```text
[1, 2, 3, 4, 5]
```

### Code

```java
List<List<Integer>> nested =
        Arrays.asList(
            Arrays.asList(1, 2),
            Arrays.asList(3, 4),
            Arrays.asList(5)
        );

List<Integer> flat =
        nested.stream()
            .flatMap(List::stream)
            .collect(Collectors.toList());
```

### map vs flatMap

`map()`:

```text
List<List<Integer>>
        ↓
Stream<List<Integer>>
```

`flatMap()`:

```text
List<List<Integer>>
        ↓
Stream<Integer>
```

### Memory Trick

> `flatMap = map + flatten`

---

## 10. reduce

### Code

```java
int sum =
        Arrays.asList(1, 2, 3, 4)
            .stream()
            .reduce(
                0,
                Integer::sum
            );

System.out.println(sum);
```

Output:

```text
10
```

### Explanation

Conceptually:

```text
0 + 1 = 1
1 + 2 = 3
3 + 3 = 6
6 + 4 = 10
```

Here:

```text
0              → identity
Integer::sum   → accumulator
```

### Complexity

- Time: `O(n)`
- Extra space: `O(1)` for sequential reduction

---

## 11. Sort List with Lambda

### Code

```java
List<String> names =
        new ArrayList<>(
            Arrays.asList(
                "Bob",
                "alice",
                "Charlie"
            )
        );

names.sort(
    (a, b) -> a.compareToIgnoreCase(b)
);
```

### Explanation

The lambda is a `Comparator`.

It defines how two elements should be compared.

### Complexity

- Time: `O(n log n)`

---

## 12. Functional Interfaces

### Code

```java
@FunctionalInterface
interface Calculator {

    int operate(int a, int b);
}

Calculator add =
        (a, b) -> a + b;

Calculator multiply =
        (a, b) -> a * b;

System.out.println(
    add.operate(2, 3)
);

System.out.println(
    multiply.operate(2, 3)
);
```

### What is a Functional Interface?

An interface with exactly **one abstract method**.

Examples from Java:

```text
Predicate<T>
Function<T,R>
Consumer<T>
Supplier<T>
```

### Memory Trick

```text
Predicate  → T → boolean
Function   → T → R
Consumer   → T → void
Supplier   → () → T
```

---

## 13. Method References

### Code

```java
List<String> names =
        Arrays.asList(
            "Alice",
            "Bob",
            "Charlie"
        );

names.forEach(
    System.out::println
);

List<Integer> lengths =
        names.stream()
            .map(String::length)
            .collect(Collectors.toList());
```

### Lambda Equivalent

This:

```java
System.out::println
```

is similar to:

```java
x -> System.out.println(x)
```

This:

```java
String::length
```

is similar to:

```java
s -> s.length()
```

### Memory Trick

> Method reference = shorter lambda when an existing method already matches.

---

## 14. Optional

### Code

```java
Optional<String> name =
        Optional.ofNullable(getName());

String result =
        name
            .map(String::trim)
            .filter(s -> !s.isEmpty())
            .orElse("Unknown");
```

### Explanation

`Optional` represents:

```text
value present
OR
value absent
```

Useful methods:

```text
of()
ofNullable()
isPresent()
ifPresent()
map()
filter()
orElse()
orElseGet()
orElseThrow()
```

### Interview Point

Avoid blindly doing:

```java
optional.get()
```

because the Optional may be empty.

---

# 21. Date & Time API

## 1. Current Date — LocalDate

### Code

```java
import java.time.LocalDate;

LocalDate today =
        LocalDate.now();

System.out.println(today);
```

### Explanation

`LocalDate` represents:

```text
date only
```

Example:

```text
2026-09-02
```

It does not contain time or timezone.

### Complexity

- Time: `O(1)`
- Space: `O(1)`

---

## 2. Add Week / Month / Year / Decade

### Code

```java
LocalDate date =
        LocalDate.now();

LocalDate nextWeek =
        date.plusWeeks(1);

LocalDate nextMonth =
        date.plusMonths(1);

LocalDate nextYear =
        date.plusYears(1);

LocalDate nextDecade =
        date.plusYears(10);
```

### Important Point

Java Date/Time objects are immutable.

This:

```java
date.plusDays(1);
```

does not change `date`.

It returns a new object.

---

## 3. Find Next Tuesday — TemporalAdjusters

### Code

```java
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;

LocalDate nextTuesday =
        LocalDate.now()
            .with(
                TemporalAdjusters.next(
                    DayOfWeek.TUESDAY
                )
            );

System.out.println(nextTuesday);
```

### Explanation

`TemporalAdjusters` provides ready-made calendar adjustments.

```text
next(TUESDAY)
```

finds the next Tuesday after the current date.

### Complexity

- Time: `O(1)`
- Space: `O(1)`

---

## 4. Current Time to Milliseconds — Instant

### Code

```java
import java.time.Instant;

long millis =
        Instant.now().toEpochMilli();

System.out.println(millis);
```

### Explanation

`Instant` represents a point on the UTC timeline.

`toEpochMilli()` gives milliseconds since:

```text
1970-01-01T00:00:00Z
```

### Complexity

- Time: `O(1)`
- Space: `O(1)`

---

## 5. Second Saturday of a Month

### Code

```java
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;

LocalDate firstDay =
        LocalDate.of(2026, 9, 1);

LocalDate secondSaturday =
        firstDay
            .with(
                TemporalAdjusters.firstInMonth(
                    DayOfWeek.SATURDAY
                )
            )
            .plusWeeks(1);

System.out.println(secondSaturday);
```

### Explanation

Step 1:

```text
Find first Saturday
```

Step 2:

```text
Add one week
```

Result:

```text
Second Saturday
```

### Complexity

- Time: `O(1)`
- Space: `O(1)`

---

## 6. Current Local Time — LocalDateTime / ZoneId

### Code

```java
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

LocalDateTime local =
        LocalDateTime.now();

ZonedDateTime mumbai =
        ZonedDateTime.now(
            ZoneId.of("Asia/Kolkata")
        );

System.out.println(local);
System.out.println(mumbai);
```

### Difference

### LocalDate

```text
Date only
```

### LocalDateTime

```text
Date + Time
```

### ZonedDateTime

```text
Date + Time + Timezone
```

### Instant

```text
Point on UTC timeline
```

### Interview Tip

If timezone matters in a distributed application, don't blindly use `LocalDateTime`.

---

## 7. equals() / hashCode() Contract

### Code

```java
import java.util.Objects;

class Employee {

    private int id;
    private String name;

    @Override
    public boolean equals(Object obj) {

        if (this == obj) {
            return true;
        }

        if (!(obj instanceof Employee)) {
            return false;
        }

        Employee other =
                (Employee) obj;

        return id == other.id
                && Objects.equals(
                    name,
                    other.name
                );
    }

    @Override
    public int hashCode() {

        return Objects.hash(
            id,
            name
        );
    }
}
```

### Core Contract

If:

```java
a.equals(b)
```

is `true`, then:

```java
a.hashCode() == b.hashCode()
```

must also be true.

### Why Important?

Especially important when objects are used in:

```java
HashMap
HashSet
HashMap keys
```

### Golden Interview Rule

> **If you override equals(), override hashCode() as well.**

---

# Final Interview Cheat Sheet

## Math

```text
Prime
→ check up to sqrt(n)

Digit problems
→ n % 10
→ n / 10

Base conversion
→ repeatedly divide by base

Armstrong
→ digit ^ numberOfDigits

Perfect number
→ sum of proper divisors

Prime factors
→ repeatedly divide by possible factors
```

---

## File I/O

```text
BufferedReader
→ read efficiently line by line

FileWriter
→ write / append text

BufferedWriter
→ buffered writing

try-with-resources
→ automatically closes resources
```

---

## Streams

```text
map()
→ transform

filter()
→ select

distinct()
→ remove duplicates

sorted()
→ sort

flatMap()
→ flatten

reduce()
→ combine into one result

groupingBy()
→ group

counting()
→ count

toMap()
→ create Map

forEach()
→ consume
```

---

## Lambda / Functional Interfaces

```text
Predicate<T>
→ T → boolean

Function<T,R>
→ T → R

Consumer<T>
→ T → void

Supplier<T>
→ () → T
```

Method reference:

```java
System.out::println
```

instead of:

```java
x -> System.out.println(x)
```

---

## Date & Time

```text
LocalDate
→ date only

LocalDateTime
→ date + time

ZonedDateTime
→ date + time + timezone

Instant
→ UTC timeline point

TemporalAdjusters
→ calendar-based adjustments
```

---

## equals / hashCode

```text
equals() true
      ↓
same hashCode()
```

If overriding:

```java
equals()
```

also override:

```java
hashCode()
```

---

# Most Important Interview Patterns to Memorize

### Number Pattern

```java
while (n > 0) {

    int digit = n % 10;

    // process digit

    n /= 10;
}
```

### Stream Pattern

```java
list.stream()
    .filter(...)
    .map(...)
    .sorted(...)
    .collect(Collectors.toList());
```

### File Reading Pattern

```java
try (BufferedReader br =
         new BufferedReader(
             new FileReader(file))) {

    String line;

    while ((line = br.readLine()) != null) {
        // process line
    }
}
```

### Date Adjustment Pattern

```java
LocalDate.now()
    .with(TemporalAdjusters.next(
        DayOfWeek.TUESDAY
    ));
```

### equals/hashCode Pattern

```java
@Override
public boolean equals(Object obj) {
    // compare important fields
}

@Override
public int hashCode() {
    // hash the same important fields
}
```

