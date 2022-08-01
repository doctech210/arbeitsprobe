//@author Marvin Gels 868603

package de.hsos.swa.coldstoneicecreator.bestellung.boundary.dto;

import java.math.BigDecimal;

import de.hsos.swa.coldstoneicecreator.kreationen.boundary.dto.*;
import de.hsos.swa.coldstoneicecreator.kreationen.entity.*;
import de.hsos.swa.coldstoneicecreator.bestellung.entity.BestellpostenEigen;

public class BestellpostenEigenDTO {
    public Long id;
    public EigenkreationDTO eigenkreationDTO;
    public Long anzahl;
    public String preis;
    
    public BestellpostenEigenDTO() {
    }

    public BestellpostenEigenDTO(Long id, EigenkreationDTO eigenkreationDTO, Long anzahl, String preis) {
        this.id = id;
        this.eigenkreationDTO = eigenkreationDTO;
        this.anzahl = anzahl;
        this.preis = preis;
    }

    public static class Converter {

        public static BestellpostenEigenDTO toDTO(BestellpostenEigen bestellpostenEigen) {
            if(bestellpostenEigen == null) return null;
            EigenkreationDTO eigenkreationDTO= EigenkreationDTO.Converter.toDTO((Eigenkreation)bestellpostenEigen.getEigenkreation());      
            return new BestellpostenEigenDTO(bestellpostenEigen.getId(), eigenkreationDTO, bestellpostenEigen.getAnzahl(), preisBerechnen(eigenkreationDTO, bestellpostenEigen.getAnzahl()));
        }

        public static BestellpostenEigen toBestellposten(BestellpostenEigenDTO bestellpostenEigenDTO) {
            if(bestellpostenEigenDTO == null) return null;
            Eigenkreation eigenkreation = EigenkreationDTO.Converter.toEigenkreation(bestellpostenEigenDTO.eigenkreationDTO);
            return new BestellpostenEigen(bestellpostenEigenDTO.id, eigenkreation, bestellpostenEigenDTO.anzahl);
        }

        private static String preisBerechnen(EigenkreationDTO eigenkreationDTO, Long _anzahl){
            BigDecimal preis = new BigDecimal(eigenkreationDTO.preis);
            BigDecimal anzahl = new BigDecimal(_anzahl.toString());
            preis = preis.multiply(anzahl);         
            return preis.toString();
        }
    }
}