package com.telenav.kain.test.properties;

import java.io.IOException;
import java.util.Properties;

/**
 * Created by zfshi on 5/29/2015.
 */
public class TestProperties {

    public static void main(String[] args) throws IOException {
        Properties props = new Properties();
        System.setProperty("TEST_DIR", "java");

        props.setProperty("base_dir", "java");

        props.load(TestProperties.class.getClassLoader().getResourceAsStream("config/config.properties"));
        System.out.println(props.getProperty("base_dir"));
        System.out.println(props.getProperty("current_dir"));
        System.out.println(props.getProperty("test"));
    }
}
