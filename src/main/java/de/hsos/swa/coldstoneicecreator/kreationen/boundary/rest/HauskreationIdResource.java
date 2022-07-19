package de.hsos.swa.coldstoneicecreator.kreationen.boundary.rest;
import java.security.Principal;
import java.util.Optional;

import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Event;
import javax.inject.Inject;
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
import javax.ws.rs.core.Response.Status;

import de.hsos.swa.coldstoneicecreator.kreationen.boundary.dao.BestellungDAO;
import de.hsos.swa.coldstoneicecreator.kreationen.boundary.dao.BestellungIdDAO;
import de.hsos.swa.coldstoneicecreator.kreationen.boundary.dto.HauskreationDTO;
import de.hsos.swa.coldstoneicecreator.kreationen.control.HauskreationControl;
import de.hsos.swa.coldstoneicecreator.kreationen.entity.Hauskreation;
import de.hsos.swa.coldstoneicecreator.kunden.entity.Nutzer;
import de.hsos.swa.coldstoneicecreator.produkt.boundary.dto.ZutatDTO;
import de.hsos.swa.coldstoneicecreator.produkt.entity.Zutat;

@RequestScoped
@Path("/hauskreationen/{id:\\d+}")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class HauskreationIdResource {
    
    @Inject
    HauskreationControl hc;

    @Inject
    Event<BestellungDAO> bestellen;

    @Inject
    Event<BestellungIdDAO> bestellenId;

    @GET
    @RolesAllowed({"Admin", "Kunde"})
    public Response get(@PathParam("id") Long id) {
        Hauskreation hauskreation = hc.getById(id);
        if(hauskreation != null) {
            HauskreationDTO hauskreationDTO = HauskreationDTO.Converter.toDTO(hauskreation);
            return Response.ok(hauskreationDTO).build();
        }
        return Response.status(Status.NOT_FOUND).build();
    }

    @PUT
    @Transactional
    @RolesAllowed({"Admin"})
    public Response put(@PathParam("id") Long id, HauskreationDTO hauskreationDTO) {
        Hauskreation hauskreation = HauskreationDTO.Converter.toHauskreation(hauskreationDTO);
        hc.put(id, hauskreation);
        return Response.ok().build();
    }
    
    @POST
    @Transactional
    @RolesAllowed({"Admin", "Kunde"})
    public Response post(@PathParam("id") Long id, @Context SecurityContext sec, Long anzahl) {
        Nutzer kunde = this.eingeloggterKunde(sec);
        if(kunde == null) return Response.status(Status.BAD_REQUEST).build();
        Hauskreation hauskreation = hc.getById(id);
        hc.create(kunde, hauskreation, anzahl);
        return Response.ok().build();
    }

    @DELETE
    @Transactional
    @RolesAllowed({"Admin"})
    public Response delete(@PathParam("id") Long id) {
        hc.delete(id);
        return Response.ok().build();
    }

    @PUT
    @Transactional
    @RolesAllowed({"Admin"})
    @Path("/zutaten/{zutatnummer:\\d+}")
    public Response putZutaten(@PathParam("id") Long id, @PathParam("zutatnummer") int zutatnummer, ZutatDTO zutatDTO) {
        Zutat zutat = ZutatDTO.Converter.toZutat(zutatDTO);
        hc.putZutat(id, --zutatnummer, zutat);
        return Response.ok().build();
    }

    private Nutzer eingeloggterKunde(SecurityContext sec) {
        Principal user = sec.getUserPrincipal();
        if(user == null) return null;
        Optional<Nutzer> optKunde = Nutzer.find("name", user.getName()).firstResultOptional();
        if(optKunde.isEmpty()) return null;
        return optKunde.get();
    }

    /*private Bestellung offeneBestellung(Nutzer kunde) {
        for(Bestellung b : kunde.getBestellungen()){
            if(!b.isBestellt()) return b;
        } 
        return null;
    }*/
}
