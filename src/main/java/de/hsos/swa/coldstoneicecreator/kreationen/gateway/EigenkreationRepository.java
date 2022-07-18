package de.hsos.swa.coldstoneicecreator.kreationen.gateway;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Event;
import javax.inject.Inject;

import de.hsos.swa.coldstoneicecreator.kreationen.boundary.dao.KreationDAO;
import de.hsos.swa.coldstoneicecreator.kreationen.control.EigenkreationControl;
import de.hsos.swa.coldstoneicecreator.kreationen.entity.Eigenkreation;
import de.hsos.swa.coldstoneicecreator.kunden.entity.Nutzer;

@ApplicationScoped
public class EigenkreationRepository implements EigenkreationControl{
    
    @Inject
    Event<KreationDAO> neueEigenkreation;

    @Override
    public boolean create(Nutzer kunde, Eigenkreation eigenkreation, Long anzahl) {
        eigenkreation.setId(null);
        eigenkreation.persist();
        kunde.addEigenkreation(eigenkreation);
        this.neueEigenkreation.fire(new KreationDAO(kunde, eigenkreation, anzahl, true));
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
