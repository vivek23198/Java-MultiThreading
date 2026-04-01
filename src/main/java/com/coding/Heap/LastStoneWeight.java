package com.coding.Heap;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;

public class LastStoneWeight {

    public static void main(String[] args) {
        int[] stones = {2,7,4,1,8,1};
        Queue<Integer> pq = new PriorityQueue(Comparator.reverseOrder());
        int result = 0;

        for(int i=0; i<stones.length; i++) {
            pq.add(stones[i]);
        }

        while(pq.size() > 1) {
            int stone1 = pq.poll();
            int stone2 = pq.poll();

            int newStoneWeight = stone1 == stone2 ? 0 : stone1 > stone2 ? stone1-stone2 : stone2-stone1;
            if(newStoneWeight > 0) {
                pq.add(newStoneWeight);
            }
        }
        int finalStoneWeight =  pq.peek();
        System.out.println("Final Stone Weight :: "+finalStoneWeight);
    }
}
