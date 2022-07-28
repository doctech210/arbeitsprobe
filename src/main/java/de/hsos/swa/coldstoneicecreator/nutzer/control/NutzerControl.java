package de.hsos.swa.coldstoneicecreator.nutzer.control;

import java.util.List;

import de.hsos.swa.coldstoneicecreator.nutzer.entity.Nutzer;


public interface NutzerControl {
    public boolean create(Nutzer nutzer);
    
    public List<Nutzer> get();

    public Nutzer getById(Long id);

    public boolean delete(Long id);

    public boolean put(Long id, Nutzer nutzer);

    public boolean nameVerfuegbar(String name);
}