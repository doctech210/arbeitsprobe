package de.hsos.swa.coldstoneicecreator.kreationen.boundary.rest;
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
import javax.ws.rs.core.Response;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.faulttolerance.Retry;
import org.eclipse.microprofile.faulttolerance.Timeout;

import de.hsos.swa.coldstoneicecreator.kreationen.boundary.dto.HauskreationDTO;
import de.hsos.swa.coldstoneicecreator.kreationen.boundary.dto.KreationIdDTO;
import de.hsos.swa.coldstoneicecreator.kreationen.control.HauskreationControl;
import de.hsos.swa.coldstoneicecreator.kreationen.entity.Hauskreation;
import de.hsos.swa.coldstoneicecreator.produkt.entity.Allergene;
import de.hsos.swa.coldstoneicecreator.produkt.entity.Eis;
import de.hsos.swa.coldstoneicecreator.produkt.entity.Sauce;
import de.hsos.swa.coldstoneicecreator.produkt.entity.Zutat;
import de.hsos.swa.coldstoneicecreator.produkt.gateway.EisRepository;
import de.hsos.swa.coldstoneicecreator.produkt.gateway.SauceRepository;
import de.hsos.swa.coldstoneicecreator.produkt.gateway.ZutatRepository;

@RequestScoped
@Path("/api/hauskreationen")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Retry(maxRetries = 4)
@Timeout(250)
public class HauskreationResource {
    
    @Inject
    HauskreationControl hauskreationRepo;

    @Inject
    EisRepository eisRepo;

    @Inject
    SauceRepository sauceRepo;

    @Inject
    ZutatRepository ZutatRepo;

    @GET
    @RolesAllowed({"Admin", "Kunde"})
    @Operation(
        summary = "Gibt alle Hauskreationen zurueck",
        description = "Gibt alle Hauskreationen mit dem angegebenen Filter zurueck"
    )
    public Response get(@Valid @QueryParam("Allergene") List<Allergene> allergene) {
        List<Hauskreation> alle = hauskreationRepo.get();
        if(allergene != null) {
            alle = hauskreationRepo.getOhneAllergene(allergene);
        }
        List<HauskreationDTO> alleDTO = new ArrayList<>();
        for(Hauskreation hauskreation : alle) {
            alleDTO.add(HauskreationDTO.Converter.toDTO(hauskreation));
        } 
        return Response.ok(alleDTO).build();
    }

    @POST
    @Transactional
    @RolesAllowed({"Admin"})
    @Operation(
        summary = "Erstellt eine neue Hauskreation",
        description = "Erstellt eine neue Hauskreation"
    )
    public Response post(@Valid @NotNull KreationIdDTO kreationIds) {
        Eis eissorte1 = eisRepo.getById(kreationIds.eissorte1Id);
        Eis eissorte2 = eisRepo.getById(kreationIds.eissorte2Id);
        Sauce sauce = sauceRepo.getById(kreationIds.sauceId);
        List<Zutat> zutaten = new ArrayList<>();
        for(Long id : kreationIds.zutatenId) {
            zutaten.add(ZutatRepo.getById(id));
        }
        Hauskreation hauskreation = new Hauskreation(null, eissorte1, eissorte2, zutaten, sauce, kreationIds.name);
        hauskreationRepo.create(hauskreation);
        return Response.ok().build();
    }
}
