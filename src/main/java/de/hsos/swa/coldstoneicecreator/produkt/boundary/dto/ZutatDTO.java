package de.hsos.swa.coldstoneicecreator.produkt.boundary.dto;

import java.util.Set;

import javax.validation.constraints.Pattern;

import de.hsos.swa.coldstoneicecreator.produkt.entity.Allergene;
import de.hsos.swa.coldstoneicecreator.produkt.entity.Zutat;

public class ZutatDTO {
    public Long id;
    @Pattern(regexp = "^[a-zA-Z]*$",
             message = "Use only letters for the name!")
    public String name;
    public boolean premium;
    public Set<Allergene> allergene;
    
    public ZutatDTO() {
    }

    public ZutatDTO(Long id, String name, boolean premium, Set<Allergene> allergene) {
        this.id = id;
        this.name = name;
        this.premium = premium;
        this.allergene = allergene;
    }

    public static class Converter{

        public static ZutatDTO toDTO(Zutat zutat) {
            if(zutat == null) return null;
            return new ZutatDTO(zutat.getId(), zutat.getName(), zutat.isPremium(), zutat.getAllergene());
        }

        public static Zutat toZutat(ZutatDTO zutatDTO) {
            if(zutatDTO == null) return null;
            return new Zutat(zutatDTO.id, zutatDTO.name, zutatDTO.premium, zutatDTO.allergene);
        }
    }
}
