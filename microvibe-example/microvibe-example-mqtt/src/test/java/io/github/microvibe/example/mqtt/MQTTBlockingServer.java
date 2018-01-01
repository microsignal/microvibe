package io.github.microvibe.example.mqtt;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

import org.fusesource.mqtt.client.BlockingConnection;
import org.fusesource.mqtt.client.MQTT;
import org.fusesource.mqtt.client.QoS;

public class MQTTBlockingServer extends MQTTSupport {

	public static void main(String[] args) throws Exception {
		new MQTTBlockingServer().start();
	}

	public void start() throws Exception {

		MQTT mqtt = createMQTT();
		final BlockingConnection publishConnection = mqtt.blockingConnection();
		publishConnection.connect();
		final long start = System.currentTimeMillis();
		final AtomicLong sendCounter = new AtomicLong();

		Thread t = new Thread(() -> {
			try {
				while (true) {
					Thread.sleep(1000);
					System.out.println("Sent: " + sendCounter.get());
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
			publishConnection.publish("foo", new byte[1024], QoS.EXACTLY_ONCE, false);
			sendCounter.incrementAndGet();
		}

		publishConnection.disconnect();
	}
}
