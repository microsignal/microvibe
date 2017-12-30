package io.github.microvibe.test.lobmok;

import java.io.UnsupportedEncodingException;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.SneakyThrows;
import lombok.experimental.Delegate;
import lombok.experimental.UtilityClass;
import lombok.experimental.Wither;
import lombok.extern.slf4j.Slf4j;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
//@Value
@Slf4j
public class DataExample {

	@NonNull
	@Wither
	private String name;
	@Wither
	private int age;
	@Wither
	private double score;
	@Wither
	private String[] tags;
	@Delegate
	private List<String> foo;

	@SneakyThrows(UnsupportedEncodingException.class)
	public byte[] nameBytes() {
		log.info("");
		return name.getBytes("utf-8");
	}

	@UtilityClass
	static class Tool {
	}
}
