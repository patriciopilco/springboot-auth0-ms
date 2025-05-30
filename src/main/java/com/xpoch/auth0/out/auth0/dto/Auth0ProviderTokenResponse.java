package com.xpoch.auth0.out.auth0.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Auth0ProviderTokenResponse(
        @JsonProperty("access_token") String accessToken,
        @JsonProperty("expires_in") Integer expiresIn,
        @JsonProperty("token_type") String tokenType,
        @JsonProperty("scope") String scope
) {
}
