# JUnit, Mockito & Integration Testing --- Senior Java Interview Revision

> **Source scope:** These notes are derived strictly from the seven
> uploaded Spring Testing PDFs. Topics not supported by the PDFs are
> listed separately under **Gaps not covered in source material**.

------------------------------------------------------------------------

# 1. JUnit

## 1.1 Introduction to Testing in Spring Boot

### What it is

Testing is presented as an important part of the Software Development
Life Cycle. The source highlights that testing helps identify bugs
early, reduce deployment risk, improve productivity, and build developer
confidence.

### Code example

``` java
class EmployeeServiceTest {

    @Test
    void testGetEmployeeById() {
        // Arrange test data
        // Execute the method
        // Assert the result
    }
}
```

### When/why to use it

Use tests to catch defects early and reduce the risk of deploying
changes that break existing functionality. The source specifically
connects testing with system stability, productivity, and confidence.

### Common interview question

**Q: Why is testing important in a Spring Boot project?**

**Model answer:** Testing helps identify bugs early, reduces deployment
risk, improves system stability and development productivity, and
increases confidence in the codebase.

------------------------------------------------------------------------

## 1.2 Test-Driven Development (TDD)

### What it is

TDD is a test-writing approach where development starts with a failing
test, followed by the minimum implementation needed to pass it, and then
refactoring.

### Code example

``` java
@Test
void shouldReturnEmployeeName() {
    // Step 1: write the test first
    Employee employee = employeeService.getEmployeeById(1L);

    assertThat(employee.getName()).isEqualTo("John Doe");
}
```

### When/why to use it

The source describes the TDD cycle as:

``` text
Write a failing test
        ↓
Write minimum code to pass
        ↓
Refactor for better design
        ↓
Repeat
```

This approach keeps the implementation driven by the expected behavior.

### Common interview question

**Q: Explain TDD.**

**Model answer:** In TDD, I first write a failing test, then implement
the minimum code required to make it pass, and finally refactor the
implementation for better design.

------------------------------------------------------------------------

## 1.3 Behavior-Driven Development (BDD)

### What it is

BDD is described in the source as an extension of TDD. It starts by
defining behavior in human-readable sentences, writing scenarios for
that behavior, and implementing code to satisfy those scenarios.

### Code example

``` java
// Human-readable behavior:
// "Given an employee exists, when I request the employee,
// then the employee details should be returned."

@Test
void shouldReturnEmployeeDetails() {
    Employee employee = employeeService.getEmployeeById(1L);

    assertThat(employee.getName()).isEqualTo("John Doe");
}
```

### When/why to use it

Use the BDD approach when the expected behavior needs to be clearly
expressed before implementation. The source emphasizes human-readable
behavior and scenarios.

### Common interview question

**Q: How is BDD related to TDD?**

**Model answer:** The source describes BDD as an extension of TDD. TDD
starts with a failing test, while BDD additionally emphasizes defining
behavior in human-readable sentences and writing scenarios for that
behavior.

------------------------------------------------------------------------

## 1.4 Other Test-Writing Approaches Mentioned

### What it is

The source also mentions **Test-After Development** and **Simultaneous
Development**, but does not explain their detailed workflows.

### Code example

``` java
@Test
void shouldCalculateEmployeeValue() {
    // Test the implemented behavior.
}
```

### When/why to use it

The PDFs only name these approaches and do not provide enough detail to
prescribe when they should be selected.

### Common interview question

**Q: What test-writing approaches are mentioned in the source?**

**Model answer:** TDD, BDD, Test-After Development, and Simultaneous
Development are mentioned. The PDFs provide detailed steps for TDD and
BDD, but not for the latter two.

------------------------------------------------------------------------

# 2. JUnit 5 Annotations

## 2.1 @Test

### What it is

`@Test` marks a method as a test method that JUnit executes when tests
are run.

### Code example

``` java
@Test
void shouldReturnEmployee() {
    // test logic
}
```

### When/why to use it

Use it for an individual test case that JUnit should execute.

### Common interview question

**Q: What does @Test do?**

**Model answer:** It marks a method as a JUnit test method, so JUnit
executes that method during the test run.

------------------------------------------------------------------------

## 2.2 @DisplayName

### What it is

`@DisplayName` provides a custom display name for a test class or test
method. The name appears in test reports and IDEs.

### Code example

``` java
@DisplayName("Get employee by ID")
@Test
void getEmployeeById() {
    // test
}
```

### When/why to use it

Use it when the default Java method/class name is not as descriptive as
the business behavior you want shown in test reports.

### Common interview question

**Q: Why would you use @DisplayName?**

**Model answer:** It gives the test class or method a custom name that
is shown in test reports and IDEs.

------------------------------------------------------------------------

## 2.3 @Disabled

### What it is

`@Disabled` disables a test class or test method so that it is not
executed.

### Code example

``` java
@Disabled
@Test
void temporarilyDisabledTest() {
    // not executed
}
```

### When/why to use it

Use it when a test needs to be disabled according to the testing setup
described by the source.

### Common interview question

**Q: What happens when @Disabled is used?**

**Model answer:** The annotated test class or method is disabled and is
not executed.

------------------------------------------------------------------------

## 2.4 @BeforeEach

### What it is

`@BeforeEach` marks a method that runs before every test method.

### Code example

``` java
@BeforeEach
void setUp() {
    // reset conditions for each test
}
```

### When/why to use it

The source specifically mentions using it to reset each test case's
conditions.

### Common interview question

**Q: When does @BeforeEach execute?**

**Model answer:** It executes before every test method and can be used
to prepare or reset the conditions for each test.

------------------------------------------------------------------------

## 2.5 @AfterEach

### What it is

`@AfterEach` marks a method that runs after every test method.

### Code example

``` java
@AfterEach
void tearDown() {
    // cleanup after each test
}
```

### When/why to use it

Use it for actions that need to happen after every test according to the
test lifecycle.

### Common interview question

**Q: Difference between @BeforeEach and @AfterEach?**

**Model answer:** `@BeforeEach` runs before every test method;
`@AfterEach` runs after every test method.

------------------------------------------------------------------------

## 2.6 @BeforeAll

### What it is

`@BeforeAll` marks a method that executes once before all test methods
in the class. The source states that the method must be static.

### Code example

``` java
@BeforeAll
static void setupOnce() {
    // runs once before all tests
}
```

### When/why to use it

Use it for setup that should happen once for the test class rather than
before every individual test.

### Common interview question

**Q: When does @BeforeAll execute?**

**Model answer:** Once before all test methods in the class. The source
specifies that the method must be static.

------------------------------------------------------------------------

## 2.7 @AfterAll

### What it is

`@AfterAll` marks a method that executes once after all test methods in
the class. The source states that the method must be static.

### Code example

``` java
@AfterAll
static void cleanupOnce() {
    // runs once after all tests
}
```

### When/why to use it

Use it for cleanup that should happen once after the complete test class
has finished.

### Common interview question

**Q: When does @AfterAll execute?**

**Model answer:** Once after all test methods in the class. The method
must be static according to the source.

------------------------------------------------------------------------

## 2.8 JUnit Annotation Quick Reference

  --------------------------------------------------------------------------------------
  Annotation              Purpose                 One-line example
  ----------------------- ----------------------- --------------------------------------
  `@Test`                 Marks a method as a     `@Test void testEmployee() {}`
                          test                    

  `@DisplayName`          Custom name in          `@DisplayName("Get employee")`
                          reports/IDE             

  `@Disabled`             Disables class/method   `@Disabled @Test void test() {}`

  `@BeforeEach`           Runs before every test  `@BeforeEach void setUp() {}`

  `@AfterEach`            Runs after every test   `@AfterEach void tearDown() {}`

  `@BeforeAll`            Runs once before all    `@BeforeAll static void init() {}`
                          tests; source says      
                          method must be static   

  `@AfterAll`             Runs once after all     `@AfterAll static void cleanup() {}`
                          tests; source says      
                          method must be static   
  --------------------------------------------------------------------------------------

------------------------------------------------------------------------

# 3. JUnit vs AssertJ

## 3.1 What is the difference?

### What it is

The source describes **JUnit** as a testing framework used to write,
execute, and report test results. **AssertJ** is a library that
complements testing frameworks and provides fluent, expressive
assertions.

### Code example

``` java
import static org.assertj.core.api.Assertions.assertThat;

assertThat(employee.getName()).isEqualTo("John Doe");
assertThat(employee.getAge()).isGreaterThan(18);
```

### When/why to use it

Use JUnit for the test structure/execution and AssertJ for readable
assertions.

### Common interview question

**Q: Is AssertJ a replacement for JUnit?**

**Model answer:** No. The source describes JUnit as a testing framework
and AssertJ as an assertion library that complements testing frameworks
with fluent and expressive assertions.

------------------------------------------------------------------------

## 3.2 JUnit vs AssertJ Comparison

  ----------------------------------------------------------------------------------
  Aspect                  JUnit                   AssertJ
  ----------------------- ----------------------- ----------------------------------
  Role                    Testing framework       Assertion library

  Main purpose            Write, execute, and     Fluent and expressive assertions
                          report tests            

  Example from source     `@Test`                 `assertThat(...).isEqualTo(...)`

  Relationship            Provides testing        Complements testing frameworks
                          framework functionality 
  ----------------------------------------------------------------------------------

------------------------------------------------------------------------

# 4. AssertJ Assertions

## 4.1 Numbers

### What it is

AssertJ provides fluent assertions for numeric values.

### Code example

``` java
assertThat(5)
    .isEqualTo(5)
    .isNotEqualTo(10)
    .isGreaterThan(4);
```

### When/why to use it

Use these assertions when the expected result is numeric and you want
multiple readable conditions.

### Common interview question

**Q: Give examples of numeric AssertJ assertions.**

**Model answer:** `isEqualTo`, `isNotEqualTo`, and `isGreaterThan` are
shown in the source.

------------------------------------------------------------------------

## 4.2 Strings

### What it is

AssertJ supports fluent assertions for string contents and
prefixes/suffixes.

### Code example

``` java
assertThat("hello")
    .startsWith("he")
    .endsWith("lo")
    .contains("ell");
```

### When/why to use it

Use it when validating a string's value or parts of its content.

### Common interview question

**Q: How can AssertJ validate a string?**

**Model answer:** The source demonstrates `startsWith`, `endsWith`, and
`contains`.

------------------------------------------------------------------------

## 4.3 Boolean Assertions

### What it is

AssertJ provides fluent assertions for boolean values.

### Code example

``` java
assertThat(true).isTrue();
assertThat(false).isFalse();
```

### When/why to use it

Use them when the result of the test is expected to be `true` or
`false`.

### Common interview question

**Q: How do you assert a boolean using AssertJ?**

**Model answer:** Use `isTrue()` for a true value and `isFalse()` for a
false value.

------------------------------------------------------------------------

## 4.4 List / Array Assertions

### What it is

AssertJ can validate list/array content and size.

### Code example

``` java
assertThat(List.of("apple", "banana"))
    .contains("apple")
    .doesNotContain("orange")
    .hasSize(2);
```

### When/why to use it

Use it when verifying collection contents and size.

### Common interview question

**Q: What collection assertions are shown in the source?**

**Model answer:** `contains`, `doesNotContain`, and `hasSize`.

------------------------------------------------------------------------

## 4.5 Exception Assertions

### What it is

AssertJ can verify that an operation throws a particular exception and
can validate its message and stack trace.

### Code example

``` java
assertThatThrownBy(() -> {
    throw new IllegalArgumentException("Invalid argument");
})
.isInstanceOf(IllegalArgumentException.class)
.hasMessage("Invalid argument")
.hasStackTraceContaining("ExampleTest");
```

### When/why to use it

Use it when the behavior under test is expected to throw an exception
and you want to validate the exception type and details.

### Common interview question

**Q: How do you assert an exception with AssertJ?**

**Model answer:** Use `assertThatThrownBy(...)` and then assert the
exception type with `isInstanceOf`, its message with `hasMessage`, and
optionally its stack trace with `hasStackTraceContaining`.

------------------------------------------------------------------------

# 5. Mockito

## 5.1 What is Mockito?

### What it is

Mockito is used to test components in isolation without relying on
actual implementations or external dependencies.

The source identifies three core uses: 1. Mocking 2. Stubbing 3.
Verification

### Code example

``` java
@Mock
private EmployeeRepository employeeRepository;

@InjectMocks
private EmployeeServiceImpl employeeService;

@Test
void shouldGetEmployee() {
    Employee employee = new Employee(1L, "John Doe", "john.doe@example.com");

    when(employeeRepository.findById(1L))
        .thenReturn(Optional.of(employee));

    EmployeeDto result = employeeService.getEmployeeById(1L);

    assertThat(result.getName()).isEqualTo("John Doe");
}
```

### When/why to use it

Use Mockito when you want to test a component without depending on the
real implementation of its collaborator or external dependency.

### Common interview question

**Q: Why use Mockito in unit testing?**

**Model answer:** Mockito lets me test a component in isolation by
creating mock objects, defining their behavior through stubbing, and
verifying interactions with them.

------------------------------------------------------------------------

# 6. Mockito --- Mocking

## 6.1 Creating a Mock with @Mock

### What it is

`@Mock` creates a mock object for a dependency.

### Code example

``` java
@Mock
private EmployeeRepository employeeRepository;
```

### When/why to use it

Use it when the class under test should interact with a simulated
dependency instead of its real implementation.

### Common interview question

**Q: How can you create a Mockito mock?**

**Model answer:** The source shows two approaches: use the `@Mock`
annotation or call `Mockito.mock(Classname)`.

------------------------------------------------------------------------

## 6.2 Creating a Mock with Mockito.mock()

### What it is

Mockito can create a mock programmatically using `Mockito.mock(...)`.

### Code example

``` java
EmployeeRepository repository =
    Mockito.mock(EmployeeRepository.class);
```

### When/why to use it

Use this approach when you want to create the mock directly in code
rather than through the annotation shown in the source.

### Common interview question

**Q: What are the two mock-creation approaches shown in the source?**

**Model answer:** `@Mock` and `Mockito.mock(Classname)`.

------------------------------------------------------------------------

# 7. Mockito --- Stubbing

## 7.1 when(...).thenReturn(...)

### What it is

`when(T methodCall)` specifies a method call to stub, while
`thenReturn(T value)` defines the value returned when that stubbed call
occurs.

### Code example

``` java
when(employeeRepository.findById(1L))
    .thenReturn(Optional.of(employee));
```

### When/why to use it

Use stubbing when the class under test depends on a specific result from
a mocked dependency.

### Common interview question

**Q: What is stubbing in Mockito?**

**Model answer:** Stubbing means defining the behavior of a mock for a
specific method call. For example, `when(...).thenReturn(...)` specifies
what value the mock should return.

------------------------------------------------------------------------

## 7.2 thenThrow(...)

### What it is

`thenThrow(...)` makes a stubbed method throw an exception when it is
called.

### Code example

``` java
when(employeeRepository.findById(1L))
    .thenThrow(new RuntimeException("Database error"));
```

### When/why to use it

Use it to test how the class under test behaves when its mocked
dependency throws an exception.

### Common interview question

**Q: Why would you use thenThrow()?**

**Model answer:** To configure a stubbed method to throw an exception
when called, allowing the test to exercise exception-handling behavior.

------------------------------------------------------------------------

# 8. Mockito --- Verification

## 8.1 verify()

### What it is

`verify(mock)` checks that a method was called on a mock.

### Code example

``` java
verify(employeeRepository).findById(1L);
```

### When/why to use it

Use verification when the interaction with a dependency is part of the
behavior you want to validate.

### Common interview question

**Q: What is the difference between stubbing and verification?**

**Model answer:** Stubbing defines what a mock should return or throw
when called. Verification checks whether the mock was called as
expected.

------------------------------------------------------------------------

## 8.2 Verification Modes

### What it is

Mockito supports verification modes that specify how many times or under
what condition an invocation should have occurred.

### Code example

``` java
verify(repository, times(1)).findById(1L);

verify(repository, never()).deleteById(1L);

verify(repository, atLeastOnce()).findById(1L);
```

### When/why to use it

Use a verification mode when simply checking that a method was called is
not enough and the invocation count matters.

### Common interview question

**Q: Which verification modes are covered in the source?**

**Model answer:** `times(int)`, `never()`, `atLeastOnce()`,
`atLeast(int)`, `atMost(int)`, and `only()`.

------------------------------------------------------------------------

## 8.3 Verification Mode Comparison

  Mode              Meaning
  ----------------- ----------------------------------------
  `times(n)`        Exact number of invocations
  `never()`         Method must never be called
  `atLeastOnce()`   Called at least once
  `atLeast(n)`      Called at least `n` times
  `atMost(n)`       Called at most `n` times
  `only()`          No other method was called on the mock

### Examples

``` java
verify(repository, times(2)).findById(1L);
```

``` java
verify(repository, never()).deleteById(1L);
```

``` java
verify(repository, atLeastOnce()).findById(1L);
```

``` java
verify(repository, atLeast(2)).findById(1L);
```

``` java
verify(repository, atMost(3)).findById(1L);
```

``` java
verify(repository, only()).findById(1L);
```

------------------------------------------------------------------------

# 9. Mockito --- ArgumentCaptor

## 9.1 What is ArgumentCaptor?

### What it is

`ArgumentCaptor` captures an argument passed to a mocked method so that
the test can inspect it and perform assertions or validation.

### Code example

``` java
employeeService.saveEmployee(employee);

ArgumentCaptor<Employee> employeeCaptor =
    ArgumentCaptor.forClass(Employee.class);

verify(mockRepository).save(employeeCaptor.capture());

Employee capturedEmployee = employeeCaptor.getValue();

assertThat(capturedEmployee.getName())
    .isEqualTo("John Doe");

assertThat(capturedEmployee.getAge())
    .isEqualTo(30);
```

### When/why to use it

Use an argument captor when you need to inspect the actual object passed
to a mocked dependency rather than only checking that the method was
called.

### Common interview question

**Q: Why use ArgumentCaptor?**

**Model answer:** It captures an argument passed to a mock so I can
retrieve it and make assertions or perform validation on the actual
value passed to the method.

------------------------------------------------------------------------

# 10. Unit Testing vs Integration Testing

## 10.1 Unit Test

### What it is

A unit test covers a single "unit". The source says this commonly means
a single class, although a cohesive cluster of classes can also be
tested together.

### Code example

``` java
class EmployeeServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    @Test
    void testGetEmployeeById() {
        Employee employee =
            new Employee(1L, "John Doe", "john.doe@example.com");

        when(employeeRepository.findById(1L))
            .thenReturn(Optional.of(employee));

        EmployeeDto result =
            employeeService.getEmployeeById(1L);

        assertThat(result.getName()).isEqualTo("John Doe");
        assertThat(result.getEmail())
            .isEqualTo("john.doe@example.com");
    }
}
```

### When/why to use it

The source describes unit testing as focusing on individual components,
such as service methods, and identifies JUnit and Mockito as the tools.

### Common interview question

**Q: What does a unit test focus on?**

**Model answer:** It focuses on an individual unit, commonly a class or
method, using tools such as JUnit and Mockito.

------------------------------------------------------------------------

## 10.2 Integration Test

### What it is

An integration test covers multiple layers and tests interactions
between components, such as a business service and persistence layer.

### Code example

``` java
@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
class EmployeeControllerIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void testGetEmployeeById() {
        ResponseEntity<EmployeeDto> response =
            restTemplate.getForEntity(
                "/employees/1",
                EmployeeDto.class
            );

        assertThat(response.getStatusCodeValue())
            .isEqualTo(200);

        assertThat(response.getBody().getName())
            .isEqualTo("John Doe");

        assertThat(response.getBody().getEmail())
            .isEqualTo("john.doe@example.com");
    }
}
```

### When/why to use it

Use integration testing when the interaction between multiple components
needs to be validated. The source specifically mentions interactions
among repositories, services, and controllers.

### Common interview question

**Q: What does an integration test focus on?**

**Model answer:** It focuses on interactions between multiple components
or layers, such as a service interacting with a repository.

------------------------------------------------------------------------

## 10.3 Unit Test vs Integration Test

  -----------------------------------------------------------------------
  Aspect                  Unit Test               Integration Test
  ----------------------- ----------------------- -----------------------
  Scope                   Single unit / cohesive  Multiple
                          class group             layers/components

  Focus                   Individual              Interaction between
                          component/method        components

  Tools named in source   JUnit, Mockito          Spring Test,
                                                  `@SpringBootTest`

  Example                 Service business logic  Service + repository /
                                                  controller flow

  Dependencies            Tested in isolation     Components interact
  -----------------------------------------------------------------------

------------------------------------------------------------------------

# 11. Spring Boot Testing Annotations

## 11.1 @SpringBootTest

### What it is

`@SpringBootTest` creates an application context and loads the full
application for integration testing.

### Code example

``` java
@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
class EmployeeControllerIntegrationTest {
}
```

### When/why to use it

Use it for integration tests where the full Spring Boot application
context is required.

### Common interview question

**Q: What does @SpringBootTest do?**

**Model answer:** It creates the Spring application context and loads
the full application, making it useful for integration testing.

------------------------------------------------------------------------

## 11.2 @DataJpaTest

### What it is

`@DataJpaTest` is a test slice tailored to JPA components such as
repositories. The source says it configures an in-memory database, sets
up Spring Data JPA repositories, and scans JPA entities.

By default, each test runs in a transaction that is rolled back after
the test completes.

### Code example

``` java
@DataJpaTest
class EmployeeRepositoryTest {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Test
    void shouldFindEmployee() {
        Employee employee =
            employeeRepository.findById(1L).orElseThrow();

        assertThat(employee.getName())
            .isEqualTo("John Doe");
    }
}
```

### When/why to use it

Use it for repository methods and their interactions with the database.
The default rollback behavior provides a clean state between tests.

### Common interview question

**Q: Why would you use @DataJpaTest?**

**Model answer:** It is tailored to JPA testing. It configures the JPA
repositories and an in-memory database, and each test runs in a
transaction that is rolled back afterward.

------------------------------------------------------------------------

## 11.3 @TestConfiguration

### What it is

`@TestConfiguration` is used to define extra beans or configurations for
tests.

### Code example

``` java
@TestConfiguration(proxyBeanMethods = false)
public class TestcontainersConfiguration {

    @Bean
    @ServiceConnection
    PostgreSQLContainer<?> postgresContainer() {
        return new PostgreSQLContainer<>(
            DockerImageName.parse("postgres:latest")
        );
    }
}
```

### When/why to use it

Use it when test-specific beans or configuration need to be defined.

### Common interview question

**Q: What is @TestConfiguration used for?**

**Model answer:** It is used to define extra beans or configurations
specifically for tests.

------------------------------------------------------------------------

## 11.4 @WebMvcTest

### What it is

`@WebMvcTest` is used for testing Spring MVC controllers. The source
says it initializes only the web layer rather than the entire
application context.

### Code example

``` java
@WebMvcTest(EmployeeController.class)
class EmployeeControllerTest {

    @Test
    void shouldGetEmployee() {
        // controller-layer test
    }
}
```

### When/why to use it

Use it when the controller/web layer needs to be tested without loading
the entire application context.

### Common interview question

**Q: What is the difference between @WebMvcTest and @SpringBootTest
according to the source?**

**Model answer:** `@WebMvcTest` initializes only the web layer for
controller testing, whereas `@SpringBootTest` loads the full application
context and is used for integration testing.

------------------------------------------------------------------------

## 11.5 @AutoConfigureTestDatabase

### What it is

`@AutoConfigureTestDatabase` configures replacement behavior for the
test DataSource.

The source shows:

``` java
@AutoConfigureTestDatabase(
    replace = AutoConfigureTestDatabase.Replace.NONE
)
```

### Code example

``` java
@DataJpaTest
@AutoConfigureTestDatabase(
    replace = AutoConfigureTestDatabase.Replace.NONE
)
class EmployeeRepositoryTest {
}
```

### When/why to use it

Use it when the test needs explicit control over whether the
application's DataSource is replaced.

### Common interview question

**Q: What replacement modes are covered by the source?**

**Model answer:** `ANY`, `NONE`, and `AUTO_CONFIGURED`.

------------------------------------------------------------------------

# 12. @AutoConfigureTestDatabase Replacement Modes

  -----------------------------------------------------------------------
  Mode                                Source description
  ----------------------------------- -----------------------------------
  `ANY`                               Replace the DataSource whether it
                                      was auto-configured or manually
                                      defined

  `NONE`                              Do not replace the application's
                                      default DataSource

  `AUTO_CONFIGURED`                   Replace the DataSource only if it
                                      was auto-configured
  -----------------------------------------------------------------------

### Example

``` java
@AutoConfigureTestDatabase(
    replace = AutoConfigureTestDatabase.Replace.NONE
)
```

This is the mode explicitly demonstrated in the source.

------------------------------------------------------------------------

# 13. @DataJpaTest and Transaction Rollback

## What it is

The source states that `@DataJpaTest` runs each test within a
transaction by default, and the transaction is rolled back after the
test completes.

### Code example

``` java
@DataJpaTest
class EmployeeRepositoryTest {

    @Autowired
    private EmployeeRepository repository;

    @Test
    void shouldSaveEmployee() {
        Employee employee = new Employee();

        repository.save(employee);

        // Assertions can be made here.
        // The test transaction is rolled back afterward
        // according to the source.
    }
}
```

### When/why to use it

This behavior keeps tests isolated from one another and provides a clean
state for each test.

### Common interview question

**Q: How does @DataJpaTest keep database tests isolated?**

**Model answer:** By default, each test runs in a transaction that is
rolled back after the test completes, so changes do not affect other
tests.

------------------------------------------------------------------------

# 14. Testcontainers

## 14.1 What is Testcontainers in the Source?

### What it is

The source presents Testcontainers as a way to use a real database
container for repository/integration testing. The example uses
PostgreSQL.

### Code example

``` java
@TestConfiguration(proxyBeanMethods = false)
public class TestcontainersConfiguration {

    @Bean
    @ServiceConnection
    PostgreSQLContainer<?> postgresContainer() {
        return new PostgreSQLContainer<>(
            DockerImageName.parse("postgres:latest")
        );
    }
}
```

Test:

``` java
@DataJpaTest
@Import(TestcontainersConfiguration.class)
class EmployeeRepositoryTest {
}
```

### When/why to use it

The source says to use Testcontainers when testing the repository or
performing integration testing with a database container.

### Common interview question

**Q: How is Testcontainers configured in the source example?**

**Model answer:** Add the Spring Boot Testcontainers test dependency,
create a `@TestConfiguration` with a `@Bean` and `@ServiceConnection`
returning a `PostgreSQLContainer`, and import that configuration into
the test using `@Import`.

------------------------------------------------------------------------

## 14.2 Testcontainers Setup From the Source

### Step 1 --- Docker

The source instructs you to download and run the Docker application.

### Step 2 --- Dependency

``` xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-testcontainers</artifactId>
    <scope>test</scope>
</dependency>
```

### Step 3 --- Test configuration

``` java
@TestConfiguration(proxyBeanMethods = false)
public class TestcontainersConfiguration {

    @Bean
    @ServiceConnection
    PostgreSQLContainer<?> postgresContainer() {
        return new PostgreSQLContainer<>(
            DockerImageName.parse("postgres:latest")
        );
    }
}
```

### Step 4 --- Import into the test

``` java
@DataJpaTest
@Import(TestcontainersConfiguration.class)
class EmployeeRepositoryTest {
    // repository tests
}
```

------------------------------------------------------------------------

# 15. WebTestClient

## 15.1 What is WebTestClient?

### What it is

`WebTestClient` provides a fluent API and supports making real HTTP
requests to the application.

The source says it is primarily associated with WebFlux applications,
but it can also be used with Spring MVC applications when configured
correctly.

### Code example

``` java
@AutoConfigureWebTestClient
class EmployeeIntegrationTest {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void shouldGetEmployee() {
        webTestClient
            .get()
            .uri("/employees/1")
            .exchange()
            .expectStatus().isOk()
            .expectBody()
            .jsonPath("$.id").isNotEmpty()
            .jsonPath("$.name").isEqualTo("Jane Doe")
            .jsonPath("$.email")
            .isEqualTo("jane.doe@example.com");
    }
}
```

### When/why to use it

Use it when the test needs fluent HTTP interaction with the application.
The source specifically shows `@AutoConfigureWebTestClient` for
auto-configuration.

### Common interview question

**Q: Is WebTestClient only for WebFlux?**

**Model answer:** The source says it is primarily associated with
WebFlux, but it can also be used with Spring MVC when set up correctly.

------------------------------------------------------------------------

## 15.2 WebTestClient Response Methods

  ---------------------------------------------------------------------------------------------
  Method                                                    Purpose
  --------------------------------------------------------- -----------------------------------
  `exchange()`                                              Executes the request and returns
                                                            `WebTestClient.ResponseSpec`

  `expectStatus()`                                          Asserts the response status code

  `expectBody()`                                            Asserts the response body

  `expectHeader()`                                          Asserts response headers

  `jsonPath("$.id").isNotEmpty()`                           Checks that the JSON `id` value is
                                                            not empty

  `jsonPath("$.name").isEqualTo("Jane Doe")`                Checks the JSON name

  `jsonPath("$.email").isEqualTo("jane.doe@example.com")`   Checks the JSON email
  ---------------------------------------------------------------------------------------------

### Interview question

**Q: How would you validate the response body with WebTestClient?**

**Model answer:** Use `expectBody()` and then JSON path assertions such
as `jsonPath("$.name").isEqualTo(...)`.

------------------------------------------------------------------------

# 16. Spring Boot Integration Test Example

The source contains an integration-test example using a random HTTP port
and `TestRestTemplate`.

### Code example

``` java
@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
class EmployeeControllerIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void testGetEmployeeById() {
        ResponseEntity<EmployeeDto> response =
            restTemplate.getForEntity(
                "/employees/1",
                EmployeeDto.class
            );

        assertThat(response.getStatusCodeValue())
            .isEqualTo(200);

        assertThat(response.getBody().getName())
            .isEqualTo("John Doe");

        assertThat(response.getBody().getEmail())
            .isEqualTo("john.doe@example.com");
    }
}
```

### When/why to use it

Use this pattern when testing the application through its HTTP endpoint
with the full Spring Boot application context.

### Common interview question

**Q: What is being tested in this integration-test example?**

**Model answer:** The test calls the employee endpoint through HTTP and
verifies the HTTP status and returned employee data, exercising the
integrated application flow.

------------------------------------------------------------------------

# 17. Integration Testing --- Why It Matters

## What it is

Integration testing checks interactions between different modules or
services.

### Code example

``` java
@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
class EmployeeControllerIntegrationTest {

    @Test
    void shouldReturnEmployeeFromIntegratedApplication() {
        // Call the application endpoint
        // Assert the HTTP response
        // Assert returned employee data
    }
}
```

### When/why to use it

The source highlights that configuration errors, missing data, and
incorrect business logic can surface only when components interact.

Integration tests also act as a safeguard against changes breaking
existing functionality and make regression testing more effective.

### Common interview question

**Q: Why can't unit tests alone catch every issue?**

**Model answer:** The source explains that some issues appear only when
components interact, such as configuration errors, missing data, or
incorrect behavior across layers. Integration tests validate those
interactions.

------------------------------------------------------------------------

# 18. JaCoCo --- Java Code Coverage

## 18.1 What is JaCoCo?

### What it is

JaCoCo is a free Java code coverage library distributed under the
Eclipse Public License. The source says it is commonly used to measure
coverage of unit or integration tests.

### Code example

``` xml
<!-- The source refers to using the JaCoCo Maven plugin -->
<!-- Maven plugin configuration goes here -->
```

### When/why to use it

Use JaCoCo to understand which parts of the Java project are covered by
unit or integration tests.

The source identifies these benefits: - Visibility into test coverage -
Identifying dead code - Focusing testing effort on critical areas -
Better project maintenance, compliance, and standards

### Common interview question

**Q: What is JaCoCo used for?**

**Model answer:** JaCoCo is used to measure Java code coverage from unit
or integration tests and provides visibility into which parts of the
code are covered.

------------------------------------------------------------------------

## 18.2 JaCoCo Test Execution

### What it is

The source states that running tests with JUnit automatically sets the
JaCoCo agent in motion and produces a binary coverage file.

### Code example

``` bash
mvn test
```

The source identifies the generated binary report location as:

``` text
target/jacoco.exec
```

### When/why to use it

Use the coverage output to identify areas that are not covered and focus
testing effort accordingly.

### Common interview question

**Q: Where does the source say the JaCoCo binary report is created?**

**Model answer:** It says the binary coverage report is created at
`target/jacoco.exec`.

------------------------------------------------------------------------

# 19. JaCoCo Benefits

  -----------------------------------------------------------------------
  Benefit from source                 Practical meaning
  ----------------------------------- -----------------------------------
  Visibility into test coverage       See which code is covered

  Identify dead code                  Find code that may not be exercised

  Focus on critical areas             Use uncovered areas to guide
                                      testing effort

  Better maintenance, compliance and  Supports better project maintenance
  standards                           and standards
  -----------------------------------------------------------------------

------------------------------------------------------------------------

# 20. Testing Annotations Quick Reference

  ------------------------------------------------------------------------------------------------------
  Annotation                      Purpose                 One-line example
  ------------------------------- ----------------------- ----------------------------------------------
  `@Test`                         Marks a JUnit test      `@Test void test() {}`
                                  method                  

  `@DisplayName`                  Custom test/class       `@DisplayName("Get employee")`
                                  display name            

  `@Disabled`                     Disables test/class     `@Disabled @Test void test() {}`

  `@BeforeEach`                   Runs before every test  `@BeforeEach void setUp() {}`

  `@AfterEach`                    Runs after every test   `@AfterEach void tearDown() {}`

  `@BeforeAll`                    Runs once before all    `@BeforeAll static void init() {}`
                                  tests                   

  `@AfterAll`                     Runs once after all     `@AfterAll static void cleanup() {}`
                                  tests                   

  `@Mock`                         Creates Mockito mock    `@Mock EmployeeRepository repo;`

  `@SpringBootTest`               Loads full application  `@SpringBootTest class Test {}`
                                  context                 

  `@DataJpaTest`                  JPA/repository test     `@DataJpaTest class RepoTest {}`
                                  slice                   

  `@TestConfiguration`            Defines extra test      `@TestConfiguration class Config {}`
                                  beans/configuration     

  `@WebMvcTest`                   Initializes web layer   `@WebMvcTest(EmployeeController.class)`
                                  for MVC controller      
                                  testing                 

  `@AutoConfigureTestDatabase`    Controls test           `@AutoConfigureTestDatabase(replace = NONE)`
                                  DataSource replacement  

  `@AutoConfigureWebTestClient`   Auto-configures         `@AutoConfigureWebTestClient`
                                  WebTestClient           

  `@ServiceConnection`            Used in the source      `@ServiceConnection`
                                  Testcontainers          
                                  configuration           
  ------------------------------------------------------------------------------------------------------

> **Important source-bound note:** The PDFs do not explain every
> annotation's internal mechanics. The table above only records purposes
> demonstrated or explicitly stated in the source.

------------------------------------------------------------------------

# 21. Key Comparisons for Interviews

## 21.1 Unit Test vs Integration Test

  -----------------------------------------------------------------------
                          Unit Test               Integration Test
  ----------------------- ----------------------- -----------------------
  Main scope              Single unit             Multiple layers

  Focus                   Individual              Component interaction
                          component/method        

  Source tools            JUnit, Mockito          Spring Test,
                                                  `@SpringBootTest`

  Example                 Service business logic  Service + repository /
                                                  controller flow
  -----------------------------------------------------------------------

------------------------------------------------------------------------

## 21.2 @SpringBootTest vs @WebMvcTest vs @DataJpaTest

  -----------------------------------------------------------------------
  Annotation                          Source-supported purpose
  ----------------------------------- -----------------------------------
  `@SpringBootTest`                   Full application context;
                                      integration testing

  `@WebMvcTest`                       MVC controller/web-layer testing;
                                      only web layer initialized

  `@DataJpaTest`                      JPA/repository testing; configures
                                      an in-memory database
  -----------------------------------------------------------------------

------------------------------------------------------------------------

## 21.3 JUnit vs AssertJ

  -----------------------------------------------------------------------
                          JUnit                   AssertJ
  ----------------------- ----------------------- -----------------------
  Type                    Testing framework       Assertion library

  Source focus            Write, execute, report  Fluent/expressive
                          tests                   assertions

  Example                 `@Test`                 `assertThat(...)`
  -----------------------------------------------------------------------

------------------------------------------------------------------------

## 21.4 Mockito Mocking vs Stubbing vs Verification

  Concept        Meaning
  -------------- ------------------------------------------------
  Mocking        Create mock objects to simulate real objects
  Stubbing       Define mock behavior for specific method calls
  Verification   Check whether methods were called on mocks

------------------------------------------------------------------------

## 21.5 @DataJpaTest vs Testcontainers

  ------------------------------------------------------------------------
                          `@DataJpaTest`          Testcontainers
  ----------------------- ----------------------- ------------------------
  Source focus            JPA/repository test     Database container for
                          slice                   repository/integration
                                                  testing

  Database setup          Source says it          Source example uses
                          configures an in-memory PostgreSQL container
                          database by default     

  Isolation               Each test transaction   Container-backed
                          is rolled back by       database configuration
                          default                 is shown

  Configuration           `@DataJpaTest`          `@TestConfiguration` +
                                                  `@Bean` +
                                                  `@ServiceConnection` +
                                                  `@Import`
  ------------------------------------------------------------------------

------------------------------------------------------------------------

# 22. Common Mistakes / Gotchas --- Source-Supported Only

This section intentionally does **not** add common Mockito/JUnit advice
that is absent from the PDFs.

## 22.1 Confusing Unit and Integration Tests

**Gotcha:** Treating a test that covers multiple layers as a unit test.

**Source distinction:** Unit tests focus on an individual unit, while
integration tests cover multiple layers and their interactions.

------------------------------------------------------------------------

## 22.2 Loading the Full Context for a Controller Slice

**Gotcha:** Using the full application context when the source
specifically describes `@WebMvcTest` for controller/web-layer testing.

**Source guidance:** `@WebMvcTest` initializes only the web layer.

------------------------------------------------------------------------

## 22.3 Forgetting the Transaction Rollback Behavior of @DataJpaTest

**Gotcha:** Assuming repository test data automatically persists across
tests.

**Source behavior:** `@DataJpaTest` runs each test in a transaction that
is rolled back after completion by default.

------------------------------------------------------------------------

## 22.4 Incorrect DataSource Replacement Assumption

**Gotcha:** Assuming `@AutoConfigureTestDatabase` always replaces the
application's DataSource.

**Source-defined modes:**

``` text
ANY
NONE
AUTO_CONFIGURED
```

Each mode has different replacement behavior.

------------------------------------------------------------------------

## 22.5 Using Only Unit Tests for Integration Problems

**Gotcha:** Assuming isolated unit tests will expose configuration or
cross-component issues.

**Source warning:** Some configuration errors, missing data, and
incorrect business logic surface only when components interact.

------------------------------------------------------------------------

## 22.6 Using ArgumentCaptor Without Inspecting the Captured Argument

**Gotcha:** Capturing an argument but not using it for
assertions/validation.

**Source purpose:** `ArgumentCaptor` is specifically useful for
capturing arguments so they can be inspected and validated.

------------------------------------------------------------------------

# 23. Likely Scenario-Based Interview Questions

## 1. How would you test a service method that depends on a repository?

**Model answer:**

I would treat it as a unit test. I would use Mockito to mock the
repository, stub the expected repository response with
`when(...).thenReturn(...)`, call the service method, assert the result
with assertions such as AssertJ's `assertThat`, and verify the expected
repository interaction.

------------------------------------------------------------------------

## 2. What is the difference between mocking, stubbing, and verification?

**Model answer:**

Mocking creates simulated objects. Stubbing defines what those mocks
should return or throw for particular calls. Verification checks whether
expected methods were actually called.

------------------------------------------------------------------------

## 3. When would you choose an integration test instead of a unit test?

**Model answer:**

When I need to validate interaction between multiple components or
layers. The source gives service-persistence and
repository-service-controller interactions as examples.

------------------------------------------------------------------------

## 4. How would you test a Spring MVC controller without loading the complete application?

**Model answer:**

The source describes `@WebMvcTest` for Spring MVC controllers. It
initializes only the web layer instead of the entire application
context.

------------------------------------------------------------------------

## 5. How would you test a repository?

**Model answer:**

I would use `@DataJpaTest`, which is tailored to JPA components. The
source says it configures an in-memory database, sets up Spring Data JPA
repositories, scans entities, and rolls back each test transaction by
default.

------------------------------------------------------------------------

## 6. How do you keep repository tests isolated?

**Model answer:**

With `@DataJpaTest`, the source says each test runs in a transaction and
that transaction is rolled back after the test completes, providing a
clean state for each test.

------------------------------------------------------------------------

## 7. How would you test against a PostgreSQL container?

**Model answer:**

The source shows using Testcontainers with PostgreSQL. I would add the
`spring-boot-testcontainers` test dependency, define a
`PostgreSQLContainer` as a bean inside `@TestConfiguration`, annotate it
with `@ServiceConnection`, and import that configuration into the
`@DataJpaTest`.

------------------------------------------------------------------------

## 8. How would you test a real HTTP endpoint in an integration test?

**Model answer:**

The source demonstrates two approaches. One uses
`@SpringBootTest(webEnvironment = RANDOM_PORT)` with `TestRestTemplate`.
Another uses `WebTestClient`, which provides a fluent API for HTTP
requests. I would assert the status and response data.

------------------------------------------------------------------------

## 9. How would you verify that a mocked dependency was called exactly once?

**Model answer:**

Use Mockito's verification mode:

``` java
verify(repository, times(1)).findById(1L);
```

The source also covers `never()`, `atLeastOnce()`, `atLeast()`,
`atMost()`, and `only()`.

------------------------------------------------------------------------

## 10. How would you inspect the exact object passed to a mocked repository?

**Model answer:**

I would use `ArgumentCaptor`. It captures the argument passed to the
mock, after which I can retrieve it with `getValue()` and assert its
fields.

------------------------------------------------------------------------

# 24. Fast Interview Revision

## JUnit

``` text
@Test         → test method
@DisplayName  → custom display name
@Disabled     → disable test
@BeforeEach   → before every test
@AfterEach    → after every test
@BeforeAll    → once before all tests
@AfterAll     → once after all tests
```

## AssertJ

``` text
assertThat(5).isEqualTo(5)
assertThat("hello").contains("ell")
assertThat(true).isTrue()
assertThat(list).hasSize(2)
assertThatThrownBy(...).isInstanceOf(...)
```

## Mockito

``` text
@Mock
Mockito.mock(...)

when(...).thenReturn(...)
when(...).thenThrow(...)

verify(...)
verify(..., times(...))
verify(..., never())
verify(..., atLeastOnce())
verify(..., atLeast(...))
verify(..., atMost(...))
verify(..., only())

ArgumentCaptor
```

## Spring Testing

``` text
@SpringBootTest       → full application context
@WebMvcTest           → web/MVC layer
@DataJpaTest          → JPA/repository slice
@TestConfiguration    → extra test beans/config
@AutoConfigureTestDatabase → DataSource replacement
@AutoConfigureWebTestClient → WebTestClient configuration
```

## Integration

``` text
@SpringBootTest + TestRestTemplate
WebTestClient
Testcontainers
```

## Coverage

``` text
JaCoCo
target/jacoco.exec
```

------------------------------------------------------------------------

# 25. Gaps Not Covered in Source Material

The following topics were requested or are common senior-interview
topics, but the uploaded PDFs **do not provide enough source material to
explain them without adding outside knowledge**. They are therefore
intentionally not presented as source-derived content.

### Mockito gaps

-   `@Mock` vs `@MockBean` vs `@Spy`
-   `@InjectMocks` behavior and injection details
-   `MockitoExtension` / `@ExtendWith(MockitoExtension.class)`
-   `when(...).thenReturn(...)` vs `doReturn(...).when(...)`
-   Strict vs lenient stubbing
-   `lenient()`
-   Resetting mocks with `reset()`
-   Static mocking
-   Constructor mocking
-   `verifyNoInteractions()`
-   `verifyNoMoreInteractions()`
-   `InOrder`
-   Mockito argument matchers such as `any()`, `eq()`, `argThat()`

### JUnit gaps

-   `@ParameterizedTest`
-   `@ValueSource`
-   `@CsvSource`
-   `@MethodSource`
-   `@Nested`
-   `@RepeatedTest`
-   JUnit assertions beyond the provided AssertJ examples
-   JUnit extensions
-   Assumptions
-   Test lifecycle details beyond the annotations explicitly covered

### Spring testing gaps

-   Detailed `@MockBean` behavior
-   Detailed `@SpyBean` behavior
-   Spring Security testing
-   `MockMvc`
-   `@SpringBootTest` configuration/customization beyond the source
    example
-   Transaction rollback testing beyond the default `@DataJpaTest`
    behavior described
-   Testing asynchronous methods
-   Testing scheduled jobs
-   Slice-test internals beyond `@WebMvcTest` and `@DataJpaTest`
-   Test profiles and test property configuration

### Integration testing gaps

-   External API integration testing strategies
-   WireMock
-   MockServer
-   Contract testing
-   Kafka integration testing
-   Redis integration testing
-   Full end-to-end testing
-   Distributed integration testing

### JaCoCo gaps

-   Line vs branch vs instruction coverage
-   Coverage thresholds
-   Maven plugin XML configuration details
-   CI/CD quality gates
-   HTML report configuration

> These are listed as **gaps rather than filled in from general
> knowledge**, in accordance with the source-only requirement.

------------------------------------------------------------------------

# 26. Senior Interview Answer Pattern

For a scenario-based testing question, keep the answer structured:

``` text
1. Identify the scope
       ↓
2. Unit or integration?
       ↓
3. Choose the testing tool/annotation
       ↓
4. Arrange the required data/dependencies
       ↓
5. Execute the behavior
       ↓
6. Assert the result
       ↓
7. Verify interactions if using Mockito
       ↓
8. Consider test isolation / rollback where applicable
```

Example:

> "First I would identify whether I am testing one component in
> isolation or an interaction across multiple layers. For an isolated
> service method, I would use JUnit with Mockito, stub the dependency,
> execute the service, assert the result, and verify the interaction. If
> I need to validate multiple Spring layers, I would use an integration
> test such as `@SpringBootTest`. For repository-focused testing, I
> would use `@DataJpaTest`, and if I need a PostgreSQL container, I
> would use the Testcontainers configuration shown in the source."

------------------------------------------------------------------------

# 27. One-Page Mental Model

``` text
                         TESTING
                            |
             +--------------+--------------+
             |                             |
          UNIT TEST                  INTEGRATION TEST
             |                             |
       JUnit + Mockito              Spring Test
             |                     @SpringBootTest
             |                             |
       Isolate component            Multiple layers
             |                             |
       Mock dependencies            Real interaction
             |                             |
       Stub behavior                HTTP / DB flow
             |                             |
       Verify calls                Testcontainers
             |
       Assert results
             |
          AssertJ


             SPRING TEST SLICES
                    |
          +---------+---------+
          |                   |
     @WebMvcTest          @DataJpaTest
          |                   |
      Web layer          JPA/repository
                              |
                       Transaction rollback
                              |
                       Test database


             HTTP TESTING
                    |
          +---------+---------+
          |                   |
    TestRestTemplate      WebTestClient
          |                   |
   @SpringBootTest       Fluent API
   RANDOM_PORT           exchange()
                         expectStatus()
                         expectBody()
                         expectHeader()


             TEST QUALITY
                    |
                  JaCoCo
                    |
            target/jacoco.exec
                    |
        Coverage visibility
                    |
       Identify uncovered areas
                    |
         Focus testing effort
```

------------------------------------------------------------------------

# 28. Final Interview Takeaways

1.  **Unit test** → isolate an individual unit; the source associates it
    with JUnit and Mockito.
2.  **Integration test** → validate interactions across multiple
    layers/components.
3.  **Mockito** → mocking, stubbing, verification, and argument capture.
4.  **AssertJ** → fluent and expressive assertions that complement
    JUnit.
5.  **`@SpringBootTest`** → full application context for integration
    testing.
6.  **`@WebMvcTest`** → web/MVC layer testing.
7.  **`@DataJpaTest`** → JPA/repository testing with an in-memory
    database according to the source.
8.  **`@DataJpaTest` rollback** → each test transaction is rolled back
    by default.
9.  **Testcontainers** → source demonstrates PostgreSQL container
    configuration for repository/integration testing.
10. **WebTestClient** → fluent HTTP testing with `exchange`,
    status/body/header assertions and JSON path checks.
11. **JaCoCo** → Java code coverage for unit/integration tests; source
    identifies `target/jacoco.exec`.
12. **TDD** → failing test → minimum implementation → refactor.
13. **BDD** → human-readable behavior → scenarios → implementation.

------------------------------------------------------------------------

## Source Files Covered

-   `Test_1.pdf` --- Introduction to Testing in Spring Boot
-   `Test_2.pdf` --- JUnit and AssertJ
-   `Test_3.pdf` --- Unit Testing vs Integration Testing
-   `Test_4.pdf` --- Persistence Layer / `@DataJpaTest` / Testcontainers
-   `Test_5.pdf` --- Mockito
-   `Test_6.pdf` --- Integration Testing / WebTestClient
-   `Test_7.pdf` --- JaCoCo


---

# 29. Cucumber — Additional Interview Revision

> **Source status:** Cucumber is **not covered in the seven uploaded PDFs**. The following section is therefore intentionally separated from the source-derived notes and is added because you specifically asked to include Cucumber for interview preparation.

## 29.1 What is Cucumber?

### What it is

Cucumber is a behavior-driven development (BDD) tool that allows application behavior to be written in a human-readable format using **Gherkin** syntax, commonly with `Feature`, `Scenario`, `Given`, `When`, and `Then`.

### Code example

```gherkin
Feature: Employee lookup

  Scenario: Get employee by ID
    Given an employee with ID 1 exists
    When I request employee with ID 1
    Then the response status should be 200
    And the employee name should be "John Doe"
```

### When/why to use it

Use Cucumber when the team wants executable specifications that describe business behavior in a format understandable by both technical and non-technical stakeholders.

### Common interview question

**Q: What is Cucumber?**

**Model answer:** Cucumber is a BDD tool where behavior is expressed in Gherkin scenarios. The scenarios are connected to executable step definitions that drive the application under test.

---

## 29.2 Gherkin

### What it is

Gherkin is the human-readable language used to write Cucumber feature files.

### Code example

```gherkin
Feature: Employee management

  Scenario: Find an existing employee
    Given employee 1 exists
    When I search for employee 1
    Then I should receive the employee details
```

### When/why to use it

Use Gherkin to express expected behavior as readable scenarios before or alongside implementation.

### Common interview question

**Q: What are Given, When, and Then?**

**Model answer:**

- **Given** → establishes the initial context.
- **When** → describes the action being performed.
- **Then** → describes the expected outcome.

---

## 29.3 Feature File

### What it is

A feature file contains one or more business-oriented scenarios written using Gherkin.

### Code example

```gherkin
Feature: Employee API

  Scenario: Successfully retrieve an employee
    Given employee 1 exists
    When the client calls GET "/employees/1"
    Then the response status should be 200
```

### When/why to use it

Use feature files to keep behavior specifications readable and separate from the Java implementation of the test steps.

### Common interview question

**Q: What is a feature file?**

**Model answer:** It is a file containing Gherkin-based feature descriptions and scenarios that express the expected behavior of the application.

---

## 29.4 Step Definitions

### What it is

Step definitions connect the natural-language steps in a Cucumber scenario to executable Java code.

### Code example

```java
@Given("employee {long} exists")
public void employeeExists(long id) {
    // prepare test data
}

@When("I request employee with ID {long}")
public void requestEmployee(long id) {
    // execute API/application call
}

@Then("the response status should be {int}")
public void verifyStatus(int status) {
    // assert response status
}
```

### When/why to use it

Use step definitions to translate business-readable Gherkin scenarios into actual test actions and assertions.

### Common interview question

**Q: What is the relationship between a feature file and step definitions?**

**Model answer:** The feature file describes the behavior in Gherkin, while step definitions provide the Java implementation that executes each Gherkin step.

---

## 29.5 Cucumber + Spring Boot

### What it is

Cucumber can be used as the BDD layer while Spring Boot provides the application being tested.

### Code example

```java
@SpringBootTest
public class CucumberSpringConfiguration {
}
```

```java
@When("I request employee with ID {long}")
public void requestEmployee(long id) {
    // invoke the Spring Boot application
}
```

### When/why to use it

Use this combination when the interview scenario involves expressing business behavior in Cucumber while exercising Spring Boot application functionality.

### Common interview question

**Q: How would you integrate Cucumber with Spring Boot?**

**Model answer:** I would use Cucumber feature files for behavior, Java step definitions for execution, and Spring Boot test configuration so the step definitions can interact with the application being tested.

---

## 29.6 Cucumber vs Unit Testing

| Aspect | Unit Test | Cucumber |
|---|---|---|
| Main focus | Individual unit/component | Business behavior/scenario |
| Typical style | Java test code | Gherkin feature |
| Readability | Primarily developer-focused | Human-readable behavior |
| Typical example | Service method with Mockito | Employee lookup scenario |
| Relationship | Tests implementation/component behavior | Describes and executes business scenarios |

---

## 29.7 Cucumber vs BDD

### What it is

BDD is the development/testing approach centered around describing expected behavior. Cucumber is a tool commonly used to express and execute those behaviors through Gherkin scenarios.

### Code example

```gherkin
Scenario: Invalid employee ID
  Given employee 999 does not exist
  When I request employee with ID 999
  Then the employee should not be returned
```

### When/why to use it

In an interview, distinguish the **practice/approach** from the **tool**:

```text
BDD
  ↓
Describe behavior
  ↓
Write scenarios
  ↓
Implement behavior
  ↓
Cucumber can execute those scenarios
```

### Common interview question

**Q: Is Cucumber the same thing as BDD?**

**Model answer:** No. BDD is an approach for describing and developing behavior. Cucumber is a tool that can express and execute BDD-style scenarios using Gherkin.

---

## 29.8 Senior Scenario — Testing an Employee API with Cucumber

### Scenario

You have a Spring Boot employee API and want to verify the complete behavior of retrieving an employee.

### Flow

```text
Feature file
    |
    | Given / When / Then
    v
Step Definitions
    |
    v
Spring Boot Application
    |
    +--> Controller
    |
    +--> Service
    |
    +--> Repository / Database
    |
    v
Assertions
```

### Interview answer

> "For a business-level scenario, I would describe the expected behavior in a Cucumber feature file using Given/When/Then. The step definitions would translate those steps into Java actions. If I need to validate the integrated Spring Boot flow, the step definitions can exercise the application rather than mocking every internal component."

---

## 29.9 Cucumber Interview Quick Revision

```text
Cucumber       → BDD test tool
Gherkin        → Human-readable scenario language
Feature        → Describes a feature
Scenario       → Describes one behavior
Given          → Initial context
When           → Action
Then           → Expected result
Step Definition→ Java implementation of a Gherkin step
```

---

# 30. Updated Testing Mental Model

```text
                         TESTING
                            |
          +-----------------+------------------+
          |                 |                  |
       UNIT TEST       INTEGRATION TEST       BDD
          |                 |                  |
   JUnit + Mockito     Spring Test         Cucumber
          |                 |                  |
   Isolate component   Multiple layers       Gherkin
          |                 |                  |
   Mock dependencies   HTTP / DB flow       Scenarios
          |                 |                  |
   Stub + Verify       Testcontainers       Step Definitions
          |                 |                  |
       Assert          WebTestClient       Business behavior
```

---

# 31. Cucumber — Gap Notice

Because Cucumber was **not present in the uploaded PDFs**, the following Cucumber topics are not claimed to be source-derived:

- Cucumber dependencies/version configuration
- `@CucumberContextConfiguration`
- Cucumber runner configuration
- JUnit Platform integration details
- Scenario Outline
- Examples tables
- Data Tables
- Background
- Hooks such as `@Before` / `@After`
- Tags
- Cucumber expressions vs regular expressions
- Parallel execution
- Reporting
- Cucumber with Mockito
- Cucumber with MockMvc/WebTestClient in detailed configuration
- Cucumber with Testcontainers
