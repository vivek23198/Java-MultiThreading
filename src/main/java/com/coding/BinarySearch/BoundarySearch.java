package com.coding.BinarySearch;

public class BoundarySearch {

    private static int firstOccurrence(int[] arr, int target) {
        int low=0, high=arr.length-1;
        int result  =-1;
        while(low <= high) {
            int mid = low + (high-low)/2;
            if(arr[mid] == target) {
                result = mid;
                high = mid-1;
            } else if(arr[mid] < target){
                low = mid + 1;
            } else {
                high = mid-1;
            }
        }
        return result;
    }

    private static int lastOccurrence(int[] arr, int target) {
        int low=0, high=arr.length-1;
        int result  = -1;
        while(low <= high) {
            int mid = low + (high-low)/2;
            if(arr[mid] == target) {
                result = mid;
                low = mid+1;
            } else if(arr[mid] > target) {
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }
        return result;
    }

    public static int lowerBound(int[] arr, int target) {
        int low = 0, high = arr.length-1;
        int result = arr.length;

        while(low <= high) {
            int mid = low + (high-low)/2;
            if(arr[mid] >= target) {
                result  = mid;
                high = mid-1;
            } else {
                low = mid + 1;
            }
        }
        return result;
    }

    public static int searchInsert(int[] arr, int target) {
        int low = 0, high = arr.length - 1;
        int result = arr.length;

        while (low <= high) {
            int mid = low + (high - low) / 2;

            if (arr[mid] >= target) {
                result = mid;
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }

        return result;
    }

    public static int upperBound(int[] arr, int target) {
        int low = 0, high = arr.length-1;
        int result = -1;

        while(low <= high) {
            int mid = low + (high-low)/2;
            if(arr[mid] > target) {
                result = mid;
                high = mid-1;
            } else {
                low = mid+1;
            }
        }
        return result;
    }

    public static void main(String[] args) {
        int[] arr = {1, 2, 2, 2, 3, 4, 4, 5};
        int target = 10;

        int firsOccurrence = firstOccurrence(arr, target);
        System.out.println("First Occurence :: "+firsOccurrence);

        int lastOccurence = lastOccurrence(arr, target);
        System.out.println("Last Occurrence :: "+lastOccurence);

        int lowerBound = lowerBound(arr, 4);
        System.out.println("Lower Bound :: "+lowerBound);

        int upperBound = upperBound(arr, 3);
        System.out.println("Upper Bound :: "+upperBound);

        int searchInsert = searchInsert(arr, 3);
        System.out.println("Search Insert index :: "+searchInsert);
    }
}
