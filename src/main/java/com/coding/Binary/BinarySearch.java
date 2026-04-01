package com.coding.Binary;

public class BinarySearch {

    public static boolean isElementPresent(int left, int right, int[] arr, int target) {
        if(left > right) {
            return false;
        }

        int mid = (left+right)/2;


        if(arr[mid] < target) {
            return isElementPresent(mid+1, right, arr, target);
        } else if(arr[mid] > target) {
            return isElementPresent(left, mid-1, arr, target);
        }else {
            return true;
        }
    }

    public static void main(String[] args) {
        int[] nums = {-1,0,3,5,9,12};
        int target = 90;

        boolean isTargetPresent = isElementPresent(0, nums.length-1, nums, target);

        if(isTargetPresent) {
            System.out.println("Target Present");
        }else {
            System.out.println("Target Not Present");
        }
    }
}
