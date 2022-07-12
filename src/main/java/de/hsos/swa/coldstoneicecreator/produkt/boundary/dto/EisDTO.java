package de.hsos.swa.coldstoneicecreator.produkt.boundary.dto;

import de.hsos.swa.coldstoneicecreator.produkt.entity.Eis;

public class EisDTO {
    public Long id;
    public String name;
    public boolean laktose;
    public boolean ei;
    public boolean nuss;
    
    public EisDTO() {
    }
    
    public EisDTO(Long id, String name, boolean laktose, boolean ei, boolean nuss) {
        this.id = id;
        this.name = name;
        this.laktose = laktose;
        this.ei = ei;
        this.nuss = nuss;
    }

    public static class Converter{

        public static EisDTO toDTO(Eis eis){
            return new EisDTO(eis.getId(), eis.getName(), eis.isLaktose(), eis.isEi(), eis.isNuss());
        }

        public static Eis toEis(EisDTO eisDTO){
            return new Eis(eisDTO.id, eisDTO.name, eisDTO.laktose, eisDTO.ei, eisDTO.nuss);
        }
    }    
}
