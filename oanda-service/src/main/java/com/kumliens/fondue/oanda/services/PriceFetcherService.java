package com.kumliens.fondue.oanda.services;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.codahale.metrics.annotation.Timed;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.google.common.util.concurrent.AbstractScheduledService;
import com.kumliens.fondue.oanda.DataFetcherConfiguration;
import com.kumliens.fondue.oanda.events.NewPriceAvailableEvent;
import com.kumliens.fondue.oanda.events.PausePriceFetcherServiceEvent;
import com.kumliens.fondue.oanda.events.ResumePriceFetcherServiceEvent;
import com.kumliens.fondue.oanda.guice.OandaPriceResource;
import com.kumliens.fondue.oanda.representation.Instrument;
import com.kumliens.fondue.oanda.responses.Price;
import com.kumliens.fondue.oanda.responses.PriceListResponse;
import com.rabbitmq.client.ConnectionFactory;
import com.sun.jersey.api.client.WebResource;

public class PriceFetcherService extends AbstractScheduledService {

    private static final Logger logger = LoggerFactory.getLogger(PriceFetcherService.class);

    private final String instrumentList;

    private final long interval;

    @Inject
    @OandaPriceResource
    private WebResource priceResource;
    
    private final EventBus eventBus;

    private boolean isPaused = false;

    @Inject
    public PriceFetcherService(final ConnectionFactory connectionFactory, final DataFetcherConfiguration config, final EventBus eventBus) {
        try {
        	this.eventBus = eventBus;
            eventBus.register(this);
            this.instrumentList = Instrument.asURLEncodedCommaSeparatedList();
        } catch (final UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
        this.interval = config.interval;
        logger.info("Price service created with interval set to " + this.interval + " millis");
    }

    @Subscribe
    public void onPause(final PausePriceFetcherServiceEvent ppfs) {
        this.isPaused = true;
    }

    @Subscribe
    public void onResume(final ResumePriceFetcherServiceEvent rpfs) {
        this.isPaused = false;
    }

    @Override
    @Timed
    protected void runOneIteration() throws Exception {
        if (this.isPaused) {
            logger.info("We are paused...");
            return;
        }
        logger.info("Fetching prices with list " + this.instrumentList);
        try {
            final PriceListResponse priceList = this.priceResource.queryParam("instruments", this.instrumentList).get(PriceListResponse.class);
            logger.info("Got prices: " + priceList);
            //todo post to the eventbus to get picked up by the rabbit gateway
            for(Price price : priceList.getPrices()) {
            	eventBus.post(new NewPriceAvailableEvent(price));
            }

        } catch (final Exception e) {
            logger.error("Error fetching prices...", e);
        }
    }

    @Override
    protected Scheduler scheduler() {
        return Scheduler.newFixedRateSchedule(2500, this.interval, TimeUnit.MILLISECONDS);
    }

}
