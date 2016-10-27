package com.kain.ioc.container.model;

public enum Region {
    NA,
    EU,
    CN,
    KR,
    TW,
    SA,
    ANZ,
    TR,
    IL,
    AF,
    MEA;

    private static Region currentRegion = null;

    public static Region parse(String name) {
        try {
            if (name == null)
                throw new IllegalArgumentException();
            else
                return Region.valueOf(name.trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    public static Region getCurrentRegion() {
        return currentRegion;
    }

    public static void updateRegion(Region region) {
        currentRegion = region;
    }

    public static boolean is(Region region) {
        return currentRegion == region;
    }
}
