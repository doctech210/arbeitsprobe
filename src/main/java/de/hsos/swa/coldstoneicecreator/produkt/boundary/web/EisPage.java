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
        
        static native TemplateInstance eisAlle(List<EisDTO> eisDTO);

        static native TemplateInstance error(int errorCode, String errorMessage);
    }

    @GET
    @RolesAllowed({"Admin", "Kunde"})
    @Operation(
        summary = "Gibt alle Eissorten zurueck",
        description = "Gibt alle Eissorten ueber den eingestellten Filter zurueck"
    )
    public TemplateInstance get(@Valid @QueryParam("Allergene") List<Allergene> allergene) {
        List<Eis> alle = eisRepo.get();
        if(allergene != null) {
            alle = eisRepo.getOhneAllergene(allergene);
        }
        List<EisDTO> alleDTO = new ArrayList<>();
        for(Eis eis : alle) {
            alleDTO.add(EisDTO.Converter.toDTO(eis));
        }
        return Templates.eisAlle(alleDTO);
    }

    @POST
    @Transactional
    @RolesAllowed({"Admin"})
    @Operation(
        summary = "Erstellen eine neue Eissorte",
        description = "Erstellen einer neuen Eissorte"
    )
    public TemplateInstance post(@Valid @NotNull EisDTO eisDTO) {
        if(eisDTO == null) return Templates.error(Status.NOT_ACCEPTABLE.getStatusCode(), "Fehlerhafte Eingabe");
        Eis eis = EisDTO.Converter.toEis(eisDTO);
        eisRepo.create(eis);
        //return Response.ok().build();
        return get(null);
    }
}
