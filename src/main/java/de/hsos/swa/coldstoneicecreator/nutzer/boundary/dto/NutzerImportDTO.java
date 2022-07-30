package de.hsos.swa.coldstoneicecreator.nutzer.boundary.dto;

import javax.validation.constraints.Pattern;

import de.hsos.swa.coldstoneicecreator.nutzer.entity.Nutzer;

public class NutzerImportDTO {
    public Long id;
    @Pattern(regexp = "^[a-zA-Z\\s]*$",
             message = "Use only letters for the name!")
    public String name;
    public String passwort;
    
    public NutzerImportDTO() {
    }

    public NutzerImportDTO(Long id, String name, String passwort) {
        this.id = id;
        this.name = name;
        this.passwort = passwort;
    }

    public static class Converter {

        public static Nutzer toNutzer(NutzerImportDTO nutzerImportDTO) {
            if(nutzerImportDTO == null) return null;
            Nutzer nutzer = new Nutzer(nutzerImportDTO.name, nutzerImportDTO.passwort, null);
            //nutzer.add(nutzerImportDTO.name, nutzerImportDTO.passwort, null);
            return nutzer;
        }
    }
}
