package de.hsos.swa.coldstoneicecreator.kunden.boundary.dto;

import de.hsos.swa.coldstoneicecreator.kunden.entity.Nutzer;

public class NutzerImportDTO {
    public Long id;
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

        /*public static toDTO(Kunde kunde){
            return new KundeImportDTO(kunde.getId(), kunde.getName(), kunde.getPasswort())
        }*/

        public static Nutzer toNutzer(NutzerImportDTO nutzerImportDTO) {
            return new Nutzer(nutzerImportDTO.name, nutzerImportDTO.passwort, null);
        }
    }
}
