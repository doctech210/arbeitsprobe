//@author Marvin Gels 868603

package de.hsos.swa.coldstoneicecreator.produkt.entity;

import java.util.Set;

import javax.validation.constraints.Pattern;

import javax.enterprise.inject.Vetoed;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

@Entity @Vetoed
public class Sauce extends PanacheEntityBase {
    
    @Id @GeneratedValue(generator = "sauce_seq") @SequenceGenerator(name = "sauce_seq", initialValue = 15)
    private Long id;

    @Pattern(regexp = "^[a-zA-Z()\\s]*$",
             message = "Use only letters for the name!")
    private String name;
    
    @ElementCollection(targetClass = Allergene.class)
    @Enumerated(EnumType.STRING)
    private Set<Allergene> allergene;
    
    public Sauce() {
    }
    
    public Sauce(Long id, String name, Set<Allergene> allergene) {
        this.id = id;
        this.name = name;
        this.allergene = allergene;
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
    public Set<Allergene> getAllergene() {
        return allergene;
    }
    public void setAllergene(Set<Allergene> allergene) {
        this.allergene = allergene;
    }
    
}
