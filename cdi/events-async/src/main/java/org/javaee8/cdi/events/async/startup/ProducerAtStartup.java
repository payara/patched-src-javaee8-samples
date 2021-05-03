/*
 *    Copyright (c) [2019] Payara Foundation and/or its affiliates.
 */

package org.javaee8.cdi.events.async.startup;

import jakarta.annotation.PostConstruct;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;
import jakarta.enterprise.event.Event;
import jakarta.inject.Inject;
import java.util.logging.Level;
import java.util.logging.Logger;

@Startup
@Singleton
public class ProducerAtStartup {

    private static final Logger LOG = Logger.getLogger(ProducerAtStartup.class.getName());

    @Inject
    Event<MyEvent> evt;

    @PostConstruct
    public void init() {
        LOG.info("ProducerAtStartup init");
        fireEvent();
    }

    public void fireEvent(){
        evt.fireAsync(new MyEvent("ProducerAtStartup")).whenComplete((e, t) -> {
            if (t != null) {
                LOG.log(Level.SEVERE, "Event dispatch failed", t);
            } else {
                LOG.info("Event dispatch succeeded");
            }
        });
        LOG.info("fireAsync done");
    }
}
