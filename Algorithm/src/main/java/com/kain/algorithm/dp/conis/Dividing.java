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
        int[] array = new int[MAX_SUM];
        for (int i = 0; i < MAX_SUM; i++) {
            array[i] = -1;
        }
        for (int i = 0; i < coins.length; i++) {
            array[coins[i]] = 1;
        }
        int tmpSum = 0;
        for (int i = 0; i < coins.length; i++) {
            tmpSum += coins[i];
            for (int j = coins[i]; j <= tmpSum; j++) {
                int step = array[j - coins[i]];
                if (step > -1) {
                    int tmpStep = step + array[coins[i]];
                    if (tmpStep <= i + 1) {
                        if (array[j] < 0 || (array[j] > 0 && array[j] > tmpStep)) {
                            array[j] = tmpStep;
                        }
                    }
                }
            }
        }
        int mid = sum / 2;
        for (int i = 0; i <= mid; i++) {
            int one = mid - i;
            if (array[one] > 0) {
                int other = sum - one;
                return Math.abs(one - other);
            }
        }
        return -1;  // never been here
    }
}
