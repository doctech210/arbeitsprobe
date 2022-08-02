//@author Marvin Gels 868603

package de.hsos.swa.coldstoneicecreator.kreationen.boundary.rest;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
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

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.faulttolerance.Retry;
import org.eclipse.microprofile.faulttolerance.Timeout;

import de.hsos.swa.coldstoneicecreator.bestellung.control.BestellpostenControl;
import de.hsos.swa.coldstoneicecreator.bestellung.entity.BestellpostenEigen;
import de.hsos.swa.coldstoneicecreator.kreationen.boundary.dto.EigenkreationDTO;
import de.hsos.swa.coldstoneicecreator.kreationen.boundary.dto.KreationIdDTO;
import de.hsos.swa.coldstoneicecreator.kreationen.control.EigenkreationControl;
import de.hsos.swa.coldstoneicecreator.kreationen.entity.Eigenkreation;
import de.hsos.swa.coldstoneicecreator.nutzer.entity.Nutzer;
import de.hsos.swa.coldstoneicecreator.produkt.control.EisControl;
import de.hsos.swa.coldstoneicecreator.produkt.control.SauceControl;
import de.hsos.swa.coldstoneicecreator.produkt.control.ZutatControl;
import de.hsos.swa.coldstoneicecreator.produkt.entity.Eis;
import de.hsos.swa.coldstoneicecreator.produkt.entity.Sauce;
import de.hsos.swa.coldstoneicecreator.produkt.entity.Zutat;

@RequestScoped
@Path("/api/eigenkreationen/{id:\\d+}")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Retry(maxRetries = 4)
@Timeout(250)
public class EigenkreationIdResource {
    
    @Inject 
    EigenkreationControl eigenkreationRepo;

    @Inject
    BestellpostenControl bestellpostenRepo;

    @Inject
    EisControl eisRepo;

    @Inject
    ZutatControl zutatRepo;
    
    @Inject
    SauceControl sauceRepo;

    @GET
    @RolesAllowed({"Admin", "Kunde"})
    @Operation(
        summary = "Gibt eine bestimmte Eigenkreation zurueck",
        description = "Gibt eine bestimmte Eigenkreation des angemeldeten Nutzer ueber die uebergebene ID zurueck"
    )
    public Response get(@Context SecurityContext sec, @NotNull @PathParam("id") Long id) {
        Nutzer nutzer = this.eingeloggterKunde(sec);
        if(nutzer == null) return Response.status(Status.NOT_FOUND).build();
        Eigenkreation eigenkreation = null;
        if(nutzer.getRole().equals("Admin")){
            eigenkreation = eigenkreationRepo.getById(id);
        }else{
            eigenkreation = eigenkreationRepo.getById(id, nutzer);
        }
        if(eigenkreation != null) { 
            EigenkreationDTO eigenkreationDTO = EigenkreationDTO.Converter.toDTO(eigenkreation);
            return Response.ok(eigenkreationDTO).build();
        }
        return Response.status(Status.NOT_FOUND).build();
    }

    @PUT
    @Transactional
    @RolesAllowed({"Admin", "Kunde"})
    @Operation(
        summary = "Aendern einer bestimmten Eigenkreation",
        description = "Aendert eine bestimmte Eigenkreation eines angemeldeten Nutzers ueber die uebergebene ID"
    )
    public Response put(@Context SecurityContext sec, @NotNull @PathParam("id") Long id, @Valid KreationIdDTO kreationIdDTO) {
        Nutzer nutzer = this.eingeloggterKunde(sec);
        if(nutzer == null) return Response.status(Status.NOT_FOUND).build();
        Eis eissorte1 = eisRepo.getById(kreationIdDTO.eissorte1Id);
        Eis eissorte2 = eisRepo.getById(kreationIdDTO.eissorte2Id);
        List<Zutat> zutaten = new ArrayList<>();
        for(Long zutatId : kreationIdDTO.zutatenId) {
            zutaten.add(zutatRepo.getById(zutatId));
        }
        Sauce sauce = sauceRepo.getById(kreationIdDTO.sauceId);
        Eigenkreation eigenkreation = new Eigenkreation(null, eissorte1, eissorte2, zutaten, sauce, kreationIdDTO.name);
        eigenkreationRepo.put(id, eigenkreation, nutzer);
        return Response.ok().build();
    }
    
    @POST
    @Transactional
    @RolesAllowed({"Admin", "Kunde"})
    @Operation(
        summary = "Fuegt eine bestimmte Eigenkreation der Bestellung hinzu",
        description = "Fuegt eine bereits existierende Eigenkreation ueber die uerbergebene ID, " + 
                        "eines angemeldeten Nutzers, der aktuellen Bestellung hinzu"
    )
    public Response post(@Context SecurityContext sec, @NotNull @PathParam("id") Long id, @NotNull @PositiveOrZero Long anzahl) {
        Nutzer nutzer = this.eingeloggterKunde(sec);
        if(nutzer == null) return Response.status(Status.NOT_FOUND).build();
        eigenkreationRepo.post(id, anzahl, nutzer);
        return Response.ok().build();
    }
    
    @PUT
    @Transactional
    @RolesAllowed({"Admin", "Kunde"})
    @Path("/zutaten/{zutatnummer:\\d+}")
    @Operation(
        summary = "Aendern einer Zutat in einer bestimmten Eigenkreation",
        description = "Aendern einer Zutaten in einer bestimmten Eigenkreation eines angemeldeten Nutzers ueber die uebergebene ID"
    )
    public Response putZutaten(@Context SecurityContext sec, @NotNull @PathParam("id") Long id, @NotNull @PathParam("zutatnummer") int zutatnummer, @NotNull Long neueZutatId) {
        Nutzer nutzer = this.eingeloggterKunde(sec);
        if(nutzer == null) return Response.status(Status.NOT_FOUND).build();
        eigenkreationRepo.putZutat(id, --zutatnummer, neueZutatId, nutzer);
        return Response.ok().build();
    }

    @DELETE
    @Transactional
    @RolesAllowed({"Admin", "Kunde"})
    @Operation(
        summary = "Loeschen einer bestimmten Eigenkreation",
        description = "Loeschen einer bestimmten Eigenkreation eines angemeldeten Nutzers ueber die uebergebene ID"
    )
    public Response delete(@Context SecurityContext sec, @NotNull @PathParam("id") Long id) {
        Nutzer nutzer = this.eingeloggterKunde(sec);
        Eigenkreation eigenkreation = eigenkreationRepo.getById(id);
        if(nutzer == null) return Response.status(Status.NOT_FOUND).build();
        List<BestellpostenEigen> bestellpostenEigen = bestellpostenRepo.getAllEigen();
        Long postenId = null;
        for(BestellpostenEigen eigen : bestellpostenEigen) {
            
            if(eigen.getEigenkreation().equals(eigenkreation)) {
                postenId = eigen.getId();
            }
                
        }
        if(postenId == null) return Response.status(Status.NOT_FOUND).build();
        nutzer.deleteEigenkreation(eigenkreation, postenId);
        return Response.ok().build();
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
