package de.hsos.swa.coldstoneicecreator.kreationen.boundary.rest;
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
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import de.hsos.swa.coldstoneicecreator.kreationen.boundary.dto.HauskreationDTO;
import de.hsos.swa.coldstoneicecreator.kreationen.control.HauskreationControl;
import de.hsos.swa.coldstoneicecreator.kreationen.entity.Hauskreation;

@RequestScoped
@Path("/hauskreationen")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class HauskreationResource {
    
    @Inject
    HauskreationControl hc;

    @GET
    @RolesAllowed({"Admin", "Kunde"})
    public Response get() {
        List<Hauskreation> alle = hc.get();
        List<HauskreationDTO> alleDTO = new ArrayList<>();
        for(Hauskreation hauskreation : alle) {
            alleDTO.add(HauskreationDTO.Converter.toDTO(hauskreation));
        } 
        return Response.ok(alleDTO).build();
    }

    @POST
    @Transactional
    @RolesAllowed({"Admin"})
    public Response post(HauskreationDTO hauskreationDTO) {
        Hauskreation hauskreation = HauskreationDTO.Converter.toHauskreation(hauskreationDTO);
        hc.create(hauskreation);
        return Response.ok().build();
    }
}
