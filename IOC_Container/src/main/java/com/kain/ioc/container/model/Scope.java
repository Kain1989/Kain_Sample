package com.kain.ioc.container.model;

/**
 * Created on 10/11/2016.
 */
public enum Scope {

    PROTOTYPE("prototype"),

    SINGLETON("singleton");

    private String name;

    Scope(String name) {
        this.name = name;
    }

    public static Scope parse(String name) {
        if (name.equalsIgnoreCase(PROTOTYPE.name)) {
            return PROTOTYPE;
        }
        if (name.equalsIgnoreCase(SINGLETON.name)) {
            return SINGLETON;
        }
        return SINGLETON;
    }

}
