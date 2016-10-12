package com.kain.ioc.container.test.model;

import java.util.List;
import java.util.Map;

/**
 * Created on 10/10/2016.
 */
public class Entity {

    private String name;

    private List list;

    private Map map;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List getList() {
        return list;
    }

    public void setList(List list) {
        this.list = list;
    }

    public Map getMap() {
        return map;
    }

    public void setMap(Map map) {
        this.map = map;
    }
}
