package de.hsos.swa.coldstoneicecreator.bestellung.entity;

import javax.enterprise.inject.Vetoed;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import de.hsos.swa.coldstoneicecreator.kreationen.entity.Eigenkreation;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

@Entity
@Vetoed
public class BestellpostenEigen extends PanacheEntityBase{
    @Id @GeneratedValue(generator = "bestellposten_seq")
    private Long id;
    @OneToOne
    private Eigenkreation kreation;
    private int anzahl = 0;
    
    public BestellpostenEigen() {
    }

    public BestellpostenEigen(Eigenkreation kreation){
        this.kreation = kreation;
        this.anzahl++;
    }

    public BestellpostenEigen(Long id, Eigenkreation kreation, int anzahl) {
        this.id = id;
        this.kreation = kreation;
        this.anzahl = anzahl;
    }

    public Long getId() {
        return id;
    }

    public Eigenkreation getEigenkreation() {
        return kreation;
    }

    public void setEigenkreation(Eigenkreation kreation) {
        this.kreation = kreation;
    }

    public int getAnzahl() {
        return anzahl;
    }

    public void setAnzahl(int anzahl) {
        this.anzahl = anzahl;
    }

    public void addEigenkreation(){
        this.anzahl++;
    }
}
