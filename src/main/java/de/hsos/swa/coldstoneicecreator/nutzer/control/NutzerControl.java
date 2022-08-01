//@author Marvin Gels 868603

package de.hsos.swa.coldstoneicecreator.nutzer.control;

import java.util.List;

import de.hsos.swa.coldstoneicecreator.nutzer.entity.Nutzer;


public interface NutzerControl {

    /**
     * Methode die einen neuen Nutzer erstellt/persistiert
     * @param nutzer Nutzer der persistiert werden soll
     * @return true, wenn erstellt, false, wenn nicht erstellt
     */
    public boolean create(Nutzer nutzer);
    
    /**
     * Methode die alle Nutzer zurueck gibt
     * @return List alle Nutzer
     */
    public List<Nutzer> get();

    /**
     * Methode die einen Nutzer zurueck gibt
     * @param id ID des gesuchten Nutzers
     * @return Gesuchte Nutzer
     */
    public Nutzer getById(Long id);

    /**
     * Methode die einen Nutzer loescht
     * @param id ID des gesuchten Nutzers
     * @return true, wenn geloescht, false, wenn nicht geloescht
     */
    public boolean delete(Long id);

    /**
     * Methode die einen Nutzer erlaubt Name und Passwort zu aendern
     * @param id ID des zu aendernden Nutzers
     * @param nutzer Neuer Nutzer
     * @return true, wenn geaendert, false, wenn nicht geaendert
     */
    public boolean put(Long id, Nutzer nutzer);

    /**
     * Methode die bestimmt, ob der uebergebene Name schon vergeben ist, oder nicht
     * @param name Name, der ueberprueft werden soll
     * @return true, wenn verfuegbar, false, wenn nicht verfuegbar
     */
    public boolean nameVerfuegbar(String name);
}
