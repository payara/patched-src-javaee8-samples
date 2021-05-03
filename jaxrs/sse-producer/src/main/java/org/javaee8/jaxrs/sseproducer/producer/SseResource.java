package org.javaee8.jaxrs.sseproducer.producer;

import jakarta.annotation.PostConstruct;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.sse.Sse;
import jakarta.ws.rs.sse.SseBroadcaster;
import jakarta.ws.rs.sse.SseEventSink;

import org.javaee8.jaxrs.sseproducer.data.EventData;

/**
 * Produces server side events.
 *
 * @author Daniel Contreras
 */
@Path("sse")
public class SseResource {

    @Context
    private Sse sse;

    private volatile SseBroadcaster sseBroadcaster;

    @PostConstruct
    public void init() {
        this.sseBroadcaster = sse.newBroadcaster();
    }

    @GET
    @Path("register")
    @Produces(MediaType.SERVER_SENT_EVENTS)
    public void register(@Context SseEventSink eventSink) {

        final Jsonb json = JsonbBuilder.create();
        eventSink.send(sse.newEvent("INIT", json.toJson(new EventData("event:intialized"))));

        sseBroadcaster.register(eventSink);

        for (int i = 0; i < 5; i++) {

            sseBroadcaster.broadcast(sse.newEvent("EVENT", json.toJson(new EventData("event:" + i))));

            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        eventSink.send(sse.newEvent("FINISH", json.toJson(new EventData("event:finished"))));
    }
}
