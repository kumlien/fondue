package com.kumliens.fondue.oanda.services;

import java.io.UnsupportedEncodingException;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.codahale.metrics.annotation.Timed;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.google.common.util.concurrent.AbstractScheduledService;
import com.kumliens.fondue.oanda.OandaServiceConfiguration;
import com.kumliens.fondue.oanda.events.NewPriceAvailableEvent;
import com.kumliens.fondue.oanda.events.PausePriceFetcherServiceEvent;
import com.kumliens.fondue.oanda.events.ResumePriceFetcherServiceEvent;
import com.kumliens.fondue.oanda.guice.OandaPriceResource;
import com.kumliens.fondue.oanda.representation.Instrument;
import com.kumliens.fondue.oanda.responses.Price;
import com.kumliens.fondue.oanda.responses.PriceListResponse;
import com.rabbitmq.client.ConnectionFactory;
import com.sun.jersey.api.client.WebResource;

/**
 * Service for fetching prices using a {@link WebResource} defined as a guice guy.
 * 
 * @author svante
 */
public class PriceFetcherService extends AbstractScheduledService {

    private static final Logger logger = LoggerFactory.getLogger(PriceFetcherService.class);

    private final String instrumentList;

    private final long interval;

    @Inject
    @OandaPriceResource
    private WebResource priceResource;
    
    private final EventBus eventBus;

    private boolean isPaused = false;
    
    private final String authHeaderContent;

    @Inject
    public PriceFetcherService(final ConnectionFactory connectionFactory, final OandaServiceConfiguration config, final EventBus eventBus) {
        try {
        	this.eventBus = eventBus;
            eventBus.register(this);
            instrumentList = Instrument.asURLEncodedCommaSeparatedList();
            authHeaderContent = "Bearer " + config.getOanda().getApiKey();
        } catch (final UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        this.interval = config.getOanda().getFetchInterval();
        if(interval < 1000) {
        	throw new RuntimeException("Interval must be higher than 1000 but is " + interval);
        }
        logger.info("Price service created with interval set to " + this.interval + " millis");
    }

    @Subscribe
    public void onPause(final PausePriceFetcherServiceEvent ppfs) {
        isPaused = true;
    }

    @Subscribe
    public void onResume(final ResumePriceFetcherServiceEvent rpfs) {
        isPaused = false;
    }

    @Override
    @Timed
    protected void runOneIteration() throws Exception {
        if (isPaused) {
            logger.info("We are paused...");
            return;
        }
        
        try {
        	final PriceListResponse priceList = fetchPrices(instrumentList);
            logger.info("Got prices: " + priceList);
            for(Price price : priceList.getPrices()) {
            	eventBus.post(new NewPriceAvailableEvent(price));
            }
        } catch (final Exception e) {
            logger.error("Error fetching prices...", e);
        }
    }

    /**
     * Fetch the prices for the given instruments
     * 
     * @param instrumentList
     * @return
     */
	public PriceListResponse fetchPrices(String instrumentList) {
		logger.info("Fetching prices with list " + instrumentList);
		final PriceListResponse priceList = priceResource.
				queryParam("instruments", instrumentList).
				header("Authorization", authHeaderContent).
				get(PriceListResponse.class);
		return priceList;
	}

    @Override
    protected Scheduler scheduler() {
        return Scheduler.newFixedRateSchedule(2500, this.interval, TimeUnit.MILLISECONDS);
    }

}
