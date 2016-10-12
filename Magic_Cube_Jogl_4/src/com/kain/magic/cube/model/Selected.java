package com.kain.magic.cube.model;

public class Selected {

    public static final String X = "x";
    public static final String Y = "y";
    public static final String Z = "z";

    public String axis;
    public int index;

    public Selected(String axis, int index) {
        super();
        this.axis = axis;
        this.index = index;
    }

}
