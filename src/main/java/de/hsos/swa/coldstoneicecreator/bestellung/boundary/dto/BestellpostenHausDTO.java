package de.hsos.swa.coldstoneicecreator.bestellung.boundary.dto;

import de.hsos.swa.coldstoneicecreator.kreationen.boundary.dto.*;
import de.hsos.swa.coldstoneicecreator.kreationen.entity.*;

import java.math.BigDecimal;

import de.hsos.swa.coldstoneicecreator.bestellung.entity.BestellpostenHaus;

public class BestellpostenHausDTO {
    public Long id;
    public HauskreationDTO hauskreationDTO;
    public Long anzahl;
    public String preis;
    
    public BestellpostenHausDTO() {
    }

    public BestellpostenHausDTO(Long id, HauskreationDTO hauskreationDTO, Long anzahl, String preis) {
        this.id = id;
        this.hauskreationDTO = hauskreationDTO;
        this.anzahl = anzahl;
        this.preis = preis;
    }

    public static class Converter {

        public static BestellpostenHausDTO toDTO(BestellpostenHaus bestellpostenHaus) {
            if(bestellpostenHaus == null) return null;
            HauskreationDTO hauskreationDTO = HauskreationDTO.Converter.toDTO(bestellpostenHaus.getHauskreation());
            return new BestellpostenHausDTO(bestellpostenHaus.getId(), hauskreationDTO, bestellpostenHaus.getAnzahl(), preisBerechnen(hauskreationDTO, bestellpostenHaus.getAnzahl()));
        }

        public static BestellpostenHaus toBestellposten(BestellpostenHausDTO bestellpostenHausDTO) {
            if(bestellpostenHausDTO == null) return null;
            Hauskreation hauskreation = HauskreationDTO.Converter.toHauskreation(bestellpostenHausDTO.hauskreationDTO);
            return new BestellpostenHaus(bestellpostenHausDTO.id, hauskreation, bestellpostenHausDTO.anzahl);
        }

        private static String preisBerechnen(HauskreationDTO hauskreationDTO, Long _anzahl){
            BigDecimal preis = new BigDecimal(hauskreationDTO.preis);
            BigDecimal anzahl = new BigDecimal(_anzahl.toString());
            preis = preis.multiply(anzahl);         
            return preis.toString();
        }
    }
}