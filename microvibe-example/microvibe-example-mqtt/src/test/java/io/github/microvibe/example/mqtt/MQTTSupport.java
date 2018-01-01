package io.github.microvibe.example.mqtt;

import java.net.URISyntaxException;

import org.fusesource.mqtt.client.MQTT;

import junit.framework.TestCase;

public class MQTTSupport extends TestCase{

	protected MQTT createMQTT() throws URISyntaxException {
		MQTT mqtt = new MQTT();
		mqtt.setConnectAttemptsMax(0);
		mqtt.setReconnectAttemptsMax(0);
		mqtt.setHost("tcp://localhost:61613");
		mqtt.setUserName("admin");
		mqtt.setPassword("password");
		return mqtt;
	}

	
	
}
