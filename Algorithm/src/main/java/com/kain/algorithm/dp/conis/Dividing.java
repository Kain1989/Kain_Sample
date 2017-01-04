package com.kain.algorithm.dp.conis;

/**
 * Created on 12/29/2016.
 */
public class Dividing {

    public static void main(String args[]) {

        int[] coins = new int[]{1, 5, 10, 25, 50, 100};
        int total = 0;
        for (int i = 0; i < coins.length; i++) {
            total += coins[i];
        }
        int res = solveByBruteForce(coins, 0, coins.length, 0, total);
        System.out.println(res);

        res = solveByDp(coins, total);
        System.out.println(res);
    }

    static int solveByBruteForce(int conis[], int cur, int coinSize, int currentValue, int sum) {
        if (cur == coinSize) { // the border of recursion
            int other = sum - currentValue;
            System.out.println("Current value " + currentValue + ", Left value " + other);
            return Math.abs(currentValue - other);
        }
        System.out.println("+ coin" + (cur + 1));
        int getCoinResult = solveByBruteForce(conis, cur + 1, coinSize, currentValue + conis[cur], sum);
        System.out.println("- coin" + (cur + 1));
        int giveUpCoinResult = solveByBruteForce(conis, cur + 1, coinSize, currentValue, sum);
        if (getCoinResult < giveUpCoinResult) {
            return getCoinResult;
        }
        return giveUpCoinResult;
    }

    private static int solveByDp(int[] coins, int sum) {
        int MAX_SUM = 100000;
        boolean[] array = new boolean[MAX_SUM];
        for (int i = 0; i < MAX_SUM; i++) {
            array[i] = false;
        }
        array[0] = true;
        for (int i = 0; i < coins.length; i++) {
            for (int j = coins[i]; j <= sum; j++) {
                if (array[j - coins[i]]) {
                    array[j] = true;
                }
            }
        }
        int mid = sum / 2;
        for (int i = 0; i <= mid; i++) {
            int one = mid - i;
            if (array[one]) {
                int other = sum - one;
                return Math.abs(one - other);
            }
        }
        return -1;  // never been here
    }
}
