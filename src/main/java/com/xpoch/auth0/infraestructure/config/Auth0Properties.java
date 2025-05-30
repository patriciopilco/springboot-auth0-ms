package com.xpoch.auth0.infraestructure.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "auth0")
public record Auth0Properties(
        String domain,
        String issuerUri,
        String clientId,
        String clientSecret,
        String audience
) {
}
