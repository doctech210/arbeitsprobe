package de.hsos.swa.coldstoneicecreator.kunden.boundary.dto;

import de.hsos.swa.coldstoneicecreator.kunden.entity.Nutzer;

public class NutzerExportDTO {

    public Long id;
    public String name;
    public String role;
    
    public NutzerExportDTO() {
    }

    public NutzerExportDTO(Long id, String name, String role) {
        this.id = id;
        this.name = name;
        this.role = role;
    }

    public static class Converter {

        public static NutzerExportDTO toDTO(Nutzer nutzer){
            if(nutzer == null) return null;
            return new NutzerExportDTO(nutzer.getId(), nutzer.getName(), nutzer.getRole());
        }

        /*public static Kunde toKunde(KundeImportDTO kundeImportDTO) {
            return new Kunde(kundeImportDTO.name, kundeImportDTO.passwort, null);
        }*/
    }

}
