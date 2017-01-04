package com.kain.algorithm.dp.conis;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created on 12/29/2016.
 */
public class RepresentableFaceAmount {

    public static void main(String args[]) {
        int[] coins = new int[]{1, 1, 1, 1, 1, 1, 1, 1, 5, 5, 5, 5, 5, 5, 10, 10, 10, 10, 10, 25, 25, 25, 50, 50, 100};
        int total = 0;
        for (int i = 0; i < coins.length; i++) {
            total += coins[i];
        }
//        Set<Integer> faceValueSet = solveByBruteForce(coins, 0, coins.length, 0, total);
        Set<Integer> faceValueSet = solveByDp(coins, total);
        List<Integer> faceValueList = new ArrayList<>(faceValueSet);
        Collections.sort(faceValueList);
        System.out.println(faceValueList);
    }



    public static Set<Integer> solveByBruteForce(int conis[], int cur, int coinSize, int currentValue, int sum) {
        Set<Integer> faceValueSet = new HashSet<>();
        if (cur == coinSize) { // the border of recursion
            System.out.println("Current value " + currentValue);
            faceValueSet.add(currentValue);
            return faceValueSet;
        }
        System.out.println("+ coin" + (cur + 1));
        faceValueSet.addAll(solveByBruteForce(conis, cur + 1, coinSize, currentValue + conis[cur], sum));
        System.out.println("- coin" + (cur + 1));
        faceValueSet.addAll(solveByBruteForce(conis, cur + 1, coinSize, currentValue, sum));
        return faceValueSet;
    }

    private static Set<Integer> solveByDp(int[] coins, int sum) {
        Set<Integer> faceValueSet = new HashSet<>();
        int MAX_SUM = sum + 1;
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
        for (int i = 0; i <= sum; i++) {
            if (array[i] > 0) {
                faceValueSet.add(i);
            }
        }
        return faceValueSet;
    }
}
