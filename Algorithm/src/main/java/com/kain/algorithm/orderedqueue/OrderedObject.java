package com.kain.algorithm.orderedqueue;

public class OrderedObject<T> {

    private T value;

    private int ordinal;

    public OrderedObject(T value, int ordinal) {
        super();
        this.value = value;
        this.ordinal = ordinal;
    }

    public T getValue() {
        return value;
    }

    public int getOrdinal() {
        return ordinal;
    }

}
