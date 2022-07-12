package de.hsos.swa.coldstoneicecreator.produkt.gateway;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;

import de.hsos.swa.coldstoneicecreator.produkt.control.ZutatControl;
import de.hsos.swa.coldstoneicecreator.produkt.entity.Zutat;

@ApplicationScoped
public class ZutatRepository implements ZutatControl{
    
    @Override
    public boolean create(Zutat zutat) {
        zutat.setId(null);
        zutat.persist();
        return true;
    }

    @Override
    public List<Zutat> get() {
        return Zutat.listAll();
    }

    @Override
    public Zutat getById(Long id) {
        return Zutat.findById(id);
    }
    
    @Override
    public boolean delete(Long id) {
        return Zutat.deleteById(id);
    }

    @Override
    public boolean put(Long id, Zutat zutat) {
        //Zutat alteZutat = Zutat.findById(id);
        //TODO: 
        return true;
    }
}
