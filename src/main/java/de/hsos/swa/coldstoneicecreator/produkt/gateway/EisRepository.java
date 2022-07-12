package de.hsos.swa.coldstoneicecreator.produkt.gateway;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;

import de.hsos.swa.coldstoneicecreator.produkt.control.EisControl;
import de.hsos.swa.coldstoneicecreator.produkt.entity.Eis;

@ApplicationScoped
public class EisRepository implements EisControl{
    
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
    public Eis getById(Long id) {
        return Eis.findById(id);
    }
    
    @Override
    public boolean delete(Long id) {
        return Eis.deleteById(id);
    }

    @Override
    public boolean put(Long id, Eis eis) {
        //Eis altesEis = Eis.findById(id);
        //TODO: 
        return true;
    }
}
