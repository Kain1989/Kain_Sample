package com.kain.ioc.container.test;

import com.kain.ioc.container.factory.BeanDefinitionSaxLoader;
import com.kain.xml.resolver.BeanSaxParser;
import org.junit.Test;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.net.URL;
import java.util.Map;

/**
 * Created on 2/21/2017.
 */
public class TestSaxReader {

    @Test
    public void test() throws SAXException, IOException {
        XMLReader xmlReader = XMLReaderFactory.createXMLReader();
        BeanSaxParser handler = new BeanSaxParser();
        xmlReader.setContentHandler(handler);
        xmlReader.setEntityResolver(handler);
        xmlReader.setErrorHandler(handler);
        xmlReader.setDTDHandler(handler);
        URL url = BeanSaxParser.class.getClassLoader().getResource("appContext/appContext.xml");
        xmlReader.parse(url.getPath());
        System.out.println(handler.getBeanMap());
    }

    @Test
    public void test2() throws ParserConfigurationException, SAXException {
        Map map = BeanDefinitionSaxLoader.load();
        System.out.println(map);
    }
}
