package de.hsos.swa.coldstoneicecreator.kunden.boundary.dto;

import de.hsos.swa.coldstoneicecreator.kunden.entity.Nutzer;

public class KundeImportDTO {
    public Long id;
    public String name;
    public String passwort;
    
    public KundeImportDTO() {
    }

    public KundeImportDTO(Long id, String name, String passwort) {
        this.id = id;
        this.name = name;
        this.passwort = passwort;
    }

    public static class Converter {

        /*public static toDTO(Kunde kunde){
            return new KundeImportDTO(kunde.getId(), kunde.getName(), kunde.getPasswort())
        }*/

        public static Nutzer toKunde(KundeImportDTO kundeImportDTO) {
            return new Nutzer(kundeImportDTO.name, kundeImportDTO.passwort, null);
        }
    }
}
