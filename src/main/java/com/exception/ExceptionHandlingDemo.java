package com.exception;

import java.io.*;

/*
================================= EXCEPTION HANDLING (COMPLETE REVISION) =================================

Definition:
------------
Exception Handling is a mechanism to handle runtime errors and maintain normal program flow.

==============================================================================================
*/

public class ExceptionHandlingDemo {

    public static void main(String[] args) {

        /*
         * ================= 1. ARITHMETIC EXCEPTION =================
         * Occurs when dividing by zero
         */
        try {
            int result = 10 / 0;
        } catch (ArithmeticException e) {
            System.out.println("ArithmeticException: Cannot divide by zero");
        }


        /*
         * ================= 2. NULL POINTER EXCEPTION =================
         * Occurs when accessing methods/fields on null object
         */
        try {
            String s = null;
            System.out.println(s.length());
        } catch (NullPointerException e) {
            System.out.println("NullPointerException: Object is null");
        }


        /*
         * ================= 3. ARRAY INDEX OUT OF BOUNDS =================
         * Occurs when accessing invalid index
         */
        try {
            int[] arr = new int[3];
            System.out.println(arr[5]);
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("ArrayIndexOutOfBoundsException: Invalid index");
        }


        /*
         * ================= 4. NUMBER FORMAT EXCEPTION =================
         * Occurs when converting invalid string to number
         */
        try {
            int num = Integer.parseInt("abc");
        } catch (NumberFormatException e) {
            System.out.println("NumberFormatException: Invalid number format");
        }


        /*
         * ================= 5. CLASS CAST EXCEPTION =================
         * Occurs when casting wrong type
         */
        try {
            Object obj = "Java";
            Integer num = (Integer) obj;
        } catch (ClassCastException e) {
            System.out.println("ClassCastException: Invalid type casting");
        }


        /*
         * ================= 6. FILE NOT FOUND (CHECKED EXCEPTION) =================
         * Occurs when file is missing
         */
        try {
            FileReader file = new FileReader("missing.txt");
        } catch (FileNotFoundException e) {
            System.out.println("FileNotFoundException: File not found");
        }


        /*
         * ================= 7. IO EXCEPTION =================
         * Occurs during input/output operations
         */
        try {
            BufferedReader br = new BufferedReader(new FileReader("test.txt"));
            br.readLine();
            br.close();
        } catch (IOException e) {
            System.out.println("IOException: Error while reading file");
        }


        /*
         * ================= 8. TRY-CATCH-FINALLY =================
         */
        try {
            System.out.println("Try block running");
        } finally {
            System.out.println("Finally always executes");
        }


        /*
         * ================= 9. THROW KEYWORD =================
         */
        try {
            validateAge(15);
        } catch (IllegalArgumentException e) {
            System.out.println("Thrown Exception: " + e.getMessage());
        }


        /*
         * ================= 10. THROWS KEYWORD =================
         */
        try {
            readFile();
        } catch (IOException e) {
            System.out.println("Handled using throws");
        }


        /*
         * ================= 11. CUSTOM EXCEPTION =================
         */
        try {
            withdraw(1000, 2000);
        } catch (InsufficientBalanceException e) {
            System.out.println("Custom Exception: " + e.getMessage());
        }


        /*
         * ================= 12. TRY WITH RESOURCES =================
         * Automatically closes resources
         */
        try (BufferedReader br = new BufferedReader(new FileReader("test.txt"))) {
            System.out.println("Reading file...");
        } catch (IOException e) {
            System.out.println("Handled resource exception");
        }
    }


    /*
     * THROW EXAMPLE
     */
    static void validateAge(int age) {
        if (age < 18) {
            throw new IllegalArgumentException("Age must be 18+");
        }
    }


    /*
     * THROWS EXAMPLE
     */
    static void readFile() throws IOException {
        FileReader file = new FileReader("abc.txt");
    }


    /*
     * CUSTOM EXCEPTION USAGE
     */
    static void withdraw(int balance, int amount) throws InsufficientBalanceException {
        if (amount > balance) {
            throw new InsufficientBalanceException("Insufficient balance");
        }
    }
}


/*
============================= CUSTOM EXCEPTION =============================
*/
class InsufficientBalanceException extends Exception {

    public InsufficientBalanceException(String message) {
        super(message);
    }
}


/*
================================= EXCEPTION HIERARCHY =================================

                Throwable
                /      \
          Exception     Error
             |
    -------------------------
    |                       |
Checked Exception     RuntimeException
                           |
        --------------------------------------------------
        |            |              |            |         |
ArithmeticEx   NullPointerEx   IndexOutOfBounds   NumberFormat   ClassCast


Detailed Explanation:

1. ArithmeticException
   - Cause: divide by zero
   - Example: 10/0

2. NullPointerException
   - Cause: using null reference
   - Example: obj.method()

3. ArrayIndexOutOfBoundsException
   - Cause: invalid index
   - Example: arr[10]

4. NumberFormatException
   - Cause: invalid parsing
   - Example: Integer.parseInt("abc")

5. ClassCastException
   - Cause: invalid casting
   - Example: (Integer) "String"

6. FileNotFoundException (Checked)
   - Cause: file missing
   - Must be handled

7. IOException (Checked)
   - Cause: input/output failure

==============================================================================================
*/


/*
================================= INTERVIEW QUESTIONS =================================

Q1: Checked vs Unchecked?
Checked → compile-time
Unchecked → runtime

Q2: finally block?
Always executes (except JVM crash)

Q3: throw vs throws?
throw → used inside method
throws → declared in method

==============================================================================================
*/


/*
================================= ONE-LINER =================================

Exception Handling = "Handle runtime errors safely"

==============================================================================================
*/
