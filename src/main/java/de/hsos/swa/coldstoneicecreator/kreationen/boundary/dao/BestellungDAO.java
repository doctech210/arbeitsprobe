package de.hsos.swa.coldstoneicecreator.kreationen.boundary.dao;

import de.hsos.swa.coldstoneicecreator.kreationen.entity.Kreation;
import de.hsos.swa.coldstoneicecreator.kunden.entity.Nutzer;

public class BestellungDAO {
    private Nutzer kunde;
    private Kreation kreation;
    private Long anzahl;


    public BestellungDAO() {
    }

    public BestellungDAO(Nutzer kunde, Kreation kreation, Long anzahl) {
        this.kunde = kunde;
        this.kreation = kreation;
        this.anzahl = anzahl;
    }

    public Nutzer getKunde() {
        return kunde;
    }
    public Kreation getKreation() {
        return kreation;
    }
    public Long getAnzahl() {
        return anzahl;
    }

}
