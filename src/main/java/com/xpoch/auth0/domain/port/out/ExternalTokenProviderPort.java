package com.xpoch.auth0.domain.port.out;


import com.xpoch.auth0.domain.model.Auth0Token;
import com.xpoch.auth0.domain.model.TokenRequestDetails;

public interface ExternalTokenProviderPort {
    Auth0Token fetchToken(TokenRequestDetails details);
}
