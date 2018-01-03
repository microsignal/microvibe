package io.github.microvibe.codegen;

import io.github.microvibe.codegen.bean.db.Table;

public interface TablesReader {

	Table read(String catalogName, String schemaName, String tableName);

	void close();

}
