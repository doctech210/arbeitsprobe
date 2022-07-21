package de.hsos.swa.coldstoneicecreator.bestellung.boundary.rest;

import de.hsos.swa.coldstoneicecreator.bestellung.control.BestellpostenControl;
import de.hsos.swa.coldstoneicecreator.bestellung.control.BestellungControl;
import de.hsos.swa.coldstoneicecreator.bestellung.entity.Bestellung;
import de.hsos.swa.coldstoneicecreator.bestellung.boundary.dto.BestellungDTO;
import de.hsos.swa.coldstoneicecreator.kunden.entity.Nutzer;

import java.util.Optional;

import java.security.Principal;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.DELETE;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.Response.Status;

import org.eclipse.microprofile.openapi.annotations.Operation;

import javax.ws.rs.PathParam;
import javax.annotation.security.RolesAllowed;

@RequestScoped
@Path("bestellungen/{id:\\d+}")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class BestellungIdResource {

    @Inject
    BestellungControl bestellungRepo;

    @Inject
    BestellpostenControl bestellpostenRepo;

    @GET
    @RolesAllowed({"Admin", "Kunde"})
    @Operation(
        summary = "Gibt eine bestimmte Bestellung zurueck",
        description = "Gibt die Bestellung mit der uebergegebenen ID zurueck"
    )
    public Response get(@Context SecurityContext sec, @PathParam("id") Long id) {
        Nutzer kunde = this.eingeloggterKunde(sec);
        if(kunde == null) Response.status(Status.NOT_FOUND).build();
        Bestellung bestellung = null;
        if(kunde.getRole().equals("Admin")){
            bestellung = bestellungRepo.bestellungAbfragen(id);
        }else{
            bestellung = bestellungRepo.bestellungAbfragen(id, kunde.getId());
        }
        if(bestellung == null) Response.status(Status.NOT_FOUND).build();
        BestellungDTO bestellungDTO = BestellungDTO.Converter.toDTO(bestellung);
        return Response.ok(bestellungDTO).build();
    } 

    @PUT
    @Transactional
    @RolesAllowed({"Admin", "Kunde"})
    @Path("/{postenId:\\d+}/eigenkreationen")
    @Operation(
        summary = "Aendert die Anzahl einer bestimmten Eigenkreation",
        description = "Aendert die Anzahl einer Eigenkreation mit der uebergebenen ID in der Bestellung"
    )
    public Response putEigen(@Context SecurityContext sec, @PathParam("id") Long id, @PathParam("postenId") Long postenId, Long anzahl){
        Nutzer kunde = this.eingeloggterKunde(sec);
        if(kunde == null) Response.status(Status.NOT_FOUND).build();
        Bestellung bestellung = null;
        if(kunde.getRole().equals("Admin")){
            bestellung = bestellungRepo.bestellungAbfragen(id);
        }else{
            bestellung = bestellungRepo.bestellungAbfragen(id, kunde.getId());
        }
        if(bestellung == null) return Response.status(Status.NOT_FOUND).build();
        bestellpostenRepo.postenAendernEigen(id, postenId, anzahl);
        return Response.ok().build();
    }

    @PUT
    @Transactional
    @RolesAllowed({"Admin", "Kunde"})
    @Path("/{postenId:\\d+}/hauskreationen")
    @Operation(
        summary = "Aendert die Anzahl einer bestimmten Hauskreation",
        description = "Aendert die Anzahl einer Hauskreation mit der uebergebenen ID in der Bestellung"
    )
    public Response putHaus(@Context SecurityContext sec, @PathParam("id") Long id, @PathParam("postenId") Long postenId, Long anzahl){
        Nutzer kunde = this.eingeloggterKunde(sec);
        if(kunde == null) Response.status(Status.NOT_FOUND).build();
        Bestellung bestellung = null;
        if(kunde.getRole().equals("Admin")){
            bestellung = bestellungRepo.bestellungAbfragen(id);
        }else{
            bestellung = bestellungRepo.bestellungAbfragen(id, kunde.getId());
        }
        if(bestellung == null) return Response.status(Status.NOT_FOUND).build();
        bestellpostenRepo.postenAendernHaus(id, postenId, anzahl);
        return Response.ok().build();
    }

    @DELETE
    @Transactional
    @RolesAllowed({"Admin", "Kunde"})
    @Operation(
        summary = "Loeschen eines bestimmten Bestellposten aus der Bestellung",
        description = "Loeschen des Bestellposten mit der uebergebenen ID aus der Bestellung"
    )
    public Response delete(@Context SecurityContext sec, @PathParam("id") Long id){
        //TODO: aendern zu postenLoeschen
        Nutzer kunde = this.eingeloggterKunde(sec);
        if(kunde == null) Response.status(Status.NOT_FOUND).build();
        if(kunde.getRole().equals("Admin")){
            bestellungRepo.bestellungLoeschen(id);
        }else{
            bestellungRepo.bestellungLoeschen(id, kunde.getId());
        }
        return Response.noContent().build();
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