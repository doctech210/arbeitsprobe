package de.hsos.swa.coldstoneicecreator.nutzer.boundary.web;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;
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
@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
@Produces(MediaType.TEXT_HTML)
@Retry(maxRetries = 4)
@Timeout(250)
public class NutzerPage {
    
    @Inject
    NutzerControl nutzerRepo;

    @CheckedTemplate
    static class Templates {

        static native TemplateInstance nutzerAlle(List<NutzerExportDTO> nutzerDTO, NutzerExportDTO eingeloggterNutzer);

        static native TemplateInstance error(int errorCode, String errorMessage);
    }

    @GET
    @RolesAllowed({"Admin"})
    @Operation(
        summary = "Gibt alle Nutzer zurueck",
        description = "Gibt alle angemeldeten Nutzer zurueck"
    )
    public TemplateInstance get(@Context SecurityContext sec) {
        NutzerExportDTO nutzer = NutzerExportDTO.Converter.toDTO(this.eingeloggterNutzer(sec));
        List<Nutzer> alle = nutzerRepo.get();
        List<NutzerExportDTO> alleDTO = new ArrayList<>();
        for(Nutzer nutz : alle) {
            alleDTO.add(NutzerExportDTO.Converter.toDTO(nutz));
        }
        return Templates.nutzerAlle(alleDTO, nutzer);
    }

    @POST
    @Transactional
    @PermitAll
    @Operation(
        summary = "Erstellt einen neuen Nutzer",
        description = "Erstellt einen neuen Nutzer"
    )
    public TemplateInstance post(@Context SecurityContext sec, @FormParam("name") String name, @FormParam("passwort") String passwort) {
        Nutzer nutzer = new Nutzer(name, passwort, new ArrayList<>());
        if(nutzerRepo.nameVerfuegbar(nutzer.getName())){
            nutzerRepo.create(nutzer);
            return get(sec);
        } else {
            return Templates.error(Status.NOT_ACCEPTABLE.getStatusCode(), "Nutzername wird bereits verwendet");
        }
    }

    private Nutzer eingeloggterNutzer(@NotNull SecurityContext sec) {
        Principal user = sec.getUserPrincipal();
        if(user == null) return null;
        Optional<Nutzer> optKunde = Nutzer.find("name", user.getName()).firstResultOptional();
        if(optKunde.isEmpty()) return null;
        return optKunde.get();
    }
}
