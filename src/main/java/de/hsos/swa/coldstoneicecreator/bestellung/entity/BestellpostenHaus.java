package de.hsos.swa.coldstoneicecreator.bestellung.entity;

import javax.enterprise.inject.Vetoed;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import de.hsos.swa.coldstoneicecreator.kreationen.entity.Hauskreation;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

@Entity
@Vetoed
public class BestellpostenHaus extends PanacheEntityBase{
    @Id @GeneratedValue(generator = "bestellposten_seq")
    private Long id;
    @OneToOne
    private Hauskreation kreation;
    private int anzahl = 0;
    
    public BestellpostenHaus() {
    }

    public BestellpostenHaus(Hauskreation kreation){
        this.kreation = kreation;
        this.anzahl++;
    }

    public BestellpostenHaus(Long id, Hauskreation kreation, int anzahl) {
        this.id = id;
        this.kreation = kreation;
        this.anzahl = anzahl;
    }

    public Long getId() {
        return id;
    }

    public Hauskreation getHauskreation() {
        return kreation;
    }

    public void setHauskreation(Hauskreation kreation) {
        this.kreation = kreation;
    }

    public int getAnzahl() {
        return anzahl;
    }

    public void setAnzahl(int anzahl) {
        this.anzahl = anzahl;
    }

    public void addHauskreation(){
        this.anzahl++;
    }
}
