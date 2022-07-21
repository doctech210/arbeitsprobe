package de.hsos.swa.coldstoneicecreator.kunden.gateway;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;

import de.hsos.swa.coldstoneicecreator.kunden.control.NutzerControl;
import de.hsos.swa.coldstoneicecreator.kunden.entity.Nutzer;
import de.hsos.swa.coldstoneicecreator.kunden.entity.UserLogin;
import io.quarkus.elytron.security.common.BcryptUtil;

@ApplicationScoped
public class NutzerRepository implements NutzerControl{
    
    @Override
    public boolean create(Nutzer kunde) {
        kunde.setId(null);
        kunde.persist();
        UserLogin.add(kunde.getName(), kunde.getPasswort(), kunde.getRole());
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
    public boolean put(Long id, Nutzer kunde) {
        boolean geaendert = false;
        Nutzer alteKunde = Nutzer.findById(id);
        UserLogin loginKunde = UserLogin.findById(id);
        String neuerName = kunde.getName();
        if(neuerName != "string" && neuerName != "") {
            alteKunde.setName(neuerName);
            loginKunde.username = neuerName;
            geaendert = true;
        }
        String neuesPasswort = kunde.getPasswort();
        if(neuesPasswort != "string" && neuesPasswort != "") {
            alteKunde.setPasswort(neuesPasswort);
            loginKunde.password = BcryptUtil.bcryptHash(neuesPasswort);
            geaendert = true;
        }
        return geaendert;
    }
    
}
