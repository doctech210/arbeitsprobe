package de.hsos.swa.coldstoneicecreator.bestellung.control;

import java.util.List;

import de.hsos.swa.coldstoneicecreator.bestellung.entity.BestellpostenEigen;
import de.hsos.swa.coldstoneicecreator.bestellung.entity.BestellpostenHaus;

public interface BestellpostenControl {
    
    public BestellpostenEigen postenEigenAnlegen(Long bestellId, Long kreationId, Long anzahl);

    public BestellpostenHaus postenHausAnlegen(Long bestellId, Long kreationId, Long anzahl);
    
    public List<BestellpostenEigen> bestellpostenAbfragen();

    public BestellpostenEigen postenAbfragen(Long postenId);

    public boolean postenAendern(Long bestellId, Long postenId, Long anzahl);

    public boolean postenLoeschen(Long postenId);
}
