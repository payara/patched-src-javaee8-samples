package org.javaee8.servlet.http2;

import static org.jboss.shrinkwrap.api.ShrinkWrap.create;
import static org.junit.Assert.assertEquals;

import java.io.File;
import java.net.URI;
import java.net.URL;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.http.HttpVersion;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Test for the HTTP/2 and the JAX-RS client
 */
@RunWith(Arquillian.class)
public class Http2Test {

    @ArquillianResource
    private URL basicUrl;
    private HttpClient client;

    @Deployment(testable = false)
    public static WebArchive createDeployment() {
        final WebArchive war = create(WebArchive.class).addClasses(Servlet.class)
                .addAsWebResource(new File("src/main/webapp/images/payara-logo.jpg"), "images/payara-logo.jpg")
                .addAsWebInfResource(new File("src/main/webapp/WEB-INF/web.xml"))
                .addAsResource("project-defaults.yml"); // only for Thormtail;
        System.out.println("War file content: \n" + war.toString(true));
        return war;
    }

    @Before
    public void setup() throws Exception {
        client = new HttpClient();
        client.start();
    }

    @After
    public void cleanUp() throws Exception {
        client.stop();
    }

    /**
     * This test runs against the public website supporting HTTP/2
     *
     * @throws Exception
     */
    @Test(timeout = 10000L)
    public void testHttp2ControlGroup() throws Exception {
        testHttp2(new URI("https://http2.akamai.com/"));
    }

    /**
     * This test runs against our private website supporting HTTP/2
     *
     * @throws Exception
     */
    @Test(timeout = 10000L)
    public void testServerHttp2() throws Exception {
        testHttp2(basicUrl.toURI());
    }

    private void testHttp2(URI uri) throws InterruptedException, ExecutionException, TimeoutException {
        assertEquals("Request wasn't over HTTP/2", HttpVersion.HTTP_2, client.GET(uri).getVersion());
    }
}
