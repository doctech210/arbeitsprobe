package de.hsos.swa.coldstoneicecreator.kreationen.boundary.web;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.Response.Status;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.faulttolerance.Retry;
import org.eclipse.microprofile.faulttolerance.Timeout;

import de.hsos.swa.coldstoneicecreator.kreationen.boundary.dto.EigenkreationDTO;
import de.hsos.swa.coldstoneicecreator.kreationen.boundary.dto.KreationIdDTO;
import de.hsos.swa.coldstoneicecreator.kreationen.control.EigenkreationControl;
import de.hsos.swa.coldstoneicecreator.kreationen.entity.Eigenkreation;
import de.hsos.swa.coldstoneicecreator.kunden.entity.Nutzer;
import de.hsos.swa.coldstoneicecreator.produkt.control.EisControl;
import de.hsos.swa.coldstoneicecreator.produkt.control.SauceControl;
import de.hsos.swa.coldstoneicecreator.produkt.control.ZutatControl;
import de.hsos.swa.coldstoneicecreator.produkt.entity.Eis;
import de.hsos.swa.coldstoneicecreator.produkt.entity.Sauce;
import de.hsos.swa.coldstoneicecreator.produkt.entity.Zutat;
import io.quarkus.qute.CheckedTemplate;
import io.quarkus.qute.TemplateInstance;

@RequestScoped
@Path("/eigenkreationen/{id:\\d+}")
@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
@Produces(MediaType.TEXT_HTML)
@Retry(maxRetries = 4)
@Timeout(250)
public class EigenkreationIdPage {
    
    @Inject 
    EigenkreationControl eigenkreationRepo;

    @Inject
    EisControl eisRepo;

    @Inject
    ZutatControl zutatRepo;
    
    @Inject
    SauceControl sauceRepo;

    @CheckedTemplate
    static class Templates {

        static native TemplateInstance eigenkreationEinzeln(EigenkreationDTO eigenkreationDTO);

        static native TemplateInstance error(int errorCode, String errorMessage);
    }

    @GET
    @RolesAllowed({"Admin", "Kunde"})
    @Operation(
        summary = "Gibt eine bestimmte Eigenkreation zurueck",
        description = "Gibt eine bestimmte Eigenkreation des angemeldeten Nutzer ueber die uebergebene ID zurueck"
    )
    public TemplateInstance get(@Context SecurityContext sec, @NotNull @PathParam("id") Long id) {
        Nutzer kunde = this.eingeloggterKunde(sec);
        if(kunde == null) return Templates.error(Response.Status.BAD_REQUEST.getStatusCode(), "Bestellung nicht gefunden");
        Eigenkreation eigenkreation = null;
        if(kunde.getRole().equals("Admin")){
            eigenkreation = eigenkreationRepo.getById(id);
        }else{
            eigenkreation = eigenkreationRepo.getById(id, kunde);
        }
        if(eigenkreation != null) { 
            EigenkreationDTO eigenkreationDTO = EigenkreationDTO.Converter.toDTO(eigenkreation);
            return Templates.eigenkreationEinzeln(eigenkreationDTO);
        }
        return Templates.error(Response.Status.BAD_REQUEST.getStatusCode(), "Bestellung nicht gefunden");
    }

    @PUT
    @Transactional
    @RolesAllowed({"Admin", "Kunde"})
    @Operation(
        summary = "Aendern einer bestimmten Eigenkreation",
        description = "Aendert eine bestimmte Eigenkreation eines angemeldeten Nutzers ueber die uebergebene ID"
    )
    public Response put(@Context SecurityContext sec, @NotNull @PathParam("id") Long id, @Valid KreationIdDTO kreationIdDTO) {
        Nutzer kunde = this.eingeloggterKunde(sec);
        if(kunde == null) return Response.status(Status.NOT_FOUND).build();
        Eis eissorte1 = eisRepo.getById(kreationIdDTO.eissorte1Id);
        Eis eissorte2 = eisRepo.getById(kreationIdDTO.eissorte2Id);
        List<Zutat> zutaten = new ArrayList<>();
        for(Long zutatId : kreationIdDTO.zutatenId) {
            zutaten.add(zutatRepo.getById(zutatId));
        }
        Sauce sauce = sauceRepo.getById(kreationIdDTO.sauceId);
        Eigenkreation eigenkreation = new Eigenkreation(null, eissorte1, eissorte2, zutaten, sauce, kreationIdDTO.name);
        eigenkreationRepo.put(id, eigenkreation, kunde);
        return Response.ok().build();
    }
    
    @POST
    @Transactional
    @RolesAllowed({"Admin", "Kunde"})
    @Operation(
        summary = "Fuegt eine bestimmte Eigenkreation der Bestellung hinzu",
        description = "Fuegt eine bereits existierende Eigenkreation ueber die uerbergebene ID, " + 
                        "eines angemeldeten Nutzers, der aktuellen Bestellung hinzu"
    )
    public Response post(@Context SecurityContext sec, @NotNull @PathParam("id") Long id, @NotNull @PositiveOrZero Long anzahl) {
        Nutzer kunde = this.eingeloggterKunde(sec);
        if(kunde == null) return Response.status(Status.NOT_FOUND).build();
        eigenkreationRepo.post(id, anzahl, kunde);
        return Response.ok().header("Refresh", "0; url=/bestellungen").build();
    }
    
    @PUT
    @Transactional
    @RolesAllowed({"Admin", "Kunde"})
    @Path("/zutaten/{zutatnummer:\\d+}")
    @Operation(
        summary = "Aendern einer Zutat einer bestimmten Eigenkreation",
        description = "Aendern einer Zutaten einer bestimmten Eigenkreation eines angemeldeten Nutzers ueber die uebergebene ID"
    )
    public Response putZutaten(@Context SecurityContext sec, @NotNull @PathParam("id") Long id, @NotNull @PathParam("zutatnummer") int zutatnummer, @NotNull Long neueZutatId) {
        Nutzer kunde = this.eingeloggterKunde(sec);
        if(kunde == null) return Response.status(Status.NOT_FOUND).build();
        eigenkreationRepo.putZutat(id, --zutatnummer, neueZutatId, kunde);
        return Response.ok().build();
    }

    @DELETE
    @Transactional
    @RolesAllowed({"Admin", "Kunde"})
    @Path("/loeschen")
    @Operation(
        summary = "Loeschen einer bestimmten Eigenkreation",
        description = "Loeschen einer bestimmten Eigenkreation eines angemeldeten Nutzers ueber die uebergebene ID"
    )
    public Response delete(@Context SecurityContext sec, @NotNull @PathParam("id") Long id) {
        Nutzer kunde = this.eingeloggterKunde(sec);
        if(kunde == null) return Response.status(Status.NOT_FOUND).build();
        eigenkreationRepo.delete(id, kunde);
        return Response.ok().header("Refresh", "0; url=/bestellungen").build();
    }

    /**
     * 
     * @param sec
     * @return
     */
    private Nutzer eingeloggterKunde(SecurityContext sec) {
        Principal user = sec.getUserPrincipal();
        if(user == null) return null;
        Optional<Nutzer> optKunde = Nutzer.find("name", user.getName()).firstResultOptional();
        if(optKunde.isEmpty()) return null;
        return optKunde.get();
    }
}
