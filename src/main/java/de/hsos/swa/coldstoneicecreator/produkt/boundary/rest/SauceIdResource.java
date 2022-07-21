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

import de.hsos.swa.coldstoneicecreator.produkt.boundary.dto.SauceDTO;
import de.hsos.swa.coldstoneicecreator.produkt.control.SauceControl;
import de.hsos.swa.coldstoneicecreator.produkt.entity.Sauce;

@RequestScoped
@Path("/saucen/{id:\\d+}")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SauceIdResource {

    @Inject
    SauceControl sauceRepo;
    
    @GET
    @RolesAllowed({"Admin", "Kunde"})
    @Operation(
        summary = "Gibt eine bestimmte Sauce zurueck",
        description = "Gibt eine bestimmte Sauce ueber die uebergebene ID zurueck"
    )
    public Response get(@NotNull @PathParam("id") Long id) {
        Sauce sauce = sauceRepo.getById(id);
        if(sauce != null) {
            SauceDTO sauceDTO = SauceDTO.Converter.toDTO(sauce);
            return Response.ok(sauceDTO).build();
        }
        return Response.status(Status.STATUS_UNKNOWN).build();
    }

    @PUT
    @Transactional
    @RolesAllowed({"Admin"})
    @Operation(
        summary = "Aendern einer bestimmten Sauce",
        description = "Aendern einer bestimmte Sauce ueber die uebergebene ID"
    )
    public Response put(@NotNull @PathParam("id") Long id, @Valid @NotNull SauceDTO sauceDTO) {
        Sauce sauce = SauceDTO.Converter.toSauce(sauceDTO);
        sauceRepo.put(id, sauce);
        return Response.ok().build();
    }

    @DELETE
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
