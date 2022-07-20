package de.hsos.swa.coldstoneicecreator.kreationen.gateway;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Event;
import javax.inject.Inject;

import de.hsos.swa.coldstoneicecreator.kreationen.boundary.dao.KreationDAO;
import de.hsos.swa.coldstoneicecreator.kreationen.boundary.dao.ZutatenIdDAO;
import de.hsos.swa.coldstoneicecreator.kreationen.control.EigenkreationControl;
import de.hsos.swa.coldstoneicecreator.kreationen.entity.Eigenkreation;
import de.hsos.swa.coldstoneicecreator.kunden.entity.Nutzer;
import de.hsos.swa.coldstoneicecreator.produkt.entity.Allergene;
import de.hsos.swa.coldstoneicecreator.produkt.entity.Eis;
import de.hsos.swa.coldstoneicecreator.produkt.entity.Sauce;
import de.hsos.swa.coldstoneicecreator.produkt.entity.Zutat;

@ApplicationScoped
public class EigenkreationRepository implements EigenkreationControl{
    
    @Inject
    Event<KreationDAO> neueEigenkreation;

    @Inject
    Event<ZutatenIdDAO> zutatWechseln;

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
    public boolean putZutat(Long id, int zutatnummer, Long neueZutatId) {
        Eigenkreation eigenkreation = Eigenkreation.findById(id);
        List<Zutat> zutaten = eigenkreation.getZutaten();
        zutaten.remove(zutatnummer);
        //TODO: Zutat und neue Zutat an ZutatRepository wechseln
        ZutatenIdDAO zutatenIdDAO = new ZutatenIdDAO(neueZutatId, eigenkreation);
        zutatWechseln.fire(zutatenIdDAO);
        return true;
    }

    @Override
    public boolean post(Long kreationId, Long anzahl, Nutzer nutzer){
        Eigenkreation eigenkreation = this.getById(kreationId);
        if(eigenkreation == null) return false;
        KreationDAO kreation = new KreationDAO(nutzer, eigenkreation, anzahl, true);
        neueEigenkreation.fire(kreation);
        return true;
    }

    @Override
    public List<Eigenkreation> getOhneAllergene(List<Allergene> allergene){
        List<Eigenkreation> eigenkreationen = Eigenkreation.listAll();
        List<Allergene> va = new ArrayList<>();
        for(Allergene allergen : allergene) {
            if(allergen.equals(Allergene.VEGAN)) {
                va.add(Allergene.EI);
                va.add(Allergene.HONIG);
                va.add(Allergene.LAKTOSE);
                va.add(Allergene.GELANTINE);
            }
        }
        va.addAll(allergene);
        for(Allergene allergen : va) {  
            eigenkreationen.removeIf(zutat -> zutat.getAllergene().contains(allergen));
        }
        return eigenkreationen;
    }
}
