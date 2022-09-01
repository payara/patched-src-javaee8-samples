package org.javaee8.cdi.bean.discovery.empty;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Set;

import jakarta.enterprise.inject.spi.Bean;
import jakarta.enterprise.inject.spi.BeanManager;
import jakarta.inject.Inject;

import org.javaee8.cdi.bean.discovery.disabled.CdiDisabledBean;
import org.javaee8.cdi.bean.discovery.enabled.CdiEnabledBean;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class CdiEmptyTest {

    @Deployment
    public static Archive<?> deploy() {
        return ShrinkWrap.create(WebArchive.class).addClasses(CdiDisabledBean.class, CdiEnabledBean.class)
        .addAsWebInfResource("empty-beans.xml", "beans.xml");
    }

    @Inject
    BeanManager beanManager;

    /**
     * After new spec for CDI 4.0, the interpretation of de empty beans changed from all
     * to annotated by default. This means that in order to enable this test it is needed to change the assertions using
     * empty beans.xml
     */
    @Test
    public void should_beans_be_injected() throws Exception {
        Set<Bean<?>> disabledBeans = beanManager.getBeans(CdiDisabledBean.class);
        assertTrue("Instances of disabled bean expected.", disabledBeans.isEmpty());

        Set<Bean<?>> enabledBeans = beanManager.getBeans(CdiEnabledBean.class);
        assertFalse("Instances of enabled bean expected.", enabledBeans.isEmpty());
    }
}
