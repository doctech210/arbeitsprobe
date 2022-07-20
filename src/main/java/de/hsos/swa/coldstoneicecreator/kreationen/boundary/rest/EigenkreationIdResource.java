package de.hsos.swa.coldstoneicecreator.kreationen.boundary.rest;
import java.security.Principal;
import java.util.Optional;

import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Status;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import de.hsos.swa.coldstoneicecreator.kreationen.boundary.dto.EigenkreationDTO;
import de.hsos.swa.coldstoneicecreator.kreationen.control.EigenkreationControl;
import de.hsos.swa.coldstoneicecreator.kreationen.entity.Eigenkreation;
import de.hsos.swa.coldstoneicecreator.kunden.entity.Nutzer;

@RequestScoped
@Path("/eigenkreationen/{id:\\d+}")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class EigenkreationIdResource {
    
    @Inject 
    EigenkreationControl eigenkreationRepo;

    @GET
    @RolesAllowed({"Admin", "Kunde"})
    public Response get(@PathParam("id") Long id) {
        Eigenkreation eigenkreation = eigenkreationRepo.getById(id);
        if(eigenkreation != null) { 
            EigenkreationDTO eigenkreationDTO = EigenkreationDTO.Converter.toDTO(eigenkreation);
            return Response.ok(eigenkreationDTO).build();
        }
        return Response.status(Status.STATUS_UNKNOWN).build();
    }

    @PUT
    @Transactional
    @RolesAllowed({"Admin", "Kunde"})
    public Response put(@PathParam("id") Long id, EigenkreationDTO eigenkreationDTO) {
        Eigenkreation eigenkreation = EigenkreationDTO.Converter.toEigenkreation(eigenkreationDTO);
        eigenkreationRepo.put(id, eigenkreation);
        return Response.ok().build();
    }
    
    @POST
    @Transactional
    @RolesAllowed({"Admin", "Kunde"})
    public Response post(@Context SecurityContext sec, @PathParam("id") Long id, Long anzahl) {
        Nutzer kunde = this.eingeloggterKunde(sec);
        eigenkreationRepo.post(id, anzahl, kunde);
        return Response.ok().build();
    }
    
    @PUT
    @Transactional
    @RolesAllowed({"Admin", "Kunde"})
    @Path("/zutaten/{zutatnummer:\\d+}")
    public Response putZutaten(@PathParam("id") Long id, @PathParam("zutatnummer") int zutatnummer, Long neueZutatId) {
        eigenkreationRepo.putZutat(id, --zutatnummer, neueZutatId);
        return Response.ok().build();
    }

    @DELETE
    @Transactional
    @RolesAllowed({"Admin", "Kunde"})
    public Response delete(@PathParam("id") Long id) {
        eigenkreationRepo.delete(id);
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
