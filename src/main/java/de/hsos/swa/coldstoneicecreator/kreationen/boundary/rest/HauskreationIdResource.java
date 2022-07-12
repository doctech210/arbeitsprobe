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

import de.hsos.swa.coldstoneicecreator.kreationen.boundary.dto.HauskreationDTO;
import de.hsos.swa.coldstoneicecreator.kreationen.control.HauskreationControl;
import de.hsos.swa.coldstoneicecreator.kreationen.entity.Hauskreation;

@RequestScoped
@Path("/hauskreationen/{id:\\d+}")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class HauskreationIdResource {
    
    @Inject
    HauskreationControl hc;

    @GET
    @RolesAllowed("Admin, Kunde")
    public Response get(@PathParam("id") Long id) {
        Hauskreation hauskreation = hc.getById(id);
        if(hauskreation != null) {
            HauskreationDTO hauskreationDTO = HauskreationDTO.Converter.toDTO(hauskreation);
            return Response.ok(hauskreationDTO).build();
        }
        return Response.status(Status.STATUS_UNKNOWN).build();
    }

    @PUT
    @Transactional
    @RolesAllowed("Admin")
    public Response put(@PathParam("id") Long id, HauskreationDTO hauskreationDTO) {
        Hauskreation hauskreation = HauskreationDTO.Converter.toHauskreation(hauskreationDTO);
        hc.put(id, hauskreation);
        return Response.ok().build();
    }
    
    @POST
    @Transactional
    @RolesAllowed("Admin, Kunde")
    public Response post(@PathParam("id") Long id) {
        //TODO: Add to shopping Cart
        return Response.ok().build();
    }

    @DELETE
    @Transactional
    @RolesAllowed("Admin")
    public Response delete(@PathParam("id") Long id) {
        hc.delete(id);
        return Response.ok().build();
    }
}
