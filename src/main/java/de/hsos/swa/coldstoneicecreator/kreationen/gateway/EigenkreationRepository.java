package de.hsos.swa.coldstoneicecreator.kreationen.gateway;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;

import de.hsos.swa.coldstoneicecreator.kreationen.control.EigenkreationControl;
import de.hsos.swa.coldstoneicecreator.kreationen.entity.Eigenkreation;

@ApplicationScoped
public class EigenkreationRepository implements EigenkreationControl{
    
    @Override
    public boolean create(Eigenkreation eigenkreation) {
        eigenkreation.setId(null);
        eigenkreation.persist();
        return true;
    }

    @Override
    public List<Eigenkreation> get() {
        return Eigenkreation.listAll();
    }
    @Override
    public Eigenkreation getById(Long id) {
        return Eigenkreation.findById(id);
    }

    @Override
    public boolean delete(Long id) {
        return Eigenkreation.deleteById(id);
    }

    @Override
    public boolean put(Long id, Eigenkreation eigenkreation) {
        //Eigenkreation alteEigenkreation = Eigenkreation.findById(id);
        //TODO: 
        return true;
    }
    
}
