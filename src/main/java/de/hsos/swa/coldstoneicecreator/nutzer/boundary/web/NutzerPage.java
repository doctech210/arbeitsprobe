package de.hsos.swa.coldstoneicecreator.nutzer.boundary.web;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.security.PermitAll;
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
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response.Status;

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
@Path("/nutzer")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Retry(maxRetries = 4)
@Timeout(250)
public class NutzerPage {
    
    @Inject
    NutzerControl nutzerRepo;

    @CheckedTemplate
    static class Templates {

        static native TemplateInstance nutzerAlle(List<NutzerExportDTO> nutzerDTO);

        static native TemplateInstance error(int errorCode, String errorMessage);
    }

    @GET
    @RolesAllowed({"Admin"})
    @Operation(
        summary = "Gibt alle Nutzer zurueck",
        description = "Gibt alle angemeldeten Nutzer zurueck"
    )
    public TemplateInstance get() {
        List<Nutzer> alle = nutzerRepo.get();
        List<NutzerExportDTO> alleDTO = new ArrayList<>();
        for(Nutzer nutzer : alle) {
            alleDTO.add(NutzerExportDTO.Converter.toDTO(nutzer));
        }
        return Templates.nutzerAlle(alleDTO);
    }

    @POST
    @Transactional
    @PermitAll
    @Operation(
        summary = "Erstellt einen neuen Nutzer",
        description = "Erstellt einen neuen Nutzer"
    )
    public TemplateInstance post(@Valid @NotNull NutzerImportDTO nutzerImportDTO) {
        Nutzer nutzer = NutzerImportDTO.Converter.toNutzer(nutzerImportDTO);
        if(nutzerRepo.nameVerfuegbar(nutzer.getName())){
            nutzerRepo.create(nutzer);
            return get();
        } else {
            return Templates.error(Status.NOT_ACCEPTABLE.getStatusCode(), "Nutzername wird bereits verwendet");
        }
    }
}
