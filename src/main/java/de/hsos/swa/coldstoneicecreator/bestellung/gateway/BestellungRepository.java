package de.hsos.swa.coldstoneicecreator.bestellung.gateway;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Event;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.ws.rs.NotFoundException;

import de.hsos.swa.coldstoneicecreator.bestellung.control.BestellpostenControl;
import de.hsos.swa.coldstoneicecreator.bestellung.control.BestellungControl;
import de.hsos.swa.coldstoneicecreator.bestellung.entity.BestellpostenEigen;
import de.hsos.swa.coldstoneicecreator.bestellung.entity.BestellpostenHaus;
import de.hsos.swa.coldstoneicecreator.bestellung.entity.Bestellung;
import de.hsos.swa.coldstoneicecreator.kreationen.boundary.dao.KreationDAO;
import de.hsos.swa.coldstoneicecreator.kreationen.boundary.dao.KreationIdDAO;
import de.hsos.swa.coldstoneicecreator.nutzer.entity.Nutzer;

@ApplicationScoped
public class BestellungRepository implements BestellungControl {

    @Inject
    BestellpostenControl bp;

    @Inject
    Event<KreationIdDAO> neueKreation;

    @Override
    public List<Bestellung> bestellungenAbfragen() {
        return Bestellung.listAll();
    }

    @Override
    public List<Bestellung> bestellungenAbfragen(Long nutzerId) {
        return Bestellung.list("bestellId_id", nutzerId);
    }

    @Override
    public Bestellung bestellungAbfragen(Long bestellId) {
        Bestellung bestellung = Bestellung.findById(bestellId);
        if(bestellung == null) throw new NotFoundException();
        return bestellung;
    }

    @Override
    public Bestellung bestellungAbfragen(Long bestellId, Long nutzerId) {
        List<Bestellung> bestellungen = Bestellung.list("bestellId_id", nutzerId);
        Bestellung bestellung = null;
        for(Bestellung b : bestellungen) {
            if(Long.compare(b.getId(), bestellId) == 0) bestellung = b;
        }
        if(bestellung == null) throw new NotFoundException();
        return bestellung;
    }

    public Bestellung bestellungAnlegen(Bestellung bestellung) {
        bestellung.setId(Long.valueOf(null));
        bestellung.persist();
        return bestellung;
    }

    @Override
    public Bestellung bestellungAnlegen(Bestellung bestellung, Long nutzerId) {
        List<Bestellung> bestellungen = this.bestellungenAbfragen(nutzerId);
        for(Bestellung b : bestellungen) {
            if(!b.isBestellt()) return null;
        }
        bestellung.setId(null);
        bestellung.persist();
        return bestellung;
    }

    @Override
    public boolean bestellungLoeschen(Long bestellId) {
        return Bestellung.deleteById(bestellId);
    }

    @Override
    public boolean bestellungLoeschen(Long bestellId, Long nutzerId) {
        List<Bestellung> bestellungen = this.bestellungenAbfragen(nutzerId);
        for(Bestellung b : bestellungen) {
            if(Long.compare(b.getId(), bestellId) == 0) b.delete(); return true;
        }
        return false;
    }

    public void empfangeBestellung(@Observes KreationDAO kreationDAO){
        List<Bestellung> bestellungen = this.bestellungenAbfragen(kreationDAO.getKunde().getId());
        for(Bestellung bestellung : bestellungen){
            if(!(bestellung.isBestellt())){
                neueKreation.fire(new KreationIdDAO(kreationDAO, bestellung.getId())); //Geht an das BestellpostenRepository
                return;
            }
        }
        Bestellung bestellung = this.bestellungAnlegen(new Bestellung(), kreationDAO.getKunde().getId());
        Nutzer nutzer = kreationDAO.getKunde();
        nutzer.addBestellung(bestellung);
        neueKreation.fire(new KreationIdDAO(kreationDAO, bestellung.getId())); //Geht an das BestellpostenRepository
    }

    @Override
    public boolean isBestellungLeer(Long bestellId){
        Bestellung bestellung = this.bestellungAbfragen(bestellId); 
        List<BestellpostenEigen> eigen = bestellung.getBestellpostenEigen();
        List<BestellpostenHaus> haus = bestellung.getBestellpostenHaus();
        if(eigen.size() == 0 && haus.size() == 0){
            return true;
        }
        return false;
    }
}

