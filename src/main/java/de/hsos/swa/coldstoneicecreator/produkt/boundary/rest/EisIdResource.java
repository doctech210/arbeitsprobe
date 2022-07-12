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

import de.hsos.swa.coldstoneicecreator.produkt.boundary.dto.EisDTO;
import de.hsos.swa.coldstoneicecreator.produkt.control.EisControl;
import de.hsos.swa.coldstoneicecreator.produkt.entity.Eis;

@RequestScoped
@Path("/eis/{id:\\d+}")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class EisIdResource {
    
    @Inject
    EisControl ec;

    @GET
    @RolesAllowed("Admin, Kunde")
    public Response get(@PathParam("id") Long id) {
        Eis eis = ec.getById(id);
        if(eis != null) {
            EisDTO eisDTO = EisDTO.Converter.toDTO(eis);
            return Response.ok(eisDTO).build();
        }
        return Response.status(Status.STATUS_UNKNOWN).build();
    }

    @PUT
    @Transactional
    @RolesAllowed("Admin")
    public Response put(@PathParam("id") Long id, EisDTO eisDTO) {
        Eis eis = EisDTO.Converter.toEis(eisDTO);
        ec.put(id, eis);
        return Response.ok().build();
    }

    @DELETE
    @Transactional
    @RolesAllowed("Admin")
    public Response delete(@PathParam("id") Long id) {
        ec.delete(id);
        return Response.ok().build();
    }
}
