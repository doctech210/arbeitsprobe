package de.hsos.swa.coldstoneicecreator.produkt.boundary.dto;

import java.util.Set;

import de.hsos.swa.coldstoneicecreator.produkt.entity.Allergene;
import de.hsos.swa.coldstoneicecreator.produkt.entity.Sauce;

public class SauceDTO {
    public Long id;
    public String name;
    public Set<Allergene> allergene;
    
    public SauceDTO() {
    }
    
    public SauceDTO(Long id, String name, Set<Allergene> allergene) {
        this.id = id;
        this.name = name;
        this.allergene = allergene;
    }

    public static class Converter{

        public static SauceDTO toDTO(Sauce sauce){
            if(sauce == null) return null;
            return new SauceDTO(sauce.getId(), sauce.getName(), sauce.getAllergene());
        }

        public static Sauce toSauce(SauceDTO sauceDTO){
            if(sauceDTO == null) return null;
            return new Sauce(sauceDTO.id, sauceDTO.name, sauceDTO.allergene);
        }
    }
}
