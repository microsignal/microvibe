package io.github.microvibe.example.mqtt;

import org.fusesource.hawtbuf.Buffer;
import org.fusesource.mqtt.client.Future;
import org.fusesource.mqtt.client.FutureConnection;
import org.fusesource.mqtt.client.MQTT;
import org.fusesource.mqtt.client.Message;
import org.fusesource.mqtt.client.Promise;
import org.fusesource.mqtt.client.QoS;
import org.fusesource.mqtt.client.Topic;

public class FutureApiTest extends MQTTSupport {
	public void testInterface() throws Exception {
		final Promise<Buffer> result = new Promise<Buffer>();

		MQTT mqtt = createMQTT();

		FutureConnection connection = mqtt.futureConnection();
		Future<Void> f1 = connection.connect();
		f1.await();

		Future<byte[]> f2 = connection.subscribe(new Topic[] { new Topic(("foo"), QoS.AT_LEAST_ONCE) });
		byte[] qoses = f2.await();

		// We can start future receive..
		Future<Message> receive = connection.receive();

		// send the message..
		connection.publish("foo", "Hello".getBytes(), QoS.AT_LEAST_ONCE, false);

		// Then the receive will get the message.
		Message message = receive.await();
		assertEquals("Hello", new String(message.getPayload()));

		// To let the server know that it has been processed.
		message.ack();

		connection.disconnect().await();

	}
}
