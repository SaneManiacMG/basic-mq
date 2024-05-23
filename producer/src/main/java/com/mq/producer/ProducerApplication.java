package com.mq.producer;

import java.time.LocalTime;
import java.util.Random;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;

@SpringBootApplication
public class ProducerApplication {
	private final static String QUEUE_NAME = "hello";

	public static void main(String[] args) throws Exception {
		SpringApplication.run(ProducerApplication.class, args);

		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");

		Random random = new Random();

		try (Connection connection = factory.newConnection();
				Channel channel = connection.createChannel()) {
			channel.queueDeclare(QUEUE_NAME, false, false, false, null);

			for (int i = 0; i < 50; i++) {
				String message = Integer.toString(random.nextInt(999));

				channel.basicPublish("", QUEUE_NAME, null, message.getBytes());

				System.out.println(" [x] \t'%s' \tSENT: \t'%s'".formatted(LocalTime.now().toString(), message));

				//Thread.sleep(5000);
			}
		}

	}
}
