# ✦ DESIGN PATTERNS — HANDWRITTEN-STYLE INTERVIEW NOTES

> Based on the Java examples in the uploaded ZIP.  
> Organized as **Creational → Structural → Behavioral**, with UML diagrams, execution flow, code snippets, memory hooks, and interview points.

---

# 1. MASTER MAP

| Category | Patterns |
|---|---|
| **Creational** | Factory, Abstract Factory, Builder, Singleton |
| **Structural** | Adapter, Decorator, Proxy |
| **Behavioral** | Observer, Strategy, Template Method, Null Object |
| **Supporting OOP Technique** | Immutable Class |

---

# 2. MASTER CHEAT SHEET

| Pattern | Easy Meaning |
|---|---|
| **Factory** | One product type → factory decides concrete object |
| **Abstract Factory** | Related product family → one factory creates the family |
| **Builder** | Construct one complex object step-by-step |
| **Singleton** | Only one shared instance |
| **Adapter** | Translate an incompatible interface |
| **Decorator** | Wrap an object to add responsibilities |
| **Proxy** | Stand-in that controls access/lifecycle |
| **Observer** | One subject notifies many subscribers |
| **Strategy** | Swap interchangeable algorithms |
| **Template Method** | Parent fixes algorithm order; child customizes steps |
| **Null Object** | Replace `null` with safe default behavior |
| **Immutable Class** | State cannot change after construction |

---

# CREATIONAL PATTERNS

# 3. Factory Pattern

**Source:** `FactoryPatternImpl.java`

## Intent

Centralize object creation so the client asks a factory for a product instead of directly choosing concrete classes.

### Memory Hook

> **Factory = one product type → factory decides the concrete product.**

### Example Structure

```text
Client
  |
  v
ShoesFactory
  |
  +----> AdidasShoes
  |
  +----> RevokeShoes

Both implement:

        Shoes
          |
       wear()
```

## UML

```text
                  +----------------------+
                  |       Shoes          |
                  +----------------------+
                  | + wear()             |
                  +----------------------+
                       ^            ^
                       | implements |
              +--------+            +--------+
              |                              |
+--------------------------+      +--------------------------+
|     AdidasShoes          |      |      RevokeShoes         |
+--------------------------+      +--------------------------+
| + wear()                 |      | + wear()                 |
+--------------------------+      +--------------------------+

                    ^
                    |
                 creates
                    |
          +----------------------+
          |    ShoesFactory      |
          +----------------------+
          | + createShoes()      |
          +----------------------+
```

## Flow

```text
Client
  ↓
ShoesFactory.createShoes("adidas")
  ↓
AdidasShoes
  ↓
wear()
```

## Example Code

```java
ShoesFactory factory = new ShoesFactory();

Shoes shoes1 = factory.createShoes("adidas");
shoes1.wear();

Shoes shoes2 = factory.createShoes("revoke");
shoes2.wear();
```

## Why?

Without Factory:

```java
Shoes shoes;

if (brand.equals("adidas")) {
    shoes = new AdidasShoes();
} else {
    shoes = new RevokeShoes();
}
```

The creation logic is exposed to the client.

With Factory:

```java
Shoes shoes = factory.createShoes("adidas");
```

The client does not need to know which concrete class is created.

### Interview Point

> Use Factory when object creation has branching logic and you want to hide concrete-class construction from the client.

---

# 4. Abstract Factory Pattern

**Source:** `AbstractFactoryImpl.java`

## Intent

Create a **family of related products** without coupling the client to their concrete classes.

### Memory Hook

> **Abstract Factory = factory of related products.**

Example:

```text
                    ClothingFactory
                         |
             +-----------+-----------+
             |                       |
       AdidasFactory           RevokeFactory
             |                       |
       +-----+-----+           +-----+-----+
       |     |     |           |     |     |
     Shoes TShirt Lower       Shoes TShirt Lower
```

## UML

```text
                 +---------------------------+
                 |     ClothingFactory       |
                 +---------------------------+
                 | + createShoes()           |
                 | + createTShirt()          |
                 | + createLower()           |
                 +---------------------------+
                       ^              ^
                       | implements   |
              +--------+              +--------+
              |                              |
+-------------------------+       +-------------------------+
|    AdidasFactory        |       |     RevokeFactory       |
+-------------------------+       +-------------------------+
| + create...()           |       | + create...()            |
+-------------------------+       +-------------------------+
              |                              |
              v                              v
     Adidas Product Family          Revoke Product Family
     Shoes / TShirt / Lower         Shoes / TShirt / Lower
```

## Example Code

```java
ClothingFactory adidasFactory = new AdidasFactory();

Showroom adidasShowroom =
        new Showroom(adidasFactory);

adidasShowroom.showProducts();


ClothingFactory revokeFactory = new RevokeFactory();

Showroom revokeShowroom =
        new Showroom(revokeFactory);

revokeShowroom.showProducts();
```

### Key Difference

```text
Factory
   ↓
Creates ONE type of product

Abstract Factory
   ↓
Creates a FAMILY of related products
```

### Interview Point

> Factory creates one kind of product; Abstract Factory creates a coordinated family of products.

---

# 5. Builder Pattern

**Source:** `BuilderPatternImpl.java`

## Intent

Build a complex object step-by-step, especially when many fields are optional.

### Memory Hook

> **Builder = readable step-by-step construction.**

## UML

```text
+----------------------+          +-------------------------+
|       Burger         | <--------|     Burger.Builder      |
+----------------------+   build  +-------------------------+
| - bun                |          | + bun()                 |
| - cheese             |          | + cheese()              |
| - patty              |          | + patty()               |
| - sauce              |          | + sauce()               |
| - onion              |          | + onion()               |
+----------------------+          | + build()               |
                                  +-------------------------+
```

## Flow

```text
new Builder()
    ↓
bun(...)
    ↓
cheese(...)
    ↓
patty(...)
    ↓
sauce(...)
    ↓
onion(...)
    ↓
build()
    ↓
Burger
```

## Example Code

```java
Burger burger = new Burger.Builder()
        .bun("Sesame")
        .cheese("Cheddar")
        .patty("Chicken")
        .sauce("Mayo")
        .onion(true)
        .build();
```

### Why Builder?

A large constructor can become difficult to understand:

```java
new Burger(
    "Sesame",
    "Cheddar",
    "Chicken",
    "Mayo",
    true
);
```

Builder is much more readable:

```java
new Burger.Builder()
    .bun("Sesame")
    .cheese("Cheddar")
    .patty("Chicken")
    .build();
```

### Interview Point

> Builder is useful when an object has many optional fields or a complex construction process.

---

# 6. Singleton Pattern

**Source:** `CreateSingletonObj.java`

## Intent

Ensure a class has one shared instance and provide a global access point.

### Memory Hook

> **Singleton = one instance.**

The source contains multiple Singleton implementations:

- Eager initialization
- Lazy initialization
- Synchronized
- Double-Checked Locking
- Bill Pugh

## UML

```text
+--------------------------------+
|          Singleton             |
+--------------------------------+
| - static instance              |
| - private constructor          |
| + getInstance()                |
+--------------------------------+
              ^
              |
        returns same
          instance
              |
+-----------------------------+
|           Client            |
+-----------------------------+
| + getInstance()             |
+-----------------------------+
```

## Bill Pugh Example

```java
private BillPughSingleton() {}

private static class SingletonHelper {

    private static final BillPughSingleton INSTANCE =
            new BillPughSingleton();
}

public static BillPughSingleton getInstance() {

    return SingletonHelper.INSTANCE;
}
```

## Flow

```text
Client
  ↓
getInstance()
  ↓
SingletonHelper.INSTANCE
  ↓
Same Singleton instance
```

### Interview Point

> Singleton guarantees a single shared instance.

The supplied source recommends the **Bill Pugh** approach for lazy initialization and thread safety through class loading without explicit synchronization in `getInstance()`.

---

# STRUCTURAL PATTERNS

# 7. Adapter Pattern

**Source:** `AdapterPatternImpl.java`

## Intent

Make an existing class with an incompatible API work with the interface expected by the client.

### Memory Hook

> **Adapter = translator between incompatible interfaces.**

## Example

The client expects:

```java
PaymentProcessor
```

with:

```java
pay()
```

But RazorPay exposes:

```java
makePayment()
```

The Adapter translates between them.

## UML

```text
+-----------------------+
|   PaymentProcessor    |
+-----------------------+
| + pay(amount)         |
+-----------------------+
           ^
           | implements
           |
+--------------------------+
|    RazorPayAdapter       |
+--------------------------+
| - RazorPayAPI            |
| + pay(amount)            |
+--------------------------+
           |
           | delegates
           v
+--------------------------+
|      RazorPayAPI        |
+--------------------------+
| + makePayment(amount)   |
+--------------------------+
```

## Flow

```text
Client
  ↓
PaymentProcessor.pay()
  ↓
RazorPayAdapter.pay()
  ↓
RazorPayAPI.makePayment()
```

## Example Code

```java
PaymentProcessor paymentProcessor =
        new RazorPayAdapter(new RazorPayAPI());

paymentProcessor.pay(100);
```

### Interview Point

> Adapter changes/bridges the interface expected by the client so an existing incompatible class can be reused.

### Remember

```text
Adapter = Compatibility
```

---

# 8. Decorator Pattern

**Source:** `DecoratorPatternImpl.java`

## Intent

Add responsibilities dynamically by wrapping an object without modifying the original class.

### Memory Hook

> **Decorator = wrap it to add behavior.**

Classic example:

```text
SimpleCoffee
     ↓
MilkDecorator
     ↓
ChocolateDecorator
     ↓
Final Coffee
```

## UML

```text
                 +----------------------+
                 |       Coffee         |
                 +----------------------+
                 | + getDescription()   |
                 | + getCost()          |
                 +----------------------+
                         ^
                         | implements
                         |
              +-----------------------+
              |  CoffeeDecorator      |
              +-----------------------+
              | - Coffee coffee       |
              | + delegates           |
              +-----------------------+
                         ^
                  +------+------+
                  |             |
                  |             |
        +----------------+ +--------------------+
        | MilkDecorator  | | ChocolateDecorator |
        +----------------+ +--------------------+
```

## Example Code

```java
Coffee coffee = new SimpleCoffee();

coffee = new MilkDecorator(coffee);
// +20

coffee = new ChocolateDecorator(coffee);
// +50
```

## Execution

```text
SimpleCoffee
   ↓
MilkDecorator
   ↓
ChocolateDecorator
```

Each decorator delegates to the wrapped object and adds its own behavior.

### Interview Point

> Decorator allows behavior to be added dynamically without modifying the original class.

### Decorator vs Adapter

```text
Adapter
    → makes interfaces compatible

Decorator
    → adds behavior
```

---

# 9. Proxy Pattern

**Source:** `ProxyPatternImpl.java`

## Intent

Provide a stand-in object that controls access to a real object.

### Memory Hook

> **Proxy = same interface + controlled access.**

The supplied example uses **lazy loading**.

## UML

```text
+--------------------+
|       IImage       |
+--------------------+
| + display()        |
+--------------------+
        ^
        | implements
        |
+-----------------------+
|   RealImageProxy      |
+-----------------------+
| - RealImage           |
| + display()           |
+-----------------------+
        |
        | delegates
        v
+-----------------------+
|      RealImage        |
+-----------------------+
| - fileName            |
| + display()           |
+-----------------------+
```

## Example Code

```java
IImage image =
        new RealImageProxy("car.jpg");

image.display(); // loads + displays

image.display(); // displays only
```

## What happens?

First call:

```text
image.display()
      ↓
Proxy
      ↓
RealImage created
      ↓
Load image
      ↓
Display image
```

Second call:

```text
image.display()
      ↓
Proxy
      ↓
Existing RealImage
      ↓
Display
```

The image is loaded only when actually needed.

### Common Proxy Uses

```text
Security
Lazy Loading
Caching
Logging
Remote Access
```

### Proxy vs Decorator

```text
Proxy
  → controls access/lifecycle

Decorator
  → adds responsibilities
```

---

# BEHAVIORAL PATTERNS

# 10. Observer Pattern

**Source:** `ObserverDesignPatternImpl.java`

## Intent

Define a one-to-many dependency so observers are notified automatically when the subject changes.

### Memory Hook

> **Observer = subscribe → subject changes → notify all.**

Example:

```text
YouTube Channel
      |
      +---- User 1
      |
      +---- User 2
      |
      +---- User 3
```

When the channel uploads a video, all subscribers are notified.

## UML

```text
+--------------------------+
|     YouTubeChannel       |
+--------------------------+
| - subscribers            |
| + subscribe()            |
| + unsubscribe()          |
| + uploadVideo()          |
+--------------------------+
            |
            | notifies
            v
+--------------------------+
|       Subscriber         |
+--------------------------+
| + update(video)          |
+--------------------------+
            ^
            | implements
            |
+--------------------------+
|          User            |
+--------------------------+
| - name                   |
| + update()               |
+--------------------------+
```

## Example Code

```java
channel.subscribe(user1);
channel.subscribe(user2);
channel.subscribe(user3);

channel.uploadVideo("Java Design Patterns");

channel.unsubscribe(user2);

channel.uploadVideo("Spring Boot Microservices");
```

## Flow

```text
User subscribes
      ↓
Channel stores subscriber
      ↓
New video uploaded
      ↓
notifySubscribers()
      ↓
User.update()
```

### Interview Point

> Observer is useful when one object's state change should automatically notify many dependent objects.

---

# 11. Strategy Pattern

**Source:** `StrategyDesignPatternImpl.java`

## Intent

Encapsulate interchangeable algorithms/behaviors and choose one at runtime.

### Memory Hook

> **Strategy = same goal, different algorithm.**

Payment example:

```text
PaymentStrategy
       |
       +---- CreditCardPayment
       |
       +---- UPIPayment
       |
       +---- PayPalPayment
```

## UML

```text
                 +-----------------------+
                 |   PaymentStrategy     |
                 +-----------------------+
                 | + pay(amount)         |
                 +-----------------------+
                    ^       ^       ^
                    |       |       |
             +------+  +----+----+  +------+
             |         |         |         |
      CreditCard     UPI      PayPal
       Payment      Payment   Payment

                 +-----------------------+
                 |    PaymentContext     |
                 +-----------------------+
                 | - strategy            |
                 | + makePayment()       |
                 +-----------------------+
                          |
                          | uses
                          v
                  PaymentStrategy
```

## Example Code

```java
PaymentContext payment =
        new PaymentContext(
                new CreditCardPayment()
        );

payment.makePayment(1000);


payment = new PaymentContext(
        new UPIPayment()
);

payment.makePayment(2000);
```

## Why?

Instead of:

```java
if (paymentType.equals("CARD")) {
    // card logic
}
else if (paymentType.equals("UPI")) {
    // UPI logic
}
else if (paymentType.equals("PAYPAL")) {
    // PayPal logic
}
```

We encapsulate each algorithm in its own class.

### Interview Point

> Strategy avoids large if/else or switch blocks when behavior/algorithm varies.

### Strategy vs Template Method

```text
Strategy
    → choose/swap algorithm

Template Method
    → parent fixes algorithm skeleton/order
```

---

# 12. Template Method Pattern

**Source:** `TemplatePatternImpl.java`

## Intent

Define the skeleton of an algorithm in a base class while subclasses customize selected steps.

### Memory Hook

> **Template = fixed steps/order; subclasses fill the variable steps.**

Your example:

```text
FoodOrder
    |
    +---- PizzaOrder
    |
    +---- BiryaniOrder
```

## UML

```text
+--------------------------------+
|          FoodOrder             |
+--------------------------------+
| + final processOrder()         |
| + abstract prepareFood()      |
| + takeOrder()                  |
| + packFood()                   |
| + deliverFood()                |
+--------------------------------+
             ^             ^
             | extends     |
      +------+             +------+
      |                           |
+-------------+             +---------------+
| PizzaOrder  |             | BiryaniOrder  |
+-------------+             +---------------+
| prepareFood |             | prepareFood   |
+-------------+             +---------------+
```

## Template

```java
public final void processOrder() {

    takeOrder();

    prepareFood();

    packFood();

    deliverFood();
}
```

The order is fixed:

```text
Take Order
    ↓
Prepare Food
    ↓
Pack Food
    ↓
Deliver Food
```

But:

```java
protected abstract void prepareFood();
```

is customized.

## Example

```java
FoodOrder pizza = new PizzaOrder();
pizza.processOrder();
```

Output:

```text
Taking Order
Baking Pizza
Packing Food
Delivering Food
```

For Biryani:

```text
Taking Order
Cooking Biryani
Packing Food
Delivering Food
```

If you make packing and delivery abstract as well, subclasses can provide:

```text
Packing Pizza
Delivering Pizza
```

and:

```text
Packing Biryani
Delivering Biryani
```

### Why `final`?

```java
public final void processOrder()
```

Prevents subclasses from changing the algorithm order.

A `PizzaOrder` should not be able to change:

```text
Take → Prepare → Pack → Deliver
```

into:

```text
Take → Pack → Prepare → Deliver
```

### Interview Point

> Template Method fixes the algorithm skeleton in the parent while subclasses customize selected steps.

---

# 13. Null Object Pattern

**Source:** `NullObjectPatternImpl.java`

## Intent

Return a safe object with default behavior instead of `null`, reducing repeated null checks.

### Memory Hook

> **Null Object = don't return null; return an object that safely does nothing/default work.**

Example:

```text
                  Discount
                     |
             +-------+-------+
             |               |
             v               v
    FestivalDiscount      NoDiscount
       20% discount          0
```

## UML

```text
+------------------------------+
|          Discount            |
+------------------------------+
| + calculateDiscount(amount) |
+------------------------------+
          ^             ^
          |             |
+-------------------+  +-------------------+
| FestivalDiscount  |  |    NoDiscount     |
+-------------------+  +-------------------+
| + calculate...()  |  | + calculate...()  |
| returns 20%       |  | returns 0         |
+-------------------+  +-------------------+

                  ^
                  |
                Client
```

## Example Code

```java
Discount discount;

if (festivalSale) {

    discount = new FestivalDiscount();

} else {

    discount = new NoDiscount();
}

double result =
        discount.calculateDiscount(1000);
```

When there is no festival:

```text
discount
   ↓
NoDiscount
   ↓
calculateDiscount()
   ↓
0
```

No need for:

```java
if (discount != null)
```

### Interview Point

> Instead of returning `null`, return a safe default implementation.

Example:

```java
class NoDiscount implements Discount {

    @Override
    public double calculateDiscount(double amount) {
        return 0;
    }
}
```

---

# SUPPORTING OOP TECHNIQUE

# 14. Immutable Class

**Source:** `ImmutableClass.java`

> **Important:** Immutable Class is not one of the classic GoF 23 Design Patterns. It is a core Java/OOP design technique.

## Intent

Create objects whose state cannot change after construction.

### Memory Hook

> **Immutable = set once, never change.**

## UML

```text
+--------------------------------+
|        ImmutableObj            |
+--------------------------------+
| - final name                   |
| - final age                    |
| - final address                |
| - final skillSet               |
+--------------------------------+
| + getName()                    |
| + getAge()                     |
| + getAddress()                 |
| + getSkillSet()                |
+--------------------------------+
               |
               | defensive copy
               v
       +------------------+
       | List<String>     |
       | skillSet         |
       +------------------+
```

## Example

```java
final class ImmutableObj {

    private final String name;

    private final int age;

    private final String address;

    private final List<String> skillSet;

    public ImmutableObj(
            String name,
            int age,
            String address,
            List<String> skillSet) {

        this.name = name;
        this.age = age;
        this.address = address;

        this.skillSet =
                new ArrayList<>(skillSet);
    }

    public List<String> getSkillSet() {

        return new ArrayList<>(skillSet);
    }
}
```

## Why defensive copy?

Suppose the caller does:

```java
List<String> skills =
        new ArrayList<>();

skills.add("Java");

ImmutableObj obj =
        new ImmutableObj(
                "Vivek",
                25,
                "Mumbai",
                skills
        );
```

If the object directly stores:

```java
this.skillSet = skillSet;
```

then the caller could later do:

```java
skills.add("Kafka");
```

and indirectly change the object's internal state.

Instead:

```java
this.skillSet =
        new ArrayList<>(skillSet);
```

creates a separate copy.

The getter also returns a copy:

```java
return new ArrayList<>(skillSet);
```

### Rules to remember

```text
1. Make class final
2. Make fields private
3. Make fields final
4. Initialize through constructor
5. Don't provide setters
6. Defensive copy mutable objects
7. Return defensive copies from getters
```

---

# 15. HIGH-VALUE INTERVIEW COMPARISONS

## Factory vs Abstract Factory

```text
Factory
    ↓
One product type

Abstract Factory
    ↓
Family of related products
```

Example:

```text
Factory:
    ShoesFactory → AdidasShoes

Abstract Factory:
    AdidasFactory
       → AdidasShoes
       → AdidasTShirt
       → AdidasLower
```

---

## Adapter vs Decorator

```text
Adapter
    → Compatibility

Decorator
    → Additional behavior
```

Adapter:

```text
Client → Adapter → Existing API
```

Decorator:

```text
Client → Decorator → Decorator → Real Object
```

---

## Decorator vs Proxy

They can look very similar because both use wrapping.

```text
Decorator
    → adds responsibility/feature

Proxy
    → controls access/lifecycle
```

Examples:

```text
Decorator:
    Coffee → Milk → Chocolate

Proxy:
    ImageProxy → RealImage
```

---

## Strategy vs Template Method

```text
Strategy
    → Choose/swap behavior

Template Method
    → Fix overall algorithm order
```

Strategy:

```text
PaymentContext
      ↓
Credit Card / UPI / PayPal
```

Template:

```text
FoodOrder
    ↓
Take → Prepare → Pack → Deliver
```

The parent controls the sequence.

---

## Observer vs Strategy

```text
Observer
    → communication/notification

Strategy
    → interchangeable behavior
```

Observer:

```text
YouTubeChannel
    ↓
notify
    ↓
Users
```

Strategy:

```text
PaymentContext
    ↓
PaymentStrategy
    ↓
UPI / Card / PayPal
```

---

# 16. FINAL REVISION — WHICH PATTERN?

| If the interviewer says... | Think... |
|---|---|
| "Hide object creation" | **Factory** |
| "Create related objects together" | **Abstract Factory** |
| "Many optional constructor parameters" | **Builder** |
| "Only one instance" | **Singleton** |
| "Existing API doesn't match" | **Adapter** |
| "Add features dynamically" | **Decorator** |
| "Lazy loading / security / caching" | **Proxy** |
| "Notify many objects" | **Observer** |
| "Swap algorithms" | **Strategy** |
| "Fixed process with customizable steps" | **Template Method** |
| "Avoid null checks" | **Null Object** |
| "Object state must never change" | **Immutable Class** |

---

# 17. ONE-LINE INTERVIEW ANSWERS

### Factory

> I hide concrete object creation behind a factory.

### Abstract Factory

> I create a consistent family of related objects.

### Builder

> I construct a complex object step-by-step.

### Singleton

> I guarantee a single shared instance.

### Adapter

> I translate one interface into the interface the client expects.

### Decorator

> I add behavior by wrapping an existing object.

### Proxy

> I control access to a real object through the same interface.

### Observer

> I automatically notify subscribers when the subject changes.

### Strategy

> I encapsulate interchangeable algorithms and select one at runtime.

### Template Method

> The parent fixes the algorithm skeleton while subclasses customize selected steps.

### Null Object

> I replace `null` with a safe default implementation.

### Immutable Class

> I create an object whose state cannot change after construction.

---

# 18. QUICK MEMORY MAP

```text
                    DESIGN PATTERNS
                          |
        +-----------------+-----------------+
        |                 |                 |
   CREATIONAL         STRUCTURAL       BEHAVIORAL
        |                 |                 |
        |                 |                 |
     Factory           Adapter          Observer
     Abstract          Decorator        Strategy
     Factory            Proxy           Template
     Builder                            Method
     Singleton                          Null Object
```

## Final mental model

```text
CREATIONAL
    = How do I CREATE objects?

STRUCTURAL
    = How do I CONNECT / WRAP objects?

BEHAVIORAL
    = How do objects COMMUNICATE / BEHAVE?
```

> **Best interview approach:** first identify the problem, then identify the pattern. Don't choose a pattern only because the class structure looks similar.
