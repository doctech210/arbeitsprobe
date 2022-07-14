package de.hsos.swa.coldstoneicecreator.kreationen.boundary.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.annotation.security.RolesAllowed;

import de.hsos.swa.coldstoneicecreator.produkt.entity.Allergene;
import de.hsos.swa.coldstoneicecreator.kreationen.entity.Eigenkreation;
import de.hsos.swa.coldstoneicecreator.produkt.boundary.dto.EisDTO;
import de.hsos.swa.coldstoneicecreator.produkt.boundary.dto.SauceDTO;
import de.hsos.swa.coldstoneicecreator.produkt.boundary.dto.ZutatDTO;
import de.hsos.swa.coldstoneicecreator.produkt.entity.Zutat;

@RolesAllowed({"user", "admin"})
public class EigenkreationDTO implements KreationDTO{
    
    public Long id;
    public EisDTO eissorte;
    public EisDTO eissorte2;
    public List<ZutatDTO> zutaten;
    public SauceDTO sauce;
    public String name;
    public Set<Allergene> allergene;
    
    public EigenkreationDTO() {
    }

    public EigenkreationDTO(Long id, EisDTO eissorte, EisDTO eissorte2, List<ZutatDTO> zutaten, SauceDTO sauce,
            String name) {
        this.id = id;
        this.eissorte = eissorte;
        this.eissorte2 = eissorte2;
        this.zutaten = zutaten;
        this.sauce = sauce;
        this.name = name;
        this.addAllergene(eissorte.allergene);
        this.addAllergene(eissorte2.allergene);
        for(ZutatDTO zutatDTO : zutaten) {
            this.addAllergene(zutatDTO.allergene);
        }
        this.addAllergene(sauce.allergene);
    }

    @Override
    public void addAllergene(Set<Allergene> allergene) {
        allergene.addAll(allergene);
    }

    public static class Converter{

        public static EigenkreationDTO toDTO(Eigenkreation eigenkreation) {
            List<ZutatDTO> liste = new ArrayList<>();
            for(Zutat zutat : eigenkreation.getZutaten()) {
                liste.add(ZutatDTO.Converter.toDTO(zutat));
            }
            return new EigenkreationDTO(eigenkreation.getId(), EisDTO.Converter.toDTO(eigenkreation.getEissorte()), 
             EisDTO.Converter.toDTO(eigenkreation.getEissorte2()), liste, SauceDTO.Converter.toDTO(eigenkreation.getSauce()), 
             eigenkreation.getName());
        }

        public static Eigenkreation toEigenkreation(EigenkreationDTO eigenkreationDTO) {
            List<Zutat> liste = new ArrayList<>();
            for(ZutatDTO zutatDTO : eigenkreationDTO.zutaten) {
                liste.add(ZutatDTO.Converter.toZutat(zutatDTO));
            }
            return new Eigenkreation(eigenkreationDTO.id, EisDTO.Converter.toEis(eigenkreationDTO.eissorte),
             EisDTO.Converter.toEis(eigenkreationDTO.eissorte2), liste, SauceDTO.Converter.toSauce(eigenkreationDTO.sauce),
             eigenkreationDTO.name, eigenkreationDTO.allergene);
        }
    }
}
