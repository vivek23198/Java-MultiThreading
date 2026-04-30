package com.oops;

public class InterviewQuestion {
}


/*
================================= HARD OOP MCQs (RUNNABLE FILE) =================================

👉 Copy this file directly into IntelliJ and run.
👉 Each question is isolated in its own class.
👉 Expected output is mentioned in comments.

==============================================================================================
*/


/*
============================= Q1 =============================
Expected Output: C
Explanation: Runtime polymorphism
*/
class Q1 {
    static class A {
        void show() { System.out.println("A"); }
    }

    static class B extends A {
        void show() { System.out.println("B"); }
    }

    static class C extends B {
        void show() { System.out.println("C"); }
    }

    public static void main(String[] args) {
        A obj = new C();
        obj.show(); // C
    }
}


/*
============================= Q2 =============================
Expected Output: 25
Explanation:
static block → 15
instance block → +10 → 25
*/
class Q2 {
    static int x = 10;

    static {
        x = x + 5;
    }

    {
        x = x + 10;
    }

    public static void main(String[] args) {
        new Q2();
        System.out.println(x); // 25
    }
}


/*
============================= Q3 =============================
Expected Output:
true
false
*/
class Q3 {
    public static void main(String[] args) {
        String s1 = "hello";
        String s2 = "hello";
        String s3 = new String("hello");

        System.out.println(s1 == s2); // true
        System.out.println(s1 == s3); // false
    }
}


/*
============================= Q4 =============================
Expected Output:
true
false
*/
class Q4 {
    public static void main(String[] args) {
        Integer a = 100;
        Integer b = 100;
        Integer c = 200;
        Integer d = 200;

        System.out.println(a == b); // true
        System.out.println(c == d); // false
    }
}


/*
============================= Q5 =============================
Expected Output:
Parent
Child
*/
class Q5 {
    static class Parent {
        Parent() { System.out.println("Parent"); }
    }

    static class Child extends Parent {
        Child() { System.out.println("Child"); }
    }

    public static void main(String[] args) {
        new Child();
    }
}


/*
============================= Q6 =============================
Expected Output: Static
*/
class Q6 {
    static void show() {
        System.out.println("Static");
    }

    void show(int a) {
        System.out.println("Instance");
    }

    public static void main(String[] args) {
        Q6 t = null;
        t.show(); // Static
    }
}


/*
============================= Q7 =============================
Expected Output: Compilation Error
(Uncomment to test)
*/
class Q7 {
/*
    static class A {
        void show() {}
    }

    static class B extends A {
        static void show() {} // ❌ Error
    }
*/
}


/*
============================= Q8 =============================
Expected Output:
Try
Finally
*/
class Q8 {
    public static void main(String[] args) {
        try {
            System.out.println("Try");
            return;
        } finally {
            System.out.println("Finally");
        }
    }
}


/*
============================= Q9 =============================
Expected Output:
Static Block
Main
*/
class Q9 {
    static {
        System.out.println("Static Block");
    }

    public static void main(String[] args) {
        System.out.println("Main");
    }
}


/*
============================= Q10 =============================
Expected Output: 20
*/
class Q10 {
    int x = 10;

    Q10() {
        this(20);
    }

    Q10(int x) {
        this.x = x;
    }

    public static void main(String[] args) {
        Q10 t = new Q10();
        System.out.println(t.x); // 20
    }
}


/*
============================= Q11 =============================
Expected Output: true
*/
class Q11 {
    public static void main(String[] args) {
        System.out.println(null == null); // true
    }
}


/*
============================= Q12 =============================
Expected Output: NullPointerException
*/
class Q12 {
    public static void main(String[] args) {
        String s = null;
        System.out.println(s.length()); // Exception
    }
}


/*
============================= Q13 =============================
Expected Output: Java
*/
class Q13 {
    public static void main(String[] args) {
        String s = "Java";
        s.concat(" Rocks");
        System.out.println(s); // Java
    }
}


/*
============================= Q14 =============================
Expected Output: 22
*/
class Q14 {
    public static void main(String[] args) {
        int x = 10;
        System.out.println(x++ + ++x); // 22
    }
}


/*
============================= Q15 =============================
Expected Output: 3345
*/
class Q15 {
    public static void main(String[] args) {
        System.out.println(1 + 2 + "3" + 4 + 5); // 3345
    }
}


/*
================================= FINAL TIP =================================

👉 Before answering:
- Static or instance?
- Compile-time or runtime?
- Reference or object?

================================================================================
*/


/*
================================= ADVANCED OOP MCQs (INTERFACE + INHERITANCE) =================================

👉 Copy this into IntelliJ and run
👉 Covers tricky edge cases for Interface + Inheritance
👉 Expected outputs are mentioned

==============================================================================================
*/


/*
============================= Q16 =============================
Expected Output: A
Explanation: Default method inherited
*/
class Q16 {

    interface A {
        default void show() {
            System.out.println("A");
        }
    }

    static class Test implements A {}

    public static void main(String[] args) {
        A obj = new Test();
        obj.show(); // A
    }
}


/*
============================= Q17 =============================
Expected Output: Compilation Error
Explanation: Conflict of default methods
*/
class Q17 {

/*
    interface A {
        default void show() { System.out.println("A"); }
    }

    interface B {
        default void show() { System.out.println("B"); }
    }

    class Test implements A, B {} // ❌ must override show()
*/
}


/*
============================= Q18 =============================
Expected Output: A B
Explanation: super keyword for interface
*/
class Q18 {

    interface A {
        default void show() {
            System.out.print("A ");
        }
    }

    interface B extends A {
        default void show() {
            A.super.show();
            System.out.print("B");
        }
    }

    static class Test implements B {}

    public static void main(String[] args) {
        new Test().show(); // A B
    }
}


/*
============================= Q19 =============================
Expected Output: Compilation Error
Explanation: Interface variables are final
*/
class Q19 {

/*
    interface A {
        int x = 10;
    }

    class Test implements A {
        public static void main(String[] args) {
            x = 20; // ❌ cannot modify final variable
        }
    }
*/
}


/*
============================= Q20 =============================
Expected Output: 10
Explanation: Interface variables are public static final
*/
class Q20 {

    interface A {
        int x = 10;
    }

    public static void main(String[] args) {
        System.out.println(A.x); // 10
    }
}


/*
============================= Q21 =============================
Expected Output: Compilation Error
Explanation: Cannot instantiate interface
*/
class Q21 {

/*
    interface A {}

    public static void main(String[] args) {
        A obj = new A(); // ❌
    }
*/
}


/*
============================= Q22 =============================
Expected Output: B
Explanation: Method overriding
*/
class Q22 {

    static class A {
        void show() { System.out.println("A"); }
    }

    static class B extends A {
        void show() { System.out.println("B"); }
    }

    public static void main(String[] args) {
        A obj = new B();
        obj.show(); // B
    }
}


/*
============================= Q23 =============================
Expected Output: A
Explanation: Variable hiding (no polymorphism)
*/
class Q23 {

    static class A {
        int x = 10;
    }

    static class B extends A {
        int x = 20;
    }

    public static void main(String[] args) {
        A obj = new B();
        System.out.println(obj.x); // 10
    }
}


/*
============================= Q24 =============================
Expected Output: Compilation Error
Explanation: Cannot reduce visibility
*/
class Q24 {

/*
    class A {
        public void show() {}
    }

    class B extends A {
        protected void show() {} // ❌
    }
*/
}


/*
============================= Q25 =============================
Expected Output: Compilation Error
Explanation: final class cannot be extended
*/
class Q25 {

/*
    final class A {}

    class B extends A {} // ❌
*/
}


/*
============================= Q26 =============================
Expected Output: Compilation Error
Explanation: abstract method in non-abstract class
*/
class Q26 {

/*
    class A {
        abstract void show(); // ❌
    }
*/
}


/*
============================= Q27 =============================
Expected Output: Hello
Explanation: Default method override
*/
class Q27 {

    interface A {
        default void show() {
            System.out.println("A");
        }
    }

    static class Test implements A {
        public void show() {
            System.out.println("Hello");
        }
    }

    public static void main(String[] args) {
        new Test().show(); // Hello
    }
}


/*
============================= Q28 =============================
Expected Output: Compilation Error
Explanation: Multiple inheritance conflict
*/
class Q28 {

/*
    interface A {
        default void show() {}
    }

    interface B {
        default void show() {}
    }

    class Test implements A, B {} // ❌ must override
*/
}


/*
============================= Q29 =============================
Expected Output: Static method in interface
*/
class Q29 {

    interface A {
        static void show() {
            System.out.println("Static method in interface");
        }
    }

    public static void main(String[] args) {
        A.show(); // correct way
    }
}


/*
============================= Q30 =============================
Expected Output: Compilation Error
Explanation: Cannot call static method via object
*/
class Q30 {

/*
    interface A {
        static void show() {}
    }

    class Test implements A {
        public static void main(String[] args) {
            Test t = new Test();
            t.show(); // ❌
        }
    }
*/
}


/*
================================= FINAL INTERVIEW PATTERNS =================================

👉 Interfaces:
- Default methods → can cause conflict
- Static methods → must be called via interface
- Variables → public static final

👉 Inheritance:
- Methods → runtime polymorphism
- Variables → compile-time binding
- Cannot reduce visibility
- final class → cannot extend

==============================================================================================
*/


