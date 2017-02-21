package com.kain.ioc.container.factory;

import com.kain.ioc.container.model.BeanDefinition;
import com.kain.xml.resolver.BeanSaxParser;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.InputStream;
import java.util.Map;

/**
 * Created on 2/21/2017.
 */
public class BeanDefinitionSaxLoader {

    private static final String CONTAINER_ROOT = "appContext/";

    private static final String MAIN_RESOURCE = "appContext.xml";


    public static Map<String, BeanDefinition> load() {
        return load(MAIN_RESOURCE);
    }

    public static Map<String, BeanDefinition> load(String resources) {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream ins = classLoader.getResourceAsStream(CONTAINER_ROOT + resources);
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            BeanSaxParser beanSaxParser = new BeanSaxParser();
            saxParser.parse(ins, beanSaxParser);
            return beanSaxParser.getBeanMap();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
