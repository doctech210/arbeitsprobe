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
    @JoinColumn(name="eigenposten_id")
    private List<BestellpostenEigen> bestellpostenEigen = new ArrayList<>();
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name="hausposten_id")
    private List<BestellpostenHaus> bestellpostenHaus = new ArrayList<>();
    private boolean bestellt = false;
    
    public Bestellung() {
    }

    public Bestellung(Long id, List<BestellpostenEigen> bestellpostenEigen, List<BestellpostenHaus> bestellpostenHaus ,boolean bestellt) {
        this.id = id;
        this.bestellpostenEigen = bestellpostenEigen;
        this.bestellpostenHaus = bestellpostenHaus;
        this.bestellt = bestellt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id){
        this.id = id;
    }

    public List<BestellpostenEigen> getBestellposten() {
        return bestellpostenEigen;
    }

    public void setBestellposten(List<BestellpostenEigen> bestellposten) {
        this.bestellpostenEigen = bestellposten;
    }

    public List<BestellpostenHaus> getBestellpostenHaus() {
        return bestellpostenHaus;
    }

    public void setBestellpostenHaus(List<BestellpostenHaus> bestellpostenHaus) {
        this.bestellpostenHaus = bestellpostenHaus;
    }

    public boolean isBestellt() {
        return bestellt;
    }

    public void setBestellt(boolean bestellt) {
        this.bestellt = bestellt;
    }

    public void addPostenEigen(BestellpostenEigen bestellpostenEigen){
        if(bestellpostenEigen == null) return;
        this.bestellpostenEigen.add(bestellpostenEigen);
    }

    public void removePostenEigen(Long postenId){
        for(int i = 0; i < bestellpostenEigen.size(); i++){
            if(Long.compare(postenId, bestellpostenEigen.get(i).getId()) == 0){
                bestellpostenEigen.remove(i);
            }
        }
    }

    public void addPostenHaus(BestellpostenHaus bestellpostenHaus){
        if(bestellpostenHaus == null) return;
        this.bestellpostenHaus.add(bestellpostenHaus);
    }

    public void removePostenHaus(Long postenId){
        for(int i = 0; i < bestellpostenHaus.size(); i++){
            if(Long.compare(postenId, bestellpostenHaus.get(i).getId()) == 0){
                bestellpostenHaus.remove(i);
            }
        }
    }

}
