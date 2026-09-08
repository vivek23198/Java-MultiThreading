# Java Exception Handling — Interview Questions & Answers

## How to use this guide

This guide is designed for Java backend interviews, especially for 4–7 years of experience.

Priority:
- ⭐⭐⭐ = Must know
- ⭐⭐ = Important
- ⭐ = Good to know

---

# 1. Exception Handling Basics

## Q1. What is an exception? ⭐⭐⭐

An exception is an event that disrupts the normal flow of program execution.

```java
int a = 10;
int b = 0;

int result = a / b;   // ArithmeticException
```

---

## Q2. Why do we need exception handling? ⭐⭐⭐

Exception handling allows us to handle runtime problems gracefully, prevent abnormal application termination, separate error handling from business logic, provide meaningful errors, and clean up resources.

---

## Q3. Difference between Error and Exception? ⭐⭐⭐

Both extend `Throwable`.

### Error
Usually represents serious JVM/system-level problems.

Examples:
- `OutOfMemoryError`
- `StackOverflowError`

### Exception
Represents conditions application code can often handle.

Examples:
- `IOException`
- `SQLException`
- `NullPointerException`

---

# 2. Exception Hierarchy

## Q4. Explain the Java exception hierarchy. ⭐⭐⭐

```text
Object
  |
Throwable
  |
  +---------------- Error
  |
  +---------------- Exception
                         |
                         +---- RuntimeException
                         |
                         +---- Other checked exceptions
```

Examples:

```text
Throwable
├── Error
│   ├── OutOfMemoryError
│   └── StackOverflowError
│
└── Exception
    ├── RuntimeException
    │   ├── NullPointerException
    │   ├── ArithmeticException
    │   ├── IllegalArgumentException
    │   └── IndexOutOfBoundsException
    │
    ├── IOException
    └── SQLException
```

---

# 3. Checked vs Unchecked

## Q5. What is a checked exception? ⭐⭐⭐

A checked exception is checked by the compiler. The method must either handle it with `try-catch` or declare it using `throws`.

```java
public void readFile() throws IOException {
    FileReader reader = new FileReader("data.txt");
}
```

Common examples:
- `IOException`
- `SQLException`
- `ClassNotFoundException`
- `InterruptedException`

---

## Q6. What is an unchecked exception? ⭐⭐⭐

Unchecked exceptions are subclasses of `RuntimeException`. The compiler does not force us to catch or declare them.

Examples:

```java
NullPointerException
ArithmeticException
IllegalArgumentException
IndexOutOfBoundsException
ClassCastException
```

---

## Q7. Checked vs unchecked exception?

| Checked | Unchecked |
|---|---|
| Compiler checks | Compiler does not require handling |
| Exception excluding RuntimeException | RuntimeException and subclasses |
| Must catch or declare | Catch/declare optional |
| IOException | NullPointerException |
| SQLException | IllegalArgumentException |

Interview one-liner:

> Checked exceptions are enforced by the compiler; unchecked exceptions are not.

---

# 4. try-catch-finally

## Q8. Explain try-catch-finally. ⭐⭐⭐

```java
try {
    // risky code
} catch (Exception e) {
    // handling
} finally {
    // cleanup
}
```

---

## Q9. Can we have try without catch?

Yes, if it has `finally`.

```java
try {
    System.out.println("Hello");
} finally {
    System.out.println("Cleanup");
}
```

---

## Q10. Can we have catch without try?

No. `catch` must be associated with a `try`.

---

## Q11. Can we have finally without catch?

Yes.

```java
try {
    System.out.println("Hello");
} finally {
    System.out.println("Cleanup");
}
```

---

## Q12. Is finally always executed? ⭐⭐⭐

Usually yes, but not literally always.

It may not execute if the JVM terminates abruptly.

```java
try {
    System.out.println("try");
    System.exit(0);
} finally {
    System.out.println("finally");
}
```

`finally` will not execute because the JVM terminates.

---

## Q13. Why is finally used?

Historically it is used for cleanup. For files, streams, database connections, and similar resources, modern Java should generally use **try-with-resources**.

---

# 5. Multiple Catch Blocks

## Q14. Can we have multiple catch blocks? ⭐⭐⭐

Yes.

```java
try {
    int x = 10 / 0;
} catch (ArithmeticException e) {
    System.out.println("Arithmetic problem");
} catch (Exception e) {
    System.out.println("General problem");
}
```

---

## Q15. Why should specific exceptions come before general exceptions?

Catch blocks are evaluated from top to bottom.

Wrong:

```java
catch (Exception e) {
}
catch (ArithmeticException e) {
}
```

The second catch is unreachable because `ArithmeticException` is already covered by `Exception`.

Correct:

```java
catch (ArithmeticException e) {
}
catch (Exception e) {
}
```

---

## Q16. What happens if no catch matches?

The exception propagates to the caller and continues up the call stack until an appropriate handler is found.

---

# 6. throw vs throws

## Q17. Difference between throw and throws? ⭐⭐⭐

### throw

Actually throws an exception object.

```java
throw new IllegalArgumentException("Invalid age");
```

### throws

Declares possible exceptions in a method signature.

```java
public void readFile() throws IOException {
}
```

Memory trick:

```text
throw  -> actually throws
throws -> declares
```

---

## Q18. Can we throw a checked exception using throw?

Yes, but the method must catch it or declare it.

```java
public void test() throws IOException {
    throw new IOException("File error");
}
```

---

## Q19. Can we throw an unchecked exception?

Yes.

```java
throw new IllegalArgumentException("Invalid input");
```

No declaration is required.

---

# 7. Exception Propagation

## Q20. What is exception propagation? ⭐⭐⭐

If a method does not handle an exception, it moves up the call stack to its caller.

```text
main()
  |
methodA()
  |
methodB()
  |
Exception
```

If `methodB()` doesn't handle it:

```text
methodB → methodA → main
```

---

## Q21. Give a practical example of exception propagation.

```java
public void controller() {
    service();
}

public void service() {
    repository();
}

public void repository() {
    throw new RuntimeException("DB error");
}
```

The exception travels:

```text
repository
   ↓
service
   ↓
controller
```

In Spring Boot, a controller/global exception handler can ultimately convert such errors into an HTTP response.

---

# 8. Custom Exceptions

## Q22. Why create custom exceptions? ⭐⭐⭐

Custom exceptions make business errors meaningful.

Instead of:

```java
throw new RuntimeException("Something went wrong");
```

use:

```java
throw new InsufficientBalanceException("Insufficient balance");
```

Benefits:
- readability
- maintainability
- API error handling
- debugging
- business-level error classification

---

## Q23. How do you create a custom unchecked exception?

```java
public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(String message) {
        super(message);
    }
}
```

Usage:

```java
if (user == null) {
    throw new UserNotFoundException("User not found");
}
```

---

## Q24. How do you create a custom checked exception?

Extend `Exception`.

```java
public class PaymentException extends Exception {

    public PaymentException(String message) {
        super(message);
    }
}
```

Usage:

```java
public void processPayment() throws PaymentException {
    throw new PaymentException("Payment failed");
}
```

---

## Q25. When would you use checked vs unchecked custom exceptions?

Use unchecked exceptions for many programming/business validation failures where the caller cannot reasonably recover at that point.

Use checked exceptions when the caller is expected to explicitly handle or recover from the condition.

In modern Spring Boot backend applications, custom business exceptions are commonly unchecked.

---

# 9. Exception Message and Cause

## Q26. What does `super(message)` do?

It passes the message to the parent exception class.

```java
public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(String message) {
        super(message);
    }
}
```

Then:

```java
e.getMessage();
```

returns the message.

---

## Q27. What is exception chaining? ⭐⭐⭐

Exception chaining means preserving the original cause when throwing another exception.

```java
try {
    repository.save();
} catch (SQLException e) {
    throw new UserServiceException("Unable to save user", e);
}
```

The original `SQLException` can be retrieved through:

```java
e.getCause();
```

---

## Q28. Why is exception chaining useful?

It lets us expose a clean business-level message while preserving the original technical cause for debugging.

```text
Database Exception
       ↓
Repository Exception
       ↓
Service Exception
       ↓
API Exception
```

---

# 10. Stack Trace

## Q29. What is a stack trace?

A stack trace shows the sequence of method calls that led to an exception.

```text
ArithmeticException: / by zero
    at Service.calculate(Service.java:20)
    at Controller.process(Controller.java:10)
    at Main.main(Main.java:5)
```

It helps identify the exception type, message, source location, and call path.

---

## Q30. Difference between `printStackTrace()` and `getMessage()`?

```java
e.getMessage();
```

Returns the exception message.

```java
e.printStackTrace();
```

Prints exception information and the stack trace.

Production applications should generally use a logging framework rather than directly calling `printStackTrace()`.

---

# 11. try-with-resources

## Q31. What is try-with-resources? ⭐⭐⭐

It automatically closes resources that implement `AutoCloseable`.

```java
try (BufferedReader br =
         new BufferedReader(new FileReader("employees.txt"))) {

    String line;

    while ((line = br.readLine()) != null) {
        System.out.println(line);
    }

} catch (IOException e) {
    e.printStackTrace();
}
```

---

## Q32. What interface must a resource implement?

The resource must implement:

```java
AutoCloseable
```

`Closeable` extends `AutoCloseable`.

Examples:
- `BufferedReader`
- `FileInputStream`
- JDBC `Connection`
- `Statement`
- `ResultSet`

---

## Q33. Why is try-with-resources better than finally?

Old style:

```java
BufferedReader br = null;

try {
    br = new BufferedReader(new FileReader("data.txt"));
} finally {
    if (br != null) {
        br.close();
    }
}
```

Modern:

```java
try (BufferedReader br =
         new BufferedReader(new FileReader("data.txt"))) {
}
```

Advantages:
- less boilerplate
- automatic cleanup
- safer
- handles close exceptions correctly
- supports multiple resources

---

## Q34. Can try-with-resources have multiple resources?

Yes.

```java
try (
    BufferedReader br = new BufferedReader(new FileReader("input.txt"));
    BufferedWriter bw = new BufferedWriter(new FileWriter("output.txt"))
) {
    // process
}
```

Resources close in reverse declaration order.

```text
bw closes first
br closes second
```

---

# 12. Suppressed Exceptions

## Q35. What is a suppressed exception? ⭐⭐

If the main operation throws an exception and closing a resource also throws an exception, Java preserves the close exception as a **suppressed exception**.

```java
try (MyResource resource = new MyResource()) {
    throw new RuntimeException("Main failure");
} catch (Exception e) {

    System.out.println(e.getMessage());

    for (Throwable suppressed : e.getSuppressed()) {
        System.out.println(suppressed.getMessage());
    }
}
```

Use:

```java
e.getSuppressed();
```

---

# 13. Multi-catch

## Q36. What is multi-catch? ⭐⭐

Java allows multiple exception types in one catch block.

```java
try {
    // code
} catch (IOException | SQLException e) {
    System.out.println(e.getMessage());
}
```

Useful when different exceptions require exactly the same handling.

---

## Q37. Can related exceptions be used in multi-catch?

No.

Invalid:

```java
catch (Exception | IOException e)
```

because `IOException` is already a subtype of `Exception`.

---

# 14. final, finally, finalize

## Q38. Difference between final, finally and finalize? ⭐⭐⭐

### final

Keyword used for variables, methods, and classes.

### finally

Exception-handling block normally used for cleanup.

### finalize

Legacy GC-related mechanism; modern Java applications should not rely on it.

Memory trick:

```text
final    → restriction
finally  → cleanup block
finalize → old GC-related mechanism
```

---

# 15. Tricky finally Questions

## Q39. What is returned?

```java
public int test() {

    try {
        return 10;
    } finally {
        return 20;
    }
}
```

Result:

```text
20
```

The `finally` return overrides the earlier return.

Interview advice:

> Avoid returning from finally because it can hide exceptions and make control flow confusing.

---

## Q40. What is returned?

```java
public int test() {

    int x = 10;

    try {
        return x;
    } finally {
        x = 20;
    }
}
```

Result:

```text
10
```

The return value is evaluated before `finally` changes the local primitive variable.

---

## Q41. What happens if finally throws an exception?

```java
try {
    throw new RuntimeException("Try exception");
} finally {
    throw new RuntimeException("Finally exception");
}
```

The exception from `finally` replaces the exception from `try`.

---

# 16. Constructors, Static Blocks and Exceptions

## Q42. Can a constructor throw an exception?

Yes.

```java
public User(String name) throws IOException {
    // initialization
}
```

Constructors can throw checked or unchecked exceptions.

---

## Q43. Can static blocks throw checked exceptions?

A static initializer cannot simply declare `throws`. A checked exception must be handled inside it or wrapped.

```java
static {
    try {
        // risky operation
    } catch (IOException e) {
        throw new ExceptionInInitializerError(e);
    }
}
```

---

# 17. Exceptions During Method Overriding

## Q44. Can an overridden method throw a broader checked exception? ⭐⭐⭐

No.

```java
class Parent {
    void test() throws IOException {}
}

class Child extends Parent {
    // Invalid:
    // void test() throws Exception {}
}
```

---

## Q45. Can the child method throw a narrower checked exception?

Yes.

```java
class Parent {
    void test() throws IOException {}
}

class Child extends Parent {
    void test() throws FileNotFoundException {}
}
```

---

## Q46. Can an overriding method throw unchecked exceptions?

Yes. Unchecked exceptions are not restricted by the same checked-exception rule.

---

# 18. Common Runtime Exceptions

## Q47. What causes NullPointerException? ⭐⭐⭐

When code tries to use a null reference as an object.

```java
String name = null;

System.out.println(name.length());
```

---

## Q48. How can you prevent NullPointerException?

Use:
- proper validation
- `Objects.requireNonNull()`
- `Optional` where appropriate
- correct initialization
- clear API contracts

```java
Objects.requireNonNull(name, "name cannot be null");
```

---

## Q49. When should IllegalArgumentException be used?

When a method receives an invalid argument.

```java
public void setAge(int age) {

    if (age < 0) {
        throw new IllegalArgumentException(
            "Age cannot be negative"
        );
    }
}
```

---

# 19. Spring Boot Exception Handling

## Q50. How do you handle exceptions globally in Spring Boot? ⭐⭐⭐

Use:

```java
@RestControllerAdvice
```

with:

```java
@ExceptionHandler
```

Example:

```java
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<String> handleUserNotFound(
            UserNotFoundException e) {

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(e.getMessage());
    }
}
```

---

## Q51. Why use `@RestControllerAdvice`?

It centralizes exception handling.

Instead of adding `try-catch` to every controller, common exceptions are handled in one place.

Benefits:
- consistent API responses
- less duplicate code
- centralized logging
- easier maintenance

---

## Q52. `@ControllerAdvice` vs `@RestControllerAdvice`?

`@RestControllerAdvice` combines the behavior of:

```java
@ControllerAdvice
@ResponseBody
```

It is convenient for REST APIs because handler return values are written directly to the response body.

---

# 20. REST API Exception Design

## Q53. What should a good API error response contain?

Example:

```json
{
  "timestamp": "2026-09-08T10:30:00Z",
  "status": 404,
  "error": "USER_NOT_FOUND",
  "message": "User 101 not found",
  "path": "/users/101"
}
```

Depending on requirements, it may also contain:
- error code
- correlation/request ID
- validation details

Avoid exposing:
- stack traces
- database internals
- passwords
- sensitive information

---

## Q54. How should exceptions be handled across Controller → Service → Repository?

A good layered approach:

```text
Controller
    ↓
Service
    ↓
Repository / External Service
```

- Controller: convert exceptions into HTTP responses.
- Service: throw meaningful business exceptions.
- Repository/integration layer: translate low-level technical exceptions where useful.
- Preserve the original cause.

---

# 21. Exception Handling Best Practices

## Q55. Should we catch every exception?

No.

Bad:

```java
try {
    service.process();
} catch (Exception e) {
    // ignore
}
```

Catch an exception when you can:
1. recover,
2. add meaningful context,
3. translate it,
4. handle it appropriately.

Otherwise, allow it to propagate.

---

## Q56. Is it okay to catch `Exception`?

Sometimes, but it should not be the default.

Bad:

```java
catch (Exception e) {
    return null;
}
```

Better:

```java
catch (SQLException e) {
    throw new UserRepositoryException(
        "Unable to load user", e
    );
}
```

Catch the most specific exception you can reasonably handle.

---

## Q57. What are common exception-handling mistakes?

1. Empty catch blocks.
2. Catching everything without a reason.
3. Losing the original cause.
4. Returning from `finally`.
5. Using exceptions for normal control flow.
6. Logging without useful context.
7. Exposing internal stack traces to clients.
8. Duplicate logging across every layer.
9. Swallowing `InterruptedException`.
10. Catching `Throwable` without a very specific reason.

---

# 22. Logging Exceptions

## Q58. How should exceptions be logged?

Prefer SLF4J/Logback or another logging framework.

```java
log.error("Failed to process order {}", orderId, e);
```

Passing `e` preserves the stack trace.

Avoid only:

```java
log.error(e.getMessage());
```

when the full diagnostic stack trace is needed.

---

# 23. Exception Translation

## Q59. What is exception translation?

Converting a low-level exception into an exception meaningful to the current layer.

```text
SQLException
     ↓
RepositoryException
     ↓
ServiceException
     ↓
API error response
```

Example:

```java
catch (SQLException e) {
    throw new OrderRepositoryException(
        "Unable to save order", e
    );
}
```

---

# 24. Transactions and Exceptions

## Q60. What happens to Spring transactions when an exception occurs? ⭐⭐⭐

Spring's default transaction behavior generally rolls back for:
- unchecked exceptions (`RuntimeException`)
- `Error`

Checked exceptions do not automatically trigger rollback by default.

```java
@Transactional
public void createOrder() {
    // DB operation
    throw new RuntimeException("Failure");
}
```

Normally the transaction rolls back.

For a checked exception, configure rollback explicitly when required:

```java
@Transactional(rollbackFor = Exception.class)
public void createOrder() throws Exception {
    // DB operations
}
```

---

# 25. Optional and Exceptions

## Q61. Should Optional replace exceptions?

No.

`Optional` is useful for representing an optional/possibly absent return value.

```java
Optional<User> findUser(Long id)
```

A missing user can be:

```java
Optional.empty()
```

But an actual failure such as a database connectivity problem should generally be represented by an exception.

---

# 26. Streams and Checked Exceptions

## Q62. How do you handle checked exceptions inside streams?

Java functional interfaces such as `Function` do not declare checked exceptions.

For example, if `readFile()` throws `IOException`, this does not compile directly:

```java
files.stream()
     .map(file -> readFile(file));
```

One option is to wrap it:

```java
files.stream()
     .map(file -> {
         try {
             return readFile(file);
         } catch (IOException e) {
             throw new UncheckedIOException(e);
         }
     });
```

For complex exception-heavy processing, a normal loop can be clearer.

---

## Q63. What is UncheckedIOException?

It wraps an `IOException` in an unchecked exception.

```java
throw new UncheckedIOException(e);
```

It is useful with APIs such as streams where checked exceptions are inconvenient.

---

# 27. InterruptedException

## Q64. What is InterruptedException? ⭐⭐⭐

It indicates that a thread waiting, sleeping, or performing an interruptible operation has been interrupted.

```java
try {
    Thread.sleep(1000);
} catch (InterruptedException e) {
    Thread.currentThread().interrupt();
}
```

---

## Q65. Why call `Thread.currentThread().interrupt()`?

Catching `InterruptedException` clears the thread's interrupted status.

Calling:

```java
Thread.currentThread().interrupt();
```

restores the status so higher-level code can detect the interruption.

Interview one-liner:

> Don't silently swallow interruption; restore the interrupt status when you are not intentionally consuming the interruption.

---

# 28. CompletableFuture and Exceptions

## Q66. How do you handle exceptions in CompletableFuture? ⭐⭐

Using `exceptionally()`:

```java
CompletableFuture
    .supplyAsync(() -> callService())
    .exceptionally(e -> {
        log.error("Service failed", e);
        return "default";
    });
```

---

## Q67. Difference between exceptionally and handle?

### exceptionally

Primarily handles the exceptional path.

```java
.exceptionally(e -> "fallback")
```

### handle

Receives both result and exception.

```java
.handle((result, exception) -> {
    if (exception != null) {
        return "fallback";
    }
    return result;
});
```

---

# 29. Multithreading and Exceptions

## Q68. What happens to an exception thrown inside a Thread?

It does not automatically propagate to the thread that created it.

```java
Thread t = new Thread(() -> {
    throw new RuntimeException("Failure");
});

t.start();
```

The exception occurs in that thread.

For tasks submitted to `ExecutorService`, exceptions can be observed through `Future.get()`:

```java
Future<?> future = executor.submit(task);

try {
    future.get();
} catch (ExecutionException e) {
    Throwable cause = e.getCause();
}
```

---

# 30. Error Types

## Q69. What is StackOverflowError?

It commonly occurs when the call stack grows beyond its limit, often because of uncontrolled recursion.

```java
public void test() {
    test();
}
```

Eventually:

```text
StackOverflowError
```

---

## Q70. What is OutOfMemoryError?

It occurs when the JVM cannot allocate required memory.

Possible causes:
- excessive object creation
- memory leaks
- very large collections
- insufficient heap

---

## Q71. ClassNotFoundException vs NoClassDefFoundError?

### ClassNotFoundException

A checked exception when an application explicitly tries to load a class and the class cannot be found.

```java
Class.forName("com.example.MyClass");
```

### NoClassDefFoundError

An `Error` that can occur when the JVM cannot find a class definition that was available when code was compiled but is unavailable at runtime.

Interview memory:

```text
ClassNotFoundException
→ explicit class loading failure

NoClassDefFoundError
→ required class definition unavailable at runtime
```

---

# 31. Coding Questions

## Q72. Write a method that validates age.

```java
public void validateAge(int age) {

    if (age < 18) {
        throw new IllegalArgumentException(
            "Age must be at least 18"
        );
    }
}
```

---

## Q73. Create a custom exception for insufficient balance.

```java
class InsufficientBalanceException
        extends RuntimeException {

    public InsufficientBalanceException(String message) {
        super(message);
    }
}
```

Usage:

```java
if (balance < amount) {
    throw new InsufficientBalanceException(
        "Insufficient balance"
    );
}
```

---

## Q74. Write safe file reading using try-with-resources.

```java
public void readFile(String path) {

    try (BufferedReader br =
             new BufferedReader(new FileReader(path))) {

        String line;

        while ((line = br.readLine()) != null) {
            System.out.println(line);
        }

    } catch (IOException e) {
        throw new UncheckedIOException(
            "Unable to read file: " + path, e
        );
    }
}
```

---

## Q75. Create a global Spring Boot exception handler.

```java
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Map<String, Object>>
    handleUserNotFound(UserNotFoundException e) {

        Map<String, Object> response = new HashMap<>();

        response.put("status", 404);
        response.put("error", "USER_NOT_FOUND");
        response.put("message", e.getMessage());

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(response);
    }
}
```

---

# 32. Tricky Output Questions

## Q76. What is printed?

```java
try {
    System.out.println("A");
    return;
} finally {
    System.out.println("B");
}
```

Output:

```text
A
B
```

`finally` executes before the method actually returns.

---

## Q77. What is printed?

```java
try {
    System.out.println("try");
} catch (Exception e) {
    System.out.println("catch");
} finally {
    System.out.println("finally");
}
```

Output:

```text
try
finally
```

---

## Q78. What happens here?

```java
try {
    throw new RuntimeException("A");
} catch (RuntimeException e) {
    throw new RuntimeException("B");
}
```

The original exception is replaced by the new exception unless the original is preserved:

```java
throw new RuntimeException("B", e);
```

---

# 33. Senior-Level Microservices Questions

## Q79. How would you design exception handling for a microservices application? ⭐⭐⭐

```text
Controller
    |
Service
    |
Repository / External Service
```

### Controller
Convert application exceptions into HTTP responses.

### Service
Throw meaningful business exceptions.

### Repository/integration
Translate low-level technical exceptions where appropriate and preserve the cause.

### Global handler
Centralize REST error responses.

Example:

```text
SQLException
    ↓
DataAccessException
    ↓
OrderServiceException
    ↓
@RestControllerAdvice
    ↓
HTTP response
```

---

## Q80. Should every layer catch and rethrow exceptions?

No.

Do it only when the layer adds value:
- recovery
- context
- translation
- logging/handling
- abstraction

Otherwise allow propagation.

---

## Q81. How do you avoid leaking internal errors to clients?

Do not expose stack traces or database internals.

Return a safe response such as:

```json
{
  "status": 500,
  "error": "INTERNAL_ERROR",
  "message": "Something went wrong",
  "traceId": "abc123"
}
```

Log the detailed exception internally.

---

# 34. Rapid-Fire Revision

## Q82. Root class of exception hierarchy?

`Throwable`

## Q83. Parent of RuntimeException?

`Exception`

## Q84. Is RuntimeException checked?

No.

## Q85. Is IOException checked?

Yes.

## Q86. Can finally exist without catch?

Yes.

## Q87. Can catch exist without try?

No.

## Q88. Can try exist without catch?

Yes, if finally is present.

## Q89. Keyword to explicitly throw?

`throw`

## Q90. Keyword to declare exceptions?

`throws`

## Q91. Interface used by try-with-resources?

`AutoCloseable`

## Q92. Method to retrieve original cause?

`getCause()`

## Q93. Method to retrieve suppressed exceptions?

`getSuppressed()`

## Q94. Spring annotation for global REST exception handling?

`@RestControllerAdvice`

## Q95. Annotation for a specific exception handler?

`@ExceptionHandler`

## Q96. Common exception for invalid method argument?

`IllegalArgumentException`

## Q97. Common exception for missing object reference?

`NullPointerException`

## Q98. What should usually happen to InterruptedException?

Restore interrupt status when appropriate:

```java
Thread.currentThread().interrupt();
```

---

# 35. Final Interview Cheat Sheet

```text
                 Throwable
                    |
          +---------+---------+
          |                   |
        Error              Exception
          |                   |
      JVM/System          +----+----+
      problems            |         |
                      RuntimeException  Checked
                           |            Exceptions
                     unchecked          |
                                        |
                              IOException
                              SQLException
                              etc.
```

### Core syntax

```java
try {
    // risky code
} catch (SpecificException e) {
    // handle
} finally {
    // cleanup
}
```

### Explicit exception

```java
throw new IllegalArgumentException("Invalid input");
```

### Declaration

```java
void read() throws IOException {
}
```

### Custom exception

```java
class UserNotFoundException
        extends RuntimeException {

    UserNotFoundException(String message) {
        super(message);
    }
}
```

### Exception chaining

```java
throw new ServiceException("Operation failed", e);
```

### Try-with-resources

```java
try (BufferedReader br = ...) {
}
```

### Spring Boot

```java
@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    ResponseEntity<?> handle(UserNotFoundException e) {
        // return API error
    }
}
```

---

# 36. What to Prioritize for a 5–6 Year Java Backend Interview

## Must Know ⭐⭐⭐

- Exception hierarchy
- Checked vs unchecked
- `throw` vs `throws`
- try/catch/finally
- exception propagation
- custom exceptions
- exception chaining
- try-with-resources
- suppressed exceptions
- overriding + checked exceptions
- Spring `@RestControllerAdvice`
- `@ExceptionHandler`
- transaction rollback behavior
- `InterruptedException`
- logging and exception best practices

## Important ⭐⭐

- multi-catch
- `ClassNotFoundException` vs `NoClassDefFoundError`
- `Error` vs `Exception`
- `finally` tricky cases
- CompletableFuture exception handling
- stream checked exceptions
- exception translation
- API error design

## Good to Know ⭐

- static initialization exceptions
- `UncheckedIOException`
- detailed JVM `Error` types
- advanced suppressed-exception scenarios

---

# Final 10 Questions You Should Be Able to Answer

1. Explain Java exception hierarchy.
2. Checked vs unchecked exception?
3. `throw` vs `throws`?
4. Is finally always executed?
5. Explain exception propagation.
6. How do you create a custom exception?
7. What is exception chaining?
8. How does try-with-resources work?
9. How do you handle exceptions globally in Spring Boot?
10. What happens to Spring transactions when checked vs unchecked exceptions occur?

If you can confidently explain these 10 with code, your Java exception-handling preparation is strong.
