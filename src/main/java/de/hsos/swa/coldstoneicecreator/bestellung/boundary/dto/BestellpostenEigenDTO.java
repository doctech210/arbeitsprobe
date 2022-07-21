package de.hsos.swa.coldstoneicecreator.bestellung.boundary.dto;

import de.hsos.swa.coldstoneicecreator.kreationen.boundary.dto.*;
import de.hsos.swa.coldstoneicecreator.kreationen.entity.*;
import de.hsos.swa.coldstoneicecreator.bestellung.entity.BestellpostenEigen;

public class BestellpostenEigenDTO {
    public Long id;
    public EigenkreationDTO eigenkreationDTO;
    public Long anzahl;
    
    public BestellpostenEigenDTO() {
    }

    public BestellpostenEigenDTO(Long id, EigenkreationDTO eigenkreationDTO, Long anzahl) {
        this.id = id;
        this.eigenkreationDTO = eigenkreationDTO;
        this.anzahl = anzahl;
    }

    public static class Converter {

        public static BestellpostenEigenDTO toDTO(BestellpostenEigen bestellpostenEigen) {
            if(bestellpostenEigen == null) return null;
            EigenkreationDTO eigenkreationDTO= EigenkreationDTO.Converter.toDTO((Eigenkreation)bestellpostenEigen.getEigenkreation());      
            return new BestellpostenEigenDTO(bestellpostenEigen.getId(), eigenkreationDTO, bestellpostenEigen.getAnzahl());
        }

        public static BestellpostenEigen toBestellposten(BestellpostenEigenDTO bestellpostenEigenDTO) {
            if(bestellpostenEigenDTO == null) return null;
            Eigenkreation eigenkreation = EigenkreationDTO.Converter.toEigenkreation(bestellpostenEigenDTO.eigenkreationDTO);
            return new BestellpostenEigen(bestellpostenEigenDTO.id, eigenkreation, bestellpostenEigenDTO.anzahl);
        }
    }
}