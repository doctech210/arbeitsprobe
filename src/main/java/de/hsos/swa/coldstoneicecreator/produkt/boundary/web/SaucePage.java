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
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response.Status;

import io.quarkus.qute.CheckedTemplate;
import io.quarkus.qute.TemplateInstance;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.faulttolerance.Retry;
import org.eclipse.microprofile.faulttolerance.Timeout;

import de.hsos.swa.coldstoneicecreator.produkt.boundary.dto.SauceDTO;
import de.hsos.swa.coldstoneicecreator.produkt.control.SauceControl;
import de.hsos.swa.coldstoneicecreator.produkt.entity.Allergene;
import de.hsos.swa.coldstoneicecreator.produkt.entity.Sauce;

@RequestScoped
@Path("/saucen")
@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
@Produces(MediaType.TEXT_HTML)
@Retry(maxRetries = 4)
@Timeout(250)
public class SaucePage {
    
    @Inject
    SauceControl sauceRepo;

    @CheckedTemplate
    static class Templates {
        
        static native TemplateInstance sauceAlle(List<SauceDTO> saucenDTO);

        static native TemplateInstance error(int errorCode, String errorMessage);
    }

    @GET
    @RolesAllowed({"Admin", "Kunde"})
    @Operation(
        summary = "Gibt alle Saucen zurueck",
        description = "Gitb alle Saucen mit dem angegebenen Filter zurueck"
    )
    public TemplateInstance get(@Valid @QueryParam("Allergene") List<Allergene> allergene) {
        List<Sauce> alle = sauceRepo.get();
        if(allergene != null){
            alle = sauceRepo.getOhneAllergene(allergene);
        }
        List<SauceDTO> alleDTO = new ArrayList<>();
        for(Sauce sauce : alle) {
            alleDTO.add(SauceDTO.Converter.toDTO(sauce));
        }
        return Templates.sauceAlle(alleDTO);
    }

    @POST
    @Transactional
    @RolesAllowed({"Admin"})
    @Operation(
        summary = "Erstellen einer neuen Sauce",
        description = "Erstellen einer neuen Sauce"
    )
    public TemplateInstance post(@Valid @NotNull SauceDTO sauceDTO) {
        if(sauceDTO == null) return Templates.error(Status.NOT_FOUND.getStatusCode(), "Fehlerhafte Eingabe");
        Sauce sauce = SauceDTO.Converter.toSauce(sauceDTO);
        sauceRepo.create(sauce);
        return get(null);
    }
}
