package io.github.microvibe.codegen.bean;

import java.util.Map;

import io.github.microvibe.util.castor.annotation.XComplexKey;
import io.github.microvibe.util.castor.annotation.XComplexType;
import io.github.microvibe.util.castor.annotation.XComplexValue;
import io.github.microvibe.util.castor.annotation.XName;

public class Property {
	String key;
	String value;

	@XName("entry")
	@XComplexType(Property.class)
	@XComplexKey("key")
	@XComplexValue("value")
	private Map<String, String> properties;

	public Map<String, String> getProperties() {
		return properties;
	}

}
