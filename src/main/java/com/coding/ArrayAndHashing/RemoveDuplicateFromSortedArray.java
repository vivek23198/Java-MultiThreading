package com.coding.ArrayAndHashing;

public class RemoveDuplicateFromSortedArray {

    public static void main(String[] args) {
        int[] nums = {0,0,1,1,1,2,2,3,3,4};

        int ptr1 = 0, ptr2 = 0;

        while(ptr2 < nums.length) {
            if(nums[ptr1] == nums[ptr2]) {
                ptr2++;
            } else {
                ptr1++;
                int temp = nums[ptr1];
                nums[ptr1] = nums[ptr2];
                nums[ptr2] = temp;

                ptr2++;
            }
        }

        for(int i=0; i<= ptr1; i++) {
            System.out.print(nums[i]+" ");
        }


    }
}
