package de.hsos.swa.coldstoneicecreator.bestellung.gateway;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Event;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.ws.rs.NotFoundException;

import de.hsos.swa.coldstoneicecreator.bestellung.control.BestellungControl;
import de.hsos.swa.coldstoneicecreator.bestellung.entity.Bestellung;
import de.hsos.swa.coldstoneicecreator.kreationen.boundary.dao.KreationDAO;

@ApplicationScoped
public class BestellungRepository implements BestellungControl {

    @Inject
    Event<KreationDAO> neueEigenkreation;

    public List<Bestellung> bestellungenAbfragen() {
        return Bestellung.listAll();
    }

    @Override
    public List<Bestellung> bestellungenAbfragen(Long kundeId) {
        return Bestellung.list("bestellId_id", kundeId);
    }

    public Bestellung bestellungAbfragen(Long bestellId) {
        Bestellung bestellung = Bestellung.findById(bestellId);
        if(bestellung == null) throw new NotFoundException();
        return bestellung;
    }

    @Override
    public Bestellung bestellungAbfragen(Long bestellId, Long kundeId) {
        List<Bestellung> bestellungen = Bestellung.list("bestellId_id", kundeId);
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
    public Bestellung bestellungAnlegen(Bestellung bestellung, Long kundeId) {
        List<Bestellung> bestellungen = this.bestellungenAbfragen(kundeId);
        for(Bestellung b : bestellungen) {
            if(!b.isBestellt()) return null;
        }
        bestellung.setId(null);
        bestellung.persist();
        return bestellung;
    }

    public boolean bestellungLoeschen(Long bestellId) {
        return Bestellung.deleteById(bestellId);
    }

    @Override
    public boolean bestellungLoeschen(Long bestellId, Long kundeId) {
        List<Bestellung> bestellungen = this.bestellungenAbfragen(kundeId);
        for(Bestellung b : bestellungen) {
            if(Long.compare(b.getId(), bestellId) == 0) b.delete(); return true;
        }
        return false;
    }

    public void empfangeBestellung(@Observes KreationDAO kreationDAO){
        List<Bestellung> bestellungen = this.bestellungenAbfragen(kreationDAO.getKunde().getId());
        for(Bestellung bestellung : bestellungen){
            if(!(bestellung.isBestellt())) kreationDAO.setBestellId(bestellung.getId());
        }
        neueEigenkreation.fire(kreationDAO);
    }
}

