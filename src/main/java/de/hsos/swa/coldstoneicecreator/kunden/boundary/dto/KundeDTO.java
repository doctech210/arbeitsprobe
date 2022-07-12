package de.hsos.swa.coldstoneicecreator.kunden.boundary.dto;

import java.util.ArrayList;
import java.util.List;

import de.hsos.swa.coldstoneicecreator.kreationen.boundary.dto.EigenkreationDTO;
import de.hsos.swa.coldstoneicecreator.kreationen.boundary.dto.HauskreationDTO;
import de.hsos.swa.coldstoneicecreator.kreationen.boundary.dto.KreationDTO;
import de.hsos.swa.coldstoneicecreator.kreationen.entity.Eigenkreation;
import de.hsos.swa.coldstoneicecreator.kreationen.entity.Hauskreation;
import de.hsos.swa.coldstoneicecreator.kreationen.entity.Kreation;
import de.hsos.swa.coldstoneicecreator.kunden.entity.Kunde;

public class KundeDTO {
    public Long id;
    public String name;
    public String passwort;
    //public List<KreationDTO> warenkorb;

    public KundeDTO() {
    }

    public KundeDTO(Long id, String name, String passwort, List<KreationDTO> warenkorb) {
        this.id = id;
        this.name = name;
        this.passwort = passwort;
        //this.warenkorb = warenkorb;
    }

    public static class Converter {

        public static KundeDTO toDTO(Kunde kunde) {
            /*List<Kreation> warenkorb = kunde.getWarenkorb();
            List<KreationDTO> warenkorbDTO = new ArrayList<>();
            for(Kreation kreation : warenkorb) {
                if(kreation.getClass() == Kunde.class) {
                    warenkorbDTO.add(EigenkreationDTO.Converter.toDTO((Eigenkreation)kreation));
                }else{
                    warenkorbDTO.add(HauskreationDTO.Converter.toDTO((Hauskreation)kreation));
                }
            }*/
            return new KundeDTO(kunde.getId(), kunde.getName(), kunde.getPasswort(), null);
        }

        public static Kunde toKunde(KundeDTO kundeDTO) {
            /*List<KreationDTO> warenkorbDTO = kundeDTO.warenkorb;
            List<Kreation> warenkorb = new ArrayList<>();
            for(KreationDTO kreationDTO : warenkorbDTO) {
                if(kreationDTO.getClass() == EigenkreationDTO.class) {
                    warenkorb.add((Kreation)EigenkreationDTO.Converter.toEigenkreation((EigenkreationDTO)kreationDTO));
                }else{
                    warenkorb.add((Kreation)HauskreationDTO.Converter.toHauskreation((HauskreationDTO)kreationDTO));
                }
            }*/
            return new Kunde(kundeDTO.id, kundeDTO.name, kundeDTO.passwort, null);
        }
    }
}
