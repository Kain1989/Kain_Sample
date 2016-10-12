package com.kain.ioc.container.factory;

import com.kain.ioc.container.model.BeanDefinition;
import com.kain.ioc.container.model.PropertyValue;
import com.kain.ioc.container.model.Scope;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created on 10/10/2016.
 */
public class BeanFactory {

    private static final String CONTAINER_ROOT = "appContext/";

    private static final String MAIN_RESOURCE = "appContext.xml";

    private final Map<String, Object> singletonObjects = new ConcurrentHashMap<String, Object>();

    private final Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<String, BeanDefinition>();

    private static BeanFactory instance;

    private BeanFactory() {
    }

    public static BeanFactory getInstance() {
        if (instance == null) {
            instance = new BeanFactory();
            instance.initialize();
        }
        return instance;
    }

    public void initialize() {
        String mainContext = CONTAINER_ROOT + MAIN_RESOURCE;
        initialize(mainContext);
        initializeInstance();
    }

    public void initialize(String context) {
        try {

            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            InputStream ins = classLoader.getResourceAsStream(context);

            SAXReader reader = new SAXReader();
            Document doc = reader.read(ins);
            Element root = doc.getRootElement();
            Element beanNode;
            for (Iterator i = root.elementIterator("import"); i.hasNext(); ) {
                beanNode = (Element) i.next();
                Attribute resource = beanNode.attribute("resource");
                if (resource != null) {
                    initialize(CONTAINER_ROOT + resource.getText());
                }
            }
            initializeBeanDefition(root);
        } catch (Exception e) {
            System.out.println(e.toString());
            e.printStackTrace();
        }
    }

    private void initializeBeanDefition(Element root) throws Exception {
        Element beanNode;
        for (Iterator i = root.elementIterator("bean"); i.hasNext(); ) {
            BeanDefinition beanDef = new BeanDefinition();
            beanNode = (Element) i.next();
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
            beanDef.setPropertyValues(loadBeanProperty(beanNode));
            beanDefinitionMap.put(id.getText(), beanDef);
        }
    }

    private void initializeInstance() {
        for (Entry<String, BeanDefinition> entry : beanDefinitionMap.entrySet()) {
            BeanDefinition beanDef = entry.getValue();
            if (beanDef.isLazy() || !beanDef.getScope().equals(Scope.SINGLETON)) {
                continue;
            }
            singletonObjects.put(entry.getKey(), createInstance(beanDef));
        }
    }

    private List<PropertyValue> loadBeanProperty(Element beanNode) throws Exception {
        List<PropertyValue> propValueList = new ArrayList<PropertyValue>();
        for (Iterator ite = beanNode.elementIterator("property"); ite.hasNext(); ) {
            Element beanPro = (Element) ite.next();
            Attribute name = beanPro.attribute("name");

            Object proVal = parsePropertyValue(beanPro);
            PropertyValue propValue = new PropertyValue(name.getText(), proVal);
            propValueList.add(propValue);
        }
        return propValueList;
    }

    private Object parsePropertyValue(Element element) {
        Attribute ref = element.attribute("ref");
        Element list = element.element("list");
        Element map = element.element("map");
        if (ref != null) {
            return singletonObjects.get(ref.getValue());
        } else if (list != null) {
            return paarseListObject(list);
        } else if (map != null) {
            return parseMapObject(map);
        } else {
            for (Iterator ite1 = element.elementIterator("value"); ite1.hasNext(); ) {
                Element node = (Element) ite1.next();
                return node.getText();
            }
            return null;
        }
    }

    private List paarseListObject(Element list) {
        List proList = new ArrayList();
        for (Iterator<Element> listElementIte = list.elementIterator("value"); listElementIte.hasNext(); ) {
            proList.add(listElementIte.next().getText());
        }
        for (Iterator<Element> listElementIte = list.elementIterator("ref"); listElementIte.hasNext(); ) {
            proList.add(beanDefinitionMap.get(listElementIte.next().getText()));
        }
        return proList;
    }

    private Map parseMapObject(Element map) {
        Map proMap = new HashMap();
        for (Iterator ite1 = map.elementIterator("entry"); ite1.hasNext(); ) {
            Element node = (Element) ite1.next();
            String key = node.attribute("key").getText();
            if (node.attribute("value") != null) {
                proMap.put(key, node.attribute("value").getText());
            }
            if (node.attribute("ref") != null) {
                proMap.put(key, beanDefinitionMap.get(node.attribute("ref").getText()));
            }
        }
        return proMap;
    }

    private Object createInstance(BeanDefinition beanDef) {
        Object obj;
        try {
            obj = beanDef.getBeanClass().newInstance();
            retrieveBeanProperty(beanDef.getBeanClass(), obj, beanDef);
            return obj;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private void retrieveBeanProperty(Class beanClass, Object obj, BeanDefinition beanDef) throws Exception {
        Method method;
        java.beans.BeanInfo beanInfo = java.beans.Introspector.getBeanInfo(beanClass);
        java.beans.PropertyDescriptor proDescriptor[] = beanInfo.getPropertyDescriptors();
        for (PropertyValue propVal : beanDef.getPropertyValues()) {
            for (int k = 0; k < proDescriptor.length; k++) {
                if (proDescriptor[k].getName().equalsIgnoreCase(propVal.getName())) {
                    Object value = createPropertyInstance(propVal.getValue());
                    method = proDescriptor[k].getWriteMethod();
                    method.invoke(obj, value);
                }
            }
        }
    }

    private Object createPropertyInstance(Object propVal) {
        if (propVal instanceof BeanDefinition) {
            Object obj = createRefInstance((BeanDefinition) propVal);
            return obj;
        }
        if (propVal instanceof List) {
            List list = new ArrayList();
            for (Object value : (List) propVal) {
                Object obj = value;
                if (value instanceof BeanDefinition) {
                    obj = createRefInstance((BeanDefinition) value);
                }
                list.add(obj);
            }
            return list;
        }
        if (propVal instanceof Map) {
            Map map = new HashMap();
            for (Object value : ((Map) propVal).entrySet()) {
                Object mapValue = ((Entry) value).getValue();
                Object obj = mapValue;
                if (mapValue instanceof BeanDefinition) {
                    obj = createRefInstance((BeanDefinition) mapValue);
                }
                map.put(((Entry) value).getKey(), obj);
            }
            return map;
        }
        return propVal;
    }

    private Object createRefInstance(BeanDefinition propVal) {
        Object obj = singletonObjects.get(propVal.getId());
        if (obj == null) {
            obj = createInstance(propVal);
        }
        return obj;
    }

    public Object getBean(String beanName) {
        Object obj = singletonObjects.get(beanName);
        if (obj != null) {
            return obj;
        }
        BeanDefinition beanDef = beanDefinitionMap.get(beanName);
        if (beanDef != null) {
            obj = createInstance(beanDef);
            if (beanDef.isLazy() && beanDef.getScope().equals(Scope.SINGLETON)) {
                singletonObjects.put(beanName, obj);
            }
        }
        return null;
    }

}
