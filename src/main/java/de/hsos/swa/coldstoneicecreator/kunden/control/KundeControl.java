package de.hsos.swa.coldstoneicecreator.kunden.control;

import java.util.List;

import de.hsos.swa.coldstoneicecreator.kunden.entity.Kunde;

public interface KundeControl {
    public boolean create(Kunde kunde);
    
    public List<Kunde> get();

    public Kunde getById(Long id);

    public boolean delete(Long id);

    public boolean put(Long id, Kunde kunde);
}
