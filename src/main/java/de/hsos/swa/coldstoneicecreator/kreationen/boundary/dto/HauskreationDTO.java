package de.hsos.swa.coldstoneicecreator.kreationen.boundary.dto;

import java.util.ArrayList;
import java.util.List;

import de.hsos.swa.coldstoneicecreator.kreationen.entity.Hauskreation;
import de.hsos.swa.coldstoneicecreator.produkt.boundary.dto.EisDTO;
import de.hsos.swa.coldstoneicecreator.produkt.boundary.dto.SauceDTO;
import de.hsos.swa.coldstoneicecreator.produkt.boundary.dto.ZutatDTO;
import de.hsos.swa.coldstoneicecreator.produkt.entity.Zutat;

public class HauskreationDTO implements KreationDTO{
    
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
    
    public HauskreationDTO() {
    }

    public HauskreationDTO(Long id, EisDTO eissorte, EisDTO eissorte2, List<ZutatDTO> zutaten, SauceDTO sauce,
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

        public static HauskreationDTO toDTO(Hauskreation hauskreation) {
            List<ZutatDTO> liste = new ArrayList<>();
            for(Zutat zutat : hauskreation.getZutaten()) {
                liste.add(ZutatDTO.Converter.toDTO(zutat));
            }
            return new HauskreationDTO(hauskreation.getId(), EisDTO.Converter.toDTO(hauskreation.getEissorte()), 
             EisDTO.Converter.toDTO(hauskreation.getEissorte2()), liste, SauceDTO.Converter.toDTO(hauskreation.getSauce()), 
             hauskreation.getName(), hauskreation.isPremium(),hauskreation.isAlkohol(), hauskreation.isEi(), 
             hauskreation.isGelantine(), hauskreation.isGluten(), hauskreation.isLaktose(), hauskreation.isNuss(), 
             hauskreation.isSuessstoff());
        }

        public static Hauskreation toHauskreation(HauskreationDTO hauskreationDTO) {
            List<Zutat> liste = new ArrayList<>();
            for(ZutatDTO zutatDTO : hauskreationDTO.zutaten) {
                liste.add(ZutatDTO.Converter.toZutat(zutatDTO));
            }
            return new Hauskreation(hauskreationDTO.id, EisDTO.Converter.toEis(hauskreationDTO.eissorte),
             EisDTO.Converter.toEis(hauskreationDTO.eissorte2), liste, SauceDTO.Converter.toSauce(hauskreationDTO.sauce),
             hauskreationDTO.name, hauskreationDTO.premium, hauskreationDTO.alkohol, hauskreationDTO.ei, 
             hauskreationDTO.gelantine, hauskreationDTO.gluten, hauskreationDTO.laktose, hauskreationDTO.nuss, 
             hauskreationDTO.suessstoff);
        }
    }
}
