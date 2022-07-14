package de.hsos.swa.coldstoneicecreator.bestellung.entity;

import javax.enterprise.inject.Vetoed;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import de.hsos.swa.coldstoneicecreator.kreationen.entity.Kreation;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

@Entity
@Vetoed
public class Bestellposten extends PanacheEntityBase{
    @Id @GeneratedValue(generator = "bestellposten_seq")
    private Long id;
    @OneToOne
    private Kreation kreation;
    private int anzahl = 0;
    
    public Bestellposten() {
    }

    public Bestellposten(Kreation kreation){
        this.kreation = kreation;
        this.anzahl++;
    }

    public Bestellposten(Long id, Kreation kreation, int anzahl) {
        this.id = id;
        this.kreation = kreation;
        this.anzahl = anzahl;
    }

    public Long getId() {
        return id;
    }

    public Kreation getKreation() {
        return kreation;
    }

    public void setKreation(Kreation kreation) {
        this.kreation = kreation;
    }

    public int getAnzahl() {
        return anzahl;
    }

    public void setAnzahl(int anzahl) {
        this.anzahl = anzahl;
    }

    public void addKreation(){
        this.anzahl++;
    }
}
