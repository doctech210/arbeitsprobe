package de.hsos.swa.coldstoneicecreator.produkt.boundary.dto;

import de.hsos.swa.coldstoneicecreator.produkt.entity.Zutat;

public class ZutatDTO {
    public Long id;
    public String name;
    public boolean premium;
    public boolean alkohol;
    public boolean ei;
    public boolean gelantine;
    public boolean laktose;
    public boolean nuss;
    public boolean suessstoff;
    
    public ZutatDTO() {
    }

    public ZutatDTO(Long id, String name, boolean premium, boolean alkohol, boolean ei, boolean gelantine,
            boolean laktose, boolean nuss, boolean suessstoff) {
        this.id = id;
        this.name = name;
        this.premium = premium;
        this.alkohol = alkohol;
        this.ei = ei;
        this.gelantine = gelantine;
        this.laktose = laktose;
        this.nuss = nuss;
        this.suessstoff = suessstoff;
    }

    public static class Converter{

        public static ZutatDTO toDTO(Zutat zutat) {
            return new ZutatDTO(zutat.getId(), zutat.getName(), zutat.isPremium(), zutat.isAlkohol(),
             zutat.isEi(), zutat.isGelantine(), zutat.isLaktose(), zutat.isNuss(), zutat.isSuessstoff());
        }

        public static Zutat toZutat(ZutatDTO zutatDTO) {
            return new Zutat(zutatDTO.id, zutatDTO.name, zutatDTO.premium, zutatDTO.alkohol, zutatDTO.ei,
             zutatDTO.gelantine, zutatDTO.laktose, zutatDTO.nuss, zutatDTO.suessstoff);
        }
    }
}
