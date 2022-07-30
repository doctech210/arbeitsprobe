package de.hsos.swa.coldstoneicecreator.nutzer.boundary.web;

import java.security.Principal;
import java.util.Optional;

import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.Response;

import io.quarkus.qute.CheckedTemplate;
import io.quarkus.qute.TemplateInstance;

import org.eclipse.microprofile.openapi.annotations.Operation;

import de.hsos.swa.coldstoneicecreator.nutzer.boundary.dto.NutzerExportDTO;
import de.hsos.swa.coldstoneicecreator.nutzer.boundary.dto.NutzerImportDTO;
import de.hsos.swa.coldstoneicecreator.nutzer.control.NutzerControl;
import de.hsos.swa.coldstoneicecreator.nutzer.entity.Nutzer;

import org.eclipse.microprofile.faulttolerance.Retry;
import org.eclipse.microprofile.faulttolerance.Timeout;

@RequestScoped
@Path("/nutzer/{id:\\d+}")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Retry(maxRetries = 4)
@Timeout(250)
public class NutzerIdPage {
    
    @Inject 
    NutzerControl nutzerRepo;

    @CheckedTemplate
    static class Templates {

        static native TemplateInstance nutzerEinzeln(NutzerExportDTO nutzerDTO);

        static native TemplateInstance error(int errorCode, String errorMessage);
    }

    @GET
    @RolesAllowed({"Admin", "Kunde"})
    @Operation(
        summary = "Gibt einen bestimmten Nutzer zurueck",
        description = "Gibt einen bestimmten Nutzer ueber die uebergebenen ID zurueck"
    )
    public TemplateInstance get(@NotNull @PathParam("id") Long id, @Context SecurityContext sec) {
        Nutzer nutzer = nutzerRepo.getById(id);
        if(nutzer != null) { 
            Nutzer erlaubt = this.eingeloggterNutzer(sec);
            if(Long.compare(erlaubt.getId(), id) == 0){
                NutzerExportDTO nutzerDTO = NutzerExportDTO.Converter.toDTO(nutzer);
                return Templates.nutzerEinzeln(nutzerDTO);
            }
            return Templates.error(Response.Status.FORBIDDEN.getStatusCode(), "Sie sind nicht fuer diese Seite authorisiert");
        }
        return Templates.error(Response.Status.NOT_FOUND.getStatusCode(), "Bitte erst Anmelden");
        
    }

    @POST
    @Transactional
    @RolesAllowed({"Admin", "Kunde"})
    @Path("/aendern")
    @Operation(
        summary = "Aendern eines bestimmten Nutzers",
        description = "Returns all orders saved in the database"
    )
    public Response put(@NotNull @PathParam("id") Long id, @Valid @NotNull NutzerImportDTO nutzerImportDTO) {
        Nutzer nutzer = NutzerImportDTO.Converter.toNutzer(nutzerImportDTO);
        nutzerRepo.put(id, nutzer);
        return Response.ok().build();
    }

    @POST
    @Transactional
    @RolesAllowed({"Admin", "Kunde"})
    @Path("/loeschen")
    @Operation(
        summary = "Loescht einen bestimmten Nutzer",
        description = "Loescht einen bestimmten Nutzer ueber die uebergebene ID"
    )
    public Response delete(@NotNull @PathParam("id") Long id) {
        nutzerRepo.delete(id);
        return Response.ok().header("Refresh", "0; url=/nutzer").build();
    }

    private Nutzer eingeloggterNutzer(@NotNull SecurityContext sec) {
        Principal user = sec.getUserPrincipal();
        if(user == null) return null;
        Optional<Nutzer> optKunde = Nutzer.find("name", user.getName()).firstResultOptional();
        if(optKunde.isEmpty()) return null;
        return optKunde.get();
    }
}
