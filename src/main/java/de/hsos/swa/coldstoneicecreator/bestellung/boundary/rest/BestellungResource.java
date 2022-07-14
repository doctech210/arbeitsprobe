package de.hsos.swa.coldstoneicecreator.bestellung.boundary.rest;

import de.hsos.swa.coldstoneicecreator.bestellung.control.BestellungControl;
import de.hsos.swa.coldstoneicecreator.bestellung.entity.Bestellung;
import de.hsos.swa.coldstoneicecreator.bestellung.boundary.dto.BestellungDTO;
import de.hsos.swa.coldstoneicecreator.kunden.entity.Kunde;

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
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.annotation.security.RolesAllowed;

@RequestScoped
@Path("Bestellungen")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class BestellungResource {

    @Inject
    BestellungControl bc;

    @GET
    @RolesAllowed("Admin, Kunde")
    public Response get(@Context SecurityContext sec) {
        Kunde kunde = this.eingeloggterKunde(sec);
        List<Bestellung> alle = bc.bestellungenAbfragen(kunde.getId());
        List<BestellungDTO> alleDTO = new ArrayList<>();
        for(Bestellung bestellung : alle) {
            alleDTO.add(BestellungDTO.Converter.toDTO(bestellung));
        }
        return Response.ok(alleDTO).build();
    } 

    @POST
    @Transactional
    @RolesAllowed("Admin, Kunde")
    public Response post(@Context SecurityContext sec, BestellungDTO bestellungDTO) {
        Kunde kunde = this.eingeloggterKunde(sec);
        Bestellung bestellung = BestellungDTO.Converter.toBestellung(bestellungDTO);
        bc.bestellungAnlegen(bestellung, kunde.getId());
        return Response.ok().build();
    }


    private Kunde eingeloggterKunde(SecurityContext sec) {
        Principal user = sec.getUserPrincipal();
        if(user == null) return null;
        Optional<Kunde> optKunde = Kunde.find("vorname", user.getName()).firstResultOptional();
        if(optKunde.isEmpty()) return null;
        return optKunde.get();
    }
}