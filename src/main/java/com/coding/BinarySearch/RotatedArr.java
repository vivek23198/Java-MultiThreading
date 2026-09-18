package com.coding.BinarySearch;

public class RotatedArr {

    public static int searchInRotatedArr(int[] arr, int target) {
        int left = 0, right = arr.length-1; //{4, 5, 6, 7, 0, 1, 2}

        while(left <= right) {
            int mid = left + (right-left)/2;
            if(arr[mid] == target) {
                return mid;
            }

            if(arr[left] <= arr[mid]) {

                if (arr[left] <= target && target < arr[mid]) {
                    right = mid - 1;
                } else {
                    left = mid + 1;
                }
            }else {
                if(arr[mid] < target && target <= arr[right]) {
                    left = mid+1;
                }else {
                    right = mid -1;
                }
            }
        }
        return -1;
    }

    public static boolean searchInRotatedDuplicateElemArr(int[] arr, int target) {
        int left=0, right=arr.length-1;
        boolean isTargetExist = false;

        while(left <= right) {
            int mid = left + (right-left)/2;

            if(arr[mid] == target){
                isTargetExist = true;
                return isTargetExist;
            }

            if(arr[left] == arr[mid] && arr[mid] == arr[right] && arr[mid] != target) {
                left++;
                right--;
                continue;
            }

            if(arr[left] <= arr[mid]) {
                if(arr[left] <= target &&  target < arr[mid]) {
                    right = mid -1;
                }else{
                    left = mid + 1;
                }
            } else {
                if(arr[mid] < target && target <= arr[right]) {
                    left = mid + 1;
                }else {
                    right = mid -1;
                }
            }
        }

        return isTargetExist;
    }

    public static int findMin(int[] arr) {
        int left=0, right=arr.length-1;

        while(left < right){  //[3, 4, 5, 1, 2]
            int mid = left + (right-left)/2;

            if(arr[mid] > arr[right]) {
                left = mid+1;
            } else{
                right = mid;
            }
        }

        return arr[left];
    }

    public static void main(String[] args) {
        int[] sortedArr  = {4, 5, 6, 7, 0, 1, 2};
        int target = 4;
        int targetElemIdx = searchInRotatedArr(sortedArr, target);
        System.out.println("Target Element is present at :: "+targetElemIdx);
    }
}
