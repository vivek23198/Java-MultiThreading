package com.coding.ArrayAndHashing;

import java.util.*;
import java.util.stream.Collectors;

public class GroupAnagram {

    private static List<List<String>> getGroupAnagram(String[] strs) {
        Map<String, List<String>> result = new HashMap<>();

        for(int i=0; i<strs.length; i++) {
            char[] sortedStr = strs[i].toCharArray();
            Arrays.sort(sortedStr);
            String key = new String(sortedStr);

            if(!result.containsKey(key)) {
                result.put(key, new ArrayList<>());
            }

            result.get(key).add(strs[i]);
        }
        return new ArrayList(result.values());
    }

    public static void main(String[] args) {
        String[] str = {"eat","tea","tan","ate","nat","bat"};

        List<List<String>> result = getGroupAnagram(str);
        System.out.println(result);
    }
}
