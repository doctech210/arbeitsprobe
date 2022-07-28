package de.hsos.swa.coldstoneicecreator.kreationen.boundary.web;
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
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.Response.Status;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.faulttolerance.Retry;
import org.eclipse.microprofile.faulttolerance.Timeout;

import de.hsos.swa.coldstoneicecreator.bestellung.control.BestellpostenControl;
import de.hsos.swa.coldstoneicecreator.bestellung.entity.BestellpostenEigen;
import de.hsos.swa.coldstoneicecreator.kreationen.boundary.dto.EigenkreationDTO;
import de.hsos.swa.coldstoneicecreator.kreationen.control.EigenkreationControl;
import de.hsos.swa.coldstoneicecreator.kreationen.entity.Eigenkreation;
import de.hsos.swa.coldstoneicecreator.nutzer.entity.Nutzer;
import de.hsos.swa.coldstoneicecreator.produkt.boundary.dto.EisDTO;
import de.hsos.swa.coldstoneicecreator.produkt.boundary.dto.SauceDTO;
import de.hsos.swa.coldstoneicecreator.produkt.boundary.dto.ZutatDTO;
import de.hsos.swa.coldstoneicecreator.produkt.entity.*;
import de.hsos.swa.coldstoneicecreator.produkt.control.EisControl;
import de.hsos.swa.coldstoneicecreator.produkt.control.SauceControl;
import de.hsos.swa.coldstoneicecreator.produkt.control.ZutatControl;
import io.quarkus.qute.CheckedTemplate;
import io.quarkus.qute.TemplateInstance;

@RequestScoped
@Path("/eigenkreationen/{id:\\d+}")
@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
@Produces(MediaType.TEXT_HTML)
@Retry(maxRetries = 4)
@Timeout(250)
public class EigenkreationIdPage {
    
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

    @CheckedTemplate
    static class Templates {

        static native TemplateInstance eigenkreationEinzeln(EigenkreationDTO eigenkreationDTO, List<EisDTO> eisDTO, List<ZutatDTO> zutatenDTO, List<SauceDTO> sauceDTO);

        static native TemplateInstance error(int errorCode, String errorMessage);
    }

    @GET
    @RolesAllowed({"Admin", "Kunde"})
    @Operation(
        summary = "Gibt eine bestimmte Eigenkreation zurueck",
        description = "Gibt eine bestimmte Eigenkreation des angemeldeten Nutzer ueber die uebergebene ID zurueck"
    )
    public TemplateInstance get(@Context SecurityContext sec, @Valid @QueryParam("Allergene") List<Allergene> allergene, @NotNull @PathParam("id") Long id) {
        Nutzer nutzer = this.eingeloggterKunde(sec);
        if(nutzer == null) return Templates.error(Response.Status.BAD_REQUEST.getStatusCode(), "Bestellung nicht gefunden");
        Eigenkreation eigenkreation = null;
        if(nutzer.getRole().equals("Admin")){
            eigenkreation = eigenkreationRepo.getById(id);
        }else{
            eigenkreation = eigenkreationRepo.getById(id, nutzer);
        }

        List<Eis> alleEis = eisRepo.get();
        if(allergene != null) {
            alleEis = eisRepo.getOhneAllergene(allergene);
        }
        List<EisDTO> alleEisDTO = new ArrayList<>();
        for(Eis eis : alleEis) {
            alleEisDTO.add(EisDTO.Converter.toDTO(eis));
        }
        List<Zutat> alleZutaten = zutatRepo.get();
        if(allergene != null) {
            alleZutaten = zutatRepo.getOhneAllergene(allergene);
        }
        List<ZutatDTO> alleZutatenDTO = new ArrayList<>();
        for(Zutat zutat : alleZutaten) {
            alleZutatenDTO.add(ZutatDTO.Converter.toDTO(zutat));
        }
        List<Sauce> alleSauce = sauceRepo.get();
        if(allergene != null){
            alleSauce = sauceRepo.getOhneAllergene(allergene);
        }
        List<SauceDTO> alleSauceDTO = new ArrayList<>();
        for(Sauce sauce : alleSauce) {
            alleSauceDTO.add(SauceDTO.Converter.toDTO(sauce));
        }


        if(eigenkreation != null) { 
            EigenkreationDTO eigenkreationDTO = EigenkreationDTO.Converter.toDTO(eigenkreation);
            return Templates.eigenkreationEinzeln(eigenkreationDTO, alleEisDTO, alleZutatenDTO, alleSauceDTO);
        }
        return Templates.error(Response.Status.BAD_REQUEST.getStatusCode(), "Bestellung nicht gefunden");
    }

    @POST
    @Transactional
    @Path("/put/")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"Admin", "Kunde"})
    @Operation(
        summary = "Aendern einer bestimmten Eigenkreation",
        description = "Aendert eine bestimmte Eigenkreation eines angemeldeten Nutzers ueber die uebergebene ID"
    )
    public Response post(@Context SecurityContext sec, @NotNull @PathParam("id") Long id, @FormParam("name") String name, @FormParam("eis") Long eisId, @FormParam("eis2") Long eis2Id, @FormParam("sauce") Long sauceId, @FormParam("zutat") String[] zutatenId, @FormParam("anzahl") Long anzahl) {
        Nutzer nutzer = this.eingeloggterKunde(sec);
        Eigenkreation eigenkreationAlt = eigenkreationRepo.getById(id);
        if(nutzer == null) return Response.status(Status.NOT_FOUND).build();
        Eis eissorte1 = eisRepo.getById(eisId);
        Eis eissorte2 = null;
        if(eis2Id != null) {
            eissorte2= eisRepo.getById(eis2Id);
        }else {
            eissorte2 = eissorte1;
        }
        Sauce sauce = null;
        if(sauceId != null){
            sauce = sauceRepo.getById(sauceId);
        }
        List<Zutat> zutaten = new ArrayList<>();
        for(String zutatId : zutatenId) {
        if(zutatenId != null)
            {
                zutaten.add(zutatRepo.getById(Long.valueOf(zutatId)));
            }
        }
        if(name == null) {
            name = eigenkreationAlt.getName();
        }
        Eigenkreation eigenkreation = new Eigenkreation(null, eissorte1, eissorte2, zutaten, sauce, name);
        if(anzahl == null) {
            eigenkreationRepo.create(nutzer, eigenkreation);
        }else{
            eigenkreationRepo.create(nutzer, eigenkreation, null);
        }
        eigenkreationAlt.setBestellbar(false);
        
        return Response.seeOther(UriBuilder.fromPath("/eigenkreationen").build()).build();
    }
    
    @POST
    @Transactional
    @RolesAllowed({"Admin", "Kunde"})
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(
        summary = "Fuegt eine bestimmte Eigenkreation der Bestellung hinzu",
        description = "Fuegt eine bereits existierende Eigenkreation ueber die uerbergebene ID, " + 
                        "eines angemeldeten Nutzers, der aktuellen Bestellung hinzu"
    )
    public Response post(@Context SecurityContext sec, @NotNull @PathParam("id") Long id, @NotNull @PositiveOrZero @FormParam("anzahl") Long anzahl) {
        Nutzer nutzer = this.eingeloggterKunde(sec);
        if(nutzer == null) return Response.status(Status.NOT_FOUND).build();
        eigenkreationRepo.post(id, anzahl, nutzer);
        return Response.ok().header("Refresh", "0; url=/bestellungen").build();
    }
    
    @PUT
    @Transactional
    @RolesAllowed({"Admin", "Kunde"})
    @Path("/zutaten/{zutatnummer:\\d+}")
    @Operation(
        summary = "Aendern einer Zutat einer bestimmten Eigenkreation",
        description = "Aendern einer Zutaten einer bestimmten Eigenkreation eines angemeldeten Nutzers ueber die uebergebene ID"
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
    @Path("/loeschen")
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
        return Response.ok().header("Refresh", "0; url=/bestellungen").build();
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
