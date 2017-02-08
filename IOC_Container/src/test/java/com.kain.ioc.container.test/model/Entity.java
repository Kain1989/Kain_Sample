package com.kain.ioc.container.test.model;

import com.kain.ioc.container.test.TestInjection;

import java.util.List;
import java.util.Map;

/**
 * Created on 10/10/2016.
 */
public class Entity {

    private String name;

    private List list;

    private Map map;

    private TestInjection test;

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

    public TestInjection getTest() {
        return test;
    }

    public void setTest(TestInjection test) {
        this.test = test;
    }
}
