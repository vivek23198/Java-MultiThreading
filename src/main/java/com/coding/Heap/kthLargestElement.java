package com.coding.Heap;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;

public class kthLargestElement {

    public static void main(String[] args) {
        Queue<Integer> pq = new PriorityQueue<>();
        int[] nums = {3,2,3,1,2,4,5,5,6};
        int k = 4;

        for(int i=0; i<nums.length; i++) {
            pq.add(nums[i]);

            while (pq.size() > k) {
                pq.poll();
            }
        }

        int kthLargestElement =  pq.peek();
        System.out.println("kthLargestElement is :: "+kthLargestElement);
    }
}
