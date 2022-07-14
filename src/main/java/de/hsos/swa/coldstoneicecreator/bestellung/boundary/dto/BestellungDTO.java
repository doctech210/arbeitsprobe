package de.hsos.swa.coldstoneicecreator.bestellung.boundary.dto;

import java.util.List;
import java.util.ArrayList;

import de.hsos.swa.coldstoneicecreator.bestellung.entity.Bestellung;
import de.hsos.swa.coldstoneicecreator.bestellung.entity.Bestellposten;

public class BestellungDTO {
    public Long id;
    public List<BestellpostenDTO> bestellposten;
    public boolean bestellt;

    public BestellungDTO() {     
    }

    public BestellungDTO(Long id, List<BestellpostenDTO> bestellposten, boolean bestellt) {
        this.id = id;
        this.bestellposten = bestellposten;
        this.bestellt = bestellt;
    }

    public static class Converter {

        public static BestellungDTO toDTO(Bestellung bestellung) {
            List<BestellpostenDTO> bestellpostenDTO = new ArrayList<>();
            for(Bestellposten bestellposten : bestellung.getBestellposten()) {
                bestellpostenDTO.add(BestellpostenDTO.Converter.toDTO(bestellposten));
            }
            return new BestellungDTO(bestellung.getId(), bestellpostenDTO, bestellung.isBestellt());
        }

        public static Bestellung toBestellung(BestellungDTO bestellungDTO) {
            List<Bestellposten> bestellposten = new ArrayList<>();
            for(BestellpostenDTO bestellpostenDTO : bestellungDTO.bestellposten) {
                bestellposten.add(BestellpostenDTO.Converter.toBestellposten(bestellpostenDTO));
            }
            return new Bestellung(bestellungDTO.id, bestellposten, bestellungDTO.bestellt);
        }
    }
}