//@author Marvin Gels 868603

package de.hsos.swa.coldstoneicecreator.kreationen.gateway;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Event;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

import de.hsos.swa.coldstoneicecreator.bestellung.gateway.BestellpostenRepository;
import de.hsos.swa.coldstoneicecreator.kreationen.boundary.dao.KreationDAO;
import de.hsos.swa.coldstoneicecreator.kreationen.boundary.dao.ZutatenIdDAO;
import de.hsos.swa.coldstoneicecreator.kreationen.control.EigenkreationControl;
import de.hsos.swa.coldstoneicecreator.kreationen.entity.Eigenkreation;
import de.hsos.swa.coldstoneicecreator.nutzer.entity.Nutzer;
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

    @Inject
    BestellpostenRepository postenRepo;

    @Override
    public boolean create(Nutzer nutzer, Eigenkreation eigenkreation, Long anzahl) {
        if(eigenkreation == null) return false;
        eigenkreation.setId(null);
        eigenkreation.persist();
        nutzer.addEigenkreation(eigenkreation);
        this.neueEigenkreation.fire(new KreationDAO(nutzer, eigenkreation, anzahl, true)); //geht an BestellungRepository
        return true;
    }

    @Override
    public boolean create(Nutzer nutzer, Eigenkreation eigenkreation) {
        if(eigenkreation == null) return false;
        eigenkreation.setId(null);
        eigenkreation.persist();
        nutzer.addEigenkreation(eigenkreation);
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
    public Eigenkreation getById(Long id, Nutzer nutzer) {
        List<Eigenkreation> eigenkreationen = nutzer.getEigenkreationen();
        for(Eigenkreation eigenkreation : eigenkreationen) {
            if(Long.compare(eigenkreation.getId(), id) == 0) return eigenkreation;
        }
        return null;
    }

    @Override
    public boolean delete(Long id, Nutzer nutzer) {
        List<Eigenkreation> eigenkreationen = nutzer.getEigenkreationen();
        if(eigenkreationen == null) return false;
        for(Eigenkreation ek : eigenkreationen) {
            if(Long.compare(ek.getId(), id) == 0){
                nutzer.deleteEigenkreation(ek, null);
            }
        }
        return false;
    }

    @Override
    public boolean put(Long id, Eigenkreation eigenkreation, Nutzer nutzer) {
        boolean geaendert = false;
        Eigenkreation alteEigenkreation = null;
        List<Eigenkreation> eigenkreationen = nutzer.getEigenkreationen();
        for(Eigenkreation ek : eigenkreationen) {
            if(Long.compare(ek.getId(), id) == 0) alteEigenkreation =  ek;
        }
        if(alteEigenkreation == null) return geaendert;
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
    public boolean putZutat(Long id, int zutatnummer, Long neueZutatId, Nutzer nutzer) {
        boolean geaendert = false;
        Eigenkreation eigenkreation = null;
        List<Eigenkreation> eigenkreationen = nutzer.getEigenkreationen();
        for(Eigenkreation ek : eigenkreationen) {
            if(Long.compare(ek.getId(), id) == 0) eigenkreation =  ek;
        }
        if(eigenkreation == null) return geaendert;
        List<Zutat> zutaten = eigenkreation.getZutaten();
        zutaten.remove(zutatnummer);
        ZutatenIdDAO zutatenIdDAO = new ZutatenIdDAO(neueZutatId, eigenkreation);
        zutatWechseln.fire(zutatenIdDAO); //geht an das ZutatenRepository
        geaendert = true;
        return geaendert;
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

    public void eisUpdate(@Observes Eis neuesEis) {
        List<Eigenkreation> eigenkreationen = Eigenkreation.listAll();
        for(Eigenkreation eigenkreation : eigenkreationen) {
            if(Long.compare(eigenkreation.getEissorte().getId(), neuesEis.getId()) == 0 || Long.compare(eigenkreation.getEissorte2().getId(), neuesEis.getId()) == 0){
                eigenkreation.checkAllergene();
            }
        }
    }

    public void sauceUpdate(@Observes Sauce neueSauce) {
        List<Eigenkreation> eigenkreationen = Eigenkreation.listAll();
        for(Eigenkreation eigenkreation : eigenkreationen) {
            if(Long.compare(eigenkreation.getSauce().getId(), neueSauce.getId()) == 0){
                eigenkreation.checkAllergene();
            }
        }
    }

    public void eisUpdate(@Observes Zutat neueZutat) {
        List<Eigenkreation> eigenkreationen = Eigenkreation.listAll();
        for(Eigenkreation eigenkreation : eigenkreationen) {
            for(Zutat zutat : eigenkreation.getZutaten()) {
                if(Long.compare(zutat.getId(), neueZutat.getId()) == 0){
                    eigenkreation.checkAllergene();
                }
            }
        }
    }
}
