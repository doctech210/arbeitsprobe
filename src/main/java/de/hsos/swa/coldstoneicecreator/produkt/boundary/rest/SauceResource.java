package de.hsos.swa.coldstoneicecreator.produkt.boundary.rest;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

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
    public Response get(@QueryParam("Allergene") List<Allergene> allergene) {
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
    public Response post(SauceDTO sauceDTO) {
        Sauce sauce = SauceDTO.Converter.toSauce(sauceDTO);
        sauceRepo.create(sauce);
        return Response.ok().build();
    }
}
