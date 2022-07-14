package de.hsos.swa.coldstoneicecreator.kreationen.boundary.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.annotation.security.RolesAllowed;

import de.hsos.swa.coldstoneicecreator.kreationen.entity.Hauskreation;
import de.hsos.swa.coldstoneicecreator.produkt.boundary.dto.EisDTO;
import de.hsos.swa.coldstoneicecreator.produkt.boundary.dto.SauceDTO;
import de.hsos.swa.coldstoneicecreator.produkt.boundary.dto.ZutatDTO;
import de.hsos.swa.coldstoneicecreator.produkt.entity.Allergene;
import de.hsos.swa.coldstoneicecreator.produkt.entity.Zutat;

@RolesAllowed("admin")
public class HauskreationDTO implements KreationDTO{
    
    public Long id;
    public EisDTO eissorte;
    public EisDTO eissorte2;
    public List<ZutatDTO> zutaten;
    public SauceDTO sauce;
    public String name;
    public Set<Allergene> allergene;
    
    public HauskreationDTO() {
    }

    public HauskreationDTO(Long id, EisDTO eissorte, EisDTO eissorte2, List<ZutatDTO> zutaten, SauceDTO sauce,
            String name) {
        this.id = id;
        this.eissorte = eissorte;
        this.eissorte2 = eissorte2;
        this.zutaten = zutaten;
        this.sauce = sauce;
        this.name = name;
        this.addAllergene(eissorte.allergene);
        this.addAllergene(eissorte2.allergene);
        for(ZutatDTO zutat : zutaten){
            this.addAllergene(zutat.allergene);
        }
        this.addAllergene(sauce.allergene);
    }

    @Override
    public void addAllergene(Set<Allergene> allergene){
        allergene.addAll(allergene);
    }

    public static class Converter{

        public static HauskreationDTO toDTO(Hauskreation hauskreation) {
            List<ZutatDTO> liste = new ArrayList<>();
            for(Zutat zutat : hauskreation.getZutaten()) {
                liste.add(ZutatDTO.Converter.toDTO(zutat));
            }
            return new HauskreationDTO(hauskreation.getId(), EisDTO.Converter.toDTO(hauskreation.getEissorte()), 
             EisDTO.Converter.toDTO(hauskreation.getEissorte2()), liste, SauceDTO.Converter.toDTO(hauskreation.getSauce()), 
             hauskreation.getName());
        }

        public static Hauskreation toHauskreation(HauskreationDTO hauskreationDTO) {
            List<Zutat> liste = new ArrayList<>();
            for(ZutatDTO zutatDTO : hauskreationDTO.zutaten) {
                liste.add(ZutatDTO.Converter.toZutat(zutatDTO));
            }
            return new Hauskreation(hauskreationDTO.id, EisDTO.Converter.toEis(hauskreationDTO.eissorte),
             EisDTO.Converter.toEis(hauskreationDTO.eissorte2), liste, SauceDTO.Converter.toSauce(hauskreationDTO.sauce),
             hauskreationDTO.name, hauskreationDTO.allergene);
        }
    }
}
