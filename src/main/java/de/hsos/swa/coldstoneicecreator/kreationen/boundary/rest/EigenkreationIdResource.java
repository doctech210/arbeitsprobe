package de.hsos.swa.coldstoneicecreator.kreationen.boundary.rest;
import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Status;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import de.hsos.swa.coldstoneicecreator.kreationen.boundary.dto.EigenkreationDTO;
import de.hsos.swa.coldstoneicecreator.kreationen.control.EigenkreationControl;
import de.hsos.swa.coldstoneicecreator.kreationen.entity.Eigenkreation;
import de.hsos.swa.coldstoneicecreator.produkt.boundary.dto.ZutatDTO;
import de.hsos.swa.coldstoneicecreator.produkt.entity.Zutat;

@RequestScoped
@Path("/eigenkreationen/{id:\\d+}")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class EigenkreationIdResource {
    
    @Inject 
    EigenkreationControl ec;

    @GET
    @RolesAllowed({"Admin", "Kunde"})
    public Response get(@PathParam("id") Long id) {
        Eigenkreation eigenkreation = ec.getById(id);
        if(eigenkreation != null) { 
            EigenkreationDTO eigenkreationDTO = EigenkreationDTO.Converter.toDTO(eigenkreation);
            return Response.ok(eigenkreationDTO).build();
        }
        return Response.status(Status.STATUS_UNKNOWN).build();
    }

    @PUT
    @Transactional
    @RolesAllowed({"Admin", "Kunde"})
    public Response put(@PathParam("id") Long id, EigenkreationDTO eigenkreationDTO) {
        Eigenkreation eigenkreation = EigenkreationDTO.Converter.toEigenkreation(eigenkreationDTO);
        ec.put(id, eigenkreation);
        return Response.ok().build();
    }
    
    @POST
    @Transactional
    @RolesAllowed({"Admin", "Kunde"})
    public Response post(@PathParam("id") Long id) {
        //TODO: Add to shoppinglist
        return Response.ok().build();
    }
    
    @PUT
    @Transactional
    @RolesAllowed({"Admin"})
    @Path("/zutaten/{zutatnummer:\\d+}")
    public Response putZutaten(@PathParam("id") Long id, @PathParam("zutatnummer") int zutatnummer, ZutatDTO zutatDTO) {
        Zutat zutat = ZutatDTO.Converter.toZutat(zutatDTO);
        ec.putZutat(id, --zutatnummer, zutat);
        return Response.ok().build();
    }

    @DELETE
    @Transactional
    @RolesAllowed({"Admin", "Kunde"})
    public Response delete(@PathParam("id") Long id) {
        ec.delete(id);
        return Response.ok().build();
    }
}
