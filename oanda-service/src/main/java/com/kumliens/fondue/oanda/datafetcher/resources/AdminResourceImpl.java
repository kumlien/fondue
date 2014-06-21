package com.kumliens.fondue.oanda.datafetcher.resources;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.codahale.metrics.annotation.Timed;
import com.google.common.eventbus.EventBus;
import com.kumliens.fondue.oanda.datafetcher.events.PausePriceFetcherServiceEvent;
import com.kumliens.fondue.oanda.datafetcher.events.ResumePriceFetcherServiceEvent;
import com.kumliens.fondue.oanda.datafetcher.representation.ServiceAction;
import com.kumliens.fondue.oanda.datafetcher.representation.ServiceStatus;
import com.kumliens.fondue.oanda.datafetcher.services.PriceFetcherService;


/**
 * Resource with a rpc-style api like start/stop stuff.
 *
 * @author svante
 */
@Path("/admin")
@Produces(MediaType.APPLICATION_JSON)
public class AdminResourceImpl {

	private static final Logger logger = LoggerFactory.getLogger(AdminResourceImpl.class);

    @Inject
    private PriceFetcherService priceFetcherService;

    @Inject
    private EventBus eventBus;

	@GET
	@Path("pricefetcher")
	@Timed
	public Response doPriceFetcherAction(@QueryParam("action") final ServiceAction action) {
		logger.info("Action is " + action);

        switch (action) {
            case START:
                this.priceFetcherService.startAsync();
                break;
            case STATUS:
                break;
            case STOP:
                this.priceFetcherService.stopAsync();
                break;
            case PAUSE:
                this.eventBus.post(new PausePriceFetcherServiceEvent());
                break;
            case RESUME:
                this.eventBus.post(new ResumePriceFetcherServiceEvent());
                break;
            default:
                break;

        }

        final ServiceStatus ss = new ServiceStatus("PriceFetcher", this.priceFetcherService.state() + "");

		return Response.ok().entity(ss).build();
	}
}
