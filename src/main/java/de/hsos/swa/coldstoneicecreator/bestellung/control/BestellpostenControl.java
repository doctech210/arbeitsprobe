package de.hsos.swa.coldstoneicecreator.bestellung.control;

import de.hsos.swa.coldstoneicecreator.bestellung.entity.BestellpostenEigen;
import de.hsos.swa.coldstoneicecreator.bestellung.entity.BestellpostenHaus;

public interface BestellpostenControl {
    
    public BestellpostenEigen postenEigenAnlegen(Long bestellId, Long kreationId, Long anzahl);

    public BestellpostenHaus postenHausAnlegen(Long bestellId, Long kreationId, Long anzahl);

    public boolean postenAendernEigen(Long bestellId, Long postenId, Long anzahl);
    
    public boolean postenAendernHaus(Long bestellId, Long postenId, Long anzahl);

    public boolean postenLoeschenEigen(Long postenId);

    public boolean postenLoeschenHaus(Long postenId);
}
