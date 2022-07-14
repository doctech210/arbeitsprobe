package de.hsos.swa.coldstoneicecreator.kreationen.boundary.dao;

import de.hsos.swa.coldstoneicecreator.kreationen.entity.Kreation;
import de.hsos.swa.coldstoneicecreator.kunden.entity.Kunde;

public class KreationDAO {

    private Kunde kunde;
    private Kreation kreation;
    private Long bestellId;
    private boolean eigen;
    
    public KreationDAO() {
    }

    public KreationDAO(Kunde kunde, Kreation kreation, boolean eigen) {
        this.kunde = kunde;
        this.kreation = kreation;
        this.eigen = eigen;
    }

    public Kunde getKunde() {
        return kunde;
    }

    public Kreation getKreation() {
        return kreation;
    }

    public Long getBestellId() {
        return bestellId;
    }

    public void setBestellId(Long bestellId) {
        this.bestellId = bestellId;
    }

    public boolean isEigen() {
        return eigen;
    }

}
