package de.hsos.swa.coldstoneicecreator.produkt.boundary.web;
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
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import io.quarkus.qute.CheckedTemplate;
import io.quarkus.qute.TemplateInstance;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.faulttolerance.Retry;
import org.eclipse.microprofile.faulttolerance.Timeout;

import de.hsos.swa.coldstoneicecreator.produkt.boundary.dto.ZutatDTO;
import de.hsos.swa.coldstoneicecreator.produkt.control.ZutatControl;
import de.hsos.swa.coldstoneicecreator.produkt.entity.Zutat;

@RequestScoped
@Path("/zutaten/{id:\\d+}")
@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
@Produces(MediaType.TEXT_HTML)
@Retry(maxRetries = 4)
@Timeout(250)
public class ZutatenIdPage {
    
    @Inject
    ZutatControl zutatRepo;

    @CheckedTemplate
    static class Templates {
        
        static native TemplateInstance zutatEinzeln(ZutatDTO zutatDTO);

        static native TemplateInstance error(int errorCode, String errorMessage);
    }

    @GET
    @RolesAllowed({"Admin", "Kunde"})
    @Operation(
        summary = "Gibt eine bestimmte Zutat zurueck",
        description = "Gibt eine bestimmte Zutat ueber die uebergebene ID zurueck"
    )
    public TemplateInstance get(@NotNull @PathParam("id") Long id) {
        Zutat zutat = zutatRepo.getById(id);
        if(zutat != null) {
            ZutatDTO zutatDTO = ZutatDTO.Converter.toDTO(zutat);
            return Templates.zutatEinzeln(zutatDTO);
        }
        return Templates.error(Status.NOT_FOUND.getStatusCode(), "Fehlerhafte Eingabe");
    }

    @POST
    @Transactional
    @RolesAllowed({"Admin"})
    @Operation(
        summary = "Aendern einer bestimmten Zutat",
        description = "Aendern einer bestimmt Zutat ueber die uebergebene ID"
    )
    public Response put(@NotNull @PathParam("id") Long id, @Valid @NotNull ZutatDTO zutatDTO) {
        Zutat zutat = ZutatDTO.Converter.toZutat(zutatDTO);
        zutatRepo.put(id, zutat);
        return Response.ok().build();
    }

    @POST
    @Transactional
    @RolesAllowed({"Admin"})
    @Operation(
        summary = "Loeschen einer bestimmten Zutat",
        description = "Loeschen einer bestimmten Zutat ueber die uebergebene ID"
    )
    public Response delete(@NotNull @PathParam("id") Long id) {
        zutatRepo.delete(id);
        return Response.noContent().build();
    }
}
