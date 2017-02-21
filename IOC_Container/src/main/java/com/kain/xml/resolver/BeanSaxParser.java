package com.kain.xml.resolver;

import com.kain.ioc.container.factory.BeanDefinitionSaxLoader;
import com.kain.ioc.container.model.BeanDefinition;
import com.kain.ioc.container.model.PropertyValue;
import com.kain.ioc.container.model.Region;
import com.kain.ioc.container.model.Scope;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.xml.sax.Attributes;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created on 2/21/2017.
 */
public class BeanSaxParser extends DefaultHandler {

    private Map<String, BeanDefinition> beanMap;

    private BeanDefinition currentBean;

    private List<PropertyValue> propValueList;

    private PropertyValue currentPropValue;

    private List currentList;

    private Map currentMap;

    private String currentKey;

    private Set<Region> includeRegionSet;

    private Set<Region> excludeRegionSet;

    private Locator locator;

    private String currentTag;


    @Override
    public void setDocumentLocator(Locator locator) {
        this.locator = locator;
    }


    @Override
    public void startDocument() throws SAXException {
        beanMap = new LinkedHashMap<String, BeanDefinition>();
        currentTag = null;

    }

    @Override
    public void startElement(String uri, String localName, String qName,
                             Attributes attributes) throws SAXException {
        if ("import".equals(qName)) {
            startImportElement(attributes);
        } else if ("bean".equals(qName)) {
            startbeanElement(attributes);
        } else if ("property".equals(qName)) {
            startPropertyElement(attributes);
        } else if ("list".equals(qName)) {
            startListElement();
        } else if ("map".equals(qName)) {
            startMapElement();
        } else if ("entry".equals(qName)) {
            startEntryElement(attributes);
        } else if ("ref".equals(qName)) {
            startRefElement(attributes);
        }
        currentTag = qName;
    }

    private void startImportElement(Attributes attributes) {
        String resource = attributes.getValue("resource");
        beanMap.putAll(BeanDefinitionSaxLoader.load(resource));
    }

    private void startbeanElement(Attributes attributes) {
        currentBean = new BeanDefinition();
        currentBean.setId(attributes.getValue("id"));
        currentBean.setBeanClassName(attributes.getValue("class"));
        String lazy = attributes.getValue("lazy");
        if (StringUtils.isNotBlank(lazy)) {
            currentBean.setLazy(Boolean.getBoolean(lazy));
        }
        String scope = attributes.getValue("scope");
        if (StringUtils.isNotBlank(scope)) {
            currentBean.setScope(Scope.parse(scope));
        }
        if (propValueList == null) {
            propValueList = new ArrayList<PropertyValue>();
        }
    }

    private void startPropertyElement(Attributes attributes) {
        if (propValueList == null) {
            propValueList = new ArrayList<PropertyValue>();
        }
        String name = attributes.getValue("name");
        currentPropValue = new PropertyValue(name);
    }

    private void startListElement() {
        currentList = new ArrayList();
    }

    private void startMapElement() {
        currentMap = new HashMap();
    }

    private void startEntryElement(Attributes attributes) {
        String key = attributes.getValue("key");
        String value = attributes.getValue("value");
        String ref = attributes.getValue("ref");
        if (StringUtils.isNotBlank(value)) {
            currentMap.put(key, value);
        } else if (StringUtils.isNotBlank(ref)) {
            currentMap.put(key, beanMap.get(ref));
        } else {
            currentKey = key;
        }
    }

    private void startRefElement(Attributes attributes) {
        String includeRegions = attributes.getValue("includeRegions");
        String excludeRegions = attributes.getValue("excludeRegions");
        includeRegionSet = this.getRegionSet(includeRegions);
        excludeRegionSet = this.getRegionSet(excludeRegions);
    }

    private Set<Region> getRegionSet(String regionStrArray) {
        if (StringUtils.isBlank(regionStrArray)) {
            return null;
        }
        Set<Region> regionSet = new HashSet<Region>();
        String regionsStr[] = regionStrArray.split(",");
        for (String regionStr : regionsStr) {
            regionSet.add(Region.parse(regionStr));
        }
        return regionSet;
    }

    @Override
    public void characters(char ch[], int start, int length)
            throws SAXException {
        if (StringUtils.isNotBlank(currentTag) && currentTag.equals("ref")) {
            String value = new String(ch, start, length);
            Object obj = beanMap.get(value);
            if (obj != null && currentList != null && this.include()) {
                currentList.add(obj);
            } else if (StringUtils.isNotBlank(value) && currentPropValue != null) {
                currentPropValue.setValue(obj);
            }
        }
        if (StringUtils.isNotBlank(currentTag) && currentTag.equals("value")) {
            String value = new String(ch, start, length);
            if (currentList != null) {
                currentList.add(value);
            } else if (currentPropValue != null) {
                currentPropValue.setValue(value);
            }
        }
    }

    private boolean include() {
        if (CollectionUtils.isEmpty(includeRegionSet) && CollectionUtils.isEmpty(excludeRegionSet)) {
            return true;
        }
        if (CollectionUtils.isNotEmpty(includeRegionSet) && includeRegionSet.contains(Region.getCurrentRegion())) {
            return true;
        }
        if (CollectionUtils.isNotEmpty(excludeRegionSet) && !excludeRegionSet.contains(Region.getCurrentRegion())) {
            return true;
        }
        return false;
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if ("bean".equals(qName)) {
            endBeanElement();
        } else if ("property".equals(qName)) {
            endPropertyElement();
        } else if ("ref".equals(qName)) {
            endRefElement();
        } else if ("map".equals(qName)) {
            endMapElement();
        } else if ("list".equals(qName)) {
            endListElement();
        }
        currentTag = null;
    }

    private void endBeanElement() {
        currentBean.setPropertyValues(propValueList);
        beanMap.put(currentBean.getId(), currentBean);
        currentBean = null;
        propValueList = null;
    }

    private void endPropertyElement() {
        propValueList.add(currentPropValue);
    }

    private void endRefElement() {
        includeRegionSet = null;
        excludeRegionSet = null;
    }

    private void endMapElement() {
        if (currentPropValue != null) {
            currentPropValue.setValue(currentMap);
        }
        currentMap = null;
    }

    private void endListElement() {
        if (currentMap != null && StringUtils.isNotBlank(currentKey)) {
            currentMap.put(currentKey, currentList);
        } else if (currentPropValue != null) {
            currentPropValue.setValue(currentList);
        }
        currentList = null;
    }

    @Override
    public void warning(SAXParseException e) throws SAXException {
        System.out.println("Warning occurred: \n");
        System.out.println("Location Info: " + locatorInfo());
        e.printStackTrace(System.out);
    }

    @Override
    public void error(SAXParseException e) throws SAXException {
        System.out.println("Error occurred: \n");
        System.out.println("Location Info: " + locatorInfo());
        e.printStackTrace(System.out);
    }

    @Override
    public void fatalError(SAXParseException e) throws SAXException {
        System.out.println("Fatal Error occurred: \n");
        System.out.println("Location Info: " + locatorInfo());
        e.printStackTrace(System.out);
        throw e;
    }

    private String locatorInfo() {
        return "resource: " + locator.getSystemId() + ", Locator Info: [" +
                locator.getLineNumber() + ", " + locator.getColumnNumber() + "]";
    }

    public Map<String, BeanDefinition> getBeanMap() {
        return beanMap;
    }

}
