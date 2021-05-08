package org.javaee8.cdi.events.async;

import java.util.concurrent.CompletionStage;

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
    public void sendSync(String message) {
        event.fire(message);
    }

    @Override
    public CompletionStage<String> sendAsync(String message) {
        System.out.println("Sending async");
        return event.fireAsync(message);
    }
}
