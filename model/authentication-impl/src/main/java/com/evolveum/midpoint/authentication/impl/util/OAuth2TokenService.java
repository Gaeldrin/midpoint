package com.evolveum.midpoint.authentication.impl.util;

import com.evolveum.midpoint.xml.ns._public.common.common_3.OAuth2CredentialsType;
import org.springframework.security.oauth2.client.AuthorizedClientServiceOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.OAuth2AuthorizeRequest;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.InMemoryOAuth2AuthorizedClientService;
import org.springframework.security.oauth2.core.AuthorizationGrantType;

/**
 * Utility class for OAuth2 token retrieval using Spring Security's OAuth2 client infrastructure.
 */
public class OAuth2TokenService {

    /**
     * Retrieves an OAuth2 access token using client credentials flow.
     * Uses Spring Security's OAuth2 client infrastructure.
     */
    public static String getAccessToken(OAuth2CredentialsType oauth2Credentials, String clientSecret) {
        // Create a ClientRegistration for this specific request
        ClientRegistration clientRegistration = ClientRegistration.withRegistrationId("mail-oauth2")
                .clientId(oauth2Credentials.getClientId())
                .clientSecret(clientSecret)
                .tokenUri(oauth2Credentials.getTokenEndpoint())
                .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
                .scope("https://outlook.office365.com/.default")
                .build();

        // Create a temporary repository and manager for this request
        InMemoryClientRegistrationRepository repo = new InMemoryClientRegistrationRepository(clientRegistration);
        OAuth2AuthorizedClientService service = new InMemoryOAuth2AuthorizedClientService(repo);
        OAuth2AuthorizedClientManager manager = new AuthorizedClientServiceOAuth2AuthorizedClientManager(repo, service);

        // Request the access token
        OAuth2AuthorizeRequest authorizeRequest = OAuth2AuthorizeRequest.withClientRegistrationId("mail-oauth2")
                .principal(oauth2Credentials.getUsername())
                .build();

        OAuth2AuthorizedClient authorizedClient = manager.authorize(authorizeRequest);
        if (authorizedClient == null || authorizedClient.getAccessToken() == null) {
            throw new IllegalStateException("Failed to authorize client or retrieve access token");
        }

        return authorizedClient.getAccessToken().getTokenValue();
    }
}
