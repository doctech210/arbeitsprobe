package de.hsos.swa.coldstoneicecreator.kreationen.gateway;

import java.util.List;

import javax.enterprise.context.RequestScoped;

import de.hsos.swa.coldstoneicecreator.kreationen.control.HauskreationControl;
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
}
