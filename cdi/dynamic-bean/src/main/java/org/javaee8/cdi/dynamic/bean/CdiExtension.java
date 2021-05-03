package org.javaee8.cdi.dynamic.bean;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.enterprise.inject.spi.AfterBeanDiscovery;
import jakarta.enterprise.inject.spi.Extension;

/**
 * 
 * @author Arjan Tijms
 *
 */
public class CdiExtension implements Extension {

    public void afterBean(final @Observes AfterBeanDiscovery afterBeanDiscovery) {
        afterBeanDiscovery
            .addBean()
            .scope(ApplicationScoped.class)
            .types(MyBean.class)
            .id("Created by " + CdiExtension.class)
            .createWith(e -> new MyBeanImpl("Hi!"));
    }
    
}
