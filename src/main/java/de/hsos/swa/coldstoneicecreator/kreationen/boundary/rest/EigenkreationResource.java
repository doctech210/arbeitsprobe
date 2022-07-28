package de.hsos.swa.coldstoneicecreator.kreationen.boundary.rest;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
import de.hsos.swa.coldstoneicecreator.nutzer.entity.Nutzer;
import de.hsos.swa.coldstoneicecreator.produkt.entity.*;
import de.hsos.swa.coldstoneicecreator.produkt.gateway.EisRepository;
import de.hsos.swa.coldstoneicecreator.produkt.gateway.SauceRepository;
import de.hsos.swa.coldstoneicecreator.produkt.gateway.ZutatRepository;

@RequestScoped
@Path("/api/eigenkreationen")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Retry(maxRetries = 4)
@Timeout(250)
public class EigenkreationResource {
    
    @Inject
    EigenkreationControl eigenkreationRepo;

    @Inject
    EisRepository eisRepo;

    @Inject
    SauceRepository sauceRepo;

    @Inject
    ZutatRepository ZutatRepo;

    @GET
    @RolesAllowed({"Admin", "Kunde"})
    @Operation(
        summary = "Gibt alle Eigenkreationen des Nutzer zurueck",
        description = "Gibt alle Eigenkreationen des angemeldeten Nutzers mit dem eingestellten Filter zurueck"
    )
    public Response get(@Valid @QueryParam("Allergene") List<Allergene> allergene, @Context SecurityContext sec) {
        Nutzer nutzer = this.eingeloggterKunde(sec);
        if(nutzer == null) return Response.status(Status.NOT_FOUND).build();
        List<Eigenkreation> alle = nutzer.getEigenkreationen();
        if(allergene != null) alle = eigenkreationRepo.getOhneAllergene(allergene);
        List<EigenkreationDTO> alleDTO = new ArrayList<>();
        for(Eigenkreation eigenkreation : alle) {
            alleDTO.add(EigenkreationDTO.Converter.toDTO(eigenkreation));
        }
        return Response.ok(alleDTO).build();
    }
    
    @POST
    @Transactional
    @RolesAllowed({"Admin", "Kunde"})
    @Operation(
        summary = "Erstellt eine neue Eigenkreation",
        description = "Erstellt eine neue Eigenkreation für einen angemeldeten Nutzer und fuegt sie automatisch " +
                        "der aktuellen Bestellung hinzu"
    )
    public Response post(@Context SecurityContext sec, @Valid @NotNull KreationIdDTO eigenkreationIds) {
        Nutzer nutzer = this.eingeloggterKunde(sec);
        if(nutzer == null) return Response.status(Status.NOT_FOUND).build();
        Eis eissorte1 = eisRepo.getById(eigenkreationIds.eissorte1Id);
        Eis eissorte2 = eisRepo.getById(eigenkreationIds.eissorte2Id);
        Sauce sauce = sauceRepo.getById(eigenkreationIds.sauceId);
        List<Zutat> zutaten = new ArrayList<>();
        for(Long id : eigenkreationIds.zutatenId) {
            zutaten.add(ZutatRepo.getById(id));
        }
        Eigenkreation eigenkreation = new Eigenkreation(null, eissorte1, eissorte2, zutaten, sauce, eigenkreationIds.name);
        eigenkreationRepo.create(nutzer, eigenkreation, eigenkreationIds.anzahl);
        return Response.ok().build();
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
