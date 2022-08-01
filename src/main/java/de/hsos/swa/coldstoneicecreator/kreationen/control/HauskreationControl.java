//@author Marvin Gels 868603

package de.hsos.swa.coldstoneicecreator.kreationen.control;

import java.util.List;

import de.hsos.swa.coldstoneicecreator.kreationen.entity.Hauskreation;
import de.hsos.swa.coldstoneicecreator.nutzer.entity.Nutzer;
import de.hsos.swa.coldstoneicecreator.produkt.entity.Allergene;

public interface HauskreationControl {

    /**
     * Methode mit der ein Admin eine neue Hauskreation einfuegen kann
     * @param hauskreation Hauskreation die erstellt/persistiert werden soll
     * @return true, wenn erstellt, false, wenn nicht erstellt
     */
    public boolean create(Hauskreation hauskreation);

    /**
     * Methode mit der Anzahl x Hauskreation der offenen Bestellung des Nutzers eingefuegt wird
     * @param nutzer Kunde der eingeloggt ist
     * @param hauskreation Die neue Hauskreation
     * @param anzahl Die Anzahl, wie oft die Hauskreation hinzu gefuegt werden soll
     * @return true, wenn hinzugefuegt, false, wenn nicht hinzugefuegt
     */
    public boolean create(Nutzer nutzer, Hauskreation hauskreation, Long anzahl);
    
    /**
     * Methode die alle Hauskreationen zurueck gibt (Admin)
     * @return List aller Hauskreationen
     */
    public List<Hauskreation> get();

    /**
     * Methode die alle Hauskreationen zurueck gibt, die nicht die uebergebenen Allergene beinhalten
     * @param allergene Allergene nach denen gefiltert werden soll
     * @return List aller Hauskreationen ohne die Allergene
     */
    public List<Hauskreation> getOhneAllergene(List<Allergene> allergene);

    /**
     * Methode die eine Hauskreation zurueck gibt (Admin)
     * @param id ID der gesuchten Hauskreation
     * @return Die gesuchte Hauskreation
     */
    public Hauskreation getById(Long id);

    /**
     * Methode mit der eine Hauskreation geloescht wird
     * @param id ID der gesuchten Hauskreation
     * @return true, wenn geloescht, false, wenn nicht geloescht
     */
    public boolean delete(Long id);

    /**
     * Methode mit der ein Kunde Teile seine Hauskreation aendern kann
     * @param id ID der gesuchten Hauskreation
     * @param hauskreation Die neue Hauskreation
     * @param nutzer Kunde der eingeloggt ist
     * @return true, wenn geaendert, false, wenn nicht geaendert
     */
    public boolean put(Long id, Hauskreation hauskreation);
}
