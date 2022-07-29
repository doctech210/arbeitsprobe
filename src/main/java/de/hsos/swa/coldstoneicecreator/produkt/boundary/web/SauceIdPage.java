package de.hsos.swa.coldstoneicecreator.produkt.boundary.web;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.core.Response.Status;
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
@Path("/saucen/{id:\\d+}")
@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
@Produces(MediaType.TEXT_HTML)
@Retry(maxRetries = 4)
@Timeout(250)
public class SauceIdPage {

    @Inject
    SauceControl sauceRepo;

    @CheckedTemplate
    static class Templates {
        
        static native TemplateInstance sauceEinzeln(SauceDTO sauceDTO);

        static native TemplateInstance error(int errorCode, String errorMessage);
    }
    
    @GET
    @RolesAllowed({"Admin", "Kunde"})
    @Operation(
        summary = "Gibt eine bestimmte Sauce zurueck",
        description = "Gibt eine bestimmte Sauce ueber die uebergebene ID zurueck"
    )
    public TemplateInstance get(@NotNull @PathParam("id") Long id) {
        Sauce sauce = sauceRepo.getById(id);
        if(sauce != null) {
            SauceDTO sauceDTO = SauceDTO.Converter.toDTO(sauce);
            return Templates.sauceEinzeln(sauceDTO);
        }
        return Templates.error(Status.NOT_FOUND.getStatusCode(), "Fehlerhafte Eingabe");
    }

    @POST
    @Transactional
    @RolesAllowed({"Admin"})
    @Operation(
        summary = "Aendern einer bestimmten Sauce",
        description = "Aendern einer bestimmte Sauce ueber die uebergebene ID"
    )
    public Response put(@NotNull @PathParam("id") Long id,  @FormParam("name") String name, @FormParam("allergene") String[] allergene) {
        Set<Allergene> enthalten = new HashSet<>();
        List<Allergene> alleAllergene = new ArrayList<Allergene>(EnumSet.allOf(Allergene.class));
        for(String allergen : allergene) {
            for(Allergene gesucht : alleAllergene) {
                if(allergen.equals(gesucht.toString()))
                enthalten.add(gesucht);
            }
        }
        Sauce sauce = new Sauce(null, name, enthalten);
        sauceRepo.put(id, sauce);
        return Response.ok().header("Refresh", "0; url=/saucen").build();
    }

    @POST
    @Transactional
    @RolesAllowed({"Admin"})
    @Operation(
        summary = "Loeschen einer bestimmten Sauce",
        description = "Loeschen einer bestimmten Sauce ueber die uebergebene ID"
    )
    public Response delete(@NotNull @PathParam("id") Long id) {
        sauceRepo.delete(id);
        return Response.noContent().build();
    }
}
