//@author Marvin Gels 868603

package de.hsos.swa.coldstoneicecreator.produkt.control;

import java.util.List;

import de.hsos.swa.coldstoneicecreator.produkt.entity.Allergene;
import de.hsos.swa.coldstoneicecreator.produkt.entity.Eis;

public interface EisControl {

    /**
     * Methode um ein Eis zu erstellen/persistieren
     * @param eis zu persistierendes Eis
     * @return true, wenn erstellt, false, wenn nicht erstellt
     */
    public boolean create(Eis eis);

    /**
     * Methode die alle Eis zurueck gibt
     * @return List aller Eis
     */
    public List<Eis> get();

    /**
     * Methode die alle Eis ohne die uebergebenen Allergene zurueck gibt
     * @param allergene Allergene, die nicht enthalten sein sollen
     * @return List aller Eis ohne die, die die Allergene enthalten
     */
    public List<Eis> getOhneAllergene(List<Allergene> allergene);

    /**
     * Methode die ein Eis zurueck gibt
     * @param id ID des gesuchten Eis
     * @return Das gesuchte Eis
     */
    public Eis getById(Long id);
    
    /**
     * Methode die ein Eis loescht
     * @param id ID des gesuchten Eis
     * @return true, wenn geloescht, false, wenn nicht geloescht
     */
    public boolean delete(Long id);

    /**
     * Methode die ein Eis aendert
     * @param id ID des zu aendernden Eis
     * @param eis Neues Eis
     * @return true, wenn geaendert, false, wenn nicht geaendert
     */
    public boolean put(Long id, Eis eis);

}
