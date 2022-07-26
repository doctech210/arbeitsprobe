package de.hsos.swa.coldstoneicecreator.bestellung.boundary.dto;

import java.util.List;
import java.util.ArrayList;

import de.hsos.swa.coldstoneicecreator.bestellung.entity.Bestellung;
import de.hsos.swa.coldstoneicecreator.bestellung.entity.BestellpostenEigen;
import de.hsos.swa.coldstoneicecreator.bestellung.entity.BestellpostenHaus;

public class BestellungDTO {
    public Long id;
    public List<BestellpostenEigenDTO> bestellpostenEigenDTO;
    public List<BestellpostenHausDTO> bestellpostenHausDTO;
    public boolean bestellt;

    public BestellungDTO() {     
    }

    public BestellungDTO(Long id, List<BestellpostenEigenDTO> bestellpostenEigenDTO, List<BestellpostenHausDTO> bestellpostenHausDTO, boolean bestellt) {
        this.id = id;
        this.bestellpostenEigenDTO = bestellpostenEigenDTO;
        this.bestellpostenHausDTO = bestellpostenHausDTO;
        this.bestellt = bestellt;
    }

    public static class Converter {

        public static BestellungDTO toDTO(Bestellung bestellung) {
            if(bestellung == null) return null;
            List<BestellpostenEigenDTO> bestellpostenEigenDTO = new ArrayList<>();
            for(BestellpostenEigen bestellposten : bestellung.getBestellpostenEigen()) {
                bestellpostenEigenDTO.add(BestellpostenEigenDTO.Converter.toDTO(bestellposten));
            }
            List<BestellpostenHausDTO> bestellpostenHausDTO = new ArrayList<>();
            for(BestellpostenHaus bestellposten : bestellung.getBestellpostenHaus()) {
                bestellpostenHausDTO.add(BestellpostenHausDTO.Converter.toDTO(bestellposten));
            }
            return new BestellungDTO(bestellung.getId(), bestellpostenEigenDTO, bestellpostenHausDTO, bestellung.isBestellt());
        }

        public static Bestellung toBestellung(BestellungDTO bestellungDTO) {
            if(bestellungDTO == null) return null;
            List<BestellpostenEigen> bestellpostenEigen = new ArrayList<>();
            for(BestellpostenEigenDTO bestellpostenDTO : bestellungDTO.bestellpostenEigenDTO) {
                bestellpostenEigen.add(BestellpostenEigenDTO.Converter.toBestellposten(bestellpostenDTO));
            }
            List<BestellpostenHaus> bestellpostenHaus = new ArrayList<>();
            for(BestellpostenHausDTO bestellpostenDTO : bestellungDTO.bestellpostenHausDTO) {
                bestellpostenHaus.add(BestellpostenHausDTO.Converter.toBestellposten(bestellpostenDTO));
            }
            return new Bestellung(bestellungDTO.id, bestellpostenEigen, bestellpostenHaus, bestellungDTO.bestellt);
        }
    }
}