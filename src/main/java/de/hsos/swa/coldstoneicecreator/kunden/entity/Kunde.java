package de.hsos.swa.coldstoneicecreator.kunden.entity;

import java.util.List;

import javax.enterprise.inject.Vetoed;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import de.hsos.swa.coldstoneicecreator.kreationen.entity.Kreation;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

@Entity
@Vetoed
public class Kunde extends PanacheEntityBase{
    
    @Id @GeneratedValue(generator = "kunde_seq")
    private Long id;
    private String name;
    private String passwort;
    /*@OneToMany
    private List<Kreation> warenkorb;
    */
    public Kunde() {
    }
    
    public Kunde(Long id, String name, String passwort, List<Kreation> warenkorb) {
        this.id = id;
        this.name = name;
        this.passwort = passwort;
        //this.warenkorb = warenkorb;
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

    public String getPasswort() {
        return passwort;
    }

    public void setPasswort(String passwort) {
        this.passwort = passwort;
    }

    /*public List<Kreation> getWarenkorb() {
        return warenkorb;
    }

    public void setWarenkorb(List<Kreation> warenkorb) {
        this.warenkorb = warenkorb;
    }*/

}
