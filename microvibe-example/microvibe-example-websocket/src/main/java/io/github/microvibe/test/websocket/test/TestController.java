package io.github.microvibe.test.websocket.test;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

	@RequestMapping
	public Object exec(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<>();
		map.put("test", "true");
		return map;
	}
}
