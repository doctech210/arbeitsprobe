package de.hsos.swa.coldstoneicecreator.kreationen.control;

import java.util.List;

import de.hsos.swa.coldstoneicecreator.kreationen.entity.Eigenkreation;

public interface EigenkreationControl {
    
    public boolean create(Eigenkreation eigenkreation);
    
    public List<Eigenkreation> get();

    public Eigenkreation getById(Long id);

    public boolean delete(Long id);

    public boolean put(Long id, Eigenkreation eigenkreation);
}
