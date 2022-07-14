package de.hsos.swa.coldstoneicecreator.bestellung.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

@Entity
public class Bestellung extends PanacheEntityBase{
    @Id @GeneratedValue(generator = "bestellung_seq")
    private Long id;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name="posten_id")
    private List<Bestellposten> bestellposten = new ArrayList<>();
    private boolean bestellt = false;
    
    public Bestellung() {
    }

    public Bestellung(Long id, List<Bestellposten> bestellposten, boolean bestellt) {
        this.id = id;
        this.bestellposten = bestellposten;
        this.bestellt = bestellt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id){
        this.id = id;
    }

    public List<Bestellposten> getBestellposten() {
        return bestellposten;
    }

    public void setBestellposten(List<Bestellposten> bestellposten) {
        this.bestellposten = bestellposten;
    }

    public boolean isBestellt() {
        return bestellt;
    }

    public void setBestellt(boolean bestellt) {
        this.bestellt = bestellt;
    }

    public void addPosten(Bestellposten bestellposten){
        if(bestellposten == null) return;
        this.bestellposten.add(bestellposten);
    }

    public void removePosten(Long postenId){
        for(int i = 0; i < bestellposten.size(); i++){
            if(Long.compare(postenId, bestellposten.get(i).getId()) == 0){
                bestellposten.remove(i);
            }
        }
    }

}
