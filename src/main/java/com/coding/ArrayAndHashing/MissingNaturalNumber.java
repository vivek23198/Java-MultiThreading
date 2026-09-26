package com.coding.ArrayAndHashing;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MissingNaturalNumber {

    public static List<Integer> findMissingIfDuplicateExists(int[] arr, int n) {
        boolean[] present = new boolean[n + 1];

        for (int num : arr) {
            if (num >= 1 && num <= n) {
                present[num] = true;
            }
        }

        List<Integer> result = new ArrayList<>();

        for (int i = 1; i <= n; i++) {
            if (!present[i]) {
                result.add(i);
            }
        }

        return result;
    }

    public static void main(String[] args) {
        int[] arr = {1, 2 , 3, 4, 5, 7, 8, 9, 10};
        int size = arr.length+1;

        int sumOfNthNaturalNumber = (size *(size+1)) / 2;
        int sumOfArrayElem = Arrays.stream(arr).sum();

        int missingNumber = sumOfNthNaturalNumber - sumOfArrayElem;
        System.out.println("Missing Number :: "+missingNumber);

        int[] arr1 = {1, 2, 2, 4, 4};
        int size1 = arr1.length;

        List<Integer> missingNum = findMissingIfDuplicateExists(arr1, size1);
        System.out.println(missingNum);
    }
}
