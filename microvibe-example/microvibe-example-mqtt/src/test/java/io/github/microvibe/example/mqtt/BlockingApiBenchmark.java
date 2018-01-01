package io.github.microvibe.example.mqtt;

import java.net.URISyntaxException;
import java.net.URL;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

import org.fusesource.mqtt.client.BlockingConnection;
import org.fusesource.mqtt.client.MQTT;
import org.fusesource.mqtt.client.QoS;
import org.fusesource.mqtt.client.Topic;


public class BlockingApiBenchmark {
	
	private static MQTT createMQTT() throws URISyntaxException {
		MQTT mqtt = new MQTT();
		mqtt.setConnectAttemptsMax(0);
		mqtt.setReconnectAttemptsMax(0);
		mqtt.setHost("tcp://localhost:61613");
		mqtt.setUserName("admin");
		mqtt.setPassword("password");
		return mqtt;
	}

	static public void main(String[] args) throws Exception {
		MQTT mqtt = createMQTT();

		final BlockingConnection publishConnection = mqtt.blockingConnection();
		publishConnection.connect();

		final BlockingConnection subscribeConnection = mqtt.blockingConnection();
		subscribeConnection.connect();
		subscribeConnection.setReceiveBuffer(1024 * 64);

		Topic[] topic = { new Topic(("foo"), QoS.EXACTLY_ONCE) };
		byte[] qoses = subscribeConnection.subscribe(topic);

		final long start = System.currentTimeMillis();
		final AtomicLong sendCounter = new AtomicLong();
		final AtomicLong receiveCounter = new AtomicLong();

		Thread receiver = new Thread("receiver") {
			@Override
			public void run() {
				try {
					while (true) {
						if (System.currentTimeMillis() > start + TimeUnit.SECONDS.toMillis(120)) {
							break;
						}
						Thread.sleep(10);
						subscribeConnection.receive().ack();
						receiveCounter.incrementAndGet();
					}

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};
		receiver.start();

		Thread sender = new Thread("sender") {
			@Override
			public void run() {
				try {
					while (true) {
						if (System.currentTimeMillis() > start + TimeUnit.SECONDS.toMillis(120)) {
							break;
						}
						publishConnection.publish("foo", new byte[1024], QoS.EXACTLY_ONCE, false);
						sendCounter.incrementAndGet();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};
		sender.start();

		while (true) {
			if (System.currentTimeMillis() > start + TimeUnit.SECONDS.toMillis(120)) {
				break;
			}
			Thread.sleep(1000);
			System.out.println("Sent: " + sendCounter.get() + ", Received: " + receiveCounter.get());
		}

		publishConnection.disconnect();
		subscribeConnection.disconnect();
		receiver.join();
		sender.join();

	}
}
