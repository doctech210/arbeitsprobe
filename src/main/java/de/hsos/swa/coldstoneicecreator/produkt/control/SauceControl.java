package de.hsos.swa.coldstoneicecreator.produkt.control;

import java.util.List;

import de.hsos.swa.coldstoneicecreator.produkt.entity.Sauce;

public interface SauceControl {
    
    public boolean create(Sauce sauce);

    public List<Sauce> get();

    public Sauce getById(Long id);
    
    public boolean delete(Long id);

    public boolean put(Long id, Sauce sauce);
}
