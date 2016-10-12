package com.kain.ioc.container.test;

import com.kain.ioc.container.factory.BeanFactory;
import com.kain.ioc.container.test.model.Entity;
import org.junit.Test;

import java.net.URISyntaxException;

/**
 * Created on 10/10/2016.
 */
public class TestInjection {

    private String name;

    @Test
    public void testInjection() throws URISyntaxException {
        BeanFactory context = BeanFactory.getInstance();
        Entity obj = (Entity) context.getBean("entity");
        System.out.println(obj.getName());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
