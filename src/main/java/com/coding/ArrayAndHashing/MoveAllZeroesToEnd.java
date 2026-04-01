package com.coding.ArrayAndHashing;

public class MoveAllZeroesToEnd {


    public static void main(String[] args) {
        int[] arr = {1, 2, 0, 4, 3, 0, 5, 0};

        int ptr1 = 0;
        int ptr2 = arr.length-1;

        while(ptr1 < ptr2) {
            if(arr[ptr1] != 0) {
                ptr1++;
            } else if(arr[ptr2] == 0) {
                ptr2--;
            }
            else  {
                int temp = arr[ptr1];
                arr[ptr1] = arr[ptr2];
                arr[ptr2] = temp;
                ptr1++;
                ptr2--;
            }
        }

        for(int elem: arr) {
            System.out.print(elem+" ");
        }

    }
}
