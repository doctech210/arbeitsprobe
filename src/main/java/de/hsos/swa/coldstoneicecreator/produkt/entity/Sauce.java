package de.hsos.swa.coldstoneicecreator.produkt.entity;

import javax.enterprise.inject.Vetoed;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

@Entity @Vetoed
public class Sauce extends PanacheEntityBase {
    
    @Id @GeneratedValue(generator = "sauce_seq")
    private Long id;

    private String name;
    private boolean gluten;
    private boolean ei;
    private boolean nuss;
    private boolean alkohol;
    
    public Sauce() {
    }
    
    public Sauce(Long id, String name, boolean gluten, boolean ei, boolean nuss, boolean alkohol) {
        this.id = id;
        this.name = name;
        this.gluten = gluten;
        this.ei = ei;
        this.nuss = nuss;
        this.alkohol = alkohol;
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
    public boolean isGluten() {
        return gluten;
    }
    public void setGluten(boolean laktose) {
        this.gluten = laktose;
    }
    public boolean isEi() {
        return ei;
    }
    public void setEi(boolean ei) {
        this.ei = ei;
    }
    public boolean isNuss() {
        return nuss;
    }
    public void setNuss(boolean nuss) {
        this.nuss = nuss;
    }
    public boolean isAlkohol() {
        return alkohol;
    }
    public void setAlkohol(boolean alkohol) {
        this.alkohol = alkohol;
    }
}
