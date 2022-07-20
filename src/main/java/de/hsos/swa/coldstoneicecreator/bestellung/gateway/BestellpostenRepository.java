package de.hsos.swa.coldstoneicecreator.bestellung.gateway;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.ws.rs.NotFoundException;

import de.hsos.swa.coldstoneicecreator.bestellung.control.BestellpostenControl;
import de.hsos.swa.coldstoneicecreator.bestellung.entity.BestellpostenEigen;
import de.hsos.swa.coldstoneicecreator.bestellung.entity.BestellpostenHaus;
import de.hsos.swa.coldstoneicecreator.bestellung.entity.Bestellung;
import de.hsos.swa.coldstoneicecreator.kreationen.boundary.dao.KreationIdDAO;
import de.hsos.swa.coldstoneicecreator.kreationen.entity.Eigenkreation;
import de.hsos.swa.coldstoneicecreator.kreationen.entity.Hauskreation;

@ApplicationScoped
public class BestellpostenRepository implements BestellpostenControl {

    @Override
    public BestellpostenEigen postenEigenAnlegen(Long bestellId, Long kreationId, Long anzahl) {
        Eigenkreation kreation = Eigenkreation.findById(kreationId);
        Bestellung bestellung = Bestellung.findById(bestellId);
        if(kreation == null || bestellung == null) return null;
        List<BestellpostenEigen> bestellposten = BestellpostenEigen.listAll();
        for(BestellpostenEigen bp : bestellposten){
            if(bp.getEigenkreation() == kreation){
                bp.setAnzahl(anzahl);
                return null;
            }
        }
        BestellpostenEigen bp = new BestellpostenEigen(kreation, anzahl);
        bp.persist();
        bestellung.addPostenEigen(bp);
        return bp;
    }

    @Override
    public BestellpostenHaus postenHausAnlegen(Long bestellId, Long kreationId, Long anzahl) {
        Hauskreation kreation = Hauskreation.findById(kreationId);
        Bestellung bestellung = Bestellung.findById(bestellId);
        if(kreation == null || bestellung == null) return null;
        List<BestellpostenHaus> bestellposten = BestellpostenHaus.listAll();
        for(BestellpostenHaus bp : bestellposten){
            if(bp.getHauskreation() == kreation){
                bp.setAnzahl(anzahl);
                return null;
            }
        }
        BestellpostenHaus bp = new BestellpostenHaus(kreation, anzahl);
        bp.persist();
        bestellung.addPostenHaus(bp);
        return bp;
    }


    @Override
    public List<BestellpostenEigen> bestellpostenAbfragen() {
        return BestellpostenEigen.listAll();
    }

    @Override
    public BestellpostenEigen postenAbfragen(Long postenId) {
        BestellpostenEigen bp = BestellpostenEigen.findById(postenId);
        if(bp == null) throw new NotFoundException();
        return bp;
    }

    @Override
    public boolean postenAendern(Long bestellId, Long postenId, Long anzahl) {
        BestellpostenEigen bp = BestellpostenEigen.findById(postenId);
        Bestellung bs = Bestellung.findById(bestellId);
        if(bp == null) throw new NotFoundException();
        if(anzahl == 0){
            bs.removePostenEigen(postenId);
            return this.postenLoeschen(postenId);
        } else if(anzahl > 0){
            bp.setAnzahl(anzahl);
            return true;
        }
        return false;
    }

    @Override
    public boolean postenLoeschen(Long postenId) {
        return BestellpostenEigen.deleteById(postenId);
    }
    
    public void empfangeBestellung(@Observes KreationIdDAO kreationIdDAO){
        if(kreationIdDAO.getBestellId() == null){
            return;
        } 
        if(kreationIdDAO.isEigen()) this.postenEigenAnlegen(kreationIdDAO.getBestellId(), kreationIdDAO.getKreation().getId(), kreationIdDAO.getAnzahl());
        else this.postenHausAnlegen(kreationIdDAO.getBestellId(), kreationIdDAO.getKreation().getId(), kreationIdDAO.getAnzahl());
    }
}