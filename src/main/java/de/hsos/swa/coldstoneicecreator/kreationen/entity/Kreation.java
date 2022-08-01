//@author Marvin Gels 868603

package de.hsos.swa.coldstoneicecreator.kreationen.entity;

import java.util.List;
import java.util.Set;

import de.hsos.swa.coldstoneicecreator.produkt.entity.*;

public interface Kreation {
    Long id = null;
    Eis eissorte = null;
    Eis eissorte2 = null;
    List<Zutat> zutaten = null;
    Sauce sauce = null;
    String name = "";
    Set<Allergene> allergene = null;

    Long getId();

    void checkAllergene();

    Set<Allergene> getAllergene();

    void setAllergene(Set<Allergene> allergene);

    void setId(Long id);

    String getName();

    void setName(String name);

    Eis getEissorte();

    void setEissorte(Eis eissorte);

    Eis getEissorte2();

    void setEissorte2(Eis eissorte2);

    List<Zutat> getZutaten();

    void setZutaten(List<Zutat> zutaten);

    Sauce getSauce();

    void setSauce(Sauce sauce);
}
