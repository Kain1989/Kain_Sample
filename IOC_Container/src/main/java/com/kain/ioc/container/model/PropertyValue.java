package com.kain.ioc.container.model;

public class PropertyValue {
	private final String name;
	private Object value;

	public PropertyValue(String name) {
		this.name = name;
	}
	
	public PropertyValue(String name, Object value) {
		this.name = name;
		this.value = value;
	}
	
	public String getName() {
		return name;
	}
	
	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}
}
