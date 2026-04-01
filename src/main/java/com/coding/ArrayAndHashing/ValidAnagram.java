package com.coding.ArrayAndHashing;

import java.util.HashMap;
import java.util.Map;

public class ValidAnagram {

    private static boolean isAnagram(String str1, String str2) {
        if(str1.length() != str2.length()) {
            return false;
        }

        Map<Character, Integer> mp = new HashMap<>();

        for(int i=0; i<str1.length(); i++){
            mp.put(str1.charAt(i), mp.getOrDefault(str1.charAt(i)+1, 1));
        }

        for (int i=0; i<str2.length(); i++) {
            if(mp.containsKey(str2.charAt(i))) {
                mp.put(str1.charAt(i), mp.get(str1.charAt(i)) - 1);
            } else {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        String str1 = "anagram";
        String str2 = "nagaram";

        boolean isAnagram = ValidAnagram.isAnagram(str1, str2);
        if(isAnagram) {
            System.out.println("Anagram");
        } else {
            System.out.println("Not an Anagram");
        }
    }
}
