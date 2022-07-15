package de.hsos.swa.coldstoneicecreator.bestellung.boundary.rest;

import de.hsos.swa.coldstoneicecreator.bestellung.control.BestellungControl;
import de.hsos.swa.coldstoneicecreator.bestellung.entity.BestellpostenEigen;
import de.hsos.swa.coldstoneicecreator.bestellung.entity.BestellpostenHaus;
import de.hsos.swa.coldstoneicecreator.bestellung.entity.Bestellung;
import de.hsos.swa.coldstoneicecreator.kreationen.boundary.dao.BestellungIdDAO;
import de.hsos.swa.coldstoneicecreator.kreationen.entity.Eigenkreation;
import de.hsos.swa.coldstoneicecreator.kreationen.entity.Hauskreation;
import de.hsos.swa.coldstoneicecreator.bestellung.boundary.dto.BestellungDTO;
import de.hsos.swa.coldstoneicecreator.kunden.entity.Kunde;

import java.util.Optional;

import java.security.Principal;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Observes;
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
@Path("Bestellungen/{id:\\d+}")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class BestellungIdResource {

    @Inject
    BestellungControl bc;

    @GET
    @RolesAllowed("Admin, Kunde")
    public Response get(@Context SecurityContext sec, @PathParam("id") Long id) {
        Kunde kunde = this.eingeloggterKunde(sec);
        Bestellung bestellung = bc.bestellungAbfragen(id, kunde.getId());
        BestellungDTO bestellungDTO = BestellungDTO.Converter.toDTO(bestellung);
        return Response.ok(bestellungDTO).build();
    } 

    @POST
    @Transactional
    @RolesAllowed("Admin, Kunde")
    public Response post(@Context SecurityContext sec, @PathParam("id") Long id) {
        //Kunde kunde = this.eingeloggterKunde(sec);
        //TODO: POST als "bestellen" nutzen?
        return Response.ok().build();
    }

    @PUT
    @Transactional
    @RolesAllowed("Admin, Kunde")
    public Response put(@Context SecurityContext sec, @PathParam("id") Long id){
        //Kunde kunde = this.eingeloggterKunde(sec);
        //Bestellung bestellung = bc.bestellungAbfragen(id, kunde.getId());
        return Response.ok().build();
    }

    @DELETE
    @Transactional
    @RolesAllowed("Admin, Kunde")
    public Response delete(@Context SecurityContext sec, @PathParam("id") Long id){
        Kunde kunde = this.eingeloggterKunde(sec);
        bc.bestellungLoeschen(id, kunde.getId());
        return Response.ok().build();
    }


    private Kunde eingeloggterKunde(SecurityContext sec) {
        Principal user = sec.getUserPrincipal();
        if(user == null) return null;
        Optional<Kunde> optKunde = Kunde.find("vorname", user.getName()).firstResultOptional();
        if(optKunde.isEmpty()) return null;
        return optKunde.get();
    }

    public void bestellen(@Observes BestellungIdDAO bestellungIdDAO) {
        if(bestellungIdDAO.getKreation().getClass() == Eigenkreation.class) {
            BestellpostenEigen bestellposten = new BestellpostenEigen(null, (Eigenkreation)bestellungIdDAO.getKreation(), bestellungIdDAO.getAnzahl().intValue());
            Long id = bestellungIdDAO.getBestellungsId();
            Kunde kunde = bestellungIdDAO.getKunde();
            Bestellung bestellung = bc.bestellungAbfragen(id, kunde.getId());
            bestellung.addPostenEigen(bestellposten);
        }else{
            BestellpostenHaus bestellposten = new BestellpostenHaus(null, (Hauskreation)bestellungIdDAO.getKreation(), bestellungIdDAO.getAnzahl().intValue());
            Long id = bestellungIdDAO.getBestellungsId();
            Kunde kunde = bestellungIdDAO.getKunde();
            Bestellung bestellung = bc.bestellungAbfragen(id, kunde.getId());
            bestellung.addPostenHaus(bestellposten);
        }
    }
}