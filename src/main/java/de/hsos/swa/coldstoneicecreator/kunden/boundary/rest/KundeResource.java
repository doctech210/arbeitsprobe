package de.hsos.swa.coldstoneicecreator.kunden.boundary.rest;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import de.hsos.swa.coldstoneicecreator.kunden.boundary.dto.KundeExportDTO;
import de.hsos.swa.coldstoneicecreator.kunden.boundary.dto.KundeImportDTO;
import de.hsos.swa.coldstoneicecreator.kunden.control.KundeControl;
import de.hsos.swa.coldstoneicecreator.kunden.entity.Nutzer;
import de.hsos.swa.coldstoneicecreator.kunden.entity.UserLogin;

@RequestScoped
@Path("/kunden")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class KundeResource {
    
    @Inject
    KundeControl kc;

    @GET
    @RolesAllowed({"Admin"})
    public Response get() {
        List<Nutzer> alle = kc.get();
        List<KundeExportDTO> alleDTO = new ArrayList<>();
        for(Nutzer kunde : alle) {
            alleDTO.add(KundeExportDTO.Converter.toDTO(kunde));
        }
        return Response.ok(alleDTO).build();
    }

    @POST
    @Transactional
    @PermitAll
    public Response post(KundeImportDTO kundeImportDTO) {
        Nutzer kunde = KundeImportDTO.Converter.toKunde(kundeImportDTO);
        kc.create(kunde);
        return Response.ok(UserLogin.listAll()).build();
    }
}
