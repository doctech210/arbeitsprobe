package de.hsos.swa.coldstoneicecreator.kreationen.entity;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.enterprise.inject.Vetoed;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import de.hsos.swa.coldstoneicecreator.produkt.entity.Allergene;
import de.hsos.swa.coldstoneicecreator.produkt.entity.Eis;
import de.hsos.swa.coldstoneicecreator.produkt.entity.Sauce;
import de.hsos.swa.coldstoneicecreator.produkt.entity.Zutat;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

@Entity @Vetoed
public class Hauskreation extends PanacheEntityBase implements Kreation{
    
    @Id @GeneratedValue(generator = "hauskreation_seq")
    private Long id;
    @ManyToOne
    private Eis eissorte;
    @ManyToOne
    private Eis eissorte2;
    @ManyToMany
    private List<Zutat> zutaten;
    @ManyToOne
    private Sauce sauce;
    private String name;
    
    @ElementCollection(targetClass = Allergene.class)
    @Enumerated(EnumType.STRING)
    private Set<Allergene> allergene;

    public Hauskreation() {
    }    

    public Hauskreation(Long id, Eis eissorte, Eis eissorte2, List<Zutat> zutaten, Sauce sauce, String name) {
        this.id = id;
        this.eissorte = eissorte;
        this.eissorte2 = eissorte2;
        this.zutaten = zutaten;
        this.sauce = sauce;
        this.name = name;
        this.checkAllergene();
    }

    public void checkAllergene() {
        Set<Allergene> neueAllergene = new HashSet<>();
        neueAllergene.addAll(eissorte.getAllergene());
        neueAllergene.addAll(eissorte2.getAllergene());
        for(Zutat zutat : zutaten) {
            neueAllergene.addAll(zutat.getAllergene());
        }
        neueAllergene.addAll(sauce.getAllergene());
        this.allergene = neueAllergene;
    }

    public void addAllergen(Set<Allergene> allergene){
        allergene.addAll(allergene);
    }

    public Set<Allergene> getAllergene() {
        return allergene;
    }
    public void setAllergene(Set<Allergene> allergene) {
        this.allergene = allergene;
    }

    @Override
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

    public void addZutat(Zutat zutat) {
        this.zutaten.add(zutat);
        this.checkAllergene();
    }

    public Sauce getSauce() {
        return sauce;
    }

    public void setSauce(Sauce sauce) {
        this.sauce = sauce;
    }
}

