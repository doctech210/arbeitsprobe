package de.hsos.swa.coldstoneicecreator.bestellung.gateway;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.ws.rs.NotFoundException;

import de.hsos.swa.coldstoneicecreator.bestellung.control.BestellpostenControl;
import de.hsos.swa.coldstoneicecreator.bestellung.entity.BestellpostenEigen;
import de.hsos.swa.coldstoneicecreator.bestellung.entity.BestellpostenHaus;
import de.hsos.swa.coldstoneicecreator.bestellung.entity.Bestellung;
import de.hsos.swa.coldstoneicecreator.kreationen.boundary.dao.KreationDAO;
import de.hsos.swa.coldstoneicecreator.kreationen.entity.Eigenkreation;
import de.hsos.swa.coldstoneicecreator.kreationen.entity.Hauskreation;

@ApplicationScoped
public class BestellpostenRepository implements BestellpostenControl {

    @Override
    public BestellpostenEigen postenEigenAnlegen(Long bestellId, Long kreationId) {
        Eigenkreation kreation = Eigenkreation.findById(kreationId);
        Bestellung bestellung = Bestellung.findById(bestellId);
        if(kreation == null || bestellung == null) return null;
        List<BestellpostenEigen> bestellposten = BestellpostenEigen.listAll();
        for(BestellpostenEigen bp : bestellposten){
            if(bp.getEigenkreation() == kreation){
                bp.addEigenkreation();
                return null;
            }
        }
        BestellpostenEigen bp = new BestellpostenEigen(kreation);
        bp.persist();
        return bp;
    }

    @Override
    public BestellpostenHaus postenHausAnlegen(Long bestellId, Long kreationId) {
        Hauskreation kreation = Hauskreation.findById(kreationId);
        Bestellung bestellung = Bestellung.findById(bestellId);
        if(kreation == null || bestellung == null) return null;
        List<BestellpostenHaus> bestellposten = BestellpostenHaus.listAll();
        for(BestellpostenHaus bp : bestellposten){
            if(bp.getHauskreation() == kreation){
                bp.addHauskreation();
                return null;
            }
        }
        BestellpostenHaus bp = new BestellpostenHaus(kreation);
        bp.persist();
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
    public boolean postenAendern(Long bestellId, Long postenId, int anzahl) {
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
    
    public void empfangeBestellung(@Observes KreationDAO kreationDAO){
        if(kreationDAO.getBestellId() == null){
            return;
        } 
        if(kreationDAO.isEigen()) this.postenEigenAnlegen(kreationDAO.getBestellId(), kreationDAO.getKreation().getId());
        else this.postenHausAnlegen(kreationDAO.getBestellId(), kreationDAO.getKreation().getId());
    }
}
