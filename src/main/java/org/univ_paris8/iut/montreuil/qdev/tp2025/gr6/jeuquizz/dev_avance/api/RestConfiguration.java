package org.univ_paris8.iut.montreuil.qdev.tp2025.gr6.jeuquizz.dev_avance.api;


import jakarta.ws.rs.ApplicationPath;
import org.glassfish.jersey.server.ResourceConfig;

@ApplicationPath("/api")
public class RestConfiguration extends ResourceConfig {
    public RestConfiguration() {
        packages("org.univ_paris8.iut.montreuil.qdev.tp2025.gr6.jeuquizz.dev_avance.api");
    }
}
