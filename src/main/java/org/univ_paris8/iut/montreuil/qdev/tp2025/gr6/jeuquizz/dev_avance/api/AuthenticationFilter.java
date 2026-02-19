package org.univ_paris8.iut.montreuil.qdev.tp2025.gr6.jeuquizz.dev_avance.api;

import jakarta.annotation.Priority;
import jakarta.ws.rs.Priorities;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import jakarta.ws.rs.ext.Provider;
import org.univ_paris8.iut.montreuil.qdev.tp2025.gr6.jeuquizz.dev_avance.security.UserPrincipal;

import javax.security.auth.Subject;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.login.LoginContext;
import javax.security.auth.login.LoginException;
import java.io.IOException;
import java.security.Principal;

@Provider
@Priority(Priorities.AUTHENTICATION)
public class AuthenticationFilter implements ContainerRequestFilter {

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        String path = requestContext.getUriInfo().getPath();
        if (path.contains("auth/login") || path.contains("openapi") || requestContext.getMethod().equals("GET")) {
            return;
        }

        String authHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            abort(requestContext); return;
        }

        String token = authHeader.substring("Bearer ".length()).trim();

        try {
            LoginContext lc = new LoginContext("MasterAnnonceToken", callbacks -> {
                for (var cb : callbacks) {
                    if (cb instanceof NameCallback) ((NameCallback) cb).setName(token);
                }
            });
            lc.login();
            Subject subject = lc.getSubject();
            Principal principal = subject.getPrincipals(UserPrincipal.class).iterator().next();
            requestContext.setSecurityContext(new SecurityContext() {
                @Override public Principal getUserPrincipal() { return principal; }
                @Override public boolean isUserInRole(String role) { return true; }
                @Override public boolean isSecure() { return requestContext.getSecurityContext().isSecure(); }
                @Override public String getAuthenticationScheme() { return "Bearer"; }
            });

        } catch (LoginException e) {
            abort(requestContext);
        }
    }

    private void abort(ContainerRequestContext ctx) {
        ctx.abortWith(Response.status(Response.Status.UNAUTHORIZED)
                .entity("{\"error\": \"Authentication failed\"}").build());
    }
}