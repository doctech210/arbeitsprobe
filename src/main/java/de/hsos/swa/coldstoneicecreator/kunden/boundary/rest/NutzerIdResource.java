package de.hsos.swa.coldstoneicecreator.kunden.boundary.rest;
import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Status;
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

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.faulttolerance.Retry;
import org.eclipse.microprofile.faulttolerance.Timeout;

import de.hsos.swa.coldstoneicecreator.kunden.boundary.dto.NutzerDTO;
import de.hsos.swa.coldstoneicecreator.kunden.boundary.dto.NutzerImportDTO;
import de.hsos.swa.coldstoneicecreator.kunden.control.NutzerControl;
import de.hsos.swa.coldstoneicecreator.kunden.entity.Nutzer;

@RequestScoped
@Path("/api/nutzer/{id:\\d+}")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Retry(maxRetries = 4)
@Timeout(250)
public class NutzerIdResource {
    
    @Inject 
    NutzerControl nutzerRepo;

    @GET
    @RolesAllowed({"Admin", "Kunde"})
    @Operation(
        summary = "Gibt einen bestimmten Nutzer zurueck",
        description = "Gibt einen bestimmten Nutzer ueber die uebergebenen ID zurueck"
    )
    public Response get(@NotNull @PathParam("id") Long id) {
        Nutzer nutzer = nutzerRepo.getById(id);
        if(nutzer != null) { 
            NutzerDTO nutzerDTO = NutzerDTO.Converter.toDTO(nutzer);
            return Response.ok(nutzerDTO).build();
        }
        return Response.status(Status.STATUS_UNKNOWN).build();
    }

    @PUT
    @Transactional
    @RolesAllowed({"Admin", "Kunde"})
    @Operation(
        summary = "Aendern eines bestimmten Nutzers",
        description = "Returns all orders saved in the database"
    )
    public Response put(@NotNull @PathParam("id") Long id, @Valid @NotNull NutzerImportDTO kundeImportDTO) {
        Nutzer nutzer = NutzerImportDTO.Converter.toNutzer(kundeImportDTO);
        nutzerRepo.put(id, nutzer);
        return Response.ok().build();
    }

    @DELETE
    @Transactional
    @RolesAllowed({"Admin", "Kunde"})
    @Operation(
        summary = "Loescht einen bestimmten Nutzer",
        description = "Loescht einen bestimmten Nutzer ueber die uebergebene ID"
    )
    public Response delete(@NotNull @PathParam("id") Long id) {
        nutzerRepo.delete(id);
        return Response.noContent().build();
    }
}
