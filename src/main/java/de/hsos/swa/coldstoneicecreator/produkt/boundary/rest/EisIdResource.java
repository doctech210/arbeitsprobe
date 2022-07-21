package de.hsos.swa.coldstoneicecreator.produkt.boundary.rest;

import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.eclipse.microprofile.openapi.annotations.Operation;

import de.hsos.swa.coldstoneicecreator.produkt.boundary.dto.EisDTO;
import de.hsos.swa.coldstoneicecreator.produkt.control.EisControl;
import de.hsos.swa.coldstoneicecreator.produkt.entity.Eis;

@RequestScoped
@Path("/eis/{id:\\d+}")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class EisIdResource {
    
    @Inject
    EisControl eisRepo;

    @GET
    @RolesAllowed({"Admin", "Kunde"})
    @Operation(
        summary = "Gibt eine bestimmte Eissorte zurueck",
        description = "Gibt eine bestimmte Eissorte ueber die uebergebene ID zureuck"
    )
    public Response get(@NotNull @PathParam("id") Long id) {
        Eis eis = eisRepo.getById(id);
        if(eis != null) {
            EisDTO eisDTO = EisDTO.Converter.toDTO(eis);
            return Response.ok(eisDTO).build();
        }
        return Response.status(Status.NOT_FOUND).build();
    }

    @PUT
    @Transactional
    @RolesAllowed({"Admin"})
    @Operation(
        summary = "Aendern einer bestimmten Eissorte",
        description = "Aendern einer bestimmten Eissorte ueber die uebergebene ID"
    )
    public Response put(@NotNull @PathParam("id") Long id, @Valid @NotNull EisDTO eisDTO) {
        Eis eis = EisDTO.Converter.toEis(eisDTO);
        eisRepo.put(id, eis);
        return Response.ok().build();
    }

    @DELETE
    @Transactional
    @RolesAllowed({"Admin"})
    @Operation(
        summary = "Loeschen einer bestimmten Eissorte",
        description = "Loeschen einer bestimmten Eissorte ueber die uebergebene ID"
    )
    public Response delete(@NotNull @PathParam("id") Long id) {
        eisRepo.delete(id);
        return Response.noContent().build();
    }
}
