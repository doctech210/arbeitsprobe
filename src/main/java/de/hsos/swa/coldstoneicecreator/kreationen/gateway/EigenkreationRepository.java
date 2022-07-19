package de.hsos.swa.coldstoneicecreator.kreationen.gateway;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Event;
import javax.inject.Inject;

import de.hsos.swa.coldstoneicecreator.kreationen.boundary.dao.KreationDAO;
import de.hsos.swa.coldstoneicecreator.kreationen.control.EigenkreationControl;
import de.hsos.swa.coldstoneicecreator.kreationen.entity.Eigenkreation;
import de.hsos.swa.coldstoneicecreator.kunden.entity.Nutzer;
import de.hsos.swa.coldstoneicecreator.produkt.entity.Eis;
import de.hsos.swa.coldstoneicecreator.produkt.entity.Sauce;
import de.hsos.swa.coldstoneicecreator.produkt.entity.Zutat;

@ApplicationScoped
public class EigenkreationRepository implements EigenkreationControl{
    
    @Inject
    Event<KreationDAO> neueEigenkreation;

    @Override
    public boolean create(Nutzer kunde, Eigenkreation eigenkreation, Long anzahl) {
        eigenkreation.setId(null);
        eigenkreation.persist();
        kunde.addEigenkreation(eigenkreation);
        this.neueEigenkreation.fire(new KreationDAO(kunde, eigenkreation, anzahl, true));
        return true;
    }

    @Override
    public List<Eigenkreation> get() {
        return Eigenkreation.listAll();
    }
    @Override
    public Eigenkreation getById(Long id) {
        return Eigenkreation.findById(id);
    }

    @Override
    public boolean delete(Long id) {
        return Eigenkreation.deleteById(id);
    }

    @Override
    public boolean put(Long id, Eigenkreation eigenkreation) {
        Eigenkreation alteEigenkreation = Eigenkreation.findById(id);
        boolean geaendert = false;
        Eis neuesEis = eigenkreation.getEissorte();
        if(neuesEis != null) {
            alteEigenkreation.setEissorte(neuesEis);
            geaendert = true;
        }
        Eis neuesEis2 = eigenkreation.getEissorte();
        if(neuesEis2 != null) {
            alteEigenkreation.setEissorte(neuesEis2);
            geaendert = true;
        }
        Sauce neueSauce = eigenkreation.getSauce();
        if(neueSauce != null) {
            alteEigenkreation.setSauce(neueSauce);
            geaendert = true;
        }
        String neuerName = eigenkreation.getName();
        if(neuerName != "string" && neuerName != "") {
            alteEigenkreation.setName(neuerName);
            geaendert = true;
        }
        return geaendert;
    }
    
    @Override
    public boolean putZutat(Long id, int zutatnummer, Zutat zutat) {
        Eigenkreation alteEigenkreation = Eigenkreation.findById(id);
        List<Zutat> zutaten = alteEigenkreation.getZutaten();
        Zutat alteZutat = zutaten.get(zutatnummer);
        alteZutat.setName(zutat.getName());
        alteZutat.setPremium(zutat.isPremium());
        alteZutat.setAllergene(zutat.getAllergene());
        return true;
    }
}
