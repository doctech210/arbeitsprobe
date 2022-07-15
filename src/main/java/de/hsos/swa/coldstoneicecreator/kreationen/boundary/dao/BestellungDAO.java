package de.hsos.swa.coldstoneicecreator.kreationen.boundary.dao;

import de.hsos.swa.coldstoneicecreator.kreationen.entity.Kreation;
import de.hsos.swa.coldstoneicecreator.kunden.entity.Kunde;

public class BestellungDAO {
    private Kunde kunde;
    private Kreation kreation;
    private Long anzahl;


    public BestellungDAO() {
    }

    public BestellungDAO(Kunde kunde, Kreation kreation, Long anzahl) {
        this.kunde = kunde;
        this.kreation = kreation;
        this.anzahl = anzahl;
    }

    public Kunde getKunde() {
        return kunde;
    }
    public Kreation getKreation() {
        return kreation;
    }
    public Long getAnzahl() {
        return anzahl;
    }

}
