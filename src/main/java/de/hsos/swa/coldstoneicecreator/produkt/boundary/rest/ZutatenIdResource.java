package de.hsos.swa.coldstoneicecreator.produkt.boundary.rest;
import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Status;
import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.faulttolerance.Retry;
import org.eclipse.microprofile.faulttolerance.Timeout;

import de.hsos.swa.coldstoneicecreator.produkt.boundary.dto.ZutatDTO;
import de.hsos.swa.coldstoneicecreator.produkt.control.ZutatControl;
import de.hsos.swa.coldstoneicecreator.produkt.entity.Zutat;

@RequestScoped
@Path("/api/zutaten/{id:\\d+}")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Retry(maxRetries = 4)
@Timeout(250)
public class ZutatenIdResource {
    
    @Inject
    ZutatControl zutatRepo;

    @GET
    @RolesAllowed({"Admin", "Kunde"})
    @Operation(
        summary = "Gibt eine bestimmte Zutat zurueck",
        description = "Gibt eine bestimmte Zutat ueber die uebergebene ID zurueck"
    )
    public Response get(@NotNull @PathParam("id") Long id) {
        Zutat zutat = zutatRepo.getById(id);
        if(zutat != null) {
            ZutatDTO zutatDTO = ZutatDTO.Converter.toDTO(zutat);
            return Response.ok(zutatDTO).build();
        }
        return Response.status(Status.STATUS_UNKNOWN).build();
    }

    @PUT
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

    @DELETE
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
