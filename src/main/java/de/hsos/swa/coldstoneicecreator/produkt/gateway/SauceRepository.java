//@author Marvin Gels 868603

package de.hsos.swa.coldstoneicecreator.produkt.gateway;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Event;
import javax.inject.Inject;

import de.hsos.swa.coldstoneicecreator.produkt.control.SauceControl;
import de.hsos.swa.coldstoneicecreator.produkt.entity.Allergene;
import de.hsos.swa.coldstoneicecreator.produkt.entity.Sauce;

@ApplicationScoped
public class SauceRepository implements SauceControl{
    
    @Inject
    Event<Sauce> kreationUpdate;

    @Override
    public boolean create(Sauce sauce) {
        sauce.setId(null);
        sauce.persist();
        return true;
    }

    @Override
    public List<Sauce> get() {
        return Sauce.listAll();
    }

    @Override
    public Sauce getById(Long id) {
        return Sauce.findById(id);
    }
    
    @Override
    public boolean delete(Long id) {
        return Sauce.deleteById(id);
    }

    @Override
    public boolean put(Long id, Sauce sauce) {
        Sauce alteSauce = Sauce.findById(id);
        if(!(sauce.getName().equals("string") || sauce.getName().equals(""))) {
            alteSauce.setName(sauce.getName());
        }
        if(sauce.getAllergene() != null) {
            alteSauce.setAllergene(sauce.getAllergene());
        }
        this.kreationenUpdaten(alteSauce);
        return true;
    }

    @Override
    public List<Sauce> getOhneAllergene(List<Allergene> allergene){
        List<Sauce> saucen = Sauce.listAll();
        List<Allergene> va = new ArrayList<>();
        for(Allergene allergen : allergene) {
            if(allergen.equals(Allergene.VEGAN)) {
                va.add(Allergene.EI);
                va.add(Allergene.HONIG);
                va.add(Allergene.LAKTOSE);
                va.add(Allergene.GELATINE);
            }
        }
        va.addAll(allergene);
        for(Allergene allergen : va) {  
            saucen.removeIf(zutat -> zutat.getAllergene().contains(allergen));
        }
        return saucen;
    }

    private void kreationenUpdaten(Sauce sauce){
        kreationUpdate.fire(sauce); //geht in das Eigenkreations- und das HauskreationsRepository
    }
}
