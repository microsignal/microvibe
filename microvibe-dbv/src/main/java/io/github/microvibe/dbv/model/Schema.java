package io.github.microvibe.dbv.model;

import io.github.microvibe.dbv.annotation.ColumnName;

public class Schema {

	@ColumnName("TABLE_SCHEM")
	private String value;

	// @ColumnName("TABLE_CATALOG")
	// public String catalog;

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "Schema [value=" + value + "]";
	}

}
