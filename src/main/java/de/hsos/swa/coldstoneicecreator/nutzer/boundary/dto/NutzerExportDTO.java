//@author Stefan Bierenriede 852142

package de.hsos.swa.coldstoneicecreator.nutzer.boundary.dto;

import de.hsos.swa.coldstoneicecreator.nutzer.entity.Nutzer;

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
    }

}
