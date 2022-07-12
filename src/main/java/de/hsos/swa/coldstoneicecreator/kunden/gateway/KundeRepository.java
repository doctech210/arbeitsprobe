package de.hsos.swa.coldstoneicecreator.kunden.gateway;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;

import de.hsos.swa.coldstoneicecreator.kunden.control.KundeControl;
import de.hsos.swa.coldstoneicecreator.kunden.entity.Kunde;

@ApplicationScoped
public class KundeRepository implements KundeControl{
    
    @Override
    public boolean create(Kunde kunde) {
        kunde.setId(null);
        kunde.persist();
        return true;
    }

    @Override
    public List<Kunde> get() {
        return Kunde.listAll();
    }
    @Override
    public Kunde getById(Long id) {
        return Kunde.findById(id);
    }

    @Override
    public boolean delete(Long id) {
        return Kunde.deleteById(id);
    }

    @Override
    public boolean put(Long id, Kunde kunde) {
        //Kunde alteKunde = Kunde.findById(id);
        //TODO: 
        return true;
    }
    
}
