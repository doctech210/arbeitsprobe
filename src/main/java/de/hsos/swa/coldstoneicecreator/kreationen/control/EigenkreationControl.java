package de.hsos.swa.coldstoneicecreator.kreationen.control;

import java.util.List;

import de.hsos.swa.coldstoneicecreator.kreationen.entity.Eigenkreation;
import de.hsos.swa.coldstoneicecreator.nutzer.entity.Nutzer;
import de.hsos.swa.coldstoneicecreator.produkt.entity.Allergene;

public interface EigenkreationControl {
    
    public boolean create(Nutzer kunde, Eigenkreation eigenkreation, Long anzahl);
    
    public List<Eigenkreation> get();

    public Eigenkreation getById(Long id);

    public Eigenkreation getById(Long id, Nutzer kunde);

    public boolean delete(Long id, Nutzer kunde);

    public boolean put(Long id, Eigenkreation eigenkreation, Nutzer kunde);

    public boolean putZutat(Long id, int zutatnummer, Long neueZutatId, Nutzer kunde);

    public boolean post(Long kreationId, Long anzahl, Nutzer nutzer);

    public List<Eigenkreation> getOhneAllergene(List<Allergene> allergene);
}
