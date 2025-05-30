package com.xpoch.auth0.application.port.in;

import com.xpoch.auth0.domain.model.Auth0Token;

public interface ObtainAuth0TokenUseCase {
    Auth0Token execute();
}
