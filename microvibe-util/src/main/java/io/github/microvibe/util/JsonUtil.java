package io.github.microvibe.util;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;

public class JsonUtil {

	private JsonUtil() {
	}

	public static String object2Json(Object o) {
		return JsonConverter.getInstance().object2Json(o);
	}

	public static <T> T json2Object(Class<T> type, String json) {
		return json2Object(type, null, json);
	}

	public static <T> T json2Object(Class<T> type, String field, String json) {
		T rs = null;
		JsonConverter jsonConverter = JsonConverter.getInstance();
		if (field == null) {
			rs = jsonConverter.json2Object(type, json);
		} else {
			JsonNode root = jsonConverter.json2JsonNode(json);
			rs = jsonConverter.json2Object(type, root.get(field));
		}
		return rs;
	}

	public static Map<?, ?> json2Map(String json) {
		return (Map<?, ?>) json2Object(java.util.HashMap.class, json);
	}

	public static Map<?, ?> json2Map(String field, String json) {
		return (Map<?, ?>) json2Object(java.util.HashMap.class, field, json);
	}

	public static List<?> json2List(Class<?> type, String json) {
		return json2List(type, null, json);
	}

	public static List<?> json2List(Class<?> type, String field, String json) {
		List<?> rs = null;
		JsonConverter jsonConverter = JsonConverter.getInstance();
		if (field == null) {
			rs = jsonConverter.json2List(type, json);
		} else {
			JsonNode root = jsonConverter.json2JsonNode(json);
			rs = jsonConverter.json2List(type, root.get(field));
		}
		return rs;
	}

	public static List<?> json2MapList(String field, String json) {
		return json2List(java.util.HashMap.class, field, json);
	}

	public static List<?> json2MapList(String json) {
		return json2List(java.util.HashMap.class, json);
	}

	public static JsonNode json2JsonNode(String json) {
		return JsonConverter.getInstance().json2JsonNode(json);
	}

}
