package io.github.microvibe.example.mqtt;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

import org.fusesource.mqtt.client.BlockingConnection;
import org.fusesource.mqtt.client.MQTT;
import org.fusesource.mqtt.client.QoS;
import org.fusesource.mqtt.client.Topic;

public class MQTTBlockingClient extends MQTTSupport {
	
	public static void main(String[] args) throws Exception {
		new MQTTBlockingClient().start();
	}

	public void start() throws Exception {

		MQTT mqtt = createMQTT();

		final BlockingConnection subscribeConnection = mqtt.blockingConnection();
		subscribeConnection.connect();
		subscribeConnection.setReceiveBuffer(1024 * 64);

		Topic[] topic = { new Topic(("foo"), QoS.EXACTLY_ONCE) };
		subscribeConnection.subscribe(topic);

		final long start = System.currentTimeMillis();
		final AtomicLong receiveCounter = new AtomicLong();

		Thread t = new Thread(() -> {
			try {
				while (true) {
					Thread.sleep(1000);
					System.out.println("Received: " + receiveCounter.get());
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		});
		t.setDaemon(true);
		t.start();

		while (true) {
			if (System.currentTimeMillis() > start + TimeUnit.SECONDS.toMillis(120)) {
				break;
			}
			Thread.sleep(10);
			subscribeConnection.receive().ack();
			receiveCounter.incrementAndGet();
		}
		subscribeConnection.disconnect();
	}
}
