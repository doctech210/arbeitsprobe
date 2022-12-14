//@author Stefan Bierenriede 852142

package de.hsos.swa.coldstoneicecreator.kreationen.boundary.web;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
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
import org.eclipse.microprofile.metrics.annotation.Counted;
import org.eclipse.microprofile.metrics.annotation.Timed;

import io.quarkus.qute.CheckedTemplate;
import io.quarkus.qute.TemplateInstance;
import de.hsos.swa.coldstoneicecreator.kreationen.boundary.dto.EigenkreationDTO;
import de.hsos.swa.coldstoneicecreator.kreationen.control.EigenkreationControl;
import de.hsos.swa.coldstoneicecreator.kreationen.entity.Eigenkreation;
import de.hsos.swa.coldstoneicecreator.nutzer.entity.Nutzer;
import de.hsos.swa.coldstoneicecreator.produkt.boundary.dto.EisDTO;
import de.hsos.swa.coldstoneicecreator.produkt.boundary.dto.SauceDTO;
import de.hsos.swa.coldstoneicecreator.produkt.boundary.dto.ZutatDTO;
import de.hsos.swa.coldstoneicecreator.produkt.entity.*;
import de.hsos.swa.coldstoneicecreator.produkt.gateway.EisRepository;
import de.hsos.swa.coldstoneicecreator.produkt.gateway.SauceRepository;
import de.hsos.swa.coldstoneicecreator.produkt.gateway.ZutatRepository;

@RequestScoped
@Path("/eigenkreationen")
@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
@Produces(MediaType.TEXT_HTML)
@Retry(maxRetries = 4)
@Timeout(250)
public class EigenkreationPage {
    
    @Inject
    EigenkreationControl eigenkreationRepo;

    @Inject
    EisRepository eisRepo;

    @Inject
    SauceRepository sauceRepo;

    @Inject
    ZutatRepository zutatRepo;

    @CheckedTemplate
    static class Templates {

        static native TemplateInstance eigenkreationAlle(List<EigenkreationDTO> eigenkreationenDTO);

        static native TemplateInstance eigenkreationErstellen(List<EisDTO> eisDTO, List<ZutatDTO> zutatenDTO, List<SauceDTO> sauceDTO);

        static native TemplateInstance error(int errorCode, String errorMessage);
    }

    @GET
    @Counted(
        name = "getEigenkreationen",
        description = "Anzahl wie oft sich alle Eigenkreationen angezeigt wurden"
    )
    @Timed(
        name = "getEigenkreationenTimed",
        description = "Zeit die benoetigt wird, um sich alle Eigenkreationen zu holen"
    )
    @RolesAllowed({"Admin", "Kunde"})
    @Operation(
        summary = "Gibt alle Eigenkreationen des Nutzer zurueck",
        description = "Gibt alle Eigenkreationen des angemeldeten Nutzers mit dem eingestellten Filter zurueck"
    )
    public TemplateInstance get(@Valid @QueryParam("Allergene") List<Allergene> allergene, @Context SecurityContext sec) {
        Nutzer nutzer = this.eingeloggterKunde(sec);
        if(nutzer == null) return Templates.error(Status.FORBIDDEN.getStatusCode(), "Bitte erst einloggen");
        List<Eigenkreation> alle = nutzer.getEigenkreationen();
        if(allergene != null) alle = eigenkreationRepo.getOhneAllergene(allergene);
        List<EigenkreationDTO> alleDTO = new ArrayList<>();
        for(Eigenkreation eigenkreation : alle) {
            alleDTO.add(EigenkreationDTO.Converter.toDTO(eigenkreation));
        }
        List<Zutat> alleZutaten = Zutat.listAll();
        List<ZutatDTO> alleZutatenDTO = new ArrayList<>();
        for(Zutat zutat : alleZutaten) {
            alleZutatenDTO.add(ZutatDTO.Converter.toDTO(zutat));
        }
        return Templates.eigenkreationAlle(alleDTO);
    }

    @GET
    @Path("/erstellen")
    @RolesAllowed({"Admin", "Kunde"})
    public TemplateInstance getErstellen(@Valid @QueryParam("Allergene") List<Allergene> allergene, @Context SecurityContext sec) {
        Nutzer nutzer = this.eingeloggterKunde(sec);
        if(nutzer == null) return Templates.error(Status.FORBIDDEN.getStatusCode(), "Bitte erst einloggen");
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

        return Templates.eigenkreationErstellen(alleEisDTO, alleZutatenDTO, alleSauceDTO);
    }

    @POST
    @Counted(
        name = "postEigenkreationen",
        description = "Anzahl wie oft sich eine Eigenkreation erstellt wurde"
    )
    @Timed(
        name = "postEigenkreationenTimed",
        description = "Zeit die benoetigt wird, um eine persoenliche Eigenkreation zu erstellen"
    )
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    @RolesAllowed({"Admin", "Kunde"})
    @Operation(
        summary = "Erstellt eine neue Eigenkreation",
        description = "Erstellt eine neue Eigenkreation f??r einen angemeldeten Nutzer und fuegt sie automatisch " +
                        "der aktuellen Bestellung hinzu"
    )
        public Response post(@Context SecurityContext sec, @FormParam("name") String name, @FormParam("eis") Long eisId, @FormParam("eis2") Long eis2Id, @FormParam("sauce") Long sauceId, @FormParam("anzahl") Long anzahl, @FormParam("zutat") String[] zutatenId) {
        Nutzer nutzer = this.eingeloggterKunde(sec);
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
        Eigenkreation eigenkreation = new Eigenkreation(null, eissorte1, eissorte2, zutaten, sauce, name);
        eigenkreationRepo.create(nutzer, eigenkreation, anzahl);

        return Response.seeOther(UriBuilder.fromPath("/eigenkreationen").build()).build();
    }

    private Nutzer eingeloggterKunde(SecurityContext sec) {
        Principal user = sec.getUserPrincipal();
        if(user == null) return null;
        Optional<Nutzer> optKunde = Nutzer.find("name", user.getName()).firstResultOptional();
        if(optKunde.isEmpty()) return null;
        return optKunde.get();
    }
}
