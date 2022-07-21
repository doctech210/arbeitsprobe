package de.hsos.swa.coldstoneicecreator.kunden.boundary.dto;

import java.util.List;
import java.util.ArrayList;

import de.hsos.swa.coldstoneicecreator.kunden.entity.Nutzer;
import de.hsos.swa.coldstoneicecreator.kreationen.entity.Eigenkreation;
import de.hsos.swa.coldstoneicecreator.kreationen.boundary.dto.EigenkreationDTO;

public class NutzerDTO {
    public Long id;
    public String name;
    public String passwort;
    public List<EigenkreationDTO> eigenkreationenDTO;

    public NutzerDTO() {
    }

    public NutzerDTO(Long id, String name, String passwort, List<EigenkreationDTO> eigenkreationenDTO) {
        this.id = id;
        this.name = name;
        this.passwort = passwort;
        this.eigenkreationenDTO = eigenkreationenDTO;
    }

    public static class Converter {

        public static NutzerDTO toDTO(Nutzer nutzer) {
            List<EigenkreationDTO> eigenkreationenDTO = new ArrayList<>();
            for(Eigenkreation eigenkreation : nutzer.getEigenkreationen()) {
                eigenkreationenDTO.add(EigenkreationDTO.Converter.toDTO(eigenkreation));
            }
            return new NutzerDTO(nutzer.getId(), nutzer.getName(), nutzer.getPasswort(), eigenkreationenDTO);
        }

        public static Nutzer toNutzer(NutzerDTO nutzerDTO) {
            List<Eigenkreation> eigenkreationen = new ArrayList<>();
            for(EigenkreationDTO eigenkreationDTO : nutzerDTO.eigenkreationenDTO) {
                eigenkreationen.add(EigenkreationDTO.Converter.toEigenkreation(eigenkreationDTO));
            }
            Nutzer nutzer = new Nutzer(nutzerDTO.name, nutzerDTO.passwort, eigenkreationen);
            nutzer.setEigenkreationen(eigenkreationen);
            return nutzer;
        }
    }
}
