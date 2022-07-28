package de.hsos.swa.coldstoneicecreator.kreationen.control;

import java.util.List;

import de.hsos.swa.coldstoneicecreator.kreationen.entity.Hauskreation;
import de.hsos.swa.coldstoneicecreator.nutzer.entity.Nutzer;
import de.hsos.swa.coldstoneicecreator.produkt.entity.Allergene;

public interface HauskreationControl {
    public boolean create(Hauskreation hauskreation);

    public boolean create(Nutzer nutzer, Hauskreation eigenkreation, Long anzahl);
    
    public List<Hauskreation> get();

    public List<Hauskreation> getOhneAllergene(List<Allergene> allergene);

    public Hauskreation getById(Long id);

    public boolean delete(Long id);

    public boolean put(Long id, Hauskreation hauskreation);
}
