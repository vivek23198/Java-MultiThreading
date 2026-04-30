package com.oops;

/*
================================= ABSTRACTION (INTERVIEW REVISION) =================================

Definition:
------------
Abstraction is the process of hiding implementation details and showing only essential features.

👉 Focus:
"What to do" NOT "How it works"

How to achieve in Java:
-----------------------
1. Abstract Class (0–100% abstraction)
2. Interface (100% abstraction)

Why Abstraction?
-----------------
- Reduces complexity
- Improves code readability
- Hides unnecessary implementation details
- Promotes loose coupling

Real-life Example:
------------------
Car:
- User knows → start(), stop(), accelerate()
- User does NOT know → engine internal working

==============================================================================================
*/


/*
 * ABSTRACT CLASS EXAMPLE
 */
abstract class Vehicle {

    /*
     * Abstract Method
     * No body → must be implemented by child class
     */
    abstract void start();

    /*
     * Concrete Method
     * Has implementation
     */
    void fuelType() {
        System.out.println("Uses fuel");
    }
}


/*
 * CHILD CLASS IMPLEMENTING ABSTRACT METHOD
 */
class Car extends Vehicle {

    @Override
    void start() {
        System.out.println("Car starts with key ignition");
    }
}


/*
 * INTERFACE EXAMPLE (100% abstraction)
 */
interface Payment {

    /*
     * By default:
     * public abstract
     */
    void pay();
}


/*
 * IMPLEMENTATION OF INTERFACE
 */
class CreditCardPayment implements Payment {

    @Override
    public void pay() {
        System.out.println("Payment via Credit Card");
    }
}



public class AbstractionDemo {

    public static void main(String[] args) {

        /*
         * Using Abstract Class
         */
        Vehicle v = new Car(); // Runtime polymorphism
        v.start();             // Calls Car implementation
        v.fuelType();


        /*
         * Using Interface
         */
        Payment p = new CreditCardPayment();
        p.pay();
    }
}


/*
================================= ABSTRACT CLASS vs INTERFACE =================================

Abstract Class:
---------------
+ Can have abstract + concrete methods
+ Can have constructors
+ Can have instance variables
+ Supports partial abstraction

Interface:
-----------
+ Only abstract methods (Java 8+: default/static allowed)
+ No constructors
+ Variables are public static final
+ Supports full abstraction

==============================================================================================
*/


/*
================================= TRADEOFFS =================================

Advantages:
------------
+ Hides implementation complexity
+ Improves maintainability
+ Encourages loose coupling
+ Easier to extend and modify

Disadvantages:
---------------
- Adds extra layer of abstraction
- Can increase complexity if overused
- Requires more planning/design

================================================================================
*/


/*
================================= INTERVIEW QUESTIONS =================================

Q1: Difference between abstraction and encapsulation?
Ans:
- Abstraction → Hides implementation
- Encapsulation → Hides data

Q2: Can abstract class have constructor?
Ans: Yes

Q3: Can we create object of abstract class?
Ans: No

Q4: Interface vs Abstract class?
Ans:
Interface → multiple inheritance
Abstract class → partial implementation

================================================================================
*/


/*
================================= ONE-LINER =================================

Abstraction = "Hide implementation, show only behavior"

================================================================================
*/
