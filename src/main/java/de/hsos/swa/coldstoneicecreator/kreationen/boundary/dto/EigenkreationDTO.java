//@author Marvin Gels 868603

package de.hsos.swa.coldstoneicecreator.kreationen.boundary.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.validation.constraints.Pattern;

import de.hsos.swa.coldstoneicecreator.produkt.entity.Allergene;
import de.hsos.swa.coldstoneicecreator.kreationen.entity.Eigenkreation;
import de.hsos.swa.coldstoneicecreator.produkt.boundary.dto.EisDTO;
import de.hsos.swa.coldstoneicecreator.produkt.boundary.dto.SauceDTO;
import de.hsos.swa.coldstoneicecreator.produkt.boundary.dto.ZutatDTO;
import de.hsos.swa.coldstoneicecreator.produkt.entity.Zutat;

public class EigenkreationDTO{
    
    public Long id;
    public EisDTO eissorte;
    public EisDTO eissorte2;
    public List<ZutatDTO> zutaten;
    public SauceDTO sauce;
    @Pattern(regexp = "^[a-zA-Z\\s]*$",
             message = "Use only letters for the name!")
    public String name;
    public boolean bestellbar;
    public Set<Allergene> allergene;
    public String preis;
    
    public EigenkreationDTO() {
    }

    public EigenkreationDTO(Long id, EisDTO eissorte, EisDTO eissorte2, List<ZutatDTO> zutaten, SauceDTO sauce,
            String name, boolean bestellbar, Set<Allergene> allergene, String preis) {
        this.id = id;
        this.eissorte = eissorte;
        this.eissorte2 = eissorte2;
        this.zutaten = zutaten;
        this.sauce = sauce;
        this.name = name;
        this.bestellbar = bestellbar;
        this.allergene = allergene;
        this.preis = preis;
    }

    public static class Converter{

        public static EigenkreationDTO toDTO(Eigenkreation eigenkreation) {
            if(eigenkreation == null) return null;
            List<ZutatDTO> liste = new ArrayList<>();
            for(Zutat zutat : eigenkreation.getZutaten()) {
                liste.add(ZutatDTO.Converter.toDTO(zutat));
            }
            return new EigenkreationDTO(eigenkreation.getId(), EisDTO.Converter.toDTO(eigenkreation.getEissorte()), 
             EisDTO.Converter.toDTO(eigenkreation.getEissorte2()), liste, SauceDTO.Converter.toDTO(eigenkreation.getSauce()), 
             eigenkreation.getName(), eigenkreation.isBestellbar(), eigenkreation.getAllergene(), preisBerechnen(eigenkreation));
        }

        public static Eigenkreation toEigenkreation(EigenkreationDTO eigenkreationDTO) {
            if(eigenkreationDTO == null) return null;
            List<Zutat> liste = new ArrayList<>();
            for(ZutatDTO zutatDTO : eigenkreationDTO.zutaten) {
                liste.add(ZutatDTO.Converter.toZutat(zutatDTO));
            }
            Eigenkreation eigenkreation =  new Eigenkreation(eigenkreationDTO.id, EisDTO.Converter.toEis(eigenkreationDTO.eissorte),
            EisDTO.Converter.toEis(eigenkreationDTO.eissorte2), liste, SauceDTO.Converter.toSauce(eigenkreationDTO.sauce),
            eigenkreationDTO.name);
            eigenkreation.setAllergene(eigenkreationDTO.allergene);
            return eigenkreation;
        }

        private static String preisBerechnen(Eigenkreation eigenkreation){
            BigDecimal preis = new BigDecimal("3.60");
            BigDecimal zutatNormal = new BigDecimal("0.60");
            BigDecimal zutatPremium = new BigDecimal("1.00");
            BigDecimal sauce = new BigDecimal("0.50");
            for(Zutat zutat : eigenkreation.getZutaten()){
                if(zutat.isPremium()) preis = preis.add(zutatPremium);
                else preis = preis.add(zutatNormal);
            }
            if(eigenkreation.getSauce() != null) preis = preis.add(sauce);
            return preis.toString();
        }
    }
}
