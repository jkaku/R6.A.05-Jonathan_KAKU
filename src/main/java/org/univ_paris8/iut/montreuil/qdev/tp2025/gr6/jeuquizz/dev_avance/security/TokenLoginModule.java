package org.univ_paris8.iut.montreuil.qdev.tp2025.gr6.jeuquizz.dev_avance.security;

import org.univ_paris8.iut.montreuil.qdev.tp2025.gr6.jeuquizz.dev_avance.models.User;
import org.univ_paris8.iut.montreuil.qdev.tp2025.gr6.jeuquizz.dev_avance.service.TokenService;

import javax.security.auth.Subject;
import javax.security.auth.callback.*;
import javax.security.auth.login.LoginException;
import javax.security.auth.spi.LoginModule;
import java.io.IOException;
import java.util.Map;

public class TokenLoginModule implements LoginModule {
    private Subject subject;
    private CallbackHandler callbackHandler;
    private UserPrincipal userPrincipal;
    private final TokenService tokenService = new TokenService();
    private boolean loginSucceeded = false;

    @Override
    public void initialize(Subject subject, CallbackHandler callbackHandler, Map<String, ?> sharedState, Map<String, ?> options) {
        this.subject = subject;
        this.callbackHandler = callbackHandler;
    }

    @Override
    public boolean login() throws LoginException {
        NameCallback tokenCb = new NameCallback("token");
        try {
            callbackHandler.handle(new Callback[]{tokenCb});
            String token = tokenCb.getName();

            User user = tokenService.validateToken(token);
            if (user != null) {
                loginSucceeded = true;
                userPrincipal = new UserPrincipal(user.getUsername());
                return true;
            }
        } catch (IOException | UnsupportedCallbackException e) {
            throw new LoginException("Token validation failed");
        }
        return false;
    }

    @Override
    public boolean commit() throws LoginException {
        if (!loginSucceeded) return false;
        if (!subject.getPrincipals().contains(userPrincipal)) {
            subject.getPrincipals().add(userPrincipal);
        }
        return true;
    }

    @Override // Méthodes standards obligatoires
    public boolean abort() { return false; }
    @Override
    public boolean logout() {
        subject.getPrincipals().remove(userPrincipal);
        return true;
    }
}