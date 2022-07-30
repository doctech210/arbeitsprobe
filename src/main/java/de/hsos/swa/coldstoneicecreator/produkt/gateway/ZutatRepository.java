package de.hsos.swa.coldstoneicecreator.produkt.gateway;

import java.util.List;
import java.util.ArrayList;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.enterprise.event.Observes;

import de.hsos.swa.coldstoneicecreator.produkt.control.ZutatControl;
import de.hsos.swa.coldstoneicecreator.kreationen.boundary.dao.ZutatenIdDAO;
import de.hsos.swa.coldstoneicecreator.produkt.entity.Allergene;
import de.hsos.swa.coldstoneicecreator.produkt.entity.Zutat;

@ApplicationScoped
public class ZutatRepository implements ZutatControl{
    
    @Inject
    Event<Zutat> kreationUpdate;

    @Override
    public boolean create(Zutat zutat) {
        zutat.setId(null);
        zutat.persist();
        return true;
    }

    @Override
    public List<Zutat> get() {
        return Zutat.listAll();
    }

    @Override
    public List<Zutat> getOhneAllergene(List<Allergene> allergene){
        List<Zutat> zutaten = Zutat.listAll();
        List<Allergene> va = new ArrayList<>();
        for(Allergene allergen : allergene) {
            if(allergen.equals(Allergene.VEGAN)) {
                va.add(Allergene.EI);
                va.add(Allergene.HONIG);
                va.add(Allergene.LAKTOSE);
                va.add(Allergene.GELANTINE);
            }
        }
        va.addAll(allergene);
        for(Allergene allergen : va) {  
            zutaten.removeIf(zutat -> zutat.getAllergene().contains(allergen));
        }
        return zutaten;
    }

    @Override
    public Zutat getById(Long id) {
        return Zutat.findById(id);
    }
    
    @Override
    public boolean delete(Long id) {
        return Zutat.deleteById(id);
    }

    @Override
    public boolean put(Long id, Zutat zutat) {
        Zutat alteZutat = Zutat.findById(id);
        if(!(zutat.getName().equals("string") || zutat.getName().equals(""))){
            alteZutat.setName(zutat.getName());
        }
        if(!(zutat.getAllergene().isEmpty()) || zutat.getAllergene() != null){
            alteZutat.setAllergene(zutat.getAllergene());
        }
        alteZutat.setPremium(zutat.isPremium());
        this.kreationenUpdaten(alteZutat); 
        return true;
    }

    private void kreationenUpdaten(Zutat zutat){
        kreationUpdate.fire(zutat); //geht in das Eigenkreations- und das HauskreationsRepository
    }

    public void zutatWechseln(@Observes ZutatenIdDAO zutatenwechselDAO) {
        Zutat neueZutat = Zutat.findById(zutatenwechselDAO.getNeueZutatId());
        zutatenwechselDAO.getEigenkreation().addZutat(neueZutat);
    }
}
