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

import de.hsos.swa.coldstoneicecreator.produkt.boundary.dto.SauceDTO;
import de.hsos.swa.coldstoneicecreator.produkt.control.SauceControl;
import de.hsos.swa.coldstoneicecreator.produkt.entity.Allergene;
import de.hsos.swa.coldstoneicecreator.produkt.entity.Sauce;

@RequestScoped
@Path("/saucen")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SauceResource {
    
    @Inject
    SauceControl sauceRepo;

    @GET
    @RolesAllowed({"Admin", "Kunde"})
    @Operation(
        summary = "Gibt alle Saucen zurueck",
        description = "Gitb alle Saucen mit dem angegebenen Filter zurueck"
    )
    public Response get(@Valid @QueryParam("Allergene") List<Allergene> allergene) {
        List<Sauce> alle = sauceRepo.get();
        if(allergene != null){
            alle = sauceRepo.getOhneAllergene(allergene);
        }
        List<SauceDTO> alleDTO = new ArrayList<>();
        for(Sauce sauce : alle) {
            alleDTO.add(SauceDTO.Converter.toDTO(sauce));
        }
        return Response.ok(alleDTO).build();
    }

    @POST
    @Transactional
    @RolesAllowed({"Admin"})
    @Operation(
        summary = "Erstellen einer neuen Sauce",
        description = "Erstellen einer neuen Sauce"
    )
    public Response post(@Valid @NotNull SauceDTO sauceDTO) {
        if(sauceDTO == null) return Response.status(Status.NOT_FOUND).build();
        Sauce sauce = SauceDTO.Converter.toSauce(sauceDTO);
        sauceRepo.create(sauce);
        return Response.ok().build();
    }
}
