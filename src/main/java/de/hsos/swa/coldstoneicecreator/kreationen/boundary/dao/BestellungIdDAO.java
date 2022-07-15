package de.hsos.swa.coldstoneicecreator.kreationen.boundary.dao;

import de.hsos.swa.coldstoneicecreator.kreationen.entity.Kreation;
import de.hsos.swa.coldstoneicecreator.kunden.entity.Kunde;

public class BestellungIdDAO {
    private Kunde kunde;
    private Kreation kreation;
    private Long anzahl;
    private Long bestellungsId;


    public BestellungIdDAO() {
    }

    public BestellungIdDAO(Kunde kunde, Kreation kreation, Long anzahl, Long bestellungsId) {
        this.kunde = kunde;
        this.kreation = kreation;
        this.anzahl = anzahl;
        this.bestellungsId = bestellungsId;
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
    public Long getBestellungsId() {
        return bestellungsId;
    }
}
