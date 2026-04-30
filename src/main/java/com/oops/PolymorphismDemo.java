package com.oops;

/*
================================= POLYMORPHISM (INTERVIEW REVISION) =================================

Definition:
------------
Polymorphism means "many forms".

👉 Same method name → different behavior depending on context

Types:
-------
1. Compile-Time Polymorphism (Method Overloading)
2. Runtime Polymorphism (Method Overriding)

Why Polymorphism?
------------------
- Improves flexibility
- Enables code reusability
- Supports dynamic behavior
- Reduces code duplication

Real-life Example:
------------------
Person:
- same action: speak()
- different behavior: language changes

==============================================================================================
*/


/*
 * ========================= 1. COMPILE-TIME POLYMORPHISM =========================
 * Method Overloading
 */
class Calculator {

    /*
     * Same method name → different parameters
     * Decision made at compile time
     */

    int add(int a, int b) {
        return a + b;
    }

    double add(double a, double b) {
        return a + b;
    }

    int add(int a, int b, int c) {
        return a + b + c;
    }
}



/*
 * ========================= 2. RUNTIME POLYMORPHISM =========================
 * Method Overriding
 */

class Animal {

    /*
     * Method to be overridden
     */
    void sound() {
        System.out.println("Animal makes sound");
    }
}


class Dog extends Animal {

    @Override
    void sound() {
        System.out.println("Dog barks");
    }
}


class Cat extends Animal {

    @Override
    void sound() {
        System.out.println("Cat meows");
    }
}



public class PolymorphismDemo {

    public static void main(String[] args) {

        /*
         * COMPILE-TIME POLYMORPHISM
         */
        Calculator calc = new Calculator();

        System.out.println(calc.add(2, 3));        // int version
        System.out.println(calc.add(2.5, 3.5));    // double version
        System.out.println(calc.add(1, 2, 3));     // 3 params version



        /*
         * RUNTIME POLYMORPHISM (Dynamic Method Dispatch)
         */

        Animal a1 = new Dog(); // parent reference → child object
        Animal a2 = new Cat();

        a1.sound(); // Dog implementation
        a2.sound(); // Cat implementation
    }
}



/*
================================= KEY POINTS =================================

Method Overloading:
--------------------
- Same method name
- Different parameters (type/number/order)
- Happens at compile time
- No inheritance required

Method Overriding:
-------------------
- Same method signature
- Requires inheritance
- Happens at runtime
- Uses dynamic method dispatch

================================================================================
*/


/*
================================= RULES (IMPORTANT) =================================

Overloading Rules:
-------------------
- Method name same
- Parameters must differ
- Return type alone cannot differentiate

Overriding Rules:
-------------------
- Same method signature
- Cannot reduce visibility
- Can change return type (covariant)
- Cannot override static/final/private methods

================================================================================
*/


/*
================================= TRADEOFFS =================================

Advantages:
------------
+ Code reusability
+ Flexibility
+ Cleaner code
+ Supports dynamic behavior

Disadvantages:
---------------
- Slight performance overhead (runtime dispatch)
- Can increase complexity
- Harder to debug sometimes

================================================================================
*/


/*
================================= INTERVIEW QUESTIONS =================================

Q1: What is polymorphism?
Ans:
Same method behaves differently based on object.

Q2: Difference between overloading and overriding?
Ans:
Overloading → compile-time
Overriding → runtime

Q3: Can we overload static methods?
Ans: Yes

Q4: Can we override static methods?
Ans: No (method hiding happens)

Q5: Why runtime polymorphism important?
Ans:
Enables dynamic behavior → key for OOP design

================================================================================
*/


/*
================================= ONE-LINER =================================

Polymorphism = "One method, multiple behaviors"

================================================================================
*/
