package de.hsos.swa.coldstoneicecreator.kreationen.control;

import java.util.List;

import de.hsos.swa.coldstoneicecreator.kreationen.entity.Eigenkreation;
import de.hsos.swa.coldstoneicecreator.kunden.entity.Nutzer;

public interface EigenkreationControl {
    
    public boolean create(Nutzer kunde, Eigenkreation eigenkreation, Long anzahl);
    
    public List<Eigenkreation> get();

    public Eigenkreation getById(Long id);

    public boolean delete(Long id);

    public boolean put(Long id, Eigenkreation eigenkreation);
}
