package io.github.microvibe.dbv.model;

import io.github.microvibe.dbv.annotation.ColumnName;

public class TableType {

	/*****************************************
	 * 以下jdbc元数据主要字段
	 *****************************************/
	@ColumnName("TABLE_TYPE")
	private String value;

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "TableType [value=" + value + "]";
	}

}
