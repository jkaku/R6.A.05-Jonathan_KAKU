package org.univ_paris8.iut.montreuil.qdev.tp2025.gr6.jeuquizz.dev_avance.api;

import jakarta.ws.rs.ApplicationPath;
import org.glassfish.jersey.server.ResourceConfig;

@ApplicationPath("/api")
public class RestConfiguration extends ResourceConfig {
    public RestConfiguration() {
        // On scanne DEUX packages :
        // 1. Le tien (pour tes annonces, auth, etc.)
        // 2. Celui de Swagger (pour qu'il expose /openapi.json)
        packages(
                "org.univ_paris8.iut.montreuil.qdev.tp2025.gr6.jeuquizz.dev_avance.api",
                "io.swagger.v3.jaxrs2.integration.resources"
        );
    }
}