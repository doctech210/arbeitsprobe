package de.hsos.swa.coldstoneicecreator.produkt.boundary.web;
import java.util.ArrayList;
import java.util.List;

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

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.faulttolerance.Retry;
import org.eclipse.microprofile.faulttolerance.Timeout;

import javax.ws.rs.QueryParam;

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
        
        static native TemplateInstance zutatAlle(List<ZutatDTO> zutatenDTO);

        static native TemplateInstance error(int errorCode, String errorMessage);
    }

    @GET
    @RolesAllowed({"Admin", "Kunde"})
    @Operation(
        summary = "Gibt alle Zutaten zureuck",
        description = "Gibt alle Zutaten mit dem eingegebenen Filter zurueck"
    )
    public TemplateInstance get(@Valid @QueryParam("Allergene") List<Allergene> allergene) {
        List<Zutat> alle = zutatRepo.get();
        if(allergene != null) {
            alle = zutatRepo.getOhneAllergene(allergene);
        }
        List<ZutatDTO> alleDTO = new ArrayList<>();
        for(Zutat zutat : alle) {
            alleDTO.add(ZutatDTO.Converter.toDTO(zutat));
        }
        return Templates.zutatAlle(alleDTO);
    }

    @POST
    @Transactional
    @RolesAllowed({"Admin"})
    @Operation(
        summary = "Erstellen einer neuen Zutat",
        description = "Erstellen einer neuen Zutat"
    )
    public TemplateInstance post(@Valid @NotNull ZutatDTO zutatDTO) {
        if(zutatDTO == null) return Templates.error(Status.NOT_FOUND.getStatusCode(), "Fehlerhafte Eingabe");
        Zutat zutat = ZutatDTO.Converter.toZutat(zutatDTO);
        zutatRepo.create(zutat); 
        return get(null);
    }
}
