package com.optus.textsearch.config;

import com.optus.textsearch.endpoints.TextSearchResource;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;

/**
 * Jersey config to register end points
 */
@Component
public class JerseyConfig extends ResourceConfig {
    public JerseyConfig() {
        register(TextSearchResource.class);
    }
}
