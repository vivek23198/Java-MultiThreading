package com.coding.ArrayAndHashing;

public class MoveAllZeroesToEnd {


    public static void main(String[] args) {
        int[] arr = {1, 2, 0, 4, 3, 0, 5, 0};

        int ptr1 = 0;

        for(int num: arr) {
            if(num != 0) {
                arr[ptr1] = num;
                ptr1++;
            }
        }

        for(int i=ptr1; i<arr.length; i++) {
            arr[i] = 0;
        }

        for(int num: arr) {
            System.out.print(num+" ");
        }


    }
}
