package io.github.microvibe.example.mqtt;

import org.fusesource.mqtt.client.BlockingConnection;
import org.fusesource.mqtt.client.MQTT;
import org.fusesource.mqtt.client.Message;
import org.fusesource.mqtt.client.QoS;
import org.fusesource.mqtt.client.Topic;
import org.fusesource.mqtt.client.Tracer;
import org.fusesource.mqtt.codec.MQTTFrame;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.*;

import java.util.concurrent.TimeUnit;

import lombok.extern.slf4j.Slf4j;

public class BlockingApiTest extends MQTTSupport {

	private static final Logger LOG = LoggerFactory.getLogger(BlockingApiTest.class);

	@Test
	public void testInterface() throws Exception {
		MQTT mqtt = createMQTT();

		BlockingConnection connection = mqtt.blockingConnection();
		connection.connect();

		Topic[] topics = { new Topic(("foo"), QoS.AT_LEAST_ONCE) };
		byte[] qoses = connection.subscribe(topics);

		connection.publish("foo", "Hello".getBytes(), QoS.AT_LEAST_ONCE, false);
		Message message = connection.receive();
		System.out.println(new String(message.getPayload()));
		Assert.assertEquals("Hello", new String(message.getPayload()));

		// To let the server know that it has been processed.
		message.ack();

		connection.disconnect();
	}

	@Test
	public void testInvalidClientId() throws Exception {
		MQTT mqtt = createMQTT();
		mqtt.setVersion("3.1.1");
		mqtt.setCleanSession(false);
		mqtt.setClientId((String) null);

		try {
			mqtt.blockingConnection();
			fail("Expected IllegalArgumentException");
		} catch (IllegalArgumentException e) {
		} catch (Throwable e) {
			fail("Unexpected exception: " + e);
		}

		// also test "" client id
		mqtt.setClientId("");
		try {
			mqtt.blockingConnection();
			fail("Expected IllegalArgumentException");
		} catch (IllegalArgumentException e) {
		} catch (Throwable e) {
			fail("Unexpected exception: " + e);
		}

	}

	@Test
	public void testReceiveTimeout() throws Exception {
		MQTT mqtt = createMQTT();
		System.out.println(LOG.isInfoEnabled());
		mqtt.setTracer(new Tracer() {
			@Override
			public void onReceive(MQTTFrame frame) {
				LOG.info("Client Received:\n" + frame);
			}

			@Override
			public void onSend(MQTTFrame frame) {
				LOG.info("Client Sent:\n" + frame);
			}

			@Override
			public void debug(String message, Object... args) {
				LOG.info(String.format(message, args));
			}
		});
		BlockingConnection connection = mqtt.blockingConnection();
		connection.connect();

		Topic[] topics = { new Topic(("foo"), QoS.AT_LEAST_ONCE) };
		byte[] qoses = connection.subscribe(topics);

		// force a receive timeout
		Message message = connection.receive(1000, TimeUnit.MILLISECONDS);
		assertNull(message);

		connection.publish("foo", "Hello".getBytes(), QoS.AT_LEAST_ONCE, false);
		message = connection.receive(5000, TimeUnit.MILLISECONDS);
		assertNotNull(message);
		assertEquals("Hello", new String(message.getPayload()));

		// To let the server know that it has been processed.
		message.ack();

		connection.disconnect();
	}
}
