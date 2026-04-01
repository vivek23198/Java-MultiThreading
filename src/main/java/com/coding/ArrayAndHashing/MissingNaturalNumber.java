package com.coding.ArrayAndHashing;

import java.util.Arrays;

public class MissingNaturalNumber {

    public static void main(String[] args) {
        int[] arr = {1, 2 , 3, 4, 5, 7, 8, 9, 10};
        int size = arr.length+1;

        int sumOfNthNaturalNumber = (size *(size+1)) / 2;
        int sumOfArrayElem = Arrays.stream(arr).sum();

        int missingNumber = sumOfNthNaturalNumber - sumOfArrayElem;
        System.out.println("Missing Number :: "+missingNumber);
    }
}
