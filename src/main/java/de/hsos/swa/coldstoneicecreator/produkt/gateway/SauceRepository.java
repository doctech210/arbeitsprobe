package de.hsos.swa.coldstoneicecreator.produkt.gateway;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Event;
import javax.inject.Inject;

import de.hsos.swa.coldstoneicecreator.produkt.control.SauceControl;
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

    private void kreationenUpdaten(Sauce sauce){
        kreationUpdate.fire(sauce);
    }
}
