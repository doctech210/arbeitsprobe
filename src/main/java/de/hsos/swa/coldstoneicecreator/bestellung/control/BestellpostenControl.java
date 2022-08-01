//@author Marvin Gels 868603

package de.hsos.swa.coldstoneicecreator.bestellung.control;

import java.util.List;

import de.hsos.swa.coldstoneicecreator.bestellung.entity.BestellpostenEigen;
import de.hsos.swa.coldstoneicecreator.bestellung.entity.BestellpostenHaus;

public interface BestellpostenControl {

    /**
     * Methode die alle BestellpostenEigen zurueck gibt
     * @return Liste aller BestellpostenEigen
     */
    public List<BestellpostenEigen> getAllEigen();

    /**
     * Methode die alle BestellpostenHaus zurueck gibt
     * @return Liste aller BestellpostenHaus
     */
    public List<BestellpostenHaus> getAllHaus();
    
    /**
     * Methode die einen neuen BestellpostenEigen mit den uebergebenen Parametern 
     * @param bestellId Die ID der Bestellung
     * @param kreationId Die ID der Kreation
     * @param anzahl Die Menge
     * @return Der neue BestellpostenEigen
     */
    public BestellpostenEigen postenEigenAnlegen(Long bestellId, Long kreationId, Long anzahl);

    /**
     * Methode die einen neuen BestellpostenHaus mit den uebergebenen Parametern 
     * @param bestellId Die ID der Bestellung
     * @param kreationId Die ID der Kreation
     * @param anzahl Die Menge
     * @return Der neue BestellpostenHaus
     */
    public BestellpostenHaus postenHausAnlegen(Long bestellId, Long kreationId, Long anzahl);

    /**
     * Methode die einen BestellpostenEigen um die Anzahl veraendert
     * @param bestellId Die ID der Bestellung
     * @param postenId Die ID des Posten
     * @param anzahl Die Menge
     * @return true, wenn der Posten geaendert wurde, false, wenn der Posten nicht geaendert werden konnte
     */
    public boolean postenAendernEigen(Long bestellId, Long postenId, Long anzahl);
    
    /**
     * Methode die einen BestellpostenHaus um die Anzahl veraendert
     * @param bestellId Die ID der Bestellung
     * @param postenId Die ID des Posten
     * @param anzahl Die Menge
     * @return true, wenn der Posten geaendert wurde, false, wenn der Posten nicht geaendert werden konnte
     */
    public boolean postenAendernHaus(Long bestellId, Long postenId, Long anzahl);

    /**
     * Methode mit der ein bestimmter BestellpostenEigen geloescht werden kann
     * @param postenId ID des zu loeschenden Posten
     * @return true, wenn geloescht wurde, false, wenn das loeschen fehlgeschlagen ist
     */
    public boolean postenLoeschenEigen(Long postenId);
    
    /**
     * Methode mit der ein bestimmter BestellpostenHaus geloescht werden kann
     * @param postenId ID des zu loeschenden Posten
     * @return true, wenn geloescht wurde, false, wenn das loeschen fehlgeschlagen ist
     */
    public boolean postenLoeschenHaus(Long postenId);

}
