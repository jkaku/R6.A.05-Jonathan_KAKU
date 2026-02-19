package org.univ_paris8.iut.montreuil.qdev.tp2025.gr6.jeuquizz.dev_avance.api;

import jakarta.ws.rs.core.Application;
import jakarta.ws.rs.core.Response;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.jupiter.api.Test;
import org.univ_paris8.iut.montreuil.qdev.tp2025.gr6.jeuquizz.dev_avance.models.Annonce;

import java.util.List;

import static jakarta.ws.rs.sse.SseEventSource.target;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class AnnonceAPIIntegrationTest extends JerseyTest {

    @Override
    protected Application configure() {
        return new ResourceConfig(AnnonceResource.class);
    }

    @Test
    void testGetAnnonces_ShouldReturn200() {
        Response response = target("/annonces").request().get();
        assertEquals(200, response.getStatus());
    }

    @Test
    void testGetUnknown_ShouldReturn404() {
        Response response = target("/annonces/9999").request().get();
        assertEquals(404, response.getStatus());
    }
}