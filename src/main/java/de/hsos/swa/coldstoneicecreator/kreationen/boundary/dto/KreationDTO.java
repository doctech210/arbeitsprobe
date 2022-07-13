package de.hsos.swa.coldstoneicecreator.kreationen.boundary.dto;

import java.util.Set;

import de.hsos.swa.coldstoneicecreator.produkt.entity.Allergene;


public interface KreationDTO {
    
    void addAllergene(Set<Allergene> allergene);
}
