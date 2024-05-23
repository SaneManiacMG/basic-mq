package com.mq.receiver;

import java.time.LocalDate;
import java.time.LocalTime;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

@SpringBootApplication
public class ReceiverApplication {

	private final static String QUEUE_NAME = "hello";

	public static void main(String[] args) throws Exception {
		SpringApplication.run(ReceiverApplication.class, args);

		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();

		channel.queueDeclare(QUEUE_NAME, false, false, false, null);
		System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

		DeliverCallback deliverCallback = (consumerTag, delivery) -> {
			String message = new String(delivery.getBody(), "UTF-8");
			System.out.println(" [x] \t'%s' \tReceived: \t'%s'".formatted(LocalTime.now(), message));
		};

		channel.basicConsume(QUEUE_NAME, true, deliverCallback, consumer -> {});
	}

}
