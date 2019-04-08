package com.kain.algorithm.dp;

/**
 * Created on 12/29/2016.
 */
public enum Chip {

    ONE(1),

    FIVE(5),

    TEN(10),

    TRENTY_FIVE(25),

    FIFTY(50),

    ONE_HUNDRED(100);

    private int value;

    Chip(int value) {
        this.value = value;
    }
}
