package de.hsos.swa.coldstoneicecreator.kunden.boundary.rest;
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

import de.hsos.swa.coldstoneicecreator.kunden.boundary.dto.KundeDTO;
import de.hsos.swa.coldstoneicecreator.kunden.boundary.dto.KundeImportDTO;
import de.hsos.swa.coldstoneicecreator.kunden.control.KundeControl;
import de.hsos.swa.coldstoneicecreator.kunden.entity.Nutzer;

@RequestScoped
@Path("/kunden/{id:\\d+}")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class KundeIdResource {
    
    @Inject 
    KundeControl kundenRepo;

    @GET
    @RolesAllowed({"Admin", "Kunde"})
    public Response get(@PathParam("id") Long id) {
        Nutzer kunde = kundenRepo.getById(id);
        if(kunde != null) { 
            KundeDTO kundeDTO = KundeDTO.Converter.toDTO(kunde);
            return Response.ok(kundeDTO).build();
        }
        return Response.status(Status.STATUS_UNKNOWN).build();
    }

    @PUT
    @Transactional
    @RolesAllowed({"Admin", "Kunde"})
    public Response put(@PathParam("id") Long id, KundeImportDTO kundeImportDTO) {
        Nutzer kunde = KundeImportDTO.Converter.toKunde(kundeImportDTO);
        kundenRepo.put(id, kunde);
        return Response.ok().build();
    }

    @DELETE
    @Transactional
    @RolesAllowed({"Admin", "Kunde"})
    public Response delete(@PathParam("id") Long id) {
        kundenRepo.delete(id);
        return Response.ok().build();
    }
}
