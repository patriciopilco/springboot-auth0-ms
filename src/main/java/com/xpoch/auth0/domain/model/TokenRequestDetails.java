package com.xpoch.auth0.domain.model;

public record TokenRequestDetails(
        String domain,
        String clientId,
        String clientSecret,
        String audience,
        String grantType
) {}
