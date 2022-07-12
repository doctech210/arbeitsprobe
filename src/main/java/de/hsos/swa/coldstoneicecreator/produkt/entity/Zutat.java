package de.hsos.swa.coldstoneicecreator.produkt.entity;

import javax.enterprise.inject.Vetoed;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

@Entity @Vetoed
public class Zutat extends PanacheEntityBase {
    
    @Id @GeneratedValue(generator = "zutat_seq")
    private Long id;

    private String name;
    private boolean premium;
    private boolean alkohol;
    private boolean ei;
    private boolean gelantine;
    private boolean laktose;
    private boolean nuss;
    private boolean suessstoff;
    
    public Zutat() {
    }

    public Zutat(Long id, String name, boolean premium, boolean alkohol, boolean ei, boolean gelantine, boolean laktose,
            boolean nuss, boolean suessstoff) {
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isPremium() {
        return premium;
    }

    public void setPremium(boolean premium) {
        this.premium = premium;
    }

    public boolean isAlkohol() {
        return alkohol;
    }

    public void setAlkohol(boolean alkohol) {
        this.alkohol = alkohol;
    }

    public boolean isEi() {
        return ei;
    }

    public void setEi(boolean ei) {
        this.ei = ei;
    }

    public boolean isGelantine() {
        return gelantine;
    }

    public void setGelantine(boolean gelantine) {
        this.gelantine = gelantine;
    }

    public boolean isLaktose() {
        return laktose;
    }

    public void setLaktose(boolean laktose) {
        this.laktose = laktose;
    }

    public boolean isNuss() {
        return nuss;
    }

    public void setNuss(boolean nuss) {
        this.nuss = nuss;
    }

    public boolean isSuessstoff() {
        return suessstoff;
    }

    public void setSuessstoff(boolean suessstoff) {
        this.suessstoff = suessstoff;
    }
    
    
}
