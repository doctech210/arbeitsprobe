package de.hsos.swa.coldstoneicecreator.bestellung.boundary.rest;

import de.hsos.swa.coldstoneicecreator.bestellung.control.BestellpostenControl;
import de.hsos.swa.coldstoneicecreator.bestellung.control.BestellungControl;
import de.hsos.swa.coldstoneicecreator.bestellung.entity.BestellpostenEigen;
import de.hsos.swa.coldstoneicecreator.bestellung.entity.BestellpostenHaus;
import de.hsos.swa.coldstoneicecreator.bestellung.entity.Bestellung;
import de.hsos.swa.coldstoneicecreator.kreationen.boundary.dao.BestellungDAO;
import de.hsos.swa.coldstoneicecreator.kreationen.entity.Eigenkreation;
import de.hsos.swa.coldstoneicecreator.kreationen.entity.Hauskreation;
import de.hsos.swa.coldstoneicecreator.bestellung.boundary.dto.BestellungDTO;
import de.hsos.swa.coldstoneicecreator.kunden.entity.Nutzer;

import java.util.List;
import java.util.Optional;
import java.util.ArrayList;

import java.security.Principal;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Observes;
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

    @Inject
    BestellpostenControl bp;

    @GET
    @RolesAllowed({"Admin", "Kunde"})
    public Response get(@Context SecurityContext sec) {
        Nutzer kunde = this.eingeloggterKunde(sec);
        List<Bestellung> alle = bc.bestellungenAbfragen(kunde.getId());
        List<BestellungDTO> alleDTO = new ArrayList<>();
        for(Bestellung bestellung : alle) {
            alleDTO.add(BestellungDTO.Converter.toDTO(bestellung));
        }
        return Response.ok(alleDTO).build();
    } 

    @POST
    @Transactional
    @RolesAllowed({"Admin", "Kunde"})
    public Response post(@Context SecurityContext sec, BestellungDTO bestellungDTO) {
        Nutzer kunde = this.eingeloggterKunde(sec);
        Bestellung bestellung = BestellungDTO.Converter.toBestellung(bestellungDTO);
        bc.bestellungAnlegen(bestellung, kunde.getId());
        return Response.ok().build();
    }

    
    public void bestellen(@Observes BestellungDAO bestellungDAO) {
        if(bestellungDAO.getKreation().getClass() == Eigenkreation.class) {
            BestellpostenEigen bestellposten = new BestellpostenEigen(null, (Eigenkreation)bestellungDAO.getKreation(), bestellungDAO.getAnzahl().intValue());
            Bestellung bestellung = new Bestellung();
            Nutzer kunde = bestellungDAO.getKunde();
            bc.bestellungAnlegen(bestellung, kunde.getId());
            bestellung.addPostenEigen(bestellposten);
        }else{
            Bestellung bestellung = new Bestellung();
            Nutzer kunde = bestellungDAO.getKunde();
            bestellung = bc.bestellungAnlegen(bestellung, kunde.getId());
            BestellpostenHaus bestellposten = bp.postenHausAnlegen(bestellung.getId(), bestellungDAO.getKreation().getId());
            bestellung.addPostenHaus(bestellposten);
        }
    }

    private Nutzer eingeloggterKunde(SecurityContext sec) {
        Principal user = sec.getUserPrincipal();
        if(user == null) return null;
        Optional<Nutzer> optKunde = Nutzer.find("name", user.getName()).firstResultOptional();
        if(optKunde.isEmpty()) return null;
        return optKunde.get();
    }
}