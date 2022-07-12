package de.hsos.swa.coldstoneicecreator.produkt.boundary.dto;

import de.hsos.swa.coldstoneicecreator.produkt.entity.Sauce;

public class SauceDTO {
    public Long id;
    public String name;
    public boolean gluten;
    public boolean ei;
    public boolean nuss;
    public boolean alkohol;
    
    public SauceDTO() {
    }
    
    public SauceDTO(Long id, String name, boolean gluten, boolean ei, boolean nuss, boolean alkohol) {
        this.id = id;
        this.name = name;
        this.gluten = gluten;
        this.ei = ei;
        this.nuss = nuss;
        this.alkohol = alkohol;
    }

    public static class Converter{

        public static SauceDTO toDTO(Sauce sauce){
            return new SauceDTO(sauce.getId(), sauce.getName(), sauce.isGluten(), sauce.isEi(), sauce.isNuss(), sauce.isAlkohol());
        }

        public static Sauce toSauce(SauceDTO sauceDTO){
            return new Sauce(sauceDTO.id, sauceDTO.name, sauceDTO.gluten, sauceDTO.ei, sauceDTO.nuss, sauceDTO.alkohol);
        }
    }
}
