package com.kain.algorithm.orderedqueue;

public class NoMoreSignal implements TerminationSignal {

    public int totalNums = 0;

    public NoMoreSignal(int totalNums) {
        this.totalNums = totalNums;
    }
}
