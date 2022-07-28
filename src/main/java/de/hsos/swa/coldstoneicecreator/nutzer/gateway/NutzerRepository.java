package de.hsos.swa.coldstoneicecreator.nutzer.gateway;

import java.util.List;
import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;

import de.hsos.swa.coldstoneicecreator.nutzer.control.NutzerControl;
import de.hsos.swa.coldstoneicecreator.nutzer.entity.Nutzer;

@ApplicationScoped
public class NutzerRepository implements NutzerControl{
    
    @Override
    public boolean create(Nutzer nutzer) {
        nutzer.setId(null);
        nutzer.persist();
        return true;
    }

    @Override
    public List<Nutzer> get() {
        return Nutzer.listAll();
    }
    @Override
    public Nutzer getById(Long id) {
        return Nutzer.findById(id);
    }

    @Override
    public boolean delete(Long id) {
        return Nutzer.deleteById(id);
    }

    @Override
    public boolean put(Long id, Nutzer nutzer) {
        boolean geaendert = false;
        Nutzer alteNutzer = Nutzer.findById(id);
        String neuerName = nutzer.getName();
        if(neuerName != "string" && neuerName != "") {
            alteNutzer.setName(neuerName);
            geaendert = true;
        }
        String neuesPasswort = nutzer.getPasswort();
        if(neuesPasswort != "string" && neuesPasswort != "") {
            alteNutzer.setPasswort(neuesPasswort);
            geaendert = true;
        }
        return geaendert;
    }
    
    @Override
    public boolean nameVerfuegbar(String name){
        Optional<Nutzer> nutzer = Nutzer.find("name", name).firstResultOptional();
        if(nutzer.isEmpty()) return true;
        return false;
    }
}
