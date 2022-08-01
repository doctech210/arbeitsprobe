//@author Stefan Bierenriede 852142

package de.hsos.swa.coldstoneicecreator.kreationen.boundary.dao;

import de.hsos.swa.coldstoneicecreator.kreationen.entity.Kreation;
import de.hsos.swa.coldstoneicecreator.nutzer.entity.Nutzer;

public class KreationDAO {

    private Nutzer nutzer;
    private Kreation kreation;
    private Long anzahl;
    private boolean eigen;
    
    public KreationDAO() {
    }

    public KreationDAO(Nutzer nutzer, Kreation kreation, Long anzahl, boolean eigen) {
        this.nutzer = nutzer;
        this.kreation = kreation;
        this.anzahl = anzahl;
        this.eigen = eigen;
    }

    public Nutzer getKunde() {
        return nutzer;
    }

    public Kreation getKreation() {
        return kreation;
    }

    public boolean isEigen() {
        return eigen;
    }

    public Long getAnzahl() {
        return anzahl;
    }

    public void setAnzahl(Long anzahl) {
        this.anzahl = anzahl;
    }

}
