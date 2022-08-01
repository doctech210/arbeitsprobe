//@author Marvin Gels 868603

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
    private Long anzahl;
    
    public BestellpostenEigen() {
    }

    public BestellpostenEigen(Eigenkreation kreation, Long anzahl){
        this.kreation = kreation;
        this.anzahl = anzahl;
    }

    public BestellpostenEigen(Long id, Eigenkreation kreation, Long anzahl) {
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

    public void addEigenkreation(){
        this.anzahl++;
    }
}
