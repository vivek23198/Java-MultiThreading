package com.coding.SlidingWindow;

public class BuyAndSellStock {

    public static int maxProfit(int[] prices) {
        int profit = 0;
        int min = Integer.MAX_VALUE;

        for(int i=0; i<prices.length; i++){
            if(min > prices[i]) {
                min = prices[i];
            }

            if(prices[i] - min > profit) {
                profit = prices[i] - min;
            }
        }

        return profit;
    }

    public static void main(String[] args) {
        int[] prices = {7,1,5,3,6,4};
        int maxProfit = maxProfit(prices);
        System.out.println("Max Profit "+maxProfit);
    }
}
