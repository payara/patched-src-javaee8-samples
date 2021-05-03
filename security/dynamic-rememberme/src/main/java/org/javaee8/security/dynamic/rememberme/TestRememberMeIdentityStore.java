package org.javaee8.security.dynamic.rememberme;


import static jakarta.security.enterprise.identitystore.CredentialValidationResult.INVALID_RESULT;

import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.security.enterprise.CallerPrincipal;
import jakarta.security.enterprise.credential.RememberMeCredential;
import jakarta.security.enterprise.identitystore.CredentialValidationResult;
import jakarta.security.enterprise.identitystore.RememberMeIdentityStore;

@ApplicationScoped
public class TestRememberMeIdentityStore implements RememberMeIdentityStore {

    private Map<String, CredentialValidationResult> loginTokens = new ConcurrentHashMap<>();

    @Override
    public CredentialValidationResult validate(RememberMeCredential credential) {
        if (!loginTokens.containsKey(credential.getToken())) {
            return INVALID_RESULT;
        }
        
        return loginTokens.get(credential.getToken());
    }

    @Override
    public String generateLoginToken(CallerPrincipal callerPrincipal, Set<String> groups) {
        String token = UUID.randomUUID().toString();
        loginTokens.put(token, new CredentialValidationResult(callerPrincipal, groups));

        return token;
    }

    @Override
    public void removeLoginToken(String token) {
        loginTokens.remove(token);
    }

}
