package com.xpoch.auth0;

import com.xpoch.auth0.infraestructure.config.Auth0Properties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(Auth0Properties.class)
public class SpringbootAuth0MsApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootAuth0MsApplication.class, args);
    }

}
