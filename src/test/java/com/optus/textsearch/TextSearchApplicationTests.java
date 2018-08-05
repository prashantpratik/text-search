package com.optus.textsearch;

import com.github.tomakehurst.wiremock.junit.WireMockRule;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TextSearchApplicationTests {

    WireMockRule wireMockRule = new WireMockRule(8080);
//    @InjetsMocks
    

    @Before
    public void setup() {
        wireMockRule.stubFor(post(urlPathMatching("/counter-api/search"))
                .withRequestBody(containing("\"searchText\":\"[\"Duis\"]\""))
                .willReturn(aResponse()
                        .withStatus(201)
                        .withHeader("Content-Type", APPLICATION_JSON)
                        .withBody("\"counts\":\"[{\"Duis\"\":\"11}]\"")));
    }

    @Test
    public void contextLoads() {
    }

}
