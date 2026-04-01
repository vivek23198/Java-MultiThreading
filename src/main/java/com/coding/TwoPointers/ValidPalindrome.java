package com.coding.TwoPointers;

public class ValidPalindrome {

    private static boolean isValidPalindrome(String str) {
        char[] ch = str.replaceAll("[^a-zA-Z]", "")
                .toLowerCase()
                .toCharArray();
        int ptr1 =0;
        int ptr2 = ch.length-1;

        while(ptr1 < ptr2) {
            if(ch[ptr1] != ch[ptr2]) {
                return false;
            }
            ptr1++;
            ptr2--;
        }
        return true;
    }

    public static void main(String[] args) {
        String str = "A man, a plan, a canal: Panama";
        if(isValidPalindrome(str)) {
            System.out.println("Given String are a Valid Palindrome");
        } else {
            System.out.println("Given String are not a Valid Palindrome");
        }
    }
}
