package org.univ_paris8.iut.montreuil.qdev.tp2025.gr6.jeuquizz.dev_avance.api;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import org.univ_paris8.iut.montreuil.qdev.tp2025.gr6.jeuquizz.dev_avance.dto.AnnonceDTO;
import org.univ_paris8.iut.montreuil.qdev.tp2025.gr6.jeuquizz.dev_avance.models.Annonce;
import org.univ_paris8.iut.montreuil.qdev.tp2025.gr6.jeuquizz.dev_avance.service.AnnonceService;
import org.univ_paris8.iut.montreuil.qdev.tp2025.gr6.jeuquizz.dev_avance.utils.DTOMapper;

import java.util.List;
import java.util.stream.Collectors;

@Path("/annonces")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AnnonceResource {

    private final AnnonceService service = new AnnonceService();

    @GET
    public Response getAll(@DefaultValue("1") @QueryParam("page") int page,
                           @DefaultValue("10") @QueryParam("limit") int limit) {
        List<Annonce> result = service.getList(page, limit);

        List<AnnonceDTO> dtos = result.stream()
                .map(DTOMapper::toAnnonceDTO)
                .collect(Collectors.toList());

        return Response.ok(dtos).build();
    }

    @GET
    @Path("/{id}")
    public Response getById(@PathParam("id") Long id) {
        Annonce a = service.getOne(id);
        if (a == null) {
            throw new NotFoundException("Annonce introuvable");
        }
        return Response.ok(DTOMapper.toAnnonceDTO(a)).build();
    }

    @POST
    public Response create(AnnonceDTO dto, @Context SecurityContext securityContext) {
        String username = securityContext.getUserPrincipal().getName();
        Annonce entity = DTOMapper.toAnnonceEntity(dto);
        service.addAnnonce(entity, username, dto.getCategoryId());
        return Response.status(Response.Status.CREATED).entity(dto).build();
    }

    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") Long id, AnnonceDTO dto, @Context SecurityContext securityContext) {
        String username = securityContext.getUserPrincipal().getName();
        Annonce updates = DTOMapper.toAnnonceEntity(dto);
        service.updateAnnonce(id, updates, username);
        return Response.ok().entity("{\"status\": \"updated\"}").build();
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id, @Context SecurityContext securityContext) {
        String username = securityContext.getUserPrincipal().getName();
        service.deleteAnnonce(id, username);
        return Response.noContent().build();
    }
}