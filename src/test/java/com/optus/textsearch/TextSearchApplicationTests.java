package com.optus.textsearch;

import com.github.tomakehurst.wiremock.junit.WireMockRule;
import org.glassfish.jersey.logging.LoggingFeature;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.logging.Logger;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TextSearchApplicationTests {

    private static final String URL = "http://localhost:8085";
    @Rule
    public WireMockRule wireMockRule = new WireMockRule(8085);

    @Before
    public void setup() {
        wireMockRule.stubFor(post(urlPathEqualTo("/counter-api/search"))
                .withRequestBody(containing("{" + "\"searchText\":\"[\"Duis\"]\"" + "}"))
                .willReturn(aResponse()
                        .withStatus(201)
                        .withHeader("Content-Type", APPLICATION_JSON)
                        .withBody("{" + "\"counts\":\"[{\"Duis\"\":\"11}]\"" + "}")));

        wireMockRule.stubFor(get(urlPathEqualTo("/counter-api/top/1"))
                .willReturn(aResponse()
                        .withStatus(201)
                        .withHeader("Content-Type", "text/csv")
                        .withBody("vel|17")));
    }

    @Test
    public void testEndPointSearch() {
        Client client = ClientBuilder.newClient().register(new LoggingFeature(Logger.getLogger(LoggingFeature.DEFAULT_LOGGER_NAME),
                LoggingFeature.Verbosity.PAYLOAD_ANY));
        WebTarget webTarget = client.target(URL + "/counter-api/search");
        Response response = webTarget.request().accept("text/csv").
                post(Entity.json("{" + "\"searchText\":\"[\"Duis\"]\"" + "}"));
        assertTrue(response.getStatus() == 201);
    }

    @Test
    public void testEndPoint() {
        Client client = ClientBuilder.newClient().register(new LoggingFeature(Logger.getLogger(LoggingFeature.DEFAULT_LOGGER_NAME),
                LoggingFeature.Verbosity.PAYLOAD_ANY));
        WebTarget webTarget = client.target(URL + "/counter-api/top/1");
        Response response = webTarget.request().accept(MediaType.APPLICATION_JSON).get();
        assertTrue(response.getStatus() == 201);
    }

}
