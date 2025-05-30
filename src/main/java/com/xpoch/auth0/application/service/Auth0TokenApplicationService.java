package com.xpoch.auth0.application.service;

import com.xpoch.auth0.application.port.in.ObtainAuth0TokenUseCase;
import com.xpoch.auth0.domain.model.Auth0Token;
import com.xpoch.auth0.domain.model.TokenRequestDetails;
import com.xpoch.auth0.domain.port.out.ExternalTokenProviderPort;
import com.xpoch.auth0.infraestructure.config.Auth0Properties;
import org.springframework.stereotype.Service;

@Service
public class Auth0TokenApplicationService implements ObtainAuth0TokenUseCase {

    private final ExternalTokenProviderPort externalTokenProviderPort;
    private final Auth0Properties auth0Properties;

    public Auth0TokenApplicationService(
            ExternalTokenProviderPort externalTokenProviderPort,
            Auth0Properties auth0Properties) {
        this.externalTokenProviderPort = externalTokenProviderPort;
        this.auth0Properties = auth0Properties;
    }

    @Override
    public Auth0Token execute() {
        // Crea el objeto de valor con los detalles para la solicitud del token
        TokenRequestDetails details = new TokenRequestDetails(
                auth0Properties.domain(),
                auth0Properties.clientId(),
                auth0Properties.clientSecret(),
                auth0Properties.audience(),
                "client_credentials" // Grant type podría ser parte de la configuración o fijado aquí
        );
        return externalTokenProviderPort.fetchToken(details);
    }
}
