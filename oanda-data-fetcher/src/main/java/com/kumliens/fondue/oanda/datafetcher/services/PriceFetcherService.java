package com.kumliens.fondue.oanda.datafetcher.services;

import java.net.URLEncoder;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.codahale.metrics.annotation.Timed;
import com.google.common.util.concurrent.AbstractScheduledService;
import com.kumliens.fondue.oanda.datafetcher.guice.OandaPriceResource;
import com.kumliens.fondue.oanda.datafetcher.representation.Instrument;
import com.kumliens.fondue.oanda.datafetcher.responses.PriceListResponse;
import com.sun.jersey.api.client.WebResource;

public class PriceFetcherService extends AbstractScheduledService {

    private static final Logger logger = LoggerFactory.getLogger(PriceFetcherService.class);

    @Inject
    @OandaPriceResource
    private WebResource priceResource;

    @Override
    @Timed
    protected void runOneIteration() throws Exception {
        String instruments = Instrument.asCommaSeparatedList();
        instruments = URLEncoder.encode(instruments, "UTF-8");
        logger.warn("Fetching prices with list " + instruments);
        try {
            final PriceListResponse priceList = this.priceResource.queryParam("instruments", instruments).get(PriceListResponse.class);
            logger.warn("Got prices: " + priceList);
        } catch (final Exception e) {
            logger.error("Error fetching prices...", e);
        }
    }

    @Override
    protected Scheduler scheduler() {
        return Scheduler.newFixedRateSchedule(2500, 10000, TimeUnit.MILLISECONDS);
    }

}
