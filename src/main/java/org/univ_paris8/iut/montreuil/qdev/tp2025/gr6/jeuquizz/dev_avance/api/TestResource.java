package org.univ_paris8.iut.montreuil.qdev.tp2025.gr6.jeuquizz.dev_avance.api;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/")
public class TestResource {

    @GET
    @Path("/helloWorld")
    @Produces(MediaType.TEXT_PLAIN)
    public String sayHello() {
        return "Hello World ! L'API fonctionne.";
    }

    @GET
    @Path("/params/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response testParams(
            @PathParam("id") Long id,
            @QueryParam("name") String name) {
        String message = "ID reçu : " + id + ", Nom reçu : " + (name != null ? name : "Anonyme");
        return Response.ok("{\"status\": \"success\", \"message\": \"" + message + "\"}").build();
    }
}
