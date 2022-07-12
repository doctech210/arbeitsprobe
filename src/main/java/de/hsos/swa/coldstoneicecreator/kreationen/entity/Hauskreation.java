package de.hsos.swa.coldstoneicecreator.kreationen.entity;

import java.util.List;

import javax.enterprise.inject.Vetoed;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import de.hsos.swa.coldstoneicecreator.produkt.entity.Eis;
import de.hsos.swa.coldstoneicecreator.produkt.entity.Sauce;
import de.hsos.swa.coldstoneicecreator.produkt.entity.Zutat;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

@Entity @Vetoed
public class Hauskreation extends PanacheEntityBase {
    
    @Id @GeneratedValue(generator = "hauskreation_seq")
    private Long id;
    @OneToOne
    private Eis eissorte;
    @OneToOne
    private Eis eissorte2;
    @OneToMany
    private List<Zutat> zutaten;
    @OneToOne
    private Sauce sauce;
    private String name;
    private boolean premium;
    private boolean alkohol;
    private boolean ei;
    private boolean gelantine;
    private boolean gluten;
    private boolean laktose;
    private boolean nuss;
    private boolean suessstoff;
    
    public Hauskreation() {
    }    

    public Hauskreation(Long id, Eis eissorte, Eis eissorte2, List<Zutat> zutaten, Sauce sauce, String name,
            boolean premium, boolean alkohol, boolean ei, boolean gelantine, boolean gluten, boolean laktose,
            boolean nuss, boolean suessstoff) {
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

    public boolean isGluten() {
        return gluten;
    }

    public void setGluten(boolean gluten) {
        this.gluten = gluten;
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

    public Eis getEissorte() {
        return eissorte;
    }

    public void setEissorte(Eis eissorte) {
        this.eissorte = eissorte;
    }

    public Eis getEissorte2() {
        return eissorte2;
    }

    public void setEissorte2(Eis eissorte2) {
        this.eissorte2 = eissorte2;
    }

    public List<Zutat> getZutaten() {
        return zutaten;
    }

    public void setZutaten(List<Zutat> zutaten) {
        this.zutaten = zutaten;
    }

    public Sauce getSauce() {
        return sauce;
    }

    public void setSauce(Sauce sauce) {
        this.sauce = sauce;
    }
    
        
}

