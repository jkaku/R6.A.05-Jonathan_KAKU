package org.univ_paris8.iut.montreuil.qdev.tp2025.gr6.jeuquizz.dev_avance.security;

import org.univ_paris8.iut.montreuil.qdev.tp2025.gr6.jeuquizz.dev_avance.models.User;
import org.univ_paris8.iut.montreuil.qdev.tp2025.gr6.jeuquizz.dev_avance.repositories.UserRepository;

import javax.security.auth.Subject;
import javax.security.auth.callback.*;
import javax.security.auth.login.LoginException;
import javax.security.auth.spi.LoginModule;
import java.io.IOException;
import java.util.Map;

public class DBLoginModule implements LoginModule {
    private Subject subject;
    private CallbackHandler callbackHandler;
    private UserPrincipal userPrincipal;
    private RolePrincipal rolePrincipal;
    private boolean loginSucceeded = false;
    private final UserRepository userRepo = new UserRepository();

    @Override
    public void initialize(Subject subject, CallbackHandler callbackHandler, Map<String, ?> sharedState, Map<String, ?> options) {
        this.subject = subject;
        this.callbackHandler = callbackHandler;
    }

    @Override
    public boolean login() throws LoginException {
        NameCallback nameCb = new NameCallback("username");
        PasswordCallback passCb = new PasswordCallback("password", false);

        try {
            callbackHandler.handle(new Callback[]{nameCb, passCb});
            String username = nameCb.getName();
            String password = new String(passCb.getPassword());

            User user = userRepo.findByUsername(username);
            if (user != null && user.getPassword().equals(password)) {
                loginSucceeded = true;
                userPrincipal = new UserPrincipal(username);
                rolePrincipal = new RolePrincipal("USER");
                return true;
            }
        } catch (IOException | UnsupportedCallbackException e) {
            throw new LoginException(e.getMessage());
        }
        return false;
    }

    @Override
    public boolean commit() throws LoginException {
        if (!loginSucceeded) return false;
        if (!subject.getPrincipals().contains(userPrincipal)) {
            subject.getPrincipals().add(userPrincipal);
            subject.getPrincipals().add(rolePrincipal);
        }
        return true;
    }

    @Override
    public boolean abort() throws LoginException { return false; }

    @Override
    public boolean logout() throws LoginException {
        subject.getPrincipals().remove(userPrincipal);
        subject.getPrincipals().remove(rolePrincipal);
        return true;
    }
}