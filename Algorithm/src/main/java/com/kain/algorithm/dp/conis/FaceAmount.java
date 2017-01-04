package com.kain.algorithm.dp.conis;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created on 12/29/2016.
 */
public class FaceAmount {

    public static void main(String args[]) {
        int[] coins = new int[]{1, 1, 1, 1, 1, 1, 1, 1, 5, 5, 5, 5, 5, 5, 10, 10, 10, 10, 10, 25, 25, 25, 50, 50, 100};
        int total = 0;
        for (int i = 0; i < coins.length; i++) {
            total += coins[i];
        }
        Set<Integer> faceValueSet = solveByBruteForce(coins, 0, coins.length, 0, total);
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
}
