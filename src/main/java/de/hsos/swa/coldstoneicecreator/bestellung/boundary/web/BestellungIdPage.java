package de.hsos.swa.coldstoneicecreator.bestellung.boundary.web;

import java.security.Principal;
import java.util.Optional;

import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriBuilder;

import org.eclipse.microprofile.faulttolerance.Retry;
import org.eclipse.microprofile.faulttolerance.Timeout;
import org.eclipse.microprofile.openapi.annotations.Operation;

import de.hsos.swa.coldstoneicecreator.bestellung.boundary.dto.BestellungDTO;
import de.hsos.swa.coldstoneicecreator.bestellung.control.BestellpostenControl;
import de.hsos.swa.coldstoneicecreator.bestellung.control.BestellungControl;
import de.hsos.swa.coldstoneicecreator.bestellung.entity.Bestellung;
import de.hsos.swa.coldstoneicecreator.kunden.entity.Nutzer;
import io.quarkus.qute.CheckedTemplate;
import io.quarkus.qute.TemplateInstance;

@RequestScoped
@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
@Produces(MediaType.TEXT_HTML)
@Path("/bestellungen/{id:\\d+}")
@Retry(maxRetries = 4)
@Timeout(250)
public class BestellungIdPage {
    
    @Inject
    BestellungControl bestellungRepo;

    @Inject
    BestellpostenControl bestellpostenRepo;

    @CheckedTemplate
    static class Templates {

        static native TemplateInstance bestellungEinzeln(BestellungDTO bestellungDTO);

        static native TemplateInstance error(int errorCode, String errorMessage);
    }

    @GET
    @RolesAllowed({"Admin", "Kunde"})
    @Operation(
        summary = "Gibt eine bestimmte Bestellung zurueck",
        description = "Gibt die Bestellung mit der uebergegebenen ID zurueck"
    )
    public TemplateInstance get(@Context SecurityContext sec, @PathParam("id") Long id) {
        Nutzer nutzer = this.eingeloggterKunde(sec);
        if(nutzer == null) return Templates.error(Response.Status.BAD_REQUEST.getStatusCode(), "Booking not found");
        Bestellung bestellung = null;
        if(nutzer.getRole().equals("Admin")){
            bestellung = bestellungRepo.bestellungAbfragen(id);
        }else{
            bestellung = bestellungRepo.bestellungAbfragen(id, nutzer.getId());
        }
        if(bestellung == null) return Templates.error(Response.Status.BAD_REQUEST.getStatusCode(), "Bestellung nicht gefunden");
        BestellungDTO bestellungDTO = BestellungDTO.Converter.toDTO(bestellung);
        //return Response.ok(bestellungDTO).build();
        return Templates.bestellungEinzeln(bestellungDTO);
    } 

    @POST
    @Transactional
    @RolesAllowed({"Admin", "Kunde"})
    @Path("/eigenposten/{eigenpostenId:\\d+}")
    @Operation(
        summary = "Aendert die Anzahl einer bestimmten Eigenkreation",
        description = "Aendert die Anzahl einer Eigenkreation mit der uebergebenen ID in der Bestellung"
    )
    public Response putEigen(@Context SecurityContext sec, @NotNull @PathParam("id") Long id, @NotNull @PathParam("eigenpostenId") Long postenId, @NotNull @PositiveOrZero Long anzahl){
        Nutzer nutzer = this.eingeloggterKunde(sec);
        if(nutzer == null) return Response.ok(Templates.error(Response.Status.BAD_REQUEST.getStatusCode(), "Nutzer nicht gefunden")).build();
        Bestellung bestellung = null;
        if(nutzer.getRole().equals("Admin")){
            bestellung = bestellungRepo.bestellungAbfragen(id);
        }else{
            bestellung = bestellungRepo.bestellungAbfragen(id, nutzer.getId());
        }
        if(bestellung == null) return Response.ok(Templates.error(Response.Status.BAD_REQUEST.getStatusCode(), "Booking not found")).build();
        bestellpostenRepo.postenAendernEigen(id, postenId, anzahl);
        //return Response.ok().build();
        return Response.seeOther(UriBuilder.fromPath(".").build()).build();
    }

    @POST
    @Transactional
    @RolesAllowed({"Admin", "Kunde"})
    @Path("/hausposten/{hauspostenId:\\d+}")
    @Operation(
        summary = "Aendert die Anzahl einer bestimmten Hauskreation",
        description = "Aendert die Anzahl einer Hauskreation mit der uebergebenen ID in der Bestellung"
    )
    public Response putHaus(@Context SecurityContext sec, @NotNull @PathParam("id") Long id, @NotNull @PathParam("hauspostenId") Long postenId, @NotNull @PositiveOrZero Long anzahl){
        Nutzer nutzer = this.eingeloggterKunde(sec);
        if(nutzer == null) return Response.ok(Templates.error(Response.Status.BAD_REQUEST.getStatusCode(), "Nutzer nicht gefunden")).build();
        Bestellung bestellung = null;
        if(nutzer.getRole().equals("Admin")){
            bestellung = bestellungRepo.bestellungAbfragen(id);
        }else{
            bestellung = bestellungRepo.bestellungAbfragen(id, nutzer.getId());
        }
        if(bestellung == null) return Response.ok(Templates.error(Response.Status.BAD_REQUEST.getStatusCode(), "Bestellung nicht gefunden")).build();
        bestellpostenRepo.postenAendernHaus(id, postenId, anzahl);
        //return Response.ok().build();
        return Response.seeOther(UriBuilder.fromPath(".").build()).build();
    }

    @POST
    @Transactional
    @RolesAllowed({"Admin", "Kunde"})
    @Path("/hausposten/{postenId:\\d+}/loeschen")
    @Operation(
        summary = "Loeschen eines bestimmten Hausbestellposten aus der Bestellung",
        description = "Loeschen des Hausbestellposten mit der uebergebenen ID aus der Bestellung"
    )
    public Response deleteHauskreation(@Context SecurityContext sec, @PathParam("id") Long id, @PathParam("postenId") Long postenId){
        Nutzer nutzer = this.eingeloggterKunde(sec);
        if(nutzer == null) return Response.ok(Templates.error(Response.Status.BAD_REQUEST.getStatusCode(), "Nutzer nicht gefunden")).build();
        Bestellung bestellung = this.offeneBestellung(nutzer);
        if(bestellung == null) return Response.ok(Templates.error(Response.Status.BAD_REQUEST.getStatusCode(), "Bestellung nicht gefunden")).build();
        bestellpostenRepo.postenLoeschenHaus(postenId);
        bestellung.removePostenHaus(postenId);
        //return Response.noContent().build();
        return Response.ok().header("Refresh", "0; url=/bestellungen").build();
    }

    @POST
    @Transactional
    @RolesAllowed({"Admin", "Kunde"})
    @Path("/eigenposten/{postenId:\\d+}/loeschen")
    @Operation(
        summary = "Loeschen eines bestimmten Eigenbestellposten aus der Bestellung",
        description = "Loeschen des Eigenbestellposten mit der uebergebenen ID aus der Bestellung"
    )
    public Response deleteEigenkreation(@Context SecurityContext sec, @PathParam("id") Long id, @PathParam("postenId") Long postenId){
        Nutzer nutzer = this.eingeloggterKunde(sec);
        if(nutzer == null) return Response.ok(Templates.error(Response.Status.BAD_REQUEST.getStatusCode(), "Nutzer nicht gefunden")).build();
        Bestellung bestellung = this.offeneBestellung(nutzer);
        if(bestellung == null) return Response.ok(Templates.error(Response.Status.BAD_REQUEST.getStatusCode(), "Bestellung nicht gefunden")).build();
        bestellpostenRepo.postenLoeschenEigen(postenId);
        bestellung.removePostenEigen(postenId);
        return Response.ok().header("Refresh", "0; url=/bookings").build();
    }

    private Bestellung offeneBestellung(@NotNull Nutzer nutzer) {
            for(Bestellung b : nutzer.getBestellungen()){
                if(!b.isBestellt()) return b;
            } 
            return null;
        }

    /**
     * 
     * @param sec
     * @return
     */
    private Nutzer eingeloggterKunde(@NotNull SecurityContext sec) {
        Principal user = sec.getUserPrincipal();
        if(user == null) return null;
        Optional<Nutzer> optKunde = Nutzer.find("name", user.getName()).firstResultOptional();
        if(optKunde.isEmpty()) return null;
        return optKunde.get();
    }
}
