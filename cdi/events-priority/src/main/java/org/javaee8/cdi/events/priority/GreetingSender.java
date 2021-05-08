package org.javaee8.cdi.events.priority;

import jakarta.enterprise.event.Event;
import jakarta.inject.Inject;

/**
 * @author Radim Hanus
 * @author Arun Gupta
 */
public class GreetingSender implements EventSender {

    @Inject
    private Event<String> event;

    @Override
    public void send(String message) {
        event.fire(message);
    }
}
