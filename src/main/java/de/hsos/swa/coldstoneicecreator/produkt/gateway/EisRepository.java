//@author Marvin Gels 868603

package de.hsos.swa.coldstoneicecreator.produkt.gateway;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Event;
import javax.inject.Inject;

import de.hsos.swa.coldstoneicecreator.produkt.control.EisControl;
import de.hsos.swa.coldstoneicecreator.produkt.entity.Allergene;
import de.hsos.swa.coldstoneicecreator.produkt.entity.Eis;

@ApplicationScoped
public class EisRepository implements EisControl{
    
    @Inject
    Event<Eis> kreationUpdate;

    @Override
    public boolean create(Eis eis) {
        eis.setId(null);
        eis.persist();
        return true;
    }

    @Override
    public List<Eis> get() {
        return Eis.listAll();
    }

    @Override
    public List<Eis> getOhneAllergene(List<Allergene> allergene){
        List<Eis> eise = Eis.listAll();
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
            eise.removeIf(eis -> eis.getAllergene().contains(allergen));
        }
        return eise;
    }


    @Override
    public Eis getById(Long id) {
        return Eis.findById(id);
    }
    
    @Override
    public boolean delete(Long id) {
        return Eis.deleteById(id);
    }

    @Override
    public boolean put(Long id, Eis eis) {
        Eis altesEis = Eis.findById(id);
        if(!(eis.getName().equals("string") || eis.getName().equals("")))
            altesEis.setName(eis.getName());
        if(!(eis.getAllergene().isEmpty()) || eis.getAllergene() != null)
            altesEis.setAllergene(eis.getAllergene());
        this.kreationenUpdaten(altesEis); 
        return true;
    }

    private void kreationenUpdaten(Eis eis) {
        kreationUpdate.fire(eis); //geht in das Eigenkreations- und das HauskreationsRepository
    }
}
