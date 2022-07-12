package de.hsos.swa.coldstoneicecreator.produkt.gateway;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;

import de.hsos.swa.coldstoneicecreator.produkt.control.SauceControl;
import de.hsos.swa.coldstoneicecreator.produkt.entity.Sauce;

@ApplicationScoped
public class SauceRepository implements SauceControl{
    
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
        //Eis altesEis = Eis.findById(id);
        //TODO: 
        return true;
    }
}
