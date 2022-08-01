//@author Stefan Bierenriede 852142

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
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.faulttolerance.Retry;
import org.eclipse.microprofile.faulttolerance.Timeout;

import javax.ws.rs.QueryParam;

import de.hsos.swa.coldstoneicecreator.nutzer.boundary.dto.NutzerExportDTO;
import de.hsos.swa.coldstoneicecreator.nutzer.entity.Nutzer;
import de.hsos.swa.coldstoneicecreator.produkt.boundary.dto.ZutatDTO;
import de.hsos.swa.coldstoneicecreator.produkt.control.ZutatControl;
import de.hsos.swa.coldstoneicecreator.produkt.entity.Allergene;
import de.hsos.swa.coldstoneicecreator.produkt.entity.Zutat;
import io.quarkus.qute.CheckedTemplate;
import io.quarkus.qute.TemplateInstance;

@RequestScoped
@Path("/zutaten")
@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
@Produces(MediaType.TEXT_HTML)
@Retry(maxRetries = 4)
@Timeout(250)
public class ZutatenPage {
    
    @Inject
    ZutatControl zutatRepo;

    @CheckedTemplate
    static class Templates {
        
        static native TemplateInstance zutatAlle(List<ZutatDTO> zutatenDTO, NutzerExportDTO nutzer, List<Allergene> allergene);

        static native TemplateInstance error(int errorCode, String errorMessage);
    }

    @GET
    @RolesAllowed({"Admin", "Kunde"})
    @Operation(
        summary = "Gibt alle Zutaten zureuck",
        description = "Gibt alle Zutaten mit dem eingegebenen Filter zurueck"
    )
    public TemplateInstance get(@Context SecurityContext sec, @Valid @QueryParam("Allergene") List<Allergene> allergene) {
        List<Zutat> alle = zutatRepo.get();
        Nutzer nutzer = this.eingeloggterKunde(sec);
        NutzerExportDTO nutzerDTO = NutzerExportDTO.Converter.toDTO(nutzer);
        if(allergene != null) {
            alle = zutatRepo.getOhneAllergene(allergene);
        }
        List<ZutatDTO> alleDTO = new ArrayList<>();
        for(Zutat zutat : alle) {
            alleDTO.add(ZutatDTO.Converter.toDTO(zutat));
        }
        List<Allergene> alleAllergene = new ArrayList<Allergene>(EnumSet.allOf(Allergene.class));
        return Templates.zutatAlle(alleDTO, nutzerDTO, alleAllergene);
    }

    @POST
    @Transactional
    @RolesAllowed({"Admin"})
    @Operation(
        summary = "Erstellen einer neuen Zutat",
        description = "Erstellen einer neuen Zutat"
    )
    public Response post(@Valid @NotNull @FormParam("name") String name, @FormParam("allergene") String[] allergene, @FormParam("premium") boolean premium) {
        Set<Allergene> enthalten = new HashSet<>();
        List<Allergene> alleAllergene = new ArrayList<Allergene>(EnumSet.allOf(Allergene.class));
        for(String allergen : allergene) {
            for(Allergene gesucht : alleAllergene) {
                if(allergen.equals(gesucht.toString()))
                enthalten.add(gesucht);
            }
        }
        Zutat zutat = new Zutat(null, name, premium, enthalten);
        zutatRepo.create(zutat);
        return Response.ok().header("Refresh", "0; url=/zutaten").build();
    }

    private Nutzer eingeloggterKunde(SecurityContext sec) {
        Principal user = sec.getUserPrincipal();
        if(user == null) return null;
        Optional<Nutzer> optKunde = Nutzer.find("name", user.getName()).firstResultOptional();
        if(optKunde.isEmpty()) return null;
        return optKunde.get();
    }
}
