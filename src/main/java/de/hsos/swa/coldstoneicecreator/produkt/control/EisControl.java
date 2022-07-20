package de.hsos.swa.coldstoneicecreator.produkt.control;

import java.util.List;

import de.hsos.swa.coldstoneicecreator.produkt.entity.Allergene;
import de.hsos.swa.coldstoneicecreator.produkt.entity.Eis;

public interface EisControl {
    public boolean create(Eis eis);

    public List<Eis> get();

    public List<Eis> getOhneAllergene(List<Allergene> allergene);

    public Eis getById(Long id);
    
    public boolean delete(Long id);

    public boolean put(Long id, Eis eis);

}
