//@author Stefan Bierenriede 852142

package de.hsos.swa.coldstoneicecreator.nutzer.boundary.rest;

import java.security.Principal;
import java.util.Optional;

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
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.Response;

import org.eclipse.microprofile.openapi.annotations.Operation;

import de.hsos.swa.coldstoneicecreator.nutzer.boundary.dto.NutzerDTO;
import de.hsos.swa.coldstoneicecreator.nutzer.boundary.dto.NutzerImportDTO;
import de.hsos.swa.coldstoneicecreator.nutzer.control.NutzerControl;
import de.hsos.swa.coldstoneicecreator.nutzer.entity.Nutzer;

import org.eclipse.microprofile.faulttolerance.Retry;
import org.eclipse.microprofile.faulttolerance.Timeout;

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
    public Response get(@NotNull @PathParam("id") Long id, @Context SecurityContext sec) {
        Nutzer nutzer = nutzerRepo.getById(id);
        if(nutzer != null) { 
            Nutzer erlaubt = this.eingeloggterNutzer(sec);
            if(Long.compare(erlaubt.getId(), id) == 0){
                NutzerDTO nutzerDTO = NutzerDTO.Converter.toDTO(nutzer);
                return Response.ok(nutzerDTO).build();
            }
            return Response.status(Status.FORBIDDEN).build();
        }
        return Response.status(Status.NOT_FOUND).build();
    }

    @PUT
    @Transactional
    @RolesAllowed({"Admin", "Kunde"})
    @Operation(
        summary = "Aendern eines bestimmten Nutzers",
        description = "Returns all orders saved in the database"
    )
    public Response put(@NotNull @PathParam("id") Long id, @Valid @NotNull NutzerImportDTO nutzerImportDTO) {
        Nutzer nutzer = NutzerImportDTO.Converter.toNutzer(nutzerImportDTO);
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

    private Nutzer eingeloggterNutzer(@NotNull SecurityContext sec) {
        Principal user = sec.getUserPrincipal();
        if(user == null) return null;
        Optional<Nutzer> optKunde = Nutzer.find("name", user.getName()).firstResultOptional();
        if(optKunde.isEmpty()) return null;
        return optKunde.get();
    }
}
