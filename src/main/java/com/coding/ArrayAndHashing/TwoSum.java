package com.coding.ArrayAndHashing;

import java.util.HashMap;
import java.util.Map;

public class TwoSum {

    public static void main(String[] args) {
        int[] nums = {2, 7, 11, 15};
        int target = 9;

        int ptr1 = 0;
        int ptr2 = nums.length-1;

        while(ptr1 < ptr2) {
            int sum = nums[ptr1] + nums[ptr2];
            if(sum < target) {
                ptr1++;
            } else if(sum > target) {
                ptr2--;
            } else {
                System.out.println("First Element :: "+nums[ptr1]+ " Second Element :: "+nums[ptr2] +" Sums to "+target);
                break;
            }
        }

        // if arrays are not sorted
        int[] arr1 = {10, 3, 6, 12, 2, 9};
        int target1 = 15;

        Map<Integer, Integer> mp = new HashMap<>();

        for(int i=0; i<arr1.length; i++){
            if(mp.containsKey(target1 - arr1[i])) {
                int secondElement = arr1[mp.get(target1-arr1[i])];
                System.out.println("First Element :: "+arr1[i]+ " Second Element :: "+secondElement +" Sums to "+target1);
                break;
            }
            mp.put(arr1[i], i);
        }

    }
}
