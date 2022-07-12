package de.hsos.swa.coldstoneicecreator.produkt.entity;

import javax.enterprise.inject.Vetoed;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

@Entity @Vetoed
public class Eis extends PanacheEntityBase {
    
    @Id @GeneratedValue(generator = "eis_seq")
    private Long id;

    private String name;
    private boolean laktose;
    private boolean ei;
    private boolean nuss;
    
    public Eis() {
    }
    
    public Eis(Long id, String name, boolean laktose, boolean ei, boolean nuss) {
        this.id = id;
        this.name = name;
        this.laktose = laktose;
        this.ei = ei;
        this.nuss = nuss;
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
    public boolean isLaktose() {
        return laktose;
    }
    public void setLaktose(boolean laktose) {
        this.laktose = laktose;
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

    
}
