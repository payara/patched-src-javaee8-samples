package org.javaee8.servlet.http2;

import static org.jboss.shrinkwrap.api.ShrinkWrap.create;
import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URL;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.spec.WebArchive;
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
                .addAsWebInfResource(new File("src/main/webapp/WEB-INF/web.xml"));
        System.out.println("War file content: \n" + war.toString(true));
        return war;
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

    private void testHttp2(URI uri) throws InterruptedException, IOException {
        HttpRequest request = HttpRequest.newBuilder().uri(uri).version(HttpClient.Version.HTTP_2).GET().build();
        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals("Request wasn't over HTTP/2", HttpClient.Version.HTTP_2.toString(),
                response.version().toString());
    }
}
