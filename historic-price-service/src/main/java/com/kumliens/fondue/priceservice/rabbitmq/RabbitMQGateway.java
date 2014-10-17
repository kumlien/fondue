package com.kumliens.fondue.priceservice.rabbitmq;

import io.dropwizard.lifecycle.Managed;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.google.common.eventbus.EventBus;
import com.google.inject.Inject;
import com.kumliens.fondue.priceservice.PriceserviceConfiguration;
import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

/**
 * 
 * 
 * @author svante
 */
public class RabbitMQGateway implements Managed {

	private static final Logger logger = LoggerFactory.getLogger(RabbitMQGateway.class);


	private final ConnectionFactory cf;

	private final EventBus eventBus;

	private Connection connection;

	private Channel channel;

	private final String queueName;

	private final String exchangeName;

	private final String routingKey;

	@Inject
	public RabbitMQGateway(ConnectionFactory cf, EventBus eventBus,
			PriceserviceConfiguration conf) {
		this.cf = cf;
		this.eventBus = eventBus;
		queueName = conf.getAmqp().getQueue();
		exchangeName = conf.getAmqp().getExchange();
		routingKey = conf.getAmqp().getRoutingKey();
		
		Preconditions.checkArgument(!Strings.isNullOrEmpty(exchangeName), "Exchange name is null or emtpy");
		Preconditions.checkArgument(!Strings.isNullOrEmpty(queueName), "Queue name is null or emtpy");
	}

	@Override
	public void start() throws Exception {
		// Set up the queues and exchanges
		connection = cf.newConnection();
		channel = connection.createChannel();
		channel.exchangeDeclare(exchangeName, "topic");
		channel.queueDeclare(queueName, false, true, true, null);
		channel.queueBind(queueName, exchangeName, routingKey);
		channel.basicConsume(queueName, true, new DefaultConsumer(channel) {

			@Override
			public void handleDelivery(String consumerTag, Envelope envelope, BasicProperties properties, byte[] body) throws IOException {
				String routingKey = envelope.getRoutingKey();
				String contentType = properties.getContentType();
				long deliveryTag = envelope.getDeliveryTag();
				String bodyString = new String(body);
				logger.info("New message received: {}", bodyString);
			}

		});
		eventBus.register(this);
		logger.info("Started..." + this.cf);
	}

	@Override
	public void stop() throws Exception {
		channel.close();
		connection.close();
		logger.info("Stopped...");
	}

}
