package com.oops;

/*
================================= INHERITANCE (INTERVIEW REVISION) =================================

Definition:
------------
Inheritance is a mechanism where one class (child/subclass) acquires
properties and behavior (fields + methods) of another class (parent/superclass).

👉 Goal:
Code reuse + establish "IS-A" relationship

Example:
---------
Dog1 IS-A Animal1

==============================================================================================
*/

/*
 * ========================= BASE / PARENT CLASS =========================
 */
class Animal1 {

    String name;

    /*
     * Constructor of parent
     */
    Animal1(String name) {
        this.name = name;
    }

    /*
     * Common behavior
     */
    void eat() {
        System.out.println(name + " is eating");
    }

    void sound() {
        System.out.println("Animal1 makes sound");
    }
}



/*
 * ========================= CHILD CLASS =========================
 */
class Dog1 extends Animal1 {

    /*
     * Constructor chaining using super
     */
    Dog1(String name) {
        super(name);
    }

    /*
     * Method specific to Dog1
     */
    void bark() {
        System.out.println(name + " is barking");
    }

    /*
     * Method overriding
     */
    @Override
    void sound() {
        System.out.println(name + " barks");
    }
}



/*
 * ========================= MULTILEVEL INHERITANCE =========================
 */
class Puppy extends Dog1 {

    Puppy(String name) {
        super(name);
    }

    void play() {
        System.out.println(name + " is playing");
    }
}



public class InheritanceDemo {

    public static void main(String[] args) {

        /*
         * SINGLE INHERITANCE
         */
        Dog1 Dog1 = new Dog1("Tommy");

        Dog1.eat();     // inherited
        Dog1.bark();    // own method
        Dog1.sound();   // overridden method



        /*
         * MULTILEVEL INHERITANCE
         */
        Puppy puppy = new Puppy("Bruno");

        puppy.eat();   // from Animal1
        puppy.bark();  // from Dog1
        puppy.play();  // from Puppy



        /*
         * RUNTIME POLYMORPHISM WITH INHERITANCE
         */
        Animal1 Animal1 = new Dog1("Rocky");

        Animal1.sound(); // calls Dog1 implementation
    }
}



/*
================================= TYPES OF INHERITANCE =================================

1. Single:
   Animal1 → Dog1

2. Multilevel:
   Animal1 → Dog1 → Puppy

3. Hierarchical:
   Animal1 → Dog1
          → Cat
          → Lion

4. Multiple:
   ❌ Not supported in Java (via classes)
   ✅ Supported via Interfaces

========================================================================================
*/


/*
================================= KEY CONCEPTS =================================

1. super keyword:
   - Calls parent constructor
   - Access parent methods/variables

2. Method Overriding:
   - Child provides specific implementation

3. IS-A Relationship:
   - Dog1 IS-A Animal1

================================================================================
*/


/*
================================= ACCESS MODIFIERS =================================

private   → NOT inherited
default   → inherited within package
protected → inherited across packages
public    → always inherited

================================================================================
*/


/*
================================= TRADEOFFS =================================

Advantages:
------------
+ Code reuse
+ Improves readability
+ Supports polymorphism
+ Reduces redundancy

Disadvantages:
---------------
- Tight coupling between classes
- Changes in parent affect child
- Deep hierarchy becomes hard to manage
- Not always flexible

================================================================================
*/


/*
================================= INTERVIEW QUESTIONS =================================

Q1: Why multiple inheritance not supported in Java?
Ans:
To avoid Diamond Problem (ambiguity)

Q2: What is IS-A relationship?
Ans:
Inheritance relationship (Dog1 IS-A Animal1)

Q3: Can constructor be inherited?
Ans:
No (but can be called using super)

Q4: Can we override private methods?
Ans:
No (not visible in child class)

================================================================================
*/


/*
================================= ONE-LINER =================================

Inheritance = "Code reuse using IS-A relationship"

================================================================================
*/
