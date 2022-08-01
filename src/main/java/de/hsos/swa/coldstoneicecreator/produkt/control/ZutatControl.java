//@author Marvin Gels 868603

package de.hsos.swa.coldstoneicecreator.produkt.control;

import java.util.List;

import de.hsos.swa.coldstoneicecreator.produkt.entity.Allergene;
import de.hsos.swa.coldstoneicecreator.produkt.entity.Zutat;

public interface ZutatControl {

    /**
     * Methode um eine Zutat zu erstellen/persistieren
     * @param eis zu persistierende Zutat
     * @return true, wenn erstellt, false, wenn nicht erstellt
     */
    public boolean create(Zutat zutat);

    /**
     * Methode die alle Zutaten zurueck gibt
     * @return List aller Zutaten
     */
    public List<Zutat> get();

    /**
     * Methode die alle Zutaten ohne die uebergebenen Allergene zurueck gibt
     * @param allergene Allergene, die nicht enthalten sein sollen
     * @return List aller Zutaten ohne die, die die Allergene enthalten
     */
    public List<Zutat> getOhneAllergene(List<Allergene> allergene);

    /**
     * Methode die eine Zutat zurueck gibt
     * @param id ID der gesuchten Zutat
     * @return Die gesuchte Zutat
     */
    public Zutat getById(Long id);
    
    /**
     * Methode die eine Zutat loescht
     * @param id ID der gesuchten Zutat
     * @return true, wenn geloescht, false, wenn nicht geloescht
     */
    public boolean delete(Long id);

    /**
     * Methode die eine Zutat aendert
     * @param id ID der zu aendernden Zutat
     * @param eis Neue Zutat
     * @return true, wenn geaendert, false, wenn nicht geaendert
     */
    public boolean put(Long id, Zutat zutat);
}
