//@author Marvin Gels 868603

package de.hsos.swa.coldstoneicecreator.kreationen.control;

import java.util.List;

import de.hsos.swa.coldstoneicecreator.kreationen.entity.Eigenkreation;
import de.hsos.swa.coldstoneicecreator.nutzer.entity.Nutzer;
import de.hsos.swa.coldstoneicecreator.produkt.entity.Allergene;

public interface EigenkreationControl {
    
    /**
     * Methode mit der ein Nutzer eine Eigenkreation erstellen kann und die danach in die offene Bestellung eingetragen werden soll x Anzahl
     * @param nutzer Nutzer, der diese Eigenkreation erstellt
     * @param eigenkreation Eigenkreation die erstellt/persistiert werden soll
     * @param anzahl Anzahl, wie oft die Eigenkreation abgespeichert werden soll
     * @return true, wenn erstellt, false, wenn nicht erstellt
     */
    public boolean create(Nutzer nutzer, Eigenkreation eigenkreation, Long anzahl);

    /**
     * Methode mit der ein Nutzer eine Eigenkreation erstellen kann
     * @param nutzer Nutzer, der diese Eigenkreation erstellt
     * @param eigenkreation Eigenkreation die erstellt/persistiert werden soll
     * @return true, wenn erstellt, false, wenn nicht erstellt
     */
    public boolean create(Nutzer nutzer, Eigenkreation eigenkreation);
    
    /**
     * Methode die alle Eigenkreationen zurueck gibt (Admin)
     * @return List aller Eigenkreationen
     */
    public List<Eigenkreation> get();

    /**
     * Methode die eine Eigenkreation zurueck gibt (Admin)
     * @param id ID der gesuchten Eigenkreation
     * @return Die gesuchte Eigenkreation
     */
    public Eigenkreation getById(Long id);

    /**
     * Methode die eine Eigenkreation zurueck gibt, sollte diese eine des Kunden sein (Kunde)
     * @param id ID der gesuchten Eigenkreation
     * @param nutzer Kunde der eingeloggt ist
     * @return Die gesuchte Eigenkreation
     */
    public Eigenkreation getById(Long id, Nutzer nutzer);

    /**
     * Methode mit der eine Kunde eine der eigenen Eigenkreationen loeschen kann (Kunde)
     * @param id ID der gesuchten Eigenkreation
     * @param nutzer Kunde der eingeloggt ist
     * @return true, wenn geloescht, false, wenn nicht geloescht
     */
    public boolean delete(Long id, Nutzer nutzer);

    /**
     * Methode mit der ein Kunde Teile seine Eigenkreation aendern kann
     * @param id ID der gesuchten Eigenkreation
     * @param eigenkreation Die neue Eigenkreation
     * @param nutzer Kunde der eingeloggt ist
     * @return true, wenn geaendert, false, wenn nicht geaendert
     */
    public boolean put(Long id, Eigenkreation eigenkreation, Nutzer nutzer);

    /**
     * Methode mit der ein Kunde ein Zutat in seiner Eigenkreation aendern kann
     * @param id ID der gesuchten Eigenkreation
     * @param zutatnummer Stelle an der die Zutat in der List steht
     * @param neueZutatId ID der neuen Zutat
     * @param nutzer Kunde der eingeloggt ist
     * @return true, wenn geaendert, false, wenn nicht geaendert
     */
    public boolean putZutat(Long id, int zutatnummer, Long neueZutatId, Nutzer nutzer);

    /**
     * Methode mit der ein Kunde Anzahl x Eigenkreation in die aktuelle Bestellung legen kann
     * @param kreationId ID der Eigenkreation
     * @param anzahl Die Anzahl
     * @param nutzer Kunde der eingeloggt ist
     * @return true, wenn eingefuegt, false, wenn nicht eingefuegt
     */
    public boolean post(Long kreationId, Long anzahl, Nutzer nutzer);

    /**
     * Methode die alle Eigenkreationen zurueck gibt, die nicht die uebergebenen Allergene beinhalten
     * @param allergene Allergene nach denen gefiltert werden soll
     * @return List aller Eigenkreationen ohne die Allergene
     */
    public List<Eigenkreation> getOhneAllergene(List<Allergene> allergene);
}
