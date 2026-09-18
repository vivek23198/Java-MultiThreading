package com.coding.BinarySearch;

public class sortedArraySearch {

    private static int searchElem(int[] arr, int target) {
        int low = 0, high = arr.length-1;

        while(low <= high) {
            int mid = low + (high - low)/2;  //This avoids potential integer overflow when low + high becomes larger than Integer.MAX_VALUE
            if(arr[mid] == target) {
                return mid;
            } else if(arr[mid] < target) {
                low = mid+1;
            } else {
                high = mid-1;
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        int[] arr = {1, 3, 5, 7, 9, 11, 15};
        int target = 15;

        int result = searchElem(arr, target);
        System.out.println("Searched index :: "+result);
    }
}
