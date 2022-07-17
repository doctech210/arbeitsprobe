package de.hsos.swa.coldstoneicecreator.kunden.boundary.dto;

import java.util.List;
import java.util.ArrayList;

import de.hsos.swa.coldstoneicecreator.kunden.entity.Nutzer;
import de.hsos.swa.coldstoneicecreator.kreationen.entity.Eigenkreation;
import de.hsos.swa.coldstoneicecreator.kreationen.boundary.dto.EigenkreationDTO;

public class KundeDTO {
    public Long id;
    public String name;
    public String passwort;
    public List<EigenkreationDTO> eigenkreationenDTO;

    public KundeDTO() {
    }

    public KundeDTO(Long id, String name, String passwort, List<EigenkreationDTO> eigenkreationenDTO) {
        this.id = id;
        this.name = name;
        this.passwort = passwort;
        this.eigenkreationenDTO = eigenkreationenDTO;
    }

    public static class Converter {

        public static KundeDTO toDTO(Nutzer kunde) {
            List<EigenkreationDTO> eigenkreationenDTO = new ArrayList<>();
            for(Eigenkreation eigenkreation : kunde.getEigenkreationen()) {
                eigenkreationenDTO.add(EigenkreationDTO.Converter.toDTO(eigenkreation));
            }
            return new KundeDTO(kunde.getId(), kunde.getName(), kunde.getPasswort(), eigenkreationenDTO);
        }

        public static Nutzer toKunde(KundeDTO kundeDTO) {
            List<Eigenkreation> eigenkreationen = new ArrayList<>();
            for(EigenkreationDTO eigenkreationDTO : kundeDTO.eigenkreationenDTO) {
                eigenkreationen.add(EigenkreationDTO.Converter.toEigenkreation(eigenkreationDTO));
            }
            Nutzer kunde = new Nutzer(kundeDTO.name, kundeDTO.passwort, eigenkreationen);
            kunde.setEigenkreationen(eigenkreationen);
            return kunde;
        }
    }
}
