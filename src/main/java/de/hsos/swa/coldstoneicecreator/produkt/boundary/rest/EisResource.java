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
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.faulttolerance.Retry;
import org.eclipse.microprofile.faulttolerance.Timeout;

import de.hsos.swa.coldstoneicecreator.produkt.boundary.dto.EisDTO;
import de.hsos.swa.coldstoneicecreator.produkt.control.EisControl;
import de.hsos.swa.coldstoneicecreator.produkt.entity.Allergene;
import de.hsos.swa.coldstoneicecreator.produkt.entity.Eis;

@RequestScoped
@Path("/api/eis")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Retry(maxRetries = 4)
@Timeout(250)
public class EisResource {
    
    @Inject
    EisControl eisRepo;

    @GET
    @RolesAllowed({"Admin", "Kunde"})
    @Operation(
        summary = "Gibt alle Eissorten zurueck",
        description = "Gibt alle Eissorten ueber den eingestellten Filter zurueck"
    )
    public Response get(@Valid @QueryParam("Allergene") List<Allergene> allergene) {
        List<Eis> alle = eisRepo.get();
        if(allergene != null) {
            alle = eisRepo.getOhneAllergene(allergene);
        }
        List<EisDTO> alleDTO = new ArrayList<>();
        for(Eis eis : alle) {
            alleDTO.add(EisDTO.Converter.toDTO(eis));
        }
        return Response.ok(alleDTO).build();
    }

    @POST
    @Transactional
    @RolesAllowed({"Admin"})
    @Operation(
        summary = "Erstellen eine neue Eissorte",
        description = "Erstellen einer neuen Eissorte"
    )
    public Response post(@Valid @NotNull EisDTO eisDTO) {
        if(eisDTO == null) return Response.status(Status.NOT_FOUND).build();
        Eis eis = EisDTO.Converter.toEis(eisDTO);
        eisRepo.create(eis);
        return Response.ok().build();
    }
}
