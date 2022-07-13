package de.hsos.swa.coldstoneicecreator.kreationen.gateway;

import java.util.List;

import javax.enterprise.event.Observes;

import javax.enterprise.context.RequestScoped;

import de.hsos.swa.coldstoneicecreator.kreationen.control.HauskreationControl;
import de.hsos.swa.coldstoneicecreator.produkt.entity.Sauce;
import de.hsos.swa.coldstoneicecreator.produkt.entity.Eis;
import de.hsos.swa.coldstoneicecreator.produkt.entity.Zutat;
import de.hsos.swa.coldstoneicecreator.kreationen.entity.Hauskreation;

@RequestScoped
public class HauskreationRepository implements HauskreationControl{
    
    @Override
    public boolean create(Hauskreation hauskreation) {
        hauskreation.setId(null);
        hauskreation.persist();
        return true;
    }

    @Override
    public List<Hauskreation> get() {
        return Hauskreation.listAll();
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
        //Hauskreation alteHauskreation = Hauskreation.findById(id);
        //TODO: 
        return true;
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
