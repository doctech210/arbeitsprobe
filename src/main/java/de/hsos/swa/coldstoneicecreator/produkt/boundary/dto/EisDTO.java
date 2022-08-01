//@author Marvin Gels 868603

package de.hsos.swa.coldstoneicecreator.produkt.boundary.dto;

import java.util.Set;

import javax.validation.constraints.Pattern;

import de.hsos.swa.coldstoneicecreator.produkt.entity.Allergene;
import de.hsos.swa.coldstoneicecreator.produkt.entity.Eis;

public class EisDTO {
    public Long id;
    @Pattern(regexp = "^[a-zA-Z\\s]*$", 
             message = "Use only letters for the name!")
    public String name;
    public Set<Allergene> allergene;
    
    public EisDTO() {
    }
    
    public EisDTO(Long id, String name, Set<Allergene> allergene) {
        this.id = id;
        this.name = name;
        this.allergene = allergene;
    }

    public static class Converter{

        public static EisDTO toDTO(Eis eis){
            if(eis == null) return null;
            return new EisDTO(eis.getId(), eis.getName(), eis.getAllergene());
        }

        public static Eis toEis(EisDTO eisDTO){
            if(eisDTO == null) return null;
            return new Eis(eisDTO.id, eisDTO.name, eisDTO.allergene);
        }
    }    
}
