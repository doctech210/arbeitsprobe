package de.hsos.swa.coldstoneicecreator.bestellung.gateway;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Event;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.ws.rs.NotFoundException;

import de.hsos.swa.coldstoneicecreator.bestellung.control.BestellpostenControl;
import de.hsos.swa.coldstoneicecreator.bestellung.control.BestellungControl;
import de.hsos.swa.coldstoneicecreator.bestellung.entity.Bestellung;
import de.hsos.swa.coldstoneicecreator.kreationen.boundary.dao.KreationDAO;
import de.hsos.swa.coldstoneicecreator.kreationen.boundary.dao.KreationIdDAO;
import de.hsos.swa.coldstoneicecreator.kunden.entity.Nutzer;

@ApplicationScoped
public class BestellungRepository implements BestellungControl {

    @Inject
    BestellpostenControl bp;

    @Inject
    Event<KreationIdDAO> neueKreation;

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
            if(!(bestellung.isBestellt())){
                neueKreation.fire(new KreationIdDAO(kreationDAO, bestellung.getId()));
                return;
            }
        }
        Bestellung bestellung = this.bestellungAnlegen(new Bestellung(), kreationDAO.getKunde().getId());
        Nutzer kunde = kreationDAO.getKunde();
        kunde.addBestellung(bestellung);
        neueKreation.fire(new KreationIdDAO(kreationDAO, bestellung.getId()));
    }

    /*
    public void bestellen(@Observes BestellungDAO bestellungDAO) {
        if(bestellungDAO.getKreation().getClass() == Eigenkreation.class) {
            BestellpostenEigen bestellposten = new BestellpostenEigen(null, (Eigenkreation)bestellungDAO.getKreation(), bestellungDAO.getAnzahl().intValue());
            Bestellung bestellung = new Bestellung();
            Nutzer kunde = bestellungDAO.getKunde();
            bestellungAnlegen(bestellung, kunde.getId());
            bestellung.addPostenEigen(bestellposten);
        }else{
            Bestellung bestellung = new Bestellung();
            Nutzer kunde = bestellungDAO.getKunde();
            bestellung = bestellungAnlegen(bestellung, kunde.getId());
            BestellpostenHaus bestellposten = bp.postenHausAnlegen(bestellung.getId(), bestellungDAO.getKreation().getId());
            bestellung.addPostenHaus(bestellposten);
        }
    }

    public void bestellen(@Observes BestellungIdDAO bestellungIdDAO) {
        if(bestellungIdDAO.getKreation().getClass() == Eigenkreation.class) {
            BestellpostenEigen bestellposten = new BestellpostenEigen(null, (Eigenkreation)bestellungIdDAO.getKreation(), bestellungIdDAO.getAnzahl().intValue());
            Long id = bestellungIdDAO.getBestellungsId();
            Nutzer kunde = bestellungIdDAO.getKunde();
            Bestellung bestellung = bestellungAbfragen(id, kunde.getId());
            bestellung.addPostenEigen(bestellposten);
        }else{
            BestellpostenHaus bestellposten = new BestellpostenHaus(null, (Hauskreation)bestellungIdDAO.getKreation(), bestellungIdDAO.getAnzahl().intValue());
            Long id = bestellungIdDAO.getBestellungsId();
            Nutzer kunde = bestellungIdDAO.getKunde();
            Bestellung bestellung = bestellungAbfragen(id, kunde.getId());
            bestellung.addPostenHaus(bestellposten);
        }
    }*/

}

