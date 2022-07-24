package de.hsos.swa.coldstoneicecreator.kunden.entity;

import java.util.ArrayList;
import java.util.List;

import de.hsos.swa.coldstoneicecreator.bestellung.entity.Bestellung;
import de.hsos.swa.coldstoneicecreator.kreationen.entity.Eigenkreation;

import javax.validation.constraints.Pattern;

import javax.enterprise.inject.Vetoed;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

import io.quarkus.security.jpa.Password;
import io.quarkus.security.jpa.Roles;
import io.quarkus.security.jpa.UserDefinition;
import io.quarkus.security.jpa.Username;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

@Entity
@Vetoed
@UserDefinition
public class Nutzer extends PanacheEntityBase{
    
    @Id @GeneratedValue(generator = "kunde_seq")
    @SequenceGenerator(name = "kunde_seq", initialValue = 2)
    private Long id;

    @Pattern(regexp = "^[a-zA-Z\\s]*$",
             message = "Use only letters for the name!")
    @Username
    private String name;
    @Password
    private String passwort;
    @Roles
    private String role = "Kunde";
    
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name="kundenkreation_id")
    private List<Eigenkreation> eigenkreationen;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name="bestellId_id")
    private List<Bestellung> bestellungen = new ArrayList<>();
    
    public Nutzer() {
    }
    
    public Nutzer(String name, String passwort, List<Eigenkreation> eigenkreationen) {
        this.id = null;
        this.name = name;
        this.passwort = passwort;
        this.eigenkreationen = eigenkreationen;
    }

    public boolean addEigenkreation(Eigenkreation eigenkreation) {
        return this.eigenkreationen.add(eigenkreation);
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

    public List<Eigenkreation> getEigenkreationen() {
        return eigenkreationen;
    }

    public void setEigenkreationen(List<Eigenkreation> eigenkreationen) {
        this.eigenkreationen = eigenkreationen;
    }

    public List<Bestellung> getBestellungen() {
        return bestellungen;
    }

    public void setBestellungen(List<Bestellung> bestellungen) {
        this.bestellungen = bestellungen;
    }
    
    public void addBestellung(Bestellung bestellung){
        this.bestellungen.add(bestellung);
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
