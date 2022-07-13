package de.hsos.swa.coldstoneicecreator.produkt.gateway;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Event;
import javax.inject.Inject;

import de.hsos.swa.coldstoneicecreator.produkt.control.EisControl;
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
        kreationUpdate.fire(eis);
    }
}
