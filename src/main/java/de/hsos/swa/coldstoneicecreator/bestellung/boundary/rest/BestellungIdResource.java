//@author Stefan Bierenriede 852142, Marvin Gels 868603

package de.hsos.swa.coldstoneicecreator.bestellung.boundary.rest;

import de.hsos.swa.coldstoneicecreator.bestellung.control.BestellpostenControl;
import de.hsos.swa.coldstoneicecreator.bestellung.control.BestellungControl;
import de.hsos.swa.coldstoneicecreator.bestellung.entity.Bestellung;
import de.hsos.swa.coldstoneicecreator.nutzer.entity.Nutzer;
import de.hsos.swa.coldstoneicecreator.bestellung.boundary.dto.BestellungDTO;

import java.util.Optional;

import java.security.Principal;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
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

import org.eclipse.microprofile.faulttolerance.Retry;
import org.eclipse.microprofile.faulttolerance.Timeout;
import org.eclipse.microprofile.openapi.annotations.Operation;

import javax.ws.rs.PathParam;
import javax.annotation.security.RolesAllowed;

@RequestScoped
@Path("/api/bestellungen/{id:\\d+}")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Retry(maxRetries = 4)
@Timeout(250)
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
        Nutzer nutzer = this.eingeloggterKunde(sec);
        if(nutzer == null) return Response.status(Status.NOT_FOUND).build();
        Bestellung bestellung = null;
        if(nutzer.getRole().equals("Admin")){
            bestellung = bestellungRepo.bestellungAbfragen(id);
        }else{
            bestellung = bestellungRepo.bestellungAbfragen(id, nutzer.getId());
        }
        if(bestellung == null) return Response.status(Status.NOT_FOUND).build();
        BestellungDTO bestellungDTO = BestellungDTO.Converter.toDTO(bestellung);
        return Response.ok(bestellungDTO).build();
    } 

    @PUT
    @Transactional
    @RolesAllowed({"Admin", "Kunde"})
    @Path("/eigenposten/{eigenpostenId:\\d+}")
    @Operation(
        summary = "Aendert die Anzahl einer bestimmten Eigenkreation",
        description = "Aendert die Anzahl einer Eigenkreation mit der uebergebenen ID in der Bestellung"
    )
    public Response putEigen(@Context SecurityContext sec, @NotNull @PathParam("id") Long id, @NotNull @PathParam("eigenpostenId") Long postenId, @NotNull @PositiveOrZero Long anzahl){
        Nutzer nutzer = this.eingeloggterKunde(sec);
        if(nutzer == null) return Response.status(Status.NOT_FOUND).build();
        Bestellung bestellung = null;
        if(nutzer.getRole().equals("Admin")){
            bestellung = bestellungRepo.bestellungAbfragen(id);
        }else{
            bestellung = bestellungRepo.bestellungAbfragen(id, nutzer.getId());
        }
        if(bestellung == null) return Response.status(Status.NOT_FOUND).build();
        bestellpostenRepo.postenAendernEigen(id, postenId, anzahl);
        return Response.ok().build();
    }

    @PUT
    @Transactional
    @RolesAllowed({"Admin", "Kunde"})
    @Path("/hausposten/{hauspostenId:\\d+}")
    @Operation(
        summary = "Aendert die Anzahl einer bestimmten Hauskreation",
        description = "Aendert die Anzahl einer Hauskreation mit der uebergebenen ID in der Bestellung"
    )
    public Response putHaus(@Context SecurityContext sec, @NotNull @PathParam("id") Long id, @NotNull @PathParam("hauspostenId") Long postenId, @NotNull @PositiveOrZero Long anzahl){
        Nutzer nutzer = this.eingeloggterKunde(sec);
        if(nutzer == null) return Response.status(Status.NOT_FOUND).build();
        Bestellung bestellung = null;
        if(nutzer.getRole().equals("Admin")){
            bestellung = bestellungRepo.bestellungAbfragen(id);
        }else{
            bestellung = bestellungRepo.bestellungAbfragen(id, nutzer.getId());
        }
        if(bestellung == null) return Response.status(Status.NOT_FOUND).build();
        bestellpostenRepo.postenAendernHaus(id, postenId, anzahl);
        return Response.ok().build();
    }

    @DELETE
    @Transactional
    @RolesAllowed({"Admin", "Kunde"})
    @Path("/hausposten/{hauspostenId:\\d+}")
    @Operation(
        summary = "Loeschen eines bestimmten Hausbestellposten aus der Bestellung",
        description = "Loeschen des Hausbestellposten mit der uebergebenen ID aus der Bestellung"
    )
    public Response deleteHauskreation(@Context SecurityContext sec, @PathParam("id") Long id, @PathParam("postenId") Long postenId){
        Nutzer nutzer = this.eingeloggterKunde(sec);
        if(nutzer == null) return Response.status(Status.NOT_FOUND).build();
        Bestellung bestellung = this.offeneBestellung(nutzer);
        if(bestellung == null) return Response.status(Status.NOT_FOUND).build();
        bestellpostenRepo.postenLoeschenHaus(postenId);
        bestellung.removePostenHaus(postenId);
        return Response.noContent().build();
    }

    @DELETE
    @Transactional
    @RolesAllowed({"Admin", "Kunde"})
    @Path("/eigenposten/{eigenpostenId:\\d+}")
    @Operation(
        summary = "Loeschen eines bestimmten Eigenbestellposten aus der Bestellung",
        description = "Loeschen des Eigenbestellposten mit der uebergebenen ID aus der Bestellung"
    )
    public Response deleteEigenkreation(@Context SecurityContext sec, @PathParam("id") Long id, @PathParam("postenId") Long postenId){
        Nutzer nutzer = this.eingeloggterKunde(sec);
        if(nutzer == null) return Response.status(Status.NOT_FOUND).build();
        Bestellung bestellung = this.offeneBestellung(nutzer);
        if(bestellung == null) return Response.status(Status.NOT_FOUND).build();
        bestellpostenRepo.postenLoeschenEigen(postenId);
        bestellung.removePostenEigen(postenId);
        return Response.noContent().build();
    }

    private Bestellung offeneBestellung(@NotNull Nutzer nutzer) {
            for(Bestellung b : nutzer.getBestellungen()){
                if(!b.isBestellt()) return b;
            } 
            return null;
        }

    private Nutzer eingeloggterKunde(@NotNull SecurityContext sec) {
        Principal user = sec.getUserPrincipal();
        if(user == null) return null;
        Optional<Nutzer> optKunde = Nutzer.find("name", user.getName()).firstResultOptional();
        if(optKunde.isEmpty()) return null;
        return optKunde.get();
    }
}