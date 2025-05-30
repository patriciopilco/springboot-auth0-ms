package com.xpoch.auth0.domain.model;

import java.time.Instant;

public record Auth0Token(
        String accessToken,
        Integer expiresIn,
        String tokenType,
        String scope,
        Instant createdAt
) {

    public Auth0Token(String accessToken, Integer expiresIn, String tokenType, String scope) {
        this(accessToken, expiresIn, tokenType, scope, Instant.now());
    }

    public boolean isExpired() {
        if (expiresIn == null) {
            return false; // If expiresIn is null, we assume the token doesn't expire
        }

        Instant expirationTime = createdAt.plusSeconds(expiresIn);
        return Instant.now().isAfter(expirationTime);
    }
}
