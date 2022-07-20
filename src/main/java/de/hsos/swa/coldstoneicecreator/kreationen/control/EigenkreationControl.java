package de.hsos.swa.coldstoneicecreator.kreationen.control;

import java.util.List;

import de.hsos.swa.coldstoneicecreator.kreationen.entity.Eigenkreation;
import de.hsos.swa.coldstoneicecreator.kunden.entity.Nutzer;
import de.hsos.swa.coldstoneicecreator.produkt.entity.Allergene;

public interface EigenkreationControl {
    
    public boolean create(Nutzer kunde, Eigenkreation eigenkreation, Long anzahl);
    
    public List<Eigenkreation> get();

    public Eigenkreation getById(Long id);

    public boolean delete(Long id);

    public boolean put(Long id, Eigenkreation eigenkreation);

    public boolean putZutat(Long id, int zutatnummer, Long neueZutatId);

    public boolean post(Long kreationId, Long anzahl, Nutzer nutzer);

    public List<Eigenkreation> getOhneAllergene(List<Allergene> allergene);
}
