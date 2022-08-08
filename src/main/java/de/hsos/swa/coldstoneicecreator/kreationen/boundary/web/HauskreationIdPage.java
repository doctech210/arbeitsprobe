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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
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

import de.hsos.swa.coldstoneicecreator.kreationen.boundary.dto.HauskreationDTO;
import de.hsos.swa.coldstoneicecreator.kreationen.control.HauskreationControl;
import de.hsos.swa.coldstoneicecreator.kreationen.entity.Hauskreation;
import de.hsos.swa.coldstoneicecreator.nutzer.entity.Nutzer;
import de.hsos.swa.coldstoneicecreator.produkt.boundary.dto.EisDTO;
import de.hsos.swa.coldstoneicecreator.produkt.boundary.dto.SauceDTO;
import de.hsos.swa.coldstoneicecreator.produkt.boundary.dto.ZutatDTO;
import de.hsos.swa.coldstoneicecreator.produkt.control.EisControl;
import de.hsos.swa.coldstoneicecreator.produkt.control.SauceControl;
import de.hsos.swa.coldstoneicecreator.produkt.control.ZutatControl;
import de.hsos.swa.coldstoneicecreator.produkt.entity.Allergene;
import de.hsos.swa.coldstoneicecreator.produkt.entity.Eis;
import de.hsos.swa.coldstoneicecreator.produkt.entity.Sauce;
import de.hsos.swa.coldstoneicecreator.produkt.entity.Zutat;
import io.quarkus.qute.CheckedTemplate;
import io.quarkus.qute.TemplateInstance;

@RequestScoped
@Path("/hauskreationen/{id:\\d+}")
@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
@Produces(MediaType.TEXT_HTML)
@Retry(maxRetries = 4)
@Timeout(250)
public class HauskreationIdPage {
    
    @Inject
    HauskreationControl hauskreationRepo;
    
    @Inject
    EisControl eisRepo;

    @Inject
    ZutatControl zutatRepo;
    
    @Inject
    SauceControl sauceRepo;

    @CheckedTemplate
    static class Templates {

        static native TemplateInstance hauskreationEinzeln(HauskreationDTO hauskreationDTO, Nutzer nutzer, List<EisDTO> eisDTO, List<ZutatDTO> zutatenDTO, List<SauceDTO> sauceDTO);

        static native TemplateInstance error(int errorCode, String errorMessage);
    }

    @GET
    @RolesAllowed({"Admin", "Kunde"})
    @Operation(
        summary = "Gibt eine bestimmte Hauskreation zurueck",
        description = "Gibt eine bestimmte Hauskreation mit der uebergebenen ID zurueck"
    )
    public TemplateInstance get(@Valid @QueryParam("Allergene") List<Allergene> allergene, @NotNull @PathParam("id") Long id, @Context SecurityContext sec) {
        Nutzer nutzer = this.eingeloggterKunde(sec);
        if(nutzer == null) return Templates.error(Status.FORBIDDEN.getStatusCode(), "Bitte erst einloggen");
        Hauskreation hauskreation = hauskreationRepo.getById(id);
        if(hauskreation == null) return Templates.error(Response.Status.BAD_REQUEST.getStatusCode(), "Hauskreation nicht gefunden");
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
        HauskreationDTO hauskreationDTO = HauskreationDTO.Converter.toDTO(hauskreation);
        return Templates.hauskreationEinzeln(hauskreationDTO, nutzer, alleEisDTO, alleZutatenDTO, alleSauceDTO);
    }

    @POST
    @Transactional
    @Path("/put/")
    @RolesAllowed({"Admin"})
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(
        summary = "Aendern einer bestimmten Hauskreation",
        description = "Aendern einer bestimmmten Hauskreation ueber die uebergebene ID"
    )
    public Response put(@Context SecurityContext sec, @NotNull @PathParam("id") Long id, @FormParam("name") String name, @FormParam("eis") Long eisId, @FormParam("eis2") Long eis2Id, @FormParam("sauce") Long sauceId, @FormParam("zutat") String[] zutatenId) {
        Nutzer nutzer = this.eingeloggterKunde(sec);
        Hauskreation hauskreationAlt = hauskreationRepo.getById(id);
        if(nutzer == null) return Response.status(Status.NOT_FOUND).build();
        Eis eissorte1 = eisRepo.getById(eisId);
        Eis eissorte2 = null;
        if(eis2Id != null) {
            eissorte2= eisRepo.getById(eis2Id);
        }else {
            eissorte2 = eissorte1;
        }
        Sauce sauce = null;
        if(sauceId != null && Long.compare(sauceId, Long.valueOf(0)) != 0){
            sauce = sauceRepo.getById(sauceId);
        }
        List<Zutat> zutaten = new ArrayList<>();
        for(String zutatId : zutatenId) {
        if(zutatenId != null)
            {
                zutaten.add(zutatRepo.getById(Long.valueOf(zutatId)));
            }
        }
        if(name == null || name.equals("")) {
            name = hauskreationAlt.getName();
        }
        Hauskreation hauskreation = new Hauskreation(null, eissorte1, eissorte2, zutaten, sauce, name);
        hauskreationRepo.put(id, hauskreation);
        return Response.seeOther(UriBuilder.fromPath("/hauskreationen").build()).build();
    }
    
    @POST
    @Transactional
    @RolesAllowed({"Admin", "Kunde"})
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(
        summary = "Fuegt eine bestimmte Hauskreation der Bestellung hinzu",
        description = "Fuegt eine bestimmte Hauskreation über die uerbergebenen ID der aktuellen Bestellung hinzu"
    )
    public Response post(@Context SecurityContext sec, @NotNull @PathParam("id") Long id, @NotNull @PositiveOrZero @FormParam("anzahl") Long anzahl) {
        Nutzer nutzer = this.eingeloggterKunde(sec);
        if(nutzer == null) return Response.status(Status.NOT_FOUND).build();
        Hauskreation hauskreation = hauskreationRepo.getById(id);
        hauskreationRepo.create(nutzer, hauskreation, anzahl);
        return Response.ok().header("Refresh", "0; url=/bestellungen").build();
    }

    private Nutzer eingeloggterKunde(SecurityContext sec) {
        Principal user = sec.getUserPrincipal();
        if(user == null) return null;
        Optional<Nutzer> optKunde = Nutzer.find("name", user.getName()).firstResultOptional();
        if(optKunde.isEmpty()) return null;
        return optKunde.get();
    }
}
