//@author Stefan Bierenriede 852142

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
import javax.validation.constraints.PositiveOrZero;
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

import de.hsos.swa.coldstoneicecreator.kreationen.boundary.dto.HauskreationDTO;
import de.hsos.swa.coldstoneicecreator.kreationen.boundary.dto.KreationIdDTO;
import de.hsos.swa.coldstoneicecreator.kreationen.control.HauskreationControl;
import de.hsos.swa.coldstoneicecreator.kreationen.entity.Hauskreation;
import de.hsos.swa.coldstoneicecreator.nutzer.entity.Nutzer;
import de.hsos.swa.coldstoneicecreator.produkt.control.EisControl;
import de.hsos.swa.coldstoneicecreator.produkt.control.SauceControl;
import de.hsos.swa.coldstoneicecreator.produkt.control.ZutatControl;
import de.hsos.swa.coldstoneicecreator.produkt.entity.Eis;
import de.hsos.swa.coldstoneicecreator.produkt.entity.Sauce;
import de.hsos.swa.coldstoneicecreator.produkt.entity.Zutat;

@RequestScoped
@Path("/api/hauskreationen/{id:\\d+}")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Retry(maxRetries = 4)
@Timeout(250)
public class HauskreationIdResource {
    
    @Inject
    HauskreationControl hauskreationRepo;
    
    @Inject
    EisControl eisRepo;

    @Inject
    ZutatControl zutatRepo;
    
    @Inject
    SauceControl sauceRepo;

    @GET
    @RolesAllowed({"Admin", "Kunde"})
    @Operation(
        summary = "Gibt eine bestimmte Hauskreation zurueck",
        description = "Gibt eine bestimmte Hauskreation mit der uebergebenen ID zurueck"
    )
    public Response get(@NotNull @PathParam("id") Long id) {
        Hauskreation hauskreation = hauskreationRepo.getById(id);
        if(hauskreation != null) {
            HauskreationDTO hauskreationDTO = HauskreationDTO.Converter.toDTO(hauskreation);
            return Response.ok(hauskreationDTO).build();
        }
        return Response.status(Status.NOT_FOUND).build();
    }

    @PUT
    @Transactional
    @RolesAllowed({"Admin"})
    @Operation(
        summary = "Aendern einer bestimmten Hauskreation",
        description = "Aendern einer bestimmmten Hauskreation ueber die uebergebene ID"
    )
    public Response put(@NotNull @PathParam("id") Long id, @Valid @NotNull KreationIdDTO kreationIdDTO) {
        Eis eissorte1 = eisRepo.getById(kreationIdDTO.eissorte1Id);
        Eis eissorte2 = eisRepo.getById(kreationIdDTO.eissorte2Id);
        List<Zutat> zutaten = new ArrayList<>();
        for(Long zutatId : kreationIdDTO.zutatenId) {
            zutaten.add(zutatRepo.getById(zutatId));
        }
        Sauce sauce = sauceRepo.getById(kreationIdDTO.sauceId);
        Hauskreation hauskreation = new Hauskreation(null, eissorte1, eissorte2, zutaten, sauce, kreationIdDTO.name);
        hauskreationRepo.put(id, hauskreation);
        return Response.ok().build();
    }
    
    @POST
    @Transactional
    @RolesAllowed({"Admin", "Kunde"})
    @Operation(
        summary = "Fuegt eine bestimmte Hauskreation der Bestellung hinzu",
        description = "Fuegt eine bestimmte Hauskreation über die uerbergebenen ID der aktuellen Bestellung hinzu"
    )
    public Response post(@Context SecurityContext sec, @NotNull @PathParam("id") Long id, @NotNull @PositiveOrZero Long anzahl) {
        Nutzer nutzer = this.eingeloggterKunde(sec);
        if(nutzer == null) return Response.status(Status.NOT_FOUND).build();
        Hauskreation hauskreation = hauskreationRepo.getById(id);
        hauskreationRepo.create(nutzer, hauskreation, anzahl);
        return Response.ok().build();
    }

    @DELETE
    @Transactional
    @RolesAllowed({"Admin"})
    @Operation(
        summary = "Loeschen einer bestimmten Hauskreation",
        description = "Loeschen einer bestimmten Hauskreation ueber die uebergebene ID"
    )
    public Response delete(@NotNull @PathParam("id") Long id) {
        hauskreationRepo.delete(id);
        return Response.noContent().build();
    }

    private Nutzer eingeloggterKunde(SecurityContext sec) {
        Principal user = sec.getUserPrincipal();
        if(user == null) return null;
        Optional<Nutzer> optKunde = Nutzer.find("name", user.getName()).firstResultOptional();
        if(optKunde.isEmpty()) return null;
        return optKunde.get();
    }
}
