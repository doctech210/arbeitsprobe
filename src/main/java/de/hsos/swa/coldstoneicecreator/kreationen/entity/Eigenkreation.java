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
public class Eigenkreation extends PanacheEntityBase implements Kreation{
    
    @Id @GeneratedValue(generator = "eigenkreation_seq")
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
    
    public Eigenkreation() {
    }    

    public Eigenkreation(Long id, Eis eissorte, Eis eissorte2, List<Zutat> zutaten, Sauce sauce, String name) {
        this.id = id;
        this.eissorte = eissorte;
        this.eissorte2 = eissorte2;
        this.zutaten = zutaten;
        this.sauce = sauce;
        this.name = name;
        this.checkAllergene();
    }

    @Override
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

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public Eis getEissorte() {
        return eissorte;
    }

    @Override
    public void setEissorte(Eis eissorte) {
        this.eissorte = eissorte;
    }

    @Override
    public Eis getEissorte2() {
        return eissorte2;
    }

    @Override
    public void setEissorte2(Eis eissorte2) {
        this.eissorte2 = eissorte2;
    }

    @Override
    public List<Zutat> getZutaten() {
        return zutaten;
    }

    @Override
    public void setZutaten(List<Zutat> zutaten) {
        this.zutaten = zutaten;
    }

    public void addZutat(Zutat zutat) {
        if(zutat != null){
            this.zutaten.add(zutat);
            this.checkAllergene();
        }
    }

    @Override
    public Sauce getSauce() {
        return sauce;
    }

    @Override
    public void setSauce(Sauce sauce) {
        this.sauce = sauce;
    }
    
    @Override
    public Set<Allergene> getAllergene() {
        return allergene;
    }

    @Override
    public void setAllergene(Set<Allergene> allergene) {
        this.allergene = allergene;
    }
}

