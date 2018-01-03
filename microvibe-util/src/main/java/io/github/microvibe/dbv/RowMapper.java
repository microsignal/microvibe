package io.github.microvibe.dbv;

import java.sql.ResultSet;

public interface RowMapper<T> {
	T rowToObject(ResultSet rs) throws DbvException;
}
