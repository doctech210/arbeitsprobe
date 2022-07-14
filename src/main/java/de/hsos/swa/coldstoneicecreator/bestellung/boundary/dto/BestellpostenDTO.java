package de.hsos.swa.coldstoneicecreator.bestellung.boundary.dto;

import de.hsos.swa.coldstoneicecreator.kreationen.boundary.dto.*;
import de.hsos.swa.coldstoneicecreator.kreationen.entity.*;
import de.hsos.swa.coldstoneicecreator.bestellung.entity.Bestellposten;

public class BestellpostenDTO {
    public Long id;
    public KreationDTO kreationDTO;
    public int anzahl = 0;
    
    public BestellpostenDTO() {
    }

    public BestellpostenDTO(Long id, KreationDTO kreationDTO, int anzahl) {
        this.id = id;
        this.kreationDTO = kreationDTO;
        this.anzahl = anzahl;
    }

    public static class Converter {

        public static BestellpostenDTO toDTO(Bestellposten bestellposten) {
            KreationDTO kreation;
            if(bestellposten.getKreation().getClass() == Eigenkreation.class) {
                kreation = EigenkreationDTO.Converter.toDTO((Eigenkreation)bestellposten.getKreation());
            }else{
                kreation = HauskreationDTO.Converter.toDTO((Hauskreation)bestellposten.getKreation());
            }
            return new BestellpostenDTO(bestellposten.getId(), kreation, bestellposten.getAnzahl());
        }

        public static Bestellposten toBestellposten(BestellpostenDTO bestellpostenDTO) {
            Kreation kreation = null;
            if(bestellpostenDTO.kreationDTO.getClass() == EigenkreationDTO.class) {
                kreation = EigenkreationDTO.Converter.toEigenkreation((EigenkreationDTO)bestellpostenDTO.kreationDTO);
            }else{
                kreation = HauskreationDTO.Converter.toHauskreation((HauskreationDTO)bestellpostenDTO.kreationDTO);
            }
            return new Bestellposten(bestellpostenDTO.id, kreation, bestellpostenDTO.anzahl);
        }
    }
}