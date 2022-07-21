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
    private Long anzahl;
    
    public BestellpostenHaus() {
    }

    public BestellpostenHaus(Hauskreation kreation, Long anzahl){
        this.kreation = kreation;
        this.anzahl = anzahl;
    }

    public BestellpostenHaus(Long id, Hauskreation kreation, Long anzahl) {
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

    public Long getAnzahl() {
        return anzahl;
    }

    public void setAnzahl(Long anzahl) {
        if(anzahl == null){
            this.anzahl = Long.valueOf(0);
        }else{
            this.anzahl = anzahl;
        }
    }

    public void addHauskreation(){
        this.anzahl++;
    }
}
