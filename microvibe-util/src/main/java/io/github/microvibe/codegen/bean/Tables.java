package io.github.microvibe.codegen.bean;

import java.util.Map;

import io.github.microvibe.codegen.bean.db.Catalog;
import io.github.microvibe.util.castor.annotation.XComplexKey;
import io.github.microvibe.util.castor.annotation.XComplexType;
import io.github.microvibe.util.castor.annotation.XName;
import io.github.microvibe.util.castor.annotation.XRootName;
import io.github.microvibe.util.collection.IgnoreCaseHashMap;

@XRootName("tables")
public class Tables  {

	@XName("catalog")
	@XComplexType(Catalog.class)
	@XComplexKey("name")
	Map<String, Catalog> catalogs = new IgnoreCaseHashMap<String, Catalog>();

	public Map<String, Catalog> getCatalogs() {
		return catalogs;
	}

	public void setCatalogs(Map<String, Catalog> catalogs) {
		this.catalogs = catalogs;
	}
}
