package de.hsos.swa.coldstoneicecreator.kreationen.boundary.rest;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.Response.Status;

import de.hsos.swa.coldstoneicecreator.kreationen.boundary.dto.EigenkreationDTO;
import de.hsos.swa.coldstoneicecreator.kreationen.control.EigenkreationControl;
import de.hsos.swa.coldstoneicecreator.kreationen.entity.Eigenkreation;
import de.hsos.swa.coldstoneicecreator.kunden.entity.Nutzer;

@RequestScoped
@Path("/eigenkreationen")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class EigenkreationResource {
    
    @Inject
    EigenkreationControl cr;

    @GET
    @RolesAllowed({"Admin", "Kunde"})
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
    @RolesAllowed({"Admin", "Kunde"})
    public Response post(@Context SecurityContext sec, EigenkreationDTO eigenkreationDTO) {
        Nutzer kunde = this.eingeloggterKunde(sec);
        if(kunde == null) return Response.status(Status.BAD_REQUEST).build();
        Eigenkreation eigenkreation = EigenkreationDTO.Converter.toEigenkreation(eigenkreationDTO);
        cr.create(kunde, eigenkreation);
        return Response.ok().build();
    }

    private Nutzer eingeloggterKunde(SecurityContext sec) {
        Principal user = sec.getUserPrincipal();
        if(user == null) return null;
        Optional<Nutzer> optKunde = Nutzer.find("name", user.getName()).firstResultOptional();
        if(optKunde.isEmpty()) return null;
        return optKunde.get();
    }
}
