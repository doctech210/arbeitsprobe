package de.hsos.swa.coldstoneicecreator.kunden.boundary.rest;
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

import de.hsos.swa.coldstoneicecreator.kunden.boundary.dto.KundeDTO;
import de.hsos.swa.coldstoneicecreator.kunden.control.KundeControl;
import de.hsos.swa.coldstoneicecreator.kunden.entity.Kunde;

@RequestScoped
@Path("/kunden")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class KundeResource {
    
    @Inject
    KundeControl kc;

    @GET
    @RolesAllowed("Admin, Kunde")
    public Response get() {
        List<Kunde> alle = kc.get();
        List<KundeDTO> alleDTO = new ArrayList<>();
        for(Kunde kunde : alle) {
            alleDTO.add(KundeDTO.Converter.toDTO(kunde));
        }
        return Response.ok(alleDTO).build();
    }

    @POST
    @Transactional
    @RolesAllowed("Admin")
    public Response post(KundeDTO kundeDTO) {
        Kunde kunde = KundeDTO.Converter.toKunde(kundeDTO);
        kc.create(kunde);
        return Response.ok().build();
    }
}
