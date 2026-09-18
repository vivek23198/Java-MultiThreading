package com.coding.BinarySearch;


public class BinarySearchOnAnswers {

    public static int kokoEatingBanana(int[] piles, int hours) {
        int maxBanana = Integer.MIN_VALUE;

        for(int banana: piles) {
            maxBanana = Math.max(maxBanana, banana);
        }

        int left=1, right = maxBanana;
        int res = -1;

        while(left <= right) {
            int mid = left + (right-left)/2;
            int totalHrsTookToEatAllBanana = getTotalHrs(piles, mid);

            if(totalHrsTookToEatAllBanana <= hours) {
                res = mid;
                right = mid-1;
            } else {
                left = mid+1;
            }
        }
        return res;
    }

    public static int minCapacityOfShip(int[] arr, int days) {
        int totalWeight = 0, maxWeight = Integer.MIN_VALUE;
        int ans = 0;
        for(int num: arr) {
            totalWeight += num;
            maxWeight = Math.max(maxWeight, num);
        }

        int left = maxWeight, right = totalWeight;


        while(left <= right) {
            int mid = left + (right-left)/2;

            int noOfDaysTook = totalNumberOfDaysTook(arr, mid);
            if(noOfDaysTook <= days) {
                ans = mid;
                right = mid -1;
            } else {
                left = mid + 1;
            }
        }
        return ans;
    }

    public static int splitArray(int[] nums, int k) {
        int ans = 0;
        int maxElem = Integer.MIN_VALUE;
        int totalSum = 0;

        for(int num: nums) {
            totalSum += num;
            maxElem = Math.max(maxElem, num);
        }

        int left =maxElem, right = totalSum;

        while(left <= right) {
            int mid = left + (right-left)/2;

            int totalSubArrayPossible = numberOfSubarrays(nums, mid);

            if(totalSubArrayPossible <= k) {
                ans = mid;
                right = mid -1;
            } else {
                left = mid + 1;
            }
        }
        return ans;
    }

    private static int numberOfSubarrays(int[] nums, int capacity) {
        // calculate how many subarrays are required
        int noOfSubArray = 1;
        int sum = 0;

        for(int num: nums) {
            sum += num;

            if(sum > capacity) {
                noOfSubArray++;
                sum = num;
            }
        }
        return noOfSubArray;
    }

    private static int totalNumberOfDaysTook(int[] arr, int minCapacity) {
        int days = 0;
        int sum = 0;

        for(int num: arr) {
            sum += num;

            if(sum > minCapacity) {
                days++;
                sum = num;
            }
        }
        return days+1;
    }

    private static int getTotalHrs(int[] piles, int rateOfBanana) {

        int totalHours = 0;

        for(int num: piles) {
            totalHours = (int) (totalHours + Math.ceil((double) num / rateOfBanana));
        }
        return totalHours;
    }

    public static void main(String[] args) {
        int[] piles = {30,11,23,4,20};
        int h = 5;

        int minRateOfEatingBananaKokoHave = kokoEatingBanana(piles, h);
        System.out.println("Min Rate Of Which Koko Will eat all banana :: "+minRateOfEatingBananaKokoHave);

        int[] weights = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        int days = 5;

        int minCapacity = minCapacityOfShip(weights, days);
        System.out.println("Minimum Capacity Of Ship To carry All loads in the Given time :: "+minCapacity);

        int[]  nums = {7,2,5,10,8};
        int k=2;

        int minSumToCreateKSubArray = splitArray(nums, k);
        System.out.println("Minimum Sum Required to Create " +k+" Subarray  :: "+minSumToCreateKSubArray);
    }
}
