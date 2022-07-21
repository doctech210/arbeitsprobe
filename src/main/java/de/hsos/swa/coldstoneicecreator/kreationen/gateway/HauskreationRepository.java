package de.hsos.swa.coldstoneicecreator.kreationen.gateway;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.event.Event;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.enterprise.context.RequestScoped;

import de.hsos.swa.coldstoneicecreator.kreationen.boundary.dao.KreationDAO;
import de.hsos.swa.coldstoneicecreator.kreationen.control.HauskreationControl;
import de.hsos.swa.coldstoneicecreator.produkt.entity.Allergene;
import de.hsos.swa.coldstoneicecreator.produkt.entity.Sauce;
import de.hsos.swa.coldstoneicecreator.produkt.entity.Eis;
import de.hsos.swa.coldstoneicecreator.produkt.entity.Zutat;
import de.hsos.swa.coldstoneicecreator.kreationen.entity.Hauskreation;
import de.hsos.swa.coldstoneicecreator.kunden.entity.Nutzer;

@RequestScoped
public class HauskreationRepository implements HauskreationControl{
    
    @Inject
    Event<KreationDAO> neueHauskreation;

    @Override
    public boolean create(Hauskreation hauskreation) {
        hauskreation.setId(null);
        hauskreation.persist();
        return true;
    }

    @Override
    public boolean create(Nutzer kunde, Hauskreation hauskreation, Long anzahl) {
        this.neueHauskreation.fire(new KreationDAO(kunde, hauskreation, anzahl, false));  //geht an BestellungRepository
        return true;
    }

    @Override
    public List<Hauskreation> get() {
        return Hauskreation.listAll();
    }

    @Override
    public List<Hauskreation> getOhneAllergene(List<Allergene> allergene){
        List<Hauskreation> hauskreationen = Hauskreation.listAll();
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
            hauskreationen.removeIf(hauskreation -> hauskreation.getAllergene().contains(allergen));
        }
        return hauskreationen;
    }

    @Override
    public Hauskreation getById(Long id) {
        return Hauskreation.findById(id);
    }

    @Override
    public boolean delete(Long id) {
        return Hauskreation.deleteById(id);
    }

    @Override
    public boolean put(Long id, Hauskreation hauskreation) {
        Hauskreation alteHauskreation = Hauskreation.findById(id);
        boolean geaendert = false;
        Eis neuesEis = hauskreation.getEissorte();
        if(neuesEis != null) {
            alteHauskreation.setEissorte(neuesEis);
            geaendert = true;
        }
        Eis neuesEis2 = hauskreation.getEissorte();
        if(neuesEis2 != null) {
            alteHauskreation.setEissorte(neuesEis2);
            geaendert = true;
        }
        Sauce neueSauce = hauskreation.getSauce();
        if(neueSauce != null) {
            alteHauskreation.setSauce(neueSauce);
            geaendert = true;
        }
        String neuerName = hauskreation.getName();
        if(neuerName != "string" && neuerName != "") {
            alteHauskreation.setName(neuerName);
            geaendert = true;
        }
        return geaendert;
    }

    public void eisUpdate(@Observes Eis neuesEis) {
        List<Hauskreation> hauskreationen = Hauskreation.listAll();
        for(Hauskreation hauskreation : hauskreationen) {
            if(Long.compare(hauskreation.getEissorte().getId(), neuesEis.getId()) == 0 || Long.compare(hauskreation.getEissorte2().getId(), neuesEis.getId()) == 0){
                hauskreation.checkAllergene();
            }
        }
    }

    public void sauceUpdate(@Observes Sauce neueSauce) {
        List<Hauskreation> hauskreationen = Hauskreation.listAll();
        for(Hauskreation hauskreation : hauskreationen) {
            if(Long.compare(hauskreation.getSauce().getId(), neueSauce.getId()) == 0){
                hauskreation.checkAllergene();
            }
        }
    }

    public void eisUpdate(@Observes Zutat neueZutat) {
        List<Hauskreation> hauskreationen = Hauskreation.listAll();
        for(Hauskreation hauskreation : hauskreationen) {
            for(Zutat zutat : hauskreation.getZutaten()) {
                if(Long.compare(zutat.getId(), neueZutat.getId()) == 0){
                    hauskreation.checkAllergene();
                }
            }
        }
    }

}
