package de.hsos.swa.coldstoneicecreator.kreationen.control;

import java.util.List;

import de.hsos.swa.coldstoneicecreator.kreationen.entity.Hauskreation;
import de.hsos.swa.coldstoneicecreator.kunden.entity.Nutzer;

public interface HauskreationControl {
    public boolean create(Hauskreation hauskreation);

    public boolean create(Nutzer kunde, Hauskreation eigenkreation, Long anzahl);
    
    public List<Hauskreation> get();

    public Hauskreation getById(Long id);

    public boolean delete(Long id);

    public boolean put(Long id, Hauskreation hauskreation);
}
