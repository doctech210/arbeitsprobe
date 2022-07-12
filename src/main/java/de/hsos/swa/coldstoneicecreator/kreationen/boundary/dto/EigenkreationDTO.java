package de.hsos.swa.coldstoneicecreator.kreationen.boundary.dto;

import java.util.ArrayList;
import java.util.List;

import de.hsos.swa.coldstoneicecreator.kreationen.entity.Eigenkreation;
import de.hsos.swa.coldstoneicecreator.produkt.boundary.dto.EisDTO;
import de.hsos.swa.coldstoneicecreator.produkt.boundary.dto.SauceDTO;
import de.hsos.swa.coldstoneicecreator.produkt.boundary.dto.ZutatDTO;
import de.hsos.swa.coldstoneicecreator.produkt.entity.Zutat;

public class EigenkreationDTO implements KreationDTO{
    
    public Long id;
    public EisDTO eissorte;
    public EisDTO eissorte2;
    public List<ZutatDTO> zutaten;
    public SauceDTO sauce;
    public String name;
    public boolean premium;
    public boolean alkohol;
    public boolean ei;
    public boolean gelantine;
    public boolean gluten;
    public boolean laktose;
    public boolean nuss;
    public boolean suessstoff;
    
    public EigenkreationDTO() {
    }

    public EigenkreationDTO(Long id, EisDTO eissorte, EisDTO eissorte2, List<ZutatDTO> zutaten, SauceDTO sauce,
            String name, boolean premium, boolean alkohol, boolean ei, boolean gelantine, boolean gluten,
            boolean laktose, boolean nuss, boolean suessstoff) {
        this.id = id;
        this.eissorte = eissorte;
        this.eissorte2 = eissorte2;
        this.zutaten = zutaten;
        this.sauce = sauce;
        this.name = name;
        this.premium = premium;
        this.alkohol = alkohol;
        this.ei = ei;
        this.gelantine = gelantine;
        this.gluten = gluten;
        this.laktose = laktose;
        this.nuss = nuss;
        this.suessstoff = suessstoff;
    }

    public static class Converter{

        public static EigenkreationDTO toDTO(Eigenkreation eigenkreation) {
            List<ZutatDTO> liste = new ArrayList<>();
            for(Zutat zutat : eigenkreation.getZutaten()) {
                liste.add(ZutatDTO.Converter.toDTO(zutat));
            }
            return new EigenkreationDTO(eigenkreation.getId(), EisDTO.Converter.toDTO(eigenkreation.getEissorte()), 
             EisDTO.Converter.toDTO(eigenkreation.getEissorte2()), liste, SauceDTO.Converter.toDTO(eigenkreation.getSauce()), 
             eigenkreation.getName(), eigenkreation.isPremium(),eigenkreation.isAlkohol(), eigenkreation.isEi(), 
             eigenkreation.isGelantine(), eigenkreation.isGluten(), eigenkreation.isLaktose(), eigenkreation.isNuss(), 
             eigenkreation.isSuessstoff());
        }

        public static Eigenkreation toEigenkreation(EigenkreationDTO eigenkreationDTO) {
            List<Zutat> liste = new ArrayList<>();
            for(ZutatDTO zutatDTO : eigenkreationDTO.zutaten) {
                liste.add(ZutatDTO.Converter.toZutat(zutatDTO));
            }
            return new Eigenkreation(eigenkreationDTO.id, EisDTO.Converter.toEis(eigenkreationDTO.eissorte),
             EisDTO.Converter.toEis(eigenkreationDTO.eissorte2), liste, SauceDTO.Converter.toSauce(eigenkreationDTO.sauce),
             eigenkreationDTO.name, eigenkreationDTO.premium, eigenkreationDTO.alkohol, eigenkreationDTO.ei, 
             eigenkreationDTO.gelantine, eigenkreationDTO.gluten, eigenkreationDTO.laktose, eigenkreationDTO.nuss, 
             eigenkreationDTO.suessstoff);
        }
    }
}
