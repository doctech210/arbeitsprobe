package de.hsos.swa.coldstoneicecreator.produkt.boundary.web;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.faulttolerance.Retry;
import org.eclipse.microprofile.faulttolerance.Timeout;

import io.quarkus.qute.CheckedTemplate;
import io.quarkus.qute.TemplateInstance;

import de.hsos.swa.coldstoneicecreator.produkt.boundary.dto.EisDTO;
import de.hsos.swa.coldstoneicecreator.produkt.control.EisControl;
import de.hsos.swa.coldstoneicecreator.produkt.entity.Allergene;
import de.hsos.swa.coldstoneicecreator.produkt.entity.Eis;

@RequestScoped
@Path("/eis/{id:\\d+}")
@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
@Produces(MediaType.TEXT_HTML)
@Retry(maxRetries = 4)
@Timeout(250)
public class EisIdPage {
    
    @Inject
    EisControl eisRepo;

    @CheckedTemplate
    static class Templates {
        
        static native TemplateInstance eisEinzeln(EisDTO eisDTO);

        static native TemplateInstance error(int errorCode, String errorMessage);
    }

    @GET
    @RolesAllowed({"Admin", "Kunde"})
    @Operation(
        summary = "Gibt eine bestimmte Eissorte zurueck",
        description = "Gibt eine bestimmte Eissorte ueber die uebergebene ID zureuck"
    )
    public TemplateInstance get(@NotNull @PathParam("id") Long id) {
        Eis eis = eisRepo.getById(id);
        if(eis != null) {
            EisDTO eisDTO = EisDTO.Converter.toDTO(eis);
            return Templates.eisEinzeln(eisDTO);
        }
        return Templates.error(Status.NOT_FOUND.getStatusCode(), "Kein Eis gefunden");
    }

    @POST
    @Transactional
    @RolesAllowed({"Admin"})
    @Operation(
        summary = "Aendern einer bestimmten Eissorte",
        description = "Aendern einer bestimmten Eissorte ueber die uebergebene ID"
    )
    public Response put(@NotNull @PathParam("id") Long id, @FormParam("name") String name, @FormParam("allergene") String[] allergene) {
        Set<Allergene> enthalten = new HashSet<>();
        List<Allergene> alleAllergene = new ArrayList<Allergene>(EnumSet.allOf(Allergene.class));
        for(String allergen : allergene) {
            for(Allergene gesucht : alleAllergene) {
                if(allergen.equals(gesucht.toString()))
                enthalten.add(gesucht);
            }
        }
        Eis eis = new Eis(null, name, enthalten);
        eisRepo.put(id, eis);
        return Response.ok().header("Refresh", "0; url=/eis").build();
    }

    @POST
    @Transactional
    @RolesAllowed({"Admin"})
    @Path("/loeschen")
    @Operation(
        summary = "Loeschen einer bestimmten Eissorte",
        description = "Loeschen einer bestimmten Eissorte ueber die uebergebene ID"
    )
    public Response delete(@NotNull @PathParam("id") Long id) {
        eisRepo.delete(id);
        return Response.noContent().build();
    }
}
