package de.hsos.swa.coldstoneicecreator.kunden.gateway;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;

import de.hsos.swa.coldstoneicecreator.kunden.control.KundeControl;
import de.hsos.swa.coldstoneicecreator.kunden.entity.Nutzer;

@ApplicationScoped
public class KundeRepository implements KundeControl{
    
    @Override
    public boolean create(Nutzer kunde) {
        kunde.setId(null);
        kunde.persist();
        return true;
    }

    @Override
    public List<Nutzer> get() {
        return Nutzer.listAll();
    }
    @Override
    public Nutzer getById(Long id) {
        return Nutzer.findById(id);
    }

    @Override
    public boolean delete(Long id) {
        return Nutzer.deleteById(id);
    }

    @Override
    public boolean put(Long id, Nutzer kunde) {
        //Kunde alteKunde = Kunde.findById(id);
        //TODO: 
        return true;
    }
    
}
