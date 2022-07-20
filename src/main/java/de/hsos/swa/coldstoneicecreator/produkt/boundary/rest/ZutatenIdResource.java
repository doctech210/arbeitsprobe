package de.hsos.swa.coldstoneicecreator.produkt.boundary.rest;
import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Status;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import de.hsos.swa.coldstoneicecreator.produkt.boundary.dto.ZutatDTO;
import de.hsos.swa.coldstoneicecreator.produkt.control.ZutatControl;
import de.hsos.swa.coldstoneicecreator.produkt.entity.Zutat;

@RequestScoped
@Path("/zutaten/{id:\\d+}")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ZutatenIdResource {
    
    @Inject
    ZutatControl zutatRepo;

    @GET
    @RolesAllowed({"Admin", "Kunde"})
    public Response get(@PathParam("id") Long id) {
        Zutat zutat = zutatRepo.getById(id);
        if(zutat != null) {
            ZutatDTO zutatDTO = ZutatDTO.Converter.toDTO(zutat);
            return Response.ok(zutatDTO).build();
        }
        return Response.status(Status.STATUS_UNKNOWN).build();
    }

    @PUT
    @Transactional
    @RolesAllowed({"Admin"})
    public Response put(@PathParam("id") Long id, ZutatDTO zutatDTO) {
        Zutat zutat = ZutatDTO.Converter.toZutat(zutatDTO);
        zutatRepo.put(id, zutat);
        return Response.ok().build();
    }

    @DELETE
    @Transactional
    @RolesAllowed({"Admin"})
    public Response delete(@PathParam("id") Long id) {
        zutatRepo.delete(id);
        return Response.ok().build();
    }
}
