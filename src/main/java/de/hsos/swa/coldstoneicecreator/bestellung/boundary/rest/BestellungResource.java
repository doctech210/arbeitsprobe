package de.hsos.swa.coldstoneicecreator.bestellung.boundary.rest;

import de.hsos.swa.coldstoneicecreator.bestellung.control.BestellpostenControl;
import de.hsos.swa.coldstoneicecreator.bestellung.control.BestellungControl;
import de.hsos.swa.coldstoneicecreator.bestellung.entity.Bestellung;
import de.hsos.swa.coldstoneicecreator.bestellung.boundary.dto.BestellungDTO;
import de.hsos.swa.coldstoneicecreator.kunden.entity.Nutzer;

import java.util.List;
import java.util.Optional;
import java.util.ArrayList;

import java.security.Principal;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.DELETE;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.Response.Status;
import javax.annotation.security.RolesAllowed;

import org.eclipse.microprofile.openapi.annotations.Operation;

@RequestScoped
@Path("/bestellungen/")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class BestellungResource {

    @Inject
    BestellungControl bestellungRepo;

    @Inject
    BestellpostenControl bestellpostenRepo;

    @GET
    @RolesAllowed({"Admin", "Kunde"})
    @Operation(
        summary = "Gibt alle Bestellungen des Nutzers zurueck",
        description = "Gibt alle Bestellungen des angemeldeten Nutzers zurueck"
    )
    public Response get(@Context SecurityContext sec) {
        Nutzer kunde = this.eingeloggterKunde(sec);
        if(kunde == null) Response.status(Status.NOT_FOUND).build();
        List<Bestellung> alle = null;
        if(kunde.getRole().equals("Admin")){
            alle = bestellungRepo.bestellungenAbfragen();
        }else{
            alle = bestellungRepo.bestellungenAbfragen(kunde.getId());
        }
        List<BestellungDTO> alleDTO = new ArrayList<>();
        for(Bestellung bestellung : alle) {
            alleDTO.add(BestellungDTO.Converter.toDTO(bestellung));
        }
        return Response.ok(alleDTO).build();
    } 

    @POST
    @Transactional
    @RolesAllowed({"Admin", "Kunde"})
    @Operation(
        summary = "Schickt die aktuelle Bestellung ab",
        description = "Schickt die aktuelle Bestellung des angemeldeten Nutzers ab"
    )
    public Response post(@Context SecurityContext sec) {
        Nutzer kunde = this.eingeloggterKunde(sec);
        if(kunde == null) Response.status(Status.NOT_FOUND).build();
        Bestellung bestellung = this.offeneBestellung(kunde);
        if(bestellung == null) return Response.status(Status.NOT_FOUND).build();
        bestellung.setBestellt(true);        
        BestellungDTO bestellungDTO = BestellungDTO.Converter.toDTO(bestellung);
        return Response.ok(bestellungDTO).build();
    }

    @DELETE
    @Transactional
    @RolesAllowed("Kunde")
    @Operation(
        summary = "Loescht die aktuelle Bestellung",
        description = "Loescht die aktuelle, nicht abgeschickte Bestellung des angemeldeten Nutzers"
    )
    public Response delete(@Context SecurityContext sec){
        Nutzer kunde = this.eingeloggterKunde(sec);
        if(kunde == null) return Response.status(Status.NOT_FOUND).build();
        Bestellung bestellung = this.offeneBestellung(kunde);
        if(bestellung == null) return Response.status(Status.NOT_FOUND).build();
        bestellungRepo.bestellungLoeschen(bestellung.getId());
        return Response.noContent().build();
    }

    /**
     * 
     * @param kunde
     * @return
     */
    private Bestellung offeneBestellung(Nutzer kunde) {
        for(Bestellung b : kunde.getBestellungen()){
            if(!b.isBestellt()) return b;
        } 
        return null;
    }

    /**
     * 
     * @param sec
     * @return
     */
    private Nutzer eingeloggterKunde(SecurityContext sec) {
        Principal user = sec.getUserPrincipal();
        if(user == null) return null;
        Optional<Nutzer> optKunde = Nutzer.find("name", user.getName()).firstResultOptional();
        if(optKunde.isEmpty()) return null;
        return optKunde.get();
    }
}