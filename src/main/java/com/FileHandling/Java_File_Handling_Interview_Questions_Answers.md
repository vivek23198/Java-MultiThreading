# Java File Handling — Interview Questions & Answers

## Target Level
Java Backend Developer — 5–6 years experience

This document covers the Java file-handling questions from the preparation roadmap, with interview-friendly answers and coding examples.

---

# 1. java.io Basics

## Q1. What are the different ways to read a file in Java?

### Answer
Common approaches:
1. `BufferedReader`
2. `Files.readAllLines()`
3. `Files.lines()`
4. `Scanner`
5. `FileInputStream` for binary data

For large text files, prefer line-by-line/streaming processing rather than loading the entire file into memory.

---

## Q2. Difference between File, FileReader, BufferedReader and InputStream?

### Answer
- `File` represents a file or directory path.
- `FileReader` reads character data.
- `BufferedReader` adds buffering and `readLine()`.
- `InputStream` reads byte data.

```java
try (BufferedReader reader =
         new BufferedReader(new FileReader("employees.txt"))) {

    String line;
    while ((line = reader.readLine()) != null) {
        System.out.println(line);
    }
}
```

---

## Q3. Byte stream vs Character stream?

### Answer
**Byte streams** are for binary data such as images, PDFs and ZIP files.

Main abstractions:
```text
InputStream
OutputStream
```

**Character streams** are for text such as TXT, CSV and JSON.

Main abstractions:
```text
Reader
Writer
```

Memory trick:
```text
Binary → bytes → InputStream/OutputStream
Text   → characters → Reader/Writer
```

---

## Q4. InputStream vs Reader?

### Answer
`InputStream` works with bytes, while `Reader` works with characters.

```text
InputStream → binary data
Reader      → text data
```

---

## Q5. OutputStream vs Writer?

### Answer
`OutputStream` writes bytes.

`Writer` writes characters/text.

---

## Q6. Why do we use BufferedReader?

### Answer
It buffers character input, reducing expensive underlying I/O operations, and provides convenient `readLine()` support.

---

## Q7. FileReader vs BufferedReader?

### Answer
`FileReader` directly reads character data. `BufferedReader` wraps a `Reader`, adds buffering and provides `readLine()`.

---

## Q8. FileWriter vs BufferedWriter?

### Answer
`FileWriter` writes characters. `BufferedWriter` adds buffering and convenient methods such as `newLine()`.

```java
try (BufferedWriter writer =
         new BufferedWriter(new FileWriter("output.txt"))) {

    writer.write("Hello");
    writer.newLine();
    writer.write("Java");
}
```

---

## Q9. How do you check whether a file exists?

### Answer
Old API:
```java
File file = new File("data.txt");
if (file.exists()) {
    System.out.println("File exists");
}
```

Modern NIO:
```java
Path path = Path.of("data.txt");
if (Files.exists(path)) {
    System.out.println("File exists");
}
```

For new code, prefer `Path` + `Files`.

---

## Q10. How do you create a file?

### Answer
```java
Path path = Path.of("data.txt");

if (!Files.exists(path)) {
    Files.createFile(path);
}
```

---

## Q11. How do you delete a file?

### Answer
```java
Files.deleteIfExists(Path.of("data.txt"));
```

---

## Q12. How do you append data to an existing file?

### Answer
```java
Files.writeString(
    Path.of("log.txt"),
    "Application started
",
    StandardOpenOption.CREATE,
    StandardOpenOption.APPEND
);
```

---

# 2. Basic Coding Questions

## Q13. Read a file line by line and print Java employees

Input:
```text
101,Vivek,Java,50000
102,Rahul,Python,60000
103,Amit,Java,55000
104,Neha,Spring,70000
105,Ravi,Java,65000
```

### Answer
```java
try (BufferedReader reader =
         new BufferedReader(new FileReader("employees.txt"))) {

    String line;

    while ((line = reader.readLine()) != null) {
        String[] parts = line.split(",");

        String name = parts[1];
        String skill = parts[2];
        int salary = Integer.parseInt(parts[3]);

        if ("Java".equalsIgnoreCase(skill)) {
            System.out.println(name + " - " + salary);
        }
    }
}
```

Output:
```text
Vivek - 50000
Amit - 55000
Ravi - 65000
```

Complexity:
```text
Time: O(N)
Extra space: O(1)
```
assuming each line is processed independently.

---

## Q14. Count lines in a file

### Answer
```java
int count = 0;

try (BufferedReader reader =
         new BufferedReader(new FileReader("data.txt"))) {

    while (reader.readLine() != null) {
        count++;
    }
}

System.out.println("Lines = " + count);
```

---

## Q15. Count lines, words and characters

### Answer
```java
int lines = 0;
int words = 0;
int characters = 0;

try (BufferedReader reader =
         new BufferedReader(new FileReader("data.txt"))) {

    String line;

    while ((line = reader.readLine()) != null) {
        lines++;
        characters += line.length();

        String trimmed = line.trim();
        if (!trimmed.isEmpty()) {
            words += trimmed.split("\s+").length;
        }
    }
}

System.out.println("Lines = " + lines);
System.out.println("Words = " + words);
System.out.println("Characters = " + characters);
```

---

## Q16. Search a word in a file

### Answer
```java
int count = 0;

try (BufferedReader reader =
         new BufferedReader(new FileReader("data.txt"))) {

    String line;

    while ((line = reader.readLine()) != null) {
        String[] words = line.split("\s+");

        for (String word : words) {
            if ("Java".equalsIgnoreCase(word)) {
                count++;
            }
        }
    }
}

System.out.println("Java count = " + count);
```

---

## Q17. Copy a file

### Answer
```java
Path source = Path.of("source.txt");
Path destination = Path.of("destination.txt");

Files.copy(
    source,
    destination,
    StandardCopyOption.REPLACE_EXISTING
);
```

---

## Q18. Append data using BufferedWriter

### Answer
```java
try (BufferedWriter writer =
         new BufferedWriter(new FileWriter("employees.txt", true))) {

    writer.write("106,Rohit,Java,70000");
    writer.newLine();
}
```

---

# 3. Java NIO — Path and Files

## Q19. What is Java NIO?

### Answer
NIO means **New I/O**. It provides modern APIs for I/O and file-system operations.

Important parts:
```text
Path
Files
Paths
Channels
Buffers
WatchService
```

For file handling, `Path` + `Files` are the most important.

---

## Q20. File vs Path?

### Answer
`File` belongs to the older `java.io` API.

`Path` belongs to `java.nio.file` and provides a modern, flexible path abstraction.

```java
File file = new File("data.txt");
Path path = Path.of("data.txt");
```

---

## Q21. What is Files?

### Answer
`Files` is a utility class containing static methods for file-system operations.

Examples:
```java
Files.exists(path);
Files.createFile(path);
Files.delete(path);
Files.copy(source, target);
Files.move(source, target);
Files.readString(path);
Files.writeString(path, data);
Files.lines(path);
Files.walk(path);
```

---

## Q22. Path.of() vs Paths.get()?

### Answer
Both create a `Path`.

```java
Path path1 = Path.of("data.txt");
Path path2 = Paths.get("data.txt");
```

`Path.of()` is a modern Java choice; `Paths.get()` is also widely seen in existing code.

---

## Q23. Files.readAllLines() vs Files.lines()?

### Answer
`readAllLines()` loads all lines into a `List<String>`.

```java
List<String> lines = Files.readAllLines(path);
```

`Files.lines()` returns a lazy `Stream<String>`.

```java
try (Stream<String> lines = Files.lines(path)) {
    lines.forEach(System.out::println);
}
```

For very large files, avoid loading all lines into memory.

---

## Q24. Why should Files.lines() be closed?

### Answer
The stream may hold an underlying file resource, so use try-with-resources:

```java
try (Stream<String> lines = Files.lines(path)) {
    lines.forEach(System.out::println);
}
```

---

## Q25. Read a file using NIO

### Answer
```java
Path path = Path.of("employees.txt");

try (BufferedReader reader = Files.newBufferedReader(path)) {
    String line;

    while ((line = reader.readLine()) != null) {
        System.out.println(line);
    }
}
```

---

## Q26. Filter Java employees using Files.lines()

### Answer
```java
Path path = Path.of("employees.txt");

try (Stream<String> lines = Files.lines(path)) {
    lines
        .filter(line -> line.split(",")[2].equalsIgnoreCase("Java"))
        .map(line -> {
            String[] parts = line.split(",");
            return parts[1] + " - " + parts[3];
        })
        .forEach(System.out::println);
}
```

---

## Q27. Copy a file using NIO

### Answer
```java
Files.copy(
    Path.of("input.txt"),
    Path.of("output.txt"),
    StandardCopyOption.REPLACE_EXISTING
);
```

---

## Q28. Move a file using NIO

### Answer
```java
Files.move(
    Path.of("input/data.txt"),
    Path.of("archive/data.txt"),
    StandardCopyOption.REPLACE_EXISTING
);
```

---

## Q29. Create nested directories

### Answer
```java
Path path = Path.of("reports/2026/september");
Files.createDirectories(path);
```

---

## Q30. Find all CSV files recursively

### Answer
```java
try (Stream<Path> paths = Files.walk(Path.of("data"))) {
    paths
        .filter(Files::isRegularFile)
        .filter(path -> path.toString().endsWith(".csv"))
        .forEach(System.out::println);
}
```

---

# 4. CSV / File Parsing

## Q31. How do you parse a CSV file?

### Answer
For simple CSV data:

```java
String[] parts = line.split(",");
```

However, `split(",")` is not a complete CSV parser because real CSV can contain quoted fields and commas inside quoted values.

For production-grade complex CSV, use a proper CSV library.

---

## Q32. Convert a CSV row into an Employee object

### Answer
```java
String[] parts = line.split(",");

Employee employee = new Employee(
    Integer.parseInt(parts[0]),
    parts[1],
    parts[2],
    Integer.parseInt(parts[3])
);
```

---

## Q33. How do you handle invalid CSV records?

### Answer
Validate each record independently.

```java
try {
    String[] parts = line.split(",");

    if (parts.length != 4) {
        throw new IllegalArgumentException("Invalid columns");
    }

    int salary = Integer.parseInt(parts[3]);

    if (salary <= 0) {
        throw new IllegalArgumentException("Invalid salary");
    }

    // Process valid record

} catch (Exception e) {
    // Write record to invalid.csv
}
```

A good production principle is that one bad record should not necessarily stop the complete file.

---

## Q34. Find highest salary from CSV

### Answer
```java
int maxSalary = Integer.MIN_VALUE;
String employeeName = null;

try (BufferedReader reader =
         Files.newBufferedReader(Path.of("employees.csv"))) {

    String line;

    while ((line = reader.readLine()) != null) {
        String[] parts = line.split(",");

        String name = parts[1];
        int salary = Integer.parseInt(parts[3]);

        if (salary > maxSalary) {
            maxSalary = salary;
            employeeName = name;
        }
    }
}

System.out.println(employeeName + " - " + maxSalary);
```

---

## Q35. Group employees by skill

### Answer
```java
Map<String, Long> result;

try (Stream<String> lines =
         Files.lines(Path.of("employees.csv"))) {

    result = lines
        .map(line -> line.split(","))
        .collect(Collectors.groupingBy(
            parts -> parts[2],
            Collectors.counting()
        ));
}

System.out.println(result);
```

---

## Q36. How would you process a 5 GB CSV?

### Answer
Do not do:

```java
Files.readAllLines(path);
```

Instead process incrementally:

```text
5 GB File
   ↓
Read one record/line
   ↓
Validate
   ↓
Transform
   ↓
Persist
   ↓
Read next record
```

Use `BufferedReader` or `Files.lines()` depending on the processing design.

---

# 5. File Writing

## Q37. How do you write employee data to a file?

### Answer
```java
try (BufferedWriter writer =
         Files.newBufferedWriter(Path.of("employees.txt"))) {

    writer.write("101,Vivek,Java,50000");
    writer.newLine();

    writer.write("102,Rahul,Python,60000");
}
```

---

## Q38. How do you generate a report from a CSV?

### Answer
```text
employees.csv
      ↓
Read records
      ↓
Filter/transform
      ↓
employee-report.txt
```

Example:
```java
try (BufferedReader reader =
         Files.newBufferedReader(Path.of("employees.csv"));
     BufferedWriter writer =
         Files.newBufferedWriter(Path.of("employee-report.txt"))) {

    String line;

    while ((line = reader.readLine()) != null) {
        String[] parts = line.split(",");

        writer.write(parts[1] + " - " + parts[3]);
        writer.newLine();
    }
}
```

---

## Q39. flush() vs close()?

### Answer
`flush()` pushes buffered data to the underlying destination.

`close()` releases the resource and normally flushes buffered data as part of closing.

With try-with-resources, you generally do not need to call `close()` manually.

---

# 6. Exception Handling and Resource Management

## Q40. What is IOException?

### Answer
`IOException` represents input/output failures, such as failures opening, reading or writing a file. It is a checked exception.

---

## Q41. What is FileNotFoundException?

### Answer
It is an `IOException` subclass commonly associated with a file that cannot be opened, for example because it does not exist or cannot be accessed.

---

## Q42. Why should file resources be closed?

### Answer
File handles and OS resources are limited.

Not closing resources can cause:
```text
Resource leaks
Too many open files
Locked resources
Unnecessary resource usage
```

---

## Q43. What is try-with-resources?

### Answer
It automatically closes resources implementing `AutoCloseable`.

```java
try (BufferedReader reader =
         Files.newBufferedReader(Path.of("data.txt"))) {

    String line;

    while ((line = reader.readLine()) != null) {
        System.out.println(line);
    }
}
```

---

## Q44. Why is try-with-resources preferred?

### Answer
It is safer and cleaner than manually closing resources because the resource is closed even when an exception occurs.

---

## Q45. What is AutoCloseable?

### Answer
`AutoCloseable` is an interface whose resources can be automatically closed by try-with-resources.

Many Java I/O classes implement `Closeable`, which extends `AutoCloseable`.

---

## Q46. What are suppressed exceptions?

### Answer
If an exception occurs in the try block and another exception occurs while closing the resource, the close exception can become a **suppressed exception** on the primary exception.

It can be inspected using:

```java
exception.getSuppressed();
```

---

# 7. Streams + Files

## Q47. How does Files.lines() help with large files?

### Answer
It allows lines to be processed lazily instead of first creating a collection containing the entire file.

```java
try (Stream<String> stream =
         Files.lines(Path.of("data.txt"))) {

    stream
        .filter(line -> line.contains("ERROR"))
        .forEach(System.out::println);
}
```

---

## Q48. Count Java employees using Stream API

### Answer
```java
long count;

try (Stream<String> lines =
         Files.lines(Path.of("employees.csv"))) {

    count = lines
        .map(line -> line.split(","))
        .filter(parts -> "Java".equalsIgnoreCase(parts[2]))
        .count();
}

System.out.println(count);
```

---

## Q49. Find duplicate lines

Input:
```text
Java
Python
Java
Spring
Python
```

### Answer
```java
Map<String, Long> frequency;

try (Stream<String> lines =
         Files.lines(Path.of("data.txt"))) {

    frequency = lines.collect(
        Collectors.groupingBy(
            Function.identity(),
            Collectors.counting()
        )
    );
}

frequency.entrySet().stream()
    .filter(entry -> entry.getValue() > 1)
    .forEach(entry ->
        System.out.println(entry.getKey()));
```

---

# 8. Directory Handling

## Q50. How do you list files in a directory?

### Answer
```java
try (Stream<Path> paths =
         Files.list(Path.of("data"))) {

    paths.forEach(System.out::println);
}
```

---

## Q51. Files.list() vs Files.walk()?

### Answer
`Files.list()` lists entries in the immediate directory.

`Files.walk()` recursively traverses the directory tree.

```text
Files.list()
data/
 ├── a.csv
 └── sub/

Files.walk()
data/
 ├── a.csv
 └── sub/
      └── b.csv
```

---

## Q52. What is DirectoryStream?

### Answer
`DirectoryStream<Path>` provides an API for iterating over directory entries.

```java
try (DirectoryStream<Path> stream =
         Files.newDirectoryStream(Path.of("data"), "*.csv")) {

    for (Path path : stream) {
        System.out.println(path);
    }
}
```

---

## Q53. Find the largest file in a directory

### Answer
Process paths incrementally rather than collecting the entire tree:

```java
Optional<Path> largest;

try (Stream<Path> paths = Files.walk(Path.of("data"))) {
    largest = paths
        .filter(Files::isRegularFile)
        .max(Comparator.comparingLong(path -> {
            try {
                return Files.size(path);
            } catch (IOException e) {
                return -1L;
            }
        }));
}

largest.ifPresent(System.out::println);
```

---

# 9. Byte Streams

## Q54. FileInputStream vs FileReader?

### Answer
```text
FileInputStream → bytes
FileReader      → characters
```

Use `FileInputStream` for binary data such as images and PDFs.

Use `FileReader` for text.

---

## Q55. How do you copy an image using streams?

### Answer
```java
try (InputStream in =
         new FileInputStream("photo.jpg");
     OutputStream out =
         new FileOutputStream("backup.jpg")) {

    byte[] buffer = new byte[8192];
    int bytesRead;

    while ((bytesRead = in.read(buffer)) != -1) {
        out.write(buffer, 0, bytesRead);
    }
}
```

---

## Q56. Why use a buffer when copying binary files?

### Answer
Reading/writing one byte at a time causes many I/O operations. A byte-array buffer transfers multiple bytes per operation and is generally more efficient.

---

# 10. NIO Channels and Buffers

## Q57. What is a Channel?

### Answer
A Channel is an NIO abstraction for I/O operations.

Examples:
```text
FileChannel
SocketChannel
ServerSocketChannel
```

Channels work closely with buffers and support additional NIO capabilities.

---

## Q58. What is ByteBuffer?

### Answer
`ByteBuffer` is an NIO buffer used to hold bytes during channel-based I/O.

Conceptually:

```text
File
 ↓
FileChannel
 ↓
ByteBuffer
 ↓
Application
```

---

## Q59. What are position, limit and capacity?

### Answer
For a `ByteBuffer`:

```text
capacity → total buffer size
position → current read/write position
limit    → boundary for current operation
```

Initially, for:

```java
ByteBuffer buffer = ByteBuffer.allocate(100);
```

```text
capacity = 100
position = 0
limit = 100
```

---

## Q60. What does flip() do?

### Answer
After writing data into a buffer, `flip()` prepares it for reading.

```text
Write mode
   ↓
flip()
   ↓
Read mode
```

It sets the limit to the current position and resets position to zero.

---

## Q61. What does clear() do?

### Answer
`clear()` prepares the buffer for another write operation:

```text
position = 0
limit = capacity
```

It does not necessarily erase the underlying bytes.

---

# 11. Multithreading + File Handling

## Q62. Can multiple threads write to the same file?

### Answer
Yes, but blindly allowing multiple threads to write can cause race conditions, interleaved output or nondeterministic ordering.

A safer design is:

```text
Producer Threads
      ↓
BlockingQueue
      ↓
Single Writer Thread
      ↓
File
```

---

## Q63. How would you process multiple files concurrently?

### Answer
Use an `ExecutorService` with a controlled worker pool.

```java
ExecutorService executor =
    Executors.newFixedThreadPool(5);

for (Path path : files) {
    executor.submit(() -> processFile(path));
}

executor.shutdown();
```

---

## Q64. Why is a single writer thread useful?

### Answer
Workers process data concurrently, while one thread owns the output file.

```text
Worker 1 ─┐
Worker 2 ─┤
Worker 3 ─┼→ BlockingQueue → Writer → output.txt
Worker 4 ─┤
Worker 5 ─┘
```

Benefits:
- avoids competing writes
- simplifies synchronization
- simplifies output ordering
- separates processing from writing

---

## Q65. Design Producer → Queue → Writer

### Answer

```text
Producer 1 ─┐
Producer 2 ─┤
Producer 3 ─┼→ BlockingQueue → Writer
Producer 4 ─┤
Producer 5 ─┘
```

Skeleton:

```java
BlockingQueue<String> queue =
    new LinkedBlockingQueue<>();

ExecutorService executor =
    Executors.newFixedThreadPool(5);

for (int i = 0; i < 5; i++) {
    executor.submit(() -> {
        String result = processSource();
        queue.put(result);
    });
}
```

A dedicated writer consumes:

```java
while (true) {
    String data = queue.take();

    writer.write(data);
    writer.newLine();
}
```

Production design also needs a reliable termination mechanism, error handling and backpressure.

---

## Q66. How do you preserve output ordering?

### Answer
Attach sequence numbers:

```text
1 → Result 1
2 → Result 2
3 → Result 3
```

If result 3 arrives first, buffer it.

When result 1 arrives:
```text
write result 1
expected = 2
```

Then write result 2 and result 3 when available.

---

# 12. Large File Processing

## Q67. How would you process a 10 GB file?

### Answer
Do not load the entire file into memory.

Use:

```text
10 GB file
    ↓
Read one chunk/line
    ↓
Process
    ↓
Persist/output
    ↓
Next chunk/line
```

Possible APIs:
```text
BufferedReader
Files.newBufferedReader()
Files.lines()
FileChannel
```

---

## Q68. How do you avoid OutOfMemoryError?

### Answer
Avoid:

```java
Files.readAllLines(path);
```

for huge files.

Use streaming:

```java
try (BufferedReader reader =
         Files.newBufferedReader(path)) {

    String line;

    while ((line = reader.readLine()) != null) {
        process(line);
    }
}
```

---

## Q69. How do you handle one malformed record in a huge file?

### Answer

```text
Read record
    ↓
Validate
    ↓
 ┌──────────────┐
 │              │
Valid        Invalid
 │              │
Process      error file/log
```

Do not necessarily terminate the entire file because one record is bad.

---

## Q70. How would you resume after a processing failure?

### Answer
Possible approaches:
- checkpointing
- record/byte offsets
- processed-record identifiers
- idempotent processing
- transaction boundaries
- moving completed files to a processed directory

Example:

```text
input/
   file.csv

       ↓ processing

processed/
   file.csv
```

---

# 13. File Locking

## Q71. What is file locking?

### Answer
File locking coordinates concurrent access to a file.

NIO provides:
```java
FileChannel
FileLock
```

Example:

```java
try (FileChannel channel =
         FileChannel.open(path, StandardOpenOption.WRITE)) {

    try (FileLock lock = channel.lock()) {
        // safely modify file
    }
}
```

---

## Q72. Why do we need file locks?

### Answer
When multiple processes or applications may access the same file, locking can coordinate access and reduce conflicting writes.

---

# 14. WatchService

## Q73. What is WatchService?

### Answer
`WatchService` monitors a directory for file-system events such as:

```text
CREATE
MODIFY
DELETE
```

---

## Q74. How would you automatically process a new CSV file?

### Answer

```text
incoming/
     ↓
WatchService
     ↓
New CSV detected
     ↓
Validation
     ↓
Processing
     ↓
processed/
```

Example:

```java
WatchService watcher =
    FileSystems.getDefault().newWatchService();

Path directory = Path.of("incoming");

directory.register(
    watcher,
    StandardWatchEventKinds.ENTRY_CREATE
);

while (true) {
    WatchKey key = watcher.take();

    for (WatchEvent<?> event : key.pollEvents()) {
        Path file = (Path) event.context();
        System.out.println("New file: " + file);
    }

    key.reset();
}
```

Production systems should also handle partially written files, retries, duplicate events and failures.

---

# 15. Serialization

## Q75. What is serialization?

### Answer
Serialization converts an object's state into a byte representation that can be stored or transferred.

Java provides:
```text
Serializable
ObjectOutputStream
ObjectInputStream
```

---

## Q76. What is serialVersionUID?

### Answer
`serialVersionUID` identifies the serialized version of a class.

```java
private static final long serialVersionUID = 1L;
```

During deserialization, Java uses it for version compatibility checks.

---

## Q77. What does transient mean?

### Answer
A `transient` field is not serialized by Java's default serialization mechanism.

```java
private transient String password;
```

---

# 16. Scenario-Based Interview Questions

## Q78. Design processing for a 5 GB vendor CSV

### Answer

```text
Vendor CSV
    ↓
Streaming Reader
    ↓
Validation
    ↓
Transformation
    ↓
Worker/Batch processing
    ↓
Database
```

Important considerations:
- Don't load the entire file into memory.
- Validate records.
- Handle invalid records separately.
- Use batching for database writes.
- Make processing idempotent where necessary.
- Add logging and metrics.
- Design retry/failure handling.
- Consider checkpointing.

---

## Q79. 10 million records arrive and some are invalid. What do you do?

### Answer

```text
10M records
     ↓
Validation
   ↙     ↘
Valid   Invalid
  ↓        ↓
DB       error.csv
```

Record the invalid record and failure reason so it can be investigated or reprocessed.

---

## Q80. Application crashes after processing 60% of a file. How do you restart?

### Answer

Use checkpointing and idempotent processing.

```text
File
 ↓
Checkpoint
 ↓
Process records
 ↓
Update checkpoint
```

On restart:

```text
Read checkpoint
      ↓
Resume from known position
```

A small overlap may be intentionally reprocessed if the operation is idempotent.

---

## Q81. 100 CSV files arrive simultaneously. You have 10 CPU cores. How do you process them?

### Answer

Use a controlled worker pool:

```text
100 files
    ↓
Task Queue
    ↓
10 workers
    ↓
Process files
```

Example:

```java
ExecutorService executor =
    Executors.newFixedThreadPool(10);

for (Path file : files) {
    executor.submit(() -> processFile(file));
}

executor.shutdown();
```

Do not automatically create 100 threads just because there are 100 files.

---

## Q82. Design a file-processing system where files arrive continuously

### Answer

```text
                New File
                   ↓
              Incoming Dir
                   ↓
              WatchService
                   ↓
                 Queue
                   ↓
              Worker Pool
                   ↓
              Validation
                   ↓
             Transformation
                   ↓
                 DB
                   ↓
             Processed Dir
```

Important production concerns:

```text
Retry
Idempotency
Duplicate files
Partial file uploads
Dead-letter/error handling
Monitoring
Metrics
Logging
Backpressure
```

---

# 17. Rapid-Fire Questions

## Q83. Which API is modern for file paths?

**Answer:** `Path` from `java.nio.file`.

## Q84. Which class provides most NIO file operations?

**Answer:** `Files`.

## Q85. Which API is useful for line-by-line text reading?

**Answer:** `BufferedReader` or `Files.lines()`.

## Q86. Which API should you avoid for a huge file?

**Answer:** `Files.readAllLines()` because it loads all lines into memory.

## Q87. Binary file — Reader or InputStream?

**Answer:** `InputStream`.

## Q88. Text file — InputStream or Reader?

**Answer:** `Reader`.

## Q89. How do you automatically close a reader?

**Answer:** Try-with-resources.

## Q90. How do you recursively traverse a directory?

**Answer:** `Files.walk()`.

## Q91. How do you monitor a directory for new files?

**Answer:** `WatchService`.

## Q92. How do you coordinate multiple writers?

**Answer:** Prefer multiple producers → `BlockingQueue` → single writer, or use appropriate synchronization for direct shared writing.

## Q93. How do you process multiple files concurrently?

**Answer:** `ExecutorService` / controlled worker pool.

## Q94. How do you process a huge file without OOM?

**Answer:** Stream/chunk the input and process incrementally.

## Q95. How do you preserve output ordering with concurrent processing?

**Answer:** Use sequence numbers and a reorder/buffer mechanism before writing.

---

# Final Interview Cheat Sheet

```text
JAVA FILE HANDLING
       |
       +--------------------+
       |                    |
    java.io               NIO
       |                    |
 FileReader              Path
 BufferedReader          Files
 FileWriter              Files.lines()
 BufferedWriter          Files.walk()
 InputStream             WatchService
 OutputStream            FileChannel
                         ByteBuffer
       |
       +--------------------+
                |
       Resource Management
                |
       try-with-resources
                |
       +--------+---------+
       |                  |
    Text File          Binary File
       |                  |
    Reader              Stream
       |                  |
    Buffered            Buffer
       |
       +--------------------+
                |
          Large File
                |
        Streaming/Batching
                |
        Multithreading
                |
        ExecutorService
                ↓
        BlockingQueue
                ↓
        Single Writer
                |
        Production Design
                |
   Validation / Retry
   Idempotency / Checkpoint
   Logging / Monitoring
   Error File / DLQ
```

# Priority for 5–6 Year Backend Interviews

## Must Master

```text
BufferedReader
BufferedWriter
InputStream / OutputStream
try-with-resources
Path
Files
Files.lines()
Files.walk()
CSV processing
Large file processing
ExecutorService + files
BlockingQueue + file writer
Ordering
Exception handling
```

## Should Know

```text
DirectoryStream
FileChannel
ByteBuffer
WatchService
FileLock
BasicFileAttributes
Serialization
```

## Awareness

```text
AsynchronousFileChannel
Memory-mapped files
Selector
Advanced NIO internals
```

# Interview Follow-up Checklist

For every coding problem, be ready to answer:

```text
1. Why did you choose this API?
2. How much memory does it use?
3. What happens if the file is huge?
4. What happens if the file doesn't exist?
5. What happens if one record is invalid?
6. How are resources closed?
7. Is it thread-safe?
8. How would you make it production-ready?
```
