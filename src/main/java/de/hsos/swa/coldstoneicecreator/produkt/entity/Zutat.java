package de.hsos.swa.coldstoneicecreator.produkt.entity;

import java.util.Set;

import javax.enterprise.inject.Vetoed;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

@Entity @Vetoed
public class Zutat extends PanacheEntityBase {
    
    @Id @GeneratedValue(generator = "zutat_seq")
    private Long id;

    private String name;
    private boolean premium;

    @ElementCollection(targetClass = Allergene.class)
    @Enumerated(EnumType.STRING)
    private Set<Allergene> allergene;
    
    public Zutat() {
    }

    public Zutat(Long id, String name, boolean premium, Set<Allergene> allergene) {
        this.id = id;
        this.name = name;
        this.premium = premium;
        this.allergene = allergene;
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

    public Set<Allergene> getAllergene() {
        return allergene;
    }

    public void setAllergene(Set<Allergene> allergene) {
        this.allergene = allergene;
    }

}
