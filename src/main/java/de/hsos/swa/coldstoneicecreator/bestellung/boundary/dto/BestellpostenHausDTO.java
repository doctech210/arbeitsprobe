package de.hsos.swa.coldstoneicecreator.bestellung.boundary.dto;

import de.hsos.swa.coldstoneicecreator.kreationen.boundary.dto.*;
import de.hsos.swa.coldstoneicecreator.kreationen.entity.*;
import de.hsos.swa.coldstoneicecreator.bestellung.entity.BestellpostenHaus;

public class BestellpostenHausDTO {
    public Long id;
    public HauskreationDTO hauskreationDTO;
    public Long anzahl;
    
    public BestellpostenHausDTO() {
    }

    public BestellpostenHausDTO(Long id, HauskreationDTO hauskreationDTO, Long anzahl) {
        this.id = id;
        this.hauskreationDTO = hauskreationDTO;
        this.anzahl = anzahl;
    }

    public static class Converter {

        public static BestellpostenHausDTO toDTO(BestellpostenHaus bestellpostenHaus) {
            if(bestellpostenHaus == null) return null;
            HauskreationDTO hauskreation = HauskreationDTO.Converter.toDTO(bestellpostenHaus.getHauskreation());
            return new BestellpostenHausDTO(bestellpostenHaus.getId(), hauskreation, bestellpostenHaus.getAnzahl());
        }

        public static BestellpostenHaus toBestellposten(BestellpostenHausDTO bestellpostenHausDTO) {
            if(bestellpostenHausDTO == null) return null;
            Hauskreation hauskreation = HauskreationDTO.Converter.toHauskreation(bestellpostenHausDTO.hauskreationDTO);
            return new BestellpostenHaus(bestellpostenHausDTO.id, hauskreation, bestellpostenHausDTO.anzahl);
        }
    }
}