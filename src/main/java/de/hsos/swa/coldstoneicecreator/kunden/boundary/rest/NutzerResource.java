package de.hsos.swa.coldstoneicecreator.kunden.boundary.rest;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.eclipse.microprofile.openapi.annotations.Operation;

import de.hsos.swa.coldstoneicecreator.kunden.boundary.dto.NutzerExportDTO;
import de.hsos.swa.coldstoneicecreator.kunden.boundary.dto.NutzerImportDTO;
import de.hsos.swa.coldstoneicecreator.kunden.control.NutzerControl;
import de.hsos.swa.coldstoneicecreator.kunden.entity.Nutzer;

@RequestScoped
@Path("/nutzer")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class NutzerResource {
    
    @Inject
    NutzerControl nutzerRepo;

    @GET
    @RolesAllowed({"Admin"})
    @Operation(
        summary = "Gibt alle Nutzer zurueck",
        description = "Gibt alle angemeldeten Nutzer zurueck"
    )
    public Response get() {
        List<Nutzer> alle = nutzerRepo.get();
        List<NutzerExportDTO> alleDTO = new ArrayList<>();
        for(Nutzer nutzer : alle) {
            alleDTO.add(NutzerExportDTO.Converter.toDTO(nutzer));
        }
        return Response.ok(alleDTO).build();
    }

    @POST
    @Transactional
    @PermitAll
    @Operation(
        summary = "Erstellt einen neuen Nutzer",
        description = "Erstellt einen neuen Nutzer"
    )
    public Response post(@Valid @NotNull NutzerImportDTO nutzerImportDTO) {
        Nutzer nutzer = NutzerImportDTO.Converter.toNutzer(nutzerImportDTO);
        nutzerRepo.create(nutzer);
        return Response.ok().build();
    }
}
