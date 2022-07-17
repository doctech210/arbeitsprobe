package de.hsos.swa.coldstoneicecreator.kreationen.boundary.dao;

import de.hsos.swa.coldstoneicecreator.kreationen.entity.Kreation;
import de.hsos.swa.coldstoneicecreator.kunden.entity.Nutzer;

public class BestellungIdDAO {
    private Nutzer kunde;
    private Kreation kreation;
    private Long anzahl;
    private Long bestellungsId;


    public BestellungIdDAO() {
    }

    public BestellungIdDAO(Nutzer kunde, Kreation kreation, Long anzahl, Long bestellungsId) {
        this.kunde = kunde;
        this.kreation = kreation;
        this.anzahl = anzahl;
        this.bestellungsId = bestellungsId;
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
    public Long getBestellungsId() {
        return bestellungsId;
    }
}
