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

import de.hsos.swa.coldstoneicecreator.kreationen.boundary.dto.EigenkreationDTO;
import de.hsos.swa.coldstoneicecreator.kreationen.control.EigenkreationControl;
import de.hsos.swa.coldstoneicecreator.kreationen.entity.Eigenkreation;

@RequestScoped
@Path("/eigenkreationen")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class EigenkreationResource {
    
    @Inject
    EigenkreationControl cr;

    @GET
    @RolesAllowed("Admin, Kunde")
    public Response get() {
        List<Eigenkreation> alle = cr.get();
        List<EigenkreationDTO> alleDTO = new ArrayList<>();
        for(Eigenkreation eigenkreation : alle) {
            alleDTO.add(EigenkreationDTO.Converter.toDTO(eigenkreation));
        }
        return Response.ok(alleDTO).build();
    }

    @POST
    @Transactional
    @RolesAllowed("Admin, Kunde")
    public Response post(EigenkreationDTO eigenkreationDTO) {
        Eigenkreation eigenkreation = EigenkreationDTO.Converter.toEigenkreation(eigenkreationDTO);
        cr.create(eigenkreation);
        return Response.ok().build();
    }
}
