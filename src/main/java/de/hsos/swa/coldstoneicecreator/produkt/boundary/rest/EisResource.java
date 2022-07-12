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
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import de.hsos.swa.coldstoneicecreator.produkt.boundary.dto.EisDTO;
import de.hsos.swa.coldstoneicecreator.produkt.control.EisControl;
import de.hsos.swa.coldstoneicecreator.produkt.entity.Eis;

@RequestScoped
@Path("/eis")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class EisResource {
    
    @Inject
    EisControl ec;

    @GET
    @RolesAllowed("Admin, Kunde")
    public Response get() {
        List<Eis> alle = ec.get();
        List<EisDTO> alleDTO = new ArrayList<>();
        for(Eis eis : alle) {
            alleDTO.add(EisDTO.Converter.toDTO(eis));
        }
        return Response.ok(alleDTO).build();
    }

    @POST
    @Transactional
    @RolesAllowed("Admin")
    public Response post(EisDTO eisDTO) {
        Eis eis = EisDTO.Converter.toEis(eisDTO);
        ec.create(eis);
        return Response.ok().build();
    }
}
