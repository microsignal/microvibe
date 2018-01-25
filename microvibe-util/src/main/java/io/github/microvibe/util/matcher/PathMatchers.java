package io.github.microvibe.util.matcher;

import java.util.regex.Pattern;

import lombok.RequiredArgsConstructor;

public class PathMatchers {

	public static final char PATH_SEPERATOR = '/';
	public static final String PATH_UNIT = "[^/]*";

	static final PathMatcher defaultPathMatcher = new DefaultPathMatcher(PATH_SEPERATOR, PATH_UNIT);

	@RequiredArgsConstructor
	static class DefaultPathMatcher implements PathMatcher {

		final char pathSeperator;
		final String pathUnit;

		@Override
		public boolean match(String pattern, String path) {
			pattern = toCanonicalPattern(pattern);
			path = toCanonicalPath(path);
			pattern = "^" + pattern + "$";
			boolean matches = Pattern.compile(pattern).matcher(path).find();
			return matches;
		}

		@Override
		public boolean matchStart(String pattern, String path) {
			pattern = toCanonicalPattern(pattern);
			path = toCanonicalPath(path);
			pattern = "^" + pattern;
			boolean matches = Pattern.compile(pattern).matcher(path).find();
			return matches;
		}

		String toCanonicalPattern(String pattern) {
			pattern = pattern.replaceAll("(" + Pattern.quote(String.valueOf(pathSeperator)) + ")+$", "") + pathSeperator;// 结尾分隔符标准化
			pattern = pattern.replace(pathSeperator + "**", "(" + pathSeperator + pathUnit + ")*");
			pattern = pattern.replace("*", pathUnit);
			return pattern;
		}

		String toCanonicalPath(String path) {
			return path.replaceAll("(" + Pattern.quote(String.valueOf(pathSeperator)) + ")+$", "") + pathSeperator;// 结尾分隔符标准化
		}
	}

	public static PathMatcher buildPathMatcher(char pathSeperator, String pathUnit) {
		return new DefaultPathMatcher(pathSeperator, pathUnit);
	}

	public static PathMatcher buildPathMatcher(char pathSeperator) {
		String pathUnit = "[^\\" + pathSeperator + "]*";
		return new DefaultPathMatcher(pathSeperator, pathUnit);
	}

	public static PathMatcher getDefaultPathMatcher() {
		return defaultPathMatcher;
	}

	public static boolean match(String pattern, String path) {
		return defaultPathMatcher.match(pattern, path);
	}

	public static boolean matchStart(String pattern, String path) {
		return defaultPathMatcher.matchStart(pattern, path);
	}

}
