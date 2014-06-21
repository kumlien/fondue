package com.kumliens.fondue.oanda.rabbitmq;

import java.io.IOException;

import io.dropwizard.lifecycle.Managed;

import org.slf4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.google.inject.Inject;
import com.kumliens.fondue.oanda.events.CanonicalPriceEvent;
import com.kumliens.fondue.oanda.events.NewPriceAvailableEvent;
import com.kumliens.fondue.oanda.responses.Price;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * 
 * 
 * @author svante
 */
public class RabbitMQGateway implements Managed {

    private static final Logger logger = org.slf4j.LoggerFactory.getLogger(RabbitMQGateway.class);
    
    private static final String ROUTING_KEY_PREFIX = "prices.oanda.fx.";

    @Inject
    private ConnectionFactory cf;
    
    @Inject
    private EventBus eventBus;
    
    private Connection connection;
    
    private Channel channel;

    @Override
    public void start() throws Exception {
        //Set up the queues and exchanges
        connection = cf.newConnection();
        channel = connection.createChannel();
        channel.exchangeDeclare("prices", "topic");
        eventBus.register(this);
        logger.info("Started..." + this.cf);
    }

    @Override
    public void stop() throws Exception {
    	channel.close();
    	connection.close();
        logger.info("Stopped...");
    }
    
    
    @Subscribe
    public void onNewPrice(final NewPriceAvailableEvent event) {
    	Price price = event.getPrice();
    	String routingKey = ROUTING_KEY_PREFIX + price.getInstrument().getCode();
    	CanonicalPriceEvent eventToPublish = new CanonicalPriceEvent.Builder().withOandaPrice(price).build();
    	ObjectMapper mapper = new ObjectMapper();
    	
        try {
			channel.basicPublish("prices", routingKey, null, mapper.writeValueAsString(eventToPublish).getBytes());
			logger.info("Price published");
		} catch (IOException e) {
			logger.error("Unable to publish new price message", e);
		}
    } 
    

}
