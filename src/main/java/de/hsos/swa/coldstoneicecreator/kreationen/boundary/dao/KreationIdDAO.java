package de.hsos.swa.coldstoneicecreator.kreationen.boundary.dao;

import de.hsos.swa.coldstoneicecreator.kreationen.entity.Kreation;
import de.hsos.swa.coldstoneicecreator.kunden.entity.Nutzer;

public class KreationIdDAO {

    private Nutzer kunde;
    private Kreation kreation;
    private Long anzahl;
    private Long bestellId;
    private boolean eigen;
    
    public KreationIdDAO() {
    }

    public KreationIdDAO(KreationDAO kreationDAO, Long bestellId){
        this.kunde = kreationDAO.getKunde();
        this.kreation = kreationDAO.getKreation();
        this.anzahl = kreationDAO.getAnzahl();
        this.eigen = kreationDAO.isEigen();
        this.bestellId = bestellId;
    }

    public Nutzer getKunde() {
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

    public Long getAnzahl() {
        return anzahl;
    }

    public void setAnzahl(Long anzahl) {
        this.anzahl = anzahl;
    }

}
