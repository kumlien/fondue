package com.kumliens.fondue.oanda.datafetcher.rabbitmq;

import io.dropwizard.lifecycle.Managed;

import org.slf4j.Logger;

import com.google.inject.Inject;
import com.rabbitmq.client.ConnectionFactory;

public class RabbitMQGateway implements Managed {

    private static final Logger logger = org.slf4j.LoggerFactory.getLogger(RabbitMQGateway.class);

    @Inject
    private ConnectionFactory cf;

    @Override
    public void start() throws Exception {
        //Set up the queues and exchanges
        logger.info("Started..." + this.cf);
    }

    @Override
    public void stop() throws Exception {
        //close resources
        logger.info("Stopped...");
    }

}
