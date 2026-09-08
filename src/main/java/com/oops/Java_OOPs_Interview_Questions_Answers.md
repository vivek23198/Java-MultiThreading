# Java OOPs — Interview Questions & Answers

## How to use this guide

This guide is designed for Java backend interviews, especially for 4–7 years of experience.

Priority:
- ⭐⭐⭐ = Must know
- ⭐⭐ = Important
- ⭐ = Good to know

This guide includes:
- Core OOP concepts
- Java-specific OOP behavior
- Code snippets
- Tricky output questions
- Common interview traps
- Design-oriented questions
- Rapid-fire revision

---

# 1. What is OOP?

## Q1. What is Object-Oriented Programming? ⭐⭐⭐

OOP is a programming approach where software is designed around **objects**, which contain:

- State → fields/data
- Behavior → methods

Example:

```java
class Employee {

    String name;
    double salary;

    void work() {
        System.out.println(name + " is working");
    }
}
```

Creating an object:

```java
Employee emp = new Employee();

emp.name = "Vivek";
emp.salary = 50000;

emp.work();
```

Output:

```text
Vivek is working
```

---

# 2. Four Pillars of OOP

## Q2. What are the four pillars of OOP? ⭐⭐⭐

The four major pillars are:

```text
OOP
 |
 +-- Encapsulation
 |
 +-- Inheritance
 |
 +-- Polymorphism
 |
 +-- Abstraction
```

Memory trick:

> **EIPA** → Encapsulation, Inheritance, Polymorphism, Abstraction

---

# 3. Encapsulation

## Q3. What is encapsulation? ⭐⭐⭐

Encapsulation means **bundling data and methods together and controlling direct access to the object's internal state**.

Example:

```java
class Employee {

    private double salary;

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {

        if (salary < 0) {
            throw new IllegalArgumentException(
                "Salary cannot be negative"
            );
        }

        this.salary = salary;
    }
}
```

The field is private, so outside code cannot directly modify it.

```java
Employee emp = new Employee();

emp.setSalary(50000);

System.out.println(emp.getSalary());
```

Output:

```text
50000.0
```

---

## Q4. Why is encapsulation important?

It provides:

- Data hiding
- Controlled modification
- Validation
- Maintainability
- Better security
- Reduced coupling

Instead of:

```java
emp.salary = -50000;
```

we force updates through:

```java
emp.setSalary(-50000);
```

and can reject invalid data.

---

## Q5. Is encapsulation simply making fields private?

No.

`private` is an important part, but encapsulation is broader.

It means the class controls how its internal state is accessed or modified.

For example:

```java
class BankAccount {

    private double balance;

    public void deposit(double amount) {

        if (amount <= 0) {
            throw new IllegalArgumentException();
        }

        balance += amount;
    }
}
```

The class controls the valid operations on its state.

---

# 4. Abstraction

## Q6. What is abstraction? ⭐⭐⭐

Abstraction means **hiding implementation details and exposing only the required behavior**.

Example:

```java
interface PaymentService {

    void pay(double amount);
}
```

Implementation:

```java
class UpiPayment implements PaymentService {

    @Override
    public void pay(double amount) {
        System.out.println(
            "Processing UPI payment: " + amount
        );
    }
}
```

Client:

```java
PaymentService payment = new UpiPayment();

payment.pay(1000);
```

The caller knows:

```text
pay()
```

but does not need to know the internal UPI processing logic.

---

## Q7. How is abstraction achieved in Java? ⭐⭐⭐

Mainly through:

### Abstract classes

```java
abstract class Vehicle {

    abstract void start();

    void stop() {
        System.out.println("Vehicle stopped");
    }
}
```

### Interfaces

```java
interface Payment {
    void pay();
}
```

---

# 5. Abstract Class

## Q8. What is an abstract class? ⭐⭐⭐

A class declared using `abstract`.

```java
abstract class Animal {

    abstract void sound();

    void sleep() {
        System.out.println("Sleeping");
    }
}
```

It can contain:

- Abstract methods
- Concrete methods
- Constructors
- Instance variables
- Static methods
- Final methods

---

## Q9. Can we create an object of an abstract class?

No.

```java
abstract class Animal {
}
```

This is invalid:

```java
Animal a = new Animal();
```

But we can create a reference:

```java
Animal a = new Dog();
```

---

## Q10. Can an abstract class have a constructor?

Yes.

```java
abstract class Animal {

    Animal() {
        System.out.println("Animal constructor");
    }

    abstract void sound();
}

class Dog extends Animal {

    Dog() {
        System.out.println("Dog constructor");
    }

    @Override
    void sound() {
        System.out.println("Bark");
    }
}
```

```java
Animal animal = new Dog();
```

Output:

```text
Animal constructor
Dog constructor
```

---

## Q11. Can an abstract class have no abstract methods?

Yes.

```java
abstract class Vehicle {

    void start() {
        System.out.println("Start");
    }
}
```

It can be abstract simply to prevent direct instantiation.

---

# 6. Interface

## Q12. What is an interface? ⭐⭐⭐

An interface defines a contract that implementing classes agree to follow.

```java
interface Notification {

    void send(String message);
}
```

Implementations:

```java
class EmailNotification implements Notification {

    @Override
    public void send(String message) {
        System.out.println("Email: " + message);
    }
}

class SmsNotification implements Notification {

    @Override
    public void send(String message) {
        System.out.println("SMS: " + message);
    }
}
```

---

## Q13. Can an interface have method implementations?

Yes.

Modern Java interfaces can have:

- `default` methods
- `static` methods
- private helper methods

Example:

```java
interface Payment {

    void pay();

    default void receipt() {
        System.out.println("Receipt generated");
    }

    static void info() {
        System.out.println("Payment interface");
    }
}
```

---

## Q14. Interface vs abstract class? ⭐⭐⭐

| Interface | Abstract Class |
|---|---|
| Defines a contract/capability | Defines common abstraction/base behavior |
| Class can implement multiple interfaces | Class can extend only one class |
| No instance state in the traditional sense; can have constants | Can have instance variables |
| Supports default/static/private methods | Supports concrete and abstract methods |
| No normal instance constructor | Can have constructors |
| Implemented using `implements` | Extended using `extends` |

Interview memory:

> **Interface = contract/capability. Abstract class = shared base/state/behavior.**

---

# 7. Inheritance

## Q15. What is inheritance? ⭐⭐⭐

Inheritance allows one class to acquire properties and behavior from another class.

```java
class Animal {

    void eat() {
        System.out.println("Eating");
    }
}

class Dog extends Animal {

    void bark() {
        System.out.println("Barking");
    }
}
```

Usage:

```java
Dog dog = new Dog();

dog.eat();
dog.bark();
```

Output:

```text
Eating
Barking
```

---

## Q16. Why is inheritance used?

It can provide:

- Code reuse
- Method overriding
- Polymorphism
- Hierarchical modeling

But inheritance should not be used just for code reuse. If the relationship is not genuinely **is-a**, composition is often better.

---

## Q17. What is an IS-A relationship?

Inheritance represents an IS-A relationship.

```text
Dog IS-A Animal
Car IS-A Vehicle
```

Example:

```java
class Dog extends Animal {
}
```

---

## Q18. What is HAS-A relationship?

Composition represents a HAS-A relationship.

```text
Car HAS-A Engine
Order HAS-A Payment
Employee HAS-A Address
```

Example:

```java
class Car {

    private Engine engine;

    Car(Engine engine) {
        this.engine = engine;
    }
}
```

---

# 8. Types of Inheritance

## Q19. What types of inheritance are supported through classes in Java?

Common forms:

### Single

```text
A
|
B
```

### Multilevel

```text
A
|
B
|
C
```

### Hierarchical

```text
      A
     / \
    B   C
```

Java does **not** support multiple inheritance of classes.

---

## Q20. Why does Java not support multiple inheritance of classes? ⭐⭐⭐

Because it can create ambiguity, especially the **diamond problem**.

Imagine:

```text
       A
      / \
     B   C
      \ /
       D
```

If both `B` and `C` override the same method, which implementation should `D` inherit?

Java avoids this ambiguity by allowing:

```java
class D extends B implements CInterface
```

but not:

```java
class D extends B, C
```

---

# 9. Composition

## Q21. What is composition? ⭐⭐⭐

Composition means one class contains another object as a field.

```java
class Engine {

    void start() {
        System.out.println("Engine started");
    }
}

class Car {

    private final Engine engine;

    Car(Engine engine) {
        this.engine = engine;
    }

    void startCar() {
        engine.start();
    }
}
```

Here:

```text
Car HAS-A Engine
```

---

## Q22. Composition vs inheritance? ⭐⭐⭐

| Inheritance | Composition |
|---|---|
| IS-A | HAS-A |
| `extends` | Object reference/field |
| Tighter coupling | Usually looser coupling |
| Parent behavior is inherited | Behavior is delegated |
| Can be harder to change | More flexible |
| Useful for genuine subtype relationships | Often preferred for reuse |

Interview one-liner:

> Favor composition over inheritance when behavior needs to vary independently or the relationship is not a true IS-A relationship.

---

# 10. Polymorphism

## Q23. What is polymorphism? ⭐⭐⭐

Polymorphism means **one interface/reference can represent multiple forms of an object**.

Two major types in Java:

```text
Polymorphism
├── Compile-time
│   └── Method Overloading
│
└── Runtime
    └── Method Overriding
```

---

# 11. Method Overloading

## Q24. What is method overloading? ⭐⭐⭐

Multiple methods have the same name but different parameter lists.

```java
class Calculator {

    int add(int a, int b) {
        return a + b;
    }

    int add(int a, int b, int c) {
        return a + b + c;
    }

    double add(double a, double b) {
        return a + b;
    }
}
```

---

## Q25. Can we overload by changing only return type?

No.

Invalid:

```java
int add(int a, int b) {
    return a + b;
}

double add(int a, int b) {
    return a + b;
}
```

The parameter list is identical.

---

## Q26. What can change for method overloading?

The parameter list can differ by:

- Number
- Type
- Order

Example:

```java
void test(int x) {}

void test(int x, int y) {}

void test(String x) {}

void test(String x, int y) {}
```

---

# 12. Method Overriding

## Q27. What is method overriding? ⭐⭐⭐

A child class provides its own implementation of a parent method.

```java
class Animal {

    void sound() {
        System.out.println("Animal sound");
    }
}

class Dog extends Animal {

    @Override
    void sound() {
        System.out.println("Bark");
    }
}
```

---

## Q28. What is runtime polymorphism? ⭐⭐⭐

When a parent reference points to a child object, the overridden method is selected based on the actual object at runtime.

```java
Animal animal = new Dog();

animal.sound();
```

Output:

```text
Bark
```

The reference type is `Animal`, but the actual object is `Dog`.

---

# 13. Compile-Time vs Runtime Polymorphism

## Q29. Overloading vs overriding? ⭐⭐⭐

| Overloading | Overriding |
|---|---|
| Compile-time polymorphism | Runtime polymorphism |
| Same class commonly | Parent-child relationship |
| Different parameter list | Same method signature |
| Return type alone cannot distinguish | Covariant return allowed |
| Static binding | Dynamic dispatch |

---

# 14. Upcasting and Downcasting

## Q30. What is upcasting?

Converting a child reference to a parent reference.

```java
Dog dog = new Dog();

Animal animal = dog;
```

This is safe and implicit.

---

## Q31. What is downcasting?

Converting a parent reference back to a child reference.

```java
Animal animal = new Dog();

Dog dog = (Dog) animal;
```

It requires an explicit cast.

---

## Q32. What happens with invalid downcasting?

```java
Animal animal = new Cat();

Dog dog = (Dog) animal;
```

Runtime:

```text
ClassCastException
```

Use `instanceof` when appropriate:

```java
if (animal instanceof Dog) {
    Dog dog = (Dog) animal;
}
```

Modern Java:

```java
if (animal instanceof Dog dog) {
    dog.bark();
}
```

---

# 15. Dynamic Method Dispatch

## Q33. What is dynamic method dispatch? ⭐⭐⭐

It is the mechanism by which Java chooses an overridden instance method at runtime based on the actual object.

```java
Animal a = new Dog();

a.sound();
```

Java invokes:

```text
Dog.sound()
```

not:

```text
Animal.sound()
```

---

# 16. Static Methods and Polymorphism

## Q34. Are static methods overridden? ⭐⭐⭐

No.

Static methods are associated with the class, not dynamically dispatched based on the object.

They are **hidden**, not overridden.

Example:

```java
class Parent {

    static void show() {
        System.out.println("Parent");
    }
}

class Child extends Parent {

    static void show() {
        System.out.println("Child");
    }
}

Parent p = new Child();

p.show();
```

Output:

```text
Parent
```

The reference/class type determines the static method call.

---

# 17. Instance Variables and Polymorphism

## Q35. Are instance variables overridden?

No.

Fields are hidden, not overridden.

```java
class Parent {

    int x = 10;
}

class Child extends Parent {

    int x = 20;
}

Parent p = new Child();

System.out.println(p.x);
```

Output:

```text
10
```

Methods use runtime polymorphism; fields do not.

---

# 18. Constructor and Polymorphism

## Q36. Are constructors inherited?

No.

Constructors belong to the class that declares them.

A child constructor can invoke a parent constructor using:

```java
super();
```

---

## Q37. Can constructors be overridden?

No.

Constructors are not inherited and do not participate in overriding.

---

# 19. `this` and `super`

## Q38. What is `this`? ⭐⭐

`this` refers to the current object.

Example:

```java
class Employee {

    private String name;

    Employee(String name) {
        this.name = name;
    }
}
```

---

## Q39. What is `super`? ⭐⭐

`super` refers to the immediate parent class.

Used to:
- access parent fields
- call parent methods
- call parent constructor

Example:

```java
class Parent {

    int x = 10;

    void show() {
        System.out.println("Parent");
    }
}

class Child extends Parent {

    int x = 20;

    void print() {
        System.out.println(super.x);
        super.show();
    }
}
```

Output:

```text
10
Parent
```

---

# 20. Constructor Chaining

## Q40. What is constructor chaining? ⭐⭐⭐

Calling one constructor from another constructor.

Within the same class:

```java
this(...)
```

Parent class:

```java
super(...)
```

Example:

```java
class Employee {

    Employee() {
        this("Unknown");
    }

    Employee(String name) {
        System.out.println(name);
    }
}
```

---

## Q41. What happens when a child object is created?

Parent constructor executes before child constructor.

```java
class Parent {

    Parent() {
        System.out.println("Parent");
    }
}

class Child extends Parent {

    Child() {
        System.out.println("Child");
    }
}

new Child();
```

Output:

```text
Parent
Child
```

---

# 21. Access Modifiers and OOP

## Q42. Explain Java access modifiers. ⭐⭐⭐

```text
public
protected
default
private
```

Visibility:

| Modifier | Same Class | Same Package | Subclass | Everywhere |
|---|---:|---:|---:|---:|
| private | Yes | No | No | No |
| default | Yes | Yes | Same package | No |
| protected | Yes | Yes | Yes* | No |
| public | Yes | Yes | Yes | Yes |

`protected` has special rules for subclasses in different packages.

---

# 22. Encapsulation and Immutability

## Q43. Is encapsulation the same as immutability?

No.

### Encapsulation

Controls access to state.

### Immutability

Object state cannot change after construction.

Example immutable class:

```java
final class Employee {

    private final String name;
    private final int age;

    Employee(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }
}
```

No setters are provided.

---

# 23. SOLID and OOP

## Q44. What is SOLID? ⭐⭐⭐

SOLID represents five design principles:

```text
S → Single Responsibility Principle
O → Open/Closed Principle
L → Liskov Substitution Principle
I → Interface Segregation Principle
D → Dependency Inversion Principle
```

---

## Q45. What is Single Responsibility Principle?

A class should have one primary responsibility and one reason to change.

Bad:

```java
class EmployeeService {

    void calculateSalary() {}
    void saveToDatabase() {}
    void sendEmail() {}
}
```

Better separation:

```text
SalaryService
EmployeeRepository
EmailService
```

---

## Q46. What is Open/Closed Principle?

Software entities should be:

> Open for extension, closed for modification.

Strategy pattern is a common example.

```java
interface PaymentStrategy {
    void pay(double amount);
}
```

Add:

```java
class UpiPayment implements PaymentStrategy {
    public void pay(double amount) {}
}
```

without modifying the existing payment context.

---

## Q47. What is Liskov Substitution Principle?

A child type should be usable wherever its parent type is expected without breaking expected behavior.

Classic violation:

```text
Bird
  |
  +-- Sparrow
  +-- Penguin
```

If `Bird` requires every bird to fly, `Penguin` breaks the abstraction.

---

## Q48. What is Interface Segregation Principle?

Clients should not be forced to depend on methods they do not use.

Bad:

```java
interface Worker {
    void work();
    void eat();
}
```

A robot may not need `eat()`.

Better:

```java
interface Workable {
    void work();
}

interface Eatable {
    void eat();
}
```

---

## Q49. What is Dependency Inversion Principle?

High-level modules should depend on abstractions, not concrete implementations.

Bad:

```java
class OrderService {

    private EmailService emailService =
        new EmailService();
}
```

Better:

```java
class OrderService {

    private final NotificationService notificationService;

    OrderService(NotificationService notificationService) {
        this.notificationService = notificationService;
    }
}
```

This is also a common Spring dependency-injection pattern.

---

# 24. Tricky Output Questions

## Q50. What is the output?

```java
class Parent {

    void show() {
        System.out.println("Parent");
    }
}

class Child extends Parent {

    @Override
    void show() {
        System.out.println("Child");
    }
}

Parent p = new Child();

p.show();
```

Output:

```text
Child
```

Reason:

> Overridden instance methods use runtime polymorphism.

---

## Q51. What is the output?

```java
class Parent {

    static void show() {
        System.out.println("Parent");
    }
}

class Child extends Parent {

    static void show() {
        System.out.println("Child");
    }
}

Parent p = new Child();

p.show();
```

Output:

```text
Parent
```

Reason:

> Static methods are hidden, not overridden.

---

## Q52. What is the output?

```java
class Parent {

    int x = 10;
}

class Child extends Parent {

    int x = 20;
}

Parent p = new Child();

System.out.println(p.x);
```

Output:

```text
10
```

Reason:

> Fields are resolved using the reference type.

---

## Q53. What is the output?

```java
class Parent {

    void show() {
        System.out.println("Parent");
    }
}

class Child extends Parent {

    @Override
    void show() {
        System.out.println("Child");
    }

    void childOnly() {
        System.out.println("Child Only");
    }
}

Parent p = new Child();

p.show();
// p.childOnly(); // compile error
```

Output:

```text
Child
```

`p.childOnly()` is not accessible through a `Parent` reference.

---

## Q54. What is the output?

```java
class Parent {

    Parent() {
        show();
    }

    void show() {
        System.out.println("Parent");
    }
}

class Child extends Parent {

    private int x = 10;

    @Override
    void show() {
        System.out.println(x);
    }
}

new Child();
```

Output is:

```text
0
```

Reason:

> The parent constructor runs before child instance field initialization. The overridden `show()` executes while the child is still being constructed, so `x` still has its default value `0`.

Interview point:

> Avoid calling overridable methods from constructors.

---

## Q55. What is the output?

```java
class Parent {

    Parent() {
        System.out.println("Parent");
    }
}

class Child extends Parent {

    Child() {
        System.out.println("Child");
    }
}

new Child();
```

Output:

```text
Parent
Child
```

---

## Q56. What is the output?

```java
class Test {

    void show(Object obj) {
        System.out.println("Object");
    }

    void show(String str) {
        System.out.println("String");
    }
}

Test t = new Test();

t.show(null);
```

Output:

```text
String
```

Reason:

`String` is more specific than `Object`, so the compiler chooses the `String` overload.

---

## Q57. What happens here?

```java
class Test {

    void show(String s) {
        System.out.println("String");
    }

    void show(Integer i) {
        System.out.println("Integer");
    }
}

Test t = new Test();

t.show(null);
```

This causes a compile-time error because both `String` and `Integer` are unrelated reference types and neither overload is more specific.

---

## Q58. What is the output?

```java
class Parent {

    void show() {
        System.out.println("Parent");
    }
}

class Child extends Parent {

    @Override
    void show() {
        System.out.println("Child");
    }

    void test() {
        super.show();
        this.show();
    }
}

new Child().test();
```

Output:

```text
Parent
Child
```

---

# 25. Advanced OOP Tricky Questions

## Q59. Can a private method be overridden?

No.

Private methods are not inherited by subclasses, so they cannot be overridden.

---

## Q60. What happens if a child defines a method with the same signature as a private parent method?

It is a new method, not an override.

```java
class Parent {

    private void show() {
        System.out.println("Parent");
    }
}

class Child extends Parent {

    void show() {
        System.out.println("Child");
    }
}
```

`Child.show()` is independent of `Parent.show()`.

---

## Q61. Can a final method be overridden?

No.

```java
class Parent {

    final void show() {
    }
}
```

Child cannot override `show()`.

---

## Q62. Can a final class be inherited?

No.

```java
final class Parent {
}
```

This is invalid:

```java
class Child extends Parent {
}
```

---

## Q63. Can an abstract method be private?

No.

A private method cannot be overridden, but an abstract method requires subclass implementation.

---

## Q64. Can an abstract class be final?

No.

`abstract` requires inheritance/implementation, while `final` prevents inheritance.

---

## Q65. Can an interface extend another interface?

Yes.

```java
interface A {
    void a();
}

interface B extends A {
    void b();
}
```

---

## Q66. Can an interface extend multiple interfaces?

Yes.

```java
interface A {
}

interface B {
}

interface C extends A, B {
}
```

This is one reason Java can achieve multiple inheritance of type/contracts through interfaces.

---

# 26. Default Method Diamond Problem

## Q67. What happens if two interfaces provide the same default method? ⭐⭐⭐

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

class C implements A, B {

    @Override
    public void show() {
        A.super.show();
    }
}
```

Output:

```text
A
```

Without the override, the class would have an ambiguity.

---

# 27. Covariant Return Type

## Q68. What is a covariant return type? ⭐⭐

An overriding method can return a subtype of the parent's return type.

```java
class Parent {

    Object getValue() {
        return new Object();
    }
}

class Child extends Parent {

    @Override
    String getValue() {
        return "Hello";
    }
}
```

`String` is a subtype of `Object`.

---

# 28. OOP and Java Memory

## Q69. Where are objects created?

Objects are generally allocated on the heap.

```java
Employee e = new Employee();
```

The variable `e` is a reference, while the `Employee` object is allocated in heap memory.

The exact JVM implementation details can vary, especially with optimizations such as escape analysis.

---

# 29. Object Class

## Q70. Why is Object important in Java? ⭐⭐⭐

`Object` is the root class of Java's class hierarchy.

Important methods include:

```java
toString()
equals()
hashCode()
getClass()
clone()
wait()
notify()
notifyAll()
```

Every normal Java class ultimately inherits from `Object`.

---

# 30. equals and hashCode

## Q71. Why should equals() and hashCode() be overridden together? ⭐⭐⭐

If two objects are equal according to `equals()`, they must return the same hash code.

```java
if (a.equals(b)) {
    a.hashCode() == b.hashCode();
}
```

Example:

```java
class Employee {

    private final int id;

    Employee(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;

        if (!(o instanceof Employee)) {
            return false;
        }

        Employee e = (Employee) o;

        return id == e.id;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }
}
```

This is especially important when using:

```java
HashMap
HashSet
HashMap keys
HashSet elements
```

---

# 31. Object Equality

## Q72. Difference between `==` and `equals()`? ⭐⭐⭐

For objects:

```java
==
```

checks whether references point to the same object.

```java
equals()
```

checks logical equality according to the class implementation.

Example:

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

# 32. Immutability and OOP

## Q73. How would you design an immutable class? ⭐⭐⭐

Typical rules:

1. Make class `final`.
2. Make fields `private final`.
3. Initialize them through constructor.
4. Do not provide setters.
5. Defensively copy mutable fields.
6. Return defensive copies of mutable fields.

Example:

```java
final class Employee {

    private final String name;
    private final List<String> skills;

    Employee(String name, List<String> skills) {
        this.name = name;
        this.skills = new ArrayList<>(skills);
    }

    public String getName() {
        return name;
    }

    public List<String> getSkills() {
        return new ArrayList<>(skills);
    }
}
```

---

# 33. Dependency Injection

## Q74. What is dependency injection? ⭐⭐⭐

Dependency Injection means an object's dependencies are supplied from outside instead of the object creating them itself.

Bad:

```java
class OrderService {

    private PaymentService paymentService =
        new PaymentService();
}
```

Better:

```java
class OrderService {

    private final PaymentService paymentService;

    OrderService(PaymentService paymentService) {
        this.paymentService = paymentService;
    }
}
```

Spring commonly uses constructor injection.

---

## Q75. Why is constructor injection preferred?

Advantages:
- dependencies are explicit
- supports immutability with `final`
- easier unit testing
- object cannot exist without required dependencies
- avoids hidden dependencies

---

# 34. Coupling and Cohesion

## Q76. What is coupling?

Coupling measures how strongly components depend on each other.

Prefer:

```text
Low coupling
```

Example:

```java
OrderService -> PaymentService interface
```

instead of tightly depending on a concrete implementation.

---

## Q77. What is cohesion?

Cohesion measures how closely related the responsibilities inside a class/module are.

Prefer:

```text
High cohesion
```

Example:

```java
EmployeeRepository
```

should primarily deal with employee persistence.

---

# 35. Association, Aggregation, Composition

## Q78. What is association?

A general relationship between objects.

```text
Teacher ---- Student
```

Both can exist independently.

---

## Q79. What is aggregation?

A weak HAS-A relationship where the contained object can exist independently.

```text
Department ---- Employee
```

If a department is deleted, employees can still exist.

---

## Q80. What is composition?

A strong ownership relationship where the contained object's lifecycle is strongly tied to the owner.

Conceptually:

```text
House ---- Room
```

The room is part of the house's lifecycle.

Interview note:

> In Java, aggregation and composition are design concepts rather than special language keywords.

---

# 36. Object-Oriented Design Scenarios

## Q81. Design a notification system using OOP. ⭐⭐⭐

Use abstraction and polymorphism.

```java
interface NotificationService {
    void send(String message);
}
```

Implementations:

```java
class EmailNotification implements NotificationService {

    public void send(String message) {
        System.out.println("Email: " + message);
    }
}

class SmsNotification implements NotificationService {

    public void send(String message) {
        System.out.println("SMS: " + message);
    }
}
```

Service:

```java
class NotificationManager {

    private final NotificationService service;

    NotificationManager(NotificationService service) {
        this.service = service;
    }

    void notifyUser(String message) {
        service.send(message);
    }
}
```

This demonstrates:
- Abstraction
- Polymorphism
- Encapsulation
- Dependency Injection
- Low coupling

---

## Q82. Design a payment system using OOP.

```java
interface PaymentStrategy {
    void pay(double amount);
}
```

Implementations:

```java
class CreditCardPayment implements PaymentStrategy {

    public void pay(double amount) {
        System.out.println(
            "Credit card: " + amount
        );
    }
}

class UpiPayment implements PaymentStrategy {

    public void pay(double amount) {
        System.out.println(
            "UPI: " + amount
        );
    }
}
```

Context:

```java
class PaymentContext {

    private final PaymentStrategy strategy;

    PaymentContext(PaymentStrategy strategy) {
        this.strategy = strategy;
    }

    void pay(double amount) {
        strategy.pay(amount);
    }
}
```

Usage:

```java
PaymentContext context =
    new PaymentContext(new UpiPayment());

context.pay(1000);
```

Output:

```text
UPI: 1000.0
```

This is a practical example of Strategy + polymorphism.

---

# 37. OOP Design Pattern Connection

## Q83. How are design patterns related to OOP?

Design patterns are reusable design solutions that use OOP principles.

Examples:

```text
Factory
→ Encapsulation + abstraction

Strategy
→ Polymorphism + composition

Decorator
→ Composition + polymorphism

Template Method
→ Inheritance + polymorphism

Adapter
→ Composition + abstraction

Observer
→ Abstraction + loose coupling

Proxy
→ Composition + polymorphism
```

---

# 38. Common Interview Traps

## Q84. Does Java support multiple inheritance?

Not multiple inheritance of classes.

Yes, multiple interfaces can be implemented.

---

## Q85. Are static methods polymorphic?

No. They are hidden.

---

## Q86. Are fields polymorphic?

No. Field access is based on the reference type.

---

## Q87. Are constructors inherited?

No.

---

## Q88. Are constructors overridden?

No.

---

## Q89. Can private methods be overridden?

No.

---

## Q90. Can final methods be overridden?

No.

---

## Q91. Can final classes be extended?

No.

---

## Q92. Can an abstract class have constructors?

Yes.

---

## Q93. Can an abstract class have concrete methods?

Yes.

---

## Q94. Can an interface have concrete methods?

Yes, through `default`, `static`, and private methods.

---

# 39. Senior-Level Interview Questions

## Q95. Why is composition often preferred over inheritance? ⭐⭐⭐

Inheritance creates a strong compile-time relationship.

Composition allows behavior to be changed by replacing the contained dependency.

Example:

```java
class OrderService {

    private PaymentStrategy paymentStrategy;

    OrderService(PaymentStrategy strategy) {
        this.paymentStrategy = strategy;
    }
}
```

We can inject:

```text
UPI
Credit Card
PayPal
```

without creating a deep inheritance hierarchy.

---

## Q96. What is the difference between abstraction and encapsulation? ⭐⭐⭐

### Abstraction

Focuses on:

> What should the object expose?

Example:

```java
interface Payment {
    void pay();
}
```

### Encapsulation

Focuses on:

> How do we protect/control internal state?

Example:

```java
private double balance;
```

Memory trick:

```text
Abstraction  → hide implementation complexity
Encapsulation → hide/protect internal state
```

---

## Q97. What is the difference between inheritance and polymorphism?

Inheritance is a mechanism for establishing a parent-child relationship.

Polymorphism is the ability to use a common type/interface to work with different implementations.

Example:

```java
Animal animal = new Dog();
```

Inheritance:

```java
Dog extends Animal
```

Polymorphism:

```java
animal.sound();
```

invokes the appropriate implementation at runtime.

---

## Q98. What is the difference between abstraction and inheritance?

Abstraction defines what behavior should be exposed.

Inheritance creates a parent-child relationship and allows reuse/extension.

An abstract class combines both ideas, but abstraction itself does not require inheritance.

---

## Q99. Why should we program to an interface?

Instead of:

```java
PaymentService service =
    new UpiPayment();
```

prefer depending on:

```java
PaymentService service;
```

Benefits:
- loose coupling
- easier testing
- easier replacement of implementations
- better extensibility
- supports dependency injection

---

## Q100. How would you explain OOP in a real backend application? ⭐⭐⭐

Example:

```text
Controller
    ↓
Service
    ↓
Repository
    ↓
Database
```

Each layer can be modeled using objects and abstractions.

Example:

```java
interface PaymentService {
    void pay(double amount);
}

class UpiPaymentService
        implements PaymentService {

    public void pay(double amount) {
        // implementation
    }
}
```

The business layer depends on the abstraction rather than a concrete implementation.

This gives:
- abstraction
- encapsulation
- polymorphism
- low coupling
- testability

---

# 40. Final Rapid-Fire Revision

## Q101. Four pillars of OOP?

Encapsulation, Inheritance, Polymorphism, Abstraction.

## Q102. IS-A relationship?

Inheritance.

## Q103. HAS-A relationship?

Composition/Aggregation.

## Q104. Compile-time polymorphism?

Method overloading.

## Q105. Runtime polymorphism?

Method overriding/dynamic dispatch.

## Q106. Can return type alone overload a method?

No.

## Q107. Can static methods be overridden?

No.

## Q108. Can private methods be overridden?

No.

## Q109. Can final methods be overridden?

No.

## Q110. Can abstract classes have constructors?

Yes.

## Q111. Can abstract classes have concrete methods?

Yes.

## Q112. Can interfaces have implemented methods?

Yes, using default/static/private methods.

## Q113. Can Java classes have multiple parents?

No.

## Q114. Can an interface extend multiple interfaces?

Yes.

## Q115. What does `this` mean?

Current object.

## Q116. What does `super` mean?

Immediate parent class.

## Q117. Root class of Java objects?

`Object`.

## Q118. Why override equals and hashCode together?

Equal objects must have equal hash codes.

## Q119. Composition vs inheritance?

Prefer composition when it gives more flexibility and the relationship is not a true IS-A relationship.

## Q120. What does `@Override` provide?

It tells the compiler that a method is intended to override a parent/interface method and helps catch signature mistakes.

---

# 41. Must-Know OOP Interview Questions for Your Experience Level

For a 5–6 year Java backend interview, prioritize these:

### ⭐⭐⭐ Core

1. Four pillars of OOP
2. Encapsulation vs abstraction
3. Abstract class vs interface
4. Inheritance
5. Composition vs inheritance
6. Overloading vs overriding
7. Runtime polymorphism
8. Dynamic method dispatch
9. Upcasting/downcasting
10. `this` vs `super`
11. Constructor chaining
12. Static method hiding
13. Field hiding
14. `equals()` and `hashCode()`
15. Immutability
16. SOLID principles
17. Dependency Injection
18. Coupling vs cohesion
19. Association/aggregation/composition
20. OOP design scenarios

### ⭐⭐ Tricky

- Static methods and polymorphism
- Fields and polymorphism
- Private methods
- Final methods
- Covariant returns
- Interface default-method conflicts
- Constructor execution order
- Calling overridden methods from constructors
- Overloading with `null`
- Downcasting and `ClassCastException`

---

# 42. One-Page OOP Cheat Sheet

```text
                    OOP
                     |
       +-------------+-------------+
       |             |             |
 Encapsulation   Inheritance   Polymorphism
       |             |             |
  Data hiding      IS-A       Overloading
  private fields               Overriding
       |
       +---------------- Abstraction
                              |
                     Interface / Abstract Class
```

### Relationships

```text
IS-A
Dog → Animal
     ↓
Inheritance

HAS-A
Car → Engine
     ↓
Composition
```

### Polymorphism

```text
Compile time
    ↓
Overloading

Runtime
    ↓
Overriding
    ↓
Dynamic Method Dispatch
```

### Important Java rules

```text
static method     → hidden, not overridden
field             → hidden, not overridden
private method    → cannot be overridden
final method      → cannot be overridden
final class       → cannot be extended
constructor       → not inherited / not overridden
abstract class    → can have constructor + concrete methods
interface         → can have default/static/private methods
```

### Interview memory

```text
Abstraction
→ WHAT

Encapsulation
→ HOW internal state is protected

Inheritance
→ IS-A

Composition
→ HAS-A

Overloading
→ compile time

Overriding
→ runtime

Interface
→ contract

Abstract class
→ shared base abstraction
```

---

# Final Practice Strategy

Before an interview, don't just memorize definitions.

For every OOP concept, be able to answer:

```text
1. What is it?
2. Why do we need it?
3. Give a real-world example.
4. Write a small Java example.
5. Explain what happens internally.
6. Compare it with a similar concept.
7. Solve a tricky output question.
8. Explain where you used it in a backend project.
```

For a senior Java backend interview, especially practice these code/output questions:

```text
Parent p = new Child();
p.method();

Parent p = new Child();
p.field;

Parent p = new Child();
p.staticMethod();

new Child();              // constructor order

overload(null);           // ambiguity/specificity

interface A + interface B // default method conflict

equals() + hashCode()     // HashMap/HashSet behavior

private/final methods     // overriding rules
```

If you can explain these confidently, your OOP fundamentals will be strong for Java backend interviews.
