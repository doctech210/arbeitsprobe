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

import de.hsos.swa.coldstoneicecreator.kreationen.boundary.dto.HauskreationDTO;
import de.hsos.swa.coldstoneicecreator.kreationen.control.HauskreationControl;
import de.hsos.swa.coldstoneicecreator.kreationen.entity.Hauskreation;
import de.hsos.swa.coldstoneicecreator.nutzer.entity.Nutzer;
import de.hsos.swa.coldstoneicecreator.produkt.boundary.dto.EisDTO;
import de.hsos.swa.coldstoneicecreator.produkt.boundary.dto.SauceDTO;
import de.hsos.swa.coldstoneicecreator.produkt.boundary.dto.ZutatDTO;
import de.hsos.swa.coldstoneicecreator.produkt.entity.Allergene;
import de.hsos.swa.coldstoneicecreator.produkt.entity.Eis;
import de.hsos.swa.coldstoneicecreator.produkt.entity.Sauce;
import de.hsos.swa.coldstoneicecreator.produkt.entity.Zutat;
import de.hsos.swa.coldstoneicecreator.produkt.gateway.EisRepository;
import de.hsos.swa.coldstoneicecreator.produkt.gateway.SauceRepository;
import de.hsos.swa.coldstoneicecreator.produkt.gateway.ZutatRepository;
import io.quarkus.qute.CheckedTemplate;
import io.quarkus.qute.TemplateInstance;

@RequestScoped
@Path("/hauskreationen")
@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
@Produces(MediaType.TEXT_HTML)
@Retry(maxRetries = 4)
@Timeout(250)
public class HauskreationPage {
    
    @Inject
    HauskreationControl hauskreationRepo;

    @Inject
    EisRepository eisRepo;

    @Inject
    SauceRepository sauceRepo;

    @Inject
    ZutatRepository zutatRepo;

    @CheckedTemplate
    static class Templates {

        static native TemplateInstance hauskreationAlle(List<HauskreationDTO> hauskreationenDTO);
        
        static native TemplateInstance hauskreationErstellen(List<EisDTO> eisDTO, List<ZutatDTO> zutatenDTO, List<SauceDTO> sauceDTO);

        static native TemplateInstance error(int errorCode, String errorMessage);
    }

    @GET
    @RolesAllowed({"Admin", "Kunde"})
    @Operation(
        summary = "Gibt alle Hauskreationen zurueck",
        description = "Gibt alle Hauskreationen mit dem angegebenen Filter zurueck"
    )
    public TemplateInstance get(@Valid @QueryParam("Allergene") List<Allergene> allergene) {
        List<Hauskreation> alle = hauskreationRepo.get();
        if(allergene != null) {
            alle = hauskreationRepo.getOhneAllergene(allergene);
        }
        List<HauskreationDTO> alleDTO = new ArrayList<>();
        for(Hauskreation hauskreation : alle) {
            alleDTO.add(HauskreationDTO.Converter.toDTO(hauskreation));
        } 
        //return Response.ok(alleDTO).build();
        return Templates.hauskreationAlle(alleDTO);
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

        return Templates.hauskreationErstellen(alleEisDTO, alleZutatenDTO, alleSauceDTO);
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    @RolesAllowed({"Admin"})
    @Operation(
        summary = "Erstellt eine neue Hauskreation",
        description = "Erstellt eine neue Hauskreation für einen angemeldeten Nutzer und fuegt sie automatisch " +
                        "der aktuellen Bestellung hinzu"
    )
        public Response post(@FormParam("name") String name, @FormParam("eis") Long eisId, @FormParam("eis2") Long eis2Id, @FormParam("sauce") Long sauceId, @FormParam("zutat") String[] zutatenId) {
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
        Hauskreation hauskreation = new Hauskreation(null, eissorte1, eissorte2, zutaten, sauce, name);
        hauskreationRepo.create(hauskreation);
        return Response.seeOther(UriBuilder.fromPath("/hauskreationen").build()).build();

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
