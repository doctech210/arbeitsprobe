package de.hsos.swa.coldstoneicecreator.kreationen.control;

import java.util.List;

import de.hsos.swa.coldstoneicecreator.kreationen.entity.Eigenkreation;
import de.hsos.swa.coldstoneicecreator.nutzer.entity.Nutzer;
import de.hsos.swa.coldstoneicecreator.produkt.entity.Allergene;

public interface EigenkreationControl {
    
    public boolean create(Nutzer nutzer, Eigenkreation eigenkreation, Long anzahl);
    
    public List<Eigenkreation> get();

    public Eigenkreation getById(Long id);

    public Eigenkreation getById(Long id, Nutzer nutzer);

    public boolean delete(Long id, Nutzer nutzer);

    public boolean put(Long id, Eigenkreation eigenkreation, Nutzer nutzer);

    public boolean putZutat(Long id, int zutatnummer, Long neueZutatId, Nutzer nutzer);

    public boolean post(Long kreationId, Long anzahl, Nutzer nutzer);

    public List<Eigenkreation> getOhneAllergene(List<Allergene> allergene);
}
