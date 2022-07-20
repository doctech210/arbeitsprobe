package de.hsos.swa.coldstoneicecreator.kreationen.control;

import java.util.List;

import de.hsos.swa.coldstoneicecreator.kreationen.entity.Hauskreation;
import de.hsos.swa.coldstoneicecreator.kunden.entity.Nutzer;
import de.hsos.swa.coldstoneicecreator.produkt.entity.Allergene;
import de.hsos.swa.coldstoneicecreator.produkt.entity.Zutat;

public interface HauskreationControl {
    public boolean create(Hauskreation hauskreation);

    public boolean create(Nutzer kunde, Hauskreation eigenkreation, Long anzahl);
    
    public List<Hauskreation> get();

    public List<Hauskreation> getOhneAllergene(List<Allergene> allergene);

    public Hauskreation getById(Long id);

    public boolean delete(Long id);

    public boolean put(Long id, Hauskreation hauskreation);

    public boolean putZutat(Long id, int zutatnummer, Zutat zutat);
}
