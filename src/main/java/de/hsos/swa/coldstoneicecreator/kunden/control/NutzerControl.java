package de.hsos.swa.coldstoneicecreator.kunden.control;

import java.util.List;

import de.hsos.swa.coldstoneicecreator.kunden.entity.Nutzer;

public interface NutzerControl {
    public boolean create(Nutzer kunde);
    
    public List<Nutzer> get();

    public Nutzer getById(Long id);

    public boolean delete(Long id);

    public boolean put(Long id, Nutzer kunde);
}