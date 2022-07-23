package de.hsos.swa.coldstoneicecreator.produkt.boundary.rest;
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
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.faulttolerance.Retry;
import org.eclipse.microprofile.faulttolerance.Timeout;

import javax.ws.rs.QueryParam;

import de.hsos.swa.coldstoneicecreator.produkt.boundary.dto.ZutatDTO;
import de.hsos.swa.coldstoneicecreator.produkt.control.ZutatControl;
import de.hsos.swa.coldstoneicecreator.produkt.entity.Allergene;
import de.hsos.swa.coldstoneicecreator.produkt.entity.Zutat;

@RequestScoped
@Path("/api/zutaten")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Retry(maxRetries = 4)
@Timeout(250)
public class ZutatenResource {
    
    @Inject
    ZutatControl zutatRepo;

    @GET
    @RolesAllowed({"Admin", "Kunde"})
    @Operation(
        summary = "Gibt alle Zutaten zureuck",
        description = "Gibt alle Zutaten mit dem eingegebenen Filter zurueck"
    )
    public Response get(@Valid @QueryParam("Allergene") List<Allergene> allergene) {
        List<Zutat> alle = zutatRepo.get();
        if(allergene != null) {
            alle = zutatRepo.getOhneAllergene(allergene);
        }
        List<ZutatDTO> alleDTO = new ArrayList<>();
        for(Zutat zutat : alle) {
            alleDTO.add(ZutatDTO.Converter.toDTO(zutat));
        }
        return Response.ok(alleDTO).build();
    }

    @POST
    @Transactional
    @RolesAllowed({"Admin"})
    @Operation(
        summary = "Erstellen einer neuen Zutat",
        description = "Erstellen einer neuen Zutat"
    )
    public Response post(@Valid @NotNull ZutatDTO zutatDTO) {
        if(zutatDTO == null) return Response.status(Status.NOT_FOUND).build();
        Zutat zutat = ZutatDTO.Converter.toZutat(zutatDTO);
        zutatRepo.create(zutat); 
        return Response.ok().build();
    }
}
