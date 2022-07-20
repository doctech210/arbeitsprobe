package de.hsos.swa.coldstoneicecreator.bestellung.boundary.rest;

import de.hsos.swa.coldstoneicecreator.bestellung.control.BestellungControl;
import de.hsos.swa.coldstoneicecreator.bestellung.entity.BestellpostenEigen;
import de.hsos.swa.coldstoneicecreator.bestellung.entity.BestellpostenHaus;
import de.hsos.swa.coldstoneicecreator.bestellung.entity.Bestellung;
import de.hsos.swa.coldstoneicecreator.bestellung.boundary.dto.BestellpostenEigenDTO;
import de.hsos.swa.coldstoneicecreator.bestellung.boundary.dto.BestellpostenHausDTO;
import de.hsos.swa.coldstoneicecreator.bestellung.boundary.dto.BestellungDTO;
import de.hsos.swa.coldstoneicecreator.kunden.entity.Nutzer;

import java.util.Optional;

import java.security.Principal;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.DELETE;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.PathParam;
import javax.annotation.security.RolesAllowed;

@RequestScoped
@Path("bestellungen/{id:\\d+}")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class BestellungIdResource {

    @Inject
    BestellungControl bestellungRepo;

    @GET
    @RolesAllowed({"Admin", "Kunde"})
    public Response get(@Context SecurityContext sec, @PathParam("id") Long id) {
        Nutzer kunde = this.eingeloggterKunde(sec);
        Bestellung bestellung = bestellungRepo.bestellungAbfragen(id, kunde.getId());
        BestellungDTO bestellungDTO = BestellungDTO.Converter.toDTO(bestellung);
        return Response.ok(bestellungDTO).build();
    } 

    @POST
    @Transactional
    @RolesAllowed({"Admin", "Kunde"})
    public Response post(@Context SecurityContext sec, @PathParam("id") Long id) {
        /*Nutzer kunde = this.eingeloggterKunde(sec);
        Bestellung bestellung = this.offeneBestellung(kunde);
        if(bestellung == null) return Response.status(Status.NOT_FOUND).build();
        bestellung.setBestellt(true);        
        BestellungDTO bestellungDTO = BestellungDTO.Converter.toDTO(bestellung);
        */return Response.ok().build();
    }

    @PUT
    @Transactional
    @RolesAllowed({"Admin", "Kunde"})
    @Path("/eigenkreationen/{kreationsnummer:\\d+}")
    public Response putEigen(@Context SecurityContext sec, @PathParam("id") Long id, @PathParam("kreationsnummer") int kreationsnummer, BestellpostenEigenDTO bestellpostenEigenDTO){
        Nutzer kunde = this.eingeloggterKunde(sec);
        Bestellung bestellung = bestellungRepo.bestellungAbfragen(id, kunde.getId());
        BestellpostenEigen bestellpostenEigen = BestellpostenEigenDTO.Converter.toBestellposten(bestellpostenEigenDTO);
        bestellungRepo.eigenkreationAnpassen(bestellung, kreationsnummer, bestellpostenEigen);
        return Response.ok().build();
    }

    @PUT
    @Transactional
    @RolesAllowed({"Admin", "Kunde"})
    @Path("/hauskreationen/{kreationsnummer:\\d+}")
    public Response putHaus(@Context SecurityContext sec, @PathParam("id") Long id, @PathParam("kreationsnummer") int kreationsnummer, BestellpostenHausDTO bestellpostenHausDTO){
        Nutzer kunde = this.eingeloggterKunde(sec);
        Bestellung bestellung = bestellungRepo.bestellungAbfragen(id, kunde.getId());
        BestellpostenHaus bestellpostenHaus = BestellpostenHausDTO.Converter.toBestellposten(bestellpostenHausDTO);
        bestellungRepo.hauskreationAnpassen(bestellung, kreationsnummer, bestellpostenHaus);
        return Response.ok().build();
    }

    @DELETE
    @Transactional
    @RolesAllowed({"Admin", "Kunde"})
    public Response delete(@Context SecurityContext sec, @PathParam("id") Long id){
        Nutzer kunde = this.eingeloggterKunde(sec);
        bestellungRepo.bestellungLoeschen(id, kunde.getId());
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