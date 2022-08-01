//@author Marvin Gels 868603

package de.hsos.swa.coldstoneicecreator.bestellung.control;

import java.util.List;

import de.hsos.swa.coldstoneicecreator.bestellung.entity.Bestellung;

public interface BestellungControl {
    
    /**
     * Methode die alle Bestellungen zurueck gibt (Admin)
     * @return List aller Bestellungen
     */
    public List<Bestellung> bestellungenAbfragen();

    /**
     * Methode die alle Bestellungen des Nutzers der uebergebenen nutzerID zurueck gibt (Kunde)
     * @param nutzerId ID des Nutzers
     * @return List aller Bestellungen des Nutzers
     */
    public List<Bestellung> bestellungenAbfragen(Long nutzerId);
    
    /**
     * Methode die eine Bestellung zurueck gibt (Admin)
     * @param bestellId ID der gesuchten Bestellung
     * @return Gesuchte Bestellung
     */
    public Bestellung bestellungAbfragen(Long bestellId);

    /**
     * Methode die eine Bestellung des Nutzers der uebergebenen nutzerID zurueck gibt (Kunde)
     * @param bestellId ID der gesuchten Bestellung
     * @param nutzerId ID des Nutzers
     * @return Gesuchte Bestellung des Nutzers
     */
    public Bestellung bestellungAbfragen(Long bestellId, Long nutzerId);

    /**
     * Methode die eine neue Bestellung anlegt und in der Datenbank persistiert
     * @param bestellungen Die zu persistierende Bestellung
     * @param nutzerId ID des Nutzers
     * @return Die persistierte Bestellung
     */
    public Bestellung bestellungAnlegen(Bestellung bestellungen, Long nutzerId);

    /**
     * Methode die eine Bestellung loescht (Admin)
     * @param bestellId ID der zu loeschenden Bestellung
     * @return true, wenn geloescht, false, wenn nicht geloescht
     */
    public boolean bestellungLoeschen(Long bestellId);
    
    /**
     * Methode die eine Bestellung loescht (Kunde)
     * @param bestellId ID der zu loeschenden Bestellung
     * @param nutzerId ID des Nutzers
     * @return true, wenn geloescht, false, wenn nicht geloescht
     */
    public boolean bestellungLoeschen(Long bestellId, Long nutzerId);
    
    /**
     * Methode die bestimmt ob eine Bestellung leer ist oder nicht
     * @param bestellId ID der zu ueberpruefenden Bestellung
     * @return true, wenn leer, false, wenn etwas in der Bestellung ist
     */
    public boolean isBestellungLeer(Long bestellId);
}
