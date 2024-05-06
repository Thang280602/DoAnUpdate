package com.otothang.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.web.client.RestTemplate;

@Configuration
public class OAuth2Config {

    @Bean
    public ClientRegistrationRepository clientRegistrationRepository() {
        ClientRegistration registration = ClientRegistration.withRegistrationId("google")
                .clientId("83651994583-qc3grregqtlh02lmq29l90tbs44siqt7.apps.googleusercontent.com")
                .clientSecret("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")
                .redirectUri("http://localhost:8080/oauth2/authorization/google")
                .authorizationUri("https://accounts.google.com/o/oauth2/auth")
                .tokenUri("https://www.googleapis.com/oauth2/v3/token")
                .userInfoUri("https://www.googleapis.com/oauth2/v3/userinfo")
                .userNameAttributeName("email")
                .clientName("Google")
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE) //Thêm dòng này
                .scope("email", "profile")
                .build();

        return new InMemoryClientRegistrationRepository(registration);
    }
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}

