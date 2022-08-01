package de.hsos.swa.coldstoneicecreator.kreationen.boundary.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.math.BigDecimal;

import javax.validation.constraints.Pattern;

import de.hsos.swa.coldstoneicecreator.kreationen.entity.Hauskreation;
import de.hsos.swa.coldstoneicecreator.produkt.boundary.dto.EisDTO;
import de.hsos.swa.coldstoneicecreator.produkt.boundary.dto.SauceDTO;
import de.hsos.swa.coldstoneicecreator.produkt.boundary.dto.ZutatDTO;
import de.hsos.swa.coldstoneicecreator.produkt.entity.Allergene;
import de.hsos.swa.coldstoneicecreator.produkt.entity.Zutat;

public class HauskreationDTO{
    
    public Long id;
    public EisDTO eissorte;
    public EisDTO eissorte2;
    public List<ZutatDTO> zutaten;
    public SauceDTO sauce;
    @Pattern(regexp = "^[a-zA-Z\\s]*$",
             message = "Use only letters for the name!")
    public String name;
    public Set<Allergene> allergene;
    public String preis;
    
    public HauskreationDTO() {
    }

    public HauskreationDTO(Long id, EisDTO eissorte, EisDTO eissorte2, List<ZutatDTO> zutaten, SauceDTO sauce,
            String name, Set<Allergene> allergene, String preis) {
        this.id = id;
        this.eissorte = eissorte;
        this.eissorte2 = eissorte2;
        this.zutaten = zutaten;
        this.sauce = sauce;
        this.name = name;
        this.allergene = allergene;
        this.preis = preis;
    }

    public static class Converter{

        public static HauskreationDTO toDTO(Hauskreation hauskreation) {
            if(hauskreation == null) return null;
            List<ZutatDTO> liste = new ArrayList<>();
            for(Zutat zutat : hauskreation.getZutaten()) {
                liste.add(ZutatDTO.Converter.toDTO(zutat));
            }
            return new HauskreationDTO(hauskreation.getId(), EisDTO.Converter.toDTO(hauskreation.getEissorte()), 
             EisDTO.Converter.toDTO(hauskreation.getEissorte2()), liste, SauceDTO.Converter.toDTO(hauskreation.getSauce()), 
             hauskreation.getName(), hauskreation.getAllergene(), preisBerechnen(hauskreation));
        }

        public static Hauskreation toHauskreation(HauskreationDTO hauskreationDTO) {
            if(hauskreationDTO == null) return null;
            List<Zutat> liste = new ArrayList<>();
            for(ZutatDTO zutatDTO : hauskreationDTO.zutaten) {
                liste.add(ZutatDTO.Converter.toZutat(zutatDTO));
            }
            Hauskreation hauskreation = new Hauskreation(hauskreationDTO.id, EisDTO.Converter.toEis(hauskreationDTO.eissorte),
             EisDTO.Converter.toEis(hauskreationDTO.eissorte2), liste, SauceDTO.Converter.toSauce(hauskreationDTO.sauce),
             hauskreationDTO.name);
             hauskreation.setAllergene(hauskreationDTO.allergene);
            return hauskreation;
        }

        private static String preisBerechnen(Hauskreation hauskreation){
            BigDecimal preis = new BigDecimal("3.60");
            BigDecimal zutatNormal = new BigDecimal("0.60");
            BigDecimal zutatPremium = new BigDecimal("1.00");
            BigDecimal sauce = new BigDecimal("0.50");
            for(Zutat zutat : hauskreation.getZutaten()){
                if(zutat.isPremium()) preis = preis.add(zutatPremium);
                else preis = preis.add(zutatNormal);
            }
            if(hauskreation.getSauce() != null) preis = preis.add(sauce);
            return preis.toString();
        }
    }
}
