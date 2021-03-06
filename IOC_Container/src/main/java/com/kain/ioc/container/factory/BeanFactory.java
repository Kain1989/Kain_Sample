package com.kain.ioc.container.factory;

import com.kain.ioc.container.model.BeanDefinition;
import com.kain.ioc.container.model.PropertyValue;
import com.kain.ioc.container.model.Scope;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created on 10/10/2016.
 */
public class BeanFactory {


    private final Map<String, Object> singletonObjects = new ConcurrentHashMap<String, Object>();

    private Map<String, BeanDefinition> beanDefinitionMap;

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

    private void initialize() {
        beanDefinitionMap = new BeanDefinitionLoader().load();
        initializeInstance();
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

    private Object createInstance(BeanDefinition beanDef) {
        Object obj;
        try {
            obj = beanDef.getBeanClass().newInstance();
            retrieveBeanProperty(obj, beanDef);
            return obj;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private void retrieveBeanProperty(Object obj, BeanDefinition beanDef) throws Exception {
        for (PropertyValue propVal : beanDef.getPropertyValues()) {
            Field field = getDeclaredField(obj.getClass(), propVal.getName());
            Object value = createPropertyInstance(propVal.getValue());
            field.setAccessible(true);
            field.set(obj, value);
        }
    }

    private Field getDeclaredField(Class clasz, String filedName) {
        try {
            return clasz.getDeclaredField(filedName);
        } catch (NoSuchFieldException e) {
            if (clasz.getGenericSuperclass() != null) {
                return getDeclaredField(clasz.getSuperclass(), filedName);
            }
        }
        return null;
    }

    private Object createPropertyInstance(Object propVal) {
        if (propVal instanceof BeanDefinition) {
            return createRefInstance((BeanDefinition) propVal);
        }
        if (propVal instanceof List) {
            return createListInstance(propVal);
        }
        if (propVal instanceof Map) {
            return createMapInstance(propVal);
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

    private Object createListInstance(Object propVal) {
        List<Object> list = new ArrayList<Object>();
        for (Object value : (List) propVal) {
            Object obj = value;
            if (value instanceof BeanDefinition) {
                obj = createRefInstance((BeanDefinition) value);
            }
            list.add(obj);
        }
        return list;
    }

    private Object createMapInstance(Object propVal) {
        Map<Object, Object> map = new HashMap<Object, Object>();
        for (Object value : ((Map) propVal).entrySet()) {
            Object mapValue = ((Entry) value).getValue();
            Object obj = mapValue;
            if (mapValue instanceof BeanDefinition) {
                obj = createRefInstance((BeanDefinition) mapValue);
            }
            if (mapValue instanceof List) {
                obj = createListInstance(mapValue);
            }
            map.put(((Entry) value).getKey(), obj);
        }
        return map;
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
