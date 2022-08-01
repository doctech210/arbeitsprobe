//@author Stefan Bierenriede 852142, Marvin Gels 868603

package de.hsos.swa.coldstoneicecreator.bestellung.gateway;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;

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
    public List<BestellpostenEigen> getAllEigen() {
        return BestellpostenEigen.listAll();
    }

    @Override
    public List<BestellpostenHaus> getAllHaus(){
        return BestellpostenHaus.listAll();
    }

    @Override
    public BestellpostenEigen postenEigenAnlegen(Long bestellId, Long kreationId, Long anzahl) {
        Eigenkreation kreation = Eigenkreation.findById(kreationId);
        Bestellung bestellung = Bestellung.findById(bestellId);
        if(kreation == null || bestellung == null) return null;
        List<BestellpostenEigen> bestellposten = bestellung.getBestellpostenEigen();//BestellpostenEigen.listAll();
        for(BestellpostenEigen bp : bestellposten){
            if(bp.getEigenkreation() == kreation){
                bp.setAnzahl(bp.getAnzahl() + anzahl);
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
        List<BestellpostenHaus> bestellposten = bestellung.getBestellpostenHaus();//BestellpostenHaus.listAll();
        for(BestellpostenHaus bp : bestellposten){
            if(bp.getHauskreation() == kreation){
                bp.setAnzahl(bp.getAnzahl() + anzahl);
                return null;
            }
        }
        BestellpostenHaus bp = new BestellpostenHaus(kreation, anzahl);
        bp.persist();
        bestellung.addPostenHaus(bp);
        return bp;
    }

    @Override
    public boolean postenAendernEigen(Long bestellId, Long postenId, Long anzahl) {
        BestellpostenEigen bp = BestellpostenEigen.findById(postenId);
        Bestellung bs = Bestellung.findById(bestellId);
        if(bp == null) return false;
        if(anzahl == 0){
            bs.removePostenEigen(postenId);
            return this.postenLoeschenEigen(postenId);
        } else if(anzahl > 0){
            bp.setAnzahl(anzahl);
            return true;
        }
        return false;
    }

    @Override
    public boolean postenAendernHaus(Long bestellId, Long postenId, Long anzahl) {
        BestellpostenHaus bp = BestellpostenHaus.findById(postenId);
        Bestellung bs = Bestellung.findById(bestellId);
        if(bp == null) return false;
        if(anzahl == 0){
            bs.removePostenHaus(postenId);
            return this.postenLoeschenHaus(postenId);
        } else if(anzahl > 0){
            bp.setAnzahl(anzahl);
            return true;
        }
        return false;
    }

    public boolean postenLoeschenEigen(Long postenId) {
        return BestellpostenEigen.deleteById(postenId);
    }

    public boolean postenLoeschenHaus(Long postenId) {
        return BestellpostenHaus.deleteById(postenId);
    }
    
    public void empfangeBestellung(@Observes KreationIdDAO kreationIdDAO){
        if(kreationIdDAO.getBestellId() == null){
            return;
        } 
        if(kreationIdDAO.isEigen()) this.postenEigenAnlegen(kreationIdDAO.getBestellId(), kreationIdDAO.getKreation().getId(), kreationIdDAO.getAnzahl());
        else this.postenHausAnlegen(kreationIdDAO.getBestellId(), kreationIdDAO.getKreation().getId(), kreationIdDAO.getAnzahl());
    }
}
