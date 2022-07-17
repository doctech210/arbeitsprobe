package de.hsos.swa.coldstoneicecreator.kunden.boundary.dto;

import de.hsos.swa.coldstoneicecreator.kunden.entity.Nutzer;

public class KundeExportDTO {

    public Long id;
    public String name;
    public String role;
    
    public KundeExportDTO() {
    }

    public KundeExportDTO(Long id, String name, String role) {
        this.id = id;
        this.name = name;
        this.role = role;
    }

    public static class Converter {

        public static KundeExportDTO toDTO(Nutzer kunde){
            return new KundeExportDTO(kunde.getId(), kunde.getName(), kunde.getRole());
        }

        /*public static Kunde toKunde(KundeImportDTO kundeImportDTO) {
            return new Kunde(kundeImportDTO.name, kundeImportDTO.passwort, null);
        }*/
    }

}
