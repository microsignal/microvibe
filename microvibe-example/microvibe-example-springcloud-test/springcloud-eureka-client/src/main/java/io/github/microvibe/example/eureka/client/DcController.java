package io.github.microvibe.example.eureka.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DcController {

	@Autowired
	DiscoveryClient discoveryClient;

	@GetMapping("/dc")
	public String dc() {
		String services = "Services: " + discoveryClient.getServices();
		System.out.println(services);
		return services;
	}

	@GetMapping("/dc/hystrix")
	public String dc_hystrix() throws InterruptedException {
		Thread.sleep(5000L);
		String services = "Services: " + discoveryClient.getServices();
		System.out.println(services);
		return services;
	}
	@GetMapping("/info")
	public String info() {
		return "UP";
	}

	@GetMapping("/health")
	public String health() {
		String services = "Services: " + discoveryClient.getServices();
		System.out.println(services);
		return services;
	}
}
