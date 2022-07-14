package de.hsos.swa.coldstoneicecreator.bestellung.gateway;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.ws.rs.NotFoundException;

import de.hsos.swa.coldstoneicecreator.bestellung.control.BestellpostenControl;
import de.hsos.swa.coldstoneicecreator.bestellung.entity.Bestellposten;
import de.hsos.swa.coldstoneicecreator.bestellung.entity.Bestellung;
import de.hsos.swa.coldstoneicecreator.kreationen.boundary.dao.KreationDAO;
import de.hsos.swa.coldstoneicecreator.kreationen.entity.Eigenkreation;
import de.hsos.swa.coldstoneicecreator.kreationen.entity.Hauskreation;

@ApplicationScoped
public class BestellpostenRepository implements BestellpostenControl {

    @Override
    public Bestellposten postenAnlegen(Long bestellId, Long kreationId) {
        Hauskreation kreation = Hauskreation.findById(kreationId);
        Bestellung bestellung = Bestellung.findById(bestellId);
        if(kreation == null || bestellung == null) throw new NotFoundException();
        List<Bestellposten> bestellposten = Bestellposten.listAll();
        for(Bestellposten bp : bestellposten){
            if(bp.getKreation() == kreation){
                bp.addKreation();
                return null;
            }
        }
        Bestellposten bp = new Bestellposten(kreation);
        bp.persist();
        return bp;
    }

    @Override
    public Bestellposten postenAnlegen2(Long bestellId, KreationDAO kreationDAO) {
        if(kreationDAO.isEigen()){
            Eigenkreation eigenkreation = Eigenkreation.findById(kreationDAO.getKreation().getId());
            Bestellung bestellung = Bestellung.findById(bestellId);
            if(eigenkreation == null || bestellung == null) throw new NotFoundException();
            List<Bestellposten> bestellposten = Bestellposten.listAll();
            for(Bestellposten bp : bestellposten){
                if(bp.getKreation() == eigenkreation){
                    bp.addKreation();
                    return null;
                }
        }
        Bestellposten bp = new Bestellposten(eigenkreation);
        bp.persist();
        return bp;
        } else {
            Hauskreation hauskreation = Hauskreation.findById(kreationDAO.getKreation().getId());
            Bestellung bestellung = Bestellung.findById(bestellId);
            if(hauskreation == null || bestellung == null) throw new NotFoundException();
            List<Bestellposten> bestellposten = Bestellposten.listAll();
            for(Bestellposten bp : bestellposten){
                if(bp.getKreation() == hauskreation){
                    bp.addKreation();
                    return null;
                }
            }
            Bestellposten bp = new Bestellposten(hauskreation);
            bp.persist();
            return bp;
        }
    }


    @Override
    public List<Bestellposten> bestellpostenAbfragen() {
        return Bestellposten.listAll();
    }

    @Override
    public Bestellposten postenAbfragen(Long postenId) {
        Bestellposten bp = Bestellposten.findById(postenId);
        if(bp == null) throw new NotFoundException();
        return bp;
    }

    @Override
    public boolean postenAendern(Long bestellId, Long postenId, int anzahl) {
        Bestellposten bp = Bestellposten.findById(postenId);
        Bestellung bs = Bestellung.findById(bestellId);
        if(bp == null) throw new NotFoundException();
        if(anzahl == 0){
            bs.removePosten(postenId);
            return this.postenLoeschen(postenId);
        } else if(anzahl > 0){
            bp.setAnzahl(anzahl);
            return true;
        }
        return false;
    }

    @Override
    public boolean postenLoeschen(Long postenId) {
        return Bestellposten.deleteById(postenId);
    }
    
    public void empfangeBestellung(@Observes KreationDAO kreationDAO){
        if(kreationDAO.getBestellId() == null){
            return;
        } 
        this.postenAnlegen(kreationDAO.getBestellId(), kreationDAO.getKreation().getId());
    }
}
