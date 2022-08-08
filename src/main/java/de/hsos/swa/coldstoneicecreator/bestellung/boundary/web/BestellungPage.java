//@author Stefan Bierenriede 852142

package de.hsos.swa.coldstoneicecreator.bestellung.boundary.web;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
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

import de.hsos.swa.coldstoneicecreator.bestellung.boundary.dto.BestellungDTO;
import de.hsos.swa.coldstoneicecreator.bestellung.control.BestellpostenControl;
import de.hsos.swa.coldstoneicecreator.bestellung.control.BestellungControl;
import de.hsos.swa.coldstoneicecreator.bestellung.entity.Bestellung;
import de.hsos.swa.coldstoneicecreator.nutzer.entity.Nutzer;
import io.quarkus.qute.CheckedTemplate;
import io.quarkus.qute.TemplateInstance;

@RequestScoped
@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
@Produces(MediaType.TEXT_HTML)
@Path("/bestellungen")
@Retry(maxRetries = 4)
@Timeout(250)
public class BestellungPage {
    
    @Inject
    BestellungControl bestellungRepo;

    @Inject
    BestellpostenControl bestellpostenRepo;

    @CheckedTemplate
    static class Templates {
        
        static native TemplateInstance bestellungAlle(List<BestellungDTO> bestellungenDTO);

        static native TemplateInstance error(int errorCode, String errorMessage);
    }

    @GET
    @RolesAllowed({"Admin", "Kunde"})
    @Operation(
        summary = "Gibt alle Bestellungen des Nutzers zurueck",
        description = "Gibt alle Bestellungen des angemeldeten Nutzers zurueck"
    )
    public TemplateInstance get(@Context SecurityContext sec) {
        Nutzer nutzer = this.eingeloggterKunde(sec);
        if(nutzer == null) return Templates.error(Status.FORBIDDEN.getStatusCode(), "Bitte erst einloggen");
        List<Bestellung> alle = null;
        if(nutzer.getRole().equals("Admin")){
            alle = bestellungRepo.bestellungenAbfragen();
        }else{
            alle = bestellungRepo.bestellungenAbfragen(nutzer.getId());
        }
        List<BestellungDTO> alleDTO = new ArrayList<>();
        for(Bestellung bestellung : alle) {
            alleDTO.add(BestellungDTO.Converter.toDTO(bestellung));
        }
        //return Response.ok(alleDTO).build();
        return Templates.bestellungAlle(alleDTO);
    } 

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    @RolesAllowed({"Admin", "Kunde"})
    @Operation(
        summary = "Schickt die aktuelle Bestellung ab",
        description = "Schickt die aktuelle Bestellung des angemeldeten Nutzers ab"
    )
    public Response post(@Context SecurityContext sec) {
        Nutzer nutzer = this.eingeloggterKunde(sec);
        if(nutzer == null) return Response.ok(Templates.error(Response.Status.BAD_REQUEST.getStatusCode(), "Nutzer nicht gefunden")).build();
        Bestellung bestellung = this.offeneBestellung(nutzer);
        if(bestellung == null) return Response.ok(Templates.error(Response.Status.BAD_REQUEST.getStatusCode(), "Bestellung nicht gefunden")).build();
        bestellung.setBestellt(true);
        return Response.ok().header("Refresh", "0; url=/bestellungen").build();
    }

    @POST
    @Transactional
    @Path("/loeschen")
    @RolesAllowed("Kunde")
    @Operation(
        summary = "Loescht die aktuelle Bestellung",
        description = "Loescht die aktuelle, nicht abgeschickte Bestellung des angemeldeten Nutzers"
    )
    public Response delete(@Context SecurityContext sec){
        Nutzer nutzer = this.eingeloggterKunde(sec);
        if(nutzer == null) return Response.ok(Templates.error(Response.Status.BAD_REQUEST.getStatusCode(), "Nutzer nicht gefunden")).build();
        Bestellung bestellung = this.offeneBestellung(nutzer);
        if(bestellung == null) return Response.ok(Templates.error(Response.Status.BAD_REQUEST.getStatusCode(), "Bestellung nicht gefunden")).build();
        bestellungRepo.bestellungLoeschen(bestellung.getId());
        nutzer.deleteBestellung();
        return Response.ok().header("Refresh", "0; url=/bestellungen").build();
    }

    /**
     * 
     * @param nutzer
     * @return
     */
    private Bestellung offeneBestellung(@NotNull Nutzer nutzer) {
        for(Bestellung b : nutzer.getBestellungen()){
            if(!b.isBestellt()) return b;
        } 
        return null;
    }

    private Nutzer eingeloggterKunde(SecurityContext sec) {
        Principal user = sec.getUserPrincipal();
        if(user == null) return null;
        Optional<Nutzer> optKunde = Nutzer.find("name", user.getName()).firstResultOptional();
        if(optKunde.isEmpty()) return null;
        return optKunde.get();
    }
}
