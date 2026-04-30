package com.oops;

/*
================================= ENCAPSULATION (INTERVIEW REVISION) =================================

Definition:
------------
Encapsulation is the process of wrapping data (variables) and methods (functions)
into a single unit (class) and restricting direct access to the data.

Key Idea:
----------
Hide internal data → expose controlled access via methods

How to achieve in Java:
-----------------------
1. Make variables PRIVATE
2. Provide PUBLIC getters/setters or controlled methods

Why Encapsulation?
-------------------
- Protects data from unauthorized access
- Allows validation before modifying data
- Improves maintainability and flexibility
- Enables data hiding

Real-life Example:
------------------
ATM Machine:
- You can withdraw/deposit money
- But cannot directly access bank database

==============================================================================================
*/

class BankAccount {

    /*
     * PRIVATE VARIABLES (Data Hiding)
     * ------------------------------
     * Cannot be accessed directly outside the class
     */
    private String accountHolder;
    private double balance;


    /*
     * CONSTRUCTOR
     * -----------
     * Used to initialize object
     */
    public BankAccount(String accountHolder, double balance) {
        this.accountHolder = accountHolder;
        this.balance = balance;
    }


    /*
     * GETTER METHOD
     * -------------
     * Provides READ access
     */
    public double getBalance() {
        return balance;
    }


    /*
     * CONTROLLED SETTER (Deposit)
     * ---------------------------
     * Adds validation before updating data
     */
    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
        } else {
            System.out.println("Invalid deposit amount!");
        }
    }


    /*
     * CONTROLLED OPERATION (Withdraw)
     * -------------------------------
     * Ensures valid transaction
     */
    public void withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
        } else {
            System.out.println("Invalid withdrawal!");
        }
    }
}



public class EncapsulationDemo {

    public static void main(String[] args) {

        BankAccount acc = new BankAccount("Vivek", 1000);

        /*
         * ❌ DIRECT ACCESS NOT ALLOWED
         * acc.balance = 5000; // Compile-time error
         */

        /*
         * ✅ ACCESS THROUGH METHODS
         */
        acc.deposit(500);
        acc.withdraw(200);

        System.out.println("Balance: " + acc.getBalance());
    }
}


/*
================================= TRADEOFFS =================================

Advantages:
------------
+ Data hiding (security)
+ Controlled access
+ Easy to maintain and update
+ Validation before modification

Disadvantages:
---------------
- Slight performance overhead (method calls)
- More boilerplate code (getters/setters)
- Can become verbose if overused

================================================================================
*/


/*
================================= INTERVIEW QUESTIONS =================================

Q1: Is encapsulation same as abstraction?
Ans:
- Encapsulation → Data hiding
- Abstraction → Hiding implementation details

Q2: Can we create fully encapsulated class?
Ans:
Yes → All variables private + public methods

Q3: Why not make variables public?
Ans:
No control → leads to invalid data and bugs

================================================================================
*/


/*
================================= ONE-LINER =================================

Encapsulation = "Hide data + Provide controlled access"

================================================================================
*/
