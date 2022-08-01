package de.hsos.swa.coldstoneicecreator.produkt.boundary.web;

import java.security.Principal;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

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
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import io.quarkus.qute.CheckedTemplate;
import io.quarkus.qute.TemplateInstance;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.faulttolerance.Retry;
import org.eclipse.microprofile.faulttolerance.Timeout;

import de.hsos.swa.coldstoneicecreator.nutzer.boundary.dto.NutzerExportDTO;
import de.hsos.swa.coldstoneicecreator.nutzer.entity.Nutzer;
import de.hsos.swa.coldstoneicecreator.produkt.boundary.dto.EisDTO;
import de.hsos.swa.coldstoneicecreator.produkt.control.EisControl;
import de.hsos.swa.coldstoneicecreator.produkt.entity.Allergene;
import de.hsos.swa.coldstoneicecreator.produkt.entity.Eis;

@RequestScoped
@Path("/eis")
@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
@Produces(MediaType.TEXT_HTML)
@Retry(maxRetries = 4)
@Timeout(250)
public class EisPage {
    
    @Inject
    EisControl eisRepo;

    @CheckedTemplate
    static class Templates {
        
        static native TemplateInstance eisAlle(List<EisDTO> eisDTO, NutzerExportDTO nutzer, List<Allergene> allergene);

        static native TemplateInstance error(int errorCode, String errorMessage);
    }

    @GET
    @RolesAllowed({"Admin", "Kunde"})
    @Operation(
        summary = "Gibt alle Eissorten zurueck",
        description = "Gibt alle Eissorten ueber den eingestellten Filter zurueck"
    )
    public TemplateInstance get(@Context SecurityContext sec, @Valid @QueryParam("Allergene") List<Allergene> allergene) {
        List<Eis> alle = eisRepo.get();
        Nutzer nutzer = this.eingeloggterKunde(sec);
        NutzerExportDTO nutzerDTO = NutzerExportDTO.Converter.toDTO(nutzer);
        if(allergene != null) {
            alle = eisRepo.getOhneAllergene(allergene);
        }
        List<EisDTO> alleDTO = new ArrayList<>();
        for(Eis eis : alle) {
            alleDTO.add(EisDTO.Converter.toDTO(eis));
        }
        List<Allergene> alleAllergene = new ArrayList<Allergene>(EnumSet.allOf(Allergene.class));
        return Templates.eisAlle(alleDTO, nutzerDTO, alleAllergene);
    }

    @POST
    @Transactional
    @RolesAllowed({"Admin"})
    @Operation(
        summary = "Erstellen eine neue Eissorte",
        description = "Erstellen einer neuen Eissorte"
    )
    public Response post(@Valid @NotNull @FormParam("name") String name, @FormParam("allergene") String[] allergene) {
        Set<Allergene> enthalten = new HashSet<>();
        List<Allergene> alleAllergene = new ArrayList<Allergene>(EnumSet.allOf(Allergene.class));
        for(String allergen : allergene) {
            for(Allergene gesucht : alleAllergene) {
                if(allergen.equals(gesucht.toString()))
                enthalten.add(gesucht);
            }
        }
        Eis eis = new Eis(null, name, enthalten);
        eisRepo.create(eis);
        return Response.ok().header("Refresh", "0; url=/eis").build();
    }

    private Nutzer eingeloggterKunde(SecurityContext sec) {
        Principal user = sec.getUserPrincipal();
        if(user == null) return null;
        Optional<Nutzer> optKunde = Nutzer.find("name", user.getName()).firstResultOptional();
        if(optKunde.isEmpty()) return null;
        return optKunde.get();
    }
}
