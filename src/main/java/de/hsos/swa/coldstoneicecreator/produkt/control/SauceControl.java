//@author Marvin Gels 868603

package de.hsos.swa.coldstoneicecreator.produkt.control;

import java.util.List;

import de.hsos.swa.coldstoneicecreator.produkt.entity.Allergene;
import de.hsos.swa.coldstoneicecreator.produkt.entity.Sauce;

public interface SauceControl {
    
    /**
     * Methode um eine Sauce zu erstellen/persistieren
     * @param sauce zu persistierende Sauce
     * @return true, wenn erstellt, false, wenn nicht erstellt
     */
    public boolean create(Sauce sauce);

    /**
     * Methode die alle Saucen zurueck gibt
     * @return List aller Saucen
     */
    public List<Sauce> get();

    /**
     * Methode die eine Sauce zurueck gibt
     * @param id ID der gesuchten Sauce
     * @return Die gesuchte Sauce
     */
    public Sauce getById(Long id);
    
    /**
     * Methode die eine Sauce loescht
     * @param id ID der gesuchten Sauce
     * @return true, wenn geloescht, false, wenn nicht geloescht
     */
    public boolean delete(Long id);

    /**
     * Methode die eine Sauce aendert
     * @param id ID der zu aendernden Sauce
     * @param eis Neue Sauce
     * @return true, wenn geaendert, false, wenn nicht geaendert
     */
    public boolean put(Long id, Sauce sauce);

    /**
     * Methode die alle Saucen ohne die uebergebenen Allergene zurueck gibt
     * @param allergene Allergene, die nicht enthalten sein sollen
     * @return List aller Saucen ohne die, die die Allergene enthalten
     */
    public List<Sauce> getOhneAllergene(List<Allergene> allergene);
}
