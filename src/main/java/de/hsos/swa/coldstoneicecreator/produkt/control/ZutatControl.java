package de.hsos.swa.coldstoneicecreator.produkt.control;

import java.util.List;

import de.hsos.swa.coldstoneicecreator.produkt.entity.Zutat;

public interface ZutatControl {
    public boolean create(Zutat zutat);

    public List<Zutat> get();

    public Zutat getById(Long id);
    
    public boolean delete(Long id);

    public boolean put(Long id, Zutat zutat);
}
