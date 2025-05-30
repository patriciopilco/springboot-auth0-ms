package com.xpoch.auth0.out.auth0;

import com.xpoch.auth0.domain.model.Auth0Token;
import com.xpoch.auth0.domain.model.TokenRequestDetails;
import com.xpoch.auth0.domain.port.out.ExternalTokenProviderPort;
import com.xpoch.auth0.out.auth0.dto.Auth0ProviderTokenResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

@Component
public class Auth0RestAdapter implements ExternalTokenProviderPort {

    private static final Logger LOGGER = LoggerFactory.getLogger(Auth0RestAdapter.class);
    private final RestTemplate restTemplate;

    public Auth0RestAdapter(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public Auth0Token fetchToken(TokenRequestDetails details) {
        String tokenUrl = "https://%s/oauth/token".formatted(details.domain());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
        requestBody.add("grant_type", details.grantType());
        requestBody.add("client_id", details.clientId());
        requestBody.add("client_secret", details.clientSecret());
        requestBody.add("audience", details.audience());

        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(requestBody, headers);

        try {
            LOGGER.info("Requesting token from Auth0 URL: {}", tokenUrl);
            Auth0ProviderTokenResponse providerResponse = restTemplate.postForObject(tokenUrl, requestEntity, Auth0ProviderTokenResponse.class);

            if (Objects.isNull(providerResponse) || Objects.isNull(providerResponse.accessToken())) {
                LOGGER.error("Failed to obtain access token from Auth0. Response or access token is null.");
                throw new RuntimeException("Failed to obtain access token from Auth0: Empty response or token.");
            }

            LOGGER.info("Successfully obtained token from Auth0.");
            return new Auth0Token(
                    providerResponse.accessToken(),
                    providerResponse.expiresIn(),
                    providerResponse.tokenType(),
                    providerResponse.scope()
            );
        } catch (HttpClientErrorException e) {
            LOGGER.error("Error while requesting token from Auth0: {} - {}", e.getStatusCode(), e.getResponseBodyAsString(), e);
            throw new RuntimeException("Error fetching token from Auth0: " + e.getMessage(), e);
        } catch (Exception e) {
            LOGGER.error("An unexpected error occurred while fetching token from Auth0.", e);
            throw new RuntimeException("Unexpected error fetching token: " + e.getMessage(), e);
        }
    }
}
