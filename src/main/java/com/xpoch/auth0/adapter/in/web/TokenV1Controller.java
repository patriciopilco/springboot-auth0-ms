package com.xpoch.auth0.adapter.in.web;


import com.xpoch.auth0.application.port.in.ObtainAuth0TokenUseCase;
import com.xpoch.auth0.domain.model.Auth0Token;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/v1/tokens")
public class TokenV1Controller {

    private final ObtainAuth0TokenUseCase obtainAuth0TokenUseCase;

    public TokenV1Controller(ObtainAuth0TokenUseCase obtainAuth0TokenUseCase) {
        this.obtainAuth0TokenUseCase = obtainAuth0TokenUseCase;
    }

    @GetMapping("/auth0")
    public ResponseEntity<Auth0Token> fetchAuth0Token() {
        try {
            Auth0Token token = obtainAuth0TokenUseCase.execute();
            return ResponseEntity.ok(token);
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to fetch Auth0 token: " + e.getMessage(), e);
        }
    }
}
