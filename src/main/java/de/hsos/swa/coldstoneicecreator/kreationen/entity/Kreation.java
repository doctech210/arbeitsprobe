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
}
