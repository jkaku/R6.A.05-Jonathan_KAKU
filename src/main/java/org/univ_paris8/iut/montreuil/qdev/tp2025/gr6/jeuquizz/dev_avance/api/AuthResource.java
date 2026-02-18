package org.univ_paris8.iut.montreuil.qdev.tp2025.gr6.jeuquizz.dev_avance.api;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.univ_paris8.iut.montreuil.qdev.tp2025.gr6.jeuquizz.dev_avance.dto.CredentialsDTO;
import org.univ_paris8.iut.montreuil.qdev.tp2025.gr6.jeuquizz.dev_avance.models.User;
import org.univ_paris8.iut.montreuil.qdev.tp2025.gr6.jeuquizz.dev_avance.repositories.UserRepository;
import org.univ_paris8.iut.montreuil.qdev.tp2025.gr6.jeuquizz.dev_avance.service.TokenService;

import javax.security.auth.login.LoginContext;
import javax.security.auth.login.LoginException;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;
import java.util.HashMap;
import java.util.Map;

@Path("/auth")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AuthResource {

    private static final Logger logger = LoggerFactory.getLogger(AuthResource.class);
    private final TokenService tokenService = new TokenService();
    private final UserRepository userRepo = new UserRepository();

    @POST
    @Path("/login")
    public Response login(CredentialsDTO credentials) {
        try {
            // JAAS LOGIN [cite: 171]
            LoginContext lc = new LoginContext("MasterAnnonceLogin", callbacks -> {
                for (var cb : callbacks) {
                    if (cb instanceof NameCallback) ((NameCallback) cb).setName(credentials.getUsername());
                    if (cb instanceof PasswordCallback) ((PasswordCallback) cb).setPassword(credentials.getPassword().toCharArray());
                }
            });

            lc.login(); // Déclenche DbLoginModule

            // Si succès, on génère le token
            User user = userRepo.findByUsername(credentials.getUsername());
            String token = tokenService.generateToken(user);

            logger.info("User {} logged in successfully via JAAS", credentials.getUsername());

            Map<String, String> response = new HashMap<>();
            response.put("token", token);
            return Response.ok(response).build();

        } catch (LoginException e) {
            logger.warn("Login failed for user {}", credentials.getUsername());
            return Response.status(Response.Status.UNAUTHORIZED).entity("{\"error\": \"Bad credentials\"}").build();
        }
    }
}