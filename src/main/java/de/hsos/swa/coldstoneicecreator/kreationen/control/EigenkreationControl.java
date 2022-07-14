package de.hsos.swa.coldstoneicecreator.kreationen.control;

import java.util.List;

import de.hsos.swa.coldstoneicecreator.kreationen.entity.Eigenkreation;
import de.hsos.swa.coldstoneicecreator.kunden.entity.Kunde;

public interface EigenkreationControl {
    
    public boolean create(Kunde kunde, Eigenkreation eigenkreation);
    
    public List<Eigenkreation> get();

    public Eigenkreation getById(Long id);

    public boolean delete(Long id);

    public boolean put(Long id, Eigenkreation eigenkreation);
}
