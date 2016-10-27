package com.kain.ioc.container.factory;

import com.kain.ioc.container.model.BeanDefinition;
import com.kain.ioc.container.model.PropertyValue;
import com.kain.ioc.container.model.Scope;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created on 10/18/2016.
 */
public class BeanDefinitionLoader {

    private static final String CONTAINER_ROOT = "appContext/";

    private static final String MAIN_RESOURCE = "appContext.xml";

    public Map<String, BeanDefinition> load() {
        Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<String, BeanDefinition>();
        this.load(CONTAINER_ROOT + MAIN_RESOURCE, beanDefinitionMap);
        return beanDefinitionMap;
    }

    private void load(String context, Map<String, BeanDefinition> beanDefinitionMap) {
        try {

            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            InputStream ins = classLoader.getResourceAsStream(context);

            SAXReader reader = new SAXReader();
            Document doc = reader.read(ins);
            Element root = doc.getRootElement();
            Element beanNode;
            for (Iterator<Element> i = root.elementIterator("import"); i.hasNext(); ) {
                beanNode = i.next();
                Attribute resource = beanNode.attribute("resource");
                if (resource != null) {
                    load(CONTAINER_ROOT + resource.getText(), beanDefinitionMap);
                }
            }
            loadBeanDefition(root, beanDefinitionMap);
        } catch (Exception e) {
            System.out.println(e.toString());
            e.printStackTrace();
        }
    }

    private void loadBeanDefition(Element root, Map<String, BeanDefinition> beanDefinitionMap) throws Exception {
        Element beanNode;
        for (Iterator<Element> i = root.elementIterator("bean"); i.hasNext(); ) {
            BeanDefinition beanDef = new BeanDefinition();
            beanNode = i.next();
            Attribute id = beanNode.attribute("id");
            beanDef.setId(id.getText());
            Attribute className = beanNode.attribute("class");
            beanDef.setBeanClassName(className.getText());
            Attribute lazy = beanNode.attribute("lazy");
            if (lazy != null) {
                beanDef.setLazy(Boolean.getBoolean(lazy.getText()));
            }
            Attribute scope = beanNode.attribute("scope");
            if (scope != null) {
                beanDef.setScope(Scope.parse(scope.getText()));
            }
            beanDef.setPropertyValues(loadBeanProperty(beanNode, beanDefinitionMap));
            beanDefinitionMap.put(id.getText(), beanDef);
        }
    }

    private List<PropertyValue> loadBeanProperty(Element beanNode, Map<String, BeanDefinition> beanDefinitionMap) throws Exception {
        List<PropertyValue> propValueList = new ArrayList<PropertyValue>();
        for (Iterator<Element> ite = beanNode.elementIterator("property"); ite.hasNext(); ) {
            Element beanPro = ite.next();
            Attribute name = beanPro.attribute("name");

            Object proVal = parsePropertyValue(beanPro, beanDefinitionMap);
            PropertyValue propValue = new PropertyValue(name.getText(), proVal);
            propValueList.add(propValue);
        }
        return propValueList;
    }

    private Object parsePropertyValue(Element element, Map<String, BeanDefinition> beanDefinitionMap) {
        Element list = element.element("list");
        Element map = element.element("map");
        if (list != null) {
            return paarseListObject(list, beanDefinitionMap);
        } else if (map != null) {
            return parseMapObject(map, beanDefinitionMap);
        } else {
            for (Iterator<Element> ite1 = element.elementIterator("value"); ite1.hasNext(); ) {
                Element node = ite1.next();
                return node.getText();
            }
            return null;
        }
    }

    private List<Object> paarseListObject(Element list, Map<String, BeanDefinition> beanDefinitionMap) {
        List<Object> proList = new ArrayList<Object>();
        for (Iterator<Element> listElementIte = list.elementIterator("value"); listElementIte.hasNext(); ) {
            proList.add(listElementIte.next().getText());
        }
        for (Iterator<Element> listElementIte = list.elementIterator("ref"); listElementIte.hasNext(); ) {
            proList.add(beanDefinitionMap.get(listElementIte.next().getText()));
        }
        return proList;
    }

    private Map<String, Object> parseMapObject(Element map, Map<String, BeanDefinition> beanDefinitionMap) {
        Map<String, Object> proMap = new HashMap<String, Object>();
        for (Iterator<Element> ite1 = map.elementIterator("entry"); ite1.hasNext(); ) {
            Element node = ite1.next();
            String key = node.attribute("key").getText();
            if (node.attribute("value") != null) {
                proMap.put(key, node.attribute("value").getText());
            }
            if (node.attribute("ref") != null) {
                proMap.put(key, beanDefinitionMap.get(node.attribute("ref").getText()));
            }
            if (node.element("list") != null) {
                proMap.put(key, paarseListObject(node.element("list"), beanDefinitionMap));
            }
        }
        return proMap;
    }
}
